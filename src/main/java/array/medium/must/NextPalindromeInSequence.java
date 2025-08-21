package array.medium.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * TODO
 * Given a string num_str representing a palindrome, the string only contains digits. Your task is to return the next possible palindrome using the same digits. The returned palindrome would be the next largest palindrome, meaning there can be more than one way to rearrange the digits to make a larger palindrome. Return an empty string if no greater palindrome can be made.
 *
 * Consider the following example to understand the expected output for a given numeric string:
 *
 *     input string = "123321"
 *
 *     possible palindromes = "213312", "231132", "312213", "132231", "321123"
 *
 *     We should return the palindrome "132231" as it is greater than "123321" yet the smallest palindrome excluding the original palindrome.
 *
 * Constraints:
 *
 *     1≤1≤ num.length ≤105≤105
 *
 *     num_str is a palindrome.
 */
/*
    ## key insight  Find the second last minima. then find the next minimum maximum behind it. Then swap it with second last minima and then sort all the number after the second minima.
    very tricky question
    ## time complexity
    ## space complexity
 */
public class NextPalindromeInSequence {
    public String findNextPalindromeNotDone(String numStr) {
        if(numStr.length() < 3){
            return "";
        }
        int len = numStr.length()/2;
        PriorityQueue<IndxAndNo> minQueue = new PriorityQueue<>(Comparator.comparingInt((IndxAndNo::getNo)).reversed());
        minQueue.offer(new IndxAndNo(len -1, numStr.charAt(len-1) - '0'));
        int i = len-2;
        for(; i >= 0; i--){
            int current = numStr.charAt(i) - '0';
            int next = numStr.charAt(i+1) - '0';
            if(current > next){
                minQueue.offer(new IndxAndNo(i ,current));
            } else{
                break;
            }
        }
        if(i==-1){
            return "";
        }
        int secMinimaIndex = i;
        int secMinimaNo =  numStr.charAt(secMinimaIndex) - '0';
        IndxAndNo nextBig = null;
        while (!minQueue.isEmpty()){
           IndxAndNo nextMax = minQueue.poll();
           if(nextMax.No > secMinimaNo){
               nextBig = nextMax;
           }
           else {
               break;
           }
        }
        if(nextBig == null){
            return "";
        } else {
            int sec = nextBig.index;
            char[] res = numStr.toCharArray();
            swapChars(res, secMinimaIndex, sec);
            Arrays.sort(res, secMinimaIndex+1, len);
            for(int j = secMinimaIndex; j < len; j++){
                res[numStr.length() -1 - j ] = res[j];
            }
            return new String(res);
        }

    }

    private static class IndxAndNo{
        public int index;
        public int No;
        public IndxAndNo(int index, int no){
            this.index = index;
            this.No = no;
        }

        public int getIndex() {
            return index;
        }

        public int getNo() {
            return No;
        }
    }

    private static void swapChars(char[] chars, int s, int t){
        char temp = chars[s];
        chars[s] = chars[t];
        chars[t] = temp;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2314334132|2331441332",
            "123321|132231",
            "211130031112|211301103112",
            "428824|482284",
            "11111|",
            "908695596809|908956659809",
            "987789|",
            "5|",
            "767|",
    })
    void test(String numStr, String expected){
        if(expected == null){
            expected = "";
        }
        Assertions.assertEquals(expected, findNextPalindromeNotDone(numStr));
    }
}
