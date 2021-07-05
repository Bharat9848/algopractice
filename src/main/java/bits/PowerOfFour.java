package bits;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a 32-bit positive integer N, determine whether it is a power of four in faster than O(log N) time.
    0101 0101 0101 0101 max 16 comparison
 */
class PowerOfFour {
    boolean isPowerOfFour(int no) {
        int temp = no;
        int noOfBits = 32;
        int[] masks= new int[]{0xFFFF, 0xFF, 0xF, 0x3};
        for (int i = noOfBits, j=0; i>2 ; i=i/2,j++) {
            int processBits = i/2;
            int right = temp & masks[j];
            int left1 = temp;
            int left = (left1 >> processBits) & masks[j];
            if(left>0 && right>0){
                return false;
            }else if(left > 0){
                temp = left;
            }else {
                temp = right;
            }
        }
        return temp==1;
    }

    @ParameterizedTest
    @CsvSource(
            delimiter = ';', value = {
            "4;true",
            "3;false",
            "16;true",
            "32;false",
            "234;false",
            "256;true",
            "1024;true",
            "1023;false",
            "1025;false",
            "4096;true",
            "16384;true",
            "65536;true",
            "262144;true",
            "1048576;true"
    })
    void test(int no, boolean expected) {
        Assert.assertEquals(expected, isPowerOfFour(no));
    }
}

