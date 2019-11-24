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
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class P49GroupAnagram {

    class Pair<K,V>{
        final K first;
        final V sec;

        public Pair(K first, V sec) {
            this.first = first;
            this.sec = sec;
        }

        public K getFirst() {
            return first;
        }

        public V getSec() {
            return sec;
        }

        public String toString(){
            return "("+first+ ", " + sec +")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair<?, ?> pair = (Pair<?, ?>) o;

            if (!first.equals(pair.getFirst())) return false;
            return sec.equals(pair.getSec());
        }

        @Override
        public int hashCode() {
            int result = first.hashCode();
            result = 31 * result + sec.hashCode();
            return result;
        }
    }

    public List<List<String>> groupAnagrams(String[] strs) {
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
