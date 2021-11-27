package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * You are given an integer length and an array updates where updates[i] = [startIdxi, endIdxi, inci].
 *
 * You have an array arr of length length with all zeros, and you have some operation to apply on arr. In the ith operation, you should increment all the elements arr[startIdxi], arr[startIdxi + 1], ..., arr[endIdxi] by inci.
 *
 * Return arr after applying all the updates.
 *
 *
 *
 * Example 1:
 *
 * ength = 10, updates = [[2,4,6],[5,6,8],[1,9,-4]]
 *  * Output: [0,-4,2,2,2,4,4,-4,-4,-4]
 *
 * Example 2:
 *
 * Input: length = 10, updates = [[2,4,6],[5,6,8],[1,9,-4]]
 * Output: [0,-4,2,2,2,4,4,-4,-4,-4]
 *solution: hint commulative sum
 * Approach 2: Range Caching
 *
 * Intuition
 *
 *     There is only one read query on the entire range, and it occurs at the end of all update queries. Additionally, the order of processing update queries is irrelevant.
 *
 *     Cumulative sums or partial_sum operations apply the effects of past elements to the future elements in the sequence.
 *
 * TODO Algorithm
 *
 * The algorithm makes use of the above intuition to simply store changes at the borders of the update ranges (instead of processing the entire range). Finally a single post processing operation is carried out over the entire output array.
 *
 * The two steps that are required are as follows:
 *
 *     For each update query (start,end,val)(start, end, val)(start,end,val) on the array arrarrarr, we need to do only two operations:
 *         Update startstartstart boundary of the range:
 *
 *     arrstart=arrstart+val arr_{start} = arr_{start} + val arrstart​=arrstart​+val
 *         Update just beyond the endendend boundary of the range:
 *
 *     arrend+1=arrend+1−val arr_{end+1} = arr_{end+1} - val arrend+1​=arrend+1​−val
 *
 *     Final Transformation. The cumulative sum of the entire array is taken (0 - based indexing)
 *
 *     arri=arri+arri−1∀i∈[1,n) arr_i = arr_i + arr_{i-1} \quad \forall \quad i \in [1, n) arri​=arri​+arri−1​∀i∈[1,n)
 *
 * Constraints:
 *
 *     1 <= length <= 105
 *     0 <= updates.length <= 104
 *     0 <= startIdxi <= endIdxi < length
 *     -1000 <= inci <= 1000
 */
public class P370RangeAddition {
     int[] getModifiedArray(int length, int[][] updates) {
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < updates.length; j++){
                if(i >= updates[j][0] && i <= updates[j][1]){
                    result[i] += updates[j][2];
                }
            }
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
           "3|[[0,2,1],[0,2,1],[0,2,0]]|2,2,2",
           "10|[[2,4,6],[5,6,8],[1,9,-4]]|0,-4,2,2,2,4,4,-4,-4,-4"
    })
    void test(int length, String updatesArr, String expArr){

        int[] expected = Arrays.stream(expArr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[][] updates = Arrays.stream(updatesArr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                    Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        Assert.assertArrayEquals(expected, getModifiedArray(length, updates));
    }
}
