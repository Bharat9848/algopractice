package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/*
* Gray code is a binary code where each successive value differ in only one bit, as well as when wrapping around. Gray code is common in hardware so that we don't see temporary spurious values during transitions.Given a number of bits n, generate a possible gray code for it. For example, for n = 2, one gray code would be [00, 01, 11, 10].
* */
public class P89GrayCode {

    int[] grayBits(int noOfBits){
        int[] lastList = new int[]{0b0, 0b1};
        int bit = 2;
        int[] nextList=null;
        boolean first0 = true;
        while (noOfBits>1 && bit <= noOfBits){
            nextList = new int[(int)Math.pow(2, bit)];
            for (int i = 0; i < lastList.length; i++) {
                int no = lastList[i];
                if(first0){
                    nextList[2*i] = no<<1;
                    nextList[2*i+1] = (no<<1) + 1;
                }else {
                    nextList[2*i] = (no<<1) + 1;
                    nextList[2*i+1] = (no<<1);
                }
                first0 = !first0;
            }
            lastList = nextList;
            bit++;
        }
        return lastList;
    }

    @Test
    public void test(){
        Assert.assertTrue(bitsDifferByOne(grayBits(1)));
        Assert.assertTrue(bitsDifferByOne(grayBits(2)));
        Assert.assertTrue(bitsDifferByOne(grayBits(3)));
        Assert.assertTrue(bitsDifferByOne(grayBits(4)));
    }

    private boolean bitsDifferByOne(int[] numbers) {
        System.out.println(Arrays.toString(Arrays.stream(numbers).mapToObj(Integer::toBinaryString).toArray()));
        boolean result = true;
        for (int i = 0; i < numbers.length - 1; i++) {
            int no = numbers[i] ^ numbers[i + 1];
            result = result && (countTheSetBits(no) == 1);
        }
        return result;
    }

    private int countTheSetBits(int no) {
        int temp = no;
        int count = 0;
        while (temp>0){
           if((temp & 1) == 1){
               count++;
           }
           temp = temp>>1;
        }
        return count;
    }
}
