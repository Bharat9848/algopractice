package interval;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.IntervalParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * For two lists of closed intervals given as input, intervalLista and intervalListb, where each interval has its own start and end time, write a function that returns the intersection of the two interval lists.
 *
 * For example, the intersection of [3,8][3,8] and [5,10][5,10] is [5,8][5,8].
 *
 * Constraints:
 *
 *     0≤0≤ intervalLista.length, intervalListb.length ≤1000≤1000
 *
 *     0≤0≤ start[i] << end[i] ≤109≤109, where ii is used to indicate intervalLista
 *
 *     end[i] << start[i + 1]
 *
 *     0≤0≤ start[j] << end[j] ≤109≤109, where jj is used to indicate intervalListb
 *
 *     end[j] << start[j + 1]
 */

/**
 * sort the lists
 * use two pointer initialize on both the list.
 * then compare intervals at both if intersect add the intersection in the result.
 * increment pointer for list having smaller end time.
 *
 * =======
 *   ==
 *
 *   ==
 * =======
 *
 *   ======
 *      ======
 *
 *      =======
 *   ======
 *
 * */
public class intervalIntersectionList {
    public static int[][] intervalsIntersection(int[][] intervalLista, int[][] intervalListb) {
        if(intervalLista.length == 0){
            return intervalListb;
        }
        if(intervalListb.length == 0){
            return intervalLista;
        }
        int i = 0, j = 0;
        ArrayList<int[]> result = new ArrayList<>();
        Arrays.sort(intervalLista, Comparator.comparingInt(arr -> arr[0]));
        Arrays.sort(intervalListb, Comparator.comparingInt(arr -> arr[0]));
        while(i < intervalLista.length && j < intervalListb.length){
            int ay = intervalLista[i][1];
            int ax = intervalLista[i][0];
            int bx = intervalListb[j][0];
            int by = intervalListb[j][1];
            if(ax >= bx && ax <= by && ay >= bx && ay <= by){
                result.add(intervalLista[i]);
            } else if(bx >= ax && bx <= ay && by >= ax && by <= ay){
                result.add(intervalListb[j]);
            } else if(ax <= bx && bx <= ay){
               result.add(new int[] {bx, ay});
            } else if (bx <= ax && ax <= by){
                result.add(new int[]{ax, by});
            }
            if(ay > by){
                j++;
            } else if(ay==by) {
                i++;
                j++;
            }else {
                i++;
            }
        }
        return result.toArray(new int[0][]);
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,4],[5,6],[7,8],[9,15]]|[[2,4],[5,7],[9,15]]|[[2,4],[5,6],[7,7],[9,15]]",
            "[[1,10]]|[[2,4]]|[[2,4]]",
            "[[2,4]]|[[1,10]]|[[2,4]]",
            "[[2,6],[7,9],[10,13],[14,19],[20,24]]|[[1,29]|[[2,6],[7,9],[10,13],[14,19]]",
            "[[2,6],[7,9],[10,13],[14,19],[20,24]]|[[1,4],[6,8],[15,18]]|[[2,4],[6,6],[7,8],[15,18]]]"
    })
    void test(String intervalAStr, String intervalBStr, String expectedStr){
        int[][] intervalA = IntervalParser.parseIntArrayString(intervalAStr, "(\\[\\d+,\\d+\\])");
        int[][] intervalB = IntervalParser.parseIntArrayString(intervalAStr, "(\\[\\d+,\\d+\\])");
        int[][] expected = IntervalParser.parseIntArrayString(intervalAStr, "(\\[\\d+,\\d+\\])");
        Assertions.assertArrayEquals(expected, intervalsIntersection(intervalA, intervalB));
    }
}
