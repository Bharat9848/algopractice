package leetcode;
/*
Given an array of strings, group anagrams together.

Example:

Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
Output:
[
  ["ate","eat","tea"],
  ["nat","tan"],
  ["bat"]
]

Note:

    All inputs will be in lowercase.
    The order of your output does not matter.


 */

import org.junit.jupiter.api.Test;
import util.Pair;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class P49GroupAnagram {


    List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<Integer>> sortedStringToIndex = IntStream.range(0, strs.length).mapToObj(i -> new Pair<String, Integer>(sortString(strs[i]), i)).collect(
                Collectors.toMap((Pair<String, Integer> pair) -> pair.getFirst(),
                        (Pair<String, Integer> pair2) -> {
                            List<Integer> a = new ArrayList<Integer>(){{add(pair2.getSec());}};
                            return a;},
                        (List<Integer> index1, List<Integer> index2) -> {
                            index1.addAll(index2);
                            return index1;
                        }));
        return sortedStringToIndex.values()
                .stream()
                .map(ls -> ls.stream().map(index -> strs[index]).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private String sortString(String str) {
        char[] charArr = str.toCharArray();
        Arrays.sort(charArr);
        return String.copyValueOf(charArr);
    }



    @Test
    public void testSimple(){
        System.out.println(groupAnagrams(new String[]{"eat","tea", "cat"}));
    }
}
