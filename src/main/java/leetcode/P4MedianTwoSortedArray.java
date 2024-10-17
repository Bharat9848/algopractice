package leetcode;

/**
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
 *
 * The overall run time complexity should be O(log (m+n)).
 *
 *
 *
 * Example 1:
 *
 * Input: nums1 = [1,3], nums2 = [2]
 * Output: 2.00000
 * Explanation: merged array = [1,2,3] and median is 2.
 *
 * Example 2:
 *
 * Input: nums1 = [1,2], nums2 = [3,4]
 * Output: 2.50000
 * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
 *
 *
 *
 * Constraints:
 *
 *     nums1.length == m
 *     nums2.length == n
 *     0 <= m <= 1000
 *     0 <= n <= 1000
 *     1 <= m + n <= 2000
 *     -106 <= nums1[i], nums2[i] <= 106
 *
 *
 *
 * invariant
 * l  and r no of elements to be removed from left and right
 * mIndex1, mIndex2 = index of median1
 *  l>2 r>2
 * Base case
 */
public class P4MedianTwoSortedArray {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m  = nums1.length;
        int n = nums2.length;
        int mIndex1 = -1, mIndex2 = -1;
        int l = -1, r=-1;
        if((m + n)%2 == 0){
            mIndex1 = (int)(m+n)/2;
            mIndex2 = (int)(m+n)/2 +1;
            l = (m+n)/2 -1;
            r = n - (m+n)/2 -1;
            System.out.printf("init mIndex1=%d, mIndex2= %d, l=%d r=%d%n \n", mIndex1, mIndex2, l, r);
        }

        int nums1Start = 0, nums1end= nums1.length -1;
        int nums2Start = 0, nums2end= nums2.length -1;
        int nums1Median = nums1[nums1.length/2], nums2Median = nums2[nums2.length/2];


        if( l <= 2 || r <= 2) {
            return solveForBaseCase(nums1, nums2, nums1Start, nums1end,  nums2Start, nums2end);
        }

        int nums2MedianPosition = binarySearchIndex(nums1, nums1Start, nums1end, nums2Median);
        if(nums2MedianPosition <= getNums1MedianPosition(nums1Start, nums1end))
        {
            l = l - (nums2MedianPosition -  nums2Start);
        } else{
            r = r - (nums2end - nums2MedianPosition);
        }
        if( l <= 2 || r <= 2) {
        }


        int nums1MedianPosition = binarySearchIndex(nums2, nums2Start, nums2end, nums1Median);
        return -1;
    }

    private int solveForBaseCase(int[] nums1, int[] nums2, int nums1Start, int nums1end, int nums2Start, int nums2end){
        return -1;
    }

    private int getNums1MedianPosition(int nums1Start, int nums1end) {
        return 0;
    }

    private int binarySearchIndex(int[] nums1, int nums1Start, int nums1end, int nums2Median) {
        return 0;
    }
}
