package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.Stack;

/**Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

 Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

 Example 1:
 [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
 Example 2:
 [[0,0,0,0,0,0,0,0]]
 Given the above grid, return 0.
 Note: The length of each dimension in the given grid does not exceed 50.
 * Created by bharat on 27/5/18.
 */
public class P695MaxAreaIsland {


    public int maxAreaOfIsland(int[][] grid) {
        if(grid == null|| grid.length==0){
            return 0;
        }
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int area = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 1 && !visited[i][j]){
                    area = Math.max(discoverIsland(grid, i, j, visited), area);
                }
            }
        }
        return area;
    }

    private int discoverIsland(int[][] grid, int rowSource, int colSource, boolean[][] visited) {
        int area = 0;
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
                if(rn < grid.length && rn >=0 && cn < grid[0].length && cn >=0 && !visited[rn][cn] && grid[rn][cn]==1){
                    toVisit.push(new Pair<>(rn, cn));
                    allVisited = false;
                    break;
                }
            }
            if(allVisited){
                area+=1;
                toVisit.pop();
            }
        }
        return area;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]|6",
            "[0,0,1,0,0,0,0,1,0],[0,0,0,0,0,0,1,1,1]|4",
            "[[1,1,0,1]]|2",
            "[[1]]|1",
            "[[0]]|0",
    })
    void test123(String matrixStr, int expected){
        int[][] map = Arrays.stream(matrixStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, maxAreaOfIsland(map));
    }

    @Test
    void testEdgeCases(){
        Assertions.assertEquals(0, maxAreaOfIsland(new int[0][]));
        Assertions.assertEquals(0, maxAreaOfIsland(new int[][]{}));
    }
}
