package org.nishant.dsa.majority;
import java.util.*;
public final class MajorityElementII {
    private MajorityElementII() {}
    // Approach 1: frequency map. O(n) time and O(n) space.
    public static List<Integer> hashMap(int[] nums) {
        Map<Integer,Integer> freq = new HashMap<>();
        for(int x : nums) freq.merge(x, 1, Integer::sum);
        List<Integer> result = new ArrayList<>();
        for(var e : freq.entrySet()) if(e.getValue() > nums.length / 3) result.add(e.getKey());
        return result;
    }
    // Approach 2: extended Boyer-Moore. O(n) time, O(1) auxiliary space.
    public static List<Integer> voting(int[] nums) {
        int a = 0, b = 0, ac = 0, bc = 0;
        for(int x: nums) {
            if(ac > 0 && x == a) ac++;
            else if(bc > 0 && x == b) bc++;
            else if(ac == 0) { a = x; ac = 1; }
            else if(bc == 0) { b = x; bc = 1; }
            else { ac--; bc--; }
        }
        int af = 0, bf = 0;
        for(int x: nums) {
            if(ac > 0 && x == a) af++;
            else if(bc > 0 && x == b) bf++;
        }
        List<Integer> result = new ArrayList<>(2);
        if(ac > 0 && af > nums.length / 3) result.add(a);
        if(bc > 0 && bf > nums.length / 3) result.add(b);
        return result;
    }
}
