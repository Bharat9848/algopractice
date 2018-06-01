package leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

 Example:

 Input:
 [
 ["1","0","1","0","0"],
 ["1","0","1","1","1"],
 ["1","1","1","1","1"],
 ["1","0","0","1","0"]
 ]
 Output: 6
 * Created by bharat on 26/5/18.
 */
public class P85MaximumReactangleTLE {
    public int maximalRectangle(char[][] matrix) {
        if(matrix.length==0||matrix[0].length==0){return 0;}
        int area = 0;
        int maxLength=matrix.length, maxWidth= matrix[0].length;
        for (int i = maxLength; i >=0 ; i--) {
            for (int j = maxWidth; j >=0 ; j--) {
                for(int r = 0; r+i-1 < matrix.length;r++){
                    for (int c = 0; c+j-1 < matrix[0].length; c++) {
                        if(i*j>area && allAreOne(r,r+i-1,c,c+j-1,matrix)) {
                            area= i * j;
                        }
                    }
                }
            }
        }
        return area;
    }


    private boolean allAreOne(int r, int r1, int c, int c1, char[][] matrix) {
        for (int i = r; i <=r1 ; i++) {
            for (int j = c; j <=c1 ; j++) {
                if(matrix[i][j]=='0'){
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void test(){
        Assert.assertEquals(12, maximalRectangle(new char[][]{{'1','0','1','1','1'},
                {'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','1','1','1'}}));
        Assert.assertEquals(12, maximalRectangle(new char[0][]));
    }
}
