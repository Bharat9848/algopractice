package leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.

 Given two integers x and y, calculate the Hamming distance.

 Note:
 0 ≤ x, y < 231.

 Example:

 Input: x = 1, y = 4

 Output: 2

 Explanation:
 1   (0 0 0 1)
 4   (0 1 0 0)
 ↑   ↑

 The above arrows point to positions where the corresponding bits are different.
 * Created by bharat on 9/6/18.
 */
public class P461HammingDistanceTwoNumber {
    public int hammingDistance(int x, int y) {
        int xor = x^y;
        int count = 0;
        while(xor!=0){
            count++;
            xor  = xor&(xor-1);
        }
        return count;
    }
    @Test
    public void test(){
        Assert.assertEquals(2, hammingDistance(8,4));
        Assert.assertEquals(24, hammingDistance(0x00FFFFFF,0));
    }

}
