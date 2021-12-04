package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;

/**
 * ou are starving and you want to eat food as quickly as possible. You want to find the shortest path to arrive at any food cell.
 *
 * You are given an m x n character matrix, grid, of these different types of cells:
 *
 *     '*' is your location. There is exactly one '*' cell.
 *     '#' is a food cell. There may be multiple food cells.
 *     'O' is free space, and you can travel through these cells.
 *     'X' is an obstacle, and you cannot travel through these cells.
 *
 * You can travel to any adjacent cell north, east, south, or west of your current location if there is not an obstacle.
 *
 * Return the length of the shortest path for you to reach any food cell. If there is no path for you to reach food, return -1.
 *
 *
 *
 * Example 1:
 *
 * Input: grid = [["X","X","X","X","X","X"],["X","*","O","O","O","X"],["X","O","O","#","O","X"],["X","X","X","X","X","X"]]
 * Output: 3
 * Explanation: It takes 3 steps to reach the food.
 *
 * Example 2:
 *
 * Input: grid = [["X","X","X","X","X"],["X","*","X","O","X"],["X","O","X","#","X"],["X","X","X","X","X"]]
 * Output: -1
 * Explanation: It is not possible to reach the food.
 *
 * Example 3:
 *
 * Input: grid = [["X","X","X","X","X","X","X","X"],["X","*","O","X","O","#","O","X"],["X","O","O","X","O","O","X","X"],["X","O","O","O","O","#","O","X"],["X","X","X","X","X","X","X","X"]]
 * Output: 6
 * Explanation: There can be multiple food cells. It only takes 6 steps to reach the bottom food.
 *
 * Example 4:
 *
 * Input: grid = [["O","*"],["#","O"]]
 * Output: 2
 *
 * Example 5:
 *
 * Input: grid = [["X","*"],["#","X"]]
 * Output: -1
 *
 *
 *
 * Constraints:
 *
 *     m == grid.length
 *     n == grid[i].length
 *     1 <= m, n <= 200
 *     grid[row][col] is '*', 'X', 'O', or '#'.
 *     The grid contains exactly one '*'.
 */
public class P1730ShortestPathToGetFood {
    public int getFood(char[][] grid) {
        List<Pair<Integer, Integer>> level = findSource(grid);
        Set<Pair<Integer, Integer>> visited = new HashSet<>(level);
        boolean foodReached = false;
        int steps = 0;
        while (!level.isEmpty()&& !foodReached){
            List<Pair<Integer,Integer>> nextLevel = new ArrayList<>();
            while (!level.isEmpty() && !foodReached){
                Pair<Integer, Integer> curr = level.remove(0);
                Integer row = curr.getFirst();
                Integer col = curr.getSec();
                int[][] neighbours = new int[][] {{row-1, col}, {row +1, col}, {row, col+1}, {row, col-1}};
                for (int[] neighbour:neighbours){
                    int r = neighbour[0], c = neighbour[1];
                    if(!visited.contains(new Pair<>(r,c)) && r >=0  && r < grid.length && c >= 0
                            && c < grid[0].length && (grid[r][c]=='#' ||grid[r][c] == 'O')){
                        nextLevel.add(new Pair<>(r,c));
                        visited.add(new Pair<>(r,c));
                        if(grid[r][c] == '#'){
                            foodReached = true;
                        }
                    }
                }
            }
            level = nextLevel;
            if(!nextLevel.isEmpty()){
                steps++;
            }
        }
        return foodReached? steps: -1;
    }

    private List<Pair<Integer, Integer>> findSource(char[][] grid) {
        List<Pair<Integer,Integer>> startList = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '*'){
                    startList.add(new Pair<>(i,j));
                    return startList;
                }
            }
        }
        throw new RuntimeException("start not found");
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
       "[[\"X\",\"X\",\"X\",\"X\",\"X\",\"X\"],[\"X\",\"*\",\"O\",\"O\",\"O\",\"X\"],[\"X\",\"O\",\"O\",\"#\",\"O\",\"X\"],[\"X\",\"X\",\"X\",\"X\",\"X\",\"X\"]]|3",
            "[[\"X\",\"*\"],[\"#\",\"X\"]]|-1",
            "[[\"O\",\"*\"],[\"#\",\"O\"]]|2",
            "[[\"X\",\"X\",\"X\",\"X\",\"X\"],[\"X\",\"*\",\"X\",\"O\",\"X\"],[\"X\",\"O\",\"X\",\"#\",\"X\"],[\"X\",\"X\",\"X\",\"X\",\"X\"]]|-1",
            "[[\"X\",\"X\",\"X\",\"X\",\"X\",\"X\",\"X\",\"X\"],[\"X\",\"*\",\"O\",\"X\",\"O\",\"#\",\"O\",\"X\"],[\"X\",\"O\",\"O\",\"X\",\"O\",\"O\",\"X\",\"X\"],[\"X\",\"O\",\"O\",\"O\",\"O\",\"#\",\"O\",\"X\"],[\"X\",\"X\",\"X\",\"X\",\"X\",\"X\",\"X\",\"X\"]]|6"
    })
    void test(String gridStr, int steps){
        char[][] grid = Arrays.stream(gridStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {

                            String[] split = s.split(",");
                            char[] row = new char[split.length];
                            for (int i = 0; i < split.length; i++) {
                                String str = split[i].replaceAll("\"", "");
                                row[i] = str.charAt(0);
                            }
                            return row;
                        }
                ).toArray(char[][]::new);
        Assert.assertEquals(steps, getFood(grid));
    }
}
