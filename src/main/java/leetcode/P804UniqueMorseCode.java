package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
International Morse Code defines a standard encoding where each letter is mapped to a series of dots and dashes, as follows: "a" maps to ".-", "b" maps to "-...", "c" maps to "-.-.", and so on.

For convenience, the full table for the 26 letters of the English alphabet is given below:

[".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]
Now, given a list of words, each word can be written as a concatenation of the Morse code of each letter. For example, "cab" can be written as "-.-..--...", (which is the concatenation "-.-." + ".-" + "-..."). We'll call such a concatenation, the transformation of a word.

Return the number of different transformations among all words we have.

Example:
Input: words = ["gin", "zen", "gig", "msg"]
Output: 2
Explanation:
The transformation of each word is:
"gin" -> "--...-."
"zen" -> "--...-."
"gig" -> "--...--."
"msg" -> "--...--."

There are 2 different transformations, "--...-." and "--...--.".
Note:

The length of words will be at most 100.
Each words[i] will have length in range [1, 12].
words[i] will only consist of lowercase letters.
 */
public class P804UniqueMorseCode {
    public int uniqueMorseRepresentations(String[] words) {
        String[] morseCodes = new String[] {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        Set<String> morseSet =
        Arrays.stream(words).map(str -> {
            StringBuilder sall = IntStream.range(0, str.length()).mapToObj(index -> {
                char ch = str.charAt(index);
                return morseCodes[(ch -'a')];
            }).collect(StringBuilder::new, (sb, str1) -> sb.append(str1), (sb,sb2) -> new StringBuilder(sb.toString() + sb2.toString()) );
            return  sall.toString();
        }).collect(Collectors.toSet());
        return morseSet.size();
    }
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "gin,zen,gig,msg;2"
    })
    void test(String wordsStr, int expected){
        Assert.assertEquals(expected, uniqueMorseRepresentations(wordsStr.split(",")));
    }
}
