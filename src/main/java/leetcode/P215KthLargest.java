package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 *
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5
 *
 * Example 2:
 *
 * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
 * Output: 4
 *
 *
 *
 * Constraints:
 *
 *     1 <= k <= nums.length <= 10^4
 *     -10^4 <= nums[i] <= 10^4
 */
public class P215KthLargest {
    int findKthLargest(int[] nums, int k){
        PriorityQueue<Integer> heap = new PriorityQueue<>(Integer::compareTo);
        for (int i = 0; i < k; i++) { //Initialize heap
            heap.offer(nums[i]);
        }

        for (int i = k; i < nums.length; i++) {
            if(nums[i] >= heap.peek()){
                heap.remove();
                heap.offer(nums[i]);
            }
        }
        return heap.peek();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3,2,1,5,6,4|2|5",
            "3,2,3,1,2,4,5,5,6|4|4"
    })
    void test(String arrStr, int k, int expected){
        int[] nums = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, findKthLargest(nums, k));
    }
}
