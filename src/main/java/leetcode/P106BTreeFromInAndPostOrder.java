package leetcode;

/**
 * Given inorder and postorder traversal of a tree, construct the binary tree.

 Note:
 You may assume that duplicates do not exist in the tree.
 */
public class P106BTreeFromInAndPostOrder {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
 }



    public TreeNode buildTree(int[] inorder, int[] postorder){
        if (inorder.length != postorder.length || inorder.length==0 || postorder.length==0){return null;}
        return buildTree(inorder,postorder,0,inorder.length-1, 0, postorder.length -1);
    }

    private TreeNode buildTree(int[] inorder, int[] postorder, int inOrderStart,
                              int inOrderEnd, int postOrderStart, int postOrderEnd) {
        if(postOrderStart == postOrderEnd){return  new TreeNode(postorder[postOrderEnd]);}
        int rootVal  = postorder[postOrderEnd];
        TreeNode rt = new TreeNode(rootVal);
        int rootInorderIndex = inOrderStart;
        //inorder

        for (; rootInorderIndex <= inOrderEnd; rootInorderIndex++) {
            if (rootVal == inorder[rootInorderIndex]){
                break;
            }
        }

        if(rootInorderIndex == inOrderStart){
            int rightSubTreeInorderStart = rootInorderIndex + 1;
            int rightSubTreeInorderEnd =  inOrderEnd;
            int rightSubTreePostOrderStart = postOrderStart;
            int rightSubTreePostOrderEnd =  postOrderEnd -1;
            rt.right = buildTree(inorder,postorder,rightSubTreeInorderStart,rightSubTreeInorderEnd,rightSubTreePostOrderStart,rightSubTreePostOrderEnd);
        }else if(rootInorderIndex == inOrderEnd){
            int leftSubtreeInorderStart = inOrderStart;
            int leftSubtreeInorderEnd = rootInorderIndex - 1;
            int leftSubTreePostorderStart = postOrderStart;
            int leftSubTreePostorderEnd = postOrderEnd -1;
            rt.left = buildTree(inorder,postorder,leftSubtreeInorderStart,leftSubtreeInorderEnd,leftSubTreePostorderStart,leftSubTreePostorderEnd);
        }else{
            int rightSubInorderStart = rootInorderIndex + 1;
            int rightSubInorderEnd =  inOrderEnd;
            if(rightSubInorderStart == rightSubInorderEnd){
                rt.right =  new TreeNode(inorder[rightSubInorderEnd]);
                int leftSubtreeInorderStart = inOrderStart;
                int leftSubtreeInorderEnd = rootInorderIndex - 1;
                int leftSubTreePostorderStart = postOrderStart;
                int rightSubPostorderStart = postOrderStart;
                for(; rightSubPostorderStart<=postOrderEnd;rightSubPostorderStart++){
                    if(postorder[rightSubPostorderStart] == inorder[rightSubInorderEnd]){
                        break;
                    }
                }
                int leftSubTreePostorderEnd = rightSubPostorderStart -1;
                rt.left = buildTree(inorder,postorder,leftSubtreeInorderStart,leftSubtreeInorderEnd,leftSubTreePostorderStart,leftSubTreePostorderEnd);
            }else {
                int rightSubPostorderEnd = postOrderEnd - 1;
                int rightSubPostorderStart = rightSubPostorderEnd;
                while(rightSubInorderEnd -rightSubInorderStart != rightSubPostorderEnd - rightSubPostorderStart){
                    rightSubPostorderStart--;
                }
                rt.right = buildTree(inorder, postorder, rightSubInorderStart, rightSubInorderEnd, rightSubPostorderStart, rightSubPostorderEnd);
                int leftSubtreeInorderStart = inOrderStart;
                int leftSubtreeInorderEnd = rootInorderIndex - 1;
                int leftSubTreePostorderStart = postOrderStart;
                int leftSubTreePostorderEnd = rightSubPostorderStart -1;
                rt.left = buildTree(inorder,postorder,leftSubtreeInorderStart,leftSubtreeInorderEnd,leftSubTreePostorderStart,leftSubTreePostorderEnd);
            }
        }

        return rt;
    }


    public static void main(String[] args){
        P106BTreeFromInAndPostOrder x = new P106BTreeFromInAndPostOrder();
       /* printTree(x.buildTree(new int[] {4,2,5,1,3},new int[]{4,5,2,3,1}));
        System.out.println();
        printTree(x.buildTree(new int[] {1,2},new int[]{2,1}));
        System.out.println();
        printTree(x.buildTree(new int[] {2,1},new int[]{2,1}));
        System.out.println();
        printTree(x.buildTree(new int[] {1,2,3}, new int[]{3,2,1}));
        System.out.println();*/
        printTree(x.buildTree(new int[] {1,2,3,4}, new int[]{1,4,3,2}));
        System.out.println();
        printTree(x.buildTree(new int[] {1,2,3,4,5}, new int[]{1,5,4,3,2}));
    }

    private static void printTree(TreeNode treeNode) {
        if(treeNode == null)
        {
            return;
        }
        printTree(treeNode.left);
        System.out.print("<====" + treeNode.val + "====>" );
        printTree(treeNode.right);
    }
}
