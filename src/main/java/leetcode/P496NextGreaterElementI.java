package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * The next greater element of some element x in an array is the first greater element that is to the right of x in the same array.
 *
 * You are given two distinct 0-indexed integer arrays nums1 and nums2, where nums1 is a subset of nums2.
 *
 * For each 0 <= i < nums1.length, find the index j such that nums1[i] == nums2[j] and determine the next greater element of nums2[j] in nums2. If there is no next greater element, then the answer for this query is -1.
 *
 * Return an array ans of length nums1.length such that ans[i] is the next greater element as described above.
 *
 *
 *
 * Example 1:
 *
 * Input: nums1 = [4,1,2], nums2 = [1,3,4,2]
 * Output: [-1,3,-1]
 * Explanation: The next greater element for each value of nums1 is as follows:
 * - 4 is underlined in nums2 = [1,3,4,2]. There is no next greater element, so the answer is -1.
 * - 1 is underlined in nums2 = [1,3,4,2]. The next greater element is 3.
 * - 2 is underlined in nums2 = [1,3,4,2]. There is no next greater element, so the answer is -1.
 *
 * Example 2:
 *
 * Input: nums1 = [2,4], nums2 = [1,2,3,4]
 * Output: [3,-1]
 * Explanation: The next greater element for each value of nums1 is as follows:
 * - 2 is underlined in nums2 = [1,2,3,4]. The next greater element is 3.
 * - 4 is underlined in nums2 = [1,2,3,4]. There is no next greater element, so the answer is -1.
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums1.length <= nums2.length <= 1000
 *     0 <= nums1[i], nums2[i] <= 104
 *     All integers in nums1 and nums2 are unique.
 *     All the integers of nums1 also appear in nums2.
 *
 *
 * Follow up: Could you find an O(nums1.length + nums2.length) solution?
 */
public class P496NextGreaterElementI {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> monotonicStack = new Stack<>();
        int[] result = new int[nums1.length];
        Map<Integer, Integer> noToIdx = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) {
            noToIdx.put(nums1[i], i);
        }
        for (int i = nums2.length - 1; i >= 0; i--) {
            int no = nums2[i];

            if (noToIdx.containsKey(no)) {
                if( monotonicStack.empty()) {
                    result[noToIdx.get(no)] = -1;
                } else {
                    if(monotonicStack.peek() > no){
                        result[noToIdx.get(no)] = monotonicStack.peek();
                    } else {
                        while(!monotonicStack.empty() && monotonicStack.peek() < no){
                            monotonicStack.pop();
                        }
                        if(monotonicStack.empty()){
                            result[noToIdx.get(no)] = -1;
                        }else {
                            result[noToIdx.get(no)] = monotonicStack.peek();
                        }
                    }
                }
            }

                if (monotonicStack.empty()) {
                    monotonicStack.push(no);
                } else {
                    while (!monotonicStack.empty() && monotonicStack.peek() < no){
                        monotonicStack.pop();
                    }
                    monotonicStack.push(no);
                }
        }
        return result;
   }

   @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,3,5,2,4|6,5,4,3,2,1,7|7,7,7,7,7",
            "1,8|8,1,6,3|6,-1",
            "2,1|2,3,1,9|3,9",
            "1,2,3,4|1,2,3,4|2,3,4,-1",
    })
    void test(String nums1Str, String nums2Str, String expectedStr){
        int[] nums1 = Arrays.stream(nums1Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] nums2 = Arrays.stream(nums2Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
       Assertions.assertArrayEquals(expected, nextGreaterElement(nums1, nums2));
   }
}
