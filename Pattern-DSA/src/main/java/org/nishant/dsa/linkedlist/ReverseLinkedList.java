package org.nishant.dsa.linkedlist;
public final class ReverseLinkedList {
    private ReverseLinkedList() {}
    public static final class Node {
        public final int val;
        public Node next;
        public Node(int val) {this.val = val;}
    }
    // Iterative reversal O(n) time, O(1) auxiliary space.
    public static Node iterative(Node head) {
        Node prev = null, cur = head;
        while(cur != null) {
            Node next = cur.next; // save chain before modifying
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
    // Recursive reversal O(n) time and O(n) call-stack space.
    public static Node recursive(Node head) {
        if(head == null || head.next == null) return head;
        Node result = recursive(head.next);
        head.next.next = head;
        head.next = null;
        return result;
    }
}
