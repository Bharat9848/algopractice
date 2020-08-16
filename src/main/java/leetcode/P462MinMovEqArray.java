package leetcode;

import org.junit.Assert;
import org.junit.Test;
import sort.QuickSort;

/**
 * Given a non-empty integer array, find the minimum number of moves required to make all array elements equal, where a move is incrementing a selected element by 1 or decrementing a selected element by 1.

 You may assume the array's length is at most 10,000.

 Example:

 Input:
 [1,2,3]

 Output:
 2

 Explanation:
 Only two moves are needed (remember each move increments or decrements one element):

 [1,2,3]  =>  [2,2,3]  =>  [2,2,2]



 wrong answer 1 : I thought sum of distance between mean and all the numbers should be minimum.
 I


 * Created by bharat on 7/5/18.
 */
public class P462MinMovEqArray {
    public int minMoves2(int[] nums) {
       int[] sortedArr = QuickSort.quickSort(nums);
       int medianIndex = (sortedArr.length - 1)/2;
       int result = 0;
        for (int i = 0; i < sortedArr.length; i++) {
            int diff = sortedArr[i] - sortedArr[medianIndex];
            result =  result + ((diff > 0) ? diff : - diff);
        }
       return result;
    }

    @Test
    public void test1(){
        Assert.assertEquals(2,minMoves2(new int[]{1,2,3}));
        Assert.assertEquals(10,minMoves2(new int[]{12,10,2}));
        Assert.assertEquals(14,minMoves2(new int[]{1,0,0,8,6}));
    }
}
