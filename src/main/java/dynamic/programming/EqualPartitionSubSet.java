package dynamic.programming;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a non-empty array of positive integers, determine if the array can be divided into two subsets so that the sum of both subsets is equal.
 *
 * Constraints:
 *
 *     1≤1≤ nums.length ≤200≤200
 *     1≤1≤ nums[i] ≤100≤100
 */
public class EqualPartitionSubSet {

    public static boolean canPartitionArray(int[] arr) {
        int sum =0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        if(sum % 2 == 0){
            int target = sum/2;
            Map<Integer, Boolean> sumPossible =  new HashMap<>();
            sumPossible.put(arr[0], true);
            for (int i = 1; i < arr.length; i++) {
                Map<Integer, Boolean> sumPossibleTemp =  new HashMap<>();
                for (Integer key: sumPossible.keySet()) {
                    sumPossibleTemp.put(key, true);
                    sumPossibleTemp.put(key + arr[i], true);
                }
                sumPossibleTemp.put(arr[i],true);
                sumPossible = sumPossibleTemp;
            }
            return sumPossible.getOrDefault(target, false);
        }
        return false;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,3,7,3|true",
            "1,2,3,11,5|true",
            "1,2,3,3,4,4,5|true",
            "1,2,7|false",
    })
    void test(String numsStr, boolean expected){
        Assertions.assertEquals(expected, canPartitionArray(Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }

}

