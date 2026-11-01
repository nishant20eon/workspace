
package org.nishant.lld.ratelimiter.model;

import lombok.Getter;

@Getter
public class RateLimitConfig {

    private final int maxRequests;
    private final long windowMillis;

    public RateLimitConfig(int maxRequests, long windowMillis) {
        if (maxRequests <= 0 || windowMillis <= 0) {
            throw new IllegalArgumentException(
                    "Limit and window size must be positive");
        }

        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }
}
