package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;

/**
 * You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns, where heights[row][col] represents the height of cell (row, col). You are situated in the top-left cell, (0, 0), and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed). You can move up, down, left, or right, and you wish to find a route that requires the minimum effort.
 *
 * A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.
 *
 * Return the minimum effort required to travel from the top-left cell to the bottom-right cell.
 *
 * Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
 * Output: 2
 * Explanation: The route of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
 * This is better than the route of [1,2,2,2,5], where the maximum absolute difference is 3.
 *
 *  Input: heights = [[1,2,3],[3,8,4],[5,3,5]]
 * Output: 1
 * Explanation: The route of [1,2,3,4,5] has a maximum absolute difference of 1 in consecutive cells, which is better than route [1,3,5,3,5].
 *
 * Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
 * Output: 0
 * Explanation: This route does not require any effort.
 * Constraints:
 *
 *     rows == heights.length
 *     columns == heights[i].length
 *     1 <= rows, columns <= 100
 *     1 <= heights[i][j] <= 106
 */
public class P1631PathWithMinEffort {

    int minimumEffortPath(int[][] heights) {
        int nRows = heights.length;
        int nCol = heights[0].length;
        int[][] shortestPathTillNow = new int[nRows][nCol];
        PriorityQueue<Pair<Integer, Pair<Integer, Integer>>> differenceToCoordinate = new PriorityQueue<>(Comparator.comparingInt(Pair::getFirst));
        Arrays.stream(shortestPathTillNow).forEach(row -> Arrays.fill(row, Integer.MAX_VALUE));
        shortestPathTillNow[0][0] = 0;
        Set<Pair<Integer,Integer>> inTree = new HashSet<>();
        Pair<Integer, Integer> current = new Pair<>(0,0);
        while (!current.equals(new Pair<>(nRows-1, nCol-1))){
            inTree.add(current);
            int row = current.getFirst();
            int col = current.getSec();
            int[][] neighbours = new int[][]{{row-1, col}, {row, col+1}, {row +1, col}, {row, col-1}};
            for (int j = 0; j < neighbours.length; j++) {
                int r = neighbours[j][0];
                int c = neighbours[j][1];
                if(r >= 0 && c >= 0 && r < heights.length && c < heights[0].length){
                    if(!inTree.contains(new Pair<>(r, c)) && shortestPathTillNow[r][c] > Math.max(shortestPathTillNow[row][col], Math.abs(heights[r][c] - heights[row][col]))){
                        differenceToCoordinate.offer(new Pair<>(Math.max(shortestPathTillNow[row][col], Math.abs(heights[r][c] - heights[row][col])), new Pair<>(r,c)));
                        shortestPathTillNow[r][c] = Math.max(shortestPathTillNow[row][col], Math.abs(heights[r][c] - heights[row][col]));
                    }

                }
            }
            current = differenceToCoordinate.remove().getSec();
        }
        return shortestPathTillNow[nRows-1][nCol-1];
    }
    int minimumEffortPathTLE(int[][] heights) {
        long[][] shortest = new long[heights.length][heights[0].length];
        long[][] prevShortest = new long[heights.length][heights[0].length];
        Arrays.stream(shortest).forEach(row -> Arrays.fill(row, Integer.MAX_VALUE));
        Arrays.stream(prevShortest).forEach(row -> Arrays.fill(row, Integer.MAX_VALUE));
        shortest[0][0] = 0;
        prevShortest[0][0] = 0;
        for (int i = 0; i < (heights.length * heights[0].length)-1; i++) {
            for (int row = 0; row < heights.length; row++) {
                for (int col = 0; col < heights[0].length; col++) {
                    int[][] neighbours = new int[][]{{row-1, col}, {row, col+1}, {row +1, col}, {row, col-1}};
                    for (int j = 0; j < neighbours.length; j++) {
                        int r = neighbours[j][0];
                        int c = neighbours[j][1];
                        if(r >= 0 && c >= 0 && r < heights.length && c < heights[0].length){
                            if(shortest[r][c] > Math.max(prevShortest[row][col],Math.abs(heights[row][col]-heights[r][c]))){
                                shortest[r][c] = Math.max(shortest[row][col], Math.abs(heights[row][col]-heights[r][c]));
                            }
                        }
                    }
                }
            }
            prevShortest = shortest;
            shortest = Arrays.stream(shortest).map(row -> Arrays.copyOfRange(row, 0, row.length)).toArray(long[][]::new);
        }

        return (int)shortest[heights.length-1][heights[0].length-1];
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,2,2],[3,8,2],[5,3,5]]|2",
            "[[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]|0",
            "[[1,2,3],[3,8,4],[5,3,5]]|1",
            "[[4,3,4,10,5,5,9,2],[10,8,2,10,9,7,5,6],[5,8,10,10,10,7,4,2],[5,1,3,1,1,3,1,9],[6,4,10,6,10,9,4,6]]|5"
    })
    void test(String heightStr, int expected){
        int[][] heights =  Arrays.stream(heightStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);

        Assertions.assertEquals(expected, minimumEffortPath(heights));
    }
}
