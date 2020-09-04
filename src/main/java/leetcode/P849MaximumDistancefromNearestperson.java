package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.stream.IntStream;

/*
In a row of seats, 1 represents a person sitting in that seat, and 0 represents that the seat is empty.

There is at least one empty seat, and at least one person sitting.

Alex wants to sit in the seat such that the distance between him and the closest person to him is maximized.

Return that maximum distance to closest person.

Example 1:

Input: [1,0,0,0,1,0,1]
Output: 2
Explanation:
If Alex sits in the second open seat (seats[2]), then the closest person has distance 2.
If Alex sits in any other open seat, the closest person has distance 1.
Thus, the maximum distance to the closest person is 2.
Example 2:

Input: [1,0,0,0]
Output: 3
Explanation:
If Alex sits in the last seat, the closest person is 3 seats away.
This is the maximum distance possible, so the answer is 3.


Constraints:

2 <= seats.length <= 20000
seats contains only 0s or 1s, at least one 0, and at least one 1.
 */
public class P849MaximumDistancefromNearestperson {
    int leftDistance(int currentIndex, int leftPersonIndex){
        return Math.abs(currentIndex - leftPersonIndex);
    }
    int rightDistance(int currentIndex, int rightPersonIndex){
        return Math.abs(rightPersonIndex - currentIndex);
    }
    int calLeftPersonIndex(int currentIndex, int[] seats) {
        return IntStream.iterate(currentIndex - 1, i -> i >=0, i -> i-1)
                .filter(index -> seats[index] == 1)
                .findFirst()
                .orElse(Integer.MAX_VALUE);
    }
    int calRightPersonIndex(int currentIndex, int[] seats){
        return IntStream.iterate(currentIndex, i -> i<seats.length, i -> i+1)
                .filter(index -> seats[index] == 1)
                .findFirst()
                .orElse(Integer.MAX_VALUE);

    }
    int maxDistToClosest(int[] seats) {
        int maxdistance = 0;
        int leftOne = calLeftPersonIndex(0, seats);
        int rightOne = calRightPersonIndex(0, seats);
        for (int i = 0; i < seats.length; i++) {
            if(seats[i] == 1){
                leftOne = rightOne;
                rightOne = calRightPersonIndex(i+1, seats);
                continue;
            }
            int leftDist = leftDistance(i, leftOne);
            int rightDist = rightDistance(i, rightOne);
            maxdistance = Math.max(maxdistance, Math.min(leftDist, rightDist));
        }
        return maxdistance;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
           "1,0,0,0,1,0,1;2",
            "1,0,0,0;3",
            "0,1;1",
            "1,0;1"
    })
    void test(String arrStr, int expected){
        int[] seats = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, maxDistToClosest(seats));
    }
}