package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given an array of strings products and a string searchWord. We want to design a system that suggests at most three product names from products after each character of searchWord is typed. Suggested products should have common prefix with the searchWord. If there are more than three products with a common prefix return the three lexicographically minimums products.
 *
 * Return list of lists of the suggested products after each character of searchWord is typed.
 *
 *
 *
 * Example 1:
 *
 * Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
 * Output: [
 * ["mobile","moneypot","monitor"],
 * ["mobile","moneypot","monitor"],
 * ["mouse","mousepad"],
 * ["mouse","mousepad"],
 * ["mouse","mousepad"]
 * ]
 * Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
 * After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
 * After typing mou, mous and mouse the system suggests ["mouse","mousepad"]
 *
 * Example 2:
 *
 * Input: products = ["havana"], searchWord = "havana"
 * Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
 *
 * Example 3:
 *
 * Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
 * Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
 *
 * Example 4:
 *
 * Input: products = ["havana"], searchWord = "tatiana"
 * Output: [[],[],[],[],[],[],[]]
 *
 *
 *
 * Constraints:
 *
 *     1 <= products.length <= 1000
 *     There are no repeated elements in products.
 *     1 <= Σ products[i].length <= 2 * 10^4
 *     All characters of products[i] are lower-case English letters.
 *     1 <= searchWord.length <= 1000
 *     All characters of searchWord are lower-case English letters.
 */
public class P1268SearchSuggestionSystem {
    private static final int AT_MOST_RESULT=3;
    private class Trie {
        private class TrieNode{
            boolean isWord;
            TrieNode[] children;
            String word;
            TrieNode(){
                isWord = false;
                children = new TrieNode[26];
            }
        }
        Trie(){
            root = new TrieNode();
        }
        private TrieNode root;
        public void insertWord(String word){
            TrieNode temp = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index = ch -'a';
                if(temp.children[index] == null){
                    temp.children[index] = new TrieNode();
                }
                temp = temp.children[index];
            }
            temp.isWord = true;
        }

        public List<String> searchPrefix(String prefix, int maxResultCount){
            TrieNode prefixNode = root;
            for (int i = 0; i < prefix.length() && prefixNode != null; i++) {
                char ch = prefix.charAt(i);
                int index = ch - 'a';
                prefixNode = prefixNode.children[index];
            }
            if(prefixNode == null){
                return new ArrayList<>();
            }else{
                return findWordsInSeq(prefixNode, prefix, maxResultCount);
            }
        }

        private List<String> findWordsInSeq(TrieNode prefixNode, String prefix, int maxResultCount) {
            List<String> words = new ArrayList<>();
            findWordsUsingDepthSearch(prefixNode, prefix, words, maxResultCount);
            return words;
        }

        private void findWordsUsingDepthSearch(TrieNode prefixNode, String prefix, List<String> words, int maxResultCount) {
            if(words.size() == maxResultCount){
                return;
            }
            if(prefixNode.isWord){
                words.add(prefix);
            }
            for (int i = 0; i < 26 && words.size() < maxResultCount; i++) {
                if(prefixNode.children[i] != null){
                    char char1 = (char)('a' + i);
                    findWordsUsingDepthSearch(prefixNode.children[i], prefix + char1, words, maxResultCount);
                }
            }
        }
    }
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Trie dictionary = new Trie();
        for (String word: products){
            dictionary.insertWord(word);
        }
        ArrayList<List<String>> result = new ArrayList<>();
        for (int i = 0; i < searchWord.length(); i++) {
            String prefix = searchWord.substring(0, i+1);
            List<String> words = dictionary.searchPrefix(prefix, 3);
            result.add(words);
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "mobile,mouse,moneypot,monitor,mousepad|mouse|mobile,moneypot,monitor;mobile,moneypot,monitor;mouse,mousepad;mouse,mousepad;mouse,mousepad",
            "bags,baggage,banner,box,cloths|bags|baggage,bags,banner;baggage,bags,banner;baggage,bags;bags",
            "havana|havana|havana;havana;havana;havana;havana;havana",
//            "havana|tatiana| ; ; ; ; ; ; ;"
    })
    void test(String productsStr, String word, String expectListOfResult){
        String[] products = productsStr.split(",");
        List<List<String>> expected = Arrays.stream(expectListOfResult.split(";")).map(str -> Arrays.stream(str.trim().split(",")).collect(Collectors.toList())).collect(Collectors.toList());
        Assert.assertEquals(expected, suggestedProducts(products, word));
    }
}
