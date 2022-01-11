package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * A school is trying to take an annual photo of all the students. The students are asked to stand in a single file line in non-decreasing order by height. Let this ordering be represented by the integer array expected where expected[i] is the expected height of the ith student in line.
 *
 * You are given an integer array heights representing the current order that the students are standing in. Each heights[i] is the height of the ith student in line (0-indexed).
 *
 * Return the number of indices where heights[i] != expected[i].
 *
 *
 *
 * Example 1:
 *
 * Input: heights = [1,1,4,2,1,3]
 * Output: 3
 * Explanation:
 * heights:  [1,1,4,2,1,3]
 * expected: [1,1,1,2,3,4]
 * Indices 2, 4, and 5 do not match.
 *
 * Example 2:
 *
 * Input: heights = [5,1,2,3,4]
 * Output: 5
 * Explanation:
 * heights:  [5,1,2,3,4]
 * expected: [1,2,3,4,5]
 * All indices do not match.
 *
 * Example 3:
 *
 * Input: heights = [1,2,3,4,5]
 * Output: 0
 * Explanation:
 * heights:  [1,2,3,4,5]
 * expected: [1,2,3,4,5]
 * All indices match.
 *
 *
 *
 * Constraints:
 *
 *     1 <= heights.length <= 100
 *     1 <= heights[i] <= 100
 *
 *     At i there should i-1 small element in left of it and size - i big element should be on the right of it
 */
class P1051HeightChecker {
    int heightChecker(int[] heights) {
        int wrongOrder = 0;

        for (int i = 0; i <heights.length; i++) {
            int h = heights[i];
            int bigCount =0;
            int smallCount =0;
            int equalCount =0;
            for (int j = 0; j < heights.length; j++) {
                if(i == j){continue;}
                if(heights[j] == h){
                    equalCount+=1;
                }else if(heights[j] < h){
                    smallCount+=1;
                }else{
                    bigCount+=1;
                }
            }

            boolean valid = (smallCount == i && bigCount == heights.length - (i+1));
                for (int j = equalCount; j > 0 && !valid ; j--) {
                    if(smallCount+j == i || bigCount+j == heights.length-(i+1)){
                        valid = true;
                    }
                }
            if(!valid){
                wrongOrder += 1;
            }
        }
        return wrongOrder;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|',value = {
          "1,2,3,4,5|0",
          "5,1,2,3,4|5",
            "1,1,4,2,1,3|3",
            "1,2,1|2",
            "2|0"
    })
    void test(String arrStr, int expected){
        int[] heights = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, heightChecker(heights));
    }
}
