package tree;

import core.ds.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bharat on 9/5/18.
 */
public class TreePractice {
// Level order traversal
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
// print binary tree vertically

    static void printBinaryTreeVertical(TreeNode treeNode){
        Map<Integer, List<Integer>> levelToValue = new HashMap<>();
        genVerticalMap(treeNode, 0, levelToValue);
        levelToValue.entrySet()
                .stream()
                .sorted((x, y) -> x.getKey() < y.getKey()?-1 : 1)
                .forEach( x -> System.out.println(x.getValue()));
    }

    private static void genVerticalMap(TreeNode treeNode, Integer level , Map<Integer, List<Integer>> levelToValue) {
        if(treeNode == null){
            return ;
        }else {
            levelToValue.putIfAbsent( level, new ArrayList<>());
            levelToValue.computeIfPresent(level, (key, oldList) -> {oldList.add(treeNode.val); return oldList;} );
        }
        genVerticalMap(treeNode.getLeft(), level-1, levelToValue);
        genVerticalMap(treeNode.getRight(), level+1, levelToValue);
    }

    public static void mainTestVerticalPrint(String[] args) {
        TreeNode root = TreeExample.treeBalanced9Nodes();
        printBinaryTreeVertical(root);
    }

}
