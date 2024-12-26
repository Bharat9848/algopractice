package sliding.window.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Given two strings, s and t, find the minimum window substring in s, which has the following properties:
 *
 *     It is the shortest substring of s that includes all of the characters present in t.
 *
 *     It must contain at least the same frequency of each character as in t.
 *
 *     The order of the characters does not matter here.
 *
 *     Note: If there are multiple valid minimum window substrings, return any one of them.
 *
 * Constraints:
 *
 *     Strings s and t consist of uppercase and lowercase English characters.
 *
 *     1 ≤≤ s.length, t.length ≤103≤103
 */

/**
 * TODO can be return more intuitively - we need to replicate the state of question need. t frequencies and window frequencies then how to expand and shrink our sliding window
 */
public class MinimumSlidingFreqAtLeastTChar {

    public static String minWindow(String s, String t) {
        if(s.isEmpty() || t.isEmpty()){
            return "";
        }
        int left = 0, right =-1;
        Map<Character,Integer> tFreqMap = new HashMap<>();
        var tchars = t.toCharArray();
        for (int i = 0; i < tchars.length; i++) {
            tFreqMap.put(tchars[i], tFreqMap.getOrDefault(tchars[i], 0) + 1);
        }
        right = 0;
        boolean reached = false;
        int count = t.length();
        while (!reached && right < s.length()){
            char current = s.charAt(right);
            if(tFreqMap.containsKey(current)){
                if(tFreqMap.get(current) > 0){
                    count -= 1;
                }
                tFreqMap.put(current, tFreqMap.get(current)-1);
            }
            if(count==0){
                reached=true;
            } else {
                right++;
            }

        }
        if(!reached){
            return "";
        }
        var minWindow = s.substring(left, right+1);
        for(int i = left; i <= right; i++){
            char x = s.charAt(i);
            if(tFreqMap.containsKey(x) && tFreqMap.get(x) < 0){
                tFreqMap.put(x, tFreqMap.get(x) + 1);
            } else if(tFreqMap.containsKey(x) && tFreqMap.get(x) == 0){
                minWindow = s.substring(i, right + 1);
                left = i;
                break;
            }

        }

        for (int i = right+1; i < s.length(); i++) {
            char leftCh = s.charAt(left);
            if(tFreqMap.containsKey(leftCh)){
                tFreqMap.put(leftCh, tFreqMap.get(leftCh) + 1);
                if(tFreqMap.get(leftCh) > 0){
                    count++;
                }
            }
            left++;
            char rightCh = s.charAt(i);
            if(tFreqMap.containsKey(rightCh)){
                tFreqMap.put(rightCh, tFreqMap.get(rightCh) - 1);
                if(tFreqMap.get(rightCh) == 0){
                    count--;
                }
            }
            if(count == 0){
                var newWindow = s.substring(left, i+1);
                minWindow = minWindow.length() > newWindow.length() ? newWindow: minWindow;
                for(int j = left; j < i; j++){
                    char x = s.charAt(j);
                    if(tFreqMap.containsKey(x) && tFreqMap.get(x) < 0){
                        tFreqMap.put(x, tFreqMap.get(x) + 1);
                    } else if(tFreqMap.containsKey(x) && tFreqMap.get(x) == 0){
                        left = j;
                        var newWindow1 = s.substring(j, i+1);
                        minWindow = minWindow.length() > newWindow1.length() ? newWindow1: minWindow;
                        break;
                    }
                    left = j;
                    var newWindow1 = s.substring(j, i+1);
                    minWindow = minWindow.length() > newWindow1.length() ? newWindow1: minWindow;
                }
            }

        }
        return minWindow;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "kxa|x|x",
            "SFS|A|",
            "ABDFGDCKAB|ABCD|DCKAB",
            "aaaaabbbc|abc|abbbc",
            "aacccbaaac|aabc|cbaa",
            "abc|cba|abc",
            "bbaac|aba|baa",
            "aabbcabc|abc|bca",
            "cccccabwefgewcwaefhllh|cae|cwae",
    })
    void test(String s, String t, String expected){
        if(expected == null){
            expected = "";
        }
        Assertions.assertEquals(expected, minWindow(s, t));
    }
}
