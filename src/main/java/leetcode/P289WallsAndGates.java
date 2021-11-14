package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * You are given an m x n grid rooms initialized with these three possible values.
 *
 *     -1 A wall or an obstacle.
 *     0 A gate.
 *     INF Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
 *
 * Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
 *
 *
 *
 * Example 1:
 *
 * Input: rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
 * Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
 *
 * Example 2:
 *
 * Input: rooms = [[-1]]
 * Output: [[-1]]
 *
 * Example 3:
 *
 * Input: rooms = [[2147483647]]
 * Output: [[2147483647]]
 *
 * Example 4:
 *
 * Input: rooms = [[0]]
 * Output: [[0]]
 *
 *
 *
 * Constraints:
 *
 *     m == rooms.length
 *     n == rooms[i].length
 *     1 <= m, n <= 250
 *     rooms[i][j] is -1, 0, or 231 - 1.
 */

class P289WallsAndGates {
    private enum Visited{EXPLORED, VISITED, NOTVISITED}
    public void wallsAndGates(int[][] rooms) {
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[0].length; j++) {
                Visited[][] visited = new Visited[rooms.length][rooms[0].length];
                Arrays.stream(visited).forEach(row -> Arrays.fill(row, Visited.NOTVISITED));
                if(rooms[i][j] == 0){
                    traversingRoomsLevelByLevel(i, j, rooms, visited);
                }
            }
        }
    }

    private void traversingRoomsLevelByLevel(int row, int col, int[][] rooms, Visited[][] visited) {
        int level = 0;
        List<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(row, col));
        while (!queue.isEmpty()) {
            List<Pair<Integer, Integer>> neighbours = new LinkedList<>();
            while (!queue.isEmpty()) {
                Pair<Integer, Integer> node = queue.remove(0);
                Integer r = node.getFirst();
                Integer c = node.getSec();
                visited[r][c] = Visited.VISITED;
                if (r > 0 && rooms[r - 1][c] > 0 && visited[r - 1][c] == Visited.NOTVISITED) {
                    neighbours.add(new Pair<>(r - 1, c));
                    visited[r - 1][c] = Visited.VISITED;
                    if (rooms[r - 1][c] > level + 1) {
                        rooms[r - 1][c] = level + 1;
                    }
                }
                if (c < rooms[0].length - 1 && rooms[r][c + 1] > 0 && visited[r][c + 1] == Visited.NOTVISITED) {
                    neighbours.add(new Pair<>(r, c + 1));
                    visited[r][c + 1] = Visited.VISITED;
                    if (rooms[r][c + 1] > level + 1) {
                        rooms[r][c + 1] = level + 1;
                    }
                }
                if (r < rooms.length - 1 && rooms[r + 1][c] > 0 && visited[r + 1][c] == Visited.NOTVISITED) {
                    visited[r + 1][c] = Visited.VISITED;
                    neighbours.add(new Pair<>(r + 1, c));
                    if (rooms[r + 1][c] > level + 1) {
                        rooms[r + 1][c] = level + 1;
                    }
                }
                if (c > 0 && rooms[r][c - 1] > 0 && visited[r][c - 1] == Visited.NOTVISITED) {
                    visited[r][c - 1] = Visited.VISITED;
                    neighbours.add(new Pair<>(r, c - 1));
                    if (rooms[r][c - 1] > level + 1) {
                        rooms[r][c - 1] = level + 1;
                    }
                }
            }
            level += 1;
            queue = neighbours;
        }
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0]]|[[0]]",
            "[[2147483647]]|[[2147483647]]",
            "[[-1]]|[[-1]]",
            "[[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]|[[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]",
            "[[2147483647,0,2147483647,2147483647,0,2147483647,-1,2147483647]]|[[1,0,1,1,0,1,-1,2147483647]]"
    })
    void test(String roomsStr, String expectedStr){
        int [][] rooms = Arrays.stream(roomsStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                    Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        int[][] expected = Arrays.stream(expectedStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                        Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        wallsAndGates(rooms);
        Assert.assertArrayEquals(expected, rooms);
    }
}
