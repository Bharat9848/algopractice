package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;

/**
 * You are given an integer num. You can swap two digits at most once to get the maximum valued number.
 *
 * Return the maximum valued number you can get.
 *
 *
 *
 * Example 1:
 *
 * Input: num = 2736
 * Output: 7236
 * Explanation: Swap the number 2 and the number 7.
 *
 * Example 2:
 *
 * Input: num = 9973
 * Output: 9973
 * Explanation: No swap.
 *
 *
 *
 * Constraints:
 *
 *     0 <= num <= 108
 */
public class P670MaximumSwap {
    public int maximumSwap(int num) {
        int[] arr = initArr(num);
        int firstMinimaIndex = -1;
        int difference = 0;
        for (int i = 0; i < arr.length; i++) {
            if(i+1 < arr.length && arr[i] >= arr[i+1]){
            } else if (i+1 == arr.length || arr[i+1] == -1) {
                continue;
            } else {
                firstMinimaIndex = i;
                difference = arr[i+1] - arr[i];
                break;
            }
        }
        if(firstMinimaIndex == -1){
            return num;
        }
        int highestIndex = firstMinimaIndex + 1;
        for (int i = firstMinimaIndex+2; i < arr.length ; i++) {
            if(arr[i] > arr[firstMinimaIndex] && arr[i] - arr[firstMinimaIndex] >= difference){
                difference = arr[i] - arr[firstMinimaIndex];
                highestIndex = i;
            }
        }
        int firstNoIndex = firstMinimaIndex;
        for (int i = 0; i < firstMinimaIndex; i++) {
            if(arr[i] >= arr[highestIndex]){} else {
                firstNoIndex = i;
                break;
            }
        }
        swapNum(arr, firstNoIndex, highestIndex);
        return createNum(arr);
    }

    private int createNum(int[] arr) {
        int num=0;
        for (int i = 0 ; i < arr.length; i++) {
            num = num * 10 + arr[i];
        }
        return num;
    }

    private void swapNum(int[] arr, int firstNoIndex, int highestIndex) {
        int temp = arr[highestIndex];
        arr[highestIndex] = arr[firstNoIndex];
        arr[firstNoIndex] = temp;
    }

    private int[] initArr(int num){
        int[] numArr = new int[9];
        int noOfDigits = 0;
        for (int i = 0; i < numArr.length && num > 0; i++) {
            int digit = num % 10;
            num = num/10;
            numArr[i] = digit;
            noOfDigits++;
        }
        int[] arr = new int[noOfDigits];
        for (int i = 0; i < noOfDigits; i++) {
            arr[i] = numArr[noOfDigits-1-i];
        }
        return arr;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1009|9001",
            "1115|5111",
            "1993|9913",
            "7384|8374",
            "987|987",
            "9314718|9814713",
            "1|1",
            "0|0"
    })
    public void test(int num, int expected) {
        Assertions.assertEquals(expected, maximumSwap(num));
    }
}
