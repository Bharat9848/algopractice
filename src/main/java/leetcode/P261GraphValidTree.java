package leetcode;

import core.ds.UnionFind;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.IntStream;

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
class P261GraphValidTree {

    boolean validTree(int n, int[][] edges) {
        UnionFind forest = new UnionFind(n);
        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];
            if(!forest.connected(src, dest)){
                forest.union(src, dest);
            }else {
                return false;
            }
        }
        return IntStream.range(0,n).allMatch(i -> forest.find(i) == forest.find(0));
    }






    private enum Visited{VISITED, EXPLORED, NOTVISITED}
    boolean validTreeDFS(int n, int[][] edges) {
        Visited[] visited = new Visited[n];
        Arrays.fill(visited, Visited.NOTVISITED);
        if(edges.length==0) { return n == 1;}
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

        return isValidTreeFrom(edges[0][0], edges[0][0], adjMatrix, visited) && Arrays.stream(visited).allMatch(visit -> visit == Visited.EXPLORED);
    }

    private boolean isValidTreeFrom(int parent, int src, Map<Integer, List<Integer>> adjMatrix, Visited[] visited) {
        visited[src] = Visited.VISITED;
        boolean isTree = true;
        List<Integer> edges = adjMatrix.get(src);
        for (int i = 0; i < edges.size(); i++) {
            int dest =  edges.get(i);
            switch (visited[dest]){
                case VISITED: if(dest != parent){
                    return false;
                }
                break;
                case EXPLORED:
                    return false;
                case NOTVISITED:
                    isTree = isTree && isValidTreeFrom(src, dest, adjMatrix, visited);
            }
        }
        visited[src] = Visited.EXPLORED;
        return isTree;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "5|[[0,1],[1,2],[2,3],[1,3],[1,4]]|false",
            "5|[[0,1],[0,2],[0,3],[1,4]]|true",
            "4|[[0,1],[2,3]]|false",
            "2|[[1,0]]|true",
            "3|[[1,0],[2,0]]|true",
            "4|[[2,3],[1,2],[1,3]]|false",
            "1|[[]]|true",
            "2|[[]]|false"
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
