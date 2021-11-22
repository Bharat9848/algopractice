package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given an array of meeting time intervals where intervals[i] = [starti, endi], determine if a person could attend all meetings.
 *
 *
 *
 * Example 1:
 *
 * Input: intervals = [[0,30],[5,10],[15,20]]
 * Output: false
 *
 * Example 2:
 *
 * Input: intervals = [[7,10],[2,4]]
 * Output: true
 *
 *
 *
 * Constraints:
 *
 *     0 <= intervals.length <= 10^4
 *     intervals[i].length == 2
 *     0 <= starti < endi <= 10^6
 *     sort the intervals. find if contiguous intervals are overlapping
 */
public class P252CanAttendMeetings {
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(arr -> arr[0]));
        boolean canAttend = true;
        for (int i = 1; i < intervals.length && canAttend; i++) {
            canAttend = !areIntervalOverlapping(intervals[i-1], intervals[i]);
        }
        return canAttend;
    }

    private boolean areIntervalOverlapping(int[] first, int[] second) {
        // TODO optimization this can be simplified by checking if start of second is less than end time of first interval
        return (first[0] > second[0] && first[0] < second[1]) || (first[1] > second[0] && first[1] < second[1]) ||(first[0] >= second[0] && first[1] <= second[1]) ||(second[0] >= first[0] && second[1] <= first[1]);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0,30],[5,10],[15,20]]|false",
            "[[7,10],[2,4]]|true",
            "[[3,6],[2,8]]|false",
            "[[2,8],[3,6]]|false",
            "[[2,8],[3,4]]|false",
            "[[2,8],[7,10]]|false",
            "[[1,13],[13,15]]|true"
    })
    void test(String meetingRoomsStr, boolean expected){
        int[][] intervals = Arrays.stream(meetingRoomsStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] duration = s.split(",");
                    return new int[]{Integer.parseInt(duration[0]), Integer.parseInt(duration[1])};
                }).toArray(int[][]::new);
        Assert.assertEquals(expected, canAttendMeetings(intervals));
    }
}
