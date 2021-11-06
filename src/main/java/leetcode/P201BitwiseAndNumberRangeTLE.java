package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given two integers left and right that represent the range [left, right], return the bitwise AND of all numbers in this range, inclusive.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: left = 5, right = 7
 * Output: 4
 * <p>
 * Example 2:
 * <p>
 * Input: left = 0, right = 0
 * Output: 0
 * <p>
 * Example 3:
 * <p>
 * Input: left = 1, right = 2147483647
 * Output: 0
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * bit wise and is commutative
 * 0 <= left <= right <= 2^31 - 1
 * <p>
 * 0000
 * 0001
 * 0010
 * 0011
 * 0100
 * 0101
 * 0110
 * 0111
 * 1000
 * 1001
 * 1001
 *
 */
public class P201BitwiseAndNumberRangeTLE {
    int rangeBitwiseAnd(int left, int right) {
            if (left == right) {
                return left;
            } else {
                int i = 0;
                while (left > 0 && left < Math.pow(2, i)) {
                    i++;
                }
                int twoPow = (int) Math.pow(2, i);
                if (left <= twoPow && right >= twoPow) {
                    return 0;
                } else {
                    int result = left;
                    for (int j = left + 1; j <= right; j++) {
                        result &= j;
                    }
                    return result;
                }
            }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {"5;7;4", "0;0;0", "1;2147483647;0", "1;1;1", "4;4;4"})
    void test(int left, int right, int expected) {
        Assert.assertEquals(expected, rangeBitwiseAnd(left, right));
    }
}
