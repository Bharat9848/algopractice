package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. The guards have gone and will come back in h hours.
 *
 * Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas and eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead and will not eat any more bananas during this hour.
 *
 * Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.
 *
 * Return the minimum integer k such that she can eat all the bananas within h hours.
 *
 * Input: piles = [3,6,7,11], h = 8
 * Output: 4
 *
 * Input: piles = [30,11,23,4,20], h = 5
 * Output: 30
 *
 * Input: piles = [30,11,23,4,20], h = 6
 * Output: 23
 * Constraints:
 *
 *     1 <= piles.length <= 10^4
 *     piles.length <= h <= 10^9
 *     1 <= piles[i] <= 109
 *
 *     Maths: did not work - find avg then work out the minimum by going to next no ..
 *     Do it by iterative approach from speed 1 to ... using binary search.
 */
class P875KokoEatingBananas {
    int minEatingSpeedSeq(int[] piles, int h){
        int totalBananas = Arrays.stream(piles).sum();
        int currentSpeed = totalBananas/h;
        int totalHours = findTotalHoursAtCurrentSpeed(piles, currentSpeed);
        while (totalHours != h){
            if(totalHours > h){
                currentSpeed++;
            }else if(totalHours < h){
                currentSpeed--;
            }
            totalHours = findTotalHoursAtCurrentSpeed(piles, currentSpeed);
        }
        return currentSpeed;
    }

    private int findTotalHoursAtCurrentSpeed(int[] piles, int currentSpeed) {
        return Arrays.stream(piles).map(pile -> { if(pile <= currentSpeed){return 1;}else{
            return pile%currentSpeed == 0? pile/currentSpeed: pile/currentSpeed +1;
        }}).sum();
    }

    int minEatingSpeed(int[] piles, int h){
        int beg = 1;
        int end = Arrays.stream(piles).max().orElseThrow();
        while (beg < end){
            int mid = beg + ((end - beg)/2);
            int totalHoursAtCurrentSpeed = findTotalHoursAtCurrentSpeed(piles, mid);
            if(totalHoursAtCurrentSpeed == h){
                end = mid;
            }else if(totalHoursAtCurrentSpeed < h){
                end = mid;
            }else{
                beg = mid + 1;
            }
        }
        return beg;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value={
            "6,11|5|4",
            "5,10|5|4",
            "3,6,7,11|8|4",
            "30,11,23,4,20|5|30",
            "30,11,23,4,20|6|23",
    } )
    void test(String pilesStr, int hours, int expected){
        Assert.assertEquals(expected, minEatingSpeed(Arrays.stream(pilesStr.split(",")).mapToInt(Integer::parseInt).toArray(), hours));
    }
}
