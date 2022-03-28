package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given an integer n, return a string array answer (1-indexed) where:
 *
 *     answer[i] == "FizzBuzz" if i is divisible by 3 and 5.
 *     answer[i] == "Fizz" if i is divisible by 3.
 *     answer[i] == "Buzz" if i is divisible by 5.
 *     answer[i] == i (as a string) if none of the above conditions are true.
 * Input: n = 3
 * Output: ["1","2","Fizz"]
 *
 * Example 2:
 *
 * Input: n = 5
 * Output: ["1","2","Fizz","4","Buzz"]
 *
 * Example 3:
 *
 * Input: n = 15
 * Output: ["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"]
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 10^4
 */
class P412FizzBuzz {
     List<String> fizzBuzz(int n) {
         List<String> result = new ArrayList<>(n);
         int nextFizz = 3, nextBuzz = 5;
         for (int i = 1; i <= n; i++) {
             if(i == nextBuzz && i == nextFizz){
                 result.add("FizzBuzz");
                 nextBuzz = nextBuzz + 5;
                 nextFizz = nextFizz + 3;
             }else if(i == nextBuzz){
                 result.add("Buzz");
                 nextBuzz = nextBuzz +5;
             }else if(i == nextFizz){
                 result.add("Fizz");
                 nextFizz = nextFizz + 3;
             }else {
                 result.add(""+i);
             }
         }
         return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3|1,2,Fizz",
            "5|1,2,Fizz,4,Buzz",
            "15|1,2,Fizz,4,Buzz,Fizz,7,8,Fizz,Buzz,11,Fizz,13,14,FizzBuzz"
    })
    void test(int n, String expected){
        Assertions.assertEquals(Arrays.stream(expected.split(",")).collect(Collectors.toList()), fizzBuzz(n));
    }
}
