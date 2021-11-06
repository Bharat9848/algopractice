package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.stream.IntStream;

/**
 * A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.
 * <p>
 * Implement the Trie class:
 * <p>
 * Trie() Initializes the trie object.
 * void insert(String word) Inserts the string word into the trie.
 * boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
 * boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input
 * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
 * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
 * Output
 * [null, null, true, false, true, null, true]
 * <p>
 * Explanation
 * Trie trie = new Trie();
 * trie.insert("apple");
 * trie.search("apple");   // return True
 * trie.search("app");     // return False
 * trie.startsWith("app"); // return True
 * trie.insert("app");
 * trie.search("app");     // return True
 */
class Trie {

    /**
     * Initialize your data structure here.
     */
    private TrieNode root;
    public Trie() {
        root = new TrieNode(false, null);
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        if (word==null)return;
        String word1 = word.toLowerCase();
        Pair<Integer, TrieNode> prefixFoundTillTreeNode =  searchPrefix(word1);
        Integer prefixTill = prefixFoundTillTreeNode.getFirst();
        TrieNode startInsertFromTrieNode = prefixFoundTillTreeNode.getSec();
        if(prefixTill == word1.length()-1){
            startInsertFromTrieNode.value = word1;
            startInsertFromTrieNode.isLeaf = true;
        }else{
            TrieNode parent = startInsertFromTrieNode;
            for (int i = prefixTill+1; i < word.length(); i++) {
                char next = word.charAt(i);
                int offset = next-'a';
                if(parent.children == null){ parent.children = new TrieNode[26];}
                parent.children[offset] = new TrieNode(false, null);
                parent = parent.children[offset];
            }
            parent.isLeaf = true;
            parent.value = word;
        }
    }

    private Pair<Integer, TrieNode> searchPrefix(String word) {
        String word1 = word;
        TrieNode current = root;
        for (int i = 0; i < word1.length(); i++) {
            char next = word1.charAt(i);
            int offset = next - 'a';
            if(current.children == null || current.children[offset] == null){
                return new Pair<>(i-1, current);
            } else {
                current = current.children[offset];
            }
        }
        return new Pair<>(word.length()-1, current);
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        if(word==null) return false;
        Pair<Integer, TrieNode> integerTrieNodePair = searchPrefix(word);
        return word.equalsIgnoreCase(integerTrieNodePair.getSec().value);
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        Pair<Integer, TrieNode> integerTrieNodePair = searchPrefix(prefix);
        return integerTrieNodePair.getFirst() == prefix.length()-1;
    }

    private class TrieNode{
        boolean isLeaf;
        TrieNode[] children;
        String value;

        TrieNode(boolean isLeaf, String value) {
            this.isLeaf = isLeaf;
            if(isLeaf){
                this.value = value;
            } else {
                this.children = new TrieNode[26];
            }
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "Trie,insert,search,search,startsWith,insert,search|,apple,apple,app,app,app,app|,,true,false,true,,true"
    })
    void testHappy(String operationsStr,String inputStr,String returnStr){
        String[] operations =  operationsStr.split(",");
        String[] inputs =  inputStr.split(",");
        String[] returns =  returnStr.split(",");
        Trie tree = null;
        for (int index = 0; index < operations.length; index++) {
            String operation = operations[index];
            String input = inputs[index];
            switch (operation) {
                case "Trie":
                    tree = new Trie();
                    break;
                case "insert":
                    tree.insert(input);
                    break;
                case "search":
                    System.out.println(operation +"(" + input+ ")" + "expected = " + returns[index]  );
                    Assert.assertEquals(Boolean.valueOf(returns[index]), tree.search(input));
                    break;
                case "startsWith":
                    System.out.println(operation +"(" + input+ ")" + "expected = " + returns[index]);
                    Assert.assertEquals(Boolean.valueOf(returns[index]), tree.startsWith(input));
                    break;
            }
        }
    }
}


