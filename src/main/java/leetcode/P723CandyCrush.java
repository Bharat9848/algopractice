package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * This question is about implementing a basic elimination algorithm for Candy Crush.
 *
 * Given an m x n integer array board representing the grid of candy where board[i][j] represents the type of candy. A value of board[i][j] == 0 represents that the cell is empty.
 *
 * The given board represents the state of the game following the player's move. Now, you need to restore the board to a stable state by crushing candies according to the following rules:
 *
 *     If three or more candies of the same type are adjacent vertically or horizontally, crush them all at the same time - these positions become empty.
 *     After crushing all candies simultaneously, if an empty space on the board has candies on top of itself, then these candies will drop until they hit a candy or bottom at the same time. No new candies will drop outside the top boundary.
 *     After the above steps, there may exist more candies that can be crushed. If so, you need to repeat the above steps.
 *     If there does not exist more candies that can be crushed (i.e., the board is stable), then return the current board.
 *
 * You need to perform the above rules until the board becomes stable, then return the stable board.
 *
 * Input: board = [[110,5,112,113,114],[210,211,5,213,214],[310,311,3,313,314],[410,411,412,5,414],[5,1,512,3,3],[610,4,1,613,614],[710,1,2,713,714],[810,1,2,1,1],[1,1,2,2,2],[4,1,4,4,1014]]
 * Output: [[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[110,0,0,0,114],[210,0,0,0,214],[310,0,0,113,314],[410,0,0,213,414],[610,211,112,313,614],[710,311,412,613,714],[810,411,512,713,1014]]
 *
 * Example 2:
 *
 * Input: board = [[1,3,5,5,2],[3,4,3,3,1],[3,2,4,5,2],[2,4,4,5,5],[1,4,4,1,1]]
 * Output: [[1,3,0,0,0],[3,4,0,5,2],[3,2,0,3,1],[2,4,0,5,2],[1,4,3,1,1]]
 *
 *
 *
 * Constraints:
 *
 *     m == board.length
 *     n == board[i].length
 *     3 <= m, n <= 50
 *     1 <= board[i][j] <= 2000
 *     sol: Mark and sweep stage- starting from top left corner scan the board left and below. Note down the direction and length of same candy. Then sweep - best strategy is to sweep from bottom left corner and go right then upper rows
 */
class P723CandyCrush {

    int[][] candyCrush(int[][] board) {
        if(board == null || board.length == 0 || board[0].length == 0){ return board;}
        Map<int[], List<Integer>> toDeleteCoordinates =markForDeletion(board);
        while (!toDeleteCoordinates.isEmpty()){
            sweepBoard(toDeleteCoordinates, board);
            toDeleteCoordinates = markForDeletion(board);
        }
        return board;
    }

    private void sweepBoard(Map<int[], List<Integer>> toDeleteCoordinates, int[][] board) {
        Set<Integer> columnAffected = new HashSet<>();
        for (Map.Entry<int[], List<Integer>> toDeleteCoordinate: toDeleteCoordinates.entrySet()) {
            int row = toDeleteCoordinate.getKey()[0];
            int col = toDeleteCoordinate.getKey()[1];
            columnAffected.add(col);
            int horizontalLen = toDeleteCoordinate.getValue().get(0);
            int verticalLen = toDeleteCoordinate.getValue().get(1);
            if(verticalLen >=3){
                for (int i = 0; i < verticalLen; i++) {
                    board[row+i][col] = -1;
                }
            }
            if(horizontalLen >=3){
                for (int i = 0; i < horizontalLen; i++) {
                    board[row][col+i] = -1;
                    columnAffected.add(col+i);
                }
            }
        }
        for (Integer column: columnAffected) {
            int replaceIndex = board.length-1;
            for ( int i = board.length-1; i >= 0 ; i--) {
                if(board[i][column] != -1){
                    board[replaceIndex][column] = board[i][column];
                    replaceIndex--;
                }
            }
            for (int j = replaceIndex; j >=0 ; j--) {
                board[j][column] = 0;
            }
        }
    }

    private Map<int[], List<Integer>> markForDeletion(int[][] board) {
        HashMap<int[], List<Integer>> pointToLength  = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 0){
                    continue;
                }
                int horizontalLen;
                if(j-1 >= 0 && board[i][j] == board[i][j-1] ){
                    horizontalLen = 0;
                }else {
                 horizontalLen = scanLeft(board[i], j);
                }
                int verticalLen;
                if(i-1 >= 0 && board[i][j] == board[i-1][j]){
                    verticalLen = 0;
                }else {
                    verticalLen = scanBelow(board, i, j);
                }
                if(horizontalLen >=3 || verticalLen >= 3){
                    pointToLength.put(new int[]{i,j}, Arrays.asList(horizontalLen, verticalLen) );
                }
            }
        }
        return pointToLength;
    }

    private int scanBelow(int[][] board, int row, int column) {
        int candyNo = board[row][column];
        int sameCount = 1;
        for (int i = row+1; i < board.length; i++) {
            if(board[i][column] == candyNo){
                sameCount++;
            }else{
                break;
            }
        }
        return sameCount;
    }

    private int scanLeft(int[] row, int col) {
        int candyNo = row[col];
        int sameCount = 1;
        for (int i = col+1; i < row.length; i++) {
            if(row[i] == candyNo){
                sameCount++;
            }else{
                break;
            }
        }
        return sameCount;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[110,5,112,113,114],[210,211,5,213,214],[310,311,3,313,314],[410,411,412,5,414],[5,1,512,3,3],[610,4,1,613,614],[710,1,2,713,714],[810,1,2,1,1],[1,1,2,2,2],[4,1,4,4,1014]]|[[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[110,0,0,0,114],[210,0,0,0,214],[310,0,0,113,314],[410,0,0,213,414],[610,211,112,313,614],[710,311,412,613,714],[810,411,512,713,1014]]",
            "[[1,3,5,5,2],[3,4,3,3,1],[3,2,4,5,2],[2,4,4,5,5],[1,4,4,1,1]]|[[1,3,0,0,0],[3,4,0,5,2],[3,2,0,3,1],[2,4,0,5,2],[1,4,3,1,1]]"})
    void test(String boardBefore, String boardAfter){
      int[][] arg =  Arrays.stream(boardBefore.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
      int[][] expected =  Arrays.stream(boardAfter.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        Assert.assertArrayEquals(expected, candyCrush(arg));
    }
}
