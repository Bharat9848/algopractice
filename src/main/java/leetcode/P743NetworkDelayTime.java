package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ou are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as directed edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it takes for a signal to travel from source to target.
 *
 * We will send a signal from a given node k. Return the time it takes for all the n nodes to receive the signal. If it is impossible for all the n nodes to receive the signal, return -1.
 * Input: times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
 * Output: 2
 * Input: times = [[1,2,1]], n = 2, k = 1
 * Output: 1
 * Input: times = [[1,2,1]], n = 2, k = 2
 * Output: -1
 * Constraints:
 *     1 <= k <= n <= 100
 *     1 <= times.length <= 6000
 *     times[i].length == 3
 *     1 <= ui, vi <= n
 *     ui != vi
 *     0 <= wi <= 100
 *     All the pairs (ui, vi) are unique. (i.e., no multiple edges.)
 *
 *     from Kth node find the distance to all nodes and take a maximum it. If any node is not reachable then return -1;
 */
public class P743NetworkDelayTime {
    int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, List<int[]>> edges = Arrays.stream(times).collect(Collectors.toMap(row -> row[0],
                row -> {
                    ArrayList<int[]> edge = new ArrayList<>();
                    int[] destWithDelay = {row[1], row[2]};
                    edge.add(destWithDelay);
                    return edge;
                }, (edge1, edge2) -> {
                    edge1.addAll(edge2);
                    return edge1;
                }));
        int[] distance = new int[n+1];
        Arrays.fill(distance, Integer.MAX_VALUE);
        int[] parent = new int[n+1];
        parent[k] = k;
        distance[k] = 0;
        Set<Integer> inTree = new HashSet<>();
        int currentNode = k;

        while (!inTree.contains(currentNode)){
            if(distance[currentNode] == Integer.MAX_VALUE){
                //Non reachable node;
                return -1;
            }
            inTree.add(currentNode);
            List<int[]> edgeFromCurr = edges.getOrDefault(currentNode, new ArrayList<>());
            for (int[] edge: edgeFromCurr){
                int dest = edge[0];
                int delay = edge[1];
                if(distance[dest] > distance[currentNode] + delay){
                    distance[dest] = distance[currentNode] + delay;
                    parent[dest] = currentNode;
                }
            }
            int minDist = Integer.MAX_VALUE;
            for (int i = 1; i < distance.length; i++) {
                if(distance[i] <= minDist && !inTree.contains(i)){
                    minDist = distance[i];
                    currentNode = i;
                }
            }
        }
        return IntStream.range(1, distance.length).map(i-> distance[i]).max().orElse(-1);
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "|2|1|-1",
            "[[1,2,1]]|2|1|1",
            "[[1,2,1],[2,1,3]]|2|1|1",
            "[[1,2,1]]|2|2|-1",
            "[[2,1,1],[2,3,1],[3,4,1]]|4|2|2",
    })
    void test(String timesStr, int n, int k, int expected){
        int [][] times = timesStr==null?new int[0][] : Arrays.stream(timesStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                        Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        Assertions.assertEquals(expected, networkDelayTime(times, n ,k));
    }
}
