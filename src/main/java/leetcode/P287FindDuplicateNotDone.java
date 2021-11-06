package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
 * There is only one repeated number in nums, return this repeated number.
 * You must solve the problem without modifying the array nums and uses only constant extra space.
 *
 * Example 1:
 *
 * Input: nums = [1,3,4,2,2]
 * Output: 2
 *
 * Example 2:
 *
 * Input: nums = [3,1,3,4,2]
 * Output: 3
 *
 * Example 3:
 *
 * Input: nums = [1,1]
 * Output: 1
 *
 * Example 4:
 *
 * Input: nums = [1,1,2]
 * Output: 1
 *
 * Constraints:
 *
 *     1 <= n <= 10^5
 *     nums.length == n + 1
 *     1 <= nums[i] <= n
 *     All the integers in nums appear only once except for precisely one integer which appears two or more times.
 *
 * Follow up:
 *
 *     How can we prove that at least one duplicate number must exist in nums?
 *     Can you solve the problem in linear runtime complexity?
 */
public class P287FindDuplicateNotDone {
    public int findDuplicate(int[] nums){
        long bit1 = 0L;//1-32
        long bit2 = 0L;//32-64
        long bit3 = 0l;//64-96
        long bit4 = 0l;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if(num <= 32){
                long onBit = 1<<(num-1);
                boolean isOff = (bit1 & onBit) == 0L;
                if(isOff){
                    bit1 = bit1 | onBit;
                }else{
                    return num;
                }
            }else if (num > 32 && num <= 64){
                long onBit = 1<<(num-1-32);
                boolean isOff = (bit2 & onBit) == 0L;
                if(isOff){
                    bit2 = bit2 | onBit;
                }else{
                    return num;
                }
            } else if(num>64 && num <= 96){
                long onBit = 1<<(num-1-64);
                boolean isOff = (bit3 & onBit) == 0L;
                if(isOff){
                    bit3 = bit3 | onBit;
                }else{
                    return num;
                }
            }else{
                long onBit = 1<<(num-1-96);
                boolean isOff = (bit4 & onBit) == 0L;
                if(isOff){
                    bit4 = bit4 | onBit;
                }else{
                    return num;
                }
            }
        }
        return 0;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,1|1",
            "1,1|1",
            "1,3,4,2,2|2",
            "3,1,3,4,2|3",
            "2,2,2,2,2|2",
            "67,90,11,12,90|90",
            "13,46,8,11,20,17,40,13|13",
            "13,46,8,11,20,17,40,13,13,13,14,1,13,36,48,41,13,13,13,13,45,13,28,42,13,10,15,22,13,13,13,13,23,9,6,13,47,49,16,13,13,39,35,13,32,29,13,25,30,13|13"
    })
    void test(String input, int expected){
        int[] nums = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, findDuplicate(nums));
    }
}
