package leetcode;

import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;


/**
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
 *
 *     For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 *
 * Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: [0,1]
 * Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1].
 *
 * Example 2:
 *
 * Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
 * Output: [0,2,1,3]
 * Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
 * So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3].
 *
 * Example 3:
 *
 * Input: numCourses = 1, prerequisites = []
 * Output: [0]
 * 0->1 0->2 1->3 2->3
 * Traverse the graph in BFS. check for any new node all the parent course are already in output list.
 */
class P210CourseScheduleII {
    private enum DiscoverEnum{
        EXPLORED, DISCOVERED, UNDISCOVERED
    }
    int[] findOrder(int numCourses, int[][] prerequisites) {
        DiscoverEnum[] visited = new DiscoverEnum[numCourses];
        Arrays.fill(visited, DiscoverEnum.UNDISCOVERED);

        List<List<Integer>> eachComponentOrder = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if(visited[i] == DiscoverEnum.UNDISCOVERED){
                LinkedList<Integer> path = new LinkedList<>();
                try {
                    findOrderForCourse(i, prerequisites, visited, path);
                } catch (IllegalArgumentException e) {
                    return new int[0];
                }
                eachComponentOrder.add(path);
            }
        }

        return eachComponentOrder.stream().flatMap(Collection::stream).mapToInt(Integer::intValue).toArray();
    }

    private void findOrderForCourse(int source, int[][] prerequisites, DiscoverEnum[] visited, List<Integer> path) throws IllegalArgumentException {
        visited[source] = DiscoverEnum.DISCOVERED;
        for (int i = 0; i < prerequisites.length; i++) {
            int[] dependency = prerequisites[i];
            int fromCou = dependency[0];
            int toCou = dependency[1];
            if(fromCou != source){
                continue;
            }
            DiscoverEnum destVisibility = visited[toCou];
            switch (destVisibility){
                case UNDISCOVERED:{
                    findOrderForCourse(toCou, prerequisites, visited, path);
                    break;
                }
                case EXPLORED:{break;}
                case DISCOVERED:{ throw new IllegalArgumentException("cycle detected");}
            }
        }
        path.add(source);
        visited[source] = DiscoverEnum.EXPLORED;
    }



    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1;[];[0]",
            "3;[[0,1]];[1,0,2]",
            "4;[[1,0],[2,1],[3,2],[0,3]];[]",
            "4;[[1,0],[2,0],[3,1],[3,2]];[0,2,1,3]|[0,1,2,3]",
            "4;[[1,0],[2,0],[3,1],[3,2],[3,0];[0,2,1,3]|[0,1,2,3]",
            "4;[[1,0],[2,0],[3,1],[3,2],[0,3];[]",
            "2;[];[0,1]"
    })
    void test(int noOfCourses, String dependGraph, String expectedStr){
        int [][] graph = Arrays.stream(dependGraph.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    int first = Integer.parseInt(s.substring(0, 1));
                    int second = Integer.parseInt(s.substring(s.length() - 1));
                    return new int[]{first, second};
                }).toArray(int[][]::new);
        String[] expectedStrs = expectedStr.split("\\|");
        Set<int[]> expectedArrays = new HashSet<>();
        for (String expected :expectedStrs) {
            if("[]".equals(expected)){
                expectedArrays.add(new int[0]);
            } else {
                expectedArrays.add(Arrays.stream(expected.replace("[","").replace("]","").split(",")).mapToInt(Integer::parseInt).toArray());
            }
        }
        int[] order = findOrder(noOfCourses, graph);
        System.out.println(Arrays.toString(order));
        boolean matched = false;
        for (int[] expected:expectedArrays) {
            matched |= Arrays.equals(expected, order);
        }
        Assert.assertTrue(matched);
    }

}
