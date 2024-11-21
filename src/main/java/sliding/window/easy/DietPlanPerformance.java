package sliding.window.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A dieter consumes calories[i] calories on the i-th day.
 *
 * Given an integer k, the dieter reviews their calorie intake over every sequence of k consecutive days (from calories[i] to calories[i+k-1] for all 0 <= i <= n-k). For each sequence, they calculate T, the total calories consumed over those k days:
 *
 *     If T is less than lower, the dieter performs poorly and loses 1 point.
 *
 *     If T is greater than upper, the dieter performs better and gains 1 point.
 *
 *     If T is between lower and upper (inclusive), the dieter’s performance is normal, and their points remain the same.
 *
 * The dieter starts with zero points. Return the total points after the dieter follows this routine for all calories.length days. The total points can be negative.
 *
 * Constraints
 *
 *     1≤k≤1≤k≤ calories.length ≤105≤105
 *
 *     0≤0≤ calories[i] ≤20000≤20000
 *
 *     0≤0≤ lower ≤upper≤upper
 */
public class DietPlanPerformance {
    public static int dietPlanPerformance(List<Integer> calories, int k, int lower, int upper) {
        int score = 0;
        int wsum = 0;
        int left = 0, right = k-1;
        for (int i = 0; i < k; i++) {
            wsum += calories.get(i);
        }
        if(wsum < lower){
            score -= 1;
        } else if (wsum >= lower && wsum <= upper) {
            score += 0;
        } else {
            score += 1;
        }

        while (right + 1 < calories.size()){
            wsum -= calories.get(left);
            left++;
            right++;
            wsum += calories.get(right);
            if(wsum < lower){
                score -= 1;
            } else if (wsum >= lower && wsum <= upper) {
                score += 0;
            } else {
                score += 1;
            }
        }
        // Replace this placeholder return statement with your code
        return score;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2,3,5,7,8|1|5|5|0",
            "2,5,8|2|1|3|2",
            "2,3,5|2|4|6|1",
            "0,0,0|2|3|4|-2",
            "0,0,0|3|2|4|-1",

    })
    void test(String calStr, int k, int lower, int upper, int expected) {
        Assertions.assertEquals(expected, dietPlanPerformance(Arrays.stream(calStr.split(",")).map(Integer::valueOf).collect(Collectors.toList()), k, lower, upper));
    }

}
