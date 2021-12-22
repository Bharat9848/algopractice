package leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * You are given a list of songs where the ith song has a duration of time[i] seconds.
 *
 * Return the number of pairs of songs for which their total duration in seconds is divisible by 60. Formally, we want the number of indices i, j such that i < j with (time[i] + time[j]) % 60 == 0.
 *
 *
 *
 * Example 1:
 *
 * Input: time = [30,20,150,100,40]
 * Output: 3
 * Explanation: Three pairs have a total duration divisible by 60:
 * (time[0] = 30, time[2] = 150): total duration 180
 * (time[1] = 20, time[3] = 100): total duration 120
 * (time[1] = 20, time[4] = 40): total duration 60
 *
 * Example 2:
 *
 * Input: time = [60,60,60]
 * Output: 3
 * Explanation: All three pairs have a total duration of 120, which is divisible by 60.
 *
 * TLE version: sort them and do modified binary search for (60*n - current)
 *
 *(a,b,c,d) - ab ac ad bc bd cd  (n*n+1)/2
 * Constraints:
 *
 *    1 <= time.length <= 6 * 104
 *     1 <= time[i] <= 500
 */
class P1010PairOfSongMinDivisibleBy60 {
    int numPairsDivisibleBy60(int[] time) {
        Map<Integer, Integer> remainder60VsCount = Arrays.stream(time).boxed().collect(Collectors.toMap(t -> t % 60, t ->1, Integer::sum));
        int count=0;
        for (Map.Entry<Integer, Integer> timeVsCountEntry : remainder60VsCount.entrySet()) {
            if(timeVsCountEntry.getKey() <= 30){
                if(timeVsCountEntry.getKey() == 0 || timeVsCountEntry.getKey() == 30){
                    int c = timeVsCountEntry.getValue() -1;
                    count += (c*(c+1))/2;
                }else {
                    count += (timeVsCountEntry.getValue() * remainder60VsCount.getOrDefault(60 - timeVsCountEntry.getKey(),0));
                }
            }
        }
        return count;
    }
    int numPairsDivisibleBy60TimeLimitExceeded(int[] time) {
        int pairCount = 0;
        Arrays.sort(time);
        for (int i = 0; i < time.length; i++) {
            int currTime = time[i];
            for (int j = i + 1; j < time.length;) {
                int nextMultipleOf60 = findNextMultipleOf60(currTime + time[j]);
                int target = nextMultipleOf60 - currTime;
                Pair<Boolean, Integer> successfulSearchToNext = binarySearchExactOrGreatest(time, j, time.length - 1, target);
                Boolean successful = successfulSearchToNext.getFirst();
                if (successful) {
                    System.out.println("pair found for" + currTime +" and " + time[successfulSearchToNext.getSec()-1]);
                    pairCount++;
                }
                j = successfulSearchToNext.getSec();
            }
        }
        return pairCount;
    }

    private Pair<Boolean, Integer> binarySearchExactOrGreatest(int[] time, int beg, int end, int target) {
        while (beg < end) {
            int mid = beg + (end - beg) / 2;
            if (time[mid] == target) {
                int i = mid-1;
                while(i>=beg && time[i] == target){i--;}
                return new Pair<>(true, i + 2);
            } else if (time[mid] > target) {
                end = mid;
            } else {
                beg = mid + 1;
            }
        }
        if(time[beg] == target){
            return new Pair<>(true, beg+1);
        }else if(time[beg] < target){
            return new Pair<>(false, beg+1);
        }else{
            return new Pair<>(false, beg);
        }
    }

    private int findNextMultipleOf60(int sum) {
        return sum % 60 == 0 ? sum: 60 *(sum/60 + 1);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "60,60,40|1",
            "2,4,6,7,58,58,58|3",
            "60,60,60|3",
            "30,20,150,100,40|3"
    })
    void test(String timeStr, int expected){
        int[] time = Arrays.stream(timeStr.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, numPairsDivisibleBy60(time));
    }
//    @Test
    void testBig(){
        int[] time = new int[]{2, 5, 9, 11, 14, 18, 23, 24, 27, 38, 44, 60, 62, 78, 81, 83, 91, 100, 106, 110, 112, 116, 117, 119, 129, 131, 137, 144, 144, 145, 146, 146, 157, 158, 164, 171, 171, 175, 186, 189, 197, 198, 199, 201, 201, 203, 204, 204, 210, 221, 224, 230, 238, 242, 242, 245, 250, 252, 256, 264, 265, 267, 269, 275, 276, 278, 279, 280, 280, 280, 284, 287, 287, 288, 289, 293, 295, 303, 317, 318, 318, 322, 323, 324, 330, 332, 332, 345, 347, 349, 350, 350, 370, 378, 379, 382, 384, 399, 400, 411, 412, 428, 430, 433, 457, 461, 467, 467, 468, 470, 470, 472, 473, 473, 475, 475, 478, 478, 479, 498, 499};
        Assert.assertEquals(105, numPairsDivisibleBy60(time));
    }
}
