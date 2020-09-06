package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/*
We have a string S of lowercase letters, and an integer array shifts.

Call the shift of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes 'a').

For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.

Now for each shifts[i] = x, we want to shift the first i+1 letters of S, x times.

Return the final string after all such shifts to S are applied.

Example 1:

Input: S = "abc", shifts = [3,5,9]
Output: "rpl"
Explanation:
We start with "abc".
After shifting the first 1 letters of S by 3, we have "dbc".
After shifting the first 2 letters of S by 5, we have "igc".
After shifting the first 3 letters of S by 9, we have "rpl", the answer.
Note:

1 <= S.length = shifts.length <= 20000
0 <= shifts[i] <= 10 ^ 9


 */
public class P848ShiftingLetters {

    public String shiftingLetters(String S, int[] shifts) {
        long shiftSum = 0;
        char[] chars = S.toCharArray();
        for (int i = shifts.length-1; i >=0 ; i--) {
            if(i >= chars.length){
                continue;
            }else{
                chars[i] = shift(chars[i], shifts[i] + shiftSum);
                shiftSum += shifts[i];
            }
        }
        return new String(chars);
    }

    private char shift(char aChar, long shift) {
        int offset = aChar - 'a';
        return (char)('a' + ((offset+shift)%26));
    }
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "abc;3,5,9,4;rpl"
    })
    void test(String str, String shiftArr, String expected){
        int [] shift = Arrays.stream(shiftArr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, shiftingLetters(str, shift));
    }

}
