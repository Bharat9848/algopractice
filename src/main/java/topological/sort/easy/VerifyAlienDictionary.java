package topological.sort.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * You’re given a list of words with lowercase English letters in a different order, written in an alien language. The order of the alphabet is some permutation of lowercase letters of the English language.
 *
 * We have to return TRUE if the given list of words is sorted lexicographically in this alien language.
 *
 * Constraints:
 *
 *     1≤1≤ words.length ≤103≤103
 *     1≤1≤ words[i].length ≤20≤20
 *     order.length ==26==26
 *     All the characters in words[i] and order are lowercase English letters.
 */
public class VerifyAlienDictionary {
    public static boolean verifyAlienDictionary(String[] words, String order) {
        boolean valid = true;
        Map<Character,Integer> chToIndex = new HashMap<>();
        for (int i = 0; i < order.length(); i++) {
          chToIndex.put(order.charAt(i), i);
        }
        for (int i = 0; i < words.length-1 && valid; i++) {
            String before = words[i];
            String after = words[i+1];
            int j = 0;
            for (; j < before.length() && j < after.length(); j++) {
                if(before.charAt(j) != after.charAt(j)){
                    valid = valid && chToIndex.get(before.charAt(j)) < chToIndex.get(after.charAt(j));
                    break;
                }
            }
            if(j == after.length() && j < before.length()){
                valid = false;
            }
        }
        return valid;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "whoami,who|oamiwh|false",
            "wrt,wrf,er,ett,rftt|hlabcdgiwjkmnopqerstfuvxyz|true",
            "alpha,bravo,charlie.delta|hlabcdgiwjkmnopqerstfuvxyz|true",
            "alpha,bravo,charlie.delta|hlabdcgiwjkmnopqerstfuvxyz|true",
            "alpha,bravo,charlie.delta,car|hlabcdgiwjkmnopqerstfuvxyz|true",
    })
    void test(String wordStr, String order, boolean expected){
        Assertions.assertEquals(expected, verifyAlienDictionary(wordStr.split(","), order));
    }
}
