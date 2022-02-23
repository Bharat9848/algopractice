package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a string columnTitle that represents the column title as appear in an Excel sheet, return its corresponding column number.
 *
 * For example:
 *
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 * Input: columnTitle = "A"
 * Output: 1
 * Input: columnTitle = "AB"
 * Output: 28
 * Input: columnTitle = "ZY"
 * Output: 701
 *Input: columnTitle = "AAA"
 *  * Output: 703
 *
 *
 * Constraints:
 *
 *     1 <= columnTitle.length <= 7
 *     columnTitle consists only of uppercase English letters.
 *     columnTitle is in the range ["A", "FXSHRXW"].
 */
class P171ExcelSheetColumnNumber {
    int titleToNumber(String columnTitle) {
        int colNo = 0;
        for (int i = 0; i <columnTitle.length() ; i++) {
            int val = (columnTitle.charAt(i) - 'A' +1) * (int)Math.pow(26,(columnTitle.length()-1 - i));
            colNo+=val;
        }
        return colNo;
    }
    @ParameterizedTest
    @CsvSource({"AA,27",
            "AB,28",
            "A,1",
            "AB,28",
            "ZY,701",
            "ZZ,702",// 26*26+26 = 702
            "AAA,703",// 703-27 = 676
    })
    void test(String colTitle, int expected){
        Assertions.assertEquals(expected, titleToNumber(colTitle));
    }
}
