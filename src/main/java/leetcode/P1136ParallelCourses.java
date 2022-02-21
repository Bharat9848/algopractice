package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * You are given an integer n, which indicates that there are n courses labeled from 1 to n. You are also given an array relations where relations[i] = [prevCoursei, nextCoursei], representing a prerequisite relationship between course prevCoursei and course nextCoursei: course prevCoursei has to be taken before course nextCoursei.
 *
 * In one semester, you can take any number of courses as long as you have taken all the prerequisites in the previous semester for the courses you are taking.
 *
 * Return the minimum number of semesters needed to take all courses. If there is no way to take all the courses, return -1.
 * Input: n = 3, relations = [[1,3],[2,3]]
 * Output: 2
 * Explanation: The figure above represents the given graph.
 * In the first semester, you can take courses 1 and 2.
 * In the second semester, you can take course 3.
 * Input: n = 3, relations = [[1,2],[2,3],[3,1]]
 * Output: -1
 * Explanation: No course can be studied because they are prerequisites of each other.
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 5000
 *     1 <= relations.length <= 5000
 *     relations[i].length == 2
 *     1 <= prevCoursei, nextCoursei <= n
 *     prevCoursei != nextCoursei
 *     All the pairs [prevCoursei, nextCoursei] are unique.
 */
public class P1136ParallelCourses {
    int minimumSemesters(int n, int[][] relations) {
        Map<Integer, Pair<Set<Integer>, Set<Integer>>> adjMap = new HashMap<>();
        for (int j = 1; j <= n; j++) {
            adjMap.putIfAbsent(j, new Pair<>(new HashSet<>(), new HashSet<>()));
        }
        for (int i = 0; i < relations.length; i++) {
            adjMap.get(relations[i][0]).getSec().add(relations[i][1]);
            adjMap.get(relations[i][1]).getFirst().add(relations[i][0]);
        }
        List<Integer> coursesWithZeroDependency = new LinkedList<>();
        List<Integer> nextSemesterCourses = new LinkedList<>();
        int semester = 0;
        for (int i = 1; i <= n; i++) {
            if(adjMap.get(i).getFirst().size() == 0){
                coursesWithZeroDependency.add(i);
            }
        }
        if(coursesWithZeroDependency.isEmpty()){
            return -1;
        }else{
            int remaining = n;
            while (remaining>0){
                remaining--;
                int course = coursesWithZeroDependency.remove(0);
                Set<Integer> dependentCourses = adjMap.get(course).getSec();
                for (Integer cor : dependentCourses) {
                    adjMap.get(cor).getFirst().remove(course);
                    if(adjMap.get(cor).getFirst().size() == 0){
                        nextSemesterCourses.add(cor);
                    }
                }
                if(coursesWithZeroDependency.isEmpty()){
                    if(nextSemesterCourses.isEmpty() && remaining > 0){
                        return -1;
                    }
                    coursesWithZeroDependency = nextSemesterCourses;
                    semester++;
                    nextSemesterCourses = new LinkedList<>();
                }
            }
        }
        return semester;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|[[]]|1",
            "2|[[1,2]]|2",
            "2|[[]]|1",
            "3|[[1,2],[2,3],[3,1]]|-1",
            "3|[[1,3],[2,3]]|2",
            "8|[1,2],[5,2],[6,2],[2,3],[3,4],[4,7],[4,8]|5" // 156,2,3,4,78
    })
    void test(int n, String relationStr, int expected){
        int[][] relations = Arrays.stream(relationStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);

        Assertions.assertEquals(expected, minimumSemesters(n,relations));
    }
}
