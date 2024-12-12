package search.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * You are given a sorted array of integers, nums, where all integers appear twice except for one. Your task is to find and return the single integer that appears only once.
 *
 * The solution should have a time complexity of O(logn) or better and a space complexity of O(1).
 *
 * Constraints:
 *
 *     1≤1≤ nums.length ≤103≤103
 *
 *     0≤0≤ nums[i] ≤103≤103
 */
public class MissingInSortArrayPairs {
    public static int singleNonDuplicate(int[] nums) {
        int beg = 0, end = nums.length - 1;
        while (beg < end) {
            int mid = beg + (end - beg) / 2;
            if ((mid - 1 >= 0 && nums[mid] != nums[mid - 1] && mid + 1 < nums.length && nums[mid] != nums[mid + 1]) || (mid == 0 && nums[mid] != nums[mid + 1]) || (mid == nums.length - 1 && nums[mid - 1] != nums[mid])) {
                return nums[mid];
            } else if ((mid - beg) % 2 != 0) {
                if (mid - 1 >= 0 && nums[mid - 1] == nums[mid]) {
                    beg = mid + 1;
                } else if (mid - 1 >= 0 && nums[mid - 1] != nums[mid]) {
                    end = mid - 1;
                }
            } else if ((mid - beg) % 2 == 0) {
                if (mid - 1 >= 0 && nums[mid - 1] != nums[mid]) {
                    beg = mid;
                } else if (mid - 1 >= 0 && nums[mid - 1] == nums[mid]) {
                    end = mid-2;
                }
            }
        }
        return nums[beg];
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,1,2,3,3,4,4|2",
            "1,1,2,2,3,4,4|3",
            "1,1,2,2,3,3,5,7,7|5",
            "0,1,1,5,5,7,7|0",
            "1,1,5,5,7,7,8|8",
            "1,1,9|9",
            "1|1",
    })
    void test(String sortedArrStr, int expected){
        Assertions.assertEquals(expected, singleNonDuplicate(Arrays.stream(sortedArrStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
