package two.pointers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a string word and an abbreviation abbr, return TRUE if the abbreviation matches the given string. Otherwise, return FALSE.
 *
 * A certain word "calendar" can be abbreviated as follows:
 *
 *     "cal3ar" ("cal end ar" skipping three characters "end" from the word "calendar" still matches the provided abbreviation)
 *
 *     "c6r" ("c alenda r" skipping six characters "alenda" from the word "calendar" still matches the provided abbreviation)
 *
 * The word "internationalization" can also be abbreviated as "i18n" (the abbreviation comes from skipping 18 characters in "internationalization", leaving the first and last letters "i" and "n").
 *
 * The following are not valid abbreviations:
 *
 *     "c06r" (has leading zeroes)
 *
 *     "cale0ndar" (replaces an empty string)
 *
 *     "c24r" ("c al enda r" the replaced substrings are adjacent)
 *
 * Constraints:
 *
 *     1≤1≤ word.length ≤20≤20
 *
 *     word consists of only lowercase English letters.
 *
 *     1≤1≤ abbr.length ≤10≤10
 *
 *     abbr consists of lowercase English letters and digits.
 *
 *     All the integers in abbr will fit in a 3232–bit integer.
 */
public class ValidAbbreviation {
    public static boolean validWordAbbreviation(String word, String abbr) {
        char[] wordChars = word.toCharArray();
        int sw = 0, ew = word.length() -1;
        char[] abbrChars = abbr.toCharArray();
        int i = 0;
        for (; i < abbr.length() && sw < word.length(); ) {
            if(Character.isDigit(abbrChars[i])){
                int j = i;
                for (; j < abbrChars.length && Character.isDigit(abbrChars[j]); j++) {}
                int no = Integer.parseInt(abbr.substring(i, j));
                i = j;
                sw =  sw + no;
            } else {
                if(abbrChars[i] != wordChars[sw]){
                    return false;
                }
                i++;
                sw++;
            }
        }
        return sw == word.length() && i == abbr.length();
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "subsequences|3sequ5es|false",
            "subsequences|3sequ3es|true",
            "innovation|in5ion|true",
            "internationalization|i18n|true",

    })
    void test(String word, String abbr, boolean expected){
        Assertions.assertEquals(expected, validWordAbbreviation(word, abbr));
    }
}
