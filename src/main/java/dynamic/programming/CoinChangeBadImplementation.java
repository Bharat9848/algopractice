package dynamic.programming;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an integer total that represents the target amount of money and a list of integers coins that represents different coin denominations, find the minimum number of coins required to make up the total amount. If it’s impossible to achieve the target amount using the given coins, return -1. If the target amount is 0, return 0.
 * <p>
 * Note: You can assume that we have an infinite number of each kind of coin.
 * <p>
 * Constraints:
 * <p>
 * 1≤1≤ coins.length ≤12≤12
 * <p>
 * 1≤1≤ coins[i] ≤104≤104
 * <p>
 * 0≤0≤ total ≤900≤900
 */
public class CoinChangeBadImplementation {
    public static int coinChange(int[] coins, int total) {
        if (total == 0) {
            return 0;
        }
        Arrays.sort(coins);
        int[] lastSubsetMin = new int[total + 1];
        int coinMaxValue = Integer.MAX_VALUE - 50000;
        Arrays.fill(lastSubsetMin, coinMaxValue);
        lastSubsetMin[0] = 0;
        for (int i = 0; i < total + 1; i++) {
            int coin = coins[0];
            int factor = 1;
            while (factor * coin <= total) {
                lastSubsetMin[factor * coin] = factor;
                factor++;
            }
        }
        for (int i = 1; i < coins.length; i++) {
            int[] lastSubsetMinTemp = new int[total + 1];
            Arrays.fill(lastSubsetMinTemp, coinMaxValue);
            lastSubsetMinTemp[0] = 0;
            int coin = coins[i];

            for (int j = 0; j < total + 1 ; j++) {
                    if(coin > j){
                        lastSubsetMinTemp[j] = lastSubsetMin[j];
                    }else if(coin == j){
                        lastSubsetMinTemp[j] = 1;
                    } else {
                        int change = 1;
                        while (coin * change <= j){
                            lastSubsetMinTemp[j] = Math.min(lastSubsetMin[j-(change*coin)] + change, Math.min(lastSubsetMin[j], lastSubsetMinTemp[j]));
                            change++;
                        }
                    }
            }
            System.out.println(Arrays.toString(lastSubsetMinTemp));
            lastSubsetMin = lastSubsetMinTemp;
        }
        if (lastSubsetMin[total] != coinMaxValue) {
            return lastSubsetMin[total];
        }
        return -1;
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
}
