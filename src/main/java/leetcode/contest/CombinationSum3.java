package leetcode.contest;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/*
Find all possible combinations of k numbers that add up to a number n, given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.

Note:

All numbers will be positive integers.
The solution set must not contain duplicate combinations.
Example 1:

Input: k = 3, n = 7
Output: [[1,2,4]]
Example 2:
Input: k = 3, n = 9
Output: [[1,2,6], [1,3,5], [2,3,4]]


 */
class CombinationSum3 {
    public List<List<Integer>> combinationSum3(int k, int n) {
        int permTotalPosition = k, sumLeft = n, start = 9;
        return combinationSumRec(permTotalPosition, sumLeft, start);
    }

    private List<List<Integer>> combinationSumRec(int permTotalPosition, int sumLeft, int start) {
        List<List<Integer>> result = new ArrayList<>();
        if(permTotalPosition == 0 && sumLeft == 0) return new ArrayList<>(){{add(new ArrayList<>());}};
        if(sumLeft < 0||(permTotalPosition == 0)) { return new ArrayList<>();}
        for (int i = start; i > 0; i--) {
            List<List<Integer>> subSets = combinationSumRec(permTotalPosition - 1,sumLeft - i, i-1);
            for(List<Integer> s: subSets){
                s.add(i);
            }
            result.addAll(subSets);
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "2;17;[8,9]",
            "3;7;[1,2,4]",
            "2;3;[1,2]",
            "3;9;[1,2,6], [1,3,5], [2,3,4]",
            "2;6;[1,5],[2,4]",
            "1;1;[1]",
            "3;15;[1,5,9],[1,6,8],[2,4,9],[2,5,8],[2,6,7],[3,4,8],[3,5,7],[4,5,6]"
    })
    void test(int k, int sum, String result) {
        Set<List<Integer>> expected = Arrays.stream(result.split("],")).map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(str -> !str.isEmpty())
                .map(str -> Arrays.stream(str.split(",")).map(Integer::parseInt)
                        .collect(Collectors.toList())).collect(Collectors.toSet());
        Assert.assertEquals(expected, new HashSet<>(combinationSum3(k, sum)));
    }
}
