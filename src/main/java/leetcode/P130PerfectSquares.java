package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an m x n matrix board containing 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X'.
 *
 * A region is captured by flipping all 'O's into 'X's in that surrounded region.
 *
 * Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
 * Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
 * Explanation: Surrounded regions should not be on the border, which means that any 'O' on the border of the board are not flipped to 'X'. Any 'O' that is not on the border and it is not connected to an 'O' on the border will be flipped to 'X'. Two cells are connected if they are adjacent cells connected horizontally or vertically.
 *
 * Input: board = [["X"]]
 * Output: [["X"]]
 * Constraints:
 *
 *     m == board.length
 *     n == board[i].length
 *     1 <= m, n <= 200
 *     board[i][j] is 'X' or 'O'.
 */
class P130PerfectSquares {
    private enum DiscoveryEnum{
        UNDISCOVER, DISCOVERED, EXPLORED
    }
    public void solve(char[][] board) {
        DiscoveryEnum[][] boardDiscovery =  initDiscoveryBoard(board);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(boardDiscovery[i][j].equals(DiscoveryEnum.EXPLORED)){
                    continue;
                }else if(board[i][j] == 'X'){
                    boardDiscovery[i][j] = DiscoveryEnum.EXPLORED;
                }else {
                    List<Integer> cells = discoverComponent(i, j, board, boardDiscovery);
                    if(!anyTouchesBoundry(cells, board.length, board[0].length)){
                        cells.forEach(cell -> {
                            int row = cell/board[0].length;
                            int col = cell - (row * board[0].length);
                            board[row][col] = 'X';
                        });
                    }
                }
            }
        }
        return;
    }

    private List<Integer> discoverComponent(int row, int col, char[][] board, DiscoveryEnum[][] boardDiscovery) {
        List<Integer> cells = new ArrayList<>();
        List<Integer> discoverCells = new ArrayList<>();
        discoverCells.add(calCellNo(row, col, board[0].length));
        while (!discoverCells.isEmpty()){
            int cell = discoverCells.remove(0);
            int row1 = cell/board[0].length;
            int col1 = cell - (row1 * board[0].length);
            if(col1 - 1 >= 0 && board[row1][col1-1]== 'O' && boardDiscovery[row1][col1-1].equals(DiscoveryEnum.UNDISCOVER)){
                processCell(boardDiscovery, discoverCells, row1, board, col1 - 1);
            }
            if(col1 + 1 < board[0].length && board[row1][col1+1]== 'O' && boardDiscovery[row1][col1+1].equals(DiscoveryEnum.UNDISCOVER)){
                processCell(boardDiscovery, discoverCells, row1, board, col1+1);
            }
            if(row1 - 1 >= 0 && board[row1-1][col1]== 'O' && boardDiscovery[row1-1][col1].equals(DiscoveryEnum.UNDISCOVER)){
                processCell(boardDiscovery, discoverCells, row1-1, board, col1);
            }

            if(row1 + 1 < board.length && board[row1+1][col1]== 'O' && boardDiscovery[row1+1][col1].equals(DiscoveryEnum.UNDISCOVER)) {
                processCell(boardDiscovery, discoverCells, row1+1, board, col1);
            }
            boardDiscovery[row1][col1] = DiscoveryEnum.EXPLORED;
            cells.add(calCellNo(row1,col1,board[0].length));
        }
        return cells;
    }

    private void processCell(DiscoveryEnum[][] boardDiscovery, List<Integer> discoverCells, int row, char[][] board, int col) {
        boardDiscovery[row][col] = DiscoveryEnum.DISCOVERED;
        discoverCells.add(calCellNo(row, col, board[0].length));
    }

    private  int calCellNo(int row, int col, int totalCol){
        return row*totalCol + col;
    }

    private boolean anyTouchesBoundry(List<Integer> cells, int totalRows, int totalCol) {
        return cells.stream().anyMatch(cell -> {
            int row = cell/totalCol;
            int col = cell - row*totalCol ;
            return row==0 || row == totalRows-1 || col ==0 || col == totalCol-1;
        });
    }

    private DiscoveryEnum[][] initDiscoveryBoard(char[][] board) {
        return Arrays.stream(board).map(r -> {
            DiscoveryEnum[] a = new DiscoveryEnum[r.length];
            Arrays.fill(a, DiscoveryEnum.UNDISCOVER);
            return a;
        }).toArray(DiscoveryEnum[][]::new);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[\"X\"]]|[[\"X\"]]",
            "[[\"O\"]]|[[\"O\"]]",
            "[[\"X\",\"X\",\"X\",\"X\"],[\"X\",\"O\",\"O\",\"X\"],[\"X\",\"X\",\"O\",\"X\"],[\"X\",\"O\",\"X\",\"X\"]]|[[\"X\",\"X\",\"X\",\"X\"],[\"X\",\"X\",\"X\",\"X\"],[\"X\",\"X\",\"X\",\"X\"],[\"X\",\"O\",\"X\",\"X\"]]",
            "[[\"X\",\"X\",\"X\",\"X\"],[\"X\",\"O\",\"O\",\"X\"],[\"X\",\"O\",\"O\",\"X\"],[\"X\",\"O\",\"X\",\"X\"]]|[[\"X\",\"X\",\"X\",\"X\"],[\"X\",\"0\",\"0\",\"X\"],[\"X\",\"0\",\"0\",\"X\"],[\"X\",\"O\",\"X\",\"X\"]]"
    })
    void testHappyCase(String input, String output){
        char[][] expected = createCharBoard(output);
        char[][] actual = createCharBoard(input);
        solve(actual);
        Assert.assertArrayEquals(expected, actual);
    }

    private char[][] createCharBoard(String boardStr) {
        return Arrays.stream(boardStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] entries = s.split(",");
                    char[] chars = new char[entries.length];
                    for (int i = 0; i < entries.length; i++) {
                        String cell = entries[i];
                        if("\"X\"".equals(cell)){
                            chars[i] = 'X';
                        }else{
                            chars[i] = 'O';
                        }
                    }
                    return chars;
                }).toArray(char[][]::new);
    }
}
