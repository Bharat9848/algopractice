package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * There are n computers numbered from 0 to n - 1 connected by ethernet cables connections forming a network where connections[i] = [ai, bi] represents a connection between computers ai and bi. Any computer can reach any other computer directly or indirectly through the network.
 *
 * You are given an initial computer network connections. You can extract certain cables between two directly connected computers, and place them between any pair of disconnected computers to make them directly connected.
 *
 * Return the minimum number of times you need to do this in order to make all the computers connected. If it is not possible, return -1.
 * Input: n = 4, connections = [[0,1],[0,2],[1,2]]
 * Output: 1
 * Input: n = 6, connections = [[0,1],[0,2],[0,3],[1,2],[1,3]]
 * Output: 2
 * Input: n = 6, connections = [[0,1],[0,2],[0,3],[1,2]]
 * Output: -1
 * Explanation: There are not enough cables.
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 105
 *     1 <= connections.length <= min(n * (n - 1) / 2, 105)
 *     connections[i].length == 2
 *     0 <= ai, bi < n
 *     ai != bi
 *     There are no repeated connections.
 *     No two computers are connected by more than one cable.
 */
class P1319MakeConnected {
    int makeConnected(int n, int[][] connections){
        if(connections.length < n-1){
            return -1;
        }
        boolean[] visited = new boolean[n];
        Map<Integer, List<Integer>> adjMap = IntStream.range(0, n).boxed().collect(Collectors.toMap(i -> i, i -> new ArrayList<Integer>(), (v1, v2) -> v1));
        for (int i = 0; i < connections.length; i++) {
            int[] edge = connections[i];
            int src = edge[0];
            int dest = edge[1];
            adjMap.get(src).add(dest);
            adjMap.get(dest).add(src);
        }
        int noOfComponent = 0;
        for (int i = 0; i < n; i++) {
            if(!visited[i]){
                discoverComp(-1, i, visited, adjMap);
                noOfComponent++;
            }
        }
        return noOfComponent-1;
    }

    private void discoverComp(int parent, int src, boolean[] visited, Map<Integer, List<Integer>> adjMap) {
        List<Integer> neighbours = adjMap.get(src);
        visited[src] = true;
        for (int dest: neighbours){
            if(dest == parent){
                continue;
            }
            if(!visited[dest]){
               discoverComp(src, dest, visited, adjMap);
            }
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "6|[[0,1],[0,2],[0,3],[1,2],[1,3]]|2",
            "6|[[0,1],[0,2],[0,3],[1,2]]|-1",
            "5|[[0,1],[0,2],[3,4],[2,3]]|0",
            "4|[[0,1],[0,2],[1,2]]|1",
    })
    void test(int n, String connectionStr, int expected){
        int[][] connections = Arrays.stream(connectionStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, makeConnected(n, connections));
    }
}
