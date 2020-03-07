package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
A message containing letters from A-Z is being encoded to numbers using the following mapping:

'A' -> 1
'B' -> 2
...
'Z' -> 26

Given a non-empty string containing only digits, determine the total number of ways to decode it.

Example 1:

Input: "12"
Output: 2
Explanation: It could be decoded as "AB" (1 2) or "L" (12).

Example 2:

Input: "226"
Output: 3
Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).

Example 3
Input: "100"
Output: 0

Algo: At a given position i if the value is in 1-6 and i-1 is 1 or 2 then  solution will be no of ways to reach i-1, and no of ways to reach i-2.
if i value is in 1-9 and i-1 is >3 then  solution will be no of ways to reach i-1
 */
class P92DecodeWays {
    int numDecodings(String s) {
        int[] decode = new int[s.length()+1];
        decode[0] = 1; // represent empty string
        decode[1] = Integer.parseInt(s.charAt(0)+"") == 0? 0:1;
        for (int i = 2; i < decode.length; i++) {
            int no = 0;
            int prev = Integer.parseInt(s.charAt(i-2) + "");
            int current = Integer.parseInt(s.charAt(i-1) + "");
            int twoDigits = prev*10 + current;
            if( prev !=0 && twoDigits>0 && twoDigits<27 ){
                no += decode[i-2];
            }
            if( current !=0 ){
                no += decode[i-1];
            }
            decode[i] = no;
        }
        return decode[s.length()];
    }
    @ParameterizedTest
    @CsvSource({"12,2", "226, 3", "1,1", "99,1", "113,3", "0,0", "100,0", "10,1", "101,1", "27,1", "26,2","100005, 0"})
     void test(String decode, String sol){
        Assert.assertEquals(numDecodings(decode), Integer.parseInt(sol));
    }

}
