package heap.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given two sorted integer arrays, nums1nums1 and nums2nums2, and the number of data elements in each array, mm and nn, implement a function that merges the second array into the first one. You have to modify nums1nums1 in place.
 *
 *     Note: Assume that nums1nums1 has a size equal to m+nm+n, meaning it has enough space to hold additional elements from nums2nums2.
 *
 * Constraints:
 *
 *     nums1.length =m+n=m+n
 *     nums2.length =n=n
 *     0≤m,n≤2000≤m,n≤200
 *     1≤m+n≤2001≤m+n≤200
 *     −103≤−103≤ nums1[i], nums2[j] ≤103≤103
 */

/**
 * todo unoptimized can be done without shifting elements
 */
public class MergeSortedArray {
    public static int[] mergeSorted(int[] nums1, int m, int[] nums2, int n) {
        int i = 0, j = 0;
        while (i < m + j && j < n){
            if(nums1[i] <= nums2[j]){
                i++;
            } else {
                for (int k = m+j-1; k >= i ; k--) {
                    nums1[k+1] = nums1[k];
                }
                nums1[i] = nums2[j];
                j++;
                i++;
            }
        }
        if(j < n){
            for (int k = j; k < n; k++) {
                nums1[m+k] = nums2[k];
            }
        }

        return nums1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
       "3,6,9,12,25,0,0,0,0|5|5,19,24,25|4|3,5,6,9,12,19,24,25,25",
            "1,2,7,92,0,0,0|4|3,4,5|3|1,2,3,4,5,7,92",
            "1,12,57,98,0,0,0,0|4|1,4,5,57|4|1,1,4,5,12,57,57,98",
            "1,2,3,0,0,0|3|4,5,6|3|1,2,3,4,5,6"
    })
    void test(String nums1Str, int m, String nums2Str, int n, String expectedStr){
        int[] nums1 = Arrays.stream(nums1Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] nums2 = Arrays.stream(nums2Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(expected, mergeSorted(nums1, m, nums2, n));
    }
}
