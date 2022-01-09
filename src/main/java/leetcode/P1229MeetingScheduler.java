package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given the availability time slots arrays slots1 and slots2 of two people and a meeting duration duration, return the earliest time slot that works for both of them and is of duration duration.
 *
 * If there is no common time slot that satisfies the requirements, return an empty array.
 *
 * The format of a time slot is an array of two elements [start, end] representing an inclusive time range from start to end.
 *
 * It is guaranteed that no two availability slots of the same person intersect with each other. That is, for any two time slots [start1, end1] and [start2, end2] of the same person, either start1 > end2 or start2 > end1.
 *
 *
 *
 * Example 1:
 *
 * Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 8
 * Output: [60,68]
 *
 * Example 2:
 *
 * Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 12
 * Output: []
 *
 *
 *
 * Constraints:
 *
 *     1 <= slots1.length, slots2.length <= 104
 *     slots1[i].length, slots2[i].length == 2
 *     slots1[i][0] < slots1[i][1]
 *     slots2[i][0] < slots2[i][1]
 *     0 <= slots1[i][j], slots2[i][j] <= 109
 *     1 <= duration <= 106
 */
 class P1229MeetingScheduler {
     List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        int i = 0, j= 0;
        Arrays.sort(slots1, Comparator.comparingInt(arr -> arr[1]));
        Arrays.sort(slots2, Comparator.comparingInt(arr -> arr[1]));
        while(i<slots1.length && j <slots2.length){
            int[] avail1 = slots1[i];
            int[] avail2 = slots2[j];
            if(durationBetweenSlot(avail1, avail2) >= duration) {
                return commonDuration(avail1, avail2, duration);
            }
            if(avail2[1] > avail1[1]){
                i++;
            }else if(avail2[1] < avail1[1]){
                j++;
            }else{
                i++;
                j++;
            }
        }
        return new ArrayList<>();
    }

    private List<Integer> commonDuration(int[] avail1, int[] avail2, int duration) {
        int maxStartTime = Math.max(avail1[0], avail2[0]);
        return Arrays.asList(maxStartTime, maxStartTime+duration);
    }

    private int durationBetweenSlot(int[] avail1, int[] avail2) {
        int maxStartTime = Math.max(avail1[0], avail2[0]);
        int minEndTime = Math.min(avail1[1], avail2[1]);
        return minEndTime - maxStartTime;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[216397070,363167701],[98730764,158208909],[441003187,466254040],[558239978,678368334],[683942980,717766451]]|[[50490609,222653186],[512711631,670791418],[730229023,802410205],[812553104,891266775],[230032010,399152578]]|456085|98730764,99186849",

            "[[10,50],[60,120],[140,210]]|[[0,15],[60,70]]|8|60,68",
            "[[10,50],[60,120],[140,210]]|[[0,15],[60,70]]|12|0",
            "[[10,50],[60,120],[140,210]]|[[0,15],[60,70]]|10|60,70",
            "[[10,500]]|[[10,20],[60,70]]|10|10,20",
            "[[0,2]]|[[1,3]]|2|0"
            })
    void test(String slot1Str, String slot2Str, int duration, String expectedStr){
        int[][] slot1 = createSlot(slot1Str);
        int[][] slot2 = createSlot(slot2Str);
        List<Integer> expected = !expectedStr.equals("0")? Arrays.stream(expectedStr.split(",")).map(Integer::parseInt).collect(Collectors.toList()): new ArrayList<>();
        Assert.assertEquals(expected, minAvailableDuration(slot1, slot2, duration));
    }

    private int[][] createSlot(String slotStr) {
        return Arrays.stream(slotStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);
    }
}
