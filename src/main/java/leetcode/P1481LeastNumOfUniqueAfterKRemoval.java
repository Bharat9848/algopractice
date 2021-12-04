package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given an array of integers arr and an integer k. Find the least number of unique integers after removing exactly k elements.
 *
 *
 *
 * Example 1:
 *
 * Input: arr = [5,5,4], k = 1
 * Output: 1
 * Explanation: Remove the single 4, only 5 is left.
 *
 * Example 2:
 *
 * Input: arr = [4,3,1,1,3,3,2], k = 3
 * Output: 2
 * Explanation: Remove 4, 2 and either one of the two 1s or three 3s. 1 and 3 will be left.
 *
 *
 *
 * Constraints:
 *
 *     1 <= arr.length <= 10^5
 *     1 <= arr[i] <= 10^9
 *     0 <= k <= arr.length
 */
class P1481LeastNumOfUniqueAfterKRemoval {
    int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> elementToFreq = Arrays.stream(arr).boxed().collect(Collectors.toMap(i-> i, i -> 1, Integer::sum));
        PriorityQueue<Pair<Integer,Integer>> minFreqHeap = new PriorityQueue<>(elementToFreq.size(), Comparator.comparingInt(Pair::getSec));
        elementToFreq.entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue())).forEach(minFreqHeap::offer);
        for(int left = k; left>0;){
            Pair<Integer, Integer> topEle = minFreqHeap.peek();
            if(topEle.getSec() <= left){
                left -= topEle.getSec();
                minFreqHeap.remove();
            }else {
                break;
            }
        }

        return minFreqHeap.size();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4,3,1,1,3,3,2|3|2",
            "5,5,4|1|1",
            "5,5,4,4|1|2",
            "5,5,4,4|2|1",
            "1|1|0",
            "1,2,3|1|2",
            "2,4,1,8,3,5,1,3|3|3"
    })
    void test(String arrStr, int k, int expected){
        int[] arr = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, findLeastNumOfUniqueInts(arr, k));
    }
}
