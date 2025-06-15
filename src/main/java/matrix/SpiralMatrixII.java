package matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

/**
 * Given a positive integer n, create an n×nn×n matrix where the elements are sequential integers from 11 to n2n2, arranged in a spiral pattern.
 *
 * Constraints:
 *
 *     1 ≤≤ n ≤≤2020
 */
public class SpiralMatrixII {
    public static int[][] generateMatrix(int n)
    {
        int[][] result = new int[n][n];
        int current = 1;
        int noOfSteps = n-1;
        int startingRow = 0;
        int startingCol = 0;
        while(noOfSteps > 0){
            result[startingRow][startingCol] = current;
            current++;
            // horizontal
            for (int i = 1; i <= noOfSteps; i++) {
                result[startingRow][startingCol+i] = current;
                current++;
            }
            // downward
            for (int i = 1; i <= noOfSteps; i++) {
                result[startingRow + i][startingCol+noOfSteps] = current;
                current++;
            }
            // right to left
            for (int i = 1; i <= noOfSteps; i++) {
                result[startingRow+noOfSteps][startingCol+ noOfSteps -i] = current;
                current++;
            }
            for(int i = 1; i <= noOfSteps-1; i++){
                result[startingRow+noOfSteps-i][startingCol] = current;
                current++;
            }
            startingRow++;
            startingCol++;
            noOfSteps-=2;
        }
        if( n%2 != 0){
            result[startingRow][startingCol] = current;
        }
        // Replace this placeholder return statement with your code
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|[[1]]",
            "2|[[1,2],[4,3]]",
            "3|[[1,2,3],[8,9,4],[7,6,5]]",
            "4|[[1,2,3,4][12,13,14,5],[11,16,15,6],[10,9,8,7]]"
    })
    void test(int n, String expectedStr){
        int[][] expected = StringParser.parseIntArrayString(expectedStr, "\\[(\\d+,)*\\d+\\]");
        Assertions.assertArrayEquals(expected, generateMatrix(n));
    }
}

