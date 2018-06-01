package leetcode;

/**
 * Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

 For example, given the following matrix:

 1 0 1 0 0
 1 0 1 1 1
 1 1 1 1 1
 1 0 0 1 0
 Return 4.
 */
public class P221MaximalSquare_TimeLimit {

    public static void main(String[] args){
        P221MaximalSquare_TimeLimit x = new P221MaximalSquare_TimeLimit();
         System.out.println(x.maximalSquare(new char[][]{{'1','0','1','0','0'},
                {'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','1','1','1'}}));
         System.out.println(x.maximalSquare(new char[][]{{'0','1','1'},
                {'1','1','1'},{'1','1','0'}}));
        System.out.println(x.maximalSquare(new char[][]{{'0','0','0'},
                {'0','0','0'},{'0','1','0'}}));
        System.out.println(x.maximalSquare(new char[][]{{'0','0','0'},
                {'0','0','0'},{'0','0','0'}}));
        System.out.println(x.maximalSquare(new char[][]{{}}));
        System.out.println(x.maximalSquare(new char[][]{{'1'}}));
        System.out.println(x.maximalSquare(new char[][]{{'0'}}));
        System.out.println(x.maximalSquare(new char[0][0]));
        System.out.println(x.maximalSquare(new char[0][]));
    }

    int maximalSquare(char[][] matrix) {
            if (matrix.length<=0){return 0;}
            int minLength = ( matrix.length > matrix[0].length ? matrix[0].length : matrix.length)-1;
            int[][][] mem = new int[matrix.length][matrix[0].length][minLength+1];
            initialiseMem(mem);
            int maxArea = 0;
            for (int i = minLength; i >=0; i--) {
                for (int j = 0 ; j + i<matrix.length; j++) {
                    for (int k = 0; k + i <  matrix[0].length; k++) {
                        findMaxAreaRec(matrix,mem,j,k,i);
                        if(maxArea< mem[j][k][i]){
                            maxArea = mem[j][k][i];
                        }
                    }
                }
            }
            if(maxArea==0){
                return 0;
            }
            return (maxArea) * (maxArea);
    }

    private void initialiseMem(int [][][] mem) {
        for (int i = 0; i < mem.length; i++) {
            for (int j = 0; j < mem[0].length; j++) {
                for (int k = 0; k < mem[0][0].length; k++) {
                    mem[i][j][k] = -1;
                }

            }

        }
    }

    private void findMaxAreaRec(char[][] mat, int[][][] mem, int row, int col, int length) {
        if (length == 0) {
            if (mat[row][col] == '1') {
                mem[row][col][length] = 1;
            } else {
                mem[row][col][length] = 0;
            }
        } else {
            boolean allColAreOne = true;
            for (int i = col; i <= col + length; i++) {
                allColAreOne &= (mat[row][i] == '1');
            }
            boolean allRowAreOne = true;
            for (int i = row; i <= row + length; i++) {
                allRowAreOne &= (mat[i][col] == '1');
            }
            if (mem[row + 1][col + 1][length - 1] == -1) {
                findMaxAreaRec(mat, mem, row + 1, col + 1, length - 1);
            }
            if (allColAreOne && allRowAreOne && mem[row + 1][col + 1][length - 1] == length){
                mem[row][col][length] = mem[row + 1][col + 1][length - 1] + 1;
            } else {
                mem[row][col][length] = mem[row + 1][col + 1][length - 1];
            }

        }

    }

}
