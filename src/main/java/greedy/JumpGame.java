package greedy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * In a single-player jump game, the player starts at one end of a series of squares, with the goal of reaching the last square.
 *
 * At each turn, the player can take up to ss steps towards the last square, where ss is the value of the current square.
 *
 * For example, if the value of the current square is 33, the player can take either 33 steps, or 22 steps, or 11 step in the direction of the last square. The player cannot move in the opposite direction, that is, away from the last square.
 *
 * You have been tasked with writing a function to validate whether a player can win a given game or not.
 *
 * You’ve been provided with the nums integer array, representing the series of squares. The player starts at the first index and, following the rules of the game, tries to reach the last index.
 *
 * If the player can reach the last index, your function returns TRUE; otherwise, it returns FALSE.
 *
 * Constraints:
 *
 *     1≤1≤ nums.length ≤103≤103
 *     0≤0≤ nums[i] ≤103≤103
 */
public class JumpGame {
    public static boolean jumpGame(int[] nums) {
        int pos = 0;
        int jump = nums[0];
        if(nums.length == 1){
            return true;
        }

        while(jump > 0){
            if(pos == nums.length -1){
                return true;
            }
            int nextPos = pos;
            int max = -1;
            for (int i = 1; i <= jump; i++) {
                if(pos+i == nums.length - 1){
                    return true;
                }
                if(nums[pos + i] >= max){
                    max = nums[pos + i];
                    nextPos = pos + i;
                }
            }
            pos = nextPos;
            jump = max;
        }
        return false;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3,2,1,0,4|false",
            "2,3,1,1,4|true",
            "3,2,2,2,2,2,2|true"
    }
    )
    void test(String numsStr, boolean expected){
        Assertions.assertEquals(expected, jumpGame(Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }

}
