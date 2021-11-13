package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * An image is represented by an m x n integer grid image where image[i][j] represents the pixel value of the image.
 *
 * You are also given three integers sr, sc, and newColor. You should perform a flood fill on the image starting from the pixel image[sr][sc].
 *
 * To perform a flood fill, consider the starting pixel, plus any pixels connected 4-directionally to the starting pixel of the same color as the starting pixel, plus any pixels connected 4-directionally to those pixels (also with the same color), and so on. Replace the color of all of the aforementioned pixels with newColor.
 *
 * Return the modified image after performing the flood fill.
 *
 * Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, newColor = 2
 * Output: [[2,2,2],[2,2,0],[2,0,1]]
 * Explanation: From the center of the image with position (sr, sc) = (1, 1) (i.e., the red pixel), all pixels connected by a path of the same color as the starting pixel (i.e., the blue pixels) are colored with the new color.
 * Note the bottom corner is not colored 2, because it is not 4-directionally connected to the starting pixel.
 *
 * Example 2:
 *
 * Input: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, newColor = 2
 * Output: [[2,2,2],[2,2,2]]
 * Constraints:
 *
 * Solution would be the MST from the starting point - DFS
 *     m == image.length
 *     n == image[i].length
 *     1 <= m, n <= 50
 *     0 <= image[i][j], newColor < 216
 *     0 <= sr < m
 *     0 <= sc < n
 */
class P733FloodFill {
    private enum DiscoverEnum{
        EXPLORED, DISCOVERED, UNDISCOVERED
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        Pair<Integer,Integer> current = new Pair<>(sr, sc);
        DiscoverEnum[][] visited = new DiscoverEnum[image.length][image[0].length];
        Arrays.stream(visited).forEach(row -> Arrays.fill(row, DiscoverEnum.UNDISCOVERED));
        int oldColor = image[sr][sc];
        image[sr][sc] = newColor;
        List<Pair<Integer, Integer>> stack = new LinkedList<>();
        stack.add(current);
        while (!stack.isEmpty()){
            Pair<Integer, Integer> node = stack.get(0);
            Integer row = node.getFirst();
            Integer col = node.getSec();
            if(col>0 && visited[row][col-1] == DiscoverEnum.UNDISCOVERED && image[row][col-1] == oldColor){
                visited[row][col-1] = DiscoverEnum.DISCOVERED;
                stack.add(new Pair<>(row, col-1));
                continue;
            }
            if(col< image[0].length-1 && visited[row][col+1] == DiscoverEnum.UNDISCOVERED && image[row][col+1] == oldColor){
                visited[row][col+1] = DiscoverEnum.DISCOVERED;
                stack.add(new Pair<>(row, col+1));
                continue;
            }
            if(row > 0 && visited[row-1][col] == DiscoverEnum.UNDISCOVERED && image[row-1][col] == oldColor){
                visited[row-1][col] = DiscoverEnum.DISCOVERED;
                stack.add(new Pair<>(row-1, col));
                continue;
            }
            if(row < image.length-1 && visited[row+1][col] == DiscoverEnum.UNDISCOVERED && image[row+1][col] == oldColor){
                visited[row+1][col] = DiscoverEnum.DISCOVERED;
                stack.add(new Pair<>(row+1, col));
                continue;
            }
            visited[row][col] = DiscoverEnum.EXPLORED;
            image[row][col] = newColor;
            stack.remove(0);
        }
        return image;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0,0,0],[0,0,0]]|0|0|2|[[2,2,2],[2,2,2]]",
            "[[1,1,1],[1,1,0],[1,0,1]]|1|1|2|[[2,2,2],[2,2,0],[2,0,1]]",
            "[[1,0,1]]|0|0|2|[[2,0,1]]",
            "[[1],[0],[1]]|0|0|2|[[2],[0],[1]]",
            "[[1]]|0|0|2|[[2]]"
    })
    void testFloodFill(String inputStr,int srow, int col, int newColor, String expectedStr){
        int[][] image = Arrays.stream(inputStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(row -> Arrays.stream(row.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        int[][] newImage = Arrays.stream(expectedStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(row -> Arrays.stream(row.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assert.assertArrayEquals(newImage, floodFill(image, srow, col, newColor));
    }
}
