package leetcode;
/*
Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.

Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.

Note:

    The order of returned grid coordinates does not matter.
    Both m and n are less than 150.



Example:

Given the following 5x5 matrix:

  Pacific ~   ~   ~   ~   ~
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * Atlantic

Return:

[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).


Approach for each position we have to reach given set of verticies.
I can see if we can reach a already explored matrix matrix then we can color code our matrix based on that
we should go with DPS if any of reachable neighbour we can reach

 */


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class P417PacificAtlanticWaterFlowND {

    private enum Discovery {
        UNDISCOVRED, PARTIAL, FULLY
    }

    private enum Reachable {
        NEITHER, ATLANTIC_REACHABLE, PACIFIC_REACHABLE, BOTH_REACHABLE
    }

    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }

        int nrow = matrix.length;
        int ncol = matrix[0].length;


        Discovery[][] discover = new Discovery[nrow][ncol];
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                discover[i][j] = Discovery.UNDISCOVRED;
            }
        }

        Reachable[][] reachables = new Reachable[nrow][ncol];
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                if ((i == 0 && j == matrix[0].length - 1) || (j == 0 && i == matrix.length - 1)) {
                    reachables[i][j] = Reachable.BOTH_REACHABLE;
                } else if (i == 0 || j == 0) {
                    reachables[i][j] = Reachable.PACIFIC_REACHABLE;
                } else if (i == matrix.length - 1 || j == matrix[0].length - 1) {
                    reachables[i][j] = Reachable.ATLANTIC_REACHABLE;
                } else {
                    reachables[i][j] = Reachable.NEITHER;
                }
            }
        }

        //solve all vertices
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                if (discover[i][j] == Discovery.UNDISCOVRED) {
                    solveFor(i, j, discover, reachables, matrix);
                }
            }
        }
        //result
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                if (reachables[i][j] == Reachable.BOTH_REACHABLE) {
                    result.add(asList(i, j));
                }

            }
        }
        return result;
    }

    private void solveFor(int i, int j, Discovery[][] discover, Reachable[][] reachables, int[][] matrix) {
        discover[i][j] = Discovery.PARTIAL;
        boolean leftReachable = (j - 1 >= 0 && matrix[i][j - 1] <= matrix[i][j]);
        boolean rightReachable = (j + 1 < matrix[0].length && matrix[i][j + 1] <= matrix[i][j]);
        boolean upReachable = (i - 1 >= 0 && matrix[i - 1][j] <= matrix[i][j]);
        boolean downReachable = (i + 1 < matrix.length && matrix[i + 1][j] <= matrix[i][j]);
        //up
        if (i - 1 >= 0 && discover[i - 1][j] == Discovery.UNDISCOVRED && matrix[i - 1][j] <= matrix[i][j]) {
            solveFor(i - 1, j, discover, reachables, matrix);
        }
        //down
        if (i + 1 < matrix.length && discover[i + 1][j] == Discovery.UNDISCOVRED && matrix[i + 1][j] <= matrix[i][j]) {
            solveFor(i + 1, j, discover, reachables, matrix);
        }

        //left
        if (j - 1 >= 0 && discover[i][j - 1] == Discovery.UNDISCOVRED && matrix[i][j - 1] <= matrix[i][j]) {
            solveFor(i, j - 1, discover, reachables, matrix);
        }

        //right
        if (j + 1 < matrix[0].length && discover[i][j + 1] == Discovery.UNDISCOVRED && matrix[i][j + 1] <= matrix[i][j]) {
            solveFor(i, j + 1, discover, reachables, matrix);
        }

        Reachable left = j - 1 >= 0 ? (leftReachable ? reachables[i][j - 1] : Reachable.NEITHER) : Reachable.PACIFIC_REACHABLE;
        Reachable right = j + 1 < matrix[0].length ? (rightReachable ? reachables[i][j + 1] : Reachable.NEITHER) : Reachable.ATLANTIC_REACHABLE;
        Reachable up = i - 1 >= 0 ? (upReachable ? reachables[i - 1][j] : Reachable.NEITHER) : Reachable.PACIFIC_REACHABLE;
        Reachable down = i + 1 < matrix.length ? (downReachable ? reachables[i + 1][j] : Reachable.NEITHER) : Reachable.ATLANTIC_REACHABLE;
        Reachable self = reachables[i][j];
        reachables[i][j] = calNextReachable(left, right, up, down, self);

        discover[i][j] = Discovery.FULLY;

    }

    private Reachable calNextReachable(Reachable left, Reachable right, Reachable up, Reachable down, Reachable self) {
        Reachable result = self;
        if (self == Reachable.BOTH_REACHABLE || left == Reachable.BOTH_REACHABLE || right == Reachable.BOTH_REACHABLE || up == Reachable.BOTH_REACHABLE || down == Reachable.BOTH_REACHABLE) {
            result = Reachable.BOTH_REACHABLE;
        } else if (self == Reachable.ATLANTIC_REACHABLE || left == Reachable.ATLANTIC_REACHABLE || right == Reachable.ATLANTIC_REACHABLE || up == Reachable.ATLANTIC_REACHABLE || down == Reachable.ATLANTIC_REACHABLE) {
            if (self == Reachable.PACIFIC_REACHABLE || left == Reachable.PACIFIC_REACHABLE || right == Reachable.PACIFIC_REACHABLE || up == Reachable.PACIFIC_REACHABLE || down == Reachable.PACIFIC_REACHABLE) {
                result = Reachable.BOTH_REACHABLE;
            } else {
                result = Reachable.ATLANTIC_REACHABLE;
            }
        } else if (self == Reachable.PACIFIC_REACHABLE || left == Reachable.PACIFIC_REACHABLE || right == Reachable.PACIFIC_REACHABLE || up == Reachable.PACIFIC_REACHABLE || down == Reachable.PACIFIC_REACHABLE) {
            result = Reachable.PACIFIC_REACHABLE;
        }
        return result;
    }

    @Test
    public void testEmpty() {
        assertTrue(pacificAtlantic(new int[][]{{}}).isEmpty() );
        assertTrue(pacificAtlantic(new int[][]{}).isEmpty());

    }

    @Test
    public void testGraphWithCycle(){
        assertThat(pacificAtlantic(new int[][]{{89, 90, 91, 101}, {100, 100, 100, 100}, {221, 89, 90, 91}}), containsInAnyOrder(asList(0,3), asList(1,0), asList(1,1), asList(1,2), asList(1,3), asList(2,0)));
    }

    @Test
    public void testGraphWithoutCycle(){
        assertThat(pacificAtlantic(new int[][]{{1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}}),
                containsInAnyOrder( asList(0,4), asList(1,3), asList(1,4), asList(2,2), asList(3,0), asList(3,1), asList(4,0)));
    }
}
