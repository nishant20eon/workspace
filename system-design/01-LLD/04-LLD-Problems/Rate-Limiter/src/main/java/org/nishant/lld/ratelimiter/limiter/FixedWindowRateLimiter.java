
package org.nishant.lld.ratelimiter.limiter;

import org.nishant.lld.ratelimiter.enums.RateLimitType;
import org.nishant.lld.ratelimiter.model.RateLimitConfig;
import org.nishant.lld.ratelimiter.model.Window;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.LongSupplier;

public class FixedWindowRateLimiter extends RateLimiter {

    // One Window object per user
    private final ConcurrentHashMap<String, Window> users =
            new ConcurrentHashMap<>();

    private final LongSupplier currentTimeMillis;

    public FixedWindowRateLimiter(RateLimitConfig config) {
        this(config, System::currentTimeMillis);
    }

    // Allows us to control time during tests
    public FixedWindowRateLimiter(
            RateLimitConfig config,
            LongSupplier currentTimeMillis) {

        super(config, RateLimitType.FIXED_WINDOW);
        this.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public boolean allowRequest(String userId) {

        // Step 1: Get current time
        long now = currentTimeMillis.getAsLong();

        // Step 2: Find current fixed window start
        long windowSize = config.getWindowMillis();

        long currentWindowStart =
                (now / windowSize) * windowSize;

        // Step 3: Find or create the user's Window
        Window window = users.computeIfAbsent(
                userId,
                id -> new Window(currentWindowStart, 0)
        );

        // Step 4: Lock this user's Window
        synchronized (window) {

            // Step 5: Reset if a new window has started
            if (window.getWindowStart() != currentWindowStart) {
                window.setWindowStart(currentWindowStart);
                window.setCount(0);
            }

            // Step 6: Check whether limit is reached
            if (window.getCount() >= config.getMaxRequests()) {
                return false;
            }

            // Step 7: Increment and allow
            window.setCount(window.getCount() + 1);

            return true;
        }
    }
}
