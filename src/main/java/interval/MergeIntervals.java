package interval;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 *We are given an array of closed intervals
 *
 * , intervals, where each interval has a start time and an end time. The input array is sorted with respect to the start times of each interval. For example, intervals = [ [1,4], [3,6], [7,9] ][ [1,4], [3,6], [7,9] ] is sorted in terms of start times 1, 31, 3, and 77.
 *
 * Your task is to merge the overlapping intervals and return a new output array consisting of only the non-overlapping intervals.
 *
 * Constraints:
 *
 *     1≤1≤ intervals.length ≤103≤103
 *     intervals[i].length =2=2
 *     0≤0≤ start time ≤≤ end time ≤104≤104
 */
public class MergeIntervals {
    public static int[][] mergeIntervals(int[][] intervals) {
        ArrayList<int[]> mergedList = new ArrayList();
        mergedList.add(intervals[0]);
        for(int i = 1; i < intervals.length; i++){
            int[] lastMergedInterval = mergedList.get(mergedList.size()-1);
            int[] current = intervals[i];
            if(lastMergedInterval[1] >= current[0]){
                mergedList.remove(mergedList.size()-1);
                mergedList.add(new int[]{lastMergedInterval[0], Math.max(current[1], lastMergedInterval[1])});
            } else {
                mergedList.add(current);
            }
        }
        return mergedList.toArray(int[][]::new);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[1,4]|[1,4]",
            "[1,4],[3,5]|[1,5]",
            "[1,5],[3,7],[4,6],[6,8]|[1,8]",
            "[10,12],[12,15]|[10,15]",
                    "[1,3],[2,6][8,10][15,18][18,20]|[1,6],[8,10],[15,20]",

    })
    void test(String intervalStr, String expectedStr){
        var pattern = Pattern.compile("\\[(\\d)+,(\\d)+\\]");
        var matcher = pattern.matcher(intervalStr);
        int[][] intervals = matcher.results()
                .map(matchResult -> matcher.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).mapToInt(ch -> Integer.parseInt(ch)).toArray();
                        }
                )
                .toArray(int[][]::new);
        var matcherEx = pattern.matcher(expectedStr);
        int[][] expected = matcherEx.results()
                .map(matchResult -> matcherEx.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).mapToInt(ch -> Integer.parseInt(ch)).toArray();
                        }
                )
                .toArray(int[][]::new);
        Assertions.assertArrayEquals(expected, mergeIntervals(intervals));
    }
}
