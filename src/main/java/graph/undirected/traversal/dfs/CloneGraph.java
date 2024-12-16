package graph.undirected.traversal.dfs;

/**
 * Given a reference of a node in a graph with data and a list of neighbors, create a deep copy of the graph. The graph has the following properties:
 *
 *     Undirected: The edges of the graph are bidirectional.
 *
 *     Connected: A path will always exist between any two nodes.
 *
 * In a deep copy, a new instance of every node is created with the same data as in the original graph. Any modifications made to the new graph are not reflected in the original graph.
 *
 * For simplicity, we are creating a graph using an adjacency list, where the data of each node is represented by its index in the adjacency list. Each list in the adjacency list describes the set of neighbors of a node in the graph. The index in the adjacency list starts from 1. For example, for [[2, 3], [1, 3], [1, 2]], there are three nodes in the graph:
 *
 * 1st1st node (data = 1): Neighbors are 2nd2nd node (data = 2) and 3rd3rd node (data = 3).
 *
 * 2nd2nd node (data = 2): Neighbors are 1st1st node (data = 1) and 3rd3rd node (data = 3).
 *
 * 3rd3rd node (data = 3): Neighbors are 1st1st node (data = 1) and 2nd2nd node (data = 2).
 *
 * Constraints:
 *
 *     0≤ Number of nodes ≤100
 *     1≤ Node.data ≤100
 *     Node.data is unique for each node.
 *     The graph is undirected, i.e., there are no self-loops or repeated edges.
 *     The graph is connected, i.e., any node can be visited from a given node.
 */
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.regex.Pattern;

public class CloneGraph {
    public Node clone(Node node){
        if(node == null){
            return null;
        }
        Map<Integer, Node> dataToNode = new HashMap<>();
        Node newRoot = new Node(node.data);
        dataToNode.put(node.data, newRoot);
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        Map<Integer, Integer> nodeToNeighbourIndex = new HashMap<>();
        stack.push(node);
        while (!stack.isEmpty()){
            Node current = stack.peek();
            visited.add(current);
            Node currentNew = dataToNode.get(current.data);
            int i = nodeToNeighbourIndex.getOrDefault(current.data, 0);
            for(; i < current.neighbors.size(); i++){
                Node neighbour = current.neighbors.get(i);
                Node neighbourNew;
                if(visited.contains(neighbour)) {
                    neighbourNew = dataToNode.get(neighbour.data);
                } else {
                    neighbourNew =  new Node(neighbour.data);
                    dataToNode.putIfAbsent(neighbour.data, neighbourNew);
                }
                currentNew.neighbors.add(neighbourNew);
//                neighbourNew.neighbors.add(currentNew);
                if(!visited.contains(neighbour)){
                    nodeToNeighbourIndex.put(current.data, i+1);
                    stack.add(neighbour);
                    break;
                }
            }
            if(i == current.neighbors.size()){
                stack.pop();
            }
        }
        return newRoot;
    }


    private class Node {
        int data;
        List<Node> neighbors;

        public Node(int data) {
            this.data = data;
            this.neighbors = new ArrayList<Node>();
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[2,3],[1,3],[1,2]]"
    })
    void test(String adjStr){
        var pattern = Pattern.compile("\\[\\d,\\d\\]");
        var matcher = pattern.matcher(adjStr);
        int[][] adjMatrix = matcher.results().map(matchResult -> matcher.group()).map(str -> {
            var tokens =  str.split(",");
            int first = Integer.parseInt(tokens[0].replace("[", ""));
            int second = Integer.parseInt(tokens[1].replace("]", ""));
            return new int[] {first, second};
        }).toArray(int[][]::new);
        Map<Integer, Node> nodeCache = new HashMap<>();
        for (int i = 0; i < adjMatrix.length; i++) {
            nodeCache.putIfAbsent(i+1, new Node(i+1));
            Node node = nodeCache.get(i+1);
            int[] neighbours = adjMatrix[i];
            for (Integer n : neighbours){
                nodeCache.putIfAbsent(n, new Node(n));
                node.neighbors.add(nodeCache.get(n));
            }
        }
        Node cloned = clone(nodeCache.get(1));
        System.out.println(cloned);
    }
}
