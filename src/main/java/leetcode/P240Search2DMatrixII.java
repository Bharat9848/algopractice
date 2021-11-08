package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Write an efficient algorithm that searches for a target value in an m x n integer matrix. The matrix has the following properties:
 *
 *     Integers in each row are sorted in ascending from left to right.
 *     Integers in each column are sorted in ascending from top to bottom.
 *
 *
 *
 * Example 1:
 *
 * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
 * Output: true
 *
 * Example 2:
 *
 * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
 * Output: false
 *
 * [2,null,3,null,4,null,5,null,6]
 *
 *
 *
 * Constraints:
 *
 *     m == matrix.length
 *     n == matrix[i].length
 *     1 <= n, m <= 300
 *     -10^9 <= matrix[i][j] <= 10^9
 *     All the integers in each row are sorted in ascending order.
 *     All the integers in each column are sorted in ascending order.
 *     -10^9 <= target <= 10^9
 */

public class P240Search2DMatrixII {

    public boolean searchMatrixOptimized(int[][] matrix, int target) {
        if (matrix == null) {
            return false;
        }

        int rows = matrix.length;
        int columns = matrix[0].length;
        int currentRow = rows - 1;
        int currentColumn = 0;

        while (currentRow >= 0 && currentColumn < columns) {
            int value = matrix[currentRow][currentColumn];
            if (value == target) {
                return true;
            }
            else if (value < target) {
                currentColumn++;
            }
            else {
                currentRow--;
            }
        }
        return false;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        boolean found = false;
        for (int i = 0; i < matrix.length && !found; i++) {
            int[] row = matrix[i];
            if(row[0] <= target || row[row.length-1] >= target){
                found= found | doBinarySearch(row,target);
            }
        }
        return found;
    }

    private boolean doBinarySearch(int[] row, int target) {
        int beg =0, end = row.length-1;
        if(beg==end){
            return row[beg] == target;
        }
        while (beg<end){
            int mid = beg + (end-beg)/2;
            if(row[mid] == target){
                return true;
            }else if(row[mid] > target) {
                end = mid;
            }else {
                beg =  mid + 1;
            }
        }
        return row[beg] == target;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]|5|true",
            "[[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]|13|true",
            "[[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]|18|true",
            "[[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]|23|true",
            "[[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]|20|false",
            "[[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]|40|false",
            "[[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]|1|true",
            "[[1,2,4]]|5|false",
            "[[1,2,4]]|0|false",
            "[[1,2,4]]|4|true",
            "[[4]]|4|true",
            "[[4]]|4|true",
            "[[5],[6]]|6|true"


    })
    void testHappy(String matrixStr, int target, boolean expected){
        int[][] matrix = Arrays.stream(matrixStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(row -> Arrays.stream(row.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assert.assertEquals(expected, searchMatrix(matrix, target));
    }
}
