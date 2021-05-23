package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.IntStream;

/*
Given an array nums of size n, return the majority element.

The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.

Example 1:
Input: nums = [3,2,3]
Output: 3

Example 2:
Input: nums = [2,2,1,1,1,2,2]
Output: 2
Constraints:

    n == nums.length
    1 <= n <= 5 * 104
    -231 <= nums[i] <= 231 - 1

 */
public class P169MajorityElement {
    public int majorityElement(int[] nums){
        ArrayList<Map.Entry<Integer, Integer>> keyFreqHeap = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int key = nums[i];
            OptionalInt keyIndex = IntStream.range(0, keyFreqHeap.size())
                    .filter(index -> keyFreqHeap.get(index).getKey() == key)
                    .findFirst();
            keyIndex.ifPresentOrElse(ind -> {
                Map.Entry<Integer, Integer> keyFeq = keyFreqHeap.get(ind);
                keyFeq.setValue(keyFeq.getValue() + 1);
                reheapify(keyFreqHeap, ind);
            }, () -> {
                keyFreqHeap.add(new AbstractMap.SimpleEntry<>(key, 1));
                reheapify(keyFreqHeap, keyFreqHeap.size()-1);
            });
        }
        return keyFreqHeap.get(0).getKey();
    }

    // i 2i+1 2i+2 (0,1,2) (1,3,4) (2,5,6)
    private void reheapify(ArrayList<Map.Entry<Integer, Integer>> keyFreqHeap, int index1) {
        int index = index1;
        while (index > 0) {
            int parentIndex = index % 2 == 0 ? index / 2 - 1 : index / 2;
            Map.Entry<Integer, Integer> parent = keyFreqHeap.get(parentIndex);
            Map.Entry<Integer, Integer> child = keyFreqHeap.get(index);
            if (parent.getValue() >= child.getValue()) {
                return;
            } else {
                keyFreqHeap.remove(parent);
                keyFreqHeap.remove(child);
                keyFreqHeap.add(parentIndex, child);
                keyFreqHeap.add(index, parent);
            }
            index = parentIndex;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "2,1,1;1",
            "2,2,1,1,1,2,2;2",
            "3,2,3;3"})
    void test(String numsStr, int expected){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, majorityElement(nums));
    }
}
