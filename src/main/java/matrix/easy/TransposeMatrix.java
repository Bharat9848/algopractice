package matrix.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

public class TransposeMatrix {
    public static int[][] transposeMatrix(int[][] matrix){
        int[][] result = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "[[1,2],[3,4]]|[[1,3],[2,4]]",
            "[[1],[3]]|[[1,3]]",
            "[[1,2]]|[[1],[2]]",
            "[[1]]|[[1]]"
    })
    void test(String matrixStr, String expectedStr){
        int[][] matrix = StringParser.parseIntArrayString(matrixStr, "\\[(\\d+,)*\\d+\\]");
        int[][] expected = StringParser.parseIntArrayString(expectedStr, "\\[(\\d+,)*\\d+\\]");
        Assertions.assertArrayEquals(expected, transposeMatrix(matrix));
    }
}
