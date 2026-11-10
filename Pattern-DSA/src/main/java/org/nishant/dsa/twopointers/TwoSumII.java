package org.nishant.dsa.twopointers;
import java.util.*;
public final class TwoSumII {
    private TwoSumII() {}
    // Sorted input; 1-based output indices. O(n^2).
    public static int[] bruteForce(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++)
            for(int j = i+1; j < nums.length; j++)
                if(nums[i]+nums[j]==target) return new int[]{i+1,j+1};
        return new int[0];
    }
    // O(n) two-pointer scan, O(1) extra space.
    public static int[] optimal(int[] nums, int target) {
        int left = 0, right = nums.length-1;
        while(left < right) {
            int sum = nums[left]+nums[right];
            if(sum == target) return new int[]{left+1,right+1};
            if(sum < target) left++;
            else right--;
        }
        return new int[0];
    }
}
