package matrix.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * You are given a grid with dimensions row x col, where each cell represents either land (grid[i][j] = 1) or water (grid[i][j] = 0). The grid satisfies the following conditions:
 *
 *     Cells are connected only horizontally or vertically (not diagonally).
 *
 *     The grid is surrounded by water and contains exactly one island, consisting of one or more connected land cells.
 *
 *     The island has no lakes, meaning no water is enclosed within the island that connects to the surrounding water.
 *
 *     The grid is rectangular, and each cell is a square with a side length 1.
 *
 * Your task is to calculate the perimeter of the island.
 *
 * Constraints:
 *
 *     row ==== grid.length
 *
 *     col ==== grid[i].length
 *
 *     1≤1≤ row, col ≤100≤100
 *
 *     grid[i][j] is 00 or 11.
 *
 *     There is exactly one island in grid.
 */
public class IslandPerimeter {
    public static int islandPerimeter(int[][] grid) {
        int perimeter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 1){
                    perimeter += (i-1 < 0 ||(i-1 >=0 && grid[i-1][j] == 0)) ? 1: 0;
                    perimeter += (i+1 == grid.length ||(i+1 < grid.length && grid[i+1][j] == 0)) ? 1: 0;
                    perimeter += (j-1 < 0 ||(j-1 >= 0 && grid[i][j-1] == 0)) ? 1: 0;
                    perimeter += (j+1 == grid[i].length ||(j+1 >=0 && grid[i][j+1] == 0)) ? 1: 0;
                }
            }
        }
        // Replace this placeholder return statement with your code
        return perimeter;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1]]|4",
            "[[1,0]]|4",
            "[[0,1,0],[1,1,1],[0,1,0]]|12",
            "[[1,1]]|6",
    })
    void test(String gridStr, int expected){
        var pattern = Pattern.compile("(\\[(\\d,)*\\d\\])");
        var matcher = pattern.matcher(gridStr);
        int[][] grid = matcher.results()
                .map(matchResult -> matcher.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).mapToInt(ch -> Integer.parseInt(ch)).toArray();
                        }
                )
                .toArray(int[][]::new);
        Assertions.assertEquals(expected, islandPerimeter(grid));
    }
}
