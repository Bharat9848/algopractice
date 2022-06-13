package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever.
 *
 *     For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
 *
 * You will start at the bus stop source (You are not on any bus initially), and you want to go to the bus stop target. You can travel between bus stops by buses only.
 *
 * Return the least number of buses you must take to travel from source to target. Return -1 if it is not possible.
 *
 * Input: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
 * Output: 2
 * Explanation: The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
 *
 * Input: routes = [[7,12],[4,5,15],[6],[15,19],[9,12,13]], source = 15, target = 12
 * Output: -1
 *
 *     1 <= routes.length <= 500.
 *     1 <= routes[i].length <= 105
 *     All the values of routes[i] are unique.
 *     sum(routes[i].length) <= 105
 *     0 <= routes[i][j] < 106
 *     0 <= source, target < 106
 */
public class P815BusRoutesTLE {
    int numBusesToDestination(int[][] routes, int source, int target) {
        Map<Integer, Integer> shortestPathFromSrc = new HashMap<>();
        shortestPathFromSrc.put(source, 0);
        Map<Integer, List<int[]>> stationToBusRoutes = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].length; j++) {
                int station = routes[i][j];
                stationToBusRoutes.put(station, stationToBusRoutes.getOrDefault(station, new ArrayList<>()));
                stationToBusRoutes.get(station).add(routes[i]);
            }
        }
        Set<Integer> inTree = new HashSet<>();
        Integer current = source;
        while (current != null){
            inTree.add(current);
            int noOfBuses = shortestPathFromSrc.get(current);
            List<int[]> routesFromCurrent = stationToBusRoutes.get(current);
            for (int i = 0; i < routesFromCurrent.size(); i++) {
                int[] busRoute = routesFromCurrent.get(i);
                for (int j = 0; j < busRoute.length; j++) {
                    if(busRoute[j] != current){
                         shortestPathFromSrc.put(busRoute[j], Math.min(shortestPathFromSrc.getOrDefault(busRoute[j], Integer.MAX_VALUE), noOfBuses + 1));
                    }
                }
            }
            int min = Integer.MAX_VALUE;
            current = null;
            for (Map.Entry<Integer,Integer> stationToLegth: shortestPathFromSrc.entrySet()) {
                if(!inTree.contains(stationToLegth.getKey()) && min > stationToLegth.getValue()){
                    min = stationToLegth.getValue();
                    current = stationToLegth.getKey();
                }
            }
        }

        return shortestPathFromSrc.getOrDefault(target,-1);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,2,7],[3,6,7]]|1|6|2",
            "[[7,12],[4,5,15],[6],[15,19],[9,12,13]]|15|12|-1"
    })
    void test(String routesStr, int source, int target, int expected){
        int[][] routes = Arrays.stream(routesStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, numBusesToDestination(routes, source, target));

    }
}
