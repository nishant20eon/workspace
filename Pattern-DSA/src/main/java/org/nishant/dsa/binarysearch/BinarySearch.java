package org.nishant.dsa.binarysearch;
public final class BinarySearch {
    private BinarySearch() {}
    // Baseline: linear scan. Time O(n), space O(1).
    public static int linear(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++) if(nums[i] == target) return i;
        return -1;
    }
    // Sorted array, closed interval [left,right]. O(log n) time.
    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while(left <= right) {
            int mid = left + (right-left)/2;
            if(nums[mid] == target) return mid;
            if(nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
    // First index with nums[index] >= target; half-open interval [left,right).
    public static int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length;
        while(left < right) {
            int mid = left + (right-left)/2;
            if(nums[mid] >= target) right = mid;
            else left = mid + 1;
        }
        return left;
    }
}
