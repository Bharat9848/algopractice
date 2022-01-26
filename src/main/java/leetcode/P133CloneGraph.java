package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

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

        public Node() {
        }

        public Node(int _val, List<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }

        @Override
        public String toString() {
            return genStr(this, new HashMap<>());
        }

        private String genStr(Node node, Map<Node, Boolean> visited) {
            StringBuilder neighborsStr = new StringBuilder("Node ==> val= " + node.val + ", neighbors={");
            visited.put(node, Boolean.TRUE);
            for (Node x : node.neighbors) {
                if (!visited.getOrDefault(x, Boolean.FALSE)) {
                    neighborsStr.append(" ").append(genStr(x, visited)).append(", ");
                } else {
                    neighborsStr.append("ref = ").append(x.val);
                }
            }
            return neighborsStr.append("}<====").toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return val == node.val && Objects.equals(neighbors.stream().map(n -> n.val).collect(Collectors.toList()), node.neighbors.stream().map(n -> n.val).collect(Collectors.toList()));
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }
    public Node cloneGraph(Node root){
        Map<Integer, Boolean> visited = new HashMap<>();
        Map<Integer, Node> registry = new HashMap<>();
        return cloneGraph(root, visited, registry);
    }

    private Node cloneGraph(Node root, Map<Integer, Boolean> visited, Map<Integer, Node> registry) {
        Node duplicate = new Node(root.val, new ArrayList<>());
        visited.put(root.val, true);
        registry.put(duplicate.val, duplicate);
        for (Node neigh: root.neighbors){
            if(visited.containsKey(neigh.val)){
                duplicate.neighbors.add(registry.get(neigh.val));
            }else {
                duplicate.neighbors.add(cloneGraph(neigh, visited, registry));
            }
        }
        return duplicate;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[2,4],[1,3],[2,4],[1,3]]|[[2,4],[1,3],[2,4],[1,3]]"
    })
    void test(String graphStr, String expectedStr){
        List<List<Integer>> adjMatrix = Arrays.stream(graphStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
        Map<Integer, Node> registry = new HashMap<>();

        for (int i = 0; i < adjMatrix.size(); i++){
            int vertex = i+1;
            Node node = new Node(vertex, new ArrayList<>());
            registry.put(vertex, node);
        }
        for (int i = 0; i < adjMatrix.size(); i++){
            List<Integer> edges = adjMatrix.get(i);
            Node src = registry.get(i+1);
            for(int edge: edges){
                src.neighbors.add(registry.get(edge));
            }
        }
        Node root = registry.get(1);
        Node clone = cloneGraph(root);
        /*Map<Integer, Node> cloneMap = new HashMap<>();
        cloneRegistry(clone, cloneMap);*/
        Assertions.assertEquals(root, clone);
    }

    private void cloneRegistry(Node clone, Map<Integer, Node> cloneMap) {
        cloneMap.putIfAbsent(clone.val, clone);
        for (Node neigh: clone.neighbors){
            if(cloneMap.get(neigh.val) == null){
                cloneRegistry(neigh, cloneMap);
            }
        }
    }
}
