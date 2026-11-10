package org.nishant.dsa.intervals;
import java.util.*;
public final class MergeIntervals {
    private MergeIntervals() {}

    public static int[][] sweep(int[][] intervals) {

        if (intervals.length <= 1) {
            return intervals;
        }

        // Step 1: Sort by starting position.
        Arrays.sort(intervals,
            (a, b) -> Integer.compare(a[0], b[0]));

        // Step 2: Store the merged intervals.
        List<int[]> result = new ArrayList<>();

        for (int[] current : intervals) {

            // First interval OR no overlap with last merged.
            if (result.isEmpty() ||
                current[0] > result.get(result.size() - 1)[1]) {

                result.add(new int[]{current[0], current[1]});

            } else {

                // Step 3: Merge overlapping intervals.
                int[] previous = result.get(result.size() - 1);

                previous[1] = Math.max(previous[1], current[1]);
            }
        }

        // Step 4: Convert List<int[]> into int[][].
        return result.toArray(new int[result.size()][]);
    }

}
