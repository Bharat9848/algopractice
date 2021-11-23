package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * Given an array of meeting time intervals intervals where intervals[i] = [starti, endi], return the minimum number of conference rooms required.
 *
 *
 *
 * Example 1:
 *
 * Input: intervals = [[0,30],[5,10],[15,20]]
 * Output: 2
 *
 * Example 2:
 *
 * Input: intervals = [[7,10],[2,4]]
 * Output: 1
 *
 *
 *
 * Constraints:
 *
 *     1 <= intervals.length <= 10^4
 *     0 <= starti < endi <= 10^6
 *
 */
class P253MeetingRoomII {
    int minMeetingRooms(int[][] intervals) {
        int noOfRooms = 1;
        Arrays.sort(intervals, Comparator.comparingInt(arr -> arr[0]));
        PriorityQueue<Integer> meetingEndTimes = new PriorityQueue<>(Integer::compareTo);
        meetingEndTimes.add(intervals[0][1]);
        for (int i = 1; i < intervals.length; i++) {
            if(meetingEndTimes.size() < noOfRooms){
                continue;
            } else{
                if(!areOverlapping(meetingEndTimes.peek(), intervals[i][0])){
                    meetingEndTimes.remove(meetingEndTimes.remove());
                }else{
                    noOfRooms += 1;
                }
                meetingEndTimes.add(intervals[i][1]);
            }
        }
        return noOfRooms;
    }

    private boolean areOverlapping(int firstEndTime, int secStartTime) {
        return secStartTime < firstEndTime;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0,30],[5,10],[15,20]]|2",
            "[[7,10],[2,4]]|1",
            "[[3,6],[2,8]]|2",
            "[[2,8],[3,6]]|2",
            "[[2,8],[3,4]]|2",
            "[[2,8],[7,10]]|2",
            "[[1,13],[13,15]]|1",
            "[[1,5],[2,5],[3,5],[4,5],[6,10]]|4",
            "[[1,5],[8,9],[8,9]]|2"
    })
    void test(String meetingRoomsStr, int expected){
        int[][] intervals = Arrays.stream(meetingRoomsStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] duration = s.split(",");
                    return new int[]{Integer.parseInt(duration[0]), Integer.parseInt(duration[1])};
                }).toArray(int[][]::new);
        Assert.assertEquals(expected, minMeetingRooms(intervals));
    }
}
