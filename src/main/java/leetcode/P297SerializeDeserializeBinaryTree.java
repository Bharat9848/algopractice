package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/*
Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

Clarification: The input/output format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.



Example 1:

Input: root = [1,2,3,null,null,4,5]
Output: [1,2,3,null,null,4,5]

Example 2:

Input: root = []
Output: []

Example 3:

Input: root = [1]
Output: [1]

Example 4:

Input: root = [1,2]
Output: [1,2]



Constraints:

    The number of nodes in the tree is in the range [0, 104].
    -1000 <= Node.val <= 1000
 */
public class P297SerializeDeserializeBinaryTree {
    public String serialize(TreeNode root) {
        StringBuilder s = new StringBuilder();
        if(root==null) return "";
        List<Map.Entry<String, TreeNode>> map = new ArrayList<>();
        map.add(new AbstractMap.SimpleEntry<>("0", root));
        while (!map.isEmpty()) {
            Map.Entry<String, TreeNode> node = map.remove(0);
            String parentKey = node.getKey();
            TreeNode node1 = node.getValue();
            s.append(parentKey).append("=").append(node1.val).append("|");
            if(node1.left!=null) map.add(new AbstractMap.SimpleEntry<>(parentKey+"0", node1.left));
            if(node1.right!=null) map.add(new AbstractMap.SimpleEntry<>(parentKey+"1", node1.right));
        }
        String result = s.toString();
        System.out.println("serialize ===> " + result);
        return result.substring(0, result.length()-1);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data.trim().isEmpty()) return null;
        TreeNode root = null, lastNode = null;
        String lastKey = "";
        for (String node:data.split("\\|")) {
            String[] keyNodeValPair = node.split("=");
            String key = keyNodeValPair[0];
            TreeNode newNode = new TreeNode(Integer.parseInt(keyNodeValPair[1]));
            if(key.equals("0")){
                root = newNode;
                lastNode = root;
                lastKey = key;
            }else {
                String parentKey = key.substring(0, key.length() - 1);
                if(lastKey.equals(parentKey)){
                    if(key.charAt(key.length()-1) == '0') {
                        lastNode.left = newNode;
                    } else {
                        lastNode.right = newNode;
                    }
                }else {
                    TreeNode parentNode = findNode(root, parentKey);
                    if(key.charAt(key.length()-1) == '0') {
                        parentNode.left = newNode;
                    } else {
                        parentNode.right = newNode;
                    }
                }
            }
            lastKey = key;
            lastNode = newNode;
        }
        return root;
    }

    private TreeNode findNode(TreeNode root, String substring) {
        TreeNode temp = root;
        for (int i = 1; i < substring.length(); i++) {
            char c = substring.charAt(i);
            if(c == '0') {
                temp = temp.left;
            }else {
                temp = temp.right;
            }
        }
        return temp;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1",
            "1,2",
            "1,2,3,null,null,4,5"
    })
    void test(String treeStr){
        Assert.assertEquals(TreeNode.createTreeFromStringArr(treeStr), deserialize(serialize(TreeNode.createTreeFromStringArr(treeStr))));
    }
}
