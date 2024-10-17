package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.
 * <p>
 * Return the max sliding window.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 * <p>
 * Example 2:
 * <p>
 * Input: nums = [1], k = 1
 * Output: [1]
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * 1 <= k <= nums.length
 */
public class P239MaxSlidingWindowError {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        int[] result = new int[nums.length - k + 1];
        LinkedList<Integer> window = new LinkedList<>();
        int max = -1, secondMax = -1;
        max = nums[0] > nums[1] ? 0 : 1;
        secondMax = nums[0] > nums[1] ? 1 : 0;

        for (int i = 2; i < k; i++) {
            if (nums[i] >= nums[max]) {
                max = i;
            } else if (nums[i] >= nums[secondMax]) {
                secondMax = i;
            }
        }
        if (max < secondMax) {
            window.add(max);
            window.add(secondMax);
        } else {
            window.add(secondMax);
            window.add(max);
        }
        result[0] = nums[max];
        int resultIndex = 1;
        for (int i = k; i < nums.length; i++) {
            int first = window.getFirst();
            int second = window.getLast();
            if (first == i - k) {
                window.remove(0);

                if (nums[i] > nums[second]) {
                    max = i;
                } else {
                    max = second;
                }

                window.addLast(i);
            } else {
                max = maintainWindow(window, nums, i);
            }
            result[resultIndex] = nums[max];
            resultIndex++;
        }
        return result;
    }

    private int maintainWindow(LinkedList<Integer> window, int[] nums, int i) {
        int first = window.removeFirst();
        int second = window.removeFirst();
        if (nums[i] >= nums[first] && nums[i] >= nums[second]) {
            window.add(i);
            if (nums[second] >= nums[first]) {
                window.add(second);
            } else {
                window.add(first);
            }
            return i;
        } else if (nums[first] > nums[i] && nums[first] > nums[second]) {
            window.add(first);
            if (nums[i] >= nums[second]) {
                window.add(i);
            } else {
                window.add(second);
            }
            return first;
        } else {
            if (nums[i] >= nums[first]) {
                window.add(second);
                window.add(i);
            } else {
                window.add(first);
                window.add(second);
            }
            return second;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,3,-1,-3,5,3,6,7|3|3,3,5,5,6,7",
            "1|1|1",
            "10,2,3,20,11,21|4|20,20,21",
            "9,8,9,8|3|9,9",
            "1,-1|1|1,-1",
            "7,2,4|2|7,4",
            "1,2,3,4,5|1|1,2,3,4,5",
            "-7,-8,7,5,7,1,6,0|4|7,7,7,7,7",
            "9,10,9,-7,-4,-8,2,-6|5|10,10,9,2"
    })
    public void test(String numsStr, int k, String expectedStr) {
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(expected, maxSlidingWindow(nums, k));
    }


}