package topological.sort.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * In this challenge, you are given a list of words written in an alien language, where the words are sorted lexicographically by the rules of this language. Surprisingly, the aliens also use English lowercase letters, but possibly in a different order.
 *
 * Given a list of words written in the alien language, you have to return a string of unique letters sorted in the lexicographical order of the alien language as derived from the list of words.
 *
 * If there’s no solution, that is, no valid lexicographical ordering, you can return an empty string.
 *
 *     Note: The lexicographic order of a given language is defined by the order in which the letters of its alphabet appear. In English, the letter “n” appears before the letter “r” in the alphabet. As a result, in two words that are the same up to the point where one features “n” and the other features “r,” the former is considered the lexicographically smaller word of the two. For this reason, “ban” is considered lexicographically smaller than “bar.”
 *
 *     Similarly, if an input contains words followed by their prefix, such as “educated” and then “educate,” these cases will never result in a valid alphabet because in a valid alphabet, prefixes are always first.
 *
 * Constraints:
 *
 *     1≤1≤ words.length ≤103≤103
 *     1≤1≤ words[i].length ≤20≤20
 *     All characters in words[i] are English lowercase letters.
 */
public class AlienDictionary {
    public static String alienOrder(List<String> words) {
        if(words.size() == 1){
            return "";
        }
        StringBuilder result = new StringBuilder("");
        Map<Character, List<Character>> outgoingGraph = new HashMap<>();
        Map<Character, Integer> incoming = new HashMap<>();
        for (int i = 0; i < words.size() - 1; i++) {
            String before = words.get(i);
            String after = words.get(i + 1);
            int j = 0;
            boolean firstMisMatch = false;
            for (; j < before.length() && j < after.length(); j++) {
                char beforeChar = before.charAt(j);
                char afterChar = after.charAt(j);
                outgoingGraph.putIfAbsent(beforeChar, new ArrayList<>());
                outgoingGraph.putIfAbsent(afterChar, new ArrayList<>());
                incoming.putIfAbsent(beforeChar, 0);
                incoming.putIfAbsent(afterChar, 0);
                if(!firstMisMatch && beforeChar != afterChar) {
                    outgoingGraph.get(beforeChar).add(afterChar);
                    incoming.put(afterChar, incoming.get(afterChar) + 1);
                    firstMisMatch = true;
                }
            }
            if(j < before.length() && j == after.length() && before.substring(0, j-1).equals(after)) {
                return "";
            }
            if (j < before.length()) {
                while (j < before.length()) {
                    outgoingGraph.putIfAbsent(before.charAt(j), new ArrayList<>());
                    incoming.putIfAbsent(before.charAt(j), 0);
                    j++;
                }
            }
            if (j < after.length()) {
                while (j < after.length()) {
                    outgoingGraph.putIfAbsent(after.charAt(j), new ArrayList<>());
                    incoming.putIfAbsent(after.charAt(j), 0);
                    j++;
                }

            }
        }
            List<Character> queue = new LinkedList<>();
            for(var incomingNode: incoming.entrySet()){
                var node = incomingNode.getKey();
                var value = incomingNode.getValue();
                if( value == 0){
                    queue.add(node);
                }
            }
            if(queue.size() ==  incoming.size()){
                return "";
            }
            while (!queue.isEmpty()){
                Character ch = queue.remove(0);
                result.append(ch);
                List<Character> outgoingEdges = outgoingGraph.get(ch);
                for(var nextNode: outgoingEdges){
                    incoming.put(nextNode, incoming.get(nextNode)-1);
                }
                incoming.remove(ch);
                outgoingGraph.remove(ch);
                if(queue.isEmpty()){
                    List<Character> queue1 = new LinkedList<>();
                    for(var incomingNode: incoming.entrySet()){
                        var node = incomingNode.getKey();
                        var value = incomingNode.getValue();
                        if( value == 0){
                            queue1.add(node);
                        }
                    }
                    queue = queue1;
                }
            }

            if(!incoming.isEmpty()){
                return "";
            }
        return result.toString();
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "educ,edu|",
            "m,mx,mxe,mxer,mxerl,mxerlo,mxerlos,mxerlost,mxerlostr,mxerlostrpq,mxerlostrp|",
            "mdx,mars,avgd,dkae|",
            "wrt,wrf,er,ett,rftt|wertf",
            "alpha,bravo,charlie,delta|aehiloprtvbcd",
            "ca,aa,ab|cab"
    })
    void test(String wordsStr, String expected){
        if(expected== null){
            expected = "";
        }
        Assertions.assertEquals(expected, alienOrder(Arrays.stream(wordsStr.split(",")).toList()));
    }
}
