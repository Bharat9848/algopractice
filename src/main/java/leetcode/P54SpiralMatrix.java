package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given an m x n matrix, return all elements of the matrix in spiral order.
 *
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [1,2,3,6,9,8,7,4,5]
 *
 * Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 *
 *1-2-3
 *4-5-6
 * Constraints:
 *
 *     m == matrix.length
 *     n == matrix[i].length
 *     1 <= m, n <= 10
 *     -100 <= matrix[i][j] <= 100
 */
public class P54SpiralMatrix {
    List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        List<Integer> accumulator = new ArrayList<>(m*n);
        int minDim = Math.min(m, n);
        int currentRowDim = m, currentColDim = n;
        int[] currentStart = new int[]{0, 0};
        for (int i = minDim; i > 0 ; i= i -2) {
            currentStart = traverseFromLeftToRight(matrix, currentStart, currentColDim, accumulator);
            currentStart = traverseFromUpToDown(matrix, currentStart, currentRowDim, accumulator);
            currentStart = traverseFromRightToLeft(matrix, currentStart, currentColDim, accumulator);
            currentStart = traverseFromDownToUp(matrix, currentStart, currentRowDim, accumulator);
            currentColDim = currentColDim-2;
            currentRowDim = currentRowDim-2;
        }
        return accumulator;
    }

    private int[] traverseFromDownToUp(int[][] matrix, int[] currentStart, int currentRowDim, List<Integer> accumulator) {
        if(currentStart == null || currentRowDim < 1){
            return currentStart;
        }
        if(currentRowDim-2 == 0 ){
            return null;
        }else{
            int i = 0;
            for(; i < currentRowDim-2; i++){
                accumulator.add(matrix[currentStart[0]-i][currentStart[1]]);
            }
            return new int[]{currentStart[0]-i+1, currentStart[1]+1};
        }
    }

    private int[] traverseFromRightToLeft(int[][] matrix, int[] currentStart, int currentColDim, List<Integer> accumulator) {
        if(currentStart == null || currentColDim < 1){
            return currentStart;
        }
        if(currentColDim - 1 == 0){
            return null;
        }else {
            int i = 0;
            for (; i < currentColDim-1; i++){
                accumulator.add(matrix[currentStart[0]][currentStart[1]-i]);
            }
            return new int[]{currentStart[0]-1, currentStart[1]-i+1};
        }
    }

    private int[] traverseFromUpToDown(int[][] matrix, int[] currentStart, int currentRowDim, List<Integer> accumulator) {
        if(currentStart == null || currentRowDim < 1){
            return currentStart;
        }
        if(currentRowDim-1 == 0){
            return null;
        }else{
            int i = 0;
            for (; i < currentRowDim-1; i++){
                accumulator.add(matrix[currentStart[0] + i][currentStart[1]]);
            }
            return new int[]{currentStart[0] + i-1, currentStart[1]-1};
        }
    }

    private int[] traverseFromLeftToRight(int[][] matrix, int[] currentStart, int currentColDim, List<Integer> accumulator) {
        if(currentStart == null || currentColDim < 1){
            return currentStart;
        }
        int i = 0;
        for ( ; i < currentColDim; i++) {
            accumulator.add(matrix[currentStart[0]][currentStart[1]+ i]);
        }
        return new int[]{currentStart[0]+1, currentStart[1] +i-1};
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1]]|1",
            "[[1],[2],[3]]|1,2,3",
            "[[1,2],[3,4]]|1,2,4,3",
            "[[1,2,3],[4,5,6],[7,8,9]]|1,2,3,6,9,8,7,4,5",
            "[[1,2,3,4],[5,6,7,8],[9,10,11,12]]|1,2,3,4,8,12,11,10,9,5,6,7"

    })
    void test(String matrixStr, String expectedStr){
        int[][] matrix = Arrays.stream(matrixStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        List<Integer> expected = Arrays.stream(expectedStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        Assertions.assertEquals(expected, spiralOrder(matrix));
    }
}
