package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 *Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
 *
 *     0 <= a, b, c, d < n
 *     a, b, c, and d are distinct.
 *     nums[a] + nums[b] + nums[c] + nums[d] == target
 *
 * You may return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,0,-1,0,-2,2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * Example 2:
 *
 * Input: nums = [2,2,2,2,2], target = 8
 * Output: [[2,2,2,2]]
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 200
 *     -109 <= nums[i] <= 109
 *     -109 <= target <= 109
 */
class P18FourSumNotDone {
    List<List<Integer>> fourSum(int[] nums, int target) {
        Set<Set<Integer>> tuples = new HashSet<>();
        Map<Integer, List<Pair<Integer,Integer>>> sumToListPair = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j <nums.length ; j++) {
                int a = nums[i];
                int b = nums[j];
                if(sumToListPair.containsKey(target-(a+b))){
                    List<Pair<Integer, Integer>> pairs = sumToListPair.get(target - (a + b));
                    int finalI = i;
                    int finalJ = j;
                    tuples.addAll(pairs.stream()
                            .filter(p -> !(p.getFirst() == finalI || p.getSec() == finalI || p.getFirst() == finalJ || p.getSec() == finalJ))
                                    .map(pair -> {
                                        Set<Integer> quadSet = new HashSet<>();
                                        quadSet.add(pair.getFirst());
                                        quadSet.add(pair.getSec());
                                        quadSet.add(finalI);
                                        quadSet.add(finalJ);
                                        return quadSet;
                                    }).collect(Collectors.toSet()));
                }
                sumToListPair.putIfAbsent((a+b), new ArrayList<>());
                sumToListPair.get(a+b).add(new Pair<>(i,j));
            }
        }
        return tuples.stream().map(quadIndices -> quadIndices.stream().map(i-> nums[i]).collect(Collectors.toList())).collect(Collectors.toList());
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,0,-1,0,-2,2|0|[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]",
            "2,2,2,2,2|8|[[2,2,2,2]]",
    })
    void test(String numsStr, int target, String expectedStr){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        List<List<Integer>> expected = Arrays.stream(expectedStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
        Assertions.assertEquals(expected, fourSum(nums, target));
    }
}
