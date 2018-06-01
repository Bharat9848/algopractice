package leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Write a function that takes an unsigned integer and returns the number of ’1' bits it has (also known as the Hamming weight).

 For example, the 32-bit integer ’11' has binary representation 00000000000000000000000000001011, so the function should return 3.
 * Created by bharat on 17/4/18.
 */
public class P191NumberOf1Bit {
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n = n & (n - 1);
            count++;
        }
        return count;
    }

    @Test
    public void test(){
        Assert.assertEquals(2,hammingWeight(3));
        Assert.assertEquals(3,hammingWeight(11));
        Assert.assertEquals(4,hammingWeight(15));
    }
}
