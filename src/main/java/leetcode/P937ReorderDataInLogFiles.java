package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * You are given an array of logs. Each log is a space-delimited string of words, where the first word is the identifier.
 *
 * There are two types of logs:
 *
 *     Letter-logs: All words (except the identifier) consist of lowercase English letters.
 *     Digit-logs: All words (except the identifier) consist of digits.
 *
 * Reorder these logs so that:
 *
 *     The letter-logs come before all digit-logs.
 *     The letter-logs are sorted lexicographically by their contents. If their contents are the same, then sort them lexicographically by their identifiers.
 *     The digit-logs maintain their relative ordering.
 *
 * Return the final order of the logs.
 *
 *
 *
 * Example 1:
 *
 * Input: logs = ["dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"]
 * Output: ["let1 art can","let3 art zero","let2 own kit dig","dig1 8 1 5 1","dig2 3 6"]
 * Explanation:
 * The letter-log contents are all different, so their ordering is "art can", "art zero", "own kit dig".
 * The digit-logs have a relative order of "dig1 8 1 5 1", "dig2 3 6".
 *
 * Example 2:
 *
 * Input: logs = ["a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo"]
 * Output: ["g1 act car","a8 act zoo","ab1 off key dog","a1 9 2 3 1","zo4 4 7"]
 *
 *
 *
 * Constraints:
 *
 *     1 <= logs.length <= 100
 *     3 <= logs[i].length <= 100
 *     All the tokens of logs[i] are separated by a single space.
 *     logs[i] is guaranteed to have an identifier and at least one word after the identifier.
 */
public class P937ReorderDataInLogFiles {
    String[] reorderLogFiles(String[] logs) {
        Comparator<String> logComparator = (log1, log2) -> {
            if (isDigitLog(log1) && isDigitLog(log2)) {
                return 0;
            } else if (isDigitLog(log1)){
                return 1;
            }else if(isDigitLog(log2)) {
                return -1;
            } else {
                int spaceIndex = log1.indexOf(" ");
                String value = log1.substring(spaceIndex + 1);
                String identifier = log1.substring(0, spaceIndex);

                int spaceIndex2 = log2.indexOf(" ");
                String value2 = log2.substring(spaceIndex2 + 1);
                String identifier2 = log2.substring(0, spaceIndex2);

                int compareResult = value.compareTo(value2);
                return compareResult == 0 ? identifier.compareTo(identifier2) : compareResult;
            }
        };


        Arrays.sort(logs, logComparator);
        return logs;
    }

    private boolean isDigitLog(String log2) {
        return Character.isDigit(log2.charAt(log2.indexOf(' ') + 1));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "dig1 8 1 5 1,let1 art can,dig2 3 6,let2 own kit dig,let3 art zero|let1 art can,let3 art zero,let2 own kit dig,dig1 8 1 5 1,dig2 3 6",
            "let1 art can,alet art can,let2 own kit dig,let3 art zero|alet art can,let1 art can,let3 art zero,let2 own kit dig",
            "dig1 b,dig2 a|dig2 a,dig1 b",
            "dig2 b,dig1 b|dig1 b,dig2 b",
            "dig1 90,dig2 89|dig1 90,dig2 89",
            "dig2 89,dig1 a|dig1 a,dig2 89"
    })
    void test(String beforStr, String afterStr){
        String[] after = afterStr.split(",");
        Assert.assertArrayEquals(after, reorderLogFiles(beforStr.split(",")));
    }
}
