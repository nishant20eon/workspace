package org.nishant.lld.ratelimiter.factory;

import org.nishant.lld.ratelimiter.enums.RateLimitType;
import org.nishant.lld.ratelimiter.limiter.*;
import org.nishant.lld.ratelimiter.model.RateLimitConfig;

public class RateLimiterFactory {

    public static RateLimiter createRateLimiter(
            RateLimitType algo,
            RateLimitConfig config) {

        return switch (algo) {
            case FIXED_WINDOW ->
                    new FixedWindowRateLimiter(config);

            case TOKEN_BUCKET, SLIDING_WINDOW ->
                    throw new UnsupportedOperationException(
                            "Not implemented yet: " + algo
                    );
        };
    }
}