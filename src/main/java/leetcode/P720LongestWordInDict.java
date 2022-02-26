package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given an array of strings words representing an English Dictionary, return the longest word in words that can be built one character at a time by other words in words.
 *
 * If there is more than one possible answer, return the longest word with the smallest lexicographical order. If there is no answer, return the empty string.
 *
 * Input: words = ["w","wo","wor","worl","world"]
 * Output: "world"
 * Explanation: The word "world" can be built one character at a time by "w", "wo", "wor", and "worl".
 *
 * Input: words = ["a","banana","app","appl","ap","apply","apple"]
 * Output: "apple"
 * Explanation: Both "apply" and "apple" can be built from other words in the dictionary. However, "apple" is lexicographically smaller than "apply".
 *     1 <= words.length <= 1000
 *     1 <= words[i].length <= 30
 *     words[i] consists of lowercase English letters.
 */
class P720LongestWordInDict {
    private class TrieNode{
        Map<Character, TrieNode> children;
        boolean isWord;
        TrieNode(){
            children = new HashMap<>();
            isWord = false;
        }
    }
    private class Trie{
        private TrieNode root;

        void addWord(String word){
            if(root == null){
                root = new TrieNode();
            }
            TrieNode temp = root;
            for (int i = 0; i < word.length(); i++) {
                Character ch = word.charAt(i);
                temp.children.putIfAbsent(ch, new TrieNode());
                temp = temp.children.get(ch);
            }
            temp.isWord = true;
        }
        TrieNode getRoot(){
            return root;
        }
    }

    String longestWord(String[] words) {
        Trie dictionary  = new Trie();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            dictionary.addWord(word);
        }
        TrieNode root = dictionary.getRoot();
        LinkedHashMap<String, TrieNode> level = root.children.entrySet().stream().filter(charToTrie -> charToTrie.getValue().isWord).collect(LinkedHashMap::new, (map, charToNode) -> map.put(charToNode.getKey() + "", charToNode.getValue()), HashMap::putAll);
        if(level.isEmpty()){
            return "";
        }
        while(true){
            LinkedHashMap<String, TrieNode> nextLevel = new LinkedHashMap<>();
            for (Map.Entry<String, TrieNode> wordToNode : level.entrySet()) {
                String prefix = wordToNode.getKey();
                TrieNode children = wordToNode.getValue();
                children.children.entrySet().stream().filter(characterTrieNodeEntry -> characterTrieNodeEntry.getValue().isWord).forEach(characterTrieNodeEntry -> {
                    nextLevel.put(prefix + characterTrieNodeEntry.getKey(), characterTrieNodeEntry.getValue());
                });
            }
            if(nextLevel.isEmpty()){
                return level.keySet().stream().sorted().collect(Collectors.toList()).get(0);
            }else{
                level = nextLevel;
            }
        }
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "a,banana,app,appl,ap,apply,apple|apple",
            "w,wo,wor,worl,world|world",
            "wo,wor,world|"
    })
    void test(String dictStr, String expected){
        if(expected == null){ expected = "";}
        String [] words = dictStr.split(",");
        Assertions.assertEquals(expected, longestWord(words));
    }
}
