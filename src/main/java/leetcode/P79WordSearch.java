package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.
 *
 *
 *
 * Example 1:
 *
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * Output: true
 *
 * Example 2:
 *
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
 * Output: true
 *
 * Example 3:
 *
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
 * Output: false
 *
 *
 *
 * Constraints:
 *
 *     m == board.length
 *     n = board[i].length
 *     1 <= m, n <= 6
 *     1 <= word.length <= 15
 *     board and word consists of only lowercase and uppercase English letters.
 */
public class P79WordSearch {
    public boolean exist(char[][] board, String word) {
        boolean found = false;
        for (int i = 0; i < board.length && !found; i++) {
            for (int j = 0; j < board[0].length && !found; j++) {
                boolean[][] inCurrentPath = new boolean[board.length][board[0].length];
                if(word.charAt(0) == board[i][j]){
                    found = trySearchFrom(board, i, j, inCurrentPath, word);
                }
            }
        }
        return found;
    }

    private boolean trySearchFrom(char[][] board, int row, int col, boolean[][] inCurrentPath, String word) {
        if(!inCurrentPath[row][col] && board[row][col] ==  word.charAt(0)){
            inCurrentPath[row][col] = true;
            if(word.substring(1).isEmpty()){
                return true;
            }
            boolean found = false;
            if(row > 0) {
                found = trySearchFrom(board, row-1, col, inCurrentPath, word.substring(1));
            }
            if (!found && row < board.length-1){
                found = trySearchFrom(board, row + 1, col, inCurrentPath, word.substring(1));
            }
            if (!found && col > 0){
                found = trySearchFrom(board, row, col-1, inCurrentPath, word.substring(1));
            }
            if(!found && col < board[0].length-1){
                found = trySearchFrom(board, row, col+1, inCurrentPath, word.substring(1));
            }
            if(!found){
                inCurrentPath[row][col] = false;
            }
            return found;
        } else {
            return false;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"[[A,B,C,E],[S,F,C,S],[A,D,E,E]]|ABFCE|true","[[A,B,C,E],[S,F,C,S],[A,D,E,E]]|ABCB|false",
            "[[A,B,C,E],[S,F,C,S],[A,D,E,E]]|SEE|true",
    "[[a]]|a|true"})
    void test(String matrixStr, String word, boolean expected){
        char[][] board = Arrays.stream(matrixStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).reduce((a,b) -> a +b).get().toCharArray()).toArray(char[][]::new);
        Assert.assertEquals(expected, exist(board, word));
    }
}
