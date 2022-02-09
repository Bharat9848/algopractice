package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a string num which represents an integer, return true if num is a strobogrammatic number.
 *
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 *
 * Input: num = "69"
 * Output: true
 * Input: num = "88"
 * Output: true
 * Input: num = "962"
 * Output: false
 * Constraints:
 *
 *     1 <= num.length <= 50
 *     num consists of only digits.
 *     num does not contain any leading zeros except for zero itself.
 */
public class P246StrobogrammaticNumber {
    boolean isStrobogrammaticNumber(String num){
        boolean isStrobo = true;
        for (int i = 0; i < num.length()/2; i++) {
            isStrobo &= isStrobo(num.charAt(i), num.charAt(num.length()-1-i));
        }
        if(num.length()%2==1){
            char midChar = num.charAt(num.length()/2 + 1-1);
            isStrobo &= (midChar=='0' || midChar=='8'||midChar=='1');
        }
        return isStrobo;
    }

    private boolean isStrobo(char fromFront, char fromLast) {
        return (fromFront=='9' && fromLast == '6') || (fromFront=='6' && fromLast =='9') || (fromFront == '8' && fromLast == '8') || (fromFront=='0' && fromLast=='0'|| ((fromFront=='1' && fromLast=='1')));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "69|true",
            "88|true",
            "808|true",
            "818|true",
            "962|false",
            "1|true",
            "11|true",
    })
    void test(String num, boolean expected){
        Assertions.assertEquals(expected, isStrobogrammaticNumber(num));
    }
}
