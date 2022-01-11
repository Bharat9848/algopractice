package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an array arr, replace every element in that array with the greatest element among the elements to its right, and replace the last element with -1.
 *
 * After doing so, return the array.
 *
 *
 *
 * Example 1:
 *
 * Input: arr = [17,18,5,4,6,1]
 * Output: [18,6,6,6,1,-1]
 * Explanation:
 * - index 0 --> the greatest element to the right of index 0 is index 1 (18).
 * - index 1 --> the greatest element to the right of index 1 is index 4 (6).
 * - index 2 --> the greatest element to the right of index 2 is index 4 (6).
 * - index 3 --> the greatest element to the right of index 3 is index 4 (6).
 * - index 4 --> the greatest element to the right of index 4 is index 5 (1).
 * - index 5 --> there are no elements to the right of index 5, so we put -1.
 *
 * Example 2:
 *
 * Input: arr = [400]
 * Output: [-1]
 * Explanation: There are no elements to the right of index 0.
 *
 *
 *
 * Constraints:
 *
 *     1 <= arr.length <= 104
 *     1 <= arr[i] <= 105
 */
public class P1299ReplaceWithGreatestEleOnRight {
    public int[] replaceElements(int[] arr) {
        if(arr==null) return null;
        int maxSoFar = arr[arr.length-1];
        arr[arr.length-1] = -1;
        for (int i = arr.length-2; i>=0; i--) {
            int temp = arr[i];
            arr[i] = maxSoFar;
            maxSoFar = Math.max(maxSoFar, temp);
        }
        return arr;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "17,18,5,4,6,1|18,6,6,6,1,-1",
            "400|-1"
    })
    void test(String arrStr, String expectedStr){
        int[] arr = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertArrayEquals(expected, replaceElements(arr));
    }
}
