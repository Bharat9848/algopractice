package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;

/**
 * There is a ball in a maze with empty spaces (represented as 0) and walls (represented as 1). The ball can go through the empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction.
 * <p>
 * Given the m x n maze, the ball's start position and the destination, where start = [startrow, startcol] and destination = [destinationrow, destinationcol], return the shortest distance for the ball to stop at the destination. If the ball cannot stop at destination, return -1.
 * <p>
 * The distance is the number of empty spaces traveled by the ball from the start position (excluded) to the destination (included).
 * <p>
 * You may assume that the borders of the maze are all walls (see examples).
 * Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [4,4]
 * Output: 12
 * Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.
 * The length of the path is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.
 * <p>
 * Example 2:
 * <p>
 * Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [3,2]
 * Output: -1
 * Explanation: There is no way for the ball to stop at the destination. Notice that you can pass through the destination but you cannot stop there.
 * <p>
 * Example 3:
 * <p>
 * Input: maze = [[0,0,0,0,0],[1,1,0,0,1],[0,0,0,0,0],[0,1,0,0,1],[0,1,0,0,0]], start = [4,3], destination = [0,1]
 * Output: -1
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * m == maze.length
 * n == maze[i].length
 * 1 <= m, n <= 100
 * maze[i][j] is 0 or 1.
 * start.length == 2
 * destination.length == 2
 * 0 <= startrow, destinationrow <= m
 * 0 <= startcol, destinationcol <= n
 * Both the ball and the destination exist in an empty space, and they will not be in the same position initially.
 * The maze contains at least 2 empty spaces.
 */
public class P505TheMazeII {

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    Optional<Pair<Pair<Integer, Integer>, Integer>> findEndNeighbour(int[][]maze, int[] from, Direction direction, boolean[][] visited, int oldDistance){
        int[] endNeighbour = endCoordinate(maze, direction, from);
        if(!visited[endNeighbour[0]][endNeighbour[1]]){
            int newDistance = calDistance(from, endNeighbour) + oldDistance;
            return Optional.of(new Pair<>(new Pair<>(endNeighbour[0], endNeighbour[1]), newDistance));
        }else {
            return Optional.empty();
        }

    }

    private int calDistance(int[] from, int[] to) {
        if(from[0] == to[0]){
            return Math.abs(from[1] - to[1]);
        }else {
            return Math.abs(from[0] - to[0]);
        }
    }

    private int[] endCoordinate(int[][] maze, Direction direction, int[] start) {
        int row = start[0], col = start[1];
        int endRow, endCol;
        switch (direction) {
            case UP: {
                int i = row - 1;
                for (; i >= 0 && maze[i][col] == 0; i--) {
                }
                endRow = i + 1;
                endCol = col;
                break;
            }
            case DOWN: {
                int i = row + 1;
                for (; i < maze.length && maze[i][col] == 0; i++) {
                }
                endRow = i - 1;
                endCol = col;
                break;
            }
            case LEFT: {
                int i = col - 1;
                for (; i >= 0 && maze[row][i] == 0; i--) {
                }
                endRow = row;
                endCol = i + 1;
                break;
            }
            case RIGHT: {
                int i = col + 1;
                for (; i < maze[0].length && maze[row][i] == 0; i++) {
                }
                endRow = row;
                endCol = i - 1;
                break;
            }
            default:{
                throw new RuntimeException("No direction");
            }
        }
        return new int[]{endRow, endCol};
    }

    public int shortestDistance(int[][] maze, int[] start, int[] destination){
        Map<Pair<Integer, Integer>, Integer> distanceFromStart = new HashMap<>();
        distanceFromStart.put(new Pair<>(start[0], start[1]), 0);
        Set<Pair<Integer, Integer>> inTree = new HashSet<>();
        Pair<Integer, Integer> next = new Pair<>(start[0], start[1]);
        Pair<Integer, Integer> destinationCoordinate = new Pair<>(destination[0], destination[1]);
        while(!inTree.contains(destinationCoordinate) && next != null) {
            inTree.add(next);
            int[] current = new int[]{next.getFirst(), next.getSec()};
            int oldDistanceFromStart = distanceFromStart.get(next);
            for (Direction direction : Direction.values()) {
                int[] endCoordinate = endCoordinate(maze, direction, current);
                if (endCoordinate[0] != next.getFirst() || endCoordinate[1] != next.getSec()) {
                    Pair<Integer, Integer> endCoordintePair = new Pair<>(endCoordinate[0], endCoordinate[1]);
                    Integer currentDistance = calDistance(current, endCoordinate);
                    int newDistance = oldDistanceFromStart + currentDistance;
                    if (distanceFromStart.getOrDefault(endCoordintePair, Integer.MAX_VALUE) > newDistance) {
                        distanceFromStart.put(endCoordintePair, newDistance);
                    }
                }
            }
            int minDistance = Integer.MAX_VALUE;
            for (Map.Entry<Pair<Integer, Integer>, Integer> coordinateToDistancePair : distanceFromStart.entrySet()) {
                Integer distance = coordinateToDistancePair.getValue();
                if (distance < minDistance && !inTree.contains(coordinateToDistancePair.getKey())) {
                    next = coordinateToDistancePair.getKey();
                    minDistance = distance;
                }
            }
            if (minDistance == Integer.MAX_VALUE) {
                next = null;
            }
        }
        return inTree.contains(destinationCoordinate) ? distanceFromStart.get(destinationCoordinate) : -1;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0,0,0,0]]|0,0|0,3|3",
            "[[0,0,0,0],[0,0,0,0]]|0,0|1,3|4",
            "[[0,0,1,0,0],[0,0,0,1,0],[0,1,0,0,0],[0,0,1,0,0],[0,0,0,0,0]]|0,0|0,4|8",
            "[[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]]|0,4|4,4|12",
            "[[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]]|0,4|2,1|9",
            "[[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]]|0,4|3,2|-1"
    })
    void test(String mazeStr, String start, String destination, int expected) {
        int[][] maze = Arrays.stream(mazeStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        int[] start1 = Arrays.stream(start.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] dest1 = Arrays.stream(destination.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, shortestDistance(maze, start1, dest1));
    }
}
