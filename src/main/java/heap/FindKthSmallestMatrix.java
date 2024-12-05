package heap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

/**
 * Find the kth smallest element in an (n×n)(n×n) matrix, where each row and column of the matrix is sorted in ascending order.
 *
 * Although there can be repeating values in the matrix, each element is considered unique and, therefore, contributes to calculating the kthkth smallest element.
 *
 * Constraints:
 *
 *     n == matrix.length
 *     n == matrix[i].length
 *     1≤1≤ n ≤100≤100
 *     −103≤−103≤ matrix[i][j] ≤103≤103
 *     1≤1≤ k ≤n2≤n2
 */
public class FindKthSmallestMatrix {
    public static int kthSmallestElement(int[][] matrix, int k) {
        if(k > matrix.length * matrix[0].length){
            return -1;
        }
        if(k ==  matrix.length * matrix[0].length){
            return matrix[matrix.length-1][matrix[0].length-1];
        }
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(Comparator.comparingInt(Integer::intValue));
        int[] colIndices = new int[matrix.length];
        int count = 0;
        while (count + heap.size() < k){
            int min = Integer.MAX_VALUE;
            int minRow = 0;
            for (int i = 0; i < matrix.length; i++) {
                if(colIndices[i] < matrix[0].length && matrix[i][colIndices[i]] < min){
                    min = matrix[i][colIndices[i]];
                    minRow =  i;
                }
            }
            colIndices[minRow] = colIndices[minRow] + 1;
            if(heap.size() == matrix.length){
                int value = heap.remove();
                count++;
                if(count == k){
                    return value;
                }

            }
                heap.offer(min);


        }
        int value = -1;
        while (!heap.isEmpty()){
            value = heap.remove();
        }
        // Replace this placeholder return statement with your code
        return value;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1]]|2|-1",
            "[[1]]|1|1",
            "[[2,6,8],[3,6,10],[5,8,11]]|5|6",
            "[[2,4],[3,6]]|3|4",
            "[[1,6,12,18],[2,7,14,19],[4,9,17,21],[5,11,18,24]|16|24",
    })
    void test(String matrixStr, int k, int expected){
        var pattern = Pattern.compile("\\[(\\d+,)*\\d+\\]");
        var matcher = pattern.matcher(matrixStr);
        int[][] matrix = matcher.results().map(m -> matcher.group()).map(str -> Arrays.stream(str.replace("]", "").replace("[", "").split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, kthSmallestElement(matrix, k));
    }
}
