package org.nishant.dsa.slidingwindow;
public final class MinimumSizeSubarraySum {
    private MinimumSizeSubarraySum() {}
    // Positive nums only. Brute force O(n^2) time, O(1) space.
    public static int bruteForce(int target, int[] nums) {
        int best = Integer.MAX_VALUE;
        for(int i = 0; i < nums.length; i++) {
            long sum = 0;
            for(int j = i; j < nums.length; j++) {
                sum += nums[j];
                if(sum >= target) {best = Math.min(best,j-i+1);break;}
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }
    // Expand, then shrink while valid. O(n) time, O(1) space.
    public static int optimal(int target, int[] nums) {
        int left = 0, best = Integer.MAX_VALUE;
        long sum = 0;
        for(int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while(sum >= target) {
                best = Math.min(best,right-left+1); // record before removing
                sum -= nums[left++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }
}
