package dynamic.programming;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Given an m×nm×n binary matrix, mat, find the distance from each cell to the nearest 00. The distance between two adjacent cells is 11. Cells to the left, right, above, and below the current cell will be considered adjacent.
 * <p>
 * Constraints:
 * <p>
 * 1≤1≤ mat.row , mat.col≤50≤50
 * <p>
 * 1≤1≤ mat.row * mat.col ≤2500≤2500
 * <p>
 * mat[i][j] ∈{0,1}∈{0,1}
 * <p>
 * There is at least one 00 in mat.
 */

public class ZeroOneMatrix {

    public static int[][] updateMatrix(int[][] mat) {
       int[][] result = new int[mat.length][mat[0].length];
       int[][] visited = new int[mat.length][mat[0].length];
       var queue = new ArrayList<Pair<Integer, Integer>>();
       //init
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if(mat[i][j] == 0){
                    queue.add(new Pair<>(i, j));
                    visited[i][j] = 1;
                }
            }
        }
        int dist = 0;
        while (!queue.isEmpty()) {
            var tempQueue = new ArrayList<Pair<Integer, Integer>>();
            while (!queue.isEmpty()) {
                var current = queue.remove(0);
                int r = current.getFirst();
                int c = current.getSec();
                result[r][c] = dist;
                visited[r][c] = 1;
                addNeighbours(mat, tempQueue, visited, r, c);
            }

            dist++;
            queue = tempQueue;
        }
        return result;
    }

    private static void addNeighbours(int[][] mat, ArrayList<Pair<Integer, Integer>> queue, int[][] visited, int i, int j) {
        if(i-1 >= 0 && visited[i-1][j] == 0){
            queue.add(new Pair<>(i-1, j));
            visited[i-1][j] = 1;
        }
        if(i+1 < mat.length && visited[i+1][j] == 0){
            queue.add(new Pair<>(i+1, j));
            visited[i+1][j] = 1;
        }

        if(j-1 >= 0 && visited[i][j-1] == 0){
            queue.add(new Pair<>(i, j-1));
            visited[i][j-1] = 1;
        }

        if(j+1 < mat[0].length && visited[i][j+1] == 0){
            queue.add(new Pair<>(i, j+1));
            visited[i][j+1] = 1;
        }
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0,0,1]]|[[0,0,1]]",
            "[[0,1,1]],[[1,1,1]]|[[0,1,2],[1,2,3]]",
            "[[0,1,1],[1,1,1],[1,1,1]]|[[0,1,2],[1,2,3],[2,3,4]]"
    })
    void test(String matrix, String expectedMatrix) {
        var pattern = Pattern.compile("(\\[(\\d,)+\\d\\])");
        var matcher = pattern.matcher(matrix);
        int[][] grid = matcher.results()
                .map(matchResult -> matcher.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).mapToInt(ch -> Integer.parseInt(ch)).toArray();
                        }
                )
                .toArray(int[][]::new);
        var pattern1 = Pattern.compile("(\\[(\\d,)+\\d\\])");
        var matcher1 = pattern1.matcher(expectedMatrix);
        int[][] expected = matcher1.results()
                .map(matchResult -> matcher1.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).mapToInt(ch -> Integer.parseInt(ch)).toArray();
                        }
                )
                .toArray(int[][]::new);
        for (int i = 0; i < grid.length; i++) {
            Assertions.assertArrayEquals(expected[i], updateMatrix(grid)[i]);
        }
    }
}
