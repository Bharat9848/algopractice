package leetcode;
/*
Given two words word1 and word2, find the minimum number of steps required to make word1 and word2 the same,
where in each step you can delete one character in either string.

Example 1:
Input: "sea", "eat"
Output: 2
Explanation: You need one step to make "sea" to "ea" and another step to make "eat" to "ea".
Note:
The length of given words won't exceed 500.
Characters in given words can only be lower-case letters.


Wrong solution : you cannot sort the strings
solution : simple dynamic programming problem similar to edit distance.
 */

import org.junit.Assert;
import org.junit.Test;

public class P583DeleteOperationTwoStrings {

    public int minDistance(String word1, String word2) {

        int[][] mem = new int[word1.length()+1][word2.length()+1];
        for (int i = 0; i < mem.length; i++) {
            for (int j = 0; j < mem[0].length; j++) {
                if(i==0){
                    mem[i][j] =  j;
                }else if(j==0){
                    mem[i][j] = i;
                }else{
                    if(word1.charAt(i-1) == word2.charAt(j-1)){
                        int min = Math.min(mem[i-1][j],mem[i][j-1])+1;
                        mem[i][j] = Math.min(mem[i-1][j-1],min);
                    }else{
                        mem[i][j] = Math.min(mem[i-1][j],mem[i][j-1]) + 1;
                    }
                }
            }
        }
        return mem[word1.length()][word2.length()];
    }

    @Test
    public void test(){
        Assert.assertEquals(2,minDistance("eat","sea"));
        Assert.assertEquals(6,minDistance("aabhrt","aaiknt"));
        Assert.assertEquals(8,minDistance("bharat","ankita"));
    }
}
