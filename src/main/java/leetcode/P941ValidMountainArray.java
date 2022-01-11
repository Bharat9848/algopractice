package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an array of integers arr, return true if and only if it is a valid mountain array.
 *
 * Recall that arr is a mountain array if and only if:
 *
 *     arr.length >= 3
 *     There exists some i with 0 < i < arr.length - 1 such that:
 *         arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
 *         arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
 *
 *
 *
 * Example 1:
 *
 * Input: arr = [2,1]
 * Output: false
 *
 * Example 2:
 *
 * Input: arr = [3,5,5]
 * Output: false
 *
 * Example 3:
 *
 * Input: arr = [0,3,2,1]
 * Output: true
 *
 *
 *
 * Constraints:
 *
 *     1 <= arr.length <= 104
 *     0 <= arr[i] <= 104
 */
public class P941ValidMountainArray {
    public boolean validMountainArray(int[] arr) {
        if(arr.length<3){
            return false;
        }
        boolean peakReached = false;
        for (int i = 0; i < arr.length-1; i++) {
            if(arr[i] == arr[i+1]){
                return false;
            }
            if((!peakReached && arr[i] < arr[i+1])|| (peakReached && arr[i] > arr[i+1])){
                continue;
            }else if( i > 0 &&!peakReached && arr[i] > arr[i+1]){
                    peakReached = true;
            }else{
                return false;
            }

        }
        return peakReached;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2,1|false",
            "3,5,5|false",
            "0,3,2,1|true",
            "0,3,2,1,3,2|false",
            "3,2,1,0|false",
            "0,1,3|false",
            "0,1,3,0|true",
    })
    void test(String arrStr, boolean expected){
        int[] nums = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, validMountainArray(nums));
    }
}

