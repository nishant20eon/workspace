package org.nishant.dsa.binarytree;
import java.util.*;
public final class InorderTraversal {
    private InorderTraversal() {}
    public static final class Node {
        public final int val;
        public Node left, right;
        public Node(int val) {this.val=val;}
    }
    // Recursive O(n) time; O(h) call stack.
    public static List<Integer> recursive(Node root) {
        List<Integer> out = new ArrayList<>(); dfs(root,out); return out;
    }
    private static void dfs(Node root,List<Integer> out) {
        if(root == null) return;
        dfs(root.left,out); out.add(root.val); dfs(root.right,out);
    }
    // Iterative O(n) time, O(h) explicit stack.
    public static List<Integer> iterative(Node root) {
        List<Integer> result = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        Node cur = root;
        while(cur != null || !stack.isEmpty()) {
            while(cur != null) {stack.push(cur);cur=cur.left;}
            cur = stack.pop();result.add(cur.val);cur = cur.right;
        }
        return result;
    }
}
