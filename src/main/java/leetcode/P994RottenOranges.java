package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;

/**
 * You are given an m x n grid where each cell can have one of three values:
 *
 *     0 representing an empty cell,
 *     1 representing a fresh orange, or
 *     2 representing a rotten orange.
 *
 * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
 *
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.
 *
 *
 *
 * Example 1:
 *
 * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 *
 * Example 2:
 *
 * Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
 *
 * Example 3:
 *
 * Input: grid = [[0,2]]
 * Output: 0
 * Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
 *
 *
 *
 * Constraints:
 *
 *     m == grid.length
 *     n == grid[i].length
 *     1 <= m, n <= 10
 *     grid[i][j] is 0, 1, or 2.
 */
public class P994RottenOranges {
    public int orangesRotting(int[][] grid) {
        List<Pair<Integer, Integer>> rottenCoordinates = findAllRotten(grid);
        Set<Pair<Integer, Integer>> visited = new HashSet<>(rottenCoordinates);
        int minutes = 0;
        while(!rottenCoordinates.isEmpty()){
            List<Pair<Integer, Integer>> newRotten = new LinkedList<>();
            while (!rottenCoordinates.isEmpty()){
                Pair<Integer, Integer> rotten = rottenCoordinates.remove(0);
                int row = rotten.getFirst();
                int col = rotten.getSec();
                int[][] neighbours = new int[][]{{row-1,col}, {row+1, col}, {row, col-1}, {row, col+1}};
                for (int[] neighbour:neighbours){
                    int r = neighbour[0], c = neighbour[1];
                    if(r>=0 && r<grid.length && c >= 0 && c < grid[0].length && grid[r][c] == 1){
                        if(!visited.contains(new Pair<>(r, c))){
                            newRotten.add(new Pair<>(r,c));
                            visited.add(new Pair<>(r, c));
                        }
                    }
                }
            }
            rottenCoordinates = newRotten;
            if(newRotten.size()>0) {
                minutes++;
            }
        }
        return checkIfAllVisited(grid, visited)? minutes:-1;
    }

    private boolean checkIfAllVisited(int[][] grid, Set<Pair<Integer, Integer>> visited) {
        boolean allVisited = true;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] != 0 && !visited.contains(new Pair<>(i,j))){
                    return false;
                }
            }
        }
        return allVisited;
    }

    private List<Pair<Integer, Integer>> findAllRotten(int[][] grid) {
        List<Pair<Integer, Integer>> source = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 2){
                    source.add(new Pair<>(i, j));
                }
            }
        }
        return source;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value={
            "[[2,1,1],[1,1,0],[0,1,1]]|4",
            "[[2,1,1],[0,1,1],[1,0,1]]|-1",
            "[[0,2]]|0",
            "[[2,1,0],[1,0,1],[0,1,2]]|1",
            "[[2,1,0],[1,0,1],[0,1,1]]|-1"
    })
    void test(String gridStr, int expected){
        int[][] grid = Arrays.stream(gridStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                        Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        Assert.assertEquals(expected, orangesRotting(grid));
    }

}
