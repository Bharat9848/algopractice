package two.pointers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a string num representing an integer, determine whether it is a strobogrammatic number. Return TRUE if the number is strobogrammatic or FALSE if it is not.
 *
 *     Note: A strobogrammatic number appears the same when rotated 180180180 degrees (viewed upside down). For example, “69” is strobogrammatic because it looks the same when flipped upside down, while “962” is not.
 *
 * Constraints:
 *
 *     1<=1<= num.length <=50<=50
 *
 *     num contains only digits.
 *
 *     num has no leading zeros except when the number itself is zero.
 */
public class StrobogrammaticNumber {
    public static boolean isStrobogrammatic (String num)
    {
        Map<Character, Character> strobommaticMap = createStrobogrammaticMap();
        char[] numChars = num.toCharArray();
        int start = 0, end = num.length() - 1;
        while (start < end){
            if(!strobommaticMap.containsKey(numChars[start]) || !strobommaticMap.containsKey(numChars[end])){
                return false;
            }else {
                if(numChars[start] == strobommaticMap.get(numChars[end])){
                    start++;
                    end--;
                }else {
                    return false;
                }
            }
        }
        return start > end || (numChars[start] == '0' || numChars[start] == '8' || numChars[start] == '1');
    }

    private static Map<Character, Character> createStrobogrammaticMap() {
        HashMap<Character, Character> map = new HashMap<>();
        map.put('6', '9');
        map.put('9', '6');
        map.put('0', '0');
        map.put('1', '1');
        map.put('8', '8');
        return map;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "9696|true",
            "961196116996889696888896968888969688966911961196|true",
            "808|true",
            "962|false",
            "000|true",
            "1|true",
            "639|false",
            "609|true",
            "689|true",
            "619|true",
    })
    void test(String no, boolean expected){
        Assertions.assertEquals(expected, isStrobogrammatic("" + no));
    }
}
