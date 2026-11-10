
package org.nishant.dsa.intervals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MergeIntervalsTest {

    @Test
    void shouldMergeOverlappingIntervals() {

        int[][] input = {
            {1, 3}, {2, 6}, {8, 10}, {15, 18}
        };

        int[][] expected = {
            {1, 6}, {8, 10}, {15, 18}
        };

        assertArrayEquals(expected, MergeIntervals.sweep(input));
    }

    @Test
    void shouldMergeTouchingIntervals() {

        int[][] input = {{1, 4}, {4, 5}};
        int[][] expected = {{1, 5}};

        assertArrayEquals(expected, MergeIntervals.sweep(input));
    }

    @Test
    void shouldHandleContainedIntervals() {

        int[][] input = {{1, 10}, {2, 3}};
        int[][] expected = {{1, 10}};

        assertArrayEquals(expected, MergeIntervals.sweep(input));
    }

    @Test
    void shouldHandleEmptyInput() {

        assertArrayEquals(
            new int[0][],
            MergeIntervals.sweep(new int[0][])
        );
    }
}
