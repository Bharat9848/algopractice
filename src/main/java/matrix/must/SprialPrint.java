package matrix.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an m×nm×n matrix, return an array containing the matrix elements in spiral order, starting from the top-left cell.
 *
 * Constraints:
 *
 *     1≤1≤ matrix.length ≤10≤10
 *     1≤1≤ matrix[i].length ≤10≤10
 *     −100≤−100≤ matrix[i][j] ≤100≤100
 */
public class SprialPrint {
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int rStart = 0, rEnd = matrix.length - 1, cStart = 0, cEnd = matrix[0].length - 1;
        while(rEnd >= rStart && cEnd >= cStart){
            for(int i = cStart; i <= cEnd; i++){
                result.add(matrix[rStart][i]);
            }
            rStart = rStart + 1;
            if(rStart > rEnd){
                break;
            }
            for(int i = rStart; i <= rEnd; i++){
                result.add(matrix[i][cEnd]);
            }
            cEnd = cEnd - 1;
            if(cStart > cEnd){
                break;
            }
            for (int i = cEnd; i >= cStart ; i--) {
                result.add(matrix[rEnd][i]);
            }
            rEnd = rEnd - 1;
            if(rStart > rEnd){
                break;
            }
            for(int i = rEnd; i >= rStart; i--){
                result.add(matrix[i][cStart]);
            }
            cStart = cStart + 1;
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[5,95,8,64,12]]|5,95,8,64,12",
            "[[1,2,3],[4,5,6],[7,8,9]]|1,2,3,6,9,8,7,4,5",
            "[[1]]|1",
            "[[1,2],[3,4]]|1,2,4,3"
    })
    void test(String matrixStr, String expectedStr){
        var expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).boxed().toList();
        Assertions.assertEquals(expected, spiralOrder(StringParser.parseIntArrayString(matrixStr, "\\[(\\d+,)*\\d+\\]")));
    }


}
