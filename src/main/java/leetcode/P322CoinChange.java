package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * ou are given coins of different denominations and a total amount of money amount.
 * Write a function to compute the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.
 * <p>
 * Example 1:
 * <p>
 * Input: coins = [1, 2, 5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 * Example 2:
 * <p>
 * Input: coins = [2], amount = 3
 * Output: -1
 * Note:
 * You may assume that you have an infinite number of each kind of coin.
 * <p>
 * <p>
 * Fewest No to reach amount is minimun of one plus the no of coins to reach forEach(amount - denomination)
 * <p>
 * <p>
 * Created by bharat on 29/5/18.
 */
public class P322CoinChange {

    public int coinChange(int[] coins, int amount) {
        if (amount < 0) {
            return -1;
        }
        if (amount == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            if (amount == coins[i]) {
                return 1;
            }
            int subCoin = coinChange(coins, amount - coins[i]);
            if (subCoin != -1 && subCoin + 1 < min) {
                min = subCoin + 1;
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    public int coinChangeIterative(int[] coins, int amount) {
        int[] amountToNoOfCoins = new int[amount + 1];
        amountToNoOfCoins[0] = 0;
        for (int subAmount = 1; subAmount < amountToNoOfCoins.length; subAmount++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (subAmount - coins[j] < 0 || amountToNoOfCoins[subAmount - coins[j]] == -1) {
                } else {
                    if (min > amountToNoOfCoins[subAmount - coins[j]] + 1) {
                        min = amountToNoOfCoins[subAmount - coins[j]] + 1;
                    }
                }
            }
            amountToNoOfCoins[subAmount] = min == Integer.MAX_VALUE ? -1 : min;
        }
        System.out.println(Arrays.toString(amountToNoOfCoins));
        return amountToNoOfCoins[amount];
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,5|10|2",
            "1,2,5|11|3",
            "1,2,3,4|11|3",
            "1,2,3,4,5|7|2",
            "11,13|7|-1",
            "11,13|0|0",
            "2|4|2",
            "2,3,4,6,8|23|4",
            "52,172|500|5",
            "186,419,83,408|6249|20"

    })
    void test(String coinsStr, int total, int expected) {
        int[] coins = Arrays.stream(coinsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, coinChange(coins, total));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,5|10|2",
            "1,2,5|11|3",
            "1,2,3,4|11|3",
            "1,2,3,4,5|7|2",
            "11,13|7|-1",
            "11,13|0|0",
            "2|4|2",
            "2,3,4,6,8|23|4",
            "52,172|500|5",
            "186,419,83,408|6249|20"

    })
    void testIterative(String coinsStr, int total, int expected) {
        int[] coins = Arrays.stream(coinsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, coinChangeIterative(coins, total));
    }
}
