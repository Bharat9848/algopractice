package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given an n x n grid containing only values 0 and 1, where 0 represents water and 1 represents land, find a water cell such that its distance to the nearest land cell is maximized, and return the distance. If no land or water exists in the grid, return -1.
 *
 * The distance used in this problem is the Manhattan distance: the distance between two cells (x0, y0) and (x1, y1) is |x0 - x1| + |y0 - y1|.
 *
 * Input: grid = [[1,0,1],[0,0,0],[1,0,1]]
 * Output: 2
 * Input: grid = [[1,0,0],[0,0,0],[0,0,0]]
 * Output: 4
 * Constraints:
 *
 *     n == grid.length
 *     n == grid[i].length
 *     1 <= n <= 100
 *     grid[i][j] is 0 or 1
 */
public class P1162AsFarAsLandPossible {
    int maxDistance(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Set<Pair<Integer, Integer>> level = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 1){
                    level.add(new Pair<>(i ,j));
                    visited[i][j] = true;
                }
            }
        }
        if(level.isEmpty()){
            return -1;
        }
        int distance = 0;
        int maxDistance = -1;
        while (!level.isEmpty()){
            Set<Pair<Integer,Integer>> nextLevel = new HashSet<>();
            for (Pair<Integer, Integer> current: level){
                int row = current.getFirst();
                int col = current.getSec();
                int[][] neighbours = new int[][]{{row-1,col},{row, col+1}, {row+1, col}, {row, col-1}};
                for (int i = 0; i < neighbours.length; i++) {
                    int r = neighbours[i][0];
                    int c = neighbours[i][1];
                    if(r >=0 && r < grid.length && c>=0 && c <grid[0].length && grid[r][c] == 0 && !visited[r][c]){
                        int distance1 = Math.abs(r - row) + Math.abs(c - col);
                        maxDistance = Math.max(maxDistance, distance1+ distance);
                        nextLevel.add(new Pair<>(r, c));
                        visited[r][c] = true;
                    }
                }
            }
            level = nextLevel;
            distance++;
        }
        return maxDistance;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,0,1],[0,0,0],[1,0,1]]|2",
            "[[1,0,0],[0,0,0],[0,0,0]]|4",
            "[[0,0]]|-1",
            "[[1,1]]|-1",
    })
    void test(String gridStr, int expected){
        int[][] grid = Arrays.stream(gridStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, maxDistance(grid));
    }
}
