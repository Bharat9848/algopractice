package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * There is a bi-directional graph with n vertices, where each vertex is labeled from 0 to n - 1 (inclusive). The edges in the graph are represented as a 2D integer array edges, where each edges[i] = [ui, vi] denotes a bi-directional edge between vertex ui and vertex vi. Every vertex pair is connected by at most one edge, and no vertex has an edge to itself.
 *
 * You want to determine if there is a valid path that exists from vertex source to vertex destination.
 *
 * Given edges and the integers n, source, and destination, return true if there is a valid path from source to destination, or false otherwise.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 3, edges = [[0,1],[1,2],[2,0]], source = 0, destination = 2
 * Output: true
 * Explanation: There are two paths from vertex 0 to vertex 2:
 * - 0 → 1 → 2
 * - 0 → 2
 *
 * Example 2:
 *
 * Input: n = 6, edges = [[0,1],[0,2],[3,5],[5,4],[4,3]], source = 0, destination = 5
 * Output: false
 * Explanation: There is no path from vertex 0 to vertex 5.
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 2 * 105
 *     0 <= edges.length <= 2 * 105
 *     edges[i].length == 2
 *     0 <= ui, vi <= n - 1
 *     ui != vi
 *     0 <= source, destination <= n - 1
 *     There are no duplicate edges.
 *     There are no self edges.
 */
class P1971CheckIfPathExist {
    boolean validPath(int n, int[][] edges, int source, int destination) {
        if(source == destination) return true;
        Map<Integer, List<Integer>> adjMatrix = new HashMap<>();
        for (int[] edge : edges) {
            int src = edge[0];
            int dest = edge[1];
            adjMatrix.putIfAbsent(src, new ArrayList<>());
            adjMatrix.putIfAbsent(dest, new ArrayList<>());
            adjMatrix.get(src).add(dest);
            adjMatrix.get(dest).add(src);
        }
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        stack.add(source);
        while (!stack.isEmpty()) {
            int src = stack.peek();
            List<Integer> neighbours = adjMatrix.get(src);
            visited[src] = true;
            boolean allVisited = true;
            if (neighbours != null) {
                for (int neigh : neighbours) {
                    if (neigh == destination) {
                        return true;
                    }
                    if (!visited[neigh]) {
                        allVisited = false;
                        stack.push(neigh);
                        break;
                    }
                }
            }
            if (allVisited) {
                stack.pop();
            }
        }
        return false;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3|[[0,1],[1,2],[2,0]]|0|2|true|simple triangle graph",
            "3||0|2|false|no edges",
            "3||0|0|true|src and destination same",
            "6|[[0,1],[0,2],[3,5],[5,4],[4,3]]|0|5|false|Two disconnected region"
    })
    void test(int n, String edgeStr, int src, int dest, boolean expected, String failureMsg){
        int[][] edges = edgeStr == null ? new int[0][]:Arrays.stream(edgeStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, validPath(n, edges, src, dest), failureMsg);
    }
}

