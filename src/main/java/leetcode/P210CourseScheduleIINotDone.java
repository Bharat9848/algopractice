package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

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
class P210CourseScheduleIINotDone {
    enum DiscoverEnum{
        EXPLORED, DISCOVERED, UNDISCOVERED
    }
    int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> nodeToChildCourses =  new HashMap<>();
        for (int i = 0; i < prerequisites.length; i++) {
            int[] coursePair = prerequisites[i];
            int child = coursePair[0];
            int parent = coursePair[1];
            nodeToChildCourses.putIfAbsent(parent, new ArrayList<>());
            nodeToChildCourses.get(parent).add(child);
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            Pair<Boolean, Pair<Boolean, List<Integer>>> cycleToNodeResult = allCoursesPossible(numCourses, i, nodeToChildCourses);
            if(cycleToNodeResult.getFirst()){
                return new int[0];
            }else{
                if(cycleToNodeResult.getSec().getFirst()){
                    result = cycleToNodeResult.getSec().getSec();
                    break;
                }else {
                    result.addAll(cycleToNodeResult.getSec().getSec());
                }
            }
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    private Pair<Boolean, Pair<Boolean, List<Integer>>> allCoursesPossible(int totalCourses, int source, Map<Integer, List<Integer>> nodeToChildCourses) {
        DiscoverEnum[] discovery = new DiscoverEnum[totalCourses];
        Arrays.fill(discovery, DiscoverEnum.UNDISCOVERED);
        List<Integer> result = new ArrayList<>();
        List<Integer> discoveredList =  new ArrayList<>();
        discoveredList.add(source);
        discovery[source] = DiscoverEnum.DISCOVERED;
        while (!discoveredList.isEmpty()){
            int parent = discoveredList.remove(0);
            List<Integer> childs = nodeToChildCourses.getOrDefault(parent, Collections.emptyList());
            for (Integer child: childs) {
                switch (discovery[child]){
                    case EXPLORED : return new Pair<>(true, new Pair<>(false, Collections.emptyList()));
                    case UNDISCOVERED: {
                        discoveredList.add(child);
                        discovery[child] = DiscoverEnum.DISCOVERED;
                        break;
                    }
                    case DISCOVERED:
                        break;
                }
            }
            discovery[source] = DiscoverEnum.EXPLORED;
            result.add(parent);
        }
        return new Pair<>(false, new Pair<>(result.size()==totalCourses, result));
    }


    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1;[];[0]",
            "4;[[1,0],[2,1],[3,2],[0,3]];[]",
            "4;[[1,0],[2,0],[3,1],[3,2]];[0,2,1,3]",
            "4;[[1,0],[2,0],[3,1],[3,2],[3,0];[0,2,1,3]",
            "4;[[1,0],[2,0],[3,1],[3,2],[0,3];[]",
            "2;[];[0,1]",
            "3;[[0,1]];[1,0,2]"
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
        int[] result = "[]".equalsIgnoreCase(expectedStr) ? new int[0] : Arrays.stream(expectedStr.split(",")).map(s -> s.replace("[","")
                .replace("]", "").trim()).mapToInt(Integer::parseInt).toArray();
        int[] order = findOrder(noOfCourses, graph);
        System.out.println(Arrays.toString(order));
        Assert.assertEquals(result.length, order.length);
    }
}
