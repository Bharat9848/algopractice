package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.Set;
import java.util.Stack;

/**
 * You are given two m x n binary matrices grid1 and grid2 containing only 0's (representing water) and 1's (representing land). An island is a group of 1's connected 4-directionally (horizontal or vertical). Any cells outside of the grid are considered water cells.
 * <p>
 * An island in grid2 is considered a sub-island if there is an island in grid1 that contains all the cells that make up this island in grid2.
 * <p>
 * Return the number of islands in grid2 that are considered sub-islands.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: grid1 = [[1,1,1,0,0],[0,1,1,1,1],[0,0,0,0,0],[1,0,0,0,0],[1,1,0,1,1]], grid2 = [[1,1,1,0,0],[0,0,1,1,1],[0,1,0,0,0],[1,0,1,1,0],[0,1,0,1,0]]
 * Output: 3
 * Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
 * The 1s colored red in grid2 are those considered to be part of a sub-island. There are three sub-islands.
 * <p>
 * Example 2:
 * <p>
 * Input: grid1 = [[1,0,1,0,1],[1,1,1,1,1],[0,0,0,0,0],[1,1,1,1,1],[1,0,1,0,1]], grid2 = [[0,0,0,0,0],[1,1,1,1,1],[0,1,0,1,0],[0,1,0,1,0],[1,0,0,0,1]]
 * Output: 2
 * Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
 * The 1s colored red in grid2 are those considered to be part of a sub-island. There are two sub-islands.
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * m == grid1.length == grid2.length
 * n == grid1[i].length == grid2[i].length
 * 1 <= m, n <= 500
 * grid1[i][j] and grid2[i][j] are either 0 or 1.
 */
public class P1905CountNoOfSubIsland {

    int countSubIslands(int[][] grid1, int[][] grid2) {
        int subIslandCount = 0;
        boolean [][] visited2 = new boolean[grid2.length][grid2[0].length];
        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                if(grid2[i][j] == 1 && !visited2[i][j]){
                    boolean subIsland = discoverIsland(grid2, i, j, visited2, grid1);
                    if(subIsland){
                            subIslandCount+=1;
                    }
                }
            }
        }
        return subIslandCount;
    }

    private boolean discoverIsland(int[][] grid, int rowStart, int colStart, boolean[][] visited, int[][] grid1) {
        boolean allSubLand = true;
        Stack<Pair<Integer, Integer>> toVisit = new Stack<>();
        toVisit.push(new Pair<>(rowStart, colStart));
        visited[rowStart][colStart] = true;
        while (!toVisit.isEmpty()){
            Pair<Integer, Integer> current = toVisit.peek();
            int row = current.getFirst();
            int col = current.getSec();
            int[][] neighbours = new int[][]{{row-1, col}, {row+1, col}, {row, col-1}, {row, col+1}};
            boolean allVisited = true;
            for (int i = 0; i < neighbours.length; i++) {
                int r = neighbours[i][0];
                int c = neighbours[i][1];
                if(r >=0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c] == 1 && !visited[r][c]){
                    toVisit.push(new Pair<>(r, c));
                    allVisited = false;
                    visited[r][c] = true;
                    break;
                }
            }
            if(allVisited){
                Pair<Integer, Integer> landExplored = toVisit.pop();
                if(grid1[landExplored.getFirst()][landExplored.getSec()] == 0){
                    allSubLand = false;
                }
            }
        }
        return allSubLand;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,0,1,0,1],[1,1,1,1,1],[0,0,0,0,0],[1,1,1,1,1],[1,0,1,0,1]]|[[0,0,0,0,0],[1,1,1,1,1],[0,1,0,1,0],[0,1,0,1,0],[1,0,0,0,1]]|2",
            "[[1,1,1,0,0],[0,1,1,1,1],[0,0,0,0,0],[1,0,0,0,0],[1,1,0,1,1]]|[[1,1,1,0,0],[0,0,1,1,1],[0,1,0,0,0],[1,0,1,1,0],[0,1,0,1,0]]|3",
            "[[1,1,1],[1,1,1],[1,1,1]]|[[1,1,1],[1,1,1],[1,1,1]]|1",
            "[[1,1,1],[1,1,1],[1,1,1]]|[[1,1,1],[0,0,0],[1,1,1]]|2",
    })
    void test(String gridStr, String grid2Str, int expected) {
        int[][] grid1 = Arrays.stream(gridStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        int[][] grid2 = Arrays.stream(grid2Str.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);

        Assertions.assertEquals(expected, countSubIslands(grid1, grid2));
    }
}
