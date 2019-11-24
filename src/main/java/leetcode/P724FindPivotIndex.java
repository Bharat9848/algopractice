package leetcode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Given an array of integers nums, write a method that returns the "pivot" index of this array.

 We define the pivot index as the index where the sum of the numbers to the left of the index is equal to the sum of the numbers to the right of the index.

 If no such index exists, we should return -1. If there are multiple pivot indexes, you should return the left-most pivot index.
 */
public class P724FindPivotIndex {

    public int pivotIndex(int[] nums) {
        int sum =0;
        for (int i = 0; i < nums.length; i++) {
            sum+=nums[i];
        }
        int lSum =0;
        for (int i = 0; i < nums.length; i++) {
           if(lSum==sum-nums[i]-lSum){
               return i;
           }
           lSum += nums[i];
        }
       return -1;

    }

    @Test
    public void test(){
        assertEquals(-1, pivotIndex(new int[]{1,2,3}));
        assertEquals(1, pivotIndex(new int[]{3,2,3}));
        assertEquals(3, pivotIndex(new int[]{1, 7, 3, 6, 5, 6}));
        assertEquals(1, pivotIndex(new int[]{40, 7, 13, 7, 19, 1}));
        assertEquals(-1, pivotIndex(new int[]{40, 7}));
        assertEquals(2, pivotIndex(new int[]{-1, -1, -1,-1,-1,0}));
        assertEquals(1, pivotIndex(new int[]{-1, -1, -1,-1,1,0}));
        assertEquals(0, pivotIndex(new int[]{-1, -1, -1,0,1,1,0}));
    }
}
