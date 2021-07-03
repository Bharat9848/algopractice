package dynamic.programming;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * You are given an array of integers, where each element represents the maximum number of steps that can be jumped going forward from that element. Write a function to return the minimum number of jumps you must take in order to get from the start to the end of the array.
 * <p>
 * For example, given [6, 2, 4, 0, 5, 1, 1, 4, 2, 9], you should return 2, as the optimal solution involves jumping from 6 to 5, and then from 5 to 9
 */
public class MinStepToJumpToEndIndex {

    int minStepToLastIndex(int[] arr) {

        int[] minJumpArr = new int[arr.length];
        minJumpArr[0] = 0;
        IntStream.range(1, arr.length).forEach(i -> minJumpArr[i] = Integer.MAX_VALUE);
        for (int i = 0; i < arr.length; i++) {
            for (int jump = 1; jump <= arr[i]; jump++) {
                if (i + jump < arr.length && minJumpArr[i + jump] > minJumpArr[i] + 1) {
                    minJumpArr[i + jump] = minJumpArr[i] + 1;
                }
            }
        }
        return minJumpArr[arr.length - 1];
    }

    @ParameterizedTest
    @CsvSource(
            delimiter = ';', value = {
            "6,2,4,0,5,1,1,4,2,9;2",
            "1,1,3,1,1;3",
            "1,1,1,1,1;4"
    })
    void test(String arrStr, int expectedJump) {
        Assert.assertEquals(expectedJump, minStepToLastIndex(Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
