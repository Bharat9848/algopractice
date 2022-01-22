package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an array of events where events[i] = [startDayi, endDayi]. Every event i starts at startDayi and ends at endDayi.
 *
 * You can attend an event i at any day d where startTimei <= d <= endTimei. Notice that you can only attend one event at any time d.
 *
 * Return the maximum number of events you can attend.
 *
 *
 *
 * Example 1:
 *
 * Input: events = [[1,2],[2,3],[3,4]]
 * Output: 3
 * Explanation: You can attend all the three events.
 * One way to attend them all is as shown.
 * Attend the first event on day 1.
 * Attend the second event on day 2.
 * Attend the third event on day 3.
 *
 * Example 2:
 *
 * Input: events= [[1,2],[2,3],[3,4],[1,2]]
 * Output: 4
 *
 * Example 3:
 *
 * Input: events = [[1,4],[4,4],[2,2],[3,4],[1,1]]
 * Output: 4
 *
 * Example 4:
 *
 * Input: events = [[1,100000]]
 * Output: 1
 *
 * Example 5:
 *
 * Input: events = [[1,1],[1,2],[1,3],[1,4],[1,5],[1,6],[1,7]]
 * Output: 7
 *
 *
 *
 * Constraints:
 *
 *     1 <= events.length <= 10^5
 *     events[i].length == 2
 *     1 <= startDayi <= endDayi <= 10^5
 */
 class P1353MaximumNoOfEventsCanAttendNotDone {
     int maxEvents(int[][] events) {
        Arrays.sort(events, (event1, event2) -> {
            if (event1[1] < event2[1]) {
                return -1;
            } else if (event1[1] > event2[1]) {
                return 1;
            } else {
                return -((event1[1] - event1[0]) - (event2[1] - event2[0]));
            }
        });
        int attendCount =0;
        for (int day = 1,j=0; j < events.length;) {
            int[] current = events[j];
            if(day >= current[0] && day <= current[1]){
                attendCount++;
                day++;
                j++;
            }else if(day < current[0]){
                day++;
            } else {
                j++;
            }
        }
        return attendCount;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"[[1,10000]]|1",
            "[[1,1],[1,2],[1,3],[1,4],[1,5],[1,6],[1,7]]|7",
    "[[1,1],[2,2],[4,4]]|3",
    "[1,1],[1,2],[2,2]]|2",
    "[[1,2],[1,2],[3,3],[1,5],[1,5]|5",
            "[[1,3],[2,3],[3,3]]|3"
    })

    public void test(String eventsStr, int expected){
        int[][] events = Arrays.stream(eventsStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] entries = s.split(",");
                    return new int[]{Integer.parseInt(entries[0]), Integer.parseInt(entries[1])};
                }).toArray(int[][]::new);
        Assert.assertEquals(expected, maxEvents(events));
    }
}
