package graph.undirected.traversal.bfs;

/**
 * You are given an m×nm×n binary matrix image, where 00 represents a white pixel and 11 represents a black pixel.
 *
 * All black pixels in the matrix form a single connected region, where connectivity is defined by horizontal or vertical adjacency.
 *
 * Given two integers x and y that represent the coordinates of one of the black pixels, write an algorithm to find the area of the smallest axis-aligned rectangle that encloses all the black pixels.
 *
 *     Note: Your solution must have a runtime complexity less than O(m×n)O(m×n).
 *
 * Constraints:
 *
 *     mm ==== image.length
 *
 *     nn == image[i].length
 *
 *     1≤m,n≤1001≤m,n≤100
 *
 *     image[i][j] is either 00 or 11
 *
 *     0≤0≤ x <m<m
 *
 *     0≤0≤ y <n<n
 *
 *     image[x][y] ==1==1
 *
 *     The black pixels in the image only form one component.
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.*;

/**
 * 1. do BFS to find all neighbouring pixels
 * 2. maintain extreme left-right and up-down seen so far
 * 3. calculate area
 *  TODO educate can be done using binary serach
 */
public class MinRectContainingBlackPixels {
    public static int minArea(char[][] image, int x, int y) {
        int leftCol=y, rightCol=y;
        int lowestRow=x, highestRow=x;
        Set<Cell> visited = new HashSet<>();
        visited.add(new Cell(x, y));
        List<Cell> queue = new ArrayList<>();
        queue.add(new Cell(x, y));
        List<Cell> newQueue = new ArrayList<>();
        while(!queue.isEmpty()){
            var current  = queue.remove(0);

            var down = new Cell(current.x + 1, current.y);
            if(down.x < image.length && !visited.contains(down) && image[down.x][down.y] == '1'){
                visited.add(down);
                if(highestRow < down.x){
                    highestRow = down.x;
                }
                newQueue.add(down);
            }

            var up = new Cell(current.x - 1 , current.y);
            if(up.x >= 0 && !visited.contains(up) && image[up.x][up.y] == '1'){
                visited.add(up);
                if(lowestRow > up.x){
                    lowestRow = up.x;
                }
                newQueue.add(up);
            }

            var right = new Cell(current.x, current.y + 1);
            if(right.y < image[0].length && !visited.contains(right) && image[right.x][right.y] == '1'){
                visited.add(right);
                if(rightCol < right.y){
                    rightCol = right.y;
                }
                newQueue.add(right);
            }

            var left = new Cell(current.x, current.y - 1 );
            if(left.y >= 0 && !visited.contains(left) && image[left.x][left.y] == '1'){
                visited.add(left);
                if(leftCol > left.y){
                    leftCol = left.y;
                }
                newQueue.add(left);
            }

            var digUp = new Cell(current.x -1, current.y - 1 );
            if(digUp.y >= 0 && digUp.x >= 0 && !visited.contains(digUp) && image[digUp.x][digUp.y] == '1'){
                visited.add(digUp);
                if(leftCol > digUp.y){
                    leftCol = digUp.y;
                }
                if(lowestRow > digUp.x){
                    lowestRow = digUp.x;
                }
                newQueue.add(digUp);
            }


            var digDown = new Cell(current.x + 1, current.y + 1 );
            if(digDown.x < image.length && digDown.y < image[0].length && !visited.contains(digDown) && image[digDown.x][digDown.y] == '1'){
                visited.add(digDown);
                if(rightCol < digDown.y){
                    rightCol = digDown.y;
                }
                if(highestRow < digDown.x){
                    highestRow = digDown.x;
                }
                newQueue.add(digDown);
            }


            if(queue.isEmpty()){
                queue = newQueue;
            }

        }
        // Replace this placeholder return statement with your code
        return (rightCol - leftCol + 1) * (highestRow - lowestRow+1);
    }

    private static class Cell {
        int x, y;
        public Cell(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter='|', value = {
            "[[0,0,0,0],[0,1,1,1],[0,1,1,1],[0,1,1,1],[0,0,0,0]]|2|1|9",
            "[[0,1],[1,1]]|1|1|4",
            "[[0,1,1],[1,1,1]]|1|1|6",

    })
    void test(String gridStr, int x, int y, int expected){
        char[][] image = StringParser.parseCharArrayString(gridStr, "\\[([0-1],)*[0-1]\\]");
        Assertions.assertEquals(expected, minArea(image, x, y));
    }
}
