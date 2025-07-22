package trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.*;

/**
 * Given an array of strings called products and a word to search, design a system that, when each character of the searched word is typed, suggests at most three product names from products. Suggested products should share a common prefix with the searched word. If more than three products exist with a common prefix, return the three product names that appear first in lexicographical order.
 *
 * Return the suggested products, which will be a list of lists after each character of searched word is typed.
 *
 * Constraints:
 *
 *     1≤1≤ products.length ≤1000≤1000
 *     1≤1≤ products[i].length ≤3000≤3000
 *     1≤1≤ sum(products[i].length) ≤2×103≤2×103
 *     All the strings of products are unique.
 *     products[i] consists of lowercase English letters.
 *     1≤1≤ searched word.length ≤1000≤1000
 *     The searched word consists of lowercase English letters.
 */
public class SearchSuggestion {
    private class Trie {
        TrieNode root = new TrieNode();
        public void insert(String word){
            TrieNode current = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if(current.getChildren(ch) == null){
                    var charNode = new TrieNode();
                    current.addChildren(ch, charNode);
                }
                current = current.getChildren(ch);
            }
            current.leaf(true);
        }

        public List<String> searchSuggest(String prefix, int noOfChildren){
            TrieNode current = root;
            List<String> result = new ArrayList<>();
            for(int i =0; i < prefix.length();i++){
                Character ch = prefix.charAt(i);
                if(current.getChildren(ch) == null){
                    return result;
                }
                current = current.getChildren(ch);
            }

            findAllChildren(current, result, prefix, noOfChildren);
            return result;
        }

        private void findAllChildren(TrieNode current, List<String> result, String prefix, int noOfChildren) {
            if(current.isLeaf()){
                result.add(prefix);
            }
            Map<Character, TrieNode> children = current.getAllChildren();
            for( Character ch: children.keySet()) {
                if(result.size() == noOfChildren){
                    break;
                }
                findAllChildren(children.get(ch), result, prefix + ch, noOfChildren);
            }
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
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> result = new ArrayList<>();
        Trie myTrie = new Trie();
        for (int i = 0; i < products.length; i++) {
            myTrie.insert(products[i]);
        }
        for (int i = 0; i < searchWord.length(); i++) {
            String word = searchWord.substring(0, i+1);
            var suggestions = myTrie.searchSuggest(word, 3);
                result.add(suggestions);
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "bags,baggage,banner,box,clothes|bags|[[baggage,bags,banner],[baggage,bags,banner],[baggage,bags],[bags]]"
    })
    void test(String productsStr, String searchWord, String expectedStr){
        List<List<String>> expected = StringParser.parseStringAsListOfList(expectedStr, "\\[([a-z]+,?)+\\]", s -> s);
        List<List<String>>actual= suggestedProducts(productsStr.split(","), searchWord);
        Assertions.assertEquals(expected, actual);
    }
}
