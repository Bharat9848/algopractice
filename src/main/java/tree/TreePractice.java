package tree;

import core.ds.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bharat on 9/5/18.
 */
public class TreePractice {

    public static String levelOrderTraversal(TreeNode treeNode) {
        StringBuilder sb = new StringBuilder();
        List<TreeNode> queue = new ArrayList<>();
        queue.add(treeNode);
        while (!queue.isEmpty()) {
            TreeNode tn = queue.remove(0);
            if (tn.getVal() == null) {
                sb.append("null, ");
            } else {
                sb.append(tn.getVal()).append(", ");
            }

            boolean leaf = tn.getLeft()==null && tn.getRight()==null;
            if (tn.getLeft() != null) {
                queue.add(tn.getLeft());
            } else if(!leaf){
                queue.add(TreeNode.EMPTY);
            }
            if (tn.getRight() != null) {
                queue.add(tn.getRight());
            } else if(!leaf){
                queue.add(TreeNode.EMPTY);
            }

        }
        return sb.toString();
    }

}
