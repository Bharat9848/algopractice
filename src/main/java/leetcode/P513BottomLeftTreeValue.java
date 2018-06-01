package leetcode;

import java.util.ArrayList;

/**DONE Given a binary tree, find the leftmost value in the last row of the tree.
 * Created by bharat on 17/4/18.
 */
public class P513BottomLeftTreeValue {

    private class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

        public int findBottomLeftValue(TreeNode root) {
            int left = -1;
            ArrayList<TreeNode> queue = new ArrayList<>();
            ArrayList<TreeNode> next = new ArrayList<>();
            queue.add(root);
            while (!queue.isEmpty() || !next.isEmpty()) {
                if (queue.isEmpty() && !next.isEmpty()) {
                    left = next.get(0).val;
                    while (!next.isEmpty()) {
                        TreeNode pop = next.remove(0);
                        if (pop.left != null) {
                            queue.add(pop.left);
                        }
                        if (pop.right != null) {
                            queue.add(pop.right);
                        }

                    }

                } else {
                    left = queue.get(0).val;

                    while (!queue.isEmpty()) {
                        TreeNode pop = queue.remove(0);
                        if (pop.left != null) {
                            next.add(pop.left);
                        }
                        if (pop.right != null) {
                            next.add(pop.right);
                        }

                    }

                }
            }
            return left;
        }


}
