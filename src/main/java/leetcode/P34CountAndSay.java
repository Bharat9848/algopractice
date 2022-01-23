package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * The count-and-say sequence is a sequence of digit strings defined by the recursive formula:
 *
 *     countAndSay(1) = "1"
 *     countAndSay(n) is the way you would "say" the digit string from countAndSay(n-1), which is then converted into a different digit string.
 *
 * To determine how you "say" a digit string, split it into the minimal number of groups so that each group is a contiguous section all of the same character. Then for each group, say the number of characters, then say the character. To convert the saying into a digit string, replace the counts with a number and concatenate every saying.
 *
 * For example, the saying and conversion for digit string "3322251":
 *
 * Given a positive integer n, return the nth term of the count-and-say sequence.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 1
 * Output: "1"
 * Explanation: This is the base case.
 *
 * Example 2:
 *
 * Input: n = 4
 * Output: "1211"
 * Explanation:
 * countAndSay(1) = "1"
 * countAndSay(2) = say "1" = one 1 = "11"
 * countAndSay(3) = say "11" = two 1's = "21"
 * countAndSay(4) = say "21" = one 2 + one 1 = "12" + "11" = "1211"
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 30
 */
class P34CountAndSay {
    String countAndSay(int n) {
        String current = "1";
        StringBuilder sayAndCurrent = new StringBuilder("");
        for (int i = 1; i < n; i++) {
            int count = 1;
            for (int j = 0; j < current.length(); j++) {
                if(j+1 == current.length() || current.charAt(j) != current.charAt(j+1)){
                    sayAndCurrent.append(count).append(current.charAt(j));
                    count=1;
                }else{
                    count++;
                }
            }
            current = sayAndCurrent.toString();
//            System.out.println(current);
            sayAndCurrent = new StringBuilder();
        }
        return current;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2|11",
            "1|1",
            "4|1211",
    })
    void test(int n, String expected){
        Assert.assertEquals(expected, countAndSay(n));
    }
}
