package org.nishant.dsa.intervals;
import java.util.*;
public final class MergeIntervals {
    private MergeIntervals() {}
    // O(n^2) comparison based on interval component expansion.
    // Baseline intentionally not preferred for performance.
    public static int[][] bruteForce(int[][] intervals) {
        boolean[] used = new boolean[intervals.length];
        List<int[]> merged = new ArrayList<>();
        for(int i = 0; i < intervals.length; i++) {
            if(used[i]) continue;
            int start = intervals[i][0], end = intervals[i][1];
            used[i] = true;
            boolean changed;
            do {
                changed = false;
                for(int j = 0; j < intervals.length; j++) {
                    if(used[j]) continue;
                    if(intervals[j][0] <= end && intervals[j][1] >= start) {
                        start = Math.min(start,intervals[j][0]);
                        end = Math.max(end,intervals[j][1]);
                        used[j] = true;
                        changed = true;
                    }
                }
            } while(changed);
            merged.add(new int[]{start,end});
        }
        merged.sort(Comparator.comparingInt(a -> a[0]));
        return merged.toArray(new int[0][]);
    }
    // Sorted sweep. O(n log n) time, O(n) output space.
    public static int[][] sweep(int[][] intervals) {
        int[][] copy = Arrays.stream(intervals).map(int[]::clone).toArray(int[][]::new);
        Arrays.sort(copy,Comparator.comparingInt(a -> a[0]));
        List<int[]> ans = new ArrayList<>();
        for(int[] current: copy) {
            if(ans.isEmpty() || current[0] > ans.get(ans.size()-1)[1]) ans.add(current);
            else {
                int[] last = ans.get(ans.size()-1);
                last[1] = Math.max(last[1],current[1]);
            }
        }
        return ans.toArray(new int[0][]);
    }
}
