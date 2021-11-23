package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * Design a data structure that will be initialized with a string array, and then it should answer queries of the shortest distance between two different strings from the array.
 * <p>
 * Implement the WordDistance class:
 * <p>
 * WordDistance(String[] wordsDict) initializes the object with the strings array wordsDict.
 * int shortest(String word1, String word2) returns the shortest distance between word1 and word2 in the array wordsDict.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input
 * ["WordDistance", "shortest", "shortest"]
 * [[["practice", "makes", "perfect", "coding", "makes"]], ["coding", "practice"], ["makes", "coding"]]
 * Output
 * [null, 3, 1]
 * <p>
 * Explanation
 * WordDistance wordDistance = new WordDistance(["practice", "makes", "perfect", "coding", "makes"]);
 * wordDistance.shortest("coding", "practice"); // return 3
 * wordDistance.shortest("makes", "coding");    // return 1
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= wordsDict.length <= 3 * 104
 * 1 <= wordsDict[i].length <= 10
 * wordsDict[i] consists of lowercase English letters.
 * word1 and word2 are in wordsDict.
 * word1 != word2
 * At most 5000 calls will be made to shortest.
 */
public class P244ShortestWordDistanceII {
    Map<String, ArrayList<Integer>> wordToIndices = new HashMap<>();

    public void WordDistance(String[] wordsDict) {
        for (int i = 0; i < wordsDict.length; i++) {
            wordToIndices.putIfAbsent(wordsDict[i], new ArrayList<>());
            wordToIndices.get(wordsDict[i]).add(i);
        }
    }


    public int shortest(String word1, String word2) {
        List<Integer> indices1 = wordToIndices.get(word1);
        List<Integer> indices2 = wordToIndices.get(word2);
        int difference = Integer.MAX_VALUE;
        for (int i = 0; i < indices1.size(); i++) {
            int indices2Index = closestIndexBinarySearch(indices2, indices1.get(i));
            if (difference > Math.abs(indices2.get(indices2Index) - indices1.get(i))) {
                difference = Math.abs(indices2.get(indices2Index) - indices1.get(i));
            }
        }
        return difference;
    }

    private int closestIndexBinarySearch(List<Integer> indices2, Integer target) {
        int beg = 0, end = indices2.size() - 1;
        if (target < indices2.get(beg)) {
            return beg;
        }
        if (target > indices2.get(end)) {
            return end;
        }
        while (beg < end) {
            int mid = beg + ((end - beg) / 2);
            int midEle = indices2.get(mid);
            if (midEle < target) {
                beg = mid + 1;
            } else {
                end = mid;
            }
        }
        int beforeEle = beg > 0 ? indices2.get(beg - 1) : Integer.MAX_VALUE;
        int atEle = indices2.get(beg);
        int afterEle = beg < indices2.size() - 1 ? indices2.get(beg + 1) : Integer.MAX_VALUE;
        int min;
        if (Math.abs(beforeEle - target) < Math.abs(atEle - target)) {
            if (Math.abs(beforeEle - target) < Math.abs(afterEle - target)) {
                min = beg - 1;
            } else {
                if (Math.abs(afterEle - target) < Math.abs(atEle - target)) {
                    min = beg + 1;
                } else {
                    min = beg;
                }
            }
        } else {
            if (Math.abs(afterEle - target) < Math.abs(atEle - target)) {
                min = beg + 1;
            } else {
                min = beg;
            }
        }

        return min;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "WordDistance,shortest,shortest|practice,makes,perfect,coding,makes;coding,practice;makes,coding|null,3,1",
            "WordDistance,shortest|practice,makes,perfect,coding,makes,practice;coding,practice|null,2",
            "WordDistance,shortest|practice,makes,perfect,coding,makes,practice,coding;coding,practice|null,1"
    })
    void test(String operationStr, String argStr, String expectedStr) {
        String[] operations = operationStr.split(",");
        String[] arguments = argStr.split(";");
        String[] expected = expectedStr.split(",");
        P244ShortestWordDistanceII distance = new P244ShortestWordDistanceII();
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            switch (operation) {
                case "WordDistance":
                    distance.WordDistance(arguments[i].split(","));
                    break;
                case "shortest":
                    Assert.assertEquals(Integer.parseInt(expected[i]), distance.shortest(arguments[i].split(",")[0], arguments[i].split(",")[1]));
                    break;
            }
        }
    }
}
