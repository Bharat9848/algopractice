package topological.sort.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.*;

/**
 * Let’s assume that there are a total of nn courses labeled from 00 to n−1n−1. Some courses may have prerequisites. A list of prerequisites is specified such that if Prerequisitesi=a,bPrerequisitesi​=a,b, you must take course bb before course aa.
 *
 * Given the total number of courses nn and a list of the prerequisite pairs, return the course order a student should take to finish all of the courses. If there are multiple valid orderings of courses, then the return any one of them.
 *
 *     Note: There can be a course in the 00 to n−1n−1 range with no prerequisites.
 *
 * Constraints:
 *
 * Let nn be the number of courses.
 *
 *     1≤n≤15001≤n≤1500
 *     0≤0≤ prerequisites.length ≤1000≤1000
 *     prerequisites[i].length ==2==2
 *     0≤a, b<n0≤a, b<n
 *     a≠ba=b
 *     All the pairs [a, b][a, b] are distinct.
 */
public class CourseSchdelueII {
    public static List<Integer> findOrder(int n, int[][] prerequisites) {
        Map<Integer,Integer> incoming = new HashMap<>();
        Map<Integer,List<Integer>> outgoing = new HashMap<>();
        for (int i = 0; i < n; i++) {
            incoming.put(i, 0);
            outgoing.put(i, new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            int after = prerequisites[i][0];
            int before = prerequisites[i][1];
            incoming.put(after, incoming.get(after) +1);
            outgoing.get(before).add(after);
        }

        ArrayList<Integer> result = new ArrayList<>();
        List<Integer> queue = new LinkedList<>();
        for (var nodeToCount :incoming.entrySet()) {
            var node = nodeToCount.getKey();
            var count = nodeToCount.getValue();
            if(count == 0){
                queue.add(node);
            }
        }
        while (!queue.isEmpty()){
            int current = queue.removeFirst();
            result.add(current);
            for(var out: outgoing.get(current)){
                if(incoming.get(out)-1 == 0){
                    queue.add(out);
                }
                incoming.put(out, incoming.get(out) - 1);
            }
            incoming.remove(current);
            outgoing.remove(current);
        }
        if(!incoming.isEmpty()){
            return new ArrayList<>();
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "5|[[1,0],[2,0],[3,1],[4,3]|0,1,2,3,4",
            "5|[[1,0],[2,1],[0,2],[3,1],[0,4]|",
            "7|[[1,0],[1,2],[2,3],[3,4],[4,5]]|0,5,6,4,3,2,1",
            "5|[[1,0],[2,0],[3,1]]|0,4,1,2,3"
    })
    void test(int n, String courseStr, String expectedStr){
        var expected = expectedStr== null ? new ArrayList<>() : Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).boxed().toList();
        Assertions.assertEquals(expected, findOrder(n, StringParser.parseIntArrayString(courseStr, "\\[\\d+,\\d+\\]")));
    }
}
