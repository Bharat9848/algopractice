package leetcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;


/*
Given a collection of intervals, merge all overlapping intervals.

Example 1:

Input: [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].

Example 2:

Input: [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.

NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.

 */
public class P56MergingInterval {
    public int[][] merge(int[][] intervals) {
        if(intervals.length == 0 || intervals.length == 1){
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a1 -> a1[0]));
        int[][] result = new int[intervals.length][2];
        result[0] = intervals[0];
        int length = 1;
        for (int i = 1; i < intervals.length; i++) {
            if(overlapping(result[length - 1], intervals[i])){
                int[] newInterval = mergeIntervals(result[length - 1], intervals[i]);
                result[length-1] = newInterval;
            }else{
                result[length] = intervals[i];
                length++;
            }
        }
        return subArray(result, length);
    }

    private int[][] subArray(int[][] result, int length) {
        int[][] sub = new int[length][result[0].length];
        for (int i = 0; i < length; i++) {
            sub[i] = result[i];
        }
        return sub;
    }

    private int[] mergeIntervals(int[] a, int[] b) {
        int[] result;
        if(a[0] <= b[0] && a[1] >= b[1]){
            result = a;
        }else {
            result = new int[]{a[0], b[1]};
        }
        return result;
    }

    private boolean overlapping(int[] a, int[] b) {
        return (a[0] <= b[0] && a[1] >= b[0]);
    }

    @Test
    public void testEmptySingle(){
        assertTrue(merge(new int[0][]).length == 0);
        assertArrayEquals(merge(new int[][]{{1,2}}), new int[][] {{1,2}});
    }

    @Test
    public void testEmbeddedInterval(){
        assertArrayEquals(merge(new int[][]{{5,10},{6,7},{11,12}}), new int[][]{{5,10}, {11,12}});
        assertArrayEquals(new int[][]{{1,6}, {8,10}, {15,18}}, merge(new int[][]{{1,3}, {2,6}, {8,10}, {15,18}}));
        assertArrayEquals(new int[][]{{1,5}}, merge(new int[][]{{1,4}, {4, 5}}));

    }




}
