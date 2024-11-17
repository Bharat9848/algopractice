package two.pointers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

public class SumOfThree {
    private boolean isSumOfThreeExist(int[] nums, int target) {
        if(nums == null || nums.length < 2){
            return false;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int first = i;
            int left = first+1;
            int right = nums.length -1;
            while (left < right){
                if(nums[first] + nums[left] + nums[right] == target){
                    return true;
                } else if(nums[first] + nums[left] + nums[right] > target){
                    right--;
                } else {
                    left++;
                }
            }
        }
        return false;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|',
            value = {
            "1,2,3|6|true",
            "1,2,7,9|17|true",
            "1,2,3|100|false",
             "5,19,1,4,8|17|true"
            })
    void test(String numsStr, int target, boolean expected){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, isSumOfThreeExist(nums, target));
    }
}
