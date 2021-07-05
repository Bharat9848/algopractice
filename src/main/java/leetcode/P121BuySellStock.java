package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 *
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
 *
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 *
 *
 *
 * Example 1:
 *
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 *
 * Example 2:
 *
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 *

 Constraints:

 1 <= prices.length <= 105
 0 <= prices[i] <= 104

 */
class P121BuySellStock {

    int maxProfit(int[] prices) {
        int profit = 0,glblMinIndex=0, glblMaxIndex=0;
        for (int i = 0; i < prices.length; i++) {
            if(prices[i] < prices[glblMinIndex]){
                glblMinIndex = i;
            }
            if(glblMinIndex > glblMaxIndex || prices[i] > prices[glblMaxIndex]){
                glblMaxIndex = i;
            }
            if(glblMinIndex < glblMaxIndex && profit < prices[glblMaxIndex]-prices[glblMinIndex]){
                profit = prices[glblMaxIndex]-prices[glblMinIndex];
            }
        }
        return profit;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "7,1,5,3,6,4;5",
            "7,1,5,3,6,4,0,10;10",
            "7,6,3,1;0"
    })
    void test(String arrStr, int expected){
        int[] prices = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, maxProfit(prices));
    }
}
