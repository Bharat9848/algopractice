package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

/**
 * Given a string s, return the longest palindromic substring in s.
 *
 * Example 1:
 *
 * Input: s = "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 *
 * Example 2:
 *
 * Input: s = "cbbd"
 * Output: "bb"
 *
 * Example 3:
 *
 *
 * Input: s = "a"
 * Output: "a"
 *
 * Example 4:
 *
 * Input: s = "ac"
 * Output: "a"
 *
 *
 *
 * Constraints:
 *
 *     1 <= s.length <= 1000
 *     s consist of only digits and English letters.
 *
 *     at index i check if it can be part of current running palindrome(?) if yes then add to palindrome. Also check if the global palindrome max is smaller than current palindrome if yes then change it.
 *     if at i if it cannot be a palindrome then discard current running palindrome start a new current running palindrome from i.
 */
class P5LargestPalindromeSubstring {
    String longestPalindrome(String s) {
        Pair<Integer, Integer> globalLeftRightPalindromeIndex = new Pair<>(0,0);
        Pair<Integer, Integer> currentLeftRightPalindromeIndex =  new Pair<>(0, 0);
        for (int i = 1; i < s.length(); i++) {
            char current = s.charAt(i);
            if(canBePartOfPalindrome(s, current, currentLeftRightPalindromeIndex)){
                if(allCharSame(s, currentLeftRightPalindromeIndex, current)){
                    currentLeftRightPalindromeIndex = new Pair<>(currentLeftRightPalindromeIndex.getFirst(), i);
                }else{
                    currentLeftRightPalindromeIndex = new Pair<>(currentLeftRightPalindromeIndex.getFirst()-1, i);
                }
            }else {
                currentLeftRightPalindromeIndex = findPalindromeBackwards(s, currentLeftRightPalindromeIndex.getFirst()+1 ,i);
            }
            if((globalLeftRightPalindromeIndex.getSec()- globalLeftRightPalindromeIndex.getFirst())<= (currentLeftRightPalindromeIndex.getSec()-currentLeftRightPalindromeIndex.getFirst())){
                globalLeftRightPalindromeIndex = currentLeftRightPalindromeIndex;
            }
        }
        return s.substring(globalLeftRightPalindromeIndex.getFirst(), globalLeftRightPalindromeIndex.getSec()+1);
    }

    private Pair<Integer, Integer> findPalindromeBackwards(String s, int fromIndex, int index) {
        int startIndex = fromIndex;
        for (int i = fromIndex; i <= index; i++) {
            startIndex = i;
            if(s.charAt(i) == s.charAt(index)){
                boolean isPalindrome = true;
                for (int left = i+1,right = index-1;  left<right && isPalindrome; left++,right--) {
                    isPalindrome = s.charAt(left) == s.charAt(right);
                }
                if(isPalindrome){
                    break;
                }
            }
        }
        return new Pair<>(startIndex, index);
    }

    private boolean canBePartOfPalindrome(String s, char current, Pair<Integer, Integer> currentLeftRightPalindromeIndex) {
        Integer leftIndex = currentLeftRightPalindromeIndex.getFirst();
        return allCharSame(s, currentLeftRightPalindromeIndex, current) || (leftIndex>0 && current == s.charAt(leftIndex -1));
    }

    private boolean allCharSame(String s, Pair<Integer, Integer> currentIndices, char current) {
        boolean same = true;
        for (int i = currentIndices.getFirst(); i <= currentIndices.getSec() ; i++) {
            same =  same && current == s.charAt(i);
        }
        return same;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "ac|c",
            "babad|aba",
            "bbd|bb",
            "a|a",
            "ccc|ccc",
            "ddccccc|ccccc",
            "ddcc1ccc|cc1cc"
    })
    void test(String input, String expected){
        Assert.assertEquals(expected, longestPalindrome(input));
    }
}
