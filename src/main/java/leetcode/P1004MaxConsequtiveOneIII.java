package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip at most k 0's.
 *
 * Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
 * Output: 6
 * Explanation: [1,1,1,0,0,1,1,1,1,1,1]
 * Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
 *
 * Example 2:
 *
 * Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3
 * Output: 10
 * Explanation: [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
 * Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
 *
 *000000
 *122222
 *
 * 0010000
 *
 * 1 = 1
 * 2 = 11
 * 3 = 111
 * 3 = 0111 - fliped count = 0 if the leftmost element in last seq is 1  then recount from index else use the same count
 * 3 = 00111 fliped count = 0 same logic as above
 * 2 = 0010110
 * 2 = 0010011
 *
 * solution can be for sol(n)
 *  = sol(n-1) +1 if arr[n-1] = 1
 *  = sol(n-1) +1 if arr[n-1] = 0 and fCount>0
 *  = sol(n-1) if n-1-(sol(n-1)-1) is fliped 1
 *  = do reverse calculate
 * first index - init
 * second index
 * Constraints:
 *
 *     1 <= nums.length <= 105
 *     nums[i] is either 0 or 1.
 *     0 <= k <= nums.length
 */
public class P1004MaxConsequtiveOneIII {
    public int longestOnes(int[] nums, int k) {
        int flips =  k;
        int[] longOnes = new int[nums.length];
        if(nums[0] == 0 ){
            if(flips > 0){
                longOnes[0] = 1;
                flips--;
            }else {
                longOnes[0] = 0;
            }
        } else {
            longOnes[0] = 1;
        }
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            if(num == 1){
                longOnes[i] = longOnes[i-1] + 1;
            }else if (num == 0 && flips > 0){
                longOnes[i] = longOnes[i-1] +1;
                flips--;
            } else {
                int firstOneIndexFromLastSeq = (i-1)-(longOnes[i-1]-1);
                if(nums[firstOneIndexFromLastSeq] == 0){
                    longOnes[i] = longOnes[i-1];
                }else{
                    flips = k;
                    int count = 0;
                    int index = i;
                    while (index > 0){
                        if(nums[index] == 1){
                            count++;
                        }else if(nums[index] == 0 && flips > 0){
                            count++;
                            flips--;
                        } else{
                            break;
                        }
                        index--;
                    }
                    longOnes[i] = count;
                }
            }
        }
        System.out.println(Arrays.toString(longOnes));
        return Arrays.stream(longOnes).max().orElseThrow();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"1,1,1,0,0,0,1,1,1,1,0|2|6",
            "0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1|3|10",
            "0,0,0,0,0|2|2",
            "0,0,0,0|0|0",
            "1,1,1,1|0|4",
            "0,0,1,0,0,0,0|2|3"
    })
    void test(String arrStr, int flipCount, int expected){
        int[] arr = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, longestOnes(arr, flipCount));
    }
}
