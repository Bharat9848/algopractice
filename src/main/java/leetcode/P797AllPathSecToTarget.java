package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a directed acyclic graph (DAG) of n nodes labeled from 0 to n - 1, find all possible paths from node 0 to node n - 1 and return them in any order.
 *
 * The graph is given as follows: graph[i] is a list of all nodes you can visit from node i (i.e., there is a directed edge from node i to node graph[i][j]).
 *
 *
 *
 * Example 1:
 *
 * Input: graph = [[1,2],[3],[3],[]]
 * Output: [[0,1,3],[0,2,3]]
 * Explanation: There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.
 *
 * Example 2:
 *
 * Input: graph = [[4,3,1],[3,2,4],[3],[4],[]]
 * Output: [[0,4],[0,3,4],[0,1,3,4],[0,1,2,3,4],[0,1,4]]
 *
 *
 *
 * Constraints:
 *
 *     n == graph.length
 *     2 <= n <= 15
 *     0 <= graph[i][j] < n
 *     graph[i][j] != i (i.e., there will be no self-loops).
 *     All the elements of graph[i] are unique.
 *     The input graph is guaranteed to be a DAG.
 */
class P797AllPathSecToTarget {
    List<List<Integer>> allPathsSourceTarget(int[][] graph){
        int src = 0, target = graph.length-1;
        List<List<Integer>> result = new ArrayList<>();
        Stack<List<Integer>> paths = new Stack<>();
        ArrayList<Integer> startPath = new ArrayList<>();
        startPath.add(src);
        paths.add(startPath);
        while (!paths.isEmpty()){
            List<Integer> lastPath = paths.pop();
            int[] neighbours = graph[lastPath.get(lastPath.size()-1)];
            for(int neigh : neighbours){
                List<Integer> newPath = new ArrayList<>(lastPath);
                newPath.add(neigh);
                if(neigh == target){
                    result.add(newPath);
                }else{
                    paths.push(newPath);
                }
            }
        }

        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[4,3,1],[3,2,4],[3],[4],[]]|[[0,4],[0,3,4],[0,1,3,4],[0,1,2,3,4],[0,1,4]]|bit complex",
            "[[1,2],[3],[3],[]]|[[0,1,3],[0,2,3]]|simple graph of square",
            "[[1,2],[],[],[]]|[]| no path"
    })
    void test(String graphStr, String expectedStr, String failMsg){
        int[][] graph = Arrays.stream(graphStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .map(s -> s.isEmpty() ? new int[0]:
                    Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        Set<List<Integer>> expected = Arrays.stream(expectedStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter( s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toSet());
        Assertions.assertEquals(expected, new HashSet<>(allPathsSourceTarget(graph)));
    }
}
