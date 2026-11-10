package org.nishant.dsa.slidingwindow;
public final class MaximumAverageSubarrayI {
    private MaximumAverageSubarrayI() {}
    // Brute force: O(n*k) time, O(1) space, k in [1,n].
    public static double bruteForce(int[] nums, int k) {
        long best = Long.MIN_VALUE;
        for(int i = 0; i + k <= nums.length; i++) {
            long sum = 0;
            for(int j = i; j < i+k; j++) sum += nums[j];
            best = Math.max(best,sum);
        }
        return (double) best/k;
    }
    // Rolling fixed window: O(n) time, O(1) space.
    public static double optimal(int[] nums, int k) {
        long sum = 0;
        for(int i = 0; i < k; i++) sum += nums[i];
        long best = sum;
        for(int right = k; right < nums.length; right++) {
            sum += nums[right] - nums[right-k]; // add incoming, remove outgoing
            best = Math.max(best,sum);
        }
        return (double) best/k;
    }
}
