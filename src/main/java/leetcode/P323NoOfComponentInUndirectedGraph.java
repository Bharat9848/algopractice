package leetcode;

import core.ds.UnionFind;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates that there is an edge between ai and bi in the graph.
 *
 * Return the number of connected components in the graph.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 5, edges = [[0,1],[1,2],[3,4]]
 * Output: 2
 *
 * Example 2:
 *
 * Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
 * Output: 1
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 2000
 *     1 <= edges.length <= 5000
 *     edges[i].length == 2
 *     0 <= ai <= bi < n
 *     ai != bi
 *     There are no repeated edges.
 */
class P323NoOfComponentInUndirectedGraph {
    int countComponents(int n, int[][] edges) {
        UnionFind forest = new UnionFind(n);
        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];
            if(!forest.connected(src, dest)){
                forest.union(src, dest);
            }
        }
        Set<Integer> allParents = IntStream.range(0, n).map(i -> forest.find(i)).boxed().collect(Collectors.toSet());
        return allParents.size();
    }








    //TODO improvement Visit can be a boolean array VISITED and EXPLORED does not have any impact.
    private enum Visit{NOTVISIT, VISITED, EXPLORED}
    int countComponentsDFS(int n, int[][] edges) {
        int count=0;
        Map<Integer, List<Integer>> edgesMap = new HashMap<>();
        //TODO bug -> for undirected edge a->b not adding second b->a in adjMap. I thought I dont need it.
        for (int[] edge: edges){
            int src = edge[0];
            int dest = edge[1];
            edgesMap.putIfAbsent(src,new ArrayList<>());
            edgesMap.putIfAbsent(dest,new ArrayList<>());
            edgesMap.get(src).add(dest);
            edgesMap.get(dest).add(src);
        }
        Visit[] visited = new Visit[n];
        Arrays.fill(visited, Visit.NOTVISIT);
        for (int i = 0; i < n; i++) {
            if(Visit.NOTVISIT.equals(visited[i])){
                discoverComponent(i, edgesMap, visited);
                count++;
            }
        }
        return count;
    }

    private void discoverComponent(int i, Map<Integer, List<Integer>> edgesMap, Visit[] visited) {
        visited[i] = Visit.VISITED;
        for (Integer dest: edgesMap.getOrDefault(i, new ArrayList<>())){
            if(Visit.NOTVISIT.equals(visited[dest])){
                discoverComponent(dest, edgesMap, visited);
            }
        }
        visited[i] = Visit.EXPLORED;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"5|[[0,1],[1,2],[2,3],[3,4]]|1",
    "5|[[0,1],[1,2],[3,4]]|2",
    "2|[[1,0]]|1"})
    void test(int n, String edgesStr, int expected){
        int[][] edges = Arrays.stream(edgesStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] edge = s.split(",");
                    return new int[]{Integer.parseInt(edge[0]), Integer.parseInt(edge[1])};
                }).toArray(int[][]::new);;
        Assert.assertEquals(expected, countComponents(n, edges));
    }
}
