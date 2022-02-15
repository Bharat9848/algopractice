package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Given an integer array nums of unique elements, return all possible subsets (the power set).
 *
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3]
 * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * Example 2:
 *
 * Input: nums = [0]
 * Output: [[],[0]]
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 10
 *     -10 <= nums[i] <= 10
 *     All the numbers of nums are unique.
 *
 *     1,2 => 1 sol(2)  [][2] [1] [1,2]
 *     1,2,3 => 1 sol(2,3) => 1, 2 sol(3) => 1, 2, [] [3] => 1 [][2][3][2,3] => [][1][2][1,2][3][1,3][2,3][1,2,3]
 */
public class P78SubSets {
    public List<List<Integer>> subsets(int[] nums) {
        if(nums == null){
            return new ArrayList<>();
        }
        return subSetRec(nums, 0);
    }

    private List<List<Integer>> subSetRec(int[] nums, int index) {
        if(index == nums.length) {
            List<List<Integer>> emptyResult = new ArrayList<>();
            emptyResult.add(new ArrayList<>());
            return emptyResult;
        }
        List<List<Integer>> subResult = subSetRec( nums, index+1);
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> subSet: subResult) {
            result.add(subSet);
            List<Integer> copiedSubSet = new ArrayList<>(subSet);
            copiedSubSet.add(nums[index]);
            result.add(copiedSubSet);
        }
        return result;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3|[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]",
            "0|[[],[0]]",
            "|[[]]"
    })
    void test(String numsStr, String expectedStr){
        int[] nums = numsStr == null ? new int[0] :Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Set<Set<Integer>> expected = Arrays.stream(expectedStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .map(s -> s.isEmpty()?  new HashSet<Integer>(): Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toSet())
                ).collect(Collectors.toSet());
        Assertions.assertEquals(expected, subsets(nums).stream().map(HashSet::new).collect(Collectors.toSet()));
    }
}
