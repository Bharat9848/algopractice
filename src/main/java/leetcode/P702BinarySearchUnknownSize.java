package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * This is an interactive problem.
 *
 * You have a sorted array of unique elements and an unknown size. You do not have an access to the array but you can use the ArrayReader interface to access it. You can call ArrayReader.get(i) that:
 *
 *     returns the value at the ith index (0-indexed) of the secret array (i.e., secret[i]), or
 *     returns 231 - 1 if the i is out of the boundary of the array.
 *
 * You are also given an integer target.
 *
 * Return the index k of the hidden array where secret[k] == target or return -1 otherwise.
 *
 * You must write an algorithm with O(log n) runtime complexity.
 *
 * Input: secret = [-1,0,3,5,9,12], target = 9
 * Output: 4
 * Explanation: 9 exists in secret and its index is 4.
 *
 * Input: secret = [-1,0,3,5,9,12], target = 2
 * Output: -1
 * Explanation: 2 does not exist in secret so return -1.
 *
 * Constraints:
 *
 *     1 <= secret.length <= 104
 *     -104 <= secret[i], target <= 104
 *     secret is sorted in a strictly increasing order.
 */
public class P702BinarySearchUnknownSize {
    private interface ArrayReader {
        int get(int index);
    }
    private class MyArrayReader implements ArrayReader{
        int[] secret;
        public MyArrayReader(int[] arr){
            secret = arr;
        }

        @Override
        public int get(int index) {
            if(index >= secret.length){
                return Integer.MAX_VALUE;
            }
            return secret[index];
        }
    }
    public int search(ArrayReader reader, int target) {
        if(reader.get(0) == Integer.MAX_VALUE){
            return -1;
        }
        int maxExpSize = 4, expPow=0;
        int beg = 0;
        int end;
        while (reader.get((int)Math.pow(10, expPow)) != Integer.MAX_VALUE){
            expPow++;
        }
        end = (int)Math.pow(10, expPow);
        int end1 = (int)Math.pow(10, expPow-1), end2 = end;
        while(end1 < end2){
            int mid = end1 + ((end2-end1)/2);
            int midVal = reader.get(mid);
            if(midVal == Integer.MAX_VALUE){
                end2 = mid;
            }else {
                end1 = mid+1;
            }
        }
        end = reader.get(end1) == Integer.MAX_VALUE ? end1-1: end1;
        while (beg<end){
            int mid = beg +(end-beg)/2;
            if(reader.get(mid) == target){
                return mid;
            }else if (reader.get(mid) > target){
                end = mid;
            }else{
                beg = mid+1;
            }
        }

        return reader.get(beg) == target ? beg:-1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "-1,0,3,5,9,12|9|4|num exist",
            "-1,0,3,5,9,12|2|-1|Num not exist in between",
            "-1,0,3,5,9,12|-2|-1|Num not exist on left extreme",
            "-1,0,3,5,9,12|22|-1|Num not exist on right extreme",
    })
    void test(String arr, int target, int expected, String msg){
        System.out.println(msg);
        ArrayReader reader = new MyArrayReader(Arrays.stream(arr.split(",")).mapToInt(Integer::parseInt).toArray());
        Assertions.assertEquals(expected, search(reader, target));
    }
}
