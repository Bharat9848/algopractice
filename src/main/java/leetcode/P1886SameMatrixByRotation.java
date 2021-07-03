package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.PrintUtil;

import java.util.Arrays;

/**
 * Given two n x n binary matrices mat and target, return true if it is possible to make mat equal to target by rotating mat in 90-degree increments, or false otherwise.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: mat = [[0,1],[1,0]], target = [[1,0],[0,1]]
 * Output: true
 * Explanation: We can rotate mat 90 degrees clockwise to make mat equal target.
 * <p>
 * Example 2:
 * <p>
 * Input: mat = [[0,1],[1,1]], target = [[1,0],[0,1]]
 * Output: false
 * Explanation: It is impossible to make mat equal to target by rotating mat.
 * <p>
 * Example 3:
 * <p>
 * Input: mat = [[0,0,0],[0,1,0],[1,1,1]], target = [[1,1,1],[0,1,0],[0,0,0]]
 * Output: true
 * Explanation: We can rotate mat 90 degrees clockwise two times to make mat equal target.
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * n == mat.length == target.length
 * n == mat[i].length == target[i].length
 * 1 <= n <= 10
 * mat[i][j] and target[i][j] are either 0 or 1.
 */
 class P1886SameMatrixByRotation {
     boolean findRotation(int[][] mat, int[][] target) {
        System.out.printf("%s\n\n", PrintUtil.printArray(mat));
        int[][] mat90 = rotateMatrix(mat);
        if (matEquals(mat90, target)) return true;
        int[][] mat180 = rotateMatrix(mat90);
        if (matEquals(mat180, target)) return true;
        int[][] mat270 = rotateMatrix(mat180);
        if (matEquals(mat270, target)) return true;
        int[][] mat360 = rotateMatrix(mat270);
        if (matEquals(mat360, target)) return true;
        return matEquals(mat, target);
    }

    private boolean matEquals(int[][] source, int[][] target) {
        boolean equals = true;
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[i].length; j++) {
                equals = equals && (source[i][j] == target[i][j]);
            }
        }
        return equals;
    }

    /*
    0123 rad = 3  newC = c + rad

     */
    private int[][] rotateMatrix(int[][] mat) {
        int length = mat.length;
        int[][] rMat = new int[mat.length][mat[0].length];
        int startR = 0, startC = 0;
        while (length > 0) {
            if (length == 1) {
                rMat[startR][startC] = mat[startR][startC];
            } else if (length == 2) {
                rMat[startR][startC] = mat[startR + 1][startC];
                rMat[startR][startC + 1] = mat[startR][startC];
                rMat[startR + 1][startC + 1] = mat[startR][startC + 1];
                rMat[startR + 1][startC] = mat[startR + 1][startC + 1];
            } else {
                for (int i = 0; i < length; i++) {
                    int c = startC + i;
                    int newC = startC + length - 1; //0,0 => 0,3  0,1 => 1,3 0,2=>,3 0,0 => 0,1
                    int newR = startR + (c + length) - newC - 1;
                    rMat[newR][newC] = mat[startR][c];
                }
                startC = startC + length - 1;
                for (int i = 0; i < length; i++) { //1,2 => 2,1 // 22 => 22
                    int r = startR + i;
                    int newR = startR + length - 1;
                    int newC = startC - ((r + length - 1) - newR);
                    rMat[newR][newC] = mat[r][startC];
                }
                startR = startR + length - 1;
                for (int i = 0; i < length; i++) {
                    int c = startC - i;
                    int newC = startC - length + 1; // 3,3 => 3,0 3,2 => 2,0 3,1 => 1,0
                    int newR = startR - Math.abs(c - newC - length + 1);
                    rMat[newR][newC] = mat[startR][c];
                }
                startC = startC - length + 1;
                for (int i = 0; i < length; i++) {
                    int r = startR - i;
                    int newR = startR - length + 1; // 3,0 =>0,0 2,0 => 0,1
                    int newC = startC + (Math.abs(r-newR - length + 1));
                    rMat[newR][newC] = mat[r][startC];
                }
            }

            startR = startR - length + 1;
            startR = startR + 1;
            startC = startC + 1;
            length = length - 2;
        }
        System.out.printf("%s\n\n",  PrintUtil.printArray(rMat));
        return rMat;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
//            "[0,1],[1,1];[1,0],[0,1];false",
//            "[0,0,0],[0,1,0],[1,1,1];[1,1,1],[0,1,0],[0,0,0];true"
//            "[1,0,0],[1,1,0],[1,0,0];[1,1,1],[0,1,0],[0,0,0];true",
            "[0,1,1,0,0],[1,0,1,1,1],[0,1,0,0,0],[1,1,1,1,0],[0,1,1,1,1]];[0,1,0,0,1],[0,1,0,1,1],[1,1,0,1,1],[1,0,1,1,1],[0,1,0,1,0];true"
//            "[0,1],[1,0];[1,0],[0,1];true"
    })
    void test(String source, String dest, boolean expected) {
        Assert.assertEquals(expected, findRotation(createMatrix(source), createMatrix(dest)));
    }

    private int[][] createMatrix(String source) {
        String[] rowStr = source.split("],");
        int[][] graph = new int[rowStr.length][];
        for (int i = 0; i < rowStr.length; i++) {
            String trimedRow = rowStr[i].replace("[", "").replace("]", "");
            if (trimedRow.isEmpty()) {
                graph[i] = new int[0];
            } else {
                graph[i] = Arrays.stream(trimedRow.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }
        }
        return graph;
    }

    /*
        00 01 02     20 10 00   c+2  2=0,3=1,4=2 r+2 = 2,3,4
        10 11 12     21 11 01   c  r=2,1,0 r-2 c=0 -1 -2
        20 21 22     22 12 02
     */
}
