package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
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
class P269AlienDictionary {
    String alienOrder(String[] words) {
        Map<Character, Pair<Set<Character>,Set<Character>>> adjMapWithOutboundAndInboundEdges = new HashMap<>();
        Map<Character, Boolean> visited = new HashMap<>();
        if(words.length == 1){
            for (int i = 0; i < words[0].length(); i++) {
                adjMapWithOutboundAndInboundEdges.putIfAbsent(words[0].charAt(i), new Pair<>(new HashSet<>(), new HashSet<>()));
            }
        }else{
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            boolean firstMismatch = false;
            if(word1.length() > word2.length() && word1.startsWith(word2)){
                return "";
            }
            for (int j = 0; j < Math.max(word1.length(), word2.length()); j++) {
                if(j < Math.min(word1.length(), word2.length())){
                    char ch1 = word1.charAt(j);
                    char ch2 = word2.charAt(j);
                    adjMapWithOutboundAndInboundEdges.putIfAbsent(ch1, new Pair<>(new HashSet<>(), new HashSet<>()));
                    adjMapWithOutboundAndInboundEdges.putIfAbsent(ch2, new Pair<>(new HashSet<>(), new HashSet<>()));
                    if(ch1 != ch2 && !firstMismatch){
                        firstMismatch = true;
                        adjMapWithOutboundAndInboundEdges.get(ch2).getFirst().add(ch1); //inbound to ch2
                        adjMapWithOutboundAndInboundEdges.get(ch1).getSec().add(ch2); // outbound from ch1
                    }
                }else {
                    char ch1 = word1.length() > word2.length()? word1.charAt(j): word2.charAt(j);
                    adjMapWithOutboundAndInboundEdges.putIfAbsent(ch1, new Pair<>(new HashSet<>(), new HashSet<>()));
                }
            }

        }
        }

        List<Character> toProcessChars = new LinkedList<>();
        Collection<Character> noInboundChars = addAllNoInboundChars(visited, adjMapWithOutboundAndInboundEdges);
        if(noInboundChars.isEmpty()){
            return "";
        }else{
            toProcessChars.addAll(noInboundChars);
            toProcessChars.forEach(ch -> visited.put(ch, true));
        }
        char[] chars = new char[adjMapWithOutboundAndInboundEdges.size()];
        int index =0;
        while (!toProcessChars.isEmpty()){
            Character current = toProcessChars.remove(0);
            chars[index] = current;
            index++;
            Set<Character> outboundEdges = adjMapWithOutboundAndInboundEdges.get(current).getSec();
            outboundEdges.stream().map(c -> adjMapWithOutboundAndInboundEdges.get(c).getFirst()).forEach(inboundList -> inboundList.remove(current));
            if(!outboundEdges.isEmpty()){
                Collection<Character> nextChars = addAllNoInboundChars(visited, adjMapWithOutboundAndInboundEdges);
                toProcessChars.addAll(nextChars);
                nextChars.forEach(ch -> visited.put(ch, true));
            }
        }
        return index == adjMapWithOutboundAndInboundEdges.size() ? new String(chars):"";
    }

    private Collection<Character> addAllNoInboundChars(Map<Character, Boolean> visited, Map<Character, Pair<Set<Character>, Set<Character>>> adjMapWithOutboundAndInboundEdges) {
        return adjMapWithOutboundAndInboundEdges.entrySet().stream()
                .filter(e -> !visited.getOrDefault(e.getKey(), false) && e.getValue().getFirst().isEmpty())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "aba|ab",
            "z,x,a,zb,zx|",
            "ac,ab,b|acb",
            "wrt,wrtkj|rtwjk",
          "wrt,wrf,er,ett,rftt|wertf",
          "z,x|zx",
          "z,x,z|",
            "zy,zx|yzx",
            "ac,ab,zc,zb|aczb",
            "abc,ab|"
    })
    void test(String dicStr, String expected){
        String[] words = dicStr.split(",");
        Assert.assertEquals(expected==null?"":expected, alienOrder(words));
    }
}
