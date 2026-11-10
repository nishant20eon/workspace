package org.nishant.dsa.arrayshashing;
import java.util.*;
public final class SubarraySumEqualsK {
    private SubarraySumEqualsK() {}
    // O(n^2) brute force; supports negatives.
    public static int bruteForce(int[] nums, int k) {
        int count = 0;
        for(int i = 0; i < nums.length; i++) {
            long sum = 0;
            for(int j = i; j < nums.length; j++) {
                sum += nums[j];
                if(sum == k) count++;
            }
        }
        return count;
    }
    // Prefix-sum frequency map O(n) expected time / O(n) space.
    public static int optimal(int[] nums, int k) {
        Map<Long,Integer> freq = new HashMap<>();
        freq.put(0L,1); // empty prefix
        long prefix = 0;
        int count = 0;
        for(int x: nums) {
            prefix += x;
            count += freq.getOrDefault(prefix-k,0);
            freq.merge(prefix,1,Integer::sum); // count AFTER query
        }
        return count;
    }
}
