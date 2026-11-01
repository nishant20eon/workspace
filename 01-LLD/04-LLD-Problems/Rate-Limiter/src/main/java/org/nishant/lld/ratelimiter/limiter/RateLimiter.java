package org.nishant.lld.ratelimiter.limiter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.nishant.lld.ratelimiter.enums.RateLimitType;
import org.nishant.lld.ratelimiter.model.RateLimitConfig;

@Getter
@AllArgsConstructor
public abstract class RateLimiter {

    protected final RateLimitConfig config;
    protected final RateLimitType type;

    public abstract boolean allowRequest(String userId);
}
