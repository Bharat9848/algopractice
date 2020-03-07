package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/*
A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

How many possible unique paths are there?

Example 1:

Input: m = 3, n = 2
Output: 3
Explanation:
From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
1. Right -> Right -> Down
2. Right -> Down -> Right
3. Down -> Right -> Right

Example 2:

Input: m = 7, n = 3
Output: 28

Algo :
Recursively to reach m*n cell it will be sum no of ways to reach m-1*n cell + m*n-1 cell
since a Random cell in the middle can be reached from left and up so there is repeated recursion.
Thus we can solve the problem using Dynamic programming
 */
 public class P62UniquePath {
     int uniquePaths(int m, int n) {
        int col = m,row =n;
        int[][] aux = new int[row][col];
        Arrays.fill(aux[0], 1);
        Arrays.stream(aux).forEach(r -> r[0] = 1);
         for (int i = 1; i < aux.length; i++) {
             for (int j = 1; j < aux[0].length; j++) {
                 aux[i][j] = aux[i-1][j] + aux[i][j-1];
             }
         }
        return aux[row-1][col-1];
    }

    @ParameterizedTest
    @CsvSource({"3,2,3","1,1,1", "2,1,1" , "1,2,1", "2,2,2","7,3,28"})
    void test(String col, String row, String sol){
        Assert.assertEquals(uniquePaths(Integer.valueOf(col), Integer.valueOf(row)), Integer.valueOf(sol).intValue());
    }
}
