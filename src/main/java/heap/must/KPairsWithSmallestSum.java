package heap.must;

/**
 * Given two lists, and an integer kk, find kk pairs of numbers with the smallest sum so that in each pair, each list contributes one number to the pair.
 *
 * Constraints:
 *
 *     11 ≤≤ list1.length, list2.length ≤≤ 500500
 *
 *     −104−104 ≤≤ list1[i], list2[i] ≤≤ 104104
 *
 *     11 ≤≤ kk ≤≤ 103103
 *
 *     Input lists should be sorted in ascending order.
 *
 *     If the value of kk exceeds the total number of valid pairs that may be formed, return all the pairs.
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.*;

/**
 * @Todo not done
 *
 * */
public class KPairsWithSmallestSum {
    public static List<List<Integer>> kSmallestPairs(int[] list1, int[] list2, int target) {
        if(list2.length == 0 || target == 0 || list1.length == 0){
            return new ArrayList<>();
        }
        PriorityQueue<PairNode> minHeap =  new PriorityQueue<PairNode>(target, Comparator.comparingInt(node -> node.sum));
        for (int i = 0; i < list1.length && i < target; i++) {
            minHeap.add(new PairNode(i, 0, list1[i] + list2[0]));
        }
        List<List<Integer>>  result = new ArrayList<>();
        while(target > 0 && !minHeap.isEmpty()){
            var heapNode = minHeap.poll();
            int l2i = heapNode.l2Index;
            int l1i = heapNode.l1Index;
            result.add(Arrays.asList(list1[l1i], list2[l2i]));
            if(l2i + 1 < list2.length){
                minHeap.offer(new PairNode(l1i, l2i + 1, list2[l2i+1]+ list1[l1i]));
            }
            target--;
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,5,9,14,24,27,35|1, 6, 8, 17,30|15|[[[1, 1], [5, 1], [1, 6], [1, 8], [9, 1], [5, 6], [5, 8], [9, 6], [14, 1], [9, 8], [1, 17], [14, 6], [5, 17], [14, 8], [24, 1]]",
            "1,1,2|1,2,3|4|[1,1],[1,1],[1,2],[1,2]",
            "1,1,2|1,2,3|10|[[1, 1], [1, 1], [1, 2], [1, 2], [2, 1], [1, 3], [2, 2], [1, 3], [2, 3]]",
            "1, 11, 20, 35, 300|1, 2, 300|4|[[1,1],[1,2],[11,1],[11,2]]",
            "1, 11, 20, 35, 300|1, 2, 300|5|[[1,1],[1,2],[11,1],[11,2],[20,1]]"
    })
    void test(String list1Str, String list2Str, int target, String expectedListStr){
        int[] list1 = Arrays.stream(list1Str.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        int[] list2 = Arrays.stream(list2Str.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        List<List<Integer>> expectedList = StringParser.parseStringAsListOfList(expectedListStr,"\\[\\d+,( )?\\d+\\]");
        Assertions.assertEquals(expectedList, kSmallestPairs(list1, list2, target));
    }

    private static class PairNode {
        public int l1Index;
        public int l2Index;
        public int sum;

        public PairNode(int i, int j, int sum) {
            this.l1Index = i;
            this.l2Index = j;
            this.sum = sum;
        }
    }
}
