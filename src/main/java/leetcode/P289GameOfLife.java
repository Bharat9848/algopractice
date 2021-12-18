package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * According to Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."
 *
 * The board is made up of an m x n grid of cells, where each cell has an initial state: live (represented by a 1) or dead (represented by a 0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):
 *
 *     Any live cell with fewer than two live neighbors dies as if caused by under-population.
 *     Any live cell with two or three live neighbors lives on to the next generation.
 *     Any live cell with more than three live neighbors dies, as if by over-population.
 *     Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 *
 * The next state is created by applying the above rules simultaneously to every cell in the current state, where births and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.
 *
 * Example 1:
 * Input: board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
 * Output: [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
 * Example 2:
 * Input: board = [[1,1],[1,0]]
 * Output: [[1,1],[1,1]]
 *
 *
 *
 * Constraints:
 *
 *     m == board.length
 *     n == board[i].length
 *     1 <= m, n <= 25
 *     board[i][j] is 0 or 1.
 * Follow up:
 *
 *     Could you solve it in-place? Remember that the board needs to be updated simultaneously: You cannot update some cells first and then use their updated values to update other cells.
 *     In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches upon the border of the array (i.e., live cells reach the border). How would you address these problems?
 */
class P289GameOfLife {
    private enum Rules{OVER_POPULATION, UNDER_POPULATION, NO_RULE, REPRODUCTION, NEXT_GENERATION }
     void gameOfLife(int[][] board) {
        boolean[][] visited = new boolean[board.length][board[0].length];

        visitRecursively(board, 0, 0, visited);
        return;
    }

    private void visitRecursively(int[][] board, int row, int col, boolean[][] visited) {
        visited[row][col] = true;
        Rules ruleApplicableToCell = calRuleForCell(board, row, col);
        int[][] neighbours =  new int[][]{{row-1, col-1}, {row-1, col}, {row-1, col+1}, {row, col+1},
                 {row+1, col+1}, {row+1, col}, {row+1, col-1}, {row, col-1}};
        for (int i = 0; i < neighbours.length; i++) {
            int newRow = neighbours[i][0];
            int newCol = neighbours[i][1];
            if(newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length && !visited[newRow][newCol]){
                visitRecursively(board, newRow, newCol, visited);
            }
        }
        switch (ruleApplicableToCell){
            case REPRODUCTION:
            case NEXT_GENERATION: board[row][col] = 1;
            break;
            case UNDER_POPULATION:
            case OVER_POPULATION: board[row][col] = 0;
            break;
            case NO_RULE:
        }
    }

    private Rules calRuleForCell(int[][] board, int row, int col) {
        int[][] neighbours =  new int[][]{{row-1, col-1}, {row-1, col}, {row-1, col+1}, {row, col+1},
                {row+1, col+1}, {row+1, col}, {row+1, col-1}, {row, col-1}};
        int totalLive = 0;
        for (int[] neigh:neighbours){
            int r = neigh[0], c = neigh[1];
            if(r>=0 && r < board.length && c >= 0 && c < board[0].length && board[r][c] == 1){
                totalLive++;
            }
        }
        if(board[row][col] == 1){
            if(totalLive > 3){
                return Rules.OVER_POPULATION;
            }else if( totalLive == 2 || totalLive == 3){
                return Rules.NEXT_GENERATION;
            }else {
                return Rules.UNDER_POPULATION;
            }
        }else {
            if(totalLive == 3){
                return Rules.REPRODUCTION;
            }
        }
        return Rules.NO_RULE;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "[[0,1,0],[0,0,1],[1,1,1],[0,0,0]]|[[0,0,0],[1,0,1],[0,1,1],[0,1,0]]|Typical usecase",
            "[[1,1],[1,0]]|[[1,1],[1,1]]|OverPopulation Use case"
    })
    void test(String boardStr, String expectedStr){
        int[][] board = Arrays.stream(boardStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.trim().split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        int[][] expectedBoard = Arrays.stream(expectedStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.trim().split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        gameOfLife(board);
        Assert.assertArrayEquals(expectedBoard, board);
    }
}
