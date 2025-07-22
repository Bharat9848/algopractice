package trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/*Trie is a tree-like data structure used to store strings. The tries are also called prefix trees because they provide very efficient prefix-matching operations. Implement a trie data structure with three functions that perform the following tasks:

    Insert (word): This inserts a word into the trie.
    Search (word): This searches the given word in the trie and returns TRUE, if found. Otherwise, return FALSE.
    Search prefix (prefix): This searches the given prefix in the trie and returns TRUE, if found. Otherwise, return FALSE.

Constraints:

    1≤1≤ word.length, prefix.length ≤500≤500
    The strings consist only of lowercase English letters.
    At most, 103103 calls in total will be made to the functions.

    Implementation notes
    Root node = trieNode
    TrieNode
     children -> Map<Character, TrieNode>
     isLeaf -> Boolean
    Init:
    root = EmptyTrieNode

*/
public class Trie {
    private class TrieNode {
        Map<Character, TrieNode> children;
        boolean leaf;
        public TrieNode(){
            this.children = new HashMap<Character, TrieNode>();
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
    }
    private class myTrie{
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

        public boolean search(String word){
            TrieNode current = root;
            for(int i = 0; i < word.length(); i++){
                char ch = word.charAt(i);
                if(current.getChildren(ch) == null){
                    return false;
                }
                current = current.getChildren(ch);
            }
            return current.isLeaf();
        }

        public boolean prefixExist(String word){
            TrieNode current = root;
            for(int i = 0; i < word.length(); i++){
                char ch = word.charAt(i);
                if(current.getChildren(ch) == null){
                    return false;
                }
                current = current.getChildren(ch);
            }
            return current != null;
        }
}

@ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "MyTrie,insert,search,prefixExist,search|,treat,treat,tre,treats|,,true,true,false",
            "MyTrie,insert,insert,search,prefixExist,prefixExist,search|,atom,at,at,at,ato,atom|,,,true,true,true,true",
    })
    void test(String operationStr, String argumentStr, String expectedStr){
    String[] operations = operationStr.split(",");
    String[] arguments = argumentStr.split(",");
    String[] expected = expectedStr.split(",");
    myTrie trie = new myTrie();
    for(int i =1; i < operations.length; i++){
        switch (operations[i]){
            case "search" : {
                var actual = trie.search(arguments[i]);
                System.out.printf("for %s %s\n",arguments[i], actual);
                Assertions.assertEquals(Boolean.parseBoolean(expected[i]),actual);
                break;
            }
            case "insert" : {
                trie.insert(arguments[i]);
                break;
            }
            case "prefixExist": {
                var actual = trie.prefixExist(arguments[i]);
                System.out.printf("for %s %s\n",arguments[i], actual);
                Assertions.assertEquals(Boolean.parseBoolean(expected[i]), actual);
                break;
            }
        }
    }

}
}
