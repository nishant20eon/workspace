
package org.nishant.lld.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Window {

    private long windowStart;
    private int count;
}
