package sliding.window.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Given an integer list, nums, find the maximum values in all the contiguous subarrays (windows) of size w.
 *
 * Constraints:
 *
 *     11 ≤≤ nums.length ≤≤ 103103
 *     −104−104 ≤≤ nums[i] ≤≤ 104104
 *     11 ≤≤ w ≤≤ nums.length
 */

/**
 *
 */
public class SlidingWindowMaximum {

    public static int[] findMaxSlidingWindow(int[] nums, int w) {
        int[] result = new int[nums.length-w+1];
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(w, (x1, x2) -> -(x1-x2));
        int left=0, right=0;
        int resultIndex = 0;
        for (right = 0; right < nums.length; right++) {
            maxHeap.offer(nums[right]);
           if(right - left + 1 > w){
                maxHeap.remove(nums[left]);
                left++;
           }
           if(right-left+1 == w){
               result[resultIndex] = maxHeap.peek();
               resultIndex++;
           }
        }
        return result;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {

            "-4,5,4,-4,4,6,7,20|2|5,5,4,4,6,7,20",
            "-4,5,4,-4,4,6,7,20|1|-4,5,4,-4,4,6,7,20",
    })
    void test(String numsStr, int k, String expectedStr){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(expected, findMaxSlidingWindow(nums, k));
    }
}
