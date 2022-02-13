package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.
 *
 * You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
 * Output: 200
 * Explanation: The graph is shown.
 * The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.
 *
 * Example 2:
 *
 * Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 0
 * Output: 500
 * Explanation: The graph is shown.
 * The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 100
 *     0 <= flights.length <= (n * (n - 1) / 2)
 *     flights[i].length == 3
 *     0 <= fromi, toi < n
 *     fromi != toi
 *     1 <= pricei <= 104
 *     There will not be any multiple flights between two cities.
 *     0 <= src, dst, k < n
 *     src != dst
 */
public class P787CheapestFlightAtMostKStops {
    int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] distance = new int[n];
        int[] prevDistance = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(prevDistance, Integer.MAX_VALUE);
        distance[src] = 0;
        prevDistance[src] = 0;
        for (int i = 0; i < k+1; i++) {
            for (int[] flight: flights){
                int u = flight[0];
                int v = flight[1];
                int w = flight[2];
                if(prevDistance[u] != Integer.MAX_VALUE && distance[v] > prevDistance[u] + w){
                    distance[v] = prevDistance[u] + w;
                }
            }
            prevDistance = distance;
            distance = Arrays.copyOfRange(distance, 0, n);
        }
        return distance[dst] == Integer.MAX_VALUE ? -1: distance[dst];
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4|[[0,1,1],[0,2,5],[1,2,1],[2,3,1]]|0|3|1|6",
            "3|[[0,1,100],[1,2,100],[0,2,500]]|0|2|1|200",
            "3|[[0,1,100],[1,2,100],[0,2,500]]|0|2|0|500",
            "4|[[0,1,100],[1,2,100],[0,2,500]]|0|3|0|-1",
    })
    void test(int n, String flightStr, int src, int dest, int k, int expected){
        int[][] flight = Arrays.stream(flightStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, findCheapestPrice(n, flight, src, dest, k));
    }
}
