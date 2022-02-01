package leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * Given the edges of a directed graph where edges[i] = [ai, bi] indicates there is an edge between nodes ai and bi, and two nodes source and destination of this graph, determine whether or not all paths starting from source eventually, end at destination, that is:
 *
 *     At least one path exists from the source node to the destination node
 *     If a path exists from the source node to a node with no outgoing edges, then that node is equal to destination.
 *     The number of possible paths from source to destination is a finite number.
 *
 * Return true if and only if all roads from source lead to destination.
 * Example 1:
 *
 * Input: n = 3, edges = [[0,1],[0,2]], source = 0, destination = 2
 * Output: false
 * Explanation: It is possible to reach and get stuck on both node 1 and node 2.
 *
 * Example 2:
 *
 * Input: n = 4, edges = [[0,1],[0,3],[1,2],[2,1]], source = 0, destination = 3
 * Output: false
 * Explanation: We have two possibilities: to end at node 3, or to loop over node 1 and node 2 indefinitely.
 *
 * Example 3:
 *
 * Input: n = 4, edges = [[0,1],[0,2],[1,3],[2,3]], source = 0, destination = 3
 * Output: true
 * Constraints:
 *
 *     1 <= n <= 104
 *     0 <= edges.length <= 104
 *     edges.length == 2
 *     0 <= ai, bi <= n - 1
 *     0 <= source <= n - 1
 *     0 <= destination <= n - 1
 *     The given graph may have self-loops and parallel edges.
 */
public class P1059AllPathsFromSrcToDest {
    private enum discover{VISITED, NOT_VISITED, EXPLORED};
    boolean leadsToDestination(int n, int[][] edges, int source, int destination) {
        Map<Integer, List<Integer>> adjMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adjMap.put(i, new ArrayList<>());
        }
        for (int[] edge: edges){
           adjMap.get(edge[0]).add(edge[1]);
        }
        discover[] visited = new discover[n];
        Arrays.fill(visited, discover.NOT_VISITED);
        Stack<Integer> queue = new Stack<>();
        queue.push(source);
        while (!queue.isEmpty()){
            int node = queue.peek();
            visited[node] = discover.VISITED;

            List<Integer> neighbours = adjMap.get(node);
            if(neighbours.isEmpty() && node != destination){
                //vertex with no outbound and its not equal to destination
                return false;
            }
            boolean allExplored = true;
            for (int i = 0; i < neighbours.size(); i++) {
                Integer next = neighbours.get(i);
                if(visited[next].equals(discover.NOT_VISITED)){
                    allExplored = false;
                    queue.push(next);
                    break;
                }else if(visited[next].equals(discover.VISITED)){
                    //cycleDetected
                    return false;
                }
            }
            if(allExplored){
                visited[node] = discover.EXPLORED;
                queue.pop();
            }
        }
        return true;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2|[[0,1]]|0|1|true",
            "4|[[0,1],[0,2],[1,3],[2,3]]|0|3|true",
            "3|[[0,1],[0,2]]|0|2|false",
            "4|[[0,1],[0,3],[1,2],[2,1]]|0|3|false",
    })
    void test(int n, String edgeStr, int source, int destination, boolean expected){
        int[][] edges = Arrays.stream(edgeStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);

        Assertions.assertEquals(expected, leadsToDestination(n, edges, source, destination));
    }
}
