package heap.must;

/**
 * We are given an input array of meeting time intervals, intervals, where each interval has a start time and an end time. Your task is to find the minimum number of meeting rooms required to hold these meetings.
 *
 *     An important thing to note here is that the specified end time for each meeting is exclusive.
 *
 * Constraints
 *
 *     1<=1<= intervals.length <=103<=103
 *     00 ≤≤ startistarti​ << endiendi​ ≤≤ 106106
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.IntervalParser;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * store all the conflicting rooms in a heap at a time with min end time meeting at the top. once we get the non-conflicting meeting we can remove the top and insert the new interval. and repeat some way. After each insertion in heap we will maintain maxHeapSize.
 */
public class MeetingRoomII {
    public static int findSets(int[][] intervals) {
        if(intervals.length == 0){
            return 0;
        }
        int maxMeetingRooms = 1;
        PriorityQueue<int[]> rooms = new PriorityQueue<>(Comparator.comparing(ints -> ints[1]));
        Arrays.sort(intervals, Comparator.comparingInt(ints -> ints[0]));
        rooms.offer(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] nextTobeFreeRoom = rooms.peek();
            int[] currentRoom =  intervals[i];
            if(currentRoom[0] < nextTobeFreeRoom[1]){
                rooms.offer(intervals[i]);
            } else {
                rooms.poll();
                rooms.offer(intervals[i]);
            }
            maxMeetingRooms = Math.max(maxMeetingRooms, rooms.size());
        }
        return maxMeetingRooms;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[2,4]|1",
            "[2,4],[3,5][9,10]|2",
            "[2,4],[3,5][9,13][9,11],[10,11]|3"
    })
    void test(String meetingsStr, int expected){
        int[][] meetings = IntervalParser.parseIntArrayString(meetingsStr, "\\[\\d+,\\d+\\]");
        Assertions.assertEquals(expected, findSets(meetings));
    }
}
