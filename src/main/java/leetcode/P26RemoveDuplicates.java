package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

class P26RemoveDuplicates {
     int removeDuplicates(int[] nums) {
        int nonDuplicateIndex = -1;
         for (int i = 0; i < nums.length; i++) {
             if(nonDuplicateIndex == -1){
                 if(i+1 < nums.length && nums[i] == nums[i+1]){
                     nonDuplicateIndex = i+1;
                 }
                 continue;
             }
             if(i+1 < nums.length && nums[i] != nums[i+1]){
                nums[nonDuplicateIndex] = nums[i+1];
                nonDuplicateIndex++;
             }
         }
        return nonDuplicateIndex == -1? nums.length: nonDuplicateIndex;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "1,2,3,4,5|1,2,3,4,5|5",
        "1,1,1,1,2,2,2,3,4,5|1,2,3,4,5|5",
        "1,1,1,1,2,2,2,3,4,4,5|1,2,3,4,5|5",
        "1,1,1,1,2,2,2,3,4,5,5|1,2,3,4,5|5",
        "1,1,1,1|1|1",

    })
    void test(String numsStr, String expecedStr, int expectedEle){
         int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
         int[] expected = Arrays.stream(expecedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expectedEle, removeDuplicates(nums));
        Assert.assertArrayEquals(expected, Arrays.copyOfRange(nums,0, expectedEle));
    }

}
