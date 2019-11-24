package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/*
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.

Note:

    The same word in the dictionary may be reused multiple times in the segmentation.
    You may assume the dictionary does not contain duplicate words.

Example 1:

Input: s = "leetcode", wordDict = ["leet", "code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".

Example 2:

Input: s = "applepenapple", wordDict = ["apple", "pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
             Note that you are allowed to reuse a dictionary word.

Example 3:

Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
Output: false


 */
public class P139wordBreakTLE {

    public boolean wordBreakRecursiveWoutMemoization(String s, List<String> wordDict) {
        if(s.isEmpty()){return true;}

        for(int len = s.length(); len > 0; len--){
            if(wordBreakRecursiveWoutMemoization(s.substring(0, s.length() -len), wordDict) && wordDict.contains(getSubStringOfLengthFromEnd(s, len))){
                return true;
            }
        }
        return false;
    }

    private String getSubStringOfLengthFromEnd(String s, int len) {
        return s.substring(s.length() -len);
    }

    public boolean wordBreak(String s, List<String> wordDict){
        boolean[][] indexTolength = initMemoizaionGraph(s.length());
        for (int index = 0; index < s.length(); index++) {
            for (int length = 1; length <= index+1; length++) {
                indexTolength[index][length] = wordDict.contains(s.substring(index+1 - length, index+1));
            }
        }
        for (int row = 0; row < indexTolength.length; row++) {
            System.out.println(Arrays.toString(indexTolength[row]));
        }
        int lastIndex = s.length()-1;
        return checkSolutionExists(lastIndex, indexTolength);
    }

    private boolean checkSolutionExists(int row, boolean[][] indexTolength) {
        if(row < 0){
            return true;
        }
        for (int len = 1; len <= row + 1; len++) {
            if(indexTolength[row][len]){
                boolean solExists = checkSolutionExists(row -len, indexTolength);
                if(solExists) return true;
            }
        }
        return false;
    }


    private boolean[][] initMemoizaionGraph(int length) {
        boolean[][] indexToLength = new boolean[length][length+1];
        for (int i = 0; i < indexToLength.length; i++) {
            indexToLength[i][0] = true;
        }
        return indexToLength;
    }

    @Test
    public void testEdgeCases(){
        Assert.assertTrue(wordBreakRecursiveWoutMemoization("ateatbay", Arrays.asList("eat", "at","a", "bay")));
        Assert.assertTrue(wordBreak("ateatbay", Arrays.asList("eat", "at","a", "bay")));
        Assert.assertTrue(wordBreakRecursiveWoutMemoization("atthegate", Arrays.asList("eat", "at","a", "bay", "gate", "the")));
        Assert.assertTrue(wordBreak("atthegate", Arrays.asList("eat", "at","a", "bay", "gate", "the")));
        Assert.assertFalse(wordBreak("catsandog", Arrays.asList("cats", "sand","and", "cat", "dog")));
        Assert.assertTrue(wordBreak("a", Arrays.asList("a", "sand","and", "cat", "dog")));
        Assert.assertFalse(wordBreak("aaaaaaa", Arrays.asList("aaaa", "aa")));
    }
}
