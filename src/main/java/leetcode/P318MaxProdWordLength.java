package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where
 * the two words do not share common letters. You may assume that each word will contain only lower case letters.
 * If no such two words exist, return 0.

 Example 1:

 Input: ["abcw","baz","foo","bar","xtfn","abcdef"]
 Output: 16
 Explanation: The two words can be "abcw", "xtfn".
 Example 2:

 Input: ["a","ab","abc","d","cd","bcd","abcd"]
 Output: 4
 Explanation: The two words can be "ab", "cd".
 Example 3:

 Input: ["a","aa","aaa","aaaa"]
 Output: 0
 Explanation: No such pair of words.
 * Created by bharat on 23/5/18.
 *
 *
 * Solution : 1st trial plain and vanilla O(n^2) in both all possible String pairs and n^2 in string characters matching
 * 2nd Trial successful : use hashmap in character matching solution accepted
 * Actual solution use integer bits as hashmap and simple bitwise and operation between two num of string operation.
 */
public class P318MaxProdWordLength
{
    public int maxProduct(String[] words) {
        int max = 0;
        for (int i = words.length-1; i >= 0; i--) {
            for (int j = i-1; j >=0 ; j--) {
                if(!hasCommonLetter(words[i],words[j])){
                    int temp = words[i].length() * words[j].length();
                    if(max < temp){
                        max = temp;
                    }
                }
            }
        }
        return max;
    }

    private boolean hasCommonLetter(String a, String b) {
        boolean[] charMap= new boolean[26];
        char[] aa = a.toCharArray();
        char[] ba = b.toCharArray();
        for (int i = 0; i < aa.length; i++) {
            charMap[aa[i] - 'a'] = true;
        }
        for (int i = 0; i < ba.length; i++) {
            if(charMap[ba[i]-'a']){
                return true;
            }
        }
        return false;
    }

    @Test
    public void test123(){
        Assert.assertEquals(4,maxProduct(new String[]{"a","ab","abc","d","cd","bcd","abcd"}));
        Assert.assertEquals(0,maxProduct(new String[]{"a","aa","aaa","aaaa"}));
        Assert.assertEquals(16,maxProduct(new String[]{"abcw","baz","foo","bar","xtfn","abcdef"}));
    }
}
