package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return the k closest points to the origin (0, 0).
 *
 * The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).
 *
 * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
 *
 *
 *
 * Example 1:
 *
 * Input: points = [[1,3],[-2,2]], k = 1
 * Output: [[-2,2]]
 * Explanation:
 * The distance between (1, 3) and the origin is sqrt(10).
 * The distance between (-2, 2) and the origin is sqrt(8).
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
 *
 * Example 2:
 *
 * Input: points = [[3,3],[5,-1],[-2,4]], k = 2
 * Output: [[3,3],[-2,4]]
 * Explanation: The answer [[-2,4],[3,3]] would also be accepted.
 *
 * Using a max heap of K points
 *
 * Constraints:
 *
 *     1 <= k <= points.length <= 104
 *     -104 < xi, yi < 104
 */
public class P973KClosest {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<Pair<Double, Pair<Integer, Integer>>> maxHeapDistanceToPoints = new PriorityQueue<>((o1, o2) -> -(o1.getFirst().compareTo(o2.getFirst())));
        for (int[] point: points) {
            if(maxHeapDistanceToPoints.size() < k){
                maxHeapDistanceToPoints.offer(new Pair<>(calcDistance(point), new Pair<>(point[0], point[1])));
            }else {
            double distance = calcDistance(point);
            if(maxHeapDistanceToPoints.peek().getFirst() > distance){
                maxHeapDistanceToPoints.remove();
                maxHeapDistanceToPoints.offer(new Pair<>(distance, new Pair<>(point[0], point[1])));
            }}
        }
        return maxHeapDistanceToPoints.stream().map(pair -> new int[]{pair.getSec().getFirst(), pair.getSec().getSec()}).toArray(int[][]::new);
    }

    private double calcDistance(int[] point) {
        int x = point[0];
        int y  = point[1];
        return Math.sqrt((x*x + y*y));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[3,3],[5,-1],[-2,4]]|2|[[3,3],[-2,4]]",
            "[[1,3],[-2,2]]|1|[[-2,2]]",
            "[[1,3],[-2,2],[2,-2]]|2|[[-2,2],[2,-2]]"
    })
    void test(String pointsStr,int k, String expectedPointsStr){
        int[][] points = Arrays.stream(pointsStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                    Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);

        Set<Pair<Integer,Integer>> expected = Arrays.stream(expectedPointsStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                        {
                            String[] nos = s.split(",");
                            return new Pair<>(Integer.parseInt(nos[0]), Integer.parseInt(nos[1]));
                        }
                ).collect(Collectors.toSet());

        int[][] actual = kClosest(points, k);
        Assert.assertEquals(expected, Arrays.stream(actual).map(arr -> new Pair<>(arr[0], arr[1])).collect(Collectors.toSet()));
    }

}
