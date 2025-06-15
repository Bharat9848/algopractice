package graph.directed.shortest;

import core.ds.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.regex.Pattern;

/**
 * A network of n nodes labeled 11 to nn is provided along with a list of travel times for directed edges represented as times[i]=(xi, yi, ti) times[i]=(xi, yi, ti), where xixi​​ is the source node, yiyi​​ is the target node, and titi​​ is the delay time from the source node to the target node.
 *
 * Considering we have a starting node, k, we have to determine the minimum time required for all the remaining n−1n−1 nodes to receive the signal. Return −1−1 if it’s not possible for all n−1n−1 nodes to receive the signal.
 *
 * Constraints:
 *
 *     1≤1≤ k ≤≤ n ≤≤ 100100
 *     1≤ times.length ≤ 6000
 *     times[i].length ==3
 *     1≤x,y≤ n
 *     x != y
 *     0≤t≤100
 *     Unique pairs of (x,y)(x,y), which means that there should be no multiple edges
 */
public class NetworkDelayTime {
    public static int networkDelayTime(int[][] times, int n, int k) {
        List<List<Edge>> adjList = new ArrayList<>(n+1);
        for (int i = 0; i < n+1; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int i = 0; i < times.length; i++) {
            int[] edgeArr = times[i];
            adjList.get(edgeArr[0]).add(new Edge(edgeArr[1], edgeArr[2]));
        }
        Set<Integer> inTree = new HashSet<>();
        int current = k;
        PriorityQueue<int[]> shortestPath = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        shortestPath.offer(new int[]{k, 0});
        int delay =  Integer.MIN_VALUE;
        while(!shortestPath.isEmpty()){
            int[] node = shortestPath.poll();
            current = node[0];
            int time = node[1];
            if(inTree.contains(current)){
                continue;
            }
            inTree.add(current);
            delay = Math.max(delay, time);
            var edges = adjList.get(current);
            for(Edge edge: edges){
                int dest = edge.destination;
                if(!inTree.contains(dest)){
                    shortestPath.offer(new int[]{dest, time + edge.weight});
                }
            }
        }

        return inTree.size() == n ? delay: -1;
    }

    private static class Edge{
        public int destination;
        public int weight;
        public Edge(int destination, int weight){
            this.destination = destination;
            this.weight = weight;
        }
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,3,5],[1,2,5],[1,4,5],[2,4,5],[3,4,5]]|4|1|5",
            "[[1,2,1],[2,3,1],[3,5,2]]|5|1|-1",
            "[[1,2,2]]|2|1|2",
            "[[3,1,1],[2,3,1],[2,4,1]]|4|2|2",
    })
    void test(String timesStr, int n, int k, int expected){
        var pattern = Pattern.compile("(\\[(\\d,)+\\d\\])");
        var matcher = pattern.matcher(timesStr);
        int[][] times = matcher.results()
                .map(matchResult -> matcher.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).mapToInt(ch -> Integer.parseInt(ch)).toArray();
                        }
                )
                .toArray(int[][]::new);
        Assertions.assertEquals(expected, networkDelayTime(times, n, k));
    }
}
