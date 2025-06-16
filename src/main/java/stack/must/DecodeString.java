package stack.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;

/**
 * Given an encoded string, return its decoded version. The encoding rule follows the pattern: k[encoded string]k[encoded string], where the encoded stringencoded string is repeated exactly kk times.
 *
 *     Note: The kk is guaranteed to be a positive integer.
 *
 * Assume that the input string is always valid, meaning there are no extra spaces, and the square brackets are properly balanced and well-formed. Additionally, assume that the original data contains no digits and that digits are only used for repeating the string.
 *
 * For example, the input will not contain patterns like 3a3a or 2[4]2[4], whereas a valid pattern is 2[aj]3[bax]2[aj]3[bax].
 * 2[a2[]
 *
 * Constraints:
 *
 *     1≤1≤ s.length ≤30≤30
 *
 *     1≤1≤ kk ≤100≤100
 *
 *     s consists of lowercase English letters, digits, and square brackets.
 *
 *     implementation heavy
 */
public class DecodeString {
    DecodeStrAndInd decodeStrGroup(String encodedStr, Stack<EncodeGroupMeta> stack, int fromIdx) {
        int i = fromIdx;
        int currentNumberIndex = -1;
        int multiplier = -1;
        StringBuilder buffer = new StringBuilder("");
        for ( ; i < encodedStr.length(); i++) {
            var currentCh = encodedStr.charAt(i);
            if(Character.isDigit(currentCh)){
                if(currentNumberIndex == -1){
                    currentNumberIndex = i;
                    multiplier = currentCh - '0';
                } else if(currentNumberIndex == i-1){
                    currentNumberIndex = i;
                    multiplier = multiplier *10 + (currentCh - '0');
                } else {
                    stack.push(new EncodeGroupMeta(buffer, multiplier));
                    buffer = new StringBuilder();
                    currentNumberIndex = i;
                    multiplier = currentCh - '0';
                }
            } else if(currentCh == '['){
            } else if (currentCh == ']'){
                if(!stack.empty()){
                    String result = multiplyStr(buffer, multiplier);
                    var oldLoop = stack.pop();
                    var oldLoopBuffer = oldLoop.buffer().append(result);
                    buffer = oldLoopBuffer;
                    multiplier = oldLoop.multiplier();
                } else{
                    break;
                }
            } else{
                buffer.append(currentCh);
            }
        }
        multiplyStr(buffer, multiplier);
        return new DecodeStrAndInd(buffer.toString(), i);
    }

    String multiplyStr(StringBuilder buffer, int multiplier){
        String temp = buffer.toString();
        for (int j = 1; j < multiplier; j++) {
            buffer.append(temp);
        }
        return buffer.toString();
    }
    record DecodeStrAndInd(String decodedStr, int index){}
    record EncodeGroupMeta(StringBuilder buffer, int multiplier){}

    String decodeString(String s){
        StringBuilder result = new StringBuilder();
        Stack<EncodeGroupMeta> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            var ch = s.charAt(i);
            if(Character.isDigit(ch)){
               var decodedStrAndIndex = decodeStrGroup(s, stack, i);
               var decoded = decodedStrAndIndex.decodedStr();
               result.append(decoded);
               i = decodedStrAndIndex.index();
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3[ab]2[c2[d]]|abababcddcdd",
            "1[2[a]b]|aab",
            "3[a]|aaa",
            "10[a]|aaaaaaaaaa",
            "3[a]2[b]|aaabb",
            "abc|abc"
    })
    void test(String encode, String expected){
        Assertions.assertEquals(expected, decodeString(encode));
    }
}
