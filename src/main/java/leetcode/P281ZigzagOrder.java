package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given two vectors of integers v1 and v2, implement an iterator to return their elements alternately.
 *
 * Implement the ZigzagIterator class:
 *
 *     ZigzagIterator(List<int> v1, List<int> v2) initializes the object with the two vectors v1 and v2.
 *     boolean hasNext() returns true if the iterator still has elements, and false otherwise.
 *     int next() returns the current element of the iterator and moves the iterator to the next element.
 *
 * Input: v1 = [1,2], v2 = [3,4,5,6]
 * Output: [1,3,2,4,5,6]
 * Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,3,2,4,5,6].
 *
 * Example 2:
 *
 * Input: v1 = [1], v2 = []
 * Output: [1]
 *
 * Example 3:
 *
 * Input: v1 = [], v2 = [1]
 * Output: [1]
 *
 *
 *
 * Constraints:
 *
 *     0 <= v1.length, v2.length <= 1000
 *     1 <= v1.length + v2.length <= 2000
 *     -231 <= v1[i], v2[i] <= 231 - 1
 *
 *
 *
 * Follow up: What if you are given k vectors? How well can your code be extended to such cases?
 *
 * Clarification for the follow-up question:
 *
 * The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases. If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic".
 *
 * Follow-up Example:
 *
 * Input: v1 = [1,2,3], v2 = [4,5,6,7], v3 = [8,9]
 * Output: [1,4,8,2,5,9,3,6,7]
 */
public class P281ZigzagOrder {
    Queue<Pair<Integer, List<Integer>>> currentIndexAndList;
    public P281ZigzagOrder(){

    }
    public void zigzagIterator(List<Integer> v1, List<Integer> v2) {
        this.currentIndexAndList = new LinkedList<>();
        if(!v1.isEmpty()){
            currentIndexAndList.add(new Pair<>(0, v1));
        }
        if(!v2.isEmpty()){
            currentIndexAndList.add(new Pair<>(0, v2));
        }
    }

    public int next() {
        Pair<Integer, List<Integer>> currentIndexList = currentIndexAndList.remove();
        Integer index = currentIndexList.getFirst();
        List<Integer> list = currentIndexList.getSec();
        if(index+1 < list.size()){
            currentIndexAndList.add(new Pair<>(index+1, list));
        }
        return list.get(index);
    }

    public boolean hasNext() {
        return !currentIndexAndList.isEmpty();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2|3,4,5,6|1,3,2,4,5,6",
            "1||1",
            "|1|1",
    })
    void test(String listStr1, String listStr2, String expectedStr){
        List<Integer> v1 = listStr1== null ? Collections.emptyList(): Arrays.stream(listStr1.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> v2 = listStr2== null ? Collections.emptyList(): Arrays.stream(listStr2.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> expected = Arrays.stream(expectedStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        P281ZigzagOrder order = new P281ZigzagOrder();
        List<Integer> actual = new ArrayList<>();
        order.zigzagIterator(v1, v2);
        while(order.hasNext()){
            actual.add(order.next());
        }
        Assertions.assertEquals(actual, expected);
    }
}
