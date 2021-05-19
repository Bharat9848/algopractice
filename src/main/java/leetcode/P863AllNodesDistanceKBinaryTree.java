package leetcode;

import org.junit.Test;
import util.Pair;

import java.util.*;

import static org.junit.Assert.assertEquals;

/*
We are given a binary tree (with root node root), a target node, and an integer value k.

Return a list of the values of all nodes that have a distance k from the target node.  The answer can be returned in any order.
Example 1:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2

Output: [7,4,1]

Explanation:
The nodes that are a distance 2 from the target node (with value 5)
have values 7, 4, and 1.



Note that the inputs "root" and "target" are actually TreeNodes.
The descriptions of the inputs above are just serializations of these objects.



Note:

    The given tree is non-empty.
    Each node in the tree has unique values 0 <= node.val <= 500.
    The target node is a node in the tree.
    0 <= k <= 1000.

Leetcode solution

Approach 1: Annotate Parent

Intuition

If we know the parent of every node x, we know all nodes that are distance 1 from x. We can then perform a breadth first search from the target node to find the answer.

 */
public class P863AllNodesDistanceKBinaryTree {
    private class Pair<K, V> {
        K first;
        V sec;

        public Pair(K first, V sec) {
            this.first = first;
            this.sec = sec;
        }

        public void setFirst(K first1) {
            this.first = first1;
        }

        public void setSec(V sec1) {
            this.sec = sec1;
        }


        public K getFirst() {
            return first;
        }

        public V getSec() {
            return sec;
        }

        public String toString() {
            return "(" + first + ", " + sec + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair<?, ?> pair = (Pair<?, ?>) o;

            if (!first.equals(pair.first)) return false;
            return sec.equals(pair.sec);
        }

        @Override
        public int hashCode() {
            int result = first.hashCode();
            result = 31 * result + sec.hashCode();
            return result;
        }
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        Set<Integer> result = new HashSet<>();
        if (root == null) {
            Collections.emptyList();
        }
        TreeNode targetSubTree;
        if(root.val == target.val){
            targetSubTree = root;
        }else{
            Pair<TreeNode, Pair<List<Integer>, Integer>> targetToUpStreamNodes = upstreamKDis(root, target, k).get();
            targetSubTree = targetToUpStreamNodes.first;
            List<Integer> upstream = targetToUpStreamNodes.sec.first;
            result.addAll(upstream);
        }
        result.addAll(downStreamKDist(targetSubTree, k));
        return new ArrayList<>(result);
    }

    private Optional<Pair<TreeNode,Integer>> distanceTwoNode(TreeNode root, TreeNode target) {
        if (root == null) {
            return Optional.empty();
        }
        if (root.val == target.val) {
            return Optional.of(new Pair<>(root, 0));
        }
        Optional<Pair<TreeNode, Integer>> left = distanceTwoNode(root.left, target).map(ndToDis -> new Pair<>(ndToDis.first, ndToDis.sec + 1));
        return left.or(() -> distanceTwoNode(root.right, target).map(ndToDis -> new Pair<>(ndToDis.first, ndToDis.sec + 1)));
    }

