package graph.undirected.traversal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * You are given an array, routes, representing bus routes where routes[i] is a bus route that the ith bus repeats forever. Every route contains one or more stations. You have also been given the source station, src, and a destination station, dest. Return the minimum number of buses someone must take to travel from src to dest, or return -1 if there is no route.
 *
 * Constraints:
 *
 *     1≤ routes.length ≤50
 *     1≤ routes[i].length ≤100
 *     0≤ routes[i][j] <1000
 *     0≤ src, dest <1000
 */
public class BusRoutes {
    public static int minimumBuses(int[][] busRoutes, int src, int dest) {
        int numBuses = 0;
        if(busRoutes.length == 0){
            return -1;
        }
        if(src == dest){
            return 0;
        }
        Map<Integer, List<Integer>> stationToBus = new HashMap<>();
        for (int i = 0; i < busRoutes.length; i++) {
            for (int j = 0; j < busRoutes[i].length; j++) {
                stationToBus.putIfAbsent(busRoutes[i][j], new ArrayList<Integer>());
                stationToBus.get(busRoutes[i][j]).add(i);
            }
        }
        if(stationToBus.getOrDefault(src, new ArrayList<>()).isEmpty() ||  stationToBus.getOrDefault(dest, new ArrayList<>()).isEmpty()){
            return -1;
        }
        Set<Integer> visited = new HashSet<>();
        Set<Integer> currentStations = new HashSet<>();
        List<Integer> busIds = stationToBus.get(src);
        for (int i = 0; i < busIds.size(); i++) {
            Arrays.stream(busRoutes[busIds.get(i)]).forEach(currentStations::add);
        }
        if(!currentStations.isEmpty()){
            numBuses++;
        }
        boolean destFound = false;
        visited.addAll(currentStations);
        Iterator<Integer> currentStationsIterator = currentStations.iterator();
        while (!destFound && currentStationsIterator.hasNext()) {
            Set<Integer> nextStations = new HashSet<>();
            while (currentStationsIterator.hasNext()) {
                if (currentStations.contains(dest)) {
                    destFound = true;
                    break;
                }
                int station = currentStationsIterator.next();
                List<Integer> buses = stationToBus.getOrDefault(station, new ArrayList<Integer>());
                for (Integer bus : buses) {
                    int[] nextStationsForBus = busRoutes[bus];
                    Set<Integer> newStationsSet = Arrays.stream(nextStationsForBus).boxed().collect(Collectors.toSet());
                    if (!visited.containsAll(newStationsSet)) {
                        visited.addAll(nextStations);
                        nextStations.addAll(newStationsSet);
                    }
                }
            }
            if (!nextStations.isEmpty()) {
                numBuses++;
                currentStations = nextStations;
                currentStationsIterator = currentStations.iterator();
            }

        }
        return destFound?numBuses:-1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,2,3],[2,4,5,6,7],[3,7,8]]|1|8|2",
            "[[]]|2|4|-1",
            "[[2]]|2|2|0",
            "[[1]]|1|5|-1",
            "[[1,2,7],[3,6,7]]|8|6|-1",
            "[[2]]|2|2|0",
            "[[2],[2,8]]|8|2|1",
            "[[1,2,7],[3,6,7]]|1|6|2",
            "[[1,2,7],[3,6,7]]|7|1|1",
            "[[7,12],[4,5,15],[6],[15,19],[9,12,13]]|15|12|-1",
            "[[1,2,7]]|1|7|1"
    })
    void test(String routesStr, int source, int target, int expected){
        int[][] routes = Arrays.stream(routesStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, minimumBuses(routes, source, target));

    }
}
