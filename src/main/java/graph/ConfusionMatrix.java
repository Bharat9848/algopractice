package graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.regex.Pattern;

/**
 * A maze consists of nn rooms numbered from 1−n1−n, and some rooms are connected by corridors. You are given a 2D integer array, corridors, where corridors[i]=[room1,room2]corridors[i]=[room1,room2] indicates that there is a corridor connecting room1room1 and room2room2, allowing a person in the maze to go from room1room1 to room2room2 and vice versa.
 *
 * The designer of the maze wants to know how confusing the maze is. The confusion score of the maze is the number of different cycles of length 3.
 *
 * For example, 1→2→3→11→2→3→1 is a cycle of length 33, but 1→2→3→41→2→3→4 and 1→2→3→2→11→2→3→2→1 are not.
 *
 * Two cycles are considered to be different if one or more of the rooms visited in the first cycle is not in the second cycle.
 *
 * Return the confusion score of the maze.
 *
 * Constraints:
 *
 *     2≤ n ≤100
 *     1≤ corridors.length ≤5×10^2
 *     corridors[i].length =2
 *     1≤room1i,room2i≤n
 *     room1i≠room2i
 *     There are no duplicate corridors.
 */
public class ConfusionMatrix {
    public static  int numberOfPaths(int n, int[][] corridors) {
        Map<Integer, List<Integer>> adjacencyList = getAdjList(corridors);
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Boolean> visited = new HashMap<>();
        int no = 0;
        for (int i = 1; i <= n; i++) {
            if(!visited.containsKey(i)){
                no += findDfsNoOf3Cycle(i, stack, visited, adjacencyList);
            }
        }
        return no;
    }

    private static Map<Integer, List<Integer>> getAdjList(int[][] corridors) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < corridors.length; i++) {
            int x = corridors[i][0];
            int y = corridors[i][1];
            adjList.putIfAbsent(x, new ArrayList<>());
            adjList.putIfAbsent(y, new ArrayList<>());
            adjList.get(x).add(y);
            adjList.get(y).add(x);
        }
        return adjList;
    }

    private static int findDfsNoOf3Cycle(int i, Stack<Integer> stack, Map<Integer, Boolean> visited, Map<Integer, List<Integer>> adjList) {
        stack.push(i);
        int cycle = 0;
        while (!stack.isEmpty()){
            int current = stack.peek();
            visited.put(current, true);
            boolean allVisited = true;
            List<Integer> neighbours = adjList.getOrDefault(current, new ArrayList<>());
            for (int j = 0; j < neighbours.size(); j++) {
                int neigh =  neighbours.get(j);
                if(visited.containsKey(neigh)){
                    if(isGrandFather(neigh, stack)){
                        cycle++;
                    }
                } else {
                    allVisited = false;
                    stack.push(neigh);
                    break;
                }
            }
            if(allVisited){
                stack.pop();
            }
        }
        return cycle;
    }

    private static boolean isGrandFather(int neigh, Stack<Integer> stack) {
        boolean isGreatGrandFather = false;
        int parent = stack.pop();
        if(!stack.isEmpty()){
            int grand  = stack.pop();
            if(!stack.isEmpty()){
                int greatGrand = stack.pop();
                isGreatGrandFather = greatGrand == neigh;
                stack.push(greatGrand);
            }
            stack.push(grand);
        }
        stack.push(parent);
        return isGreatGrandFather;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3|[1,3],[2,3],[1,2]]|1",
            "3|[[1,2],[2,3]|0",
            "5|[[1,2],[5,2],[4,1],[2,4],[3,1],[3,4]]|2",
            "7|[[1,2],[1,4],[2,4],[5,4],[5,1],[3,7],[6,7],[3,6]]|3",
            "6|[[1,2],[3,4],[5,6]]|0",
            })
    void test(int n, String roomsStr, int expected) {
        var pattern = Pattern.compile("\\[\\d,\\d\\]");
        var matcher = pattern.matcher(roomsStr);
        int[][] rooms = matcher.results().map(matchResult -> matcher.group()).map(str -> {
            var tokens =  str.split(",");
            int first = Integer.parseInt(tokens[0].replace("[", ""));
            int second = Integer.parseInt(tokens[1].replace("]", ""));
            return new int[] {first, second};
        }).toArray(int[][]::new);
        Assertions.assertEquals(expected, numberOfPaths(n, rooms));
    }
}
