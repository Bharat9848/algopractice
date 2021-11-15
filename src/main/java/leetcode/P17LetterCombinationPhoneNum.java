package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
 *
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 *
 *1  -> ""
 * 2 -> "abc
 * 3 -> def
 * 4 -> ghi
 * 5 -> jkl
 * 6 -> mno
 * 7 -> pqrs
 * 8 -> tuv
 * 9 -> wxyz
 *
 * Example 1:
 *
 * Input: digits = "23"
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * Example 2:
 *
 * Input: digits = ""
 * Output: []
 *
 * Example 3:
 *
 * Input: digits = "2"
 * Output: ["a","b","c"]
 */
public class P17LetterCombinationPhoneNum {
    public List<String> letterCombinations(String digits) {
        if(digits==null || digits.isEmpty()){ return new ArrayList<>();}
        digits = digits.trim();
        List<String> result = new ArrayList<>();
        result.add("");
        for (int i = 0; i < digits.length(); i++) {
            char digit = digits.charAt(i);
            List<String> digitLetters =  findDigitLetters(digit);
            List<String> newList = new ArrayList<>();
            for (String res: result) {
                for (String letter:digitLetters){
                    newList.add(res + letter);
                }
            }
            result = newList;
        }
        return result;
    }

    private List<String> findDigitLetters(char digit) {
        List<String> result;
        switch (digit){
            case '2': result =Arrays.stream("abc".split("")).collect(Collectors.toList());
            break;
            case '3':
                result =Arrays.stream("def".split("")).collect(Collectors.toList());
                break;
            case '4':
                result =Arrays.stream("ghi".split("")).collect(Collectors.toList());
                break;
            case '5':
                result =Arrays.stream("jkl".split("")).collect(Collectors.toList());
                break;
            case '6':result =Arrays.stream("mno".split("")).collect(Collectors.toList());
                break;
            case '7':result =Arrays.stream("pqrs".split("")).collect(Collectors.toList());
                break;
            case '8':result =Arrays.stream("tuv".split("")).collect(Collectors.toList());
                break;
            case '9':result =Arrays.stream("wxyz".split("")).collect(Collectors.toList());
                break;
            default: throw  new RuntimeException("missing");
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2|a,b,c",
            "23|ad,ae,af,bd,be,bf,cd,ce,cf"
    })
    void test(String digits, String sol){
        List<String> expected = Arrays.stream(sol.split(",")).collect(Collectors.toList());
        Assert.assertEquals(expected, letterCombinations(digits));
    }

    @Test
    public void test(){
        Assert.assertTrue( letterCombinations("").size()==0);
        Assert.assertTrue( letterCombinations(null).size()==0);
    }
}
