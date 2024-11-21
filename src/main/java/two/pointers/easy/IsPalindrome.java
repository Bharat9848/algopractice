package two.pointers.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class IsPalindrome {

    boolean isPalindrome(String str){
        char[] chars = str.toCharArray();
        int left = 0, right = chars.length-1;
        while (left<right){
            if(chars[left] !=  chars[right]){
                return false;
            } else {
              left++;
              right--;
            }
        }
        return true;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "abc|false",
            "aacaa|true",
            "aaa|true",
            "aa|true"
    })
    void test(String str, boolean expected){
        Assertions.assertEquals(expected, isPalindrome(str));
    }
}
