package matrix.must;

/**
 * Given an n×n matrix, rotate the matrix 90 degrees clockwise. The performed rotation should be in place, i.e., the given matrix is modified directly without allocating another matrix.
 *
 *     Note: The function should only return the modified input matrix.
 *
 * Constraints:
 *
 *     matrix.length == matrix[i].length
 *     1≤1≤ matrix.length ≤20≤20
 *     −103≤−103≤ matrix[i][j] ≤103≤103
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.Arrays;

/**
 * [[a,b,c],[d,e,f],[g,h,i] ==>  [g,h,i][e.b][f,c]
 */

/**
 * todo redo
 */
public class RotateImage {

    public static int[][] rotateImage(int[][] matrix) {
        int n = matrix.length;
        int[] tempArray = new int[n];
        for (int i = 0; i < n; i++) {
            tempArray[i] = matrix[0][i];
        }
        int currentSize = n, row = 0, col=0;
        while(currentSize > 1){
            for(int i = 0; i < currentSize-1; i++){
                swapEles(matrix, row, col + i, currentSize, row + currentSize, col + currentSize, row, col);
            }
            row++;
            col=row;
            currentSize =  currentSize-2;
        }
        return matrix;
    }

    private static void swapEles(int[][] matrix, int row, int col, int currentSize, int rowHigherBoundary, int colHighBoundary, int rowLowerBoundary, int colLowerBoundary) {
        int jump = currentSize-1;
        int current = -1;
        for(int i = 0; i < 4; i++){
            if(i == 0){
              current = matrix[row][col];
              if(col + jump < colHighBoundary){
                  col = col + jump;
              } else {
                  row = row + (col + jump - colHighBoundary+1);
                  col = colHighBoundary-1;
              }
              int temp = matrix[row][col];
              matrix[row][col] = current;
              current = temp;
            } else if(i == 1) {
                if(row + jump < rowHigherBoundary){
                    row = row + jump;
                } else {
                    col = colHighBoundary - 1 - (row + jump - rowHigherBoundary +1);
                    row = rowHigherBoundary - 1;
                }
                int temp = matrix[row][col];
                matrix[row][col] = current;
                current = temp;
            } else if(i == 2){
                if(col-jump >= colLowerBoundary){
                    col = col - jump;
                } else {
                    row = row - (colLowerBoundary - col + jump );
                    col = colLowerBoundary;
                }
                int temp = matrix[row][col];
                matrix[row][col] = current;
                current = temp;
            } else {
                if(row - jump >= rowLowerBoundary){
                    row = row - jump;
                } else {
                    col = col + (rowLowerBoundary - row + jump);
                    row = rowLowerBoundary;
                }
                int temp = matrix[row][col];
                matrix[row][col] = current;
                current = temp;
            }

        }
        return;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[10,1,14,11,14],[13,4,8,2,13],[10,19,1,6,8],[20,10,8,2,12],[15,6,8,8,18]]|[[15,20,10,13,10],[6,10,19,4,1],[8,8,1,8,14],[8,2,6,2,11],[18,12,8,13,14]]",
            "[[6,7],[10,11]]|[[10,6],[11,7]]",
            "[[1,2,3],[4,5,6],[7,8,9]]|[[7,4,1],[8,5,2],[9,6,3]]",
            "[[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]]|[[13,9,5,1],[14,10,6,2],[15,11,7,3],[16,12,8,4]]"
    })
    void test(String matrixStr, String expectedStr){
        int[][] expected = StringParser.parseIntArrayString(expectedStr, "\\[(\\d+,)+(\\d+)\\]");
        int[][] matrix = StringParser.parseIntArrayString(matrixStr, "\\[(\\d+,)+(\\d+)\\]");
        int[][] actual = rotateImage(matrix);
        for(int i=0; i < matrix.length; i++){
            System.out.println(Arrays.toString(actual[i]));
        }
        Assertions.assertArrayEquals(expected, actual);
    }
}
