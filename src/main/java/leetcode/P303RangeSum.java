package leetcode;

import org.junit.Assert;
import org.junit.Test;

public class P303RangeSum {
    class NumArray {

        int[] arr;

        public NumArray(int[] nums) {
            arr = nums;
        }

        public int sumRange(int i, int j) {
            int sum =0;
            for(int k =i ; k<=j; k++){
                sum += arr[k];
            }
            return sum;
        }
    }

    @Test
    public void test(){
        Assert.assertEquals(new NumArray(new int[]{1,2,3,4}).sumRange(0,2),6);
    }
}
