package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an integer n, return the least number of perfect square numbers that sum to n.
 *
 * A perfect square is an integer that is the square of an integer; in other words, it is the product of some integer with itself. For example, 1, 4, 9, and 16 are perfect squares while 3 and 11 are not.
 *
 * Input: n = 12
 * Output: 3
 * Explanation: 12 = 4 + 4 + 4.
 *
 * Input: n = 13
 * Output: 2
 * Explanation: 13 = 4 + 9.
 *
 * Constraints:
 *
 *     1 <= n <= 104
 */
public class P279PerfectSquares {

    public int numSquares(int n) {
        Map<Integer, Integer> minSquareMap = new HashMap<>();
        return numSquares(n, minSquareMap);

    }

    private int numSquares(int n, Map<Integer, Integer> minSquareMap) {
        if(minSquareMap.get(n) != null){
            return minSquareMap.get(n);
        }else{
            int result = Integer.MAX_VALUE;
            int nearestSquareRoot = (int)Math.floor(Math.sqrt(n));
            if(nearestSquareRoot*nearestSquareRoot == n){
                result = 1;
            }else{
                for (int i = nearestSquareRoot; i > 0; i--) {
                    int noOfSquares = 1 + numSquares(n-(i*i), minSquareMap);
                    if(noOfSquares<result){
                        result=noOfSquares;
                    }
                }
            }
            minSquareMap.put(n, result);
            return result;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "12|3",
            "2|2",
            "13|2",
            "27|3",
            "59|3"
    })
    void testHappyCase(int n, int expected){
        Assert.assertEquals(expected, numSquares(n));
    }
}
