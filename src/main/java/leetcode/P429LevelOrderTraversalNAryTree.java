package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given an n-ary tree, return the level order traversal of its nodes' values.
 * <p>
 * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
 * Input: root = [1,null,3,2,4,null,5,6]
 * Output: [[1],[3,2,4],[5,6]]

 * Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
 * Output: [[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]
 * Constraints:
 * <p>
 * The height of the n-ary tree is less than or equal to 1000
 * The total number of nodes is between [0, 104]
 */
public class P429LevelOrderTraversalNAryTree {

    private class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public List<List<Integer>> levelOrder(Node root) {
        if(root == null){return new ArrayList<>();}
        List<List<Integer>> result = new LinkedList<>();
        Queue<Node> level = new LinkedList<>();

        level.add(root);
        while(!level.isEmpty()){
            Queue<Node> nextLevel = new LinkedList<>();
            List<Integer> levelList = new LinkedList<>();
            while (!level.isEmpty()){
                Node curr = level.remove();
                levelList.add(curr.val);
                if(curr.children != null){
                    nextLevel.addAll(curr.children);
                }
            }
            level = nextLevel;
            result.add(levelList);
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|[[1]]",
            "1,null,3,2,4,null,5,6|[[1],[3,2,4],[5,6]]",
            "1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14|[[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]"

    })
    void test(String treeStr, String levelStr) {
        Node root = createNode(treeStr);
        List<List<Integer>> levels = Arrays.stream(levelStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
        Assertions.assertEquals(levels, levelOrder(root));
    }

    private Node createNode(String treeStr) {
        Map<Integer, Node> valToNode = new HashMap<>();
        String[] nodeVals = treeStr.split(",");
        Node root = new Node(Integer.parseInt(nodeVals[0]));
        int currentNodeIndex = 0;
        valToNode.put(root.val, root);
        for (int i = 2; i < nodeVals.length; i++) {
            Node parent = valToNode.get(Integer.parseInt(nodeVals[currentNodeIndex]));
            if(nodeVals[i].equals("null")){
                while(nodeVals[currentNodeIndex+1].equals("null")){
                    currentNodeIndex++;
                }
                currentNodeIndex++;
            }else{
                int val = Integer.parseInt(nodeVals[i]);
                if(parent.children == null){
                    parent.children = new ArrayList<Node>();
                }
                Node child = new Node(val);
                parent.children.add(child);
                valToNode.put(child.val, child);
            }
        }
        return root;
    }

}
