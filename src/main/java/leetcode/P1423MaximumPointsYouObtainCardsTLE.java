package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * There are several cards arranged in a row, and each card has an associated number of points. The points are given in the integer array cardPoints.
 *
 * In one step, you can take one card from the beginning or from the end of the row. You have to take exactly k cards.
 *
 * Your score is the sum of the points of the cards you have taken.
 *
 * Given the integer array cardPoints and the integer k, return the maximum score you can obtain.
 *
 * Input: cardPoints = [1,2,3,4,5,6,1], k = 3
 * Output: 12
 * Input: cardPoints = [2,2,2], k = 2
 * Output: 4
 * Input: cardPoints = [9,7,7,9,7,7,9], k = 7
 * Output: 55
 * Explanation: You have to take all the cards. Your score is the sum of points of all cards.
 *
 *
 *2 10 12 1 > Max( rec{2,10,12} + 1, rec{10,12,1} + 2)
 * Max( 12 + 1, 10 + 2)
 * ans =13
 * Constraints:
 *
 *     1 <= cardPoints.length <= 105
 *     1 <= cardPoints[i] <= 104
 *     1 <= k <= cardPoints.length
 */
class P1423MaximumPointsYouObtainCardsTLE {
    int maxScore(int[] cardPoints, int k) {
        if(cardPoints== null|| cardPoints.length==0 || k <= 0){
            return 0;
        }
        Map<Pair<Integer,Integer>, Integer> subArrayMaxScore = new HashMap<>();
        return maxScore(cardPoints, k, 1, 0, cardPoints.length-1, subArrayMaxScore);
    }

    private int maxScore(int[] cardPoints, int k, int count, int beg, int end, Map<Pair<Integer,Integer>, Integer> subArrayMaxScore) {
        if(beg > end){
            return 0;
        }else if(beg==end){
            return cardPoints[beg];
        }

        if(subArrayMaxScore.containsKey(new Pair<>(beg, end))){
            return subArrayMaxScore.get(new Pair<>(beg, end));
        }else {
            int max;
            if(count == k){
                max = Math.max(cardPoints[beg], cardPoints[end]);
            }else{
                max = Math.max(cardPoints[beg] +  maxScore(cardPoints, k, count+1, beg+1, end, subArrayMaxScore), cardPoints[end] +maxScore(cardPoints, k, count+1, beg, end-1, subArrayMaxScore));
            }
            subArrayMaxScore.put(new Pair<>(beg,end), max);
            return max;

        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3,4,5,6,1|3|12|descending order in last",
                    "2,2,2|2|4|all equal",
                    "2,4,6|3|12|pick all",
                    "2,4,6|10|12|pick all extended",
                    "2,10,12,1|2|13|greedy approach will not work",
                    "9,7,7,9,7,7,9|7|55|more example",
    })
    void test(String arrStr, int k, int expected, String msg){
        System.out.println(msg);
        Assertions.assertEquals(expected, maxScore(Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray(), k));
    }
}
