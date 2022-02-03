package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Given an n x n binary matrix grid, return the length of the shortest clear path in the matrix. If there is no clear path, return -1.
 *
 * A clear path in a binary matrix is a path from the top-left cell (i.e., (0, 0)) to the bottom-right cell (i.e., (n - 1, n - 1)) such that:
 *
 *     All the visited cells of the path are 0.
 *     All the adjacent cells of the path are 8-directionally connected (i.e., they are different and they share an edge or a corner).
 *
 * The length of a clear path is the number of visited cells of this path.
 *
 *  Input: grid = [[0,1],[1,0]]
 * Output: 2
 *
 * Input: grid = [[0,0,0],[1,1,0],[1,1,0]]
 * Output: 4
 *
 * Input: grid = [[1,0,0],[1,1,0],[1,1,0]]
 * Output: -1
 *
 * Constraints:
 *
 *     n == grid.length
 *     n == grid[i].length
 *     1 <= n <= 100
 *     grid[i][j] is 0 or 1
 */
public class P1091ClearPathInBinaryMatrix {
    int shortestPathBinaryMatrix(int[][] grid) {
        if(grid[0][0] == 1){
            return -1;
        }
        List<int[]> level = new LinkedList<>();
        level.add(new int[]{0,0});
        int levelCount = 1;
        int[] destination = new int[]{grid.length-1, grid[0].length-1};
        boolean reached = false;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        visited[0][0] = true;
        while (!level.isEmpty() && !reached){
            List<int[]> nextLevel = new LinkedList<>();
            while (!level.isEmpty()){
                int[] curr = level.remove(0);
                int row = curr[0];
                int col = curr[1];
                if(row == destination[0] && col == destination[1]){
                    reached = true;
                    break;
                }
                int[][] neighbours = new int[][]{{row-1, col-1}, {row-1, col}, {row-1, col + 1}, {row, col+1}, {row+1, col+1}, {row+1, col},{row+1, col-1}, {row, col-1}};
                for (int i = 0; i < 8; i++) {
                    int[] neighbour = neighbours[i];
                    int newRow = neighbour[0], newCol = neighbour[1];
                    if(newRow < grid.length && newRow >= 0
                    && newCol < grid[0].length && newCol >= 0 && grid[newRow][newCol] == 0 && !visited[newRow][newCol]){
                        visited[newRow][newCol] = true;
                        nextLevel.add(new int[]{newRow, newCol});
                    }
                }
            }
            if(!reached) levelCount++;
            level = nextLevel;
        }
        return reached ? levelCount:-1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,0,0],[1,1,0],[1,1,0]]|-1",
            "[[0,0,0],[1,1,0],[1,1,0]]|4",
            "[[0,0,0],[1,1,0],[1,1,1]]|-1",
            "[[0,1],[1,0]]|2",
    })
    void test(String gridStr, int expected){
        int[][] grid = Arrays.stream(gridStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, shortestPathBinaryMatrix(grid));
    }
}
