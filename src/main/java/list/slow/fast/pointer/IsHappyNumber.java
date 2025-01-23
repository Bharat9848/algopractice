package list.slow.fast.pointer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.Set;

/**
 * Write an algorithm to determine if a number nn is a happy number.
 *
 * We use the following process to check if a given number is a happy number:
 *
 *     Starting with the given number nn, replace the number with the sum of the squares of its digits.
 *     Repeat the process until:
 *         The number equals 11, which will depict that the given number nn is a happy number.
 *         The number enters a cycle, which will depict that the given number nn is not a happy number.
 *
 * Return TRUE if nn is a happy number, and FALSE if not.
 *
 * Constraints
 *
 *     1≤1≤ nn ≤231−1≤231−1
 */
public class IsHappyNumber {

    public static boolean isHappyNumber(int n) {
        int slow = nextSlow(n), fast = nextFast(n);
        while(slow != fast){
            slow = nextSlow(slow);
            fast = nextFast(fast);
            if(fast == 1){
                break;
            }
        }
        return fast == 1;
    }
    private static int nextSlow(int no){
        int result = 0;
        while(no > 0){
            int digit = no % 10;
            result += digit * digit;
            no = no/10;
        }
        return result;
    }

    private static int nextFast(int no){
        return nextSlow(nextSlow(no));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "19|true",
            "23|true",
            "2|false",
            "1|true"
    })
    void test(int number, boolean expected){
        Assertions.assertEquals(expected, isHappyNumber(number));
    }
}
