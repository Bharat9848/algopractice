package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.stream.IntStream;

/*
You have an inventory of different colored balls, and there is a customer that wants orders balls of any color.

The customer weirdly values the colored balls. Each colored ball's value is the number of balls of that color you currently have in your inventory. For example, if you own 6 yellow balls, the customer would pay 6 for the first yellow ball. After the transaction, there are only 5 yellow balls left, so the next yellow ball is then valued at 5 (i.e., the value of the balls decreases as you sell more to the customer).

You are given an integer array, inventory, where inventory[i] represents the number of balls of the ith color that you initially own. You are also given an integer orders, which represents the total number of balls that the customer wants. You can sell the balls in any order.

Return the maximum total value that you can attain after selling orders colored balls. As the answer may be too large, return it modulo 109 + 7.



Example 1:

Input: inventory = [2,5], orders = 4
Output: 14
Explanation: Sell the 1st color 1 time (2) and the 2nd color 3 times (5 + 4 + 3).
The maximum total value is 2 + 5 + 4 + 3 = 14.

Example 2:

Input: inventory = [3,5], orders = 6
Output: 19
Explanation: Sell the 1st color 2 times (3 + 2) and the 2nd color 4 times (5 + 4 + 3 + 2).
The maximum total value is 3 + 2 + 5 + 4 + 3 + 2 = 19.

Example 3:

Input: inventory = [2,8,4,10,6], orders = 20
Output: 110

Example 4:

Input: inventory = [1000000000], orders = 1000000000
Output: 21
Explanation: Sell the 1st color 1000000000 times for a total value of 500000000500000000. 500000000500000000 modulo 109 + 7 = 21.

Constraints:
    1 <= inventory.length <= 105
    1 <= inventory[i] <= 109
    1 <= orders <= min(sum(inventory[i]), 109)

for n orders
    find max - for x inventory  ---> solution 0(nx)

for inventory sorted = xlogx
for n orders = nlog x
    add value and decrement 0(1)
    compare 1 and 2 each step if x1 < x2 do binary search to position right place else do nothing log(x)
 */
public class P1648SellDiminishedValueColorNodesTLE {


     public int maxProfit(int[] inventory, int orders) {
        long modulo = (long)Math.pow(10,9);
        long result = 0;
        Arrays.sort(inventory);
        int lastIndex = inventory.length -1;
        if(lastIndex-1 >= 0){
            orders = orders - (inventory[lastIndex] -  inventory[lastIndex-1]);
            result += IntStream.iterate(inventory[lastIndex],no -> no > inventory[lastIndex-1], no -> no-1 ).sum();
//                    ((long)(inventory[lastIndex]* (inventory[lastIndex]+1))/2) - ((long)inventory[lastIndex-1] *(inventory[lastIndex - 1] +1)/2);
            inventory[lastIndex] = inventory[lastIndex-1];
        }
        for (int i = 0; i < orders; i++) {
            result += inventory[lastIndex];
            inventory[lastIndex]--;
            if(lastIndex-1 >= 0 && inventory[lastIndex] < inventory[lastIndex - 1]){
                if(lastIndex-2 >= 0 && inventory[lastIndex] < inventory[lastIndex - 2]){
                   int pos = binarySearchPos(inventory, 0, lastIndex - 2, inventory[lastIndex - 1]);
                   swap(inventory, pos, lastIndex);
                }else{
                    swap(inventory, lastIndex, lastIndex - 1);
                }
            }
        }
        return (int)((result > modulo)? (result % (modulo+7)):result);
    }

    private int binarySearchPos(int[] inventory, int beg, int end, int ele) {

        while (beg < end){
            int mid = beg + ((end - beg)/2);
            if(inventory[mid] == ele){
                return mid;
            }else if(inventory[mid] < ele){
                beg = mid + 1;
            }else{
                end = mid;
            }
        }
        if(beg > 0 && beg < inventory.length-1){
            if(ele > inventory[beg] ){
                return beg + 1;
            }else{
                return beg;
            }
        }else{
            return beg;
        }
    }

    private void swap(int[] inventory, int bIndex, int aIndex) {
        int temp = inventory[aIndex];
        inventory[aIndex] = inventory[bIndex];
        inventory[bIndex] = temp;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {"" +
            "[3,5];6;19",
            "[2,5];4;14",
    "[2,8,4,10,6];20;110",
    "[1000000000];1000000000;21",
    "[497978859,167261111,483575207,591815159];836556809;373219333"}) //41 5,6,6,4,2
    void test(String inventoryStr, int order, int result){
        int[] inventory = Arrays.stream(inventoryStr.replace("[", "").replace("]", "").split(",")).mapToInt(Integer::parseInt).toArray();
        long start = System.currentTimeMillis();
        int actual = maxProfit(inventory, order);
        System.out.println(System.currentTimeMillis()-start);
        System.out.println(Long.MAX_VALUE);
        Assert.assertEquals(result, actual);
    }
}
