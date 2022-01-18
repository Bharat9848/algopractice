package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * You have a long flowerbed in which some of the plots are planted, and some are not. However, flowers cannot be planted in adjacent plots.
 *
 * Given an integer array flowerbed containing 0's and 1's, where 0 means empty and 1 means not empty, and an integer n, return if n new flowers can be planted in the flowerbed without violating the no-adjacent-flowers rule.
 *
 *
 *
 * Example 1:
 *
 * Input: flowerbed = [1,0,0,0,1], n = 1
 * Output: true
 *
 * Example 2:
 *
 * Input: flowerbed = [1,0,0,0,1], n = 2
 * Output: false
 *
 *
 *
 * Constraints:
 *
 *     1 <= flowerbed.length <= 2 * 104
 *     flowerbed[i] is 0 or 1.
 *     There are no two adjacent flowers in flowerbed.
 *     0 <= n <= flowerbed.length
 */
public class P605CanPlaceFlower {
    boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        for (int i = 0; i < flowerbed.length; ) {
            if(flowerbed[i] == 0 && (i-1 < 0 ||flowerbed[i-1] ==0) && (i+1== flowerbed.length || flowerbed[i+1] ==0)){
                i=i+2;
                count++;
            }else{
                i=i+1;
            }
        }
        return count >= n;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,0,0,0,1|1|true",
            "1,0,0,0,1|2|false",
            "0,0,0|2|true",
            "0,0,0,0|2|true",
            "0,1,0,0|1|true",
    })
    void test(String arrStr, int n, boolean expected){
        int[] flowerBed = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, canPlaceFlowers(flowerBed, n));
    }
}
