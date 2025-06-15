package matrix.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.Arrays;
import java.util.Optional;

/** Given a 0-indexed 1-dimensional (1D) integer array original and two integers, m and n, your task is to reshape the array into a 2-dimensional (2D) array with m rows and n columns while preserving the order of elements in original.

 To construct the 2D array, the first n elements in the original should populate the first row. The next n elements should populate the second row, and so on, until all rows are filled. Your goal is to return the resulting m x n 2D array.

 Note: If it is impossible to create an m x n array (e.g., if the total number of elements in the original is not equal to m * n), return an empty 2D array.

 Constraints:

 1<=1<= original.length <=103<=103

 1<=1<= original[i] <=103<=103

 1<=1<= m, n <=33<=33
 *
 */
/*
Jumping 1D array in m steps of n step each and copy n elements to new array
 */
public class Convert1DTo2D {
    public static int[][] construct2DArray(int[] original, int m, int n) {
        if(original.length != m * n){
            return new int[][]{};
        }
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            Optional<int[]> subArr = copyArray(original, i*n, n);
            if(subArr.isEmpty()){
                return new int [][] {};
            } else {
                result[i] = subArr.get();
            }
        }
        return result;
    }

    private static Optional<int[]> copyArray(int[] original, int startIndex, int size) {
        if(startIndex + size - 1 < original.length){
            var aubArr = new int[size];
            for (int i = 0; i < aubArr.length; i++) {
                aubArr[i] = original[i+startIndex];
            }
            return Optional.of(aubArr);

        } else {
            return Optional.empty();
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3|3|1|[[1],[2],[3]]",
            "1,2,3,4|2|2|[[1,2],[3,4]]",
            "1,2,3,4,5,6|2|3|[[1,2,3],[4,5,6]]",
            "1,2,3,4,5,6|3|3|",
    })
    void test(String oneArr, int m, int n, String expectedStr){
        int[][] expected = expectedStr == null ? new int[][]{} : StringParser.parseIntArrayString(expectedStr, "\\[(\\d,)*\\d\\]");
        Assertions.assertArrayEquals(expected, construct2DArray(Arrays.stream(oneArr.split(",")).mapToInt(Integer::parseInt).toArray(), m, n));
    }
}
