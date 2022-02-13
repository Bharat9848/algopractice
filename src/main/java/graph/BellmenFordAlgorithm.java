package graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

public class BellmenFordAlgorithm {

    long[] shortestPath(int nodes, int[][] weighEdges){
        long[] distance = new long[nodes];
        long[] prevDistances = new long[nodes];
        Arrays.fill(prevDistances, Integer.MAX_VALUE);
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[0] = 0;
        prevDistances[0] = 0;

        for (int i = 0; i < nodes-1; i++) {
            for (int[] edge: weighEdges){
                int src = edge[0];
                int dest = edge[1];
                int weight = edge[2];
                if(distance[dest] > prevDistances[src] + weight){
                    distance[dest] = prevDistances[src] + weight;
                }
            }
            prevDistances = distance;
            distance= Arrays.copyOfRange(distance, 0, distance.length);
        }
        return distance;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "3|[[0,1,10],[0,2,15],[1,2,1]]|0,10,11"
    })
    void test(int n, String edgesStr, String expectedStr){
        long[] expected = Arrays.stream(expectedStr.split(",")).mapToLong(Long::parseLong).toArray();
        int[][] edges = Arrays.stream(edgesStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertArrayEquals(expected, shortestPath(n, edges));
    }
}
