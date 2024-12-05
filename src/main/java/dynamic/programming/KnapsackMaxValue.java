package dynamic.programming;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * You are given nn items whose weights and values are known, as well as a knapsack to carry these items. The knapsack cannot carry more than a certain maximum weight, known as its capacity.
 *
 * You need to maximize the total value of the items in your knapsack, while ensuring that the sum of the weights of the selected items does not exceed the capacity of the knapsack.
 *
 * If there is no combination of weights whose sum is within the capacity constraint, return 00.
 *
 *     Notes:
 *
 *         An item may not be broken up to fit into the knapsack, i.e., an item either goes into the knapsack in its entirety or not at all.
 *         We may not add an item more than once to the knapsack.
 *
 * Constraints:
 *
 *     1≤1≤ capacity ≤1000≤1000
 *     1≤1≤ values.length ≤500≤500
 *     weights.length ==== values.length
 *     1≤1≤ values[i] ≤1000≤1000
 *     1≤1≤ weights[i] ≤≤ capacity
 */

/**
 * solution recursive formula
 *  at index i, if choose wi then vi + S(W-wi, V - vi, C-vi) or if I dont choose wi then S(W-wi,V-vi, C)
 *  s(W, V C) = Max(S(W-wi, V-vi, C), S(W-wi, V-vi, C-vi) + vi)
 *
 */
public class KnapsackMaxValue {
    public static int findMaxKnapsackProfit(int capacity, int [] weights, int [] values) {
        int[] weightsToCapacity = new int[capacity +1];
        for (int i = 0; i < capacity + 1; i++) {
            if(i - weights[0] >= 0 ){
                weightsToCapacity[i] = values[0];
            }
        }
        for (int i = 1; i < weights.length; i++) {
            int[] weightsToCapacityNew = new int[capacity +1];
            for (int j = 1; j < capacity +1 ; j++) {
                int includingWi = j-weights[i] < 0 ? Integer.MIN_VALUE : weightsToCapacity[j - weights[i]] + values[i];
                int excludingWi = weightsToCapacity[j];
                weightsToCapacityNew[j] = Math.max(excludingWi, includingWi);
            }
            weightsToCapacity = weightsToCapacityNew;
        }
        return weightsToCapacity[capacity];
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|3|10|3",
            "10,20,30|22,33,44|30|55",
            "1,2,3,5|10,5,4,8|5|15",
            "24,10,10,7|24,16,16,11|26|32"
    })
    void test(String weighStr, String valuesStr, int capacity, int expected){
        int[] weights = Arrays.stream(weighStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] values = Arrays.stream(valuesStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, findMaxKnapsackProfit(capacity, weights, values));
    }
}
