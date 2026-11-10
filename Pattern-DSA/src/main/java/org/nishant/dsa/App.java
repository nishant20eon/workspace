package org.nishant.dsa;

import java.util.Arrays;
import org.nishant.dsa.majority.MajorityElement;
import org.nishant.dsa.intervals.MergeIntervals;

public final class App {
    public static void main(String[] args) {
        System.out.println("Pattern DSA | Java 17+ | Maven");
        System.out.println("Majority (voting): " + MajorityElement.voting(new int[]{2,2,1,1,1,2,2}));
        int[][] merged = MergeIntervals.sweep(new int[][]{{1,3},{2,6},{8,10}});
        System.out.println("Merge intervals: " + Arrays.deepToString(merged));
        System.out.println("Run mvn test, then log each attempt with scripts/progress.py");
    }
}
