package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

class P221MaximalSquare {
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0,0,0,1],[1,1,0,1],[1,1,1,1],[0,1,1,1],[0,1,1,1]]|9",
            "[[0,1],[1,0]]|1",
            "[[1,0,1,0,0],[1,0,1,1,1],[1,1,1,1,1],[1,0,0,1,0]]|4",
            "[[1,0,1],[1,0,1]]|1",
            "[[1,1,1],[1,1,1]]|4",
            "[[1,1,1],[1,1,1],[1,1,1]]|9",
            "[[0,0,0,0],[0,1,1,1],[0,1,1,1],[0,1,1,1]]|9",

            "[[0]]|0",
    })
    void test(String matrixStr, int expected){
        char[][] matrix = Arrays.stream(matrixStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] entries = s.split(",");
                    char[] chars = new char[entries.length];
                    for (int i = 0; i < entries.length; i++) {
                        String cell = entries[i];
                        if ("1".equals(cell)) {
                            chars[i] = '1';
                        } else {
                            chars[i] = '0';
                        }
                    }
                    return chars;
                }).toArray(char[][]::new);
        Assert.assertEquals(expected, maximalSquare(matrix));
    }

    int maximalSquare(char[][] matrix) {
        int result = 0;
        int[][] length = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == '1'){
                    int diagonalLen = (i - 1 >= 0 && j - 1 >= 0) ? length[i - 1][j - 1] : 0;
                    int leftWall = (i - 1 >= 0) ? length[i - 1][j] : 0;
                    int rightWall = (j - 1 >= 0) ? length[i][j - 1] : 0;
                    int squareLength = Math.min(diagonalLen, Math.min(leftWall, rightWall)) + 1;
                    length[i][j] = squareLength;
                    result = Math.max(squareLength * squareLength, result);
                }
            }
        }
        return result;
    }

    int maximalSquareTLE(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] maxSquare = new int[m][n];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = matrix[i][j] == '1' ? 1 : 0;
                max = Math.max(val, max);
                maxSquare[i][j] = val;
            }
        }
        for (int len = 1; len < (Math.min(m, n)); len++) {
            for (int i = m-len-1; i >=0; i--) {
                for (int j = n-len-1; j >= 0;j--) {
                    int row = i+len;
                    int col = j+len;
                    if(matrix[row][col] == '1'){
                        if(maxSquare[row-1][col-1] == 0){
                            maxSquare[row][col] = 1;
                        }else{
                            int maxSquareLength =  (int)Math.sqrt(maxSquare[row-1][col-1]);
                            boolean oneAlongLength = true;
                            for (int k = 1; k <= len && oneAlongLength; k++) {
                                if(matrix[row-k][col] != '1' || matrix[row][col-k] != '1'){
                                    oneAlongLength=false;
                                }
                            }
                            if(oneAlongLength){
                                maxSquare[row][col] = (maxSquareLength+1)* (maxSquareLength+1);
                            }else{
                                maxSquare[row][col] = 1;
                            }
                        }
                    }else{
                        maxSquare[row][col] = 0;
                    }
                    max = Math.max(maxSquare[row][col], max);
                }
            }
//            for (int i = 0; i < maxSquare.length; i++) {
//                System.out.println(Arrays.toString(maxSquare[i]));
//            }
        }
        return max;
    }
}
