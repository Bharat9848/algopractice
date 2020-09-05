package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*
We have a grid of 1s and 0s; the 1s in a cell represent bricks.  A brick will not drop if and only if it is directly connected to the top of the grid, or at least one of its (4-way) adjacent bricks will not drop.

We will do some erasures sequentially. Each time we want to do the erasure at the location (i, j), the brick (if it exists) on that location will disappear, and then some other bricks may drop because of that erasure.

Return an array representing the number of bricks that will drop after each erasure in sequence.

Example 1:
Input:
grid = [[1,0,0,0],[1,1,1,0]]
hits = [[1,0]]
Output: [2]
Explanation:
If we erase the brick at (1, 0), the brick at (1, 1) and (1, 2) will drop. So we should return 2.
Example 2:
Input:
grid = [[1,0,0,0],[1,1,0,0]]
hits = [[1,1],[1,0]]
Output: [0,0]
Explanation:
When we erase the brick at (1, 0), the brick at (1, 1) has already disappeared due to the last move. So each erasure will cause no bricks dropping.  Note that the erased brick (1, 0) will not be counted as a dropped brick.


Note:

The number of rows and columns in the grid will be in the range [1, 200].
The number of erasures will not exceed the area of the grid.
It is guaranteed that each erasure will be different from any other erasure, and located inside the grid.
An erasure may refer to a location with no brick - if it does, no bricks drop.
for each hit
    check all the node in left neighbour graph safe
    check all the node in right neighbour graph safe
    check all the node in upper neighbour graph safe
    check all the node in lower neighbour graph safe

    graphSafe ->find all the reachable nodes
    if(any touch top grid) mark all safe else all will fall
 */
 class P803BrickFallingWhenHitTLE {
     int[] hitBricks(int[][] grid, int[][] hits) {
        int[] sol = new int[hits.length];
        for (int i = 0; i < hits.length; i++) {
            int hitR = hits[i][0];
            int hitC = hits[i][1];

            grid[hitR][hitC] = 0;
            int count = 0;
            Set<Integer> nodesUpper = findAllConnected(hitR -1, hitC, grid);
            if(!containTopGridNodes(nodesUpper, grid[0].length)){
                count += nodesUpper.size();
                removeBricks(nodesUpper, grid);
            }
            Set<Integer> nodesRight = findAllConnected(hitR, hitC + 1, grid);
            if(!containTopGridNodes(nodesRight, grid[0].length)){
                count += nodesRight.size();
                removeBricks(nodesRight, grid);
            }
            Set<Integer> nodesBelow = findAllConnected(hitR + 1, hitC, grid);
            if(!containTopGridNodes(nodesBelow, grid[0].length)){
                count += nodesBelow.size();
                removeBricks(nodesBelow, grid);
            }
            Set<Integer> nodesLeft = findAllConnected(hitR, hitC - 1, grid);
            if(!containTopGridNodes(nodesLeft, grid[0].length)){
                count += nodesLeft.size();
                removeBricks(nodesLeft, grid);
            }
            sol[i] = count;
        }
        return sol;
    }

    private void removeBricks(Set<Integer> nodes, int[][] grid) {
        for (int node: nodes) {
            int row = node/grid[0].length;
            int col = node % grid[0].length;
            grid[row][col] = 0;
        }
    }

    private Set<Integer> findAllConnected(int row, int col, int[][] grid) {
        if(row < 0 || row >= grid.length || col < 0  || col >= grid[0].length || grid[row][col] == 0){
            return Collections.emptySet();
        }else{
            Set<Integer> result = new HashSet<>();
            result.add((row*grid[0].length) + col);
            findAllByBDS(grid, row, col, resetDiscoveryMatrix(grid.length, grid[0].length), result);
            return result;
        }
    }

    private Discovery[][] resetDiscoveryMatrix(int nRows, int nCol) {
        Discovery[][] discoveries = new Discovery[nRows][nCol];
        for (int i = 0; i < nRows; i++) {
            Arrays.fill(discoveries[i], Discovery.UNREACH);
        }
        return discoveries;
    }

    enum Discovery {
        UNREACH, DISCOVED, EXPLORED
    }

    private boolean containTopGridNodes(Set<Integer> allNodes, int nCols){
        boolean haveTopGrid = false;
        for (int i = 0; i < nCols && !haveTopGrid; i++) {
          haveTopGrid = allNodes.contains(i);
        }
        return haveTopGrid;
    }
    private void findAllByBDS(int[][] grid, int row, int col, Discovery[][] discoveries, Set<Integer> reachableNodes){
        if(Discovery.EXPLORED.equals(discoveries[row][col]) || Discovery.DISCOVED.equals(discoveries[row][col])){
            return;
        }
        discoveries[row][col] = Discovery.DISCOVED;
        if( row + 1 < grid.length && grid[row + 1][col] == 1){
            int nRow = row + 1, nCol = col;
            if(discoveries[nRow][nCol] == Discovery.UNREACH){
                reachableNodes.add((nRow*(grid[0].length)) + nCol);
                findAllByBDS(grid, nRow, nCol, discoveries, reachableNodes);
            }
        }
        if( row - 1 >= 0  && grid[row - 1][col] == 1){
            int nRow = row - 1, nCol = col;
            if(discoveries[nRow][nCol] == Discovery.UNREACH){
                reachableNodes.add((nRow*(grid[0].length)) + nCol);
                findAllByBDS(grid, nRow, nCol, discoveries, reachableNodes);
            }
        }
        if( col + 1 < grid[0].length && grid[row][col + 1] == 1){
            int nRow = row, nCol = col + 1;
            if(discoveries[nRow][nCol] == Discovery.UNREACH){
                reachableNodes.add((nRow*(grid[0].length)) + nCol);
                findAllByBDS(grid, nRow, nCol, discoveries, reachableNodes);
            }
        }
        if( col - 1 >= 0 && grid[row][col - 1] == 1){
            int nRow = row, nCol = col -1;
            if(discoveries[nRow][nCol] == Discovery.UNREACH){
                reachableNodes.add((nRow*(grid[0].length)) + nCol);
                findAllByBDS(grid, nRow, nCol, discoveries, reachableNodes);
            }
        }
        discoveries[row][col] = Discovery.EXPLORED;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';',value = {
            "[1,0,0,0],[1,1,1,0];[1,0];2",
            "[1,0,0,0],[1,1,0,0];[1,1],[1,0];0,0",
            "[1],[1],[1],[1],[1];[3,0],[4,0],[1,0],[2,0],[0,0];1,0,1,0,0"
    })
    void test(String gridStr, String hitArr, String sol){
        int[] solution = Arrays.stream(sol.split(",")).mapToInt(Integer::parseInt).toArray();
        int[][] hit = parseHit(hitArr);
        int[][] grid = parseGrid(gridStr);
        Assert.assertArrayEquals(solution, hitBricks(grid, hit));
    }

    private int[][] parseHit(String hitArr) {
        String[] rows = hitArr.split("],");
        int[][] hit = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            hit[i] = Arrays.stream(rows[i].replace("[","").replace("]","").split(",")).mapToInt(Integer::parseInt).toArray();
        }
        return hit;
    }

    private int[][] parseGrid(String gridStr) {
        String[] rows = gridStr.split("],");
        int[][] grid = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            grid[i] = Arrays.stream(rows[i].replace("[","").replace("]","").split(",")).mapToInt(Integer::parseInt).toArray();
        }
        return grid;
    }
}
