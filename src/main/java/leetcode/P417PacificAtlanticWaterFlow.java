package leetcode;
/*
Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.

Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.

Note:

    The order of returned grid coordinates does not matter.
    Both m and n are less than 150.



Example:

Given the following 5x5 matrix:

  Pacific ~   ~   ~   ~   ~
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * Atlantic

Return:

[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).

SOLUTION : think reverse problem : Water come inside.

 */

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class P417PacificAtlanticWaterFlow {

    private enum Reachable {
        NEITHER, ATLANTIC_REACHABLE, PACIFIC_REACHABLE, BOTH_REACHABLE
    }

     List<List<Integer>> pacificAtlantic(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }

        int nrow = matrix.length;
        int ncol = matrix[0].length;

         Reachable[][] reachables = new Reachable[nrow][ncol];
         if (ncol == 1 || nrow == 1) {
                 return IntStream.range(0, Math.max(nrow, ncol)).mapToObj(index -> {
                     if(ncol == 1){
                        return Arrays.asList(index, 0);
                     }else {
                         return Arrays.asList(0, index);
                     }
                 }).collect(Collectors.toList());
         }
         List<int[]> pacificNodes = new ArrayList<>(ncol + nrow -1);
         List<int[]> atlanticNodes = new ArrayList<>(ncol + nrow -1);
         for (int i = 0; i < nrow; i++) {
             for (int j = 0; j < ncol; j++) {
                 if ((i == 0 && j == matrix[0].length - 1) || (j == 0 && i == matrix.length - 1)) {
                     pacificNodes.add(new int[]{i,j});
                     atlanticNodes.add(new int[] {i,j});
                     reachables[i][j] = Reachable.BOTH_REACHABLE;
                 } else if (i == 0 || j == 0) {
                     pacificNodes.add(new int[]{i,j});
                     reachables[i][j] = Reachable.PACIFIC_REACHABLE;
                 } else if (i == matrix.length - 1 || j == matrix[0].length - 1) {
                     atlanticNodes.add(new int[] {i,j});
                     reachables[i][j] = Reachable.ATLANTIC_REACHABLE;
                 } else {
                     reachables[i][j] = Reachable.NEITHER;
                 }
             }
         }
         final boolean[][] visited = new boolean[nrow][ncol];
         pacificNodes.forEach(cell -> visited[cell[0]][cell[1]] = true);
        //Pacific cityWater Climb
         while (!pacificNodes.isEmpty()){
             List<int[]> nextGenPacific = new LinkedList<>();
             while (!pacificNodes.isEmpty()){
                 int[] node = pacificNodes.remove(0);
                 int row = node[0], col = node[1];
                 int[][] neighbours = new int[][]{{row-1, col}, {row, col+1},{row+1, col}, {row, col-1}};
                 for (int i = 0; i < neighbours.length ; i++) {
                     int newRow = neighbours[i][0];
                     int newCol = neighbours[i][1];
                     if(newRow >= 0 && newCol >=0 && newRow < matrix.length && newCol < matrix[0].length && matrix[newRow][newCol] >= matrix[row][col] && !visited[newRow][newCol]){
                         if(reachables[newRow][newCol] == Reachable.ATLANTIC_REACHABLE || reachables[newRow][newCol] == Reachable.BOTH_REACHABLE){
                             reachables[newRow][newCol] = Reachable.BOTH_REACHABLE;
                         }else{
                             reachables[newRow][newCol] = Reachable.PACIFIC_REACHABLE;
                         }
                         nextGenPacific.add(new int[]{newRow, newCol});
                     }
                 }
                 visited[row][col] = true;
             }
             pacificNodes = nextGenPacific;
         }
         final boolean[][] visited1 = new boolean[nrow][ncol];
         atlanticNodes.forEach(cell -> visited1[cell[0]][cell[1]] = true);
         //Atlantic water climb
         while (!atlanticNodes.isEmpty()){
             List<int[]> nextGenAtlantic = new LinkedList<>();
             while (!atlanticNodes.isEmpty()){
                 int[] node = atlanticNodes.remove(0);
                 int row = node[0], col = node[1];
                 int[][] neighbours = new int[][]{{row-1, col}, {row, col+1},{row+1, col}, {row, col-1}};
                 visited1[row][col] = true;
                 for (int i = 0; i < neighbours.length ; i++) {
                     int newRow = neighbours[i][0];
                     int newCol = neighbours[i][1];
                     if(newRow >= 0 && newCol >=0 && newRow < matrix.length && newCol < matrix[0].length && matrix[newRow][newCol] >= matrix[row][col] && !visited1[newRow][newCol]){
                         if(reachables[newRow][newCol] == Reachable.PACIFIC_REACHABLE || reachables[newRow][newCol] == Reachable.BOTH_REACHABLE ){
                             reachables[newRow][newCol] = Reachable.BOTH_REACHABLE;
                         }else{
                             reachables[newRow][newCol] = Reachable.ATLANTIC_REACHABLE;
                         }
                         nextGenAtlantic.add(new int[]{newRow, newCol});
                     }
                 }
             }
             atlanticNodes = nextGenAtlantic;
         }

        //solve all vertices
         List<List<Integer>> result = new ArrayList<>();
         for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                if (reachables[i][j] == Reachable.BOTH_REACHABLE) {
                    result.add(asList(i, j));
                }
            }
        }
        return result;
    }

    @Test
    public void testEmpty() {
        assertTrue(pacificAtlantic(new int[][]{{}}).isEmpty() );
        assertTrue(pacificAtlantic(new int[][]{}).isEmpty());

    }

    @ParameterizedTest
    @CsvSource(delimiter = '|',
            value = {
                "[[89, 90],[100, 100]|[[0,1],[1,0],[1,1]]",
                "[[89, 90, 91, 101],[100, 100, 100, 100],[221, 89, 90, 91]]|[[0,3],[1,0],[1,1],[1,2],[1,3],[2,0]]",
                 "[[1,2,3,4]]|[[0,0],[0,1],[0,2],[0,3]]",
                 "[[1],[2],[3],[4]]|[[0,0],[1,0],[2,0],[3,0]]",
                    "[[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]|[[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]"
            })
    void test(String waterStr, String expectedStr){
        int[][] water =Arrays.stream(waterStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);
        Set<List<Integer>> expected = Arrays.stream(expectedStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).map(String::trim).map(Integer::parseInt).collect(Collectors.toList()))
                .collect(Collectors.toSet());
        Assert.assertEquals(expected, new HashSet<>(pacificAtlantic(water)));
    }
}
