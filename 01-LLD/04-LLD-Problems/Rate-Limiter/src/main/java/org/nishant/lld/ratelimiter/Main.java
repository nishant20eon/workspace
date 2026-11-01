
package org.nishant.lld.ratelimiter;

import org.nishant.lld.ratelimiter.model.RateLimitConfig;
import org.nishant.lld.ratelimiter.limiter.RateLimiter;
import org.nishant.lld.ratelimiter.enums.RateLimitType;
import org.nishant.lld.ratelimiter.factory.RateLimiterFactory;

public class Main {

    public static void main(String[] args) {

        RateLimitConfig config =
                new RateLimitConfig(5, 60_000);

        RateLimiter limiter = RateLimiterFactory.createRateLimiter(
                RateLimitType.FIXED_WINDOW,
                config
        );

        for (int i = 1; i <= 7; i++) {

            boolean allowed = limiter.allowRequest("user1");

            System.out.println(
                    "Request " + i + " -> " +
                    (allowed ? "ALLOWED" : "REJECTED")
            );
        }
    }
}
