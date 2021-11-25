package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Assume the following rules are for the tic-tac-toe game on an n x n board between two players:
 *
 *     A move is guaranteed to be valid and is placed on an empty block.
 *     Once a winning condition is reached, no more moves are allowed.
 *     A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
 *
 * Implement the TicTacToe class:
 *
 *     TicTacToe(int n) Initializes the object the size of the board n.
 *     int move(int row, int col, int player) Indicates that the player with id player plays at the cell (row, col) of the board. The move is guaranteed to be a valid move.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["TicTacToe", "move", "move", "move", "move", "move", "move", "move"]
 * [[3], [0, 0, 1], [0, 2, 2], [2, 2, 1], [1, 1, 2], [2, 0, 1], [1, 0, 2], [2, 1, 1]]
 * Output
 * [null, 0, 0, 0, 0, 0, 0, 1]
 *
 * Explanation
 * TicTacToe ticTacToe = new TicTacToe(3);
 * Assume that player 1 is "X" and player 2 is "O" in the board.
 * ticTacToe.move(0, 0, 1); // return 0 (no one wins)
 * |X| | |
 * | | | |    // Player 1 makes a move at (0, 0).
 * | | | |
 *
 * ticTacToe.move(0, 2, 2); // return 0 (no one wins)
 * |X| |O|
 * | | | |    // Player 2 makes a move at (0, 2).
 * | | | |
 *
 * ticTacToe.move(2, 2, 1); // return 0 (no one wins)
 * |X| |O|
 * | | | |    // Player 1 makes a move at (2, 2).
 * | | |X|
 *
 * ticTacToe.move(1, 1, 2); // return 0 (no one wins)
 * |X| |O|
 * | |O| |    // Player 2 makes a move at (1, 1).
 * | | |X|
 *
 * ticTacToe.move(2, 0, 1); // return 0 (no one wins)
 * |X| |O|
 * | |O| |    // Player 1 makes a move at (2, 0).
 * |X| |X|
 *
 * ticTacToe.move(1, 0, 2); // return 0 (no one wins)
 * |X| |O|
 * |O|O| |    // Player 2 makes a move at (1, 0).
 * |X| |X|
 *
 * ticTacToe.move(2, 1, 1); // return 1 (player 1 wins)
 * |X| |O|
 * |O|O| |    // Player 1 makes a move at (2, 1).
 * |X|X|X|
 *
 *
 *
 * Constraints:
 *
 *     2 <= n <= 100
 *     player is 1 or 2.
 *     0 <= row, col < n
 *     (row, col) are unique for each different call to move.
 *     At most n2 calls will be made to move.
 *
 *TODO can be done in o(1) by keeping track of each individual user for each winning scenario
 *
 * Follow-up: Could you do better than O(n2) per move() operation?
 */
public class P348DesignTicTacToe {
    private int winner;
    private int[][] matrix;
    public void init(int n) {
        matrix = new int[n][n];
        winner = 0;
    }

    public int move(int row, int col, int player) {
        if(winner != 0){
            return 0;
        }
        matrix[row][col] = player;
        boolean rowWise = true;
        for (int i = 0; i < matrix[0].length; i++) {
            rowWise &= matrix[row][i] == player;
        }
        boolean columnWise = true;
        for (int i = 0; !rowWise && i < matrix.length; i++) {
            columnWise &= matrix[i][col] == player;
        }
        boolean forwardDiagnol = row == col;
        if(!rowWise && !columnWise && row == col){
            for (int i = 0; i < matrix.length; i++) {
                forwardDiagnol &= matrix[i][i] == player;
            }
        }

        boolean backwardDiagnol = matrix.length -1-row == col;
        if(!rowWise && !columnWise && !forwardDiagnol && matrix.length -1-row == col){
            for (int i = 0; i < matrix.length; i++) {
                backwardDiagnol &= matrix[i][matrix.length -1-i] == player;
            }
        }
         if(rowWise || columnWise ||forwardDiagnol || backwardDiagnol) {
             winner = player;
         }
         return winner;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "TicTacToe,move,move,move,move,move,move,move|3;0,0,1;0,2,2;2,2,1;1,1,2;2,0,1;1,0,2;2,1,1|null,0,0,0,0,0,0,1",
        "TicTacToe,move,move,move,move|2;0,0,1;0,1,2;1,0,1;1,1,2|null,0,0,1,0"
    })
    void test(String opsStr, String argsStr, String expStr){
        String[] operations = opsStr.split(",");
        String[] arguments = argsStr.split(";");
        String[] expected = expStr.split(",");
        P348DesignTicTacToe game = new P348DesignTicTacToe();
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            switch (operation){
                case "TicTacToe": {
                    game.init(Integer.parseInt(arguments[i]));
                    break;
                }
                case "move" : {
                    String[] arg = arguments[i].split(",");
                    Assert.assertEquals(Integer.parseInt(expected[i]), game.move(Integer.parseInt(arg[0]), Integer.parseInt(arg[1]), Integer.parseInt(arg[2])));
                    break;}
                default: throw new RuntimeException("Invalid operation");
            }
        }
    }

}
