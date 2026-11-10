package org.nishant.dsa.arrayshashing;
import java.util.*;
public final class TwoSum {
    private TwoSum() {}
    // Brute force: O(n^2) time, O(1) auxiliary space.
    public static int[] bruteForce(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++)
            for(int j = i+1; j < nums.length; j++)
                if(nums[i]+nums[j] == target) return new int[]{i,j};
        return new int[0];
    }
    // HashMap complement: O(n) expected time, O(n) space.
    public static int[] hashMap(int[] nums, int target) {
        Map<Integer,Integer> position = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if(position.containsKey(complement))
                return new int[]{position.get(complement),i};
            position.put(nums[i],i); // after lookup prevents same element reuse
        }
        return new int[0];
    }
}
