package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 *
 *     Integers in each row are sorted from left to right.
 *     The first integer of each row is greater than the last integer of the previous row.
 * Example 1:
 *
 * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 * Output: true
 *
 * Example 2:
 *
 * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
 * Output: false
 *
 *
 *
 * Constraints:
 *
 *     m == matrix.length
 *     n == matrix[i].length
 *     1 <= m, n <= 100
 *     -10^4 <= matrix[i][j], target <= 10^4
 *
 * If all the rows are flattened then the it become very simple problem of searching in a single sorted array. i will try to use give sequential numbering to each cell. and calculate cells row and col on the fly and try to mimic search in a single array
 */
class P74SearchIn2DMatrix {
    public boolean searchMatrix(int[][] matrix, int target){
        int nRow = matrix.length, ncol= matrix[0].length;
        int beg = 0, end = nRow*ncol - 1;
        if(beg==end){
            return matrix[beg][beg] == target;
        }
        while (beg<end){
            int mid= beg+((end-beg)/2);
            int rowMid = mid/ncol;
            int colMid = mid - rowMid*ncol;
            if(target == matrix[rowMid][colMid]){
                return true;
            }else if(target > matrix[rowMid][colMid]){
                beg = mid+1;
            }else {
                end = mid;
            }
        }
        int begRow = beg/ncol;
        int begCol = beg-begRow*ncol;
        return matrix[begRow][begCol] == target;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,3,5,7],[10,11,16,20],[23,30,34,60]]|5|true",
            "[[1,3,5,7],[10,11,16,20],[23,30,34,60]]|20|true",
            "[[1,3,5,7],[10,11,16,20],[23,30,34,60]]|40|false",
            "[[1,3,5,7],[10,11,16,20],[23,30,34,60]]|0|false",
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

