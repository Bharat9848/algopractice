package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;

/**
 * In an infinite chess board with coordinates from -infinity to +infinity, you have a knight at square [0, 0].
 *
 * A knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a cardinal direction, then one square in an orthogonal direction.
 *
 * Return the minimum number of steps needed to move the knight to the square [x, y]. It is guaranteed the answer exists.
 *
 *
 *
 * Example 1:
 *
 * Input: x = 2, y = 1
 * Output: 1
 * Explanation: [0, 0] → [2, 1]
 *
 * Example 2:
 *
 * Input: x = 5, y = 5
 * Output: 4
 * Explanation: [0, 0] → [2, 1] → [4, 2] → [3, 4] → [5, 5]
 *
 *
 *
 * Constraints:
 *
 *     -300 <= x, y <= 300
 *     0 <= |x| + |y| <= 300
 */
class P1149MinKnightMoves {
    int minKnightMoves(int x, int y) {
        int minSteps = 0;
        boolean found = false;
        List<int[]> level = new LinkedList<>();
        level.add(new int[]{0,0});
        boolean[][] visited = new boolean[602][602];
        visited[0+301][0+301] = true;
        while(!found && !level.isEmpty()){
            List<int[]> nextLevel = new LinkedList<>();
            while (!level.isEmpty()){
                int[] node = level.remove(0);
                int xNext = node[0];
                int yNext = node[1];
                if(xNext == x && yNext == y){
                    found = true;
                    break;
                }
                int[][] nextMoveAdd = new int[][]{ {xNext+2, yNext+1}, {xNext+2, yNext-1},
                        {xNext+1, yNext +2}, { xNext-1, yNext+2},
                        {xNext-2, yNext+1}, {xNext-2, yNext-1},
                        {xNext+1, yNext-2}, {xNext-1, yNext-2} };
                for (int i = 0; i < 8; i++) {
                    if (!visited[nextMoveAdd[i][0] + 301][nextMoveAdd[i][1] + 301]) {
                        visited[nextMoveAdd[i][0] + 301][nextMoveAdd[i][1] + 301] = true;
                        nextLevel.add(nextMoveAdd[i]);
                    }
                }
            }
            if(!found){
                minSteps+=1;
            }
            level = nextLevel;
        }
        return minSteps;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2|1|1",
            "5|5|4"
    })
    void test(int x, int y, int expected){
        Assert.assertEquals(expected, minKnightMoves(x, y));
    }
}
