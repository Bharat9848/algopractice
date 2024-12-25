package interval;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *Given a sorted list of nonoverlapping intervals and a new interval, your task is to insert the new interval into the correct position while ensuring that the resulting list of intervals remains sorted and nonoverlapping. Each interval is a pair of nonnegative numbers, the first being the start time and the second being the end time of the interval.
 *
 * Constraints:
 *
 *     0≤0≤ existing_intervals.length ≤104≤104
 *
 *     existing_intervals[i].length, new_interval.length == 2
 *
 *     0≤0≤ start time << end time ≤104≤104
 *
 *     The list of intervals is sorted in ascending order based on the start timestart time.
 */

/**
 *  find beforeIndex for start and end from newInterval
 *  then possible scenarios are
 *   1. start does not lie in any interval and end also does not fall into any interval. Then remove any interval between beforeIndex of start and end. insert newInterval at start's beforeIndex
 *   2. start lie in an interval but end does not. Use start beforeIndex interval's start and remove any interval between beforeIndex of start and end
 *   3. vice versa step 2 for end
 *   4. both fall in their respective indexes. remove all the intervals between start and end indexes and insert a new interval at start beforeIndex using start interval start and end interval end.
 *
 *   TODO implementation can be done better - bad performance binary search would be a good choice if we can update the array in place. hint:variation of merge sort
 */
public class InsertIntervals {
    public static int[][] insertInterval(int[][] existingIntervals, int[] newInterval) {
        if(newInterval == null){
            throw new IllegalArgumentException("wrong input");
        }
        if(existingIntervals == null || existingIntervals.length == 0){
            return new int[][] {newInterval};
        }
        int start = newInterval[0];
        int end = newInterval[1];
        FoundAndIndex startIndex = binarySearchIntervals(existingIntervals, start);
        FoundAndIndex endIndex = binarySearchIntervals(existingIntervals, end);
        boolean startFound = startIndex.found();
        int startInd = startIndex.index();
        boolean endFound = endIndex.found();
        int endInd = endIndex.index();
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < startInd ; i++) {
           result.add(existingIntervals[i]);
        }
        if (startFound && endFound) {
            result.add(new int[]{existingIntervals[startInd][0], existingIntervals[endInd][1]});
        } else if (startFound) {
            result.add(new int[]{existingIntervals[startInd][0], newInterval[1]});
            result.add(existingIntervals[endInd]);
        } else if (endFound) {
            result.add(new int[]{newInterval[0], existingIntervals[endInd][1]});
        } else {
            result.add(newInterval);
            if(endInd < existingIntervals.length){
                result.add(existingIntervals[endInd]);
            }
        }
        for (int i = endInd+1; i < existingIntervals.length; i++) {
            result.add(existingIntervals[i]);
        }
        return result.toArray(int[][]::new);
    }

    private static FoundAndIndex binarySearchIntervals(int[][] existingIntervals, int target) {
        int beg = 0, end = existingIntervals.length - 1;
        while (beg < end) {
            int mid = beg + (end - beg) / 2;
            int[] midInt = existingIntervals[mid];
            if (target >= midInt[0] && target <= midInt[1]) {
                return new FoundAndIndex(true, mid);
            } else if (target > midInt[1]) {
                beg = mid + 1;
            } else {
                end = mid;
            }
        }
        if(beg == existingIntervals.length-1 && target > existingIntervals[beg][1]){
            return new FoundAndIndex(false, existingIntervals.length);
        } else {
            return new FoundAndIndex(target >= existingIntervals[beg][0] && target <= existingIntervals[beg][1], beg);
        }
    }

    private record FoundAndIndex(boolean found, int index){ }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {

          "[2,3],[5,7],[8,9],[11,13]|[1,1]|[1,1],[2,3],[5,7],[8,9],[11,13]",
          "[1,3],[5,7],[8,9],[11,13]|[2,6]|[1,7],[8,9],[11,13]",
          "[1,3],[5,7],[8,9],[11,13]|[14,15]|[1,3],[5,7],[8,9],[11,13],[14,15]",
          "[1,3],[5,7],[8,9],[11,13]|[2,12]|[1,13]",
          "[1,3],[5,7],[8,9],[11,13]|[10,12]|[1,3],[5,7],[8,9],[10,13]",
          "[1,3],[5,7],[8,9],[11,13]|[2,4]|[1,4],[5,7],[8,9],[11,13]",
          "[1,3],[5,7],[8,9],[11,13]|[4,4]|[1,3],[4,4],[5,7],[8,9],[11,13]",
    }
    )
    void test(String intervalStr, String newIntervalStr, String expectedStr){
        var pattern = Pattern.compile("(\\[\\d+,\\d+\\])");
        var matcher = pattern.matcher(intervalStr);
        int[][] intervals = matcher.results()
                .map(matchResult -> matcher.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).mapToInt(ch -> Integer.parseInt(ch)).toArray();
                        }
                )
                .toArray(int[][]::new);
        var pattern1 = Pattern.compile("(\\[\\d+,\\d+\\])");
        var matcher1 = pattern1.matcher(expectedStr);
        int[][] expected = matcher1.results()
                .map(matchResult -> matcher1.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).mapToInt(ch -> Integer.parseInt(ch)).toArray();
                        }
                )
                .toArray(int[][]::new);
        int[] newInterval = Arrays.stream(newIntervalStr.replace("[","").replace("]","").split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(expected, insertInterval(intervals, newInterval));
    }
}
