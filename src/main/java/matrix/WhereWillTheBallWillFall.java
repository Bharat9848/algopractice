package matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 *You have nn balls and a 2D grid of size m×nm×n representing a box. The box is open on the top and bottom sides. Each cell in the box has a diagonal that can redirect a ball to the right or the left. You must drop nn balls at each column’s top. The goal is to determine whether each ball will fall out of the bottom or become stuck in the box. Each cell in the grid has a value of 11 or −1−1.
 *
 *     11 represents that the grid will redirect the ball to the right.
 *     −1−1 represents that the grid will redirect the ball to the left.
 *
 * A ball gets stuck if it hits a V-shaped pattern between two grids or if a grid redirects the ball into either wall of the box.
 *
 * The solution should return an array of size nn, with the ithith element indicating the column that the ball falls out of, or it becomes −1−1 if it’s stuck. If the ball drops from column xx and falls out from column yy, then in the result array, index xx contains value yy.
 *
 * Constraints:
 *
 *     m==m== grid.length
 *     n==n== grid[i].length
 *     1≤m,n≤100 1≤m,n≤100
 *     grid[i][j] is 11 or −1−1.
 */
public class WhereWillTheBallWillFall {

    public static int[] findExitColumn(int[][] grid) {
        int[] result = new int[grid[0].length];
        for (int ballNo = 0; ballNo < grid[0].length; ballNo++) {
            int currentCol = ballNo;
            for (int row = 0; row < grid.length; row++) {
                if(grid[row][currentCol] == 1 && currentCol+1 < grid[0].length && grid[row][currentCol+1] == 1){
                    currentCol = currentCol+1;
                } else if(grid[row][currentCol] == -1 && currentCol-1 >= 0  && grid[row][currentCol-1] == -1){
                    currentCol = currentCol - 1;
                } else {
                    result[ballNo] = -1;
                    break;
                }
            }
            if(result[ballNo] != -1){
                result[ballNo] = currentCol;
            }
        }
        return result;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1]]|-1",
            "[[-1]]|-1",
            "[[1,1,1,-1,-1]]|1,2,-1,-1,3",
            "[[1,1,1,-1,-1],[1,1,1,-1,-1],[-1,-1,-1,1,1],[1,1,1,1,-1],[-1,-1,-1,-1,-1]]|1,-1,-1,-1,-1",
            "[[1,1],[-1,-1]]|0,-1",
            "[[1,1,1],[-1,-1,-1],[1,1,1],[-1,-1,-1]]|0,1,-1"
    })
    void test(String matrixStr, String expectedStr){
        var pattern = Pattern.compile("\\[((-)?\\d,)*((-)?\\d)\\]");
        var matcher = pattern.matcher(matrixStr);
        int[][] grid = matcher.results().map(matchResult -> matcher.group()).map(str -> {
            var tokens =  Arrays.stream(str.replace("[", "").replace("]", "").split(",")).mapToInt(Integer::parseInt).toArray();
            return tokens;
        }).toArray(int[][]::new);
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(expected, findExitColumn(grid));
    }
}
