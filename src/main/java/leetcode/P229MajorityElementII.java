package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
 *
 * Input: nums = [3,2,3]
 * Output: [3]
 *
 * Input: nums = [1]
 * Output: [1]
 *
 * Input: nums = [1,2]
 * Output: [1,2]
 *
 *
 *     1 <= nums.length <= 5 * 104
 *     -109 <= nums[i] <= 109
 *
 *
 *
 * Follow up: Could you solve the problem in linear time and in O(1) space?
 */
public class P229MajorityElementII {

    List<Integer> majorityElementBoyerMooreVoting(int[] nums) {
        int no1 = nums[0], no1Count = 1;
        int no2 = nums[0], no2Count = 0;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] == no1){
                no1Count++;
            }else if(nums[i] == no2){
                no2Count++;
            }else{
                if(no1Count == 0){
                    no1 = nums[i];
                    no1Count++;
                }else if (no2Count == 0){
                    no2 = nums[i];
                    no2Count++;
                }else{
                        no1Count--;
                        no2Count--;
                }
            }
        }
        int times = nums.length/3;
        List<Integer> result = new ArrayList<>();
        int count1=0, count2=0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == no1){
                count1++;
            }else if(nums[i] == no2){
                count2++;
            }
        }
        if(count1>times){
            result.add(no1);
        }
        if(count2>times){
            result.add(no2);
        }
        return result;
    }




    List<Integer> majorityElement(int[] nums) {
        Arrays.sort(nums);
        int times = nums.length/3;
        if(times == 0){
            return Arrays.stream(nums).boxed().distinct().collect(Collectors.toList());
        }
        int currentCount =1;
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < nums.length; i++) {
            if(nums[i-1] != nums[i]){
                if(currentCount>times){
                    result.add(nums[i-1]);
                }
                currentCount = 1;
            }else {
                currentCount++;
            }
        }
        if(currentCount > times){
            result.add(nums[nums.length-1]);
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3,2,3|3",
            "3,2,3,2,1,3|3",
                    "1|1",
                    "1,2|1,2",
                    "1,2,3,3,3,3,4|3",
                    "1,2,3,4,5,6,1|",
    })
    void test(String numsStr, String expectedStr){
        List<Integer> expected = expectedStr == null ? Collections.emptyList():Arrays.stream(expectedStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, majorityElementBoyerMooreVoting(nums));
    }
}
