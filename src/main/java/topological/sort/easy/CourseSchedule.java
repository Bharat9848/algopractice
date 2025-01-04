package topological.sort.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * There are a total of numCourses courses you have to take. The courses are labeled from 0 to numCourses - 1. You are also given a prerequisites array, where prerequisites[i] = [a[i], b[i]] indicates that you must take course b[i] first if you want to take the course a[i]. For example, the pair [1,0][1,0] indicates that to take course 11, you have to first take course 00.
 *
 * Return TRUE if all of the courses can be finished. Otherwise, return FALSE.
 *
 * Constraints:
 *
 *     1≤1≤ numCourses ≤1500≤1500
 *     0≤0≤ prerequisites.length ≤1000≤1000
 *     prerequisites[i].length =2=2
 *     0≤0≤ a[i], b[i] << numCourses
 *     All the pairs prerequisites[i] are unique.
 */
public class CourseSchedule {
    public static boolean canFinish(int numCourses, int[][] prerequisites) {

        Map<Integer, Integer> incoming = new HashMap<>();
        Map<Integer, List<Integer>> outgoing = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            incoming.put(i, 0);
            outgoing.put(i, new ArrayList<>());
        }
        for (int i = 0; i <prerequisites.length; i++) {
            int before = prerequisites[i][1];
            int after = prerequisites[i][0];
            incoming.put(after, incoming.get(after)+1);
            outgoing.get(before).add(after);
        }
        List<Integer> queue = new ArrayList<>();
        for (var nodeToCount: incoming.entrySet()) {
            var node = nodeToCount.getKey();
            var count = nodeToCount.getValue();
            if(count == 0){
                queue.add(node);
            }
        }
        while (!queue.isEmpty()){
            int current = queue.remove(0);
            incoming.remove(current);
            for(var outNode: outgoing.get(current)){
                if(incoming.get(outNode)-1 == 0){
                    queue.add(outNode);
                }
                incoming.put(outNode, incoming.get(outNode)-1);
            }
            outgoing.remove(current);
        }
        return incoming.isEmpty();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3|[[1,0],[2,1]]|true",
            "3|[[1,0],[2,1],[0,2]]|false",
            "3|[[1,0],[2,1],[2,0]]|true",
            "4||true",
    })
    void test(int numCourses, String coursesStr, boolean expected){
        int[][] courses = coursesStr == null ? new int[0][] :StringParser.parseIntArrayString(coursesStr, "\\[\\d+,\\d+\\]");
        Assertions.assertEquals(expected, canFinish(numCourses, courses));
    }
}
