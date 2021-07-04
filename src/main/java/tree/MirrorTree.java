package tree;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MirrorTree {
    TreeNode mirrorTree(TreeNode root){
        if(root==null){
            return null;
        }
        TreeNode temp = new TreeNode(root.val);
        temp.setLeft(mirrorTree(root.left));
        temp.setRight(mirrorTree(root.right));
        return temp;
    }

    // @TODO
    TreeNode mirrorWithoutRecursion(TreeNode root){
        TreeNode temp = root;
        List<TreeNode> stack = new ArrayList<>();
        return null;
    }

    @Test
    public void treeSame(){
        TreeNode oldTree = TreeExample.treeBalanced9Nodes();
        TreeNode newTree = mirrorTree(oldTree);
        Assert.assertEquals(newTree, oldTree);
    }
}
