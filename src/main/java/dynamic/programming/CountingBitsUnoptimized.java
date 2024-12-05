package dynamic.programming;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * For a given positive integer, n, your task is to return an array of length n+1n+1 such that for each xx where 0≤x≤n0≤x≤n, result[x] is the count of 11s in the binary representation of xx.
 *
 * Constraints:
 *
 *     0≤n≤10^4
 */
public class CountingBitsUnoptimized {
    public static int[] countingBits(int n) {
        if(n==0){
            return new int[]{0};
        }
        int[] solution = new int[n+1];
        int[] minTwoPower = new int[n+1];
        solution[0] = 0;
        solution[1] = 1;
        minTwoPower[0] = 0;
        minTwoPower[1] = 1;
        for (int i = 2; i <= n; i++) {
            if(i == 2*minTwoPower[i-1]){
                minTwoPower[i] = 2*minTwoPower[i-1];
            }else {
                minTwoPower[i] = minTwoPower[i-1];
            }
            int temp = i;
            int noOfBits = 0;
            while (temp > 0){
                temp =  temp - minTwoPower[temp];
                noOfBits++;
            }
            solution[i] = noOfBits;
        }
        return solution;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0|0",
            "1|0,1",
            "2|0,1,1",
            "3|0,1,1,2",
            "10|0,1,1,2,1,2,2,3,1,2,2",
            "27|0,1,1,2,1,2,2,3,1,2,2,3,2,3,3,4,1,2,2,3,2,3,3,4,2,3,3,4"
    })
    void test(int n, String expectedStr){
        Assertions.assertArrayEquals(Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray(), countingBits(n));
    }
}
