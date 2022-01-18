package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * You are given an integer array nums. You want to maximize the number of points you get by performing the following operation any number of times:
 *
 *     Pick any nums[i] and delete it to earn nums[i] points. Afterwards, you must delete every element equal to nums[i] - 1 and every element equal to nums[i] + 1.
 *
 * Return the maximum number of points you can earn by applying the above operation some number of times.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [3,4,2]
 * Output: 6
 * Explanation: You can perform the following operations:
 * - Delete 4 to earn 4 points. Consequently, 3 is also deleted. nums = [2].
 * - Delete 2 to earn 2 points. nums = [].
 * You earn a total of 6 points.
 *
 * Example 2:
 *
 * Input: nums = [2,2,3,3,3,4]
 * Output: 9
 * Explanation: You can perform the following operations:
 * - Delete a 3 to earn 3 points. All 2's and 4's are also deleted. nums = [3,3].
 * - Delete a 3 again to earn 3 points. nums = [3].
 * - Delete a 3 once more to earn 3 points. nums = [].
 * You earn a total of 9 points.
 *
 *
 *sol max(Sum of List where no two number are are consecutive) max(List(2,2,3,3,4)) = max(alist(2,2,4), asList(3,3,3), alist(2,2,4))
 * max(a(1,1,2,2,3,3,4,4,5,5)) = max(rl(1,1,3,3,5,5)
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 2 * 10^4
 *     1 <= nums[i] <= 10^4
 */
class P740DeleteAndEarn {

    int deleteAndEarn(int[] nums){
        Map<Integer, Integer> freqMap = Arrays.stream(nums, 0, nums.length).boxed().collect(Collectors.toMap(a->a, a->1, Integer::sum));
        List<Integer> uniqueList = new ArrayList<>(freqMap.keySet());
        Collections.sort(uniqueList);
        Map<Integer, Integer> indextoSol = new HashMap<>();
        return deleteAndEarnFromLast(uniqueList, uniqueList.size()-1, freqMap, indextoSol);
    }

    private int deleteAndEarnFromLast(List<Integer> nums, int to, Map<Integer, Integer> freqMap, Map<Integer, Integer> indextoSol) {
        if(to == 0){
            int sol = nums.get(0) * freqMap.get(nums.get(0));
            indextoSol.put(0, sol);
            return sol;
        }
        if(to<0){
            return 0;
        }
        int including = nums.get(to) * freqMap.get(nums.get(to));
        if(nums.get(to-1)+1 == nums.get(to)){
            including += indextoSol.containsKey(to-2)? indextoSol.get(to-2): deleteAndEarnFromLast(nums, to-2, freqMap, indextoSol);
        }else{
            including += indextoSol.containsKey(to-1)? indextoSol.get(to-1):deleteAndEarnFromLast(nums, to-1, freqMap, indextoSol);
        }
        int excluding = indextoSol.containsKey(to-1)? indextoSol.get(to-1):deleteAndEarnFromLast(nums, to-1, freqMap, indextoSol);

        int sol = Math.max(including, excluding);
        indextoSol.put(to, sol);
        return sol;
    }


   /* int deleteAndEarnTLE(int[] nums){
        Arrays.sort(nums);
        Map<Pair<Integer, Integer>, Integer> memo = new HashMap<>();
        return deleteAndEarnRec(new Pair<>(0, nums.length-1), toList(nums), memo);
    }

    int deleteAndEarnRec(Pair<Integer, Integer> startAndEndIndex, List<Integer> numList, Map<Pair<Integer, Integer>, Integer> memo) {
        Integer from = startAndEndIndex.getFirst();
        Integer to = startAndEndIndex.getSec();
        if (from.equals(to)) {
            memo.put(startAndEndIndex, numList.get(to));
            return numList.get(to);
        }
        for (int i = from; i < to; i++) {
            Pair<Integer, Integer> remainingOnLeft = remainingLeftAfterPick(numList, i, from, to);
            Pair<Integer, Integer> remainingOnRight = remainingRightAfterPick(numList, i, from, to);

            int leftMax;
            if (remainingOnLeft.getFirst() == -1 && remainingOnLeft.getSec() == -1) {
                leftMax = 0;
            } else {
                leftMax = memo.containsKey(remainingOnLeft) ?
                        memo.get(remainingOnLeft) : deleteAndEarnRec(remainingOnLeft, numList, memo);
            }
            int rightMax;
            if (remainingOnRight.getFirst() == -1 && remainingOnRight.getSec() == -1) {
                rightMax = 0;
            } else {
                rightMax = memo.containsKey(remainingOnRight) ? memo.get(remainingOnRight) : deleteAndEarnRec(remainingOnRight, numList, memo);
            }
            int earn = leftMax + rightMax + numList.get(i);
            for (int j = i - 1; j >= from; j--) {
                if (numList.get(j).equals(numList.get(i))) {
                    earn += numList.get(i);
                    break;
                }
            }
            for (int j = i + 1; j <= to; j++) {
                if (numList.get(j).equals(numList.get(i))) {
                    earn += numList.get(i);
                    break;
                }
            }
            memo.put(startAndEndIndex, Math.max(memo.getOrDefault(startAndEndIndex, 0), earn));
        }
//        System.out.println(memo);
        return memo.get(startAndEndIndex);
    }

    private Pair<Integer, Integer> remainingRightAfterPick(List<Integer> numList, int i, int from, int to) {
            int end = to;
            int start = -1;
            for (int j = i; j <= to; j++) {
                Integer e = numList.get(j);
                if(e != numList.get(i) + 1 && !e.equals(numList.get(i))){
                    start = j;
                    break;
                }
            }
            if(start == -1){
                return new Pair<>(-1,-1);
            }
            return new Pair<>(start, end);
    }

    private Pair<Integer, Integer> remainingLeftAfterPick(List<Integer> numList, int i, int from, int to) {
        int start = from;
        int end = -1;
        for (int j = i; j >= from; j--) {
            Integer e = numList.get(j);
            if (e != numList.get(i) - 1 && !e.equals(numList.get(i))) {
                end = j;
                break;
            }
        }
        if(end == -1){
            return new Pair<>(-1, -1);
        }else{
            return new Pair<>(start, end);
        }
    }

    private List<Integer> toList(int[] nums) {
        return Arrays.stream(nums).boxed().collect(Collectors.toList());
    }
*/

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"2,2|4",
            "2,2,3,3,3,4|9",
            "3,4,2|6",
            "3,5,7|15",
            "8,10,4,9,1,3,5,9,4,10|37",
            "2|2"})
    void test(String arrStr, int expected){
        int[] nums = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, deleteAndEarn(nums));
    }
}
