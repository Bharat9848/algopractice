package heap.two.heap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Given an integer array, nums, and an integer, k, there is a sliding window of size k, which is moving from the very left to the very right of the array. We can only see the k numbers in the window. Each time the sliding window moves right by one position.
 * <p>
 * Given this scenario, return the median of the each window. Answers within 10−510−5 of the actual value will be accepted.
 * <p>
 * Constraints:
 * <p>
 * 1≤ k ≤ nums.length ≤103
 * −231≤−231≤ nums[i] ≤231−1≤231−1
 * todo educative
 */
public class SlidingWindowMedian {
    public static double[] medianSlidingWindow(int[] nums, int k) {
        return new double[]{};
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2147483647,-14756,21474,-2147483646,-2147483647,-5555,9999,78967|8|2222",
            "1,3,-1,-3,5,3,6,7|5|1,3,3,5",
            "1,3,-1,-3,5,3,6,7|3|1.0,-1.0,-1.0,3.0,5.0,6.0",
            "1,2,3,4,5|5|3.0",
            "1,2,3,4,5|1|1.0,2.0,3.0,4.0,5.0",
            "1,2,3,4,5|2|1.5,2.5,3.5,4.5",
            "1,3,-1,2,-2,-3,5,1,5,3|4|1.5,0.5,-1.5,0.0,-0.5,3.0,4.0"
    })
    void test(String numsStr, int k, String expectedStr) {
        double[] expected = Arrays.stream(expectedStr.split(",")).mapToDouble(Double::parseDouble).toArray();
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(expected, medianSlidingWindow(nums, k));
    }
}
