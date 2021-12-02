package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * You are given an n x n integer matrix board where the cells are labeled from 1 to n2 in a Boustrophedon style starting from the bottom left of the board (i.e. board[n - 1][0]) and alternating direction each row.
 *
 * You start on square 1 of the board. In each move, starting from square curr, do the following:
 *
 *     Choose a destination square next with a label in the range [curr + 1, min(curr + 6, n2)].
 *         This choice simulates the result of a standard 6-sided die roll: i.e., there are always at most 6 destinations, regardless of the size of the board.
 *     If next has a snake or ladder, you must move to the destination of that snake or ladder. Otherwise, you move to next.
 *     The game ends when you reach the square n2.
 *
 * A board square on row r and column c has a snake or ladder if board[r][c] != -1. The destination of that snake or ladder is board[r][c]. Squares 1 and n2 do not have a snake or ladder.
 *
 * Note that you only take a snake or ladder at most once per move. If the destination to a snake or ladder is the start of another snake or ladder, you do not follow the subsequent snake or ladder.
 *
 *     For example, suppose the board is [[-1,4],[-1,3]], and on the first move, your destination square is 2. You follow the ladder to square 3, but do not follow the subsequent ladder to 4.
 *
 * Return the least number of moves required to reach the square n2. If it is not possible to reach the square, return -1.
 *
 *
 *
 * Example 1:
 *
 * Input: board = [[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,35,-1,-1,13,-1],[-1,-1,-1,-1,-1,-1],[-1,15,-1,-1,-1,-1]]
 * Output: 4
 * Explanation:
 * In the beginning, you start at square 1 (at row 5, column 0).
 * You decide to move to square 2 and must take the ladder to square 15.
 * You then decide to move to square 17 and must take the snake to square 13.
 * You then decide to move to square 14 and must take the ladder to square 35.
 * You then decide to move to square 36, ending the game.
 * This is the lowest possible number of moves to reach the last square, so return 4.
 *
 * Example 2:
 *
 * Input: board = [[-1,-1],[-1,3]]
 * Output: 1
 *
 * Soution DP : at i I can calculate min move to move to next six square that will be min(M(i+1),M(i) +1) if its snakes then I have to do recalculation from snake tail onwards. As it might change the previous calculations.
 *
 * Constraints:
 *
 *     n == board.length == board[i].length
 *     2 <= n <= 20
 *     grid[i][j] is either -1 or in the range [1, n2].
 *     The squares labeled 1 and n2 do not have any ladders or snakes.
 */
public class P909SnakesAndLadderLotOFEdgeCases {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int[] minSteps = new int[n*n];
        Arrays.fill(minSteps, Integer.MAX_VALUE);
        minSteps[0] = 0;
        int current = 0;
        boolean leftToRight = true;
        for (int i = n-1; i >= 0;i--) {
            int j = leftToRight ? 0: n-1;
            while (true) {
                if(board[i][j] == -1){
                        for (int k = 1; k < 7 && current+k<minSteps.length; k++) {
                        minSteps[current+k] = Math.min(minSteps[current] + 1, minSteps[current+k]);
                    }
                    current++;
                }else{
                    int prevMinSteps = minSteps[board[i][j] - 1];
                    minSteps[board[i][j] - 1] = Math.min(prevMinSteps, minSteps[current]);
                    if(board[i][j] - 1 == (n*n)-1){
                        i = board.length-1;
                        break;
                    }
                    if(board[i][j]-1 < current && minSteps[current] < prevMinSteps){
                        System.out.printf("translation for %d cells", current+1);
                        current = board[i][j]-1;
                        //reset i and j //1 = 6*0+1 6 = 6*1 +0
                        i = n - (board[i][j]-1)/n -1; //21 = 6-3-1
                        j = (leftToRight? (board[i][j]%(n)==0? n: board[i][j]%n): n - (board[i][j]%(n)==0? n: board[i][j]%n))-1; // 21 = 6- 3-1
                        System.out.printf("is row = %d, col = %d\n", i,j);
                    }else{
                        current++;
                    }

                }
                if(leftToRight){
                    if(j + 1 == board[0].length){
                        leftToRight = false;
                        break;
                    }else {
                        j++;
                    }
                }else{
                    if(j-1 < 0){
                        leftToRight = true;
                        break;
                    }else{
                        j--;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(minSteps));
        return minSteps[minSteps.length-1] == Integer.MAX_VALUE ? -1 : minSteps[minSteps.length-1];
    }
//
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[-1,-1],[-1,3]]|1",
            "[[-1,-1,-1],[-1,-1,-1],[-1,-1,-1]]|2",
            "[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,35,-1,-1,13,-1],[-1,-1,-1,-1,-1,-1],[-1,15,-1,-1,-1,-1]]|4",
            "[[1,1,-1],[1,1,1],[-1,1,1]]|-1",
            "[[-1,-1,2,-1],[14,2,12,3],[4,9,1,11],[-1,2,1,16]]|1"
    })
    void test(String matrixStr, int expected){
        int [][] matrix = Arrays.stream(matrixStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                    Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        Assert.assertEquals(expected, snakesAndLadders(matrix));
    }
}
