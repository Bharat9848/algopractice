package leetcode;

import org.junit.Assert;
import org.junit.Test;

/*
Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

Range Sum Query 2D
The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.

Example:
Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
sumRegion(1, 1, 2, 2) -> 11
sumRegion(1, 2, 2, 4) -> 12
Note:
You may assume that the matrix does not change.
There are many calls to sumRegion function.
You may assume that row1 ≤ row2 and col1 ≤ col2.
 */
public class P304RangeQuery2D {
    class NumMatrix {
        int[][] arr;
        public NumMatrix(int[][] matrix) {
            arr = matrix;
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            int sum =0;
            for (int i = row1; i <= row2; i++) {
                for (int j = col1; j <= col2; j++) {
                    sum += arr[i][j];
                }
            }
            return sum;
        }
    }

    @Test
    public void test(){
        NumMatrix num = new NumMatrix(new int[][]{{3, 0, 1, 4, 2},{5, 6, 3, 2, 1}, {1, 2, 0, 1, 5},{4, 1, 0, 1, 7}, {1, 0, 3, 0, 5}});
        Assert.assertEquals(8, num.sumRegion(2, 1, 4, 3));
        Assert.assertEquals(11, num.sumRegion(1, 1, 2, 2));
        Assert.assertEquals(12, num.sumRegion(1, 2, 2, 4));
    }
}
