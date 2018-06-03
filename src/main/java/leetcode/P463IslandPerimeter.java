package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water.
 * Grid cells are connected horizontally/vertically (not diagonally).
 * The grid is completely surrounded by water,
 * and there is exactly one island (i.e., one or more connected land cells).
 * The island doesn't have "lakes" (water inside that isn't connected to the water around the island).
 * One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100.
 * Determine the perimeter of the island.

 Example:

 [[0,1,0,0],
 [1,1,1,0],
 [0,1,0,0],
 [1,1,0,0]]

 Answer: 16

 for each cell
 0 neigh = 4
 1 neigh = 3
 2 neigh = 2
 3 neigh = 1
 4 neigh = 0

 * Created by bharat on 3/6/18.
 */
public class P463IslandPerimeter {
    public int islandPerimeter(int[][] grid) {
        int peri = 0;
        int nrow = grid.length;
        int ncol = grid[0].length;
        List<Integer> stack  = new ArrayList<>();
        int[][] visited = new int[nrow][ncol];
        boolean foundSource = false;
        for (int i = 0; i < nrow&& !foundSource; i++) {
            for (int j = 0; j < ncol && !foundSource; j++) {
                if(grid[i][j]==1){
                    stack.add(ncol*i + j);
                    visited[i][j] = 1;
                    foundSource = true;
                }
            }
        }
         while(!stack.isEmpty()) {
             int ele = stack.remove(0);
             int rowIndex = ele / ncol;
             int colIndex = ele % ncol;
             int noOfNeigh = 0;
             if (rowIndex + 1 < nrow && grid[rowIndex + 1][colIndex] == 1 ) {
                 if (visited[rowIndex + 1][colIndex] == 0) {
                     visited[rowIndex + 1][colIndex] = 1;
                     stack.add(0, (rowIndex + 1) * ncol + colIndex);
                 }
                 noOfNeigh = noOfNeigh + 1;
             }
             if (rowIndex - 1 >= 0 && grid[rowIndex - 1][colIndex] == 1 ) {
                 if (visited[rowIndex - 1][colIndex] == 0) {
                     visited[rowIndex - 1][colIndex] = 1;
                     stack.add(0, (rowIndex - 1) * ncol + colIndex);
                 }
                 noOfNeigh = noOfNeigh + 1;
             }
             if (colIndex + 1 < ncol && grid[rowIndex][colIndex + 1] == 1 ) {
                 if (visited[rowIndex][colIndex + 1] == 0) {
                     visited[rowIndex][colIndex + 1] = 1;
                     stack.add(0, (rowIndex) * ncol + colIndex + 1);
                 }

                 noOfNeigh = noOfNeigh + 1;
             }
             if (colIndex - 1 >= 0 && grid[rowIndex][colIndex - 1] == 1) {
                 if (visited[rowIndex][colIndex - 1] == 0) {
                     visited[rowIndex][colIndex - 1] = 1;
                     stack.add(0, (rowIndex) * ncol + colIndex - 1);
                 }
                 noOfNeigh = noOfNeigh + 1;
             }

                 peri = peri + ( 4 - noOfNeigh);
             visited[rowIndex][colIndex] = 2;
         }

        return peri;
    }

    @Test
    public void test(){
        Assert.assertEquals(16,islandPerimeter(new int[][]{{0,1,0,0},{1,1,1,0},{0,1,0,0},{1,1,0,0}}));
        Assert.assertEquals(6,islandPerimeter(new int[][]{{0,1,0,0},{0,1,0,0}}));
        Assert.assertEquals(4,islandPerimeter(new int[][]{{0,1,0,0}}));
    }
}
