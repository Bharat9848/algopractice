package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Design a data structure that supports adding new words and finding if a string matches any previously added string.
 *
 * Implement the WordDictionary class:
 *
 *     WordDictionary() Initializes the object.
 *     void addWord(word) Adds word to the data structure, it can be matched later.
 *     bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.
 *
 * Input
 * ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
 * [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
 * Output
 * [null,null,null,null,false,true,true,true]
 *
 * Explanation
 * WordDictionary wordDictionary = new WordDictionary();
 * wordDictionary.addWord("bad");
 * wordDictionary.addWord("dad");
 * wordDictionary.addWord("mad");
 * wordDictionary.search("pad"); // return False
 * wordDictionary.search("bad"); // return True
 * wordDictionary.search(".ad"); // return True
 * wordDictionary.search("b.."); // return True
 *
 *
 *
 * Constraints:
 *
 *     1 <= word.length <= 25
 *     word in addWord consists of lowercase English letters.
 *     word in search consist of '.' or lowercase English letters.
 *     There will be at most 3 dots in word for search queries.
 *     At most 104 calls will be made to addWord and search.
 */
public class P211DesignAddSearchWordDictionary {
    private class TrieNode{
        boolean isWord;
        Map<Character, TrieNode> children;
        public TrieNode() {
            this.children = new HashMap<>();
            isWord = false;
        }
    }
    private TrieNode root;
    void init() {
        root = new TrieNode();
    }

    public void addWord(String word) {
        TrieNode temp = root;
        for (int i = 0; i < word.length(); i++) {
            Character ch = word.charAt(i);
            temp.children.putIfAbsent(ch, new TrieNode());
            temp = temp.children.get(ch);
        }
        temp.isWord = true;
    }

    public boolean search(String word) {
        return search(root, word);
    }

    private boolean search(TrieNode root, String word) {
        if ("".equals(word)) {
            return root.isWord;
        }
        Character ch = word.charAt(0);
        if (ch != '.') {
            if (root.children.containsKey(ch)) {
                return search(root.children.get(ch), word.substring(1));
            } else {
                return false;
            }
        } else {
            for (Map.Entry<Character, TrieNode> charToTrieChild : root.children.entrySet()) {
                if (search(charToTrieChild.getValue(), word.substring(1))) {
                    return true;
                }
            }
            return false;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "WordDictionary,addWord,addWord,addWord,addWord,search,search,addWord,search,search,search,search,search,search|,at,and,an,add,a,.at,bat,.at,an.,a.d.,b.,a.d,.|null,null,null,null,null,false,false,null,true,true,false,false,true,false",
            "WordDictionary,addWord,addWord,addWord,search,search,search,search,search,search|,bad,dad,mad,pad,bad,.ad,b..,beds,bads|null,null,null,null,false,true,true,true,false,false"
    })
    void test(String operationStr, String argsStr, String expectedStr){
        String[] operations = operationStr.split(",");
        String[] argument = argsStr.split(",");
        String[] expected = expectedStr.split(",");
        P211DesignAddSearchWordDictionary dictionary = null;
        for (int i = 0; i < operations.length; i++) {
            switch (operations[i]){
                case "WordDictionary" :{
                    dictionary = new P211DesignAddSearchWordDictionary();
                    dictionary.init();
                }
                    break;
                case "addWord": dictionary.addWord(argument[i]);
                break;
                case "search":
                    Assertions.assertEquals(Boolean.parseBoolean(expected[i]), dictionary.search(argument[i]));
            }
        }
    }
}
