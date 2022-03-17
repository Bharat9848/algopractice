package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class P542DistanceTo0 {
    int[][] updateMatrix(int[][] mat) {
        Set<Pair<Integer, Integer>> current = new HashSet<>();
        int[][] result = new int[mat.length][mat[0].length];
        Arrays.stream(result).forEach(row -> Arrays.fill(row, Integer.MAX_VALUE));
        //init
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if(mat[i][j] == 0){
                    result[i][j] = 0;
                    current.add(new Pair<>(i, j));
                }
            }
        }
        int currentDistance = 1;
        while (!current.isEmpty()){
            Set<Pair<Integer,Integer>> nextLevel = new HashSet<>();
            for(Pair<Integer,Integer> point : current){
                int row = point.getFirst();
                int col = point.getSec();
                int[][] neighbours = new int[][]{{row-1,col},{row, col+1},{row+1,col},{row, col-1}};
                for (int i = 0; i < neighbours.length; i++) {
                    int r = neighbours[i][0];
                    int c = neighbours[i][1];
                    if(r >=0 && r< mat.length && c>=0 && c < mat[0].length && result[r][c] == Integer.MAX_VALUE){
                        nextLevel.add(new Pair<>(r, c));
                    }
                }
            }
            int dist = currentDistance;
            nextLevel.forEach(rc -> {
                int row = rc.getFirst();
                int col = rc.getSec();
                result[row][col] = dist;
            });
            currentDistance++;
            current = nextLevel;
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|',value = {
            "[[0,0,0],[0,1,0],[0,0,0]]|[[0,0,0],[0,1,0],[0,0,0]]",
            "[[0,0,0],[0,1,0],[1,1,1]]|[[0,0,0],[0,1,0],[1,2,1]]",
    })
    void test(String matrixStr, String expectedStr){
        int[][] mat = Arrays.stream(matrixStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        int[][] expected = Arrays.stream(expectedStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertArrayEquals(expected, updateMatrix(mat));
    }
}
