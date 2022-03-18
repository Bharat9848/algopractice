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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class P417PacificAtlanticWaterFlow {
    List<List<Integer>> pacificAtlantic(int[][] matrix) {
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        Set<Pair<Integer,Integer>> atlanticReachable = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            atlanticReachable.add(new Pair<>(i, matrix[0].length-1));
            visited[i][matrix[0].length-1] = true;
        }
        for (int j = 0; j < matrix[0].length; j++) {
            atlanticReachable.add(new Pair<>(matrix.length-1, j));
            visited[matrix.length-1][j] = true;
        }
        Set<Pair<Integer, Integer>> allAtlanticReachable = findAllReachableBFS(atlanticReachable, matrix, visited);

        boolean[][] visited1 = new boolean[matrix.length][matrix[0].length];
        Set<Pair<Integer,Integer>> pacificReachables = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            pacificReachables.add(new Pair<>(i, 0));
            visited1[i][0] = true;
        }
        for (int j = 0; j < matrix[0].length; j++) {
            pacificReachables.add(new Pair<>(0, j));
            visited1[0][j] = true;
        }
        Set<Pair<Integer,Integer>> allPacificReachable = findAllReachableBFS(pacificReachables, matrix, visited1);
        allPacificReachable.retainAll(allAtlanticReachable);
        return allPacificReachable.stream().map(pair -> Arrays.asList(pair.getFirst(), pair.getSec())).collect(Collectors.toList());
    }

    Set<Pair<Integer,Integer>> findAllReachableBFS(Set<Pair<Integer, Integer>> levelNodes, int[][] matrix, boolean[][] visited){
        Set<Pair<Integer,Integer>> allReachable = new HashSet<>();
        allReachable.addAll(levelNodes);
        while (!levelNodes.isEmpty()){
            Set<Pair<Integer,Integer>> nextReachable = new HashSet<>();
            for(Pair<Integer, Integer> atlanticCurrent: levelNodes){
                int row = atlanticCurrent.getFirst();
                int col = atlanticCurrent.getSec();
                int[][] neighbours = new int[][]{{row-1, col},{row, col+1}, {row+1,col}, {row, col-1}};
                for (int i = 0; i <neighbours.length; i++) {
                    int r = neighbours[i][0];
                    int c = neighbours[i][1];
                    if(r>=0 && r< matrix.length && c >=0 && c<matrix[0].length && !visited[r][c] && matrix[r][c] >= matrix[row][col]){
                        nextReachable.add(new Pair<>(r,c));
                        visited[r][c] = true;
                    }
                }
            }
            allReachable.addAll(nextReachable);
            levelNodes = nextReachable;
        }
        return allReachable;
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
        Assertions.assertEquals(expected, new HashSet<>(pacificAtlantic(water)));
    }
}
