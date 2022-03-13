package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.Stack;

/**
 * You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.
 *
 * A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking off the boundary of the grid.
 *
 * Return the number of land cells in grid for which we cannot walk off the boundary of the grid in any number of moves.
 *
 * Input: grid = [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
 * Output: 3
 * Input: grid = [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
 * Output: 0
 *
 *     m == grid.length
 *     n == grid[i].length
 *     1 <= m, n <= 500
 *     grid[i][j] is either 0 or 1.
 */
public class P1020NumberOfEnclave {
    int numEnclaves(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int nonSeaLandBlocks = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(!visited[i][j] && grid[i][j] == 1){
                    nonSeaLandBlocks += discoverNonSeaLandBlocks(grid, i , j, visited);
                }
            }
        }
        return nonSeaLandBlocks;
    }

    private int discoverNonSeaLandBlocks(int[][] grid, int rowStart, int colStart, boolean[][] visited) {
        int count = 0;
        boolean gridReachable = false;
        Stack<Pair<Integer, Integer>> toVisit = new Stack<>();
        toVisit.push(new Pair<>(rowStart, colStart));
        visited[rowStart][colStart] = true;
        while (!toVisit.isEmpty()){
            Pair<Integer, Integer> current = toVisit.peek();
            int row = current.getFirst();
            int col = current.getSec();
            int[][] neighbours = new int[][]{{row-1, col}, {row+1, col}, {row, col-1}, {row, col+1}};
            gridReachable |= (row==0 || row == grid.length-1 || col == 0 || col == grid[0].length-1);
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
                toVisit.pop();
                if(!gridReachable){
                    count+=1;
                }else {
                    count=0;
                }
            }
        }
        return count;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0]]|0",
            "[[1]]|0",
            "[[0,0,0],[0,1,0],[0,0,0]]|1",
            "[[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]|3",
            "[[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]|0",
    })
    void test(String gridStr, int expected){
        int[][] grid = Arrays.stream(gridStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, numEnclaves(grid));
    }
}
