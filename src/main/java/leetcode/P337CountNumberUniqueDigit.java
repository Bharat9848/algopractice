package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given an integer n, return the count of all numbers with unique digits, x, where 0 <= x < 10n.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 2
 * Output: 91
 * Explanation: The answer should be the total numbers in the range of 0 ≤ x < 100, excluding 11,22,33,44,55,66,77,88,99
 *
 * Example 2:
 *
 * Input: n = 0
 * Output: 1
 *
 *
 *
 * Constraints:
 *
 *     0 <= n <= 8
 */
public class P337CountNumberUniqueDigit {
    public int countNumbersWithUniqueDigits(int n) {

        if(n == 0){
            return 1;
        } else {
            int prodPossibilities = 1;
            int noOfPossibilities = 10;
            for(int noOfDigits = n; noOfDigits > 0; noOfDigits--){
                if (noOfDigits == n){
                    prodPossibilities = prodPossibilities * (noOfPossibilities - 1);
                } else {
                    prodPossibilities = prodPossibilities * noOfPossibilities;
                }
                noOfPossibilities--;
            }
            prodPossibilities =  prodPossibilities + countNumbersWithUniqueDigits(n-1);
            return prodPossibilities;
        }

    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0|1",
            "2|91"
    })
    public void test(int n, int expected){
        Assertions.assertEquals(expected, countNumbersWithUniqueDigits(n));
    }
}
