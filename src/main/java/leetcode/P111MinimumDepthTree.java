package leetcode;

import core.ds.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, find its minimum depth.
 *
 * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
 *
 * Note: A leaf is a node with no children.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 2
 *
 * Example 2:
 *
 * Input: root = [2,null,3,null,4,null,5,null,6]
 * Output: 5
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [0, 105].
 *     -1000 <= Node.val <= 1000
 */
public class P111MinimumDepthTree {
    public int minDepth(TreeNode root) {
        if(root != null ){
            int height;
            if(root.left == null && root.right!=null){
                height = minDepth(root.right);
            }else if(root.right == null && root.left!=null){
                height = minDepth(root.left);
            }else if(root.left != null && root.right!=null){
                height = Math.min(minDepth(root.left), minDepth(root.right));
            }else {
                height = 0;
            }
            return height + 1;
        }
        return 0;
    }

    public int minDepthBFS(TreeNode root){
        if (root==null) return 0;
        List<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int height = 1;
        while (!queue.isEmpty()){
            List<TreeNode> childNodes = new LinkedList<>();
            while (!queue.isEmpty()){
                TreeNode node = queue.remove(0);
                if(node.left == null && node.right == null){
                    return height;
                }else {
                    if(node.right != null){
                        childNodes.add(node.right);
                    }
                    if(node.left != null){
                        childNodes.add(node.left);
                    }
                }
            }
            height++;
            queue = childNodes;
        }
        return height;
    }
}
