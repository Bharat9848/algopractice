package leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

 Integers in each row are sorted from left to right.
 The first integer of each row is greater than the last integer of the previous row.
 For example,

 Consider the following matrix:

 [
 [1,   3,  5,  7],
 [10, 11, 16, 20],
 [23, 30, 34, 50]
 ]
 Given target = 3, return true.
 * Created by bharat on 7/4/18.
 */
public class P76Search2DMatrix {

    @Test
    public void test(){
        P76Search2DMatrix x = new P76Search2DMatrix();
        Assert.assertTrue(x.searchMatrix(new int[][]{{1,3,5,7},{10,11,16,20},{23,30,34,50}},3));
        Assert.assertFalse(x.searchMatrix(new int[][]{{1,3,5,7},{10,11,16,20},{23,30,34,50}},70));
        Assert.assertFalse(x.searchMatrix(new int[][]{{1,3,5,7},{10,11,16,20},{23,30,34,50}},0));
        Assert.assertTrue(x.searchMatrix(new int[][]{{1,3,5,7},{10,11,16,20},{23,30,34,50}},16));
        Assert.assertFalse(x.searchMatrix(new int[0][0],16));
        Assert.assertFalse(x.searchMatrix(new int[][]{{}},16));

    }

    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix==null ||matrix.length<=0||matrix[0].length<=0){
            return false;
        }
        int[] col0 = new int[matrix.length];
        for(int i=0; i<matrix.length;i++){
            col0[i] = matrix[i][0];
        }
        int row = binarySearchRowIndex(col0,target);
        if(row == -1){
            return false;
        }
        return binarySearchRow(matrix[row],target);
    }

    private boolean binarySearchRow(int[] matrix, int target) {
        int beg =0, end = matrix.length -1;
        if(matrix[0]>target){
            return false;
        }
        if(matrix[matrix.length-1]< target){
            return false;
        }
        while (beg<=end){
            int mid = beg + (end - beg)/2;
            if (matrix[mid]==target){
                return true;
            }else if(matrix[mid]<target){
                beg = beg + 1;
            }else{
                end = end - 1;
            }
        }

        return false;
    }

    private int binarySearchRowIndex(int[] col0, int target) {
        if (col0[0] > target) {
            return -1;
        }
        if (col0[col0.length - 1] < target) {
            return col0.length - 1;
        }
        int beg = 0, end = col0.length - 1;
        while (beg <= end) {
            int mid = beg + (end - beg) / 2;
            if (col0[mid] == target) {
                return mid;
            } else if (col0[mid] < target) {
                beg = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return beg - 1;
    }

}
