package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * You are assigned to put some amount of boxes onto one truck. You are given a 2D array boxTypes, where boxTypes[i] = [numberOfBoxesi, numberOfUnitsPerBoxi]:
 *
 *     numberOfBoxesi is the number of boxes of type i.
 *     numberOfUnitsPerBoxi is the number of units in each box of the type i.
 *
 * You are also given an integer truckSize, which is the maximum number of boxes that can be put on the truck. You can choose any boxes to put on the truck as long as the number of boxes does not exceed truckSize.
 *
 * Return the maximum total number of units that can be put on the truck.
 *
 *
 *
 * Example 1:
 *
 * Input: boxTypes = [[1,3],[2,2],[3,1]], truckSize = 4
 * Output: 8
 * Explanation: There are:
 * - 1 box of the first type that contains 3 units.
 * - 2 boxes of the second type that contain 2 units each.
 * - 3 boxes of the third type that contain 1 unit each.
 * You can take all the boxes of the first and second types, and one box of the third type.
 * The total number of units will be = (1 * 3) + (2 * 2) + (1 * 1) = 8.
 *
 * Example 2:
 *
 * Input: boxTypes = [[5,10],[2,5],[4,7],[3,9]], truckSize = 10
 * Output: 91
 *
 *
 *
 * Constraints:
 *
 *     1 <= boxTypes.length <= 1000
 *     1 <= numberOfBoxesi, numberOfUnitsPerBoxi <= 1000
 *     1 <= truckSize <= 106
 */
public class P1710MaxUnitsOnTruck {
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        int capacityLeft = truckSize;
        int units = 0;
        Arrays.sort(boxTypes, (o1, o2) -> -(o1[1] - o2[1]));
        for (int i = 0; i < boxTypes.length && capacityLeft>0; i++) {
            int[] box = boxTypes[i];
            int noOfUnits = box[1];
            int noOfBoxes = box[0];
            if(capacityLeft >= noOfBoxes){
                units+=(noOfBoxes * noOfUnits);
                capacityLeft -= noOfBoxes;
            }else {
                units +=(capacityLeft * noOfUnits);
                capacityLeft = 0;
            }
        }
        return units;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[5,10],[2,5],[4,7],[3,9]]|10|91",
            "[[1,3],[2,2],[3,1]]|4|8",
            "[[1,3],[2,2],[3,1]]|6|10"
    })
    void test(String boxStr, int truckSize, int expected){
        int[][] boxTypes =  Arrays.stream(boxStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                        Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        Assert.assertEquals(expected, maximumUnits(boxTypes, truckSize));
    }
}
