package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import javax.naming.ldap.UnsolicitedNotification;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
 *
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 *
 *
 *
 * Example 1:
 *
 * Input: grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * Output: 1
 *
 * Example 2:
 *
 * Input: grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * Output: 3
 *

 * Constraints:
 *
 *     m == grid.length
 *     n == grid[i].length
 *     1 <= m, n <= 300
 *     grid[i][j] is '0' or '1'.
 *
 * problem is if we can find the number of components
 */
public class P200NumberOfIslands {
    private enum Visited{EXPLORED, DISCOVERED, UNDISCOVERED}
    int numIslands(char[][] grid) {
        int result = 0;
        Visited[][] visitedArr = new Visited[grid.length][grid[0].length];
        Arrays.stream(visitedArr).forEach(row -> Arrays.fill(row, Visited.UNDISCOVERED));
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j]=='1' && visitedArr[i][j] == Visited.UNDISCOVERED){
                    result +=1;
                    discoverIsland(i, j, grid, visitedArr);
                }
            }
        }
        return result;
    }

    private void discoverIsland(int row, int col, char[][] grid, Visited[][] visitedArr) {
        List<Pair<Integer,Integer>> reachableNodes = new ArrayList<>();
        reachableNodes.add(new Pair<>(row, col));
        while (!reachableNodes.isEmpty()){
            Pair<Integer, Integer> node = reachableNodes.remove(0);
            Integer r = node.getFirst();
            Integer c = node.getSec();
            if(r > 0 && grid[r-1][c]=='1' && visitedArr[r-1][c]  == Visited.UNDISCOVERED){
                visitedArr[r-1][c] = Visited.DISCOVERED;
                reachableNodes.add(new Pair<>(r-1,c));
            }
            if(r < grid.length-1 && grid[r+1][c] == '1' && visitedArr[r+1][c] == Visited.UNDISCOVERED){
                visitedArr[r+1][c] = Visited.DISCOVERED;
                reachableNodes.add(new Pair<>(r+1,c));
            }
            if(c > 0 && grid[r][c-1] == '1' && visitedArr[r][c-1] == Visited.UNDISCOVERED){
                visitedArr[r][c-1] = Visited.DISCOVERED;
                reachableNodes.add(new Pair<>(r,c-1));
            }
            if(c < grid[0].length-1 && grid[r][c+1] == '1' && visitedArr[r][c+1] == Visited.UNDISCOVERED){
                visitedArr[r][c+1] = Visited.DISCOVERED;
                reachableNodes.add(new Pair<>(r,c+1));
            }
            visitedArr[r][c] = Visited.EXPLORED;
        }
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[\"1\",\"1\",\"0\",\"0\",\"0\"],[\"1\",\"1\",\"0\",\"0\",\"0\"],[\"0\",\"0\",\"1\",\"0\",\"0\"],[\"0\",\"0\",\"0\",\"1\",\"1\"]] |3",
            "[[\"1\",\"1\",\"1\",\"1\",\"0\"],[\"1\",\"1\",\"0\",\"1\",\"0\"],[\"1\",\"1\",\"0\",\"0\",\"0\"],[\"0\",\"0\",\"0\",\"0\",\"0\"]]|1",
            "[[\"1\"],[\"1\"],[\"1\"],[\"0\"]]|1",
            "[[\"1\",\"1\",\"1\",\"0\"]]|1"
    })
    void test(String gridStr, int expected){
        char[][] grid =  Arrays.stream(gridStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] charst = s.replaceAll("\"", "").split(",");
                    char[] chars = new char[charst.length];
                    for (int i = 0; i < charst.length; i++) {
                        if("1".equals(charst[i])){
                            chars[i] = '1';
                        }else {
                            chars[i] = '0';
                        }
                    }
                    return chars;
                })
                .toArray(char[][]::new);
        Assert.assertEquals(expected, numIslands(grid));
    }
}
