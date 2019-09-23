package leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Given a reference of a node in a connected undirected graph, return a deep copy (clone) of the graph. Each node in the graph contains a val (int) and a list (List[Node]) of its neighbors.



Example:

Input:
{"$id":"1","neighbors":[{"$id":"2","neighbors":[{"$ref":"1"},{"$id":"3","neighbors":[{"$ref":"2"},{"$id":"4","neighbors":[{"$ref":"3"},{"$ref":"1"}],"val":4}],"val":3}],"val":2},{"$ref":"4"}],"val":1}

Explanation:
Node 1's value is 1, and it has two neighbors: Node 2 and 4.
Node 2's value is 2, and it has two neighbors: Node 1 and 3.
Node 3's value is 3, and it has two neighbors: Node 2 and 4.
Node 4's value is 4, and it has two neighbors: Node 1 and 3.



Note:

    The number of nodes will be between 1 and 100.
    The undirected graph is a simple graph, which means no repeated edges and no self-loops in the graph.
    Since the graph is undirected, if node p has node q as neighbor, then node q must have node p as neighbor too.
    You must return the copy of the given node as a reference to the cloned graph.


 */
public class P133CloneGraph {

    private class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {}

        public Node(int _val,List<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }

        @Override
        public String toString(){
            return  genStr(this,new HashMap<>());
        }
        }
        public String genStr(Node node, Map<Node,Boolean> visited) {
            StringBuilder neighborsStr = new StringBuilder("Node ==> val= " + node.val  +", neighbors={");
            visited.put(node, Boolean.TRUE);
            for(Node x : node.neighbors){
                if(!visited.getOrDefault(x, Boolean.FALSE)){
                    neighborsStr.append(" ").append(genStr(x,visited)).append(", ");
                }else{
                    neighborsStr.append("ref = ").append(x.val);
                }
            }
            return neighborsStr.append("}<====").toString();
    }

    public Node cloneGraph(Node root){
        Map<Node,Node> visited = new HashMap<>();
        return copyNode(root, visited);
    }

    private Node copyNode(Node node, Map<Node,Node> visited){
        Node duplicate = new Node(node.val,null);
        List<Node> duplicateNeighs = new ArrayList<>();
        visited.put(node, duplicate);
        for(Node neighbour : node.neighbors){
            if(visited.get(neighbour) == null){
                duplicateNeighs.add(copyNode(neighbour, visited));
            }else{
                duplicateNeighs.add(visited.get(neighbour));
            }
        }
        duplicate.neighbors = duplicateNeighs;
        return duplicate;
    }

    @Test
    public void test1(){
        Node one  = new Node(1, null);
        Node two  = new Node(2, new ArrayList<Node>(){{add(one);}});
        Node three = new Node(3, new ArrayList<Node>(){{add(one); add(two);}});
        one.neighbors = new ArrayList<Node>(){{add(two);add(three);}};
        two.neighbors.add(three);
        System.out.println(cloneGraph(one));
    }
}
