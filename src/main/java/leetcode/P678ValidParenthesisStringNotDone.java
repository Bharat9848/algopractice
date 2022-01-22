package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.
 *
 * The following rules define a valid string:
 *
 *     Any left parenthesis '(' must have a corresponding right parenthesis ')'.
 *     Any right parenthesis ')' must have a corresponding left parenthesis '('.
 *     Left parenthesis '(' must go before the corresponding right parenthesis ')'.
 *     '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".
 *
 *
 *
 * Example 1:
 *
 * Input: s = "()"
 * Output: true
 *
 * Example 2:
 *
 * Input: s = "(*)"
 * Output: true
 *
 * Example 3:
 *
 * Input: s = "(*))"
 * Output: true
 *
 *
 *
 * Constraints:
 *
 *     1 <= s.length <= 100
 *     s[i] is '(', ')' or '*'.
 */
class P678ValidParenthesisStringNotDone {
    boolean checkValidString(String s) {
        List<Integer> wildCardsIndices = new LinkedList<>();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char charEle = s.charAt(i);
            switch (charEle){
                case '(' : stack.push(charEle);
                break;
                case ')' : if(stack.size() > 0) {
                    stack.pop();
                }else{
                    //* reincarnated as '('
                    i = wildCardsIndices.remove(0);
                }
                break;
                case '*' : wildCardsIndices.add(i);
                break;
                default: throw new RuntimeException("Invalid character");
            }
        }
        if(!stack.empty()){

        }
        return false;
    }



    @ParameterizedTest
    @CsvSource(delimiter = '|',value = {"(*)|true",
    "(*|true",
    "*)|true",
    "*|true",
    "**|true",
     ")(|false","((()))(*)()|true"

    })

    void test(String str, boolean expected){
        Assert.assertEquals(expected, checkValidString(str));
    }
}
