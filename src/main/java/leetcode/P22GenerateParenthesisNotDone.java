package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 *
 * Example 2:
 *
 * Input: n = 1
 * Output: ["()"]
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 8
 */
public class P22GenerateParenthesisNotDone {
    public List<String> generateParenthesisWrong(int n){
        if(n==1){ return Collections.singletonList("()");}
        List<String> subParenList = generateParenthesisWrong(n-1);
        Set<String> newSet = new HashSet<>();
        for (String subParenStr: subParenList){
            newSet.add(subParenStr + "()");
            newSet.add("()" + subParenStr);
            newSet.add("(" + subParenStr + ")");
        }
        return new ArrayList<>(newSet);
    }

    public List<String> generateParenthesis(int n){
       return null;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|()",
            "2|()(),(())",
            "3|()()(),(())(),()(()),((())),(()())",
            "4|()()()(), (()())(), (()(())), ()()(()), (())()(), (((()))), ()((())), ()(())(), ()(()()), (()()()), ((()())), ((()))(), ((())()),(())(())"
    })
    void test(int n, String expectedStr){
        List<String> expected = Arrays.stream(expectedStr.split(",")).map(String::trim).collect(Collectors.toList());
        assertEquals(new HashSet<>(expected), new HashSet<>(generateParenthesis(n)));
    }
}
