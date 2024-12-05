package dynamic.programming.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a number n, calculate the corresponding Tribonacci number. The Tribonacci sequence Tn is defined as:
 * T0=0, T1=1, T2=1, and  Tn+3=Tn+Tn+1+Tn+2, , for n>=0
 *
 * The input number, n, is a non-negative integer.
 *
 * Constraints:
 *
 *     0≤0≤ n ≤37≤37
 *     The answer is guaranteed to fit within a 32-bit integer, i.e., answer ≤231−1≤231−1
 */
public class TribonacciNumber {

    public static int findTribonacci(int n) {

        if(n<=0){
            return 0;
        }
        if(n == 1||n==2){
            return 1;
        }

        int last=1, lastMinus1=1, lastMinus2=0;
        int result=0;
        for (int i = 3; i <= n; i++) {
            result = last + lastMinus1 + lastMinus2;
            lastMinus2 = lastMinus1;
            lastMinus1 = last;
            last = result;
        }

        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0|0",
            "1|1",
            "2|1",
            "3|2",
            "4|4",
            "5|7",
            "6|13",
            "7|24",
            "10|149"
    })
    void test(int n, int expected){
        Assertions.assertEquals(expected, findTribonacci(n));
    }
}
