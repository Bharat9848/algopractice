package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Given a string s, rearrange the characters of s so that any two adjacent characters are not the same.
 *
 * Return any possible rearrangement of s or return "" if not possible.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "aab"
 * Output: "aba"
 *
 * Example 2:
 *
 * Input: s = "aaab"
 * Output: ""
 *
 * 0-1
 *
 * 2-2
 *
 * Constraints:
 *
 *     1 <= s.length <= 500
 *     s consists of lowercase English letters.
 */
 class P767ReorganizeString {
     String reorganizeString(String s) {
         Map<Character, Integer> freqMap = new HashMap<>();
         for (int i = 0; i < s.length(); i++) {
             Character ch = s.charAt(i);
             freqMap.putIfAbsent(ch, 0);
             freqMap.put(ch, freqMap.get(ch) + 1);
         }
         PriorityQueue<Pair<Character, Integer>> charFreqInDesc = new PriorityQueue<>((p1,p2)-> -(p1.getSec()-p2.getSec()));
         freqMap.entrySet().stream().map(e -> new Pair<>(e.getKey(), e.getValue())).forEach(charFreqInDesc::offer);
         int noOfBuckets = charFreqInDesc.peek().getSec();
         List<StringBuilder> bucketSubStr = IntStream.range(0, noOfBuckets).mapToObj(StringBuilder::new).collect(Collectors.toList());

         int bucket = 0;
         while(charFreqInDesc.size() > 0){
             Pair<Character, Integer> charFreq = charFreqInDesc.remove();
             Character currentCharacter = charFreq.getFirst();
             Integer charLeft = charFreq.getSec();
             while(charLeft > 0){
                 StringBuilder subString = bucketSubStr.get(bucket);
                 subString.append(currentCharacter);
                 bucket = (bucket+1==noOfBuckets)?0: bucket+1;
                 charLeft--;
             }
         }
         StringBuilder result = new StringBuilder();
         for (int i=0; i<bucketSubStr.size(); i++){
             String subStr = bucketSubStr.get(i).toString();
             if(subStr.length()==1 && i+1<bucketSubStr.size() && bucketSubStr.get(i+1).toString().equals(subStr)){
                 return "";
             }
             result.append(subStr);
         }
         return result.toString();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "aab|aba",
            "aaab|",
            "abc|abc",
            "aaa|",
            "aaabbc|ababac",
            "baaba|ababa",
            "abbbbaaacccddd|ababbbaaacccddd"
    })
    void test(String s, String expected){
        Assert.assertEquals(expected==null?"":expected, reorganizeString(s));
    }
}
