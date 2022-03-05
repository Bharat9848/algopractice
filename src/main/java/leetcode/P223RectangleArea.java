package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given the coordinates of two rectilinear rectangles in a 2D plane, return the total area covered by the two rectangles.
 *
 * The first rectangle is defined by its bottom-left corner (ax1, ay1) and its top-right corner (ax2, ay2).
 *
 * The second rectangle is defined by its bottom-left corner (bx1, by1) and its top-right corner (bx2, by2).
 *
 *
 *
 * Example 1:
 * Rectangle Area
 *
 * Input: ax1 = -3, ay1 = 0, ax2 = 3, ay2 = 4, bx1 = 0, by1 = -1, bx2 = 9, by2 = 2
 * Output: 45
 *
 * Example 2:
 *
 * Input: ax1 = -2, ay1 = -2, ax2 = 2, ay2 = 2, bx1 = -2, by1 = -2, bx2 = 2, by2 = 2
 * Output: 16
 *
 *
 *
 * Constraints:
 *
 *     -104 <= ax1, ay1, ax2, ay2, bx1, by1, bx2, by2 <= 104
 */
public class P223RectangleArea {

    int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int area1 = computeAreaRect(ax1, ay1, ax2, ay2);
        int area2 = computeAreaRect(bx1, by1, bx2, by2);
        int commonArea = calCommonArea(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2);
        return area1 + area2 - commonArea;
    }

    private int calCommonArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int[] xCords = new int[]{ax1, ax2, bx1, bx2};
        int[] yCords = new int[]{ay1, ay2, by1, by2};
        Arrays.sort(xCords);
        Arrays.sort(yCords);
        if(intersectingBothSide(Math.min(ax1, ax2), Math.max(ax1, ax2), xCords) && intersectingBothSide(Math.min(ay1, ay2), Math.max(ay1, ay2), yCords) || intersectingOneSide(ax1, ax2, ay1, ay2, xCords, yCords) || intersectingOneSide(bx1, bx2, by1, by2, xCords, yCords)){
            return  computeAreaRect(xCords[1], yCords[1], xCords[2], yCords[2]);
        }else if(isSubRectangle(ax1, ax2, ay1, ay2, xCords, yCords)){
            return computeAreaRect(ax1, ay1, ax2, ay2);
        }else if(isSubRectangle(bx1, bx2, by1, by2, xCords, yCords)){
            return computeAreaRect(bx1, by1, bx2, by2);
        }
        return 0;
    }

    private boolean intersectingOneSide(int x1, int x2, int y1, int y2, int[] xCords, int[] yCords) {
        return (Math.min(x1, x2) == xCords[1] &&  Math.max(x1, x2) == xCords[2] && intersectingBothSide(Math.min(y1, y2), Math.max(y1, y2), yCords)) ||
                (Math.min(y1, y2) == yCords[1] &&  Math.max(y1, y2) == yCords[2] && intersectingBothSide(Math.min(x1, x2), Math.max(x1, x2), xCords));
    }

    private boolean isSubRectangle(int ax1, int ax2, int ay1, int ay2, int[] xCords, int[] yCords){
        return xCords[1] == Math.min(ax1, ax2) && xCords[2] == Math.max(ax1, ax2)
                && yCords[1] == Math.min(ay1, ay2) && yCords[2] == Math.max(ay1, ay2);
    }
    private boolean intersectingBothSide(int ax1, int ax2, int[] xCords) {
        return (ax1 == xCords[0] && ax2 != xCords[1]) || (ax1 == xCords[2] && ax2 != xCords[3]) || (ax1 == xCords[1] && ax2 != xCords[2]);
    }

    private int computeAreaRect(int ax1, int ay1, int ax2, int ay2) {
        return Math.abs(ax1 - ax2) * Math.abs(ay1 - ay2);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
                    "-2,-2,2,2,-1,4,1,6|20",
            "-2,-2,2,2,-3,-3,3,-1|24",
            "-2,-2,2,2,3,3,4,4|17",
            "-3,0,3,4,0,-1,9,2|45",
             "-2,-2,2,2,-2,-2,2,2|16",

    })
    void test(String argsStr, int expected){
        String[] coordinate = argsStr.split(",");
        int ax1 = Integer.parseInt(coordinate[0]);
        int ay1 = Integer.parseInt(coordinate[1]);
        int ax2 = Integer.parseInt(coordinate[2]);
        int ay2 = Integer.parseInt(coordinate[3]);
        int bx1 = Integer.parseInt(coordinate[4]);
        int by1 = Integer.parseInt(coordinate[5]);
        int bx2 = Integer.parseInt(coordinate[6]);
        int by2 = Integer.parseInt(coordinate[7]);
        Assertions.assertEquals(expected, computeArea(ax1, ay1, ax2, ay2, bx1, by1,  bx2, by2));
    }
}
