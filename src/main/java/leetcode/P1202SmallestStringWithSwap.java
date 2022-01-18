package leetcode;

import core.ds.UnionFind;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * You are given a string s, and an array of pairs of indices in the string pairs where pairs[i] = [a, b] indicates 2 indices(0-indexed) of the string.
 *
 * You can swap the characters at any pair of indices in the given pairs any number of times.
 *
 * Return the lexicographically smallest string that s can be changed to after using the swaps.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "dcab", pairs = [[0,3],[1,2]]
 * Output: "bacd"
 * Explaination:
 * Swap s[0] and s[3], s = "bcad"
 * Swap s[1] and s[2], s = "bacd"
 *
 * Example 2:
 *
 * Input: s = "dcab", pairs = [[0,3],[1,2],[0,2]]
 * Output: "abcd"
 * Explaination:
 * Swap s[0] and s[3], s = "bcad"
 * Swap s[0] and s[2], s = "acbd"
 * Swap s[1] and s[2], s = "abcd"
 *
 * Example 3:
 *
 * Input: s = "cba", pairs = [[0,1],[1,2]]
 * Output: "abc"
 * Explaination:
 * Swap s[0] and s[1], s = "bca"
 * Swap s[1] and s[2], s = "bac"
 * Swap s[0] and s[1], s = "abc"
 *
 *
 *
 * Constraints:
 *
 *     1 <= s.length <= 10^5
 *     0 <= pairs.length <= 10^5
 *     0 <= pairs[i][0], pairs[i][1] < s.length
 *     s only contains lower case English letters.
 */
public class P1202SmallestStringWithSwap {
    String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        UnionFind swapGroups = new UnionFind(s.length());
        for(List<Integer> pair : pairs){
            int src = pair.get(0);
            int dest = pair.get(1);
            if(!swapGroups.connected(src, dest)){
                swapGroups.union(src, dest);
            }
        }
        StringBuilder sb = new StringBuilder();
        Map<Integer, PriorityQueue<Character>> rootToSortedList = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            int root = swapGroups.find(i);
            rootToSortedList.putIfAbsent(root, new PriorityQueue<>(Character::compareTo));
            rootToSortedList.get(root).offer(s.charAt(i));
        }
        for (int i = 0; i < s.length(); i++) {
            int root = swapGroups.find(i);
            PriorityQueue<Character> sortedList = rootToSortedList.get(root);
            sb.append(sortedList.remove());
        }
        return sb.toString();
        }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "cba|[[0,1],[1,2]]|abc",
            "dcab|[[0,3],[1,2],[0,2]]|abcd",
            "dcab|[[0,3],[1,2]]|bacd"
    })
    void test(String s,String swapsStr, String expected ){
        List<List<Integer>> swaps = Arrays.stream(swapsStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s1 -> !s1.isEmpty())
                .map(s1 ->
                        Arrays.stream(s1.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList())).collect(Collectors.toList());
        Assert.assertEquals(expected, smallestStringWithSwaps(s, swaps));
    }

}
