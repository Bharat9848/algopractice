package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

public class P1346CheckIfNAndItsDoubleExist {
    boolean checkIfExist(int[] arr) {
        Arrays.sort(arr);
        for (int i = 0; i < arr.length-1; i++) {
            if(doubleExist(arr, i)){
                return true;
            }
        }
        return false;
    }

    private boolean doubleExist(int[] arr, int index) {
        int beg = index+1, end = arr.length-1;
        if(arr[index] < 0){
            beg = 0;
            end = index-1;
        }
        while (beg < end){
            int mid = beg + ((end-beg)/2);
            if(arr[mid] == 2*arr[index]){
                return true;
            }else if(arr[mid] < 2*arr[index]){
                beg = mid+1;
            }else {
                end = mid;
            }
        }
        return arr[beg] == arr[index]*2;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "10,2,5,3|true",
            "7,1,14,11|true",
            "7,1,13,11|false",
            "-40,4,9,-20|true",
            "-40,4,9,-21,0|false",
            "-40,4,9,-21,1,2|true",

    })
    void test(String arrStr, boolean expected){
        int[] nums = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();

        Assert.assertEquals(expected, checkIfExist(nums));
    }
}
