package trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * In this problem, we are considering the words that are composed of a prefix and a postfix. For example, if we append a postfix “happy” to a prefix “un”, it forms the word “unhappy”. Similarly, “disagree” is formed from a prefix, “dis” followed by a postfix, “agree”.
 *
 * You’re given a dictionary, dictionary, consisting of prefixes, and a sentence, sentence, which has words separated by spaces only. Your task is to replace the postfix in sentence with their prefixes given in dictionary (if found) and return the modified sentence.
 *
 * A couple of points to keep in mind:
 *
 *     If a postfix in the sentence matches more than one prefix in the dictionary, replace it with the prefix that has the shortest length. For example, if we have the sentence “iphone is amazing”, and the dictionary {“i”, “ip”, “hone”}, then the word “iphone” has two prefixes in the dictionary “i” and “ip”, but we will replace it with the one that is shorter among the two, that is, “i”.
 *
 *     If there is no root word against any word in the sentence, leave it unchanged.
 *
 * Constraints:
 *
 *     1≤1≤ dictionary.length ≤1000≤1000
 *     1≤1≤ dictionary[i].length ≤100≤100
 *     dictionary[i] consists of only lowercase letters.
 *     1≤1≤ sentence.length ≤103≤103
 *     The number of words in sentence is in the range [1,100][1,100].
 *     The length of each word in sentence is in the range [1,100][1,100].
 *     Two consecutive words in sentence should be separated by exactly one space.
 *     All words in sentence are lowercase.
 *     For a word in sentence, the length of a prefix can be [1,100][1,100], and the length of a postfix can be [0,100][0,100].
 */
public class ReplaceWords {
    private class MyTrie {
        TrieNode root = new TrieNode();

        public void insert(String word) {
            TrieNode current = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (current.getChildren(ch) == null) {
                    var charNode = new TrieNode();
                    current.addChildren(ch, charNode);
                }
                current = current.getChildren(ch);
            }
            current.leaf(true);
        }

        public String minPrefix(String word){
            StringBuilder prefix = new StringBuilder("");
            TrieNode current = root;
            for(int i = 0; i < word.length(); i++){
                char ch = word.charAt(i);
                var child = current.getChildren(ch);
                if(child != null){
                    prefix.append(ch);
                    if(child.isLeaf()){
                        return prefix.toString();
                    }
                   current = child;
                } else {
                    break;
                }
            }
            return "";
        }
    }


    private class TrieNode {
        Map<Character, TrieNode> children;
        boolean leaf;
        public TrieNode(){
            this.children = new TreeMap<>();
            this.leaf = false;
        }
        public boolean isLeaf(){
            return leaf;
        }
        public void leaf(boolean leaf){
            this.leaf = leaf;
        }
        public TrieNode getChildren(Character ch){
            if(children.containsKey(ch)){
                return children.get(ch);
            }
            return null;
        }
        public void addChildren(Character ch, TrieNode trieNode){
            children.put(ch, trieNode);
        }

        public Map<Character, TrieNode> getAllChildren(){
            return children;
        }
    }

    public String replaceWords(String sentence, List<String> dictionary) {
        StringBuilder result = new StringBuilder();
        MyTrie trie = new MyTrie();
        for(int i = 0; i < dictionary.size(); i++){
            trie.insert(dictionary.get(i));
        }
        String[] words = sentence.split(" ");
        for(int i =0; i < words.length; i++){
           String minPrefixMatched = trie.minPrefix(words[i]);
           String matched = minPrefixMatched.isEmpty() ? words[i]: minPrefixMatched;
           result.append(matched);
           if(i != words.length -1){
               result.append( " ");
           }
        }
        return result.toString();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "when life gives you lemons make lemonade|li,gi,lem,m,l|when l gi you l m l",
             "wear sunglasses to watch fireworks|ear,glasses,work|wear sunglasses to watch fireworks",
            "coward runs attractive speed offered lighthouse|offer,cow,attract,at,of,light,house|cow runs at speed of light"
    })
    public void test(String sentence, String dicStr, String expected){
        Assertions.assertEquals(expected, replaceWords(sentence, Arrays.stream(dicStr.split(",")).toList()));
    }
}
