package matrix.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.*;

/**
 * Given a matrix, mat, if any element within the matrix is zero, set that row and column to zero.
 *
 * Constraints:
 *
 *     1≤1≤ mat.row, mat.col ≤20≤20
 *     −231≤−231≤ mat[i][j] ≤231≤231
 */

/**
 * todo do it in O(mn) time
 */
public class SetMatrixZeros{
    public static int[][] setMatrixZeros(int[][] mat) {
        Set<Integer> zeroRow = new HashSet<>();
        Set<Integer> zeroCol = new HashSet<>();
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[0].length; j++){
                if(mat[i][j] == 0 && !(zeroRow.contains(i) || zeroCol.contains(j))){
                    turnRowColZeroRec(mat, i, j, zeroRow, zeroCol);
                }
            }
        }
        return mat;
    }
    private static void turnRowColZeroRec(int[][] mat, int i, int j, Set<Integer> zeroRow, Set<Integer> zeroCol){
        if(!zeroRow.contains(i)){
            zeroRow.add(i);
            for(int r = 0; r < mat[0].length; r++){
                if(r != j){
                    if(mat[i][r] == 0){
                        turnRowColZeroRec(mat, i, r,  zeroRow, zeroCol);
                    } else {
                        mat[i][r] = 0;
                    }
                }
            }
        }
        if(!zeroCol.contains(j)){
            zeroCol.add(j);
            for(int c = 0; c < mat.length; c++){
                if(c != i){
                    if(mat[c][j] == 0){
                        turnRowColZeroRec(mat, c, j, zeroRow, zeroCol);
                    } else {
                        mat[c][j] = 0;
                    }

                }

            }

        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "[[1,2,3],[1,0,4]]|[[1,0,3],[0,0,0]]",
            "[[1,2,3],[1,1,1,],[0,1,2],[1,3,5],[0,1,0]]|[[0,2,0],[0,1,0],[0,1,0],[0,3,0],[0,0,0]]"
    })
    void test(String matStr, String expectedStr){
        int[][] mat = StringParser.parseIntArrayString(matStr, "\\[(\\d+),\\d+\\]");
        int[][] expected = StringParser.parseIntArrayString(expectedStr, "\\[(\\d+),\\d+\\]");
        Assertions.assertArrayEquals(expected, setMatrixZeros(mat));
    }

}
