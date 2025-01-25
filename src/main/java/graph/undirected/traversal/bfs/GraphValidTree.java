package graph.undirected.traversal.bfs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Given n as the number of nodes and an array of the edges of a graph, find out if the graph is a valid tree. The nodes of the graph are labeled from 00 to n−1n−1, and edges[i]=[x,y]edges[i]=[x,y] represents an undirected edge connecting the nodes xx and yy of the graph.
 *
 * A graph is a valid tree when all the nodes are connected and there is no cycle between them.
 *
 * Constraints:
 *
 *     1≤ n ≤1000
 *     0≤ edges.length ≤2000
 *     edges[i].length =2
 *     0≤0≤ xx, yy << n
 *     x != y
 */
public class GraphValidTree {
    public static boolean validTree(int n, int[][] edges) {
        if(n==1){
            return edges.length == 0;
        }
        if(edges.length == 0){
            return false;
        }
        int[] visited = new int[n];
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];
            adjList.putIfAbsent(src, new ArrayList<>());
            adjList.putIfAbsent(dest, new ArrayList<>());
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }
        List<Integer> queue = new ArrayList<>();
        queue.add(0);
        visited[0] = 1;
        while (!queue.isEmpty()){
            List<Integer> newQueue = new ArrayList<>();
            while (!queue.isEmpty()){
                int current = queue.removeFirst();
                List<Integer> neighbors = adjList.get(current);
                for (int i = 0; i < neighbors.size(); i++) {
                    int neigh = neighbors.get(i);
                    if(visited[neigh] == 1 ){
                        return false;
                    } else if(visited[neigh] == 0) {
                        visited[neigh] = 1;
                        parent.put(neigh, current);
                        newQueue.add(neigh);
                    }
                }
                visited[current] = 2;
            }
            if(!newQueue.isEmpty()){
                queue = newQueue;
            }
        }
        return Arrays.stream(visited).allMatch(i -> i == 2);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "5|[[0,1],[0,2],[0,3],[3,4]]|true",
            "5|[[0,1],[0,2],[0,3]]|false",
            "5|[[0,1],[0,2],[0,3],[2,3]]|false",
            "5|[[0,1],[0,2],[0,3],[3,4],[4,0]]|false",
    })
    void test(int n, String edgesStr, boolean expected){
        var pattern = Pattern.compile("\\[\\d,\\d\\]");
        var matcher = pattern.matcher(edgesStr);
        int[][] edges = matcher.results().map(matchResult -> matcher.group()).map(str -> {
            var tokens =  str.split(",");
            int first = Integer.parseInt(tokens[0].replace("[", ""));
            int second = Integer.parseInt(tokens[1].replace("]", ""));
            return new int[] {first, second};
        }).toArray(int[][]::new);
        Assertions.assertEquals(expected, validTree(n, edges));
    }

    @Test
    void testOneNodeGraph(){
        Assertions.assertTrue(validTree(1, new int[][]{}));
    }
}
