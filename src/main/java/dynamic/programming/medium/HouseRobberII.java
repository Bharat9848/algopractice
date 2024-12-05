package dynamic.programming.medium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * A professional robber plans to rob some houses along a street. These houses are arranged in a circle, which means that the first and the last house are neighbors. The robber cannot rob adjacent houses because they have security alarms installed.
 *
 * Following the constraints mentioned above and given an integer array money representing the amount of money in each house, return the maximum amount the robber can steal without alerting the police.
 *
 * Constraints:
 *
 *     1≤ money.length ≤10^3
 *     0≤ money[i] ≤10^3
 *
 *     MaxLoot(n) = max(MaxLoot(not including n-1) + n, MaxLoot(including n-1))
 */
public class HouseRobberII {
    public static int houseRobber(int[] money) {
        if(money.length == 1){
            return money[0];
        }
        int maxLootFromZeroToSecLast = houseRobberSub(money, 0, money.length-2);
        int maxLootFromOneToLast = houseRobberSub(money, 1, money.length-1);
        return Math.max(maxLootFromOneToLast, maxLootFromZeroToSecLast);
    }

    private static int houseRobberSub(int[] money, int beg, int end) {
        if(beg == end){
            return money[beg];
        }
        int maxSumNotIncludingLast = money[beg];
        int maxSumIncludingLast = money[beg+1];
        int currentMax = Math.max(maxSumIncludingLast, maxSumNotIncludingLast);
        for (int i = beg+2; i <= end ; i++) {
            currentMax = Math.max(maxSumNotIncludingLast + money[i], maxSumIncludingLast);
            int temp = maxSumNotIncludingLast;
            maxSumNotIncludingLast = Math.max(maxSumIncludingLast, maxSumNotIncludingLast);
            maxSumIncludingLast = temp + money[i];
        }
        return currentMax;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,5,3|5",
            "12|12",
            "1,5|5",
            "100,1,3,5|103",
            "100,1,3,5,10|105",
            "100,96,3,5,10|106",
            "2,5,3,6|11",
            "2,10,14,8,1|18"
    })
    void test(String moneyStr, int expected){
        Assertions.assertEquals(expected, houseRobber(Arrays.stream(moneyStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
