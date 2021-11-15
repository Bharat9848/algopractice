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
        int levelCount = 0;
        Map<String, Boolean> dictionaryVisited = wordList.stream().collect(Collectors.toMap(w -> w, w -> false));
        List<String> level = new LinkedList<>();
        level.add(beginWord);
        boolean endWordFound = false;
        while (!level.isEmpty() && !endWordFound){
            List<String> nextLevel = new LinkedList<>();
            while (!level.isEmpty() && !endWordFound){
                String word = level.remove(0);
                if(word.equals(endWord)){
                    endWordFound = true;
                }else {
                    List<String> newGeneratedWords = findSigleLetterDistance(word);
                    for (int i = 0; i < newGeneratedWords.size(); i++) {
                        String newWord = newGeneratedWords.get(i);
                        if(!dictionaryVisited.containsKey(newWord)){
                            continue;
                        }else {
                            Boolean visitedBefore = dictionaryVisited.get(newWord);
                            if(visitedBefore){
                                continue;
                            }else {
                                dictionaryVisited.put(newWord, true);
                                nextLevel.add(newWord);
                            }
                        }
                    }
                }
            }
            level = nextLevel;
            levelCount+=1;
        }
        return endWordFound ? levelCount:0;
    }

    private List<String> findSigleLetterDistance(String word) {
        List<String> newWords = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            for (int j = 0; j < 26; j++) {
                if (ch - 'a' == j) {
                    continue;
                } else {
                    char newChar = (char) ('a' + j);
                    newWords.add((i > 0 ? word.substring(0, i) : "") + Character.valueOf(newChar).toString() + (i < word.length() - 1 ? word.substring(i + 1) : ""));
                }
            }
        }
        return newWords;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "hit|cog|hot,dot,dog,lot,log|0",
            "hit|cog|hot,dot,dog,lot,log,cog|5"
    })
    void test(String a, String b, String dicStr, int expected){
        List<String> dict = Arrays.stream(dicStr.split(",")).collect(Collectors.toList());
        Assert.assertEquals(expected, ladderLength(a, b, dict));
//        System.out.println(findSigleLetterDistance("hot"));
    }
}
