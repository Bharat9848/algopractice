package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * You have a graph of n nodes labeled from 0 to n - 1. You are given an integer n and a list of edges where edges[i] = [ai, bi] indicates that there is an undirected edge between nodes ai and bi in the graph.
 *
 * Return true if the edges of the given graph make up a valid tree, and false otherwise.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
 * Output: true
 *
 * Example 2:
 *
 * Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
 * Output: false
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 2000
 *     0 <= edges.length <= 5000
 *     edges[i].length == 2
 *     0 <= ai, bi < n
 *     ai != bi
 *     There are no self-loops or repeated edges.
 *     Solution is to detect a cycle
 */
class P261GraphValidTreeNotDone {
    private enum Visited{VISITED, EXPLORED, NOTVISITED}

    boolean validTree(int n, int[][] edges) {
        Visited[] visited = new Visited[n];
        Arrays.fill(visited, Visited.NOTVISITED);
        boolean valid = true;
        Map<Integer, List<Integer>> adjMatrix = new HashMap<>(n);
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            int src = edge[0];
            int dest = edge[1];
            adjMatrix.putIfAbsent(src, new LinkedList<>());
            adjMatrix.putIfAbsent(dest, new LinkedList<>());
            adjMatrix.get(src).add(dest);
            adjMatrix.get(dest).add(src);
        }
        boolean rootFound = false;
        for (int i = 0; i < n && valid && !rootFound; i++) {
            if(visited[i] == Visited.NOTVISITED){
                Arrays.fill(visited, Visited.NOTVISITED);
                valid = isValidTreeFrom(i, adjMatrix, visited);
                rootFound |= Arrays.stream(visited).allMatch(nodeVisit -> Visited.EXPLORED == nodeVisit);
            }
        }
        return valid && rootFound;
    }

    private boolean isValidTreeFrom(int src, Map<Integer, List<Integer>> adjMatrix, Visited[] visited) {
        List<Integer> queue = new ArrayList<>();
        queue.add(src);
        visited[src] = Visited.VISITED;
        boolean cycleFound = false;
        while (!queue.isEmpty() && !cycleFound){
            Integer node = queue.remove(0);
            List<Integer> orDefault = adjMatrix.getOrDefault(node, new ArrayList<>());
            for (int i = 0; i < orDefault.size() && !cycleFound; i++) {
                Integer child = orDefault.get(i);
                Visited destVisit = visited[child];
                switch (destVisit) {
                    case VISITED:
                    case EXPLORED:{
                        cycleFound = true;
                        break;
                    }
                    case NOTVISITED: {
                        queue.add(child);
                        visited[child] = Visited.VISITED;
                        break;
                    }

                }
            }
            visited[node] = Visited.EXPLORED;
        }
        return !cycleFound;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "5|[[0,1],[1,2],[2,3],[1,3],[1,4]]|false",
            "5|[[0,1],[0,2],[0,3],[1,4]]|true",
            "4|[[0,1],[2,3]]|false",
            "2|[[1,0]]|true",
            "3|[[1,0],[2,0]]|true"
    })
    public void testIsValidTree(int nodesTotal, String edgsStr, boolean expected){
        int[][] edges = Arrays.stream(edgsStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                        Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        Assert.assertEquals(expected, validTree(nodesTotal, edges));
    }
}
