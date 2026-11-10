package org.nishant.dsa;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.nishant.dsa.majority.*;
import org.nishant.dsa.binarysearch.*;
import org.nishant.dsa.slidingwindow.*;
import org.nishant.dsa.arrayshashing.*;
import org.nishant.dsa.twopointers.*;
import org.nishant.dsa.stack.*;
import org.nishant.dsa.intervals.*;
import org.nishant.dsa.linkedlist.*;
import org.nishant.dsa.binarytree.*;

class PatternSolutionsTest {
    @Test void majorityVotingMatchesCounting() {
        int[] a = {2,2,1,1,1,2,2};
        assertEquals(2,MajorityElement.voting(a));
        assertEquals(MajorityElement.hashMap(a),MajorityElement.voting(a));
        assertNull(MajorityElement.verifiedVoting(new int[]{1,2,3}));
        assertEquals(Set.of(1,2),new HashSet<>(MajorityElementII.voting(new int[]{1,1,1,2,2,2,3})));
        assertEquals(new HashSet<>(MajorityElementII.hashMap(new int[]{3,2,3})),
                new HashSet<>(MajorityElementII.voting(new int[]{3,2,3})));
    }
    @Test void binarySearchAndBoundary() {
        int[] nums = {-1,0,3,5,9,12};
        assertEquals(4,BinarySearch.search(nums,9));
        assertEquals(BinarySearch.linear(nums,9),BinarySearch.search(nums,9));
        assertEquals(-1,BinarySearch.search(nums,2));
        assertEquals(1,BinarySearch.lowerBound(new int[]{1,2,2,2,5},2));
    }
    @Test void slidingWindowBothApproaches() {
        int[] nums={1,12,-5,-6,50,3};
        assertEquals(12.75,MaximumAverageSubarrayI.optimal(nums,4),1e-8);
        assertEquals(MaximumAverageSubarrayI.bruteForce(nums,4),MaximumAverageSubarrayI.optimal(nums,4),1e-8);
        int[] a={2,3,1,2,4,3};
        assertEquals(2,MinimumSizeSubarraySum.optimal(7,a));
        assertEquals(MinimumSizeSubarraySum.bruteForce(7,a),MinimumSizeSubarraySum.optimal(7,a));
    }
    @Test void arraysAndTwoPointers() {
        assertArrayEquals(new int[]{0,1},TwoSum.hashMap(new int[]{2,7,11,15},9));
        assertArrayEquals(TwoSum.bruteForce(new int[]{2,7,11,15},9),TwoSum.hashMap(new int[]{2,7,11,15},9));
        assertEquals(2,SubarraySumEqualsK.optimal(new int[]{1,1,1},2));
        assertEquals(3,SubarraySumEqualsK.optimal(new int[]{1,-1,0},0));
        assertEquals(SubarraySumEqualsK.bruteForce(new int[]{1,-1,0},0),SubarraySumEqualsK.optimal(new int[]{1,-1,0},0));
        assertArrayEquals(new int[]{1,2},TwoSumII.optimal(new int[]{2,7,11,15},9));
    }
    @Test void stackAndIntervals() {
        assertTrue(ValidParentheses.isValid("({[]})"));
        assertFalse(ValidParentheses.isValid("(]"));
        int[][] a={{1,3},{2,6},{8,10},{15,18}};
        int[][] expected={{1,6},{8,10},{15,18}};
        assertArrayEquals(expected,MergeIntervals.sweep(a));
        assertArrayEquals(expected,MergeIntervals.bruteForce(a));
        assertArrayEquals(new int[][]{{1,6}},MergeIntervals.bruteForce(new int[][]{{5,6},{1,2},{2,5}}));
    }
    @Test void linkedListAndTree() {
        ReverseLinkedList.Node a=new ReverseLinkedList.Node(1);
        a.next=new ReverseLinkedList.Node(2); a.next.next=new ReverseLinkedList.Node(3);
        ReverseLinkedList.Node out=ReverseLinkedList.iterative(a);
        assertEquals(3,out.val); assertEquals(2,out.next.val); assertEquals(1,out.next.next.val);
        assertEquals(1,ReverseLinkedList.recursive(out).val);
        InorderTraversal.Node root=new InorderTraversal.Node(2);
        root.left=new InorderTraversal.Node(1); root.right=new InorderTraversal.Node(3);
        assertEquals(List.of(1,2,3),InorderTraversal.iterative(root));
        assertEquals(InorderTraversal.recursive(root),InorderTraversal.iterative(root));
    }
}
