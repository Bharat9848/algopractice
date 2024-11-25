package cyclic.sort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

public class FindMissingNumber {
    public static int findMissingNumber(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            while (i != arr[i]){
                int temp = arr[i];
                if(arr[i] < 0 || arr[i]>= arr.length|| arr[arr[i]] == arr[i]) {
                    break;
                }
                arr[i] = arr[arr[i]];
                arr[temp] = temp;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] != i){
                return i;
            }
        }
        // Your code will replace this placeholder return statement
        return -1;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
     "0,2,3,4|1",
     "0,2,3,2|1",
     "1,2,3,2|0",

    })
    void test(String arrStr, int expected){
        Assertions.assertEquals(expected, findMissingNumber(Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
