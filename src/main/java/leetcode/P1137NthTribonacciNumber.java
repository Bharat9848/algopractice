package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * The Tribonacci sequence Tn is defined as follows:
 *
 * T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.
 *
 * Given n, return the value of Tn.
 *
 * Input: n = 4
 * Output: 4
 * Explanation:
 * T_3 = 0 + 1 + 1 = 2
 * T_4 = 1 + 1 + 2 = 4
 *
 * Example 2:
 *
 * Input: n = 25
 * Output: 1389537
 *
 *
 *
 * Constraints:
 *
 *     0 <= n <= 37
 *     The answer is guaranteed to fit within a 32-bit integer, ie. answer <= 2^31 - 1.
 */
public class P1137NthTribonacciNumber {
    int tribonacci(int n) {
        int tribNMinus3 = 0,tribNMinus2=1, tribNMinus1=1;
        if(n==0){return tribNMinus3;}
        for (int i = 3; i <=n; i++) {
            int next = tribNMinus3 + tribNMinus2 + tribNMinus1;
            tribNMinus3 = tribNMinus2;
            tribNMinus2 = tribNMinus1;
            tribNMinus1 = next;
        }

        return tribNMinus1;
    }
    @ParameterizedTest
    @CsvSource({"4,4", "25,1389537","0,0","1,1","2,1","3,2"})
    void test(int n, int expected){
        Assert.assertEquals(expected, tribonacci(n));
    }
}
