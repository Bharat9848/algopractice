package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
 *
 * An island is considered to be the same as another if and only if one island can be translated (and not rotated or reflected) to equal the other.
 *
 * Return the number of distinct islands.
 *
 * Input: grid = [[1,1,0,0,0],[1,1,0,0,0],[0,0,0,1,1],[0,0,0,1,1]]
 * Output: 1
 *
 * Input: grid = [[1,1,0,1,1],[1,0,0,0,0],[0,0,0,0,1],[1,1,0,1,1]]
 * Output: 3
 *
 *
 *
 * Constraints:
 *
 *     m == grid.length
 *     n == grid[i].length
 *     1 <= m, n <= 50
 *     grid[i][j] is either 0 or 1.
 *
 * @TODO unoptimized instead of getting all cooridinating and then transforming, A pattern can be calculated while doing dfs.
 */
public class P694DistinctIsland {
    public int numDistinctIslands(int[][] grid) {
        Map<Pair<Integer, Integer>, Set<Pair<Integer, Integer>>> islands = new HashMap<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
               if(!visited[i][j] && grid[i][j] ==1){
                   Set<Pair<Integer,Integer>> gridPoints = discoverIsland(grid, i, j, visited);
                   Set<Pair<Integer, Integer>> transformedGridPoints = transformIslandToOrigin(i, j, gridPoints);
                   boolean similar = false;
                   for (Map.Entry<Pair<Integer, Integer>, Set<Pair<Integer, Integer>>> island: islands.entrySet()) {
                        if(island.getValue().equals(transformedGridPoints)){
                           similar = true;
                            break;
                        }
                   }
                   if(!similar){
                       islands.put(new Pair<>(i, j), transformedGridPoints);
                   }
               }
            }
        }
        return islands.size();
    }

    private Set<Pair<Integer, Integer>> discoverIsland(int[][] grid, int rowStart, int colStart, boolean[][] visited) {
        Set<Pair<Integer,Integer>> result = new HashSet<>();
        Stack<Pair<Integer, Integer>> toProcess = new Stack<>();
        toProcess.add(new Pair<>(rowStart, colStart));
        while(!toProcess.isEmpty()){
            Pair<Integer, Integer> current = toProcess.peek();
            int row = current.getFirst();
            int col = current.getSec();
            visited[row][col] = true;
            int[][] neighbours = new int[][]{{row-1, col}, {row, col+1}, {row+1, col}, {row, col-1}};
            boolean allVisited = true;
            for (int[] neigh : neighbours){
                int r = neigh[0];
                int c = neigh[1];
                if(r>=0 && r< grid.length && c >=0 && c < grid[0].length && grid[r][c]==1 && !visited[r][c]){
                    toProcess.push(new Pair<>(r,c));
                    allVisited = false;
                }
            }
            if(allVisited){
                toProcess.pop();
                result.add(new Pair<>(row,col));
            }
        }
        return result;
    }

    private Set<Pair<Integer, Integer>> transformIslandToOrigin(int rowStart, int colStart, Set<Pair<Integer, Integer>> gridPoints) {
        return gridPoints.stream().map(original -> new Pair<>(original.getFirst() - rowStart, original.getSec() - colStart)).collect(Collectors.toSet());
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,1,0,0,0],[1,1,0,0,0],[0,0,0,1,1],[0,0,0,1,1]] |1",
            "[[1,1,0,1,1],[1,0,0,0,0],[0,0,0,0,1],[1,1,0,1,1]]|3",
    })
    void test(String gridStr, int expected){
        int[][] grid = Arrays.stream(gridStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected,numDistinctIslands(grid));
    }
}
