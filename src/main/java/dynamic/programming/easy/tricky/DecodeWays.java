package dynamic.programming.easy.tricky;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a string that has only positive digits, your task is to decode the string and determine the number of possible ways to decode it.
 *
 * Consider the input string as an encoded message, where each digit in the string represents an alphabet from A to Z. For reference, let’s look at the mapping below:
 *
 * "1" represents "A"
 *
 * "2" represents "B"
 *
 * "3" represents "C"
 *
 * ……
 *
 * "26" represents "Z"
 *
 * How to perform the decode operation?
 *
 * To decode an encoded message, we have to check whether or not each digit in the string can be mapped onto the mapping above. There can be multiple ways to decode a string.
 *
 * For example, the string "231012" can be decoded in the following ways:
 *
 *     Option 1 →→ "2", "3", "10", "1", "2" →→ "B", "C", "J", "A", "B"
 *
 *     Option 2 →→ "2", "3", "10", "12" →→ "B", "C", "J", "L"
 *
 *     Option 3 →→ "23", "10", "1", "2" →→ "W", "J", "A", "B"
 *
 *     Option 4 →→ "23", "10", "12" →→ "W", "J", "L"
 *
 *     Note: In the mapping above, we haven't included "01" since it's an invalid mapping. It cannot be mapped onto "A" since "01" and "1" are different.
 *
 * Constraints:
 *
 *     1≤1≤ decodeStr.length ≤100≤100
 *
 *     The string decodeStr contains only digits and may contain leading zero(s).
 *
 *     noOfWays(n) =  noOfWays(n-1) + if( d(n-1)*10 + d(n)) < 26 && d(n-1) != 0) noOfWays(n-2)
 */
public class DecodeWays {

    public static int numOfDecodings(String decodeStr) {
        if(decodeStr == null || decodeStr.isEmpty()){
            return 0;
        }
        if(decodeStr.length() == 1){
            return decodeSingleDigit(decodeStr, 0);
        }
        if(decodeStr.length() == 2){
            return decodeTwoDigit(decodeStr, 1);
        }
        int prevLast = decodeSingleDigit(decodeStr, 0), last = decodeTwoDigit(decodeStr, 1), current = 0;
        for (int i = 2; i < decodeStr.length(); i++) {
            current = 0;
            if (decodeStr.charAt(i) - '0' > 0) {
                current = last;
            }
            int twoDigitNum = Integer.parseInt(decodeStr.substring(i - 1, i + 1));
            if (twoDigitNum >= 10 && twoDigitNum <= 26) {
                current += prevLast;
            }
            prevLast = last;
            last = current;
        }


        return current;
    }

    private static int decodeSingleDigit(String decodeStr, int index){
        return decodeStr.charAt(index) - '0' > 0 ? 1: 0;
    }

    private static int decodeTwoDigit(String decodeStr, int index){
        int x = 0;
        int twoDigitNum = Integer.parseInt(decodeStr.substring(index-1, index+1));
        if (twoDigitNum >= 11 && twoDigitNum <= 26 && twoDigitNum != 20){
            x = 2;
        } else if(twoDigitNum != 0 ) {
            x = decodeStr.charAt(index-1) - '0' > 0 ? 1: 0;
        }
        return x;

    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "203213183778637737854989168352535835759229314758443166994753887108546366156888324|768",
            "012|0",
            "20|1",
            "12012|2",
            "100|0",
            "10|1",
            "1001234|0",
            "22|2",
            "226|3",
            "96|1",
            "999|1",
            "231|2",
            "2310|2",
            "231012|4",
            "1111|5"
    })
    void test(String decodeStr, int expected){
//        Assertions.assertEquals(expected, numOfDecodingsRecursive(decodeStr));
        Assertions.assertEquals(expected, numOfDecodings(decodeStr));
    }
}
