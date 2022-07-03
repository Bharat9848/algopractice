package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 1 2 3 2 1
 * 3 2 1 4 7
 *
 * 0 0 1 0 0
 * 0 1 0 2 0
 * 1 0 0 0 3
 *
 *
 * Given two integer arrays nums1 and nums2, return the maximum length of a subarray that appears in both arrays.
 *
 * Input: nums1 =   [1,2,3,2,1], nums2 = [3,2,1,4,7]
 * Output: 3
 * Explanation: The repeated subarray with maximum length is [3,2,1].
 * Input: nums1 = [0,0,0,0,0], nums2 = [0,0,0,0,0]
 * Output: 5
 *
 *
 * Constraints:
 *
 *     1 <= nums1.length, nums2.length <= 1000
 *     0 <= nums1[i], nums2[i] <= 100
 *     
 *     create a map of value vs List of indices for num1 and then traverse nums2 while checking the values of subarray in nums1 map. If value in nums1 is repeating then it might go O(N)
 *     1,2,1,2,3,1,2,3,4,5
 */
class P718MaxLengthRepeatedArray {

    int findLength(int[] nums1, int[] nums2) {
        int[][] maxLen = new int[nums1.length][nums2.length];
        int maxLength = 0;
        for (int i = 0; i < nums1.length; i++) {
            if(nums2[0] == nums1[i]){
                maxLen[i][0] = 1;
            }
            maxLength = Math.max(maxLength, maxLen[i][0]);
        }
        for (int i = 1; i < nums2.length; i++) {
            for (int j = 0; j < nums1.length; j++) {
                if(nums2[i] == nums1[j]){
                    maxLen[j][i] = (j-1 >= 0 && i-1 >= 0? maxLen[j-1][i-1]:0) + 1;
                }else{
                    maxLen[j][i] = 0;
                }
                maxLength = Math.max(maxLength, maxLen[j][i]);
            }
        }
        return maxLength;
    }
    int findLengthTLE(int[] nums1, int[] nums2) {
        int[] maxArr = nums1.length > nums2.length ? nums1: nums2;
        int[] minArr = nums1.length <= nums2.length ? nums1: nums2;

        Map<Integer, Set<Integer>> valToIndices = IntStream.range(0, maxArr.length).boxed().collect(Collectors.toMap(index -> maxArr[index], index -> {
            Set<Integer> indices = new HashSet<>();
            indices.add(index);
            return indices;
        }, (indices1, indices2) -> {
            indices1.addAll(indices2);
            return indices1;
        }));
        int maxLength = 0;
        for (int i = 0; i < minArr.length; i++) {
            int no = minArr[i];
            if(valToIndices.get(no) != null){
                int max = 0;
                for (Integer index: valToIndices.get(no)) {
                    int currentLength = findMaxlengthFrom(minArr, i, maxArr, index);
                    max = Math.max(max, currentLength);
                }
                maxLength = Math.max(maxLength, max);
            }
        }
        return maxLength;
    }

    private int findMaxlengthFrom(int[] nums2, Integer index, int[] nums1, int indexNum1) {
        int i = 0;
        while(indexNum1+i < nums1.length && index + i < nums2.length && nums1[indexNum1+i]==nums2[index + i]){
            i++;
        }
        return i;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0,0,0,0,0,0,1,0,0,0|0,0,0,0,0,0,0,1,0,0|9",
            "1,2,3,2,1|3,2,1,4,7|3",
            "1,2,3|1,4,7|1",
            "1,2,3|5,4,7|0",
            "0,0,0,0,0|0,0,0,0,0|5"
    })
    void test(String numStr1, String numStr2, int expected){
        int[] nums1 = Arrays.stream(numStr1.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] nums2 = Arrays.stream(numStr2.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, findLength(nums1, nums2));
    }
}
