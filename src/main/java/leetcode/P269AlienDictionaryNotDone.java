package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * There is a new alien language that uses the English alphabet. However, the order among the letters is unknown to you.
 *
 * You are given a list of strings words from the alien language's dictionary, where the strings in words are sorted lexicographically by the rules of this new language.
 *
 * Return a string of the unique letters in the new alien language sorted in lexicographically increasing order by the new language's rules. If there is no solution, return "". If there are multiple solutions, return any of them.
 *
 * A string s is lexicographically smaller than a string t if at the first letter where they differ, the letter in s comes before the letter in t in the alien language. If the first min(s.length, t.length) letters are the same, then s is smaller if and only if s.length < t.length.
 *
 *
 *
 * Example 1:
 *
 * Input: words = ["wrt","wrf","er","ett","rftt"]
 * Output: "wertf"
 *
 * Example 2:
 *
 * Input: words = ["z","x"]
 * Output: "zx"
 *
 * Example 3:
 *
 * Input: words = ["z","x","z"]
 * Output: ""
 * Explanation: The order is invalid, so return "".
 *
 * Disjoint data structure using hashmap. Starting with all letter in their individual set.
 *
 * Constraints:
 *
 *     1 <= words.length <= 100
 *     1 <= words[i].length <= 100
 *     words[i] consists of only lowercase English letters.
 */
class P269AlienDictionaryNotDone {
    String alienOrder(String[] words) {
        Map<Character, UnionFind> charVsDictionaryStr = new HashMap<>();
        if(words.length == 1){
            return "";
        }
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            int len = Math.max(word1.length(), word2.length());
            for (int j = 0; j < len; j++) {
                Character ch = j< word1.length()? word1.charAt(j):null;
                Character ch2 = j < word2.length()? word2.charAt(j):null;
                if(ch != null){
                    charVsDictionaryStr.putIfAbsent(ch, new UnionFind("" + ch));
                }
                if(ch2 != null){
                    charVsDictionaryStr.putIfAbsent(ch2, new UnionFind("" + ch2));
                }
                if(ch == null || ch2 == null){
                    break;
                }else if(ch.equals(ch2)){
                    continue;
                }else{

                    UnionFind chSet = charVsDictionaryStr.get(ch);
                    UnionFind ch2Set = charVsDictionaryStr.get(ch2);
                    UnionFind rootCh1 = chSet.findRoot();
                    UnionFind ch2SetRoot = ch2Set.findRoot();
                    if(rootCh1 != ch2SetRoot){
                        rootCh1.join(ch2SetRoot);
                    }else if(rootCh1.val.indexOf(ch) > rootCh1.val.indexOf(ch2)){
                        return "";
                    }
                }
            }
        }
        return charVsDictionaryStr.values().stream().map(UnionFind::findRoot).reduce((s1,s2) -> {if(s1 != s2)s1.join(s2); return s1;}).map(se1 -> se1.val).orElse("");
    }

    private class UnionFind{
        private UnionFind parent;
        private String val;
        private int size;
        public UnionFind(String val){
            this.val = val;
            this.size = 1;
        }
        public void join(UnionFind subRoot){
            subRoot.parent = this;
            this.size = this.size + subRoot.size;
            this.val = this.val + subRoot.val;
        }

        public UnionFind findRoot(){
            UnionFind temp = this;
            while(temp.parent != null)
            {
                temp = temp.parent;
            }
            return temp;
        }

        public int size() {
            return size;
        }
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
          "wrt,wrf,er,ett,rftt|wertf",
          "z,x|zx",
          "z,x,z|",
            "zy,zx|yxz"
    })
    void test(String dicStr, String expected){
        String[] words = dicStr.split(",");
        Assert.assertEquals(expected==null?"":expected, alienOrder(words));
    }
}
