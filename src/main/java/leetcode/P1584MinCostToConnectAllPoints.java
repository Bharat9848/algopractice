package leetcode;

import core.ds.UnionFind;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;

/**
 * You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] = [xi, yi].
 *
 * The cost of connecting two points [xi, yi] and [xj, yj] is the manhattan distance between them: |xi - xj| + |yi - yj|, where |val| denotes the absolute value of val.
 *
 * Return the minimum cost to make all points connected. All points are connected if there is exactly one simple path between any two points.
 * Example 1:
 *
 * Input: points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
 * Output: 20
 * Explanation:
 *
 * We can connect the points as shown above to get the minimum cost of 20.
 * Notice that there is a unique path between every pair of points.
 *
 * Example 2:
 *
 * Input: points = [[3,12],[-2,5],[-4,1]]
 * Output: 18
 *
 *
 *
 * Constraints:
 *
 *     1 <= points.length <= 1000
 *     -106 <= xi, yi <= 106
 *     All pairs (xi, yi) are distinct.
 */
class P1584MinCostToConnectAllPoints {

    int minCostConnectPoints(int[][] points) {
        Set<Integer> inTreeVertices = new HashSet<>();
        int nonTreeVerticesToAdd = 0;
        int[] nonTreeToDist = new int[points.length];
        Arrays.fill(nonTreeToDist, Integer.MAX_VALUE);
        nonTreeToDist[0] = 0;
        while(!inTreeVertices.contains(nonTreeVerticesToAdd)){
            inTreeVertices.add(nonTreeVerticesToAdd);
            for (int i = 0; i < points.length; i++) {
                if(i == nonTreeVerticesToAdd){
                    continue;
                }
                int dist = dist(points, nonTreeVerticesToAdd, i);
                if(!inTreeVertices.contains(i) && dist < nonTreeToDist[i]){
                    nonTreeToDist[i] = dist;
                }
            }
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < nonTreeToDist.length; i++) {
                if(!inTreeVertices.contains(i) && nonTreeToDist[i] < min){
                    min = nonTreeToDist[i];
                    nonTreeVerticesToAdd =  i;
                }
            }
        }
        return Arrays.stream(nonTreeToDist).sum();
    }
    int minCostConnectPointsKruskal(int[][] points) {
        UnionFind unionFind = new UnionFind(points.length);
        List<Pair<Integer, Pair<Integer, Integer>>> edges = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                Pair<Integer, Pair<Integer, Integer>> edge = new Pair<>(dist(points, i, j), new Pair<>(i, j));
                edges.add(edge);
            }
        }
        edges.sort(Comparator.comparingInt(Pair::getFirst));
        int result = 0;
        for (int i = 0; i < edges.size(); i++) {
            Pair<Integer, Pair<Integer, Integer>> edgeToVertices = edges.get(i);
            Integer vert1 = edgeToVertices.getSec().getFirst();
            Integer vert2 = edgeToVertices.getSec().getSec();
            if(!unionFind.connected(vert1, vert2)){
                result += edgeToVertices.getFirst();
                unionFind.union(vert1, vert2);
            }
        }
        return result;
    }

    private Integer dist(int[][] points, int i, int j) {
        return Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "[[0,0],[2,2],[3,10],[5,2],[7,0]]|20",
            "[[3,12],[-2,5],[-4,1]]|18"
    })
    void test(String pointsStr, int expected){
        int[][] points = Arrays.stream(pointsStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, minCostConnectPoints(points));
    }
}
