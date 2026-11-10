package org.nishant.dsa.majority;
import java.util.HashMap;
import java.util.Map;
public final class MajorityElement {
    private MajorityElement() {}
    // Approach 1: HashMap counting. Time O(n), space O(n).
    public static int hashMap(int[] nums) {
        Map<Integer,Integer> freq = new HashMap<>();
        for(int x : nums) {
            int count = freq.merge(x, 1, Integer::sum);
            if(count > nums.length / 2) return x;
        }
        throw new IllegalArgumentException("No majority");
    }
    // Approach 2: Boyer-Moore cancellation. O(n) time, O(1) space.
    // Input assumes a majority exists (as in LeetCode 169).
    public static int voting(int[] nums) {
        int count = 0, candidate = 0;
        for(int x: nums) {
            if(count == 0) candidate = x;
            count += (candidate == x) ? 1 : -1;
        }
        return candidate;
    }
    // Optional verification when a majority is not guaranteed.
    public static Integer verifiedVoting(int[] nums) {
        if(nums.length == 0) return null;
        int candidate = voting(nums), freq = 0;
        for(int x : nums) if(x == candidate) freq++;
        return freq > nums.length / 2 ? candidate : null;
    }
}
