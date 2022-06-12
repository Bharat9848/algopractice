package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Given an encoded string, return its decoded string.
 *
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
 *
 * You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].
 *
 * The test cases are generated so that the length of the output will never exceed 10^5.
 *
 * Input: s = "3[a]2[bc]"
 * Output: "aaabcbc"
 * Input: s = "3[a2[c]]"
 * Output: "accaccacc"
 * Input: s = "2[abc]3[cd]ef"
 * Output: "abcabccdcdcdef"
 *
 *  Expression = List[Expression]
 *  Expression = number[string]
 *  Expression = string
 * Constraints:
 *
 *     1 <= s.length <= 30
 *     s consists of lowercase English letters, digits, and square brackets '[]'.
 *     s is guaranteed to be a valid input.
 *     All the integers in s are in the range [1, 300].
 */
public class P394DecodeString {

    class MyExpression{
        int multiplier;
        String s;
        List<MyExpression> subExpression;

        public MyExpression(int k, String substring, List<MyExpression> subExpressions) {
            this.multiplier = k;
            this.s = substring;
            this.subExpression = subExpressions == null ? new ArrayList<>(): subExpressions;
        }

        public String decode(){
            String s1 = s + subExpression.stream().map(MyExpression::decode).collect(Collectors.joining(""));
            return IntStream.range(0, multiplier).mapToObj(i -> s1).collect(Collectors.joining(""));
        }

    }


    String decodeString(String s) {
        List<MyExpression> expressions = parse(s, 0, s.length() - 1);
        return  expressions.stream().map(MyExpression::decode).collect(Collectors.joining());
    }

    List<MyExpression> parse(String s, int beg , int end) {
        List<MyExpression> result = new ArrayList<>();
        for (int i = beg; i <= end; ) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                int k = Integer.parseInt(current + "");
                int j = i + 1;
                while (Character.isDigit(s.charAt(j))) {
                    k = k * 10 + Integer.parseInt(s.charAt(j) + "");
                    j++;
                }
                int startBracesIndex = j;
                Pair<Integer, Boolean> endBraceIndexAndSimpleExpression = findClosingBraceIndex(s, startBracesIndex, end);
                Integer endBracesIndex = endBraceIndexAndSimpleExpression.getFirst();
                Boolean simple = endBraceIndexAndSimpleExpression.getSec();
                if (simple) {
                    MyExpression ex = new MyExpression(k, s.substring(startBracesIndex + 1, endBracesIndex), null);
                    result.add(ex);
                } else {
                    List<MyExpression> subExpression = parse(s, startBracesIndex+1, endBracesIndex-1);
                    result.add(new MyExpression(k, "", subExpression));
                }
                i = endBracesIndex+1;
            } else {
                int j = i + 1;
                while (j<=end && !Character.isDigit(s.charAt(j))) {
                    j++;
                }
                result.add(new MyExpression(1, s.substring(i, j), null));
                i = j;
            }
        }
        return result;
    }

    private Pair<Integer, Boolean> findClosingBraceIndex(String s, int startBraceIndex, int end) {
        Stack<Integer> bracesStack = new Stack<>();
        bracesStack.push(startBraceIndex);
        boolean simple = true;
        for (int i = startBraceIndex+1; i <= end; i++) {
            if(s.charAt(i) == '['){
                simple = false;
                bracesStack.push(i);
            }else if(s.charAt(i) == ']'){
                bracesStack.pop();
                if(bracesStack.isEmpty()){
                    return new Pair<>(i, simple);
                }
            }
        }
        throw new RuntimeException("wrong expression" + s.substring(startBraceIndex, end+1));
    }


    @ParameterizedTest
    @CsvSource({
            "abc,abc",
            "3[a],aaa",
            "2[a]2[b],aabb",
            "3[a2[b]3[c]],abbcccabbcccabbccc",
            "3[a]2[bc],aaabcbc",
            "3[a2[c]],accaccacc",
            "2[abc]3[cd]ef,abcabccdcdcdef"})
    void test(String s, String expected){
        Assertions.assertEquals(expected, decodeString(s));
    }
}
