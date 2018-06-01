package leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given an array of integers A and let n to be its length.

 Assume Bk to be an array obtained by rotating the array A k positions clock-wise, we define a "rotation function" F on A as follow:

 F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1].

 Calculate the maximum value of F(0), F(1), ..., F(n-1).

 Note:
 n is guaranteed to be less than 105.

 Example:

 A = [4, 3, 2, 6]

 F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
 F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
 F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
 F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26

 So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
 * Created by bharat on 26/5/18.
 */
public class P396RotateFunction {
    public int maxRotateFunction(int[] A) {
        int sum = 0,total =0;
        for (int i = 0; i < A.length; i++) {
            sum+=i*A[i];
            total += A[i];
        }
        int max=sum;
        int lastSum = max;
        int lastIndex= A.length-1;
        for (int i = 1; i < A.length; i++) {
            int nexSum= calNextRotateSum(lastSum,lastIndex,A,total);
            if(max<nexSum){
                max = nexSum;
            }
            lastSum = nexSum;
            lastIndex = lastIndex-1;
        }
        return max;
    }


    int calNextRotateSum(int lastSum,int lastIndex,int[]A,int total){
        return lastSum +total-A.length*A[lastIndex];

    }

    @Test
    public void test(){
        Assert.assertEquals(26, maxRotateFunction(new int[]{4,3,2,6}));
    }
}
