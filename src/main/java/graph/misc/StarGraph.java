package graph.misc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Given an array edges where each element edges[i] = [ui, vi] represents an edge between nodes ui and vi in an undirected star graph, find the central node of this star graph.
 *
 *     Note: A star graph is a graph where one central node is connected to every other node. This implies that a star graph with n nodes has exactly n - 1 edges.
 *
 * Constraints:
 *
 *     3≤3≤ n ≤103≤103
 *
 *     edges.length ==== n - 1
 *
 *     edges[i].length ==2==2
 *
 *     1≤1≤ ui, vi ≤≤ n
 *
 *     ui ≠= vi
 *
 *     The given edges represent a valid star graph.
 */
public class StarGraph {

    public static int findCenter(int[][] edges) {
        Map<Integer, Integer> edgesCount = new HashMap<>();
        for(int[] edge: edges){
            edgesCount.put(edge[0], edgesCount.getOrDefault(edge[0], 0) + 1);
            edgesCount.put(edge[1], edgesCount.getOrDefault(edge[1], 0) + 1);
        }
        int spokeCount = 0;
        int starNodeCount = 0;
        int starNode = -1;
        for (var nodeToCountPair: edgesCount.entrySet()) {
            int node = nodeToCountPair.getKey();
            int count = nodeToCountPair.getValue();
            if(count == edgesCount.size() - 1){
                starNodeCount++;
                starNode = node;
            } else if(count == 1){
                spokeCount++;
            }
        }
        return spokeCount == edgesCount.size()-1 && starNodeCount == 1 ? starNode: -1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,2]]|-1",
            "[[1,2][3,2]]|2",
            "[[1,2][3,2],[4,2]]|2",
            "[[1,2][3,2],[4,2],[3,4]]|-1"
    })
    void test(String edgesStr, int expected){
        var pattern = Pattern.compile("\\[\\d,\\d\\]");
        var matcher = pattern.matcher(edgesStr);
        int[][] edges = matcher.results().map(matchResult -> matcher.group()).map(str -> {
            var tokens =  str.split(",");
            int first = Integer.parseInt(tokens[0].replace("[", ""));
            int second = Integer.parseInt(tokens[1].replace("]", ""));
            return new int[] {first, second};
        }).toArray(int[][]::new);
        Assertions.assertEquals(expected, findCenter(edges));
    }
}
