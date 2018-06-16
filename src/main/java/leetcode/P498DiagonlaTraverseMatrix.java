package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/*
Given a matrix of M x N elements (M rows, N columns), return all elements of the matrix in diagonal order as shown in the below image.

Example:
Input:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
Output:  [1,2,4,7,5,3,6,8,9]

Trick all the downward index sum have remainder 2 factor is zero o.No while nested loop is needed
 */
public class P498DiagonlaTraverseMatrix {
    public int[] findDiagonalOrder(int[][] matrix) {
        if(matrix.length == 0 || matrix[0].length==0) return new int[0];
        int[] result = new int[matrix.length * matrix[0].length];
        int ri=0;
        int r = 0,c=0;
        boolean diagUp = true;

        while(ri < matrix[0].length* matrix.length){
           while(diagUp){
               if(r >= 0 && c < matrix[0].length){
                 result[ri] = matrix[r][c];
                 ri++;
                 r  = r - 1;
                 c = c + 1;
               }else{
                   diagUp = false;
                   if(c == matrix[0].length){
                       c = matrix[0].length-1;
                       r = r + 2;
                   }else{
                       r = 0;
                   }
               }
           }
           while (!diagUp){
               if(c >=0 && r < matrix.length){
                   result[ri] = matrix[r][c];
                   ri++;
                   r = r + 1;
                   c = c - 1;
               }else{
                   diagUp = true;
                   if(r == matrix.length){
                       r = matrix.length -1;
                       c = c + 2;
                   }
                   else {
                      c = 0;
                   }
               }
           }
        }
        return result;
    }
    @Test
    public void test(){
        Assert.assertEquals(Arrays.toString(new int[]{1,2,4,7,5,3,6,8,9}), Arrays.toString(findDiagonalOrder(new int[][]{{1,2,3},{4,5,6},{7,8,9}})));
        Assert.assertEquals(Arrays.toString(new int[]{1,2,3}), Arrays.toString(findDiagonalOrder(new int[][]{{1,2,3}})));
        Assert.assertEquals(Arrays.toString(new int[]{1,2,4,5,3,6}), Arrays.toString(findDiagonalOrder(new int[][]{{1,2,3},{4,5,6}})));
    }
}
