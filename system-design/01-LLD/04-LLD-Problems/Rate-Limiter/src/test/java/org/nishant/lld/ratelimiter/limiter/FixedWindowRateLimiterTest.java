
package org.nishant.lld.ratelimiter.limiter;

import org.junit.jupiter.api.Test;
import org.nishant.lld.ratelimiter.model.RateLimitConfig;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class FixedWindowRateLimiterTest {

    @Test
    void shouldResetCounterInNextWindow() {

        // Simulated time: 30 seconds
        AtomicLong time = new AtomicLong(30_000);

        RateLimitConfig config =
                new RateLimitConfig(5, 60_000);

        FixedWindowRateLimiter limiter =
                new FixedWindowRateLimiter(config, time::get);

        // First minute: 5 allowed, 2 rejected
        for (int i = 1; i <= 7; i++) {
            boolean allowed = limiter.allowRequest("user1");

            assertEquals(i <= 5, allowed);
        }

        // Move time into the next minute
        time.set(90_000);

        // Second minute: 5 allowed, 2 rejected
        for (int i = 1; i <= 7; i++) {
            boolean allowed = limiter.allowRequest("user1");

            assertEquals(i <= 5, allowed);
        }
    }

    @Test
    void shouldMaintainSeparateCountersForUsers() {

        RateLimitConfig config =
                new RateLimitConfig(5, 60_000);

        FixedWindowRateLimiter limiter =
                new FixedWindowRateLimiter(config, () -> 30_000L);

        for (int i = 0; i < 5; i++) {
            assertTrue(limiter.allowRequest("user1"));
        }

        assertFalse(limiter.allowRequest("user1"));

        // user2 has an independent counter
        assertTrue(limiter.allowRequest("user2"));
    }
}
