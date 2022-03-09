package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.Stack;

/**
 * Given a 2D grid consists of 0s (land) and 1s (water).  An island is a maximal 4-directionally connected group of 0s and a closed island is an island totally (all left, top, right, bottom) surrounded by 1s.
 *
 * Return the number of closed islands.
 *
 * Input: grid = [[1,1,1,1,1,1,1,0],[1,0,0,0,0,1,1,0],[1,0,1,0,1,1,1,0],[1,0,0,0,0,1,0,1],[1,1,1,1,1,1,1,0]]
 * Output: 2
 * Input: grid = [[0,0,1,0,0],[0,1,0,1,0],[0,1,1,1,0]]
 * Output: 1
 * Input: grid = [[1,1,1,1,1,1,1],
 *                [1,0,0,0,0,0,1],
 *                [1,0,1,1,1,0,1],
 *                [1,0,1,0,1,0,1],
 *                [1,0,1,1,1,0,1],
 *                [1,0,0,0,0,0,1],
 *                [1,1,1,1,1,1,1]]
 * Output: 2
 *     1 <= grid.length, grid[0].length <= 100
 *     0 <= grid[i][j] <=1
 */
public class P1254NoOfClosedIsland {
    int closedIsland(int[][] grid) {
        if(grid == null|| grid.length==0){
            return 0;
        }
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int closeIslandCount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 0 && !visited[i][j]){
                    closeIslandCount += discoverIsland(grid, i, j, visited) ? 1:0;
                }
            }
        }
        return closeIslandCount;
    }

    private boolean discoverIsland(int[][] grid, int rowSource, int colSource, boolean[][] visited) {
        boolean closed = !(rowSource == 0 || rowSource == grid.length-1 || colSource == 0 || colSource == grid[0].length-1);
        Stack<Pair<Integer, Integer>> toVisit = new Stack<>();
        toVisit.push(new Pair<>(rowSource, colSource));
        while (!toVisit.isEmpty()){
            Pair<Integer,Integer> visiting = toVisit.peek();
            int row = visiting.getFirst();
            int col = visiting.getSec();
            visited[row][col] = true;
            int[][] neighbours = new int[][]{ {row+1, col}, {row-1,col}, {row,col+1}, {row, col-1}};
            boolean allVisited = true;
            for (int i = 0; i < 4; i++) {
                int rn = neighbours[i][0];
                int cn = neighbours[i][1];
                if(rn < grid.length && rn >= 0 && cn < grid[0].length && cn >= 0 && !visited[rn][cn] && grid[rn][cn]==0){
                    if((rn == 0 || rn == grid.length-1 || cn == 0 || cn == grid[0].length-1) && grid[rn][cn] == 0){
                        closed = false;
                    }
                    toVisit.push(new Pair<>(rn, cn));
                    allVisited = false;
                    break;
                }
            }
            if(allVisited){
                toVisit.pop();
            }
        }
        return closed;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,1,1],[1,0,1],[1,1,1]]|1",
            "[[0,0,1],[1,0,1],[1,1,1]]|0",
            "[[1,1,1,1,1,1,1,0],[1,0,0,0,0,1,1,0],[1,0,1,0,1,1,1,0],[1,0,0,0,0,1,0,1],[1,1,1,1,1,1,1,0]]|2",
            "[[0,0,1,0,0],[0,1,0,1,0],[0,1,1,1,0]]|1",
            "[[1,1,1,1,1],[0,1,0,1,0],[1,1,1,1,1]]|1",
            "[[1,1,1,1,1,1,1],[1,0,0,0,0,0,1],[1,0,1,1,1,0,1],[1,0,1,0,1,0,1],[1,0,1,1,1,0,1],[1,0,0,0,0,0,1],[1,1,1,1,1,1,1]]|2"
    })
    void test(String gridStr, int expected){
        int[][] grid = Arrays.stream(gridStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected,closedIsland(grid));

    }
}
