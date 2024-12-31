package heap.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Given an mm number of sorted lists in ascending order and an integer, k, find the kthkth smallest number among all the given lists.
 *
 * Although there can be repeating values in the lists, each element is considered unique and, therefore, contributes to calculating the kthkth smallest element.
 *
 * If k is greater than the total number of elements in the input lists, return the greatest element from all the lists, and if there are no elements in the input lists, return 0.
 *
 * Constraints:
 *
 *     1≤1≤ m ≤50≤50
 *     0≤0≤ list[i].length ≤50≤50
 *     −109≤−109≤ list[i][j] ≤109≤109
 *     1≤1≤ k ≤109≤109
 */
public class KthSmallestElement {
    public static int kSmallestNumber(List<List<Integer>> lists, int k) {
        if(lists.isEmpty()){
            return 0;
        }
        PriorityQueue<HeapNode> minHeap =  new PriorityQueue<>(lists.size(), Comparator.comparingInt(node -> node.val));
        for (int i = 0; i < lists.size(); i++) {
            if(!lists.get(i).isEmpty()){
                minHeap.offer(new HeapNode(lists.get(i).get(0), i, 0));
            }
        }
        int result = 0;
        for (int i = 0; i < k && !minHeap.isEmpty(); i++) {
            var node = minHeap.poll();
            if(node.eleIndex + 1 < lists.get(node.index).size()){
                minHeap.offer(new HeapNode(lists.get(node.index).get(node.eleIndex + 1), node.index, node.eleIndex + 1));
            }
            result = node.val;
        }
        return result;
    }

    private static class HeapNode {
        int val;
        int index;
        int eleIndex;
        public HeapNode(int val, int index, int eleIndex){
            this.val = val;
            this.index = index;
            this.eleIndex = eleIndex;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[10,20,30]]|2|20",
            "[[10,20,30]]|4|30",
            "[10,20,30],[1,2,3000]|4|20",
    })
    void test(String listsStr, int k, int expected){
        List<List<Integer>> lists = StringParser.parseStringAsListOfList(listsStr,"\\[(\\d+,)*\\d+\\]");
        Assertions.assertEquals(expected, kSmallestNumber(lists, k));
    }
}
