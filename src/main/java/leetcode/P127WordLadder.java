package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
 *
 *     Every adjacent pair of words differs by a single letter.
 *     Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
 *     sk == endWord
 *
 * Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.
 *
 * Example 1:
 *
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: 5
 * Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.
 *
 * Example 2:
 *
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * Output: 0
 * Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 *
 *
 *
 * Constraints:
 *
 *     1 <= beginWord.length <= 10
 *     endWord.length == beginWord.length
 *     1 <= wordList.length <= 5000
 *     wordList[i].length == beginWord.length
 *     beginWord, endWord, and wordList[i] consist of lowercase English letters.
 *     beginWord != endWord
 *     All the words in wordList are unique.
 */
class P127WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int transformationBegin =1;
        int transformationEnd = 1;
        Set<String> dictionary = new HashSet<>(wordList);
        if(!dictionary.contains(endWord)){
            return 0;
        }
        Set<String> queueBegin = new HashSet<>();
        Set<String> queueEnd = new HashSet<>();
        queueBegin.add(beginWord);
        queueEnd.add(endWord);
        Set<String> seenBegin = new HashSet<>();
        Set<String> seenEnd = new HashSet<>();
        while (!queueBegin.isEmpty() && !queueEnd.isEmpty()){
            Set<String> nextLevelBegin = new HashSet<>();
            Set<String> nextLevelEnd = new HashSet<>();
            for(String current: queueBegin){
                Set<String> allTransformed = findAllTransformed(current, dictionary, seenBegin);
                for(String transformed: allTransformed){
                    if(queueEnd.contains(transformed)){
                        return transformationBegin + transformationEnd;
                    }else{
                        nextLevelBegin.add(transformed);
                    }
                }
            }
            transformationBegin +=1;
            for(String current: queueEnd){
                Set<String> allTransformed = findAllTransformed(current, dictionary, seenEnd);
                for(String transformed: allTransformed){
                    if(nextLevelBegin.contains(transformed)){
                        return transformationBegin + transformationEnd;
                    }else{
                        nextLevelEnd.add(transformed);
                    }
                }
            }
            transformationEnd += 1;
            if(!nextLevelBegin.isEmpty() && !nextLevelEnd.isEmpty()){
                seenBegin.addAll(queueBegin);
                seenEnd.addAll(queueEnd);
                queueBegin = nextLevelBegin;
                queueEnd = nextLevelEnd;
            }else{
                queueBegin = new HashSet<>();
                queueEnd = new HashSet<>();
            }
        }
        return 0;
    }

    private Set<String> findAllTransformed(String current, Set<String> dictionary, Set<String> seen) {
        return dictionary.stream()
                .filter(word -> !word.equals(current))
                .filter(word -> isOneCharDist(word, current) && !seen.contains(word))
                .collect(Collectors.toSet());
    }

    private boolean isOneCharDist(String word, String current) {
        int count = 0;
        for (int i = 0; i < current.length() && count < 2; i++) {
            if(word.charAt(i) != current.charAt(i)){
                count++;
            }
        }
        return count==1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "hit|hot|hot,dot,dog,lot,log|2",
            "hit|cog|hot,dot,dog,lot,log|0",
            "hit|cog|hot,dot,dog,lot,log,cog|5"
    })
    void test(String a, String b, String dicStr, int expected){
        List<String> dict = Arrays.stream(dicStr.split(",")).collect(Collectors.toList());
        Assert.assertEquals(expected, ladderLength(a, b, dict));
//        System.out.println(findSigleLetterDistance("hot"));
    }
}