    private Optional<Pair<TreeNode,Pair<List<Integer>, Integer>>> upstreamKDis(TreeNode root, TreeNode target, int distK) {
        if (root == null) {
            return Optional.empty();
        }
        if (root.val == target.val) {
            return Optional.of(new Pair<>(root, new Pair<>(new ArrayList<>(), 0)));
        }
        Optional<Pair<TreeNode, Pair<List<Integer>, Integer>>> leftSubtreeFind = upstreamKDis(root.left, target, distK);
        Optional<Pair<TreeNode, Pair<List<Integer>, Integer>>> leftFind = leftSubtreeFind.map(leftsub -> {
            int dist = leftsub.sec.sec + 1;
            List<Integer> leftKDistNodes = leftsub.sec.first;
            if(distK == dist){
                leftKDistNodes.add(root.val);
                return new Pair<>(leftsub.first, new Pair<>(leftKDistNodes, dist));
            }else{
                List<Integer> rightSub = new ArrayList<>(downStreamKDist(root.right, distK - dist -1));
                rightSub.addAll(leftKDistNodes);
                return new Pair<>(leftsub.first, new Pair<>(rightSub, dist));
            }
        });
        Optional<Pair<TreeNode, Pair<List<Integer>, Integer>>> rightSubtreeFind = upstreamKDis(root.right, target, distK);
        Optional<Pair<TreeNode, Pair<List<Integer>, Integer>>> rightFind = rightSubtreeFind.map(rightSub -> {
            int dist = rightSub.sec.sec + 1;
            List<Integer> rightKDistNodes = rightSub.sec.first;
            if(distK == dist){
                rightKDistNodes.add(root.val);
                return new Pair<>(rightSub.first, new Pair<>(rightKDistNodes, dist));
            }else{
                List<Integer> leftSub = new ArrayList<>(downStreamKDist(root.left, distK - dist -1));
                leftSub.addAll(rightKDistNodes);
                return new Pair<>(rightSub.first, new Pair<>(leftSub, dist));
            }
        });
        return leftFind.or(() -> rightFind);
    }

    private List<Integer> downStreamKDist(TreeNode root, int k) {
        if (root == null) {
            return new ArrayList<>();
        }
        if (k == 0) {
            return Collections.singletonList(root.val);
        }
        List<Integer> result = new ArrayList<>(downStreamKDist(root.left, k - 1));
        result.addAll(downStreamKDist(root.right, k - 1));
        return result;
    }

    private enum Direction {
        Both, Only_Left, Only_Right
    }

    @Test
    public void test() {
     assertEquals(Arrays.asList(15, 7), downStreamKDist(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7))), 2));
        assertEquals(Arrays.asList(3), downStreamKDist(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7))), 0));
        assertEquals(Arrays.asList(9, 20), downStreamKDist(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7))), 1));
        assertEquals(Integer.valueOf(2), distanceTwoNode(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7))), new TreeNode(7)).get().sec);
        assertEquals(Integer.valueOf(0), distanceTwoNode(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7))), new TreeNode(3)).get().sec);
        assertEquals(new HashSet<>(Arrays.asList(7,4,1)), new HashSet<>(distanceK(new TreeNode(3, new TreeNode(5,new TreeNode(6),new TreeNode(2, new TreeNode(7), new TreeNode(4))), new TreeNode(1, new TreeNode(0), new TreeNode(8))), new TreeNode(5), 2)));


    }

    @Test
    public void distanceKTest(){
        assertEquals(new HashSet<>(Arrays.asList(3, 6)), new HashSet<>(distanceK(new TreeNode(3, new TreeNode(5,new TreeNode(6),new TreeNode(2, new TreeNode(7), new TreeNode(4))), new TreeNode(1, new TreeNode(0), new TreeNode(8))), new TreeNode(2), 2)));
        assertEquals(new HashSet<>(Arrays.asList(5,7,4)), new HashSet<>(distanceK(new TreeNode(3, new TreeNode(5,new TreeNode(6),new TreeNode(2, new TreeNode(7), new TreeNode(4))), new TreeNode(1, new TreeNode(0), new TreeNode(8))), new TreeNode(2), 1)));
        assertEquals(Collections.EMPTY_SET, new HashSet<>(distanceK(new TreeNode(1),  new TreeNode(1), 3)));
//[0,null,1,2,5,null,3,null,null,null,4]
//2
//2
        assertEquals(new HashSet<>(Arrays.asList(0, 5)), new HashSet<>(distanceK(new TreeNode(0, null, new TreeNode(1, new TreeNode(2, null, new TreeNode(3, null, new TreeNode(4))), new TreeNode(5))), new TreeNode(3), 3)));

    }
}
