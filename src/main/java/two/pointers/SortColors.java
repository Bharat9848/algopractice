package two.pointers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Given an array, colors, which contains a combination of the following three elements:
 *
 *     00 (representing red)
 *
 *     11 (representing white)
 *
 *     22 (representing blue)
 *
 * Sort the array in place so that the elements of the same color are adjacent, with the colors in the order of red, white, and blue. To improve your problem-solving skills, do not utilize the built-in sort function.
 *
 * Constraints:
 *
 *     1≤1≤ colors.length ≤≤ 300
 *     colors[i] can only contain 00s, 11s, or 22s.
 */
public class SortColors {
    public static int[] sortColors (int[] colors) {
        int left = 0, right=colors.length-1;
        while (left<=right){
            if(colors[left] == 0){
                left++;
            } else {
                int temp = colors[left];
                colors[left] = colors[right];
                colors[right] = temp;
                right--;
            }
        }
        int mid = left;
        right = colors.length-1;
        while (mid < right){
            if(colors[mid] == 1){
                mid++;
            } else {
                int temp = colors[mid];
                colors[mid] = colors[right];
                colors[right] = temp;
                right--;
            }
        }
        // Write your code here

        return colors;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0,1,1,0,1,1,1,2|0,0,1,1,1,1,1,2",
            "1,1,0,2,2|0,1,1,2,2",
            "1,1,1|1,1,1",
            "2,2,2|2,2,2",
            "0,1,2|0,1,2",
            "2,1,0|0,1,2",
    })
    void test(String arrStr, String expectedStr){
        int[] colors = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] ex = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(ex, sortColors(colors));
    }
}
