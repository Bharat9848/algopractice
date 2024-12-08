package heap.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

/**
 *  You are given an m×nm×n binary matrix of 1’s (representing soldiers) and 00’s (representing civilians). The soldiers are positioned in front of the civilians, i.e., all the 1’s will appear to the left of all the 0’s in each row.
 *
 * A row ii is weaker than a row jj if any of the following is TRUE:
 *
 *     The number of soldiers in row i is less than the number of soldiers in row j.
 *
 *     Both rows have the same number of soldiers and i<j.
 *
 * You have to return the indexes of the kk weakest rows in the matrix ordered from weakest to strongest.
 *
 * Constraints:
 *
 *     m=matrix.length
 *
 *     n= matrix[i].length
 *
 *     2≤n,m≤100
 *
 *     1≤k≤m1≤k≤m
 *
 *     matrix[i][j] is either 0 or 1.
 */
public class FindKWeakRows {
    public static int[] findKWeakestRows(int[][] matrix, int k) {
        int[] result = new int[k];
        PriorityQueue<Node> maxHeap = new PriorityQueue<>(FindKWeakRows::nodeComparator);
        for (int i = 0; i < matrix.length; i++) {
            int count = 0;
            for (int j = 0; j < matrix[0].length && matrix[i][j]==1; j++) {
                count++;
            }
            if(maxHeap.size() == k){
                Node top = maxHeap.peek();
                if(top.soldierCount > count){
                    maxHeap.remove();
                    maxHeap.add(new Node(count, i));
                }
            } else {
                maxHeap.add(new Node(count, i));
            }

        }
        for (int i = 0; i < k; i++) {
            Node node =  maxHeap.remove();
            result[k-i-1] = node.index;
        }
        return result;
    }

    private static int nodeComparator(Node node1, Node node2){
        if(!Objects.equals(node1.soldierCount, node2.soldierCount)){
            return -(node1.soldierCount - node2.soldierCount);
        } else {
            return -(node1.index - node2.index);
        }
    }

    private static class Node {

        public final Integer soldierCount;
        public final int index;

        public Node(Integer soldierCount, int index){
            this.soldierCount = soldierCount;
            this.index = index;
        }


    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,1,0,0,0],[1,1,1,1,0],[1,0,0,0,0],[1,1,0,0,0],[1,1,1,1,1]]|3|2,0,3",
            "[[1,1,1,0],[1,0,0,0],[1,1,1,1],[1,1,0,0]]|2|1,3",
    })
    void test(String matrixStr, int k, String expectedStr){
        var pattern = Pattern.compile("\\[(\\d,)+\\d\\]");
        var matcher = pattern.matcher(matrixStr);
        int[][] matrix = matcher.results().map(m -> matcher.group()).map(str -> Arrays.stream(str.replace("]", "").replace("[", "").split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        var result = findKWeakestRows(matrix, k);
        Assertions.assertArrayEquals(Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray(), result);
    }
}
