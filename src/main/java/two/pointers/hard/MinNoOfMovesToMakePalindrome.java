package two.pointers.hard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a string s, return the minimum number of moves required to transform s into a palindrome. In each move, you can swap any two adjacent characters in s.
 *
 *     Note: The input string is guaranteed to be convertible into a palindrome.
 *
 * Constraints:
 *
 *     1≤1≤ s.length ≤2000≤2000
 *
 *     s consists of only lowercase English letters.
 *
 *     s is guaranteed to be converted into a palindrome in a finite number of moves.
 */
public class MinNoOfMovesToMakePalindrome {
    public static int minMovesToMakePalindrome(String s) {
        char[] sChars = s.toCharArray();
        int move = 0;
        int left = 0;
        int right = s.length()-1;
        while (left < right){
            if(sChars[left] == sChars[right]){
                left++;
                right--;
            } else {
                int search = right;
                for (; sChars[search] != sChars[left] && search > left; search--) {}
                if(search == left){
                    int mid = left + (right - left)/2;
                    for (int i = left; i < mid; i++) {
                        move++;
                        swapSchars(sChars, i , i+1);
                    }
                } else {
                    for (int i = search; i < right ; i++) {
                        move++;
                        swapSchars(sChars, i, i+1);
                    }
                    left++;
                    right--;
                }
            }
        }
        return move;
    }

    private static void swapSchars(char[] sChars, int first, int sec) {
        char temp = sChars[first];
        sChars[first] = sChars[sec];
        sChars[sec] = temp;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "acaaac|1", "lstzgogeivvvgctrsbusvihcvbzzvsscycituqylxjoknhrcvuuzbtzlitovccykivkuavxzgvlmtiqtnmshonqclcknptabpewxxtqnvreoiyucsujxooxhoxwqirtqfzbgjgpsvlsmqfbmcsqlxirogcbwuiepmzifcljytcpougvfrihfjztikkjjtoyszhmfdqculcrmlwdameewzfznakyfgktonpqqouhnvvddtsoaxtbwhhyekokxpizxlhpdfghctuigaqcvpqsopgjrxoiroyfitofwojveowkqvosyqjuesovhqhrvryvdjbtykrvdrvrlyrayccfzzvcuagzziwmtbnrlvyytomwlzqqyworqmwuhnorihzgnrwbqbyqakrmfkdhutjlvjikxybztupvwqrrnxnfybbshxrbuwyxxgbrkbkvwutrcdtcryffdclccfdgljubxxefnxfvqfvwmtqyehslovoskmxdgiuncvlravylmdenqgzbirfvvakflzstzysfdmziurzmunxvijdlkjyrxlyndzleujdzhlcvuffbwsutlkbpqihdmqlbphvnhqgctogiqzsiwttbfycjbbwhhpfduuldcjwinrzuvspmumgzujyyhtqcekfrvcihnhhshnvipzjtjocmazghntrnruwkmk|17161",
            "arcacer|4",
            "ntiin|1",
            "iit|1",
            "baaaa|2",
            "ccxx|2"
    })
    void test(String s, int expected){
        Assertions.assertEquals(expected, minMovesToMakePalindrome(s));
    }
}
