package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * You want to schedule a list of jobs in d days. Jobs are dependent (i.e To work on the ith job, you have to finish all the jobs j where 0 <= j < i).
 *
 * You have to finish at least one task every day. The difficulty of a job schedule is the sum of difficulties of each day of the d days. The difficulty of a day is the maximum difficulty of a job done on that day.
 *
 * You are given an integer array jobDifficulty and an integer d. The difficulty of the ith job is jobDifficulty[i].
 *
 * Return the minimum difficulty of a job schedule. If you cannot find a schedule for the jobs return -1.
 *
 *
 *
 * Example 1:
 *
 * Input: jobDifficulty = [6,5,4,3,2,1], d = 2
 * Output: 7
 * Explanation: First day you can finish the first 5 jobs, total difficulty = 6.
 * Second day you can finish the last job, total difficulty = 1.
 * The difficulty of the schedule = 6 + 1 = 7
 *
 * Example 2:
 *
 * Input: jobDifficulty = [9,9,9], d = 4
 * Output: -1
 * Explanation: If you finish a job per day you will still have a free day. you cannot find a schedule for the given jobs.
 *
 * Example 3:
 *
 * Input: jobDifficulty = [1,1,1], d = 3
 * Output: 3
 * Explanation: The schedule is one job per day. total difficulty will be 3.
 *
 *
 *
 * Constraints:
 *
 *     1 <= jobDifficulty.length <= 300
 *     0 <= jobDifficulty[i] <= 1000
 *     1 <= d <= 10
 */
public class P1335MinJobSchedule {
    int minDifficulty(int[] jobDifficulty, int d) {
        if(d > jobDifficulty.length){
            return -1;
        }
        return minDifficultyRecursive(jobDifficulty, 0, 1, d, new HashMap<>());
    }

    int minDifficultyRecursive(int[] difficulty, int startJob, int currentDay, int totalDays, Map<Pair<Integer, Integer>, Integer> resultholder){
        if(startJob >= difficulty.length) {
            resultholder.put(new Pair<>(startJob,currentDay),0);
            return 0;
        }
        if(currentDay == totalDays){
            Integer value = resultholder.get(new Pair<>(startJob, currentDay));
            if(value == null){
                int max = getMaxDifficultJobFrom(difficulty, startJob, difficulty.length-1);
                resultholder.put(new Pair<>(startJob, currentDay), max);
            }
            return resultholder.get(new Pair<>(startJob, currentDay));
        }
        int min = difficulty[startJob] + minDifficultyRecursive(difficulty, startJob+1, currentDay+1, totalDays, resultholder);
        for (int s = startJob; s < difficulty.length -(totalDays-currentDay); s++) {
            if(resultholder.get(new Pair<>(s+1, currentDay+1)) == null){
                minDifficultyRecursive(difficulty, s+1, currentDay+1, totalDays, resultholder);
            }
            int curr = getMaxDifficultJobFrom(difficulty, startJob, s) + resultholder.get(new Pair<>(s+1, currentDay+1));
            min = Math.min(curr, min);
        }
        resultholder.put(new Pair<>(startJob, currentDay), min);
        return resultholder.get(new Pair<>(startJob, currentDay));
    }

    private int getMaxDifficultJobFrom(int[] difficulty, int startIndex, int endIndex) {
        int max = difficulty[startIndex];
        for (int i = startIndex+1; i <= endIndex; i++) {
            max = Math.max(difficulty[i], max);
        }
        return max;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "7,1,7,1,7,1|3|15",
            "6,5,4,3,2,1|2|7",
            "9,9,9|4|-1",
            "1,1,1|3|3",
    })
    void test(String difficultyStr, int days, int expected){
        int[] jobDifficulty = Arrays.stream(difficultyStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, minDifficulty(jobDifficulty, days));
    }
}
