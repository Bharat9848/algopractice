package matrix.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

/**
 * Given a matrix grid of size m ∗ nm ∗ n, where each row and column is sorted in non-increasing order, find and return the total count of negative numbers in the matrix.
 *
 * Constraints:
 *
 *     mm ==== grid.length
 *
 *     nn ==== grid[i].length
 *
 *     1≤1≤ mm, nn ≤1000≤1000
 *
 *     −100≤−100≤ grid[i][j] ≤100≤100
 */

/**
 * TODO Could be optimized to O(m + n) instead of using binary search think something else.
 */
public class CountNegativeSortedArray {
    public static int countNegatives(int[][] grid) {
        int count = 0;
        int m = grid.length, n = grid[0].length;
        if(m != Math.min(m, n)){
            count += countColwise(grid);
        } else {
            count += countRowwise(grid);
        }
        return count;
    }

    private static int countRowwise(int[][] grid) {
        int count = 0;
        for(int i = 0; i < grid.length; i++){
            int index = doBinarySearchRow(grid, i, 0, grid[0].length-1, -1);
            if(index < grid[0].length ) {
                count += grid[0].length - index;
            }
        }
        return count;
    }

    private static int doBinarySearchRow(int[][] grid, int row, int begin, int end, int target) {
        while(begin < end){
            int mid = begin + (end - begin) / 2;
            int midEle = grid[row][mid];
            if(midEle == target){
                if (mid - 1 < 0 || grid[row][mid-1] > target){
                    return mid;
                } else {
                    end = mid;
                }
            } else if (midEle < target){
                end =  mid;
            } else {
                begin = mid + 1;
            }
        }
        return grid[row][begin] > target ? begin + 1: begin;
    }

    private static int countColwise(int[][] grid) {
        int count = 0;
        for(int i = 0; i < grid[0].length; i++){
            int index = doBinarySearchColumn(grid, i, 0, grid.length-1, -1);
            if(index < grid.length ) {
                count += grid.length - index;
            }
        }
        return count;
    }

    private static int doBinarySearchColumn(int[][] grid, int col, int begin, int end, int target) {
        while(begin < end){
            int mid = begin + (end - begin) / 2;
            int midEle = grid[mid][col];
            if(midEle == target){
                if(mid - 1 < 0 || grid[mid-1][col] > target){
                    return mid;
                } else {
                    end = mid;
                }

            } else if (midEle < target){
                end =  mid;
            } else {
                begin = mid + 1;
            }
        }
        return grid[begin][col] > target ? begin + 1: begin;

    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[-1,-1,-1]]|3",
            "[[-1],[-1],[-1]]|3",
            "[[-1]]|1",
            "[[1,-1]]|1",
            "[[2,1]]|0",
        "[[3,2,-1],[2,-1,-2],[-1,-3,-4]]|6",
            "[[3,2,-1,-4],[2,-1,-2,-5],[-1,-3,-4,-6]]|9",
            "[[3,2,-1],[2,-1,-3],[-1,-2,-4],[-4,-5,-6]]|9"
    })
    void test(String gridStr, int expected){
        Assertions.assertEquals(expected, countNegatives(StringParser.parseIntArrayString(gridStr, "\\[(-?\\d+,)*-?\\d+\\]")));
    }

}
