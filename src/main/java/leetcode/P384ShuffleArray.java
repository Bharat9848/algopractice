package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Given an integer array nums, design an algorithm to randomly shuffle the array. All permutations of the array should be equally likely as a result of the shuffling.
 *
 * Implement the Solution class:
 *
 *     Solution(int[] nums) Initializes the object with the integer array nums.
 *     int[] reset() Resets the array to its original configuration and returns it.
 *     int[] shuffle() Returns a random shuffling of the array.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["Solution", "shuffle", "reset", "shuffle"]
 * [[[1, 2, 3]], [], [], []]
 * Output
 * [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
 *
 * Explanation
 * Solution solution = new Solution([1, 2, 3]);
 * solution.shuffle();    // Shuffle the array [1,2,3] and return its result.
 *                        // Any permutation of [1,2,3] must be equally likely to be returned.
 *                        // Example: return [3, 1, 2]
 * solution.reset();      // Resets the array back to its original configuration [1,2,3]. Return [1, 2, 3]
 * solution.shuffle();    // Returns the random shuffling of array [1,2,3]. Example: return [1, 3, 2]
 *
 *TODO The Fisher-Yates algorithm see Array.md
 *
 * Constraints:
 *
 *     1 <= nums.length <= 200
 *     -106 <= nums[i] <= 106
 *     All the elements of nums are unique.
 *     At most 5 * 104 calls in total will be made to reset and shuffle.
 */
public class P384ShuffleArray {
    private int[] original ;
    void init(int[] nums) {
        this.original = nums;
    }

    public int[] reset() {
        return original;
    }

    public int[] shuffle() {
        int[] result = new int[original.length];
        Random random = new Random();
        Set<Integer> indexUsed = new HashSet<>();
        int i = 0;
        while (i < original.length) {
            int index = random.nextInt(original.length);
            if(!indexUsed.contains(index)){
                indexUsed.add(index);
                result[i] = original[index];
                i++;
            }
        }
        return result;
    }

    @Test
    void testShuffle(){
        int[] original = {1, 2, 3,4,5};
        this.init(original);
        int[] shuffled = this.shuffle();
        System.out.println(Arrays.toString(shuffled));
        Assertions.assertFalse(Arrays.equals(original, shuffled));
    }
}
