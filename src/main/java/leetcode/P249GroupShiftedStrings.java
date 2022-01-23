package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * We can shift a string by shifting each of its letters to its successive letter.
 *
 *     For example, "abc" can be shifted to be "bcd".
 *
 * We can keep shifting the string to form a sequence.
 *
 *     For example, we can keep shifting "abc" to form the sequence: "abc" -> "bcd" -> ... -> "xyz".
 *
 * Given an array of strings strings, group all strings[i] that belong to the same shifting sequence. You may return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: strings = ["abc","bcd","acef","xyz","az","ba","a","z"]
 * Output: [["acef"],["a","z"],["abc","bcd","xyz"],["az","ba"]]
 *
 * Example 2:
 *
 * Input: strings = ["a"]
 * Output: [["a"]]
 *
 *
 *
 * Constraints:
 *
 *     1 <= strings.length <= 200
 *     1 <= strings[i].length <= 50
 *     strings[i] consists of lowercase English letters.
 *
 *
 *     Solution:
 *     1. have a map of string length to List of groups
 *     2. for each string
 *       2.1 check the map if we have a List of group if not then create a new group add to the list
 *       2.2 if groups are there the compare curr string belongs to any group
 *          2.2.1 if yes then add it in the group
 *          2.2.2 if no then create another group and add
 */
class P249GroupShiftedStrings {

    List<List<String>> groupStrings(String[] strings) {
        Map<Integer, List<Pair<String, List<String>>>> lenToGroupLeaderToPeers = new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            String curr = strings[i];
            List<Pair<String, List<String>>> groups = lenToGroupLeaderToPeers.get(curr.length());
            if(groups == null){
                lenToGroupLeaderToPeers.putIfAbsent(curr.length(), new ArrayList<>());
                ArrayList<String> groupList = new ArrayList<>();
                groupList.add(curr);
                lenToGroupLeaderToPeers.get(curr.length()).add(new Pair<>(curr, groupList));
            }else {
                boolean belongToAnyGroup = false;
                for(Pair<String, List<String>> group : groups){
                    String leader = group.getFirst();
                    if(belongToGroup(leader, curr)){
                        belongToAnyGroup = true;
                        group.getSec().add(curr);
                        break;
                    }
                }
                if(!belongToAnyGroup){
                    ArrayList<String> groupList = new ArrayList<>();
                    groupList.add(curr);
                    lenToGroupLeaderToPeers.get(curr.length()).add(new Pair<>(curr, groupList));
                }
            }
        }
        return lenToGroupLeaderToPeers.values().stream().flatMap(Collection::stream).map(Pair::getSec).collect(Collectors.toList());
    }

    private boolean belongToGroup(String leader, String curr) {
        int dist = distance(leader.charAt(0), curr.charAt(0));
        boolean belongToGroup = true;
        for (int i = 1; i < leader.length(); i++) {
            if(dist != distance(leader.charAt(i), curr.charAt(i))){
                belongToGroup = false;
                break;
            }
        }
        return belongToGroup;
    }

    private int distance(char src, char dest) {
        int sDist = src - 'a' < 0 ? (src - 'a') + 26: src - 'a';
        int dDist = dest - 'a' < 0 ? (dest - 'a') + 26: dest - 'a';
        int dist = sDist - dDist;
        return dist < 0? dist + 26 : dist;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "a|[[a]]",
            "a,a,b,z,c,ab,ba,cb,za|[[a,a,b,z,c],[ab,za],[ba,cb]]",
    })
    void test(String stringsStr, String expectedStr){
        String[] strings = stringsStr.split(",");
        List<List<String>> expected = Arrays.stream(expectedStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).collect(Collectors.toList())).collect(Collectors.toList());
        Assert.assertEquals(expected, groupStrings(strings));
    }
}
