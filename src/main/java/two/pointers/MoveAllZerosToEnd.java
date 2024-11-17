package two.pointers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

public class MoveAllZerosToEnd {

    void moveAllZeroToEnd(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            while (nums[left] != 0 && left < right) {
                left++;
            }
            while (nums[right] == 0 && right > left) {
                right--;
            }
            if (left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
            }
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3|1,2,3",
            "1,2,3,0|1,2,3,0",
            "0,0,0|0,0,0",
            "1,0,2,0,3,0,4,0|1,4,2,3,0,0,0,0"
    })
    void test(String numStr, String expectednumStr) {
        int[] nums = Arrays.stream(numStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectednumStr.split(",")).mapToInt(Integer::parseInt).toArray();
        moveAllZeroToEnd(nums);
        Assertions.assertArrayEquals(expected, nums);
    }
}
