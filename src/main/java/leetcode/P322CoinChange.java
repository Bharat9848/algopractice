package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * ou are given coins of different denominations and a total amount of money amount.
 * Write a function to compute the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.

 Example 1:

 Input: coins = [1, 2, 5], amount = 11
 Output: 3
 Explanation: 11 = 5 + 5 + 1
 Example 2:

 Input: coins = [2], amount = 3
 Output: -1
 Note:
 You may assume that you have an infinite number of each kind of coin.


 Fewest No to reach amount is minimun of one plus the no of coins to reach forEach(amount - denomination)


 * Created by bharat on 29/5/18.
 */
public class P322CoinChange {

    public int coinChange(int[] coins, int amount) {
        if(amount<0){
            return -1;
        }
        if(amount == 0){
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            if(amount==coins[i]){
                return 1;
            }
            int subCoin = coinChange(coins,amount-coins[i]);
            if(subCoin!=-1 && subCoin+1<min){
                min = subCoin + 1;
            }
        }
        return min==Integer.MAX_VALUE?-1:min;
    }

    public int coinChangeIterative(int[] coins, int amount) {
            int[] amountToNoOfCoins = new int[amount + 1];
            amountToNoOfCoins[0] = 0;
            for (int i = 1; i < amountToNoOfCoins.length; i++) {
                int min = Integer.MAX_VALUE;
                for (int j = 0; j < coins.length; j++) {
                    if (i-coins[j]<0 || amountToNoOfCoins[i-coins[j]] == -1){
                        continue;
                    }else{
                        if(min>amountToNoOfCoins[i-coins[j]]+1){
                            min = amountToNoOfCoins[i-coins[j]]+1;
                        }
                    }
                }
                amountToNoOfCoins[i] = min==Integer.MAX_VALUE?-1:min;
            }
            System.out.println(Arrays.toString(amountToNoOfCoins));
            return amountToNoOfCoins[amount];
    }

    @Test
    public void test(){
        Assert.assertEquals(2,coinChangeIterative(new int[]{2,5},10));
        Assert.assertEquals(2,coinChangeIterative(new int[]{1,2,5},10));
        Assert.assertEquals(2,coinChangeIterative(new int[]{1,2,5},3));
        Assert.assertEquals(4,coinChangeIterative(new int[]{1,2,5},20));

        Assert.assertEquals(-1,coinChangeIterative(new int[]{13,17},18));
    }
}
