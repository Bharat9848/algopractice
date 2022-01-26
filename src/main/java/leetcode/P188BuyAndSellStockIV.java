package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.
 *
 * Find the maximum profit you can achieve. You may complete at most k transactions.
 *
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 *
 *
 *
 * Example 1:
 *
 * Input: k = 2, prices = [2,4,1]
 * Output: 2
 * Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
 *
 * Example 2:
 *
 * Input: k = 2, prices = [3,2,6,5,0,3]
 * Output: 7
 * Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 *
 *
 *
 * Constraints:
 *
 *     0 <= k <= 100
 *     0 <= prices.length <= 1000
 *     0 <= prices[i] <= 1000
 */
class P188BuyAndSellStockIV {

    int maxProfit(int k, int[] prices) {
        int[][] maxTransactionToDays = new int[k+1][prices.length+1];
        for (int trans = 1; trans < k+1; trans++) {
            for(int day = trans*2; day <= prices.length; day++){
                int fromDay = (trans-1)*2+1;
                int previousDaySameNoOfTransaction = maxTransactionToDays[trans][day - 1];
                int currentDayTransaction = calculateTransaction(prices, fromDay, day, maxTransactionToDays, trans);
                maxTransactionToDays[trans][day] = Math.max(currentDayTransaction, previousDaySameNoOfTransaction);
            }
        }
        return IntStream.range(0, k+1).map(row -> maxTransactionToDays[row][prices.length]).max().orElseThrow();
    }

    private int calculateTransaction(int[] prices, int fromDay, int toDay, int[][] maxTransactionToDays, int trans) {
        int max = 0;
        for (int i = fromDay; i < toDay; i++) {
            if(prices[i-1] <= prices[toDay-1]){
                max = Math.max((prices[toDay-1] - prices[i-1])+maxTransactionToDays[trans-1][i-1], max);
            }
        }
        return max;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2|2,4,1|2",
            "2|1,2,4,2,5,7,2,4,9,0|13",
            "2|8,6,4,3,3,2,3,5,8,3,8,2,6|11",
           "2|3,2,6,5,0,3|7",

    })
    void test(int k, String priceStr, int expected){
        Assert.assertEquals(expected, maxProfit(k, Arrays.stream(priceStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
