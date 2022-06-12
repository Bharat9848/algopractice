package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A string s is called good if there are no two different characters in s that have the same frequency.
 *
 * Given a string s, return the minimum number of characters you need to delete to make s good.
 *
 * The frequency of a character in a string is the number of times it appears in the string. For example, in the string "aab", the frequency of 'a' is 2, while the frequency of 'b' is 1.
 *
 * Input: s = "aab"
 * Output: 0
 * Explanation: s is already good.
 *
 * Input: s = "aaabbbcc"
 * Output: 2
 * Explanation: You can delete two 'b's resulting in the good string "aaabcc".
 * Another way it to delete one 'b' and one 'c' resulting in the good string "aaabbc".
 *
 * Input: s = "ceabaacb"
 * Output: 2
 * Explanation: You can delete both 'c's resulting in the good string "eabaab".
 * Note that we only care about characters that are still in the string at the end (i.e. frequency of 0 is ignored).
 * Constraints:
 *
 *     1 <= s.length <= 105
 *     s contains only lowercase English letters.
 */
class P1634MinCharDeleteToMakeStringGood {

    int minDeletion(String s){
        char[] strChars = s.toCharArray();
        Arrays.sort(strChars);
        int minDeletion = 0;
        Set<Integer> freqSet = new HashSet<>(); // @TODO instead of hashset we can use some other data structure which will remove nested while loop.
        int currentFreq = 0;
        for (int i = 0; i < strChars.length; i++) {
            currentFreq++;
            if(i == strChars.length-1 || strChars[i] != strChars[i+1]){
                if(!freqSet.contains(currentFreq)){
                    freqSet.add(currentFreq);
                }else{
                    while(currentFreq > 0 && freqSet.contains(currentFreq)){
                        minDeletion++;
                        currentFreq--;
                    }
                    if(currentFreq > 0){
                        freqSet.add(currentFreq);
                    }
                }
                currentFreq = 0;
            }
        }
        return minDeletion;
    }

    @ParameterizedTest
    @CsvSource({
            "a,0",
            "ab,1",
            "ceabaacb,2",
            "aaabbbcc,2",
            "aab,0"
    })
    void test(String s, int expected){
        Assertions.assertEquals(expected, minDeletion(s));
    }
}
