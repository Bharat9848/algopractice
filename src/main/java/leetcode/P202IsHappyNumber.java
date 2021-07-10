package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.Set;

/**
 *Write an algorithm to determine if a number n is happy.
 *
 * A happy number is a number defined by the following process:
 *
 *     Starting with any positive integer, replace the number by the sum of the squares of its digits.
 *     Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
 *     Those numbers for which this process ends in 1 are happy.
 *
 * Return true if n is a happy number, and false if not.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 19
 * Output: true
 * Explanation:
 * 12 + 92 = 82
 * 82 + 22 = 68
 * 62 + 82 = 100
 * 12 + 02 + 02 = 1
 *
 *
 * Example 2:
 *
 * Input: n = 2
 * Output: false
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 231 - 1
 */
class P202IsHappyNumber {
    boolean isHappy(int n){
        Set<Integer> seen = new HashSet<>();
        int curr = n;
        while (!seen.contains(curr)){
            seen.add(curr);
            int next = nextSumOfSquare(curr);
            if(next==1){
                return true;
            }
            curr = next;
        }
        System.out.println(seen);
        return false;
    }

    private int nextSumOfSquare(int curr) {
        int next = 0;
        int temp = curr;
        while (temp/10 > 0)
        {
            int digit = temp%10;
            next += digit*digit;
            temp = temp/10;
        }
        next += temp*temp;
        return next;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "100;true",
            "21;false"
    })
    void test(int no, boolean expected){
        Assert.assertEquals(expected, isHappy(no));
    }
}
