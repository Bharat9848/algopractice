package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given an array of strings wordsDict and two strings that already exist in the array word1 and word2, return the shortest distance between these two words in the list.
 *
 * Note that word1 and word2 may be the same. It is guaranteed that they represent two individual words in the list.
 *
 * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "coding"
 * Output: 1
 *
 * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "makes"
 * Output: 3
 *
 *     1 <= wordsDict.length <= 10^5
 *     1 <= wordsDict[i].length <= 10
 *     wordsDict[i] consists of lowercase English letters.
 *     word1 and word2 are in wordsDict.
 */
 class P245ShortestDistanceIII {
    int shortestWordDistance(String[] wordsDict, String word1, String word2) {
       if(word1 == null || word2 == null || wordsDict.length < 2){
           return 0;
       }
      int index1 = -1, index2 = -1;
      Boolean searchWord1 = null;
        int i = 0;
        for (; i < wordsDict.length; ) {
            if(index1 == -1 && index2 == -1){
                if(wordsDict[i].equals(word1)){
                    index1 = i;
                }else if(wordsDict[i].equals(word2)){
                    index2 = i;
                }
                i++;
            }else if(index2 == -1 && wordsDict[i].equals(word2)){
                index2 = i;
                i++;
            }else if(index1 == -1 && wordsDict[i].equals(word1)) {
                index1 = i;
                i++;
            }else if(index1>-1 && index2 >-1) {
                break;
            }else {
                i++;
            }
        }
        i = Math.min(index2, index1)+1;
        searchWord1 = index1 < index2;
        int distance = Math.abs(index2-index1);
        for (; i < wordsDict.length; ) {
            if(searchWord1){
                if((word1.equals(word2) && i > index2 && wordsDict[i].equals(word1)) || ( !word1.equals(word2) && wordsDict[i].equals(word1))){
                    index1 = i;
                    distance = Math.min(distance,Math.abs(index2- index1));
                    i = Math.min(index1, index2) + 1;
                    searchWord1 = index1 < index2;
                }else {
                    i++;
                }

            }else {
                if((word1.equals(word2) && i > index1 && wordsDict[i].equals(word2))||(!word1.equals(word2) && wordsDict[i].equals(word2))){
                    index2 = i;
                    distance = Math.min(distance, Math.abs(index2- index1));
                    i = Math.min(index1, index2) + 1;
                    searchWord1 = index1 < index2;
                }else {
                    i++;
                }
            }
        }
        return distance;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "a,c,a,b|a|b|1",
            "a,b|b|a|1",
            "makes1,mafd,makes1,makes2,mafdk,makes1|makes1|makes2|1",
            "practice,makes,perfect,coding,makes|makes|coding|1",
            "practice,makes,perfect,coding,makes|makes|makes|3",
            "makes|makes|makes|0",
            "makes1,makes2|makes1|makes2|1",

    })
    void test(String wordDictStr, String word1, String word2, int expected){
        String [] wordDict = wordDictStr.split(",");
        Assertions.assertEquals(expected, shortestWordDistance(wordDict, word1, word2));
    }
}
