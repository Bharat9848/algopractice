package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 *
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 *     1 <= nums.length <= 105
 *     k is in the range [1, the number of unique elements in the array].
 *     It is guaranteed that the answer is unique.
 *
 *
 *
 * Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class P347TopKFreqElements {
    int[] topKFrequent(int[] nums, int k) {
        if(nums == null || nums.length == 0|| k<=0){
            return new int[0];
        }
        Map<Integer, Integer> numToFreq = Arrays.stream(nums).boxed().collect(Collectors.toMap(ele -> ele, ele -> 1, (v1, v2) -> v1 + v2));
        PriorityQueue<Pair<Integer, Integer>> minFreqHeap = new PriorityQueue<>((p1, p2) -> p1.getSec() - p2.getSec());
        for (Map.Entry<Integer, Integer> numToFreqEle: numToFreq.entrySet()){
            int no = numToFreqEle.getKey();
            int freq = numToFreqEle.getValue();
            if(minFreqHeap.size() < k){
                minFreqHeap.offer(new Pair<>(no, freq));
            }else{
                Pair<Integer, Integer> topEle = minFreqHeap.peek();
                if(topEle.getSec() < freq){
                    minFreqHeap.remove();
                    minFreqHeap.offer(new Pair<>(no, freq));
                }
            }
        }
        return minFreqHeap.stream().mapToInt(pair -> pair.getFirst()).sorted().toArray();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,1,1,2,2,3|2|1,2|normal case",
                    "1|1|1| single element",
                    "1|0|| single element",
                    "1,1,1,1,1|3|1| single element",
    })
    void test(String numStr, int k, String expectedStr, String msg ){
        System.out.println(msg);
        int[] expected = expectedStr == null ? new int[0] :Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] nums = Arrays.stream(numStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(expected, topKFrequent(nums, k));
    }
}
