package heap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Given a list of points on a plane, where the plane is a 2-D array with (x, y) coordinates, find the kk closest points to the origin (0,0)(0,0).
 *
 *     Note: Here, the distance between two points on a plane is the Euclidean distance: x2+y2
 *
 *
 *
 * Constraints:
 *
 *     1≤ k ≤ points.length ≤103
 *     −104< x[i], y[i]<104
 */
public class KClosestToOrigin {
    public static List<Point> kClosest(Point[] points, int k) {
        PriorityQueue<Point> minKNearestPoints = new PriorityQueue<>(k, (point1, point2) -> {
            int dist1 = distance(point1.x, point1.y);
            int dist2 = distance(point2.x, point2.y);
            return dist1 < dist2 ? 1 : dist2 == dist1 ? 0: -1;
        });
        for (int i = 0; i < k; i++) {
            minKNearestPoints.offer(points[i]);
        }
        for (int i = k; i < points.length; i++) {
            int currentDist = distance(points[i].x, points[i].y);
            Point topEle = minKNearestPoints.peek();
            if(currentDist < distance(topEle.x, topEle.y)){
                minKNearestPoints.remove();
                minKNearestPoints.offer(points[i]);
            }
        }
        // Replace this placeholder return statement with your code
        return new ArrayList<Point>(minKNearestPoints);
    }

    private static int distance(int x, int y){
        return x*x + y*y;
    }

    private class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
        // Write your code here
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|[[1,3],[-2,2]]|[[-2,2]]",
            "3|[[1,3],[-2,4],[2,-1],[-2,2],[5,-3],[3,-2]]|[[1,3],[2,-1],[-2,2]]"
    })
    void test(int k, String pointsStr, String expectedStr){
        var pattern = Pattern.compile("\\[(-)?\\d+,(-)?\\d+\\]");
        var matcher = pattern.matcher(pointsStr);
        var points = matcher.results().map(m -> matcher.group()).map(str -> {
            String[] comp = str.replace("]", "").replace("[", "").split(",");
            return new Point(Integer.parseInt(comp[0]), Integer.parseInt(comp[1]));
        }).toArray(Point[]::new);
        var pattern1 = Pattern.compile("\\[(-)?\\d+,(-)?\\d+\\]");
        var matcher1 = pattern1.matcher(expectedStr);
        var expected = matcher1.results().map(m -> matcher1.group()).map(str -> {
            String[] comp = str.replace("]", "").replace("[", "").split(",");
            return new Point(Integer.parseInt(comp[0]), Integer.parseInt(comp[1]));
        }).toList();
        Assertions.assertEquals(expected, kClosest(points, k));
    }
}
