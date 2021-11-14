package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.stream.IntStream;

/*
There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

Example 1:

Input: 2, [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take.
             To take course 1 you should have finished course 0. So it is possible.

Example 2:

Input: 2, [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take.
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.

Note:

    The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
    You may assume that there are no duplicate edges in the input prerequisites.

Approach : its a dependency graph. it there is some cycle then answer is definitly false.
Check all component. If all component are tree then its possible.

a->b a->g b-c c->d c->a e->f


 */
public class P207CourseSchedule {
    boolean canFinishOld(int numCourses, int[][] prerequisites) {
        Discovery[] discovery = new Discovery[numCourses];
        IntStream.range(0, numCourses).forEach(i -> discovery[i] = Discovery.UNEXPLORED);
        boolean result = true;
        for (int i = 0; i < numCourses && discovery[i] == Discovery.UNEXPLORED; i++) {
            result = result && canFinishDPS(i,prerequisites, discovery);
        }
        return result;
    }

    private boolean canFinishDPS(int vertex, int[][] prerequisites, Discovery[] discoveries){
        discoveries[vertex] = Discovery.PARTIALLY;
        boolean result = true;
        for (int i = 0; i < prerequisites.length; i++) {
            if(prerequisites[i][0] == vertex){
                int dest = prerequisites[i][1];
                if(discoveries[dest] == Discovery.EXPLORED){
                    continue;
                }else if(discoveries[dest] == Discovery.PARTIALLY){
                    result =  false;
                }else{
                    result = result && canFinishDPS(dest,prerequisites, discoveries);
                }
            }
        }
        discoveries[vertex] = Discovery.EXPLORED;
        return result;
    }

   /* @Test
    public void test121(){
        Assert.assertFalse(canFinishOld(2, new int[][]{{0,1},{1,0}}));
        Assert.assertTrue(canFinishOld(2, new int[][]{{1,0}}));
        Assert.assertFalse(canFinishOld(9, new int[][]{{0,1},{0,8},{1,2},{2,3},{3,4},{3,1}}));
    }*/

    private enum Discovery{UNEXPLORED,PARTIALLY,EXPLORED}

    boolean canFinish(int numCourses, int[][] prerequisites){
        Discovery[] discoveries = new Discovery[numCourses];
        Arrays.fill(discoveries, Discovery.UNEXPLORED);
        boolean result = true;
        for (int i = 0; i < numCourses; i++) {
            result = result & canFinishCourse(i, prerequisites, discoveries);
        }
        return result;
    }

    private boolean canFinishCourse(int source, int[][] prerequisites, Discovery[] discoveries) {
        boolean result = true;
        discoveries[source] = Discovery.PARTIALLY;
        for (int j = 0; j < prerequisites.length; j++) {
            int[] dependency = prerequisites[j];
            int course = dependency[0];
            int dependsUpon = dependency[1];
            if(course != source){
                continue;
            }else {
                Discovery visited = discoveries[dependsUpon];
                switch (visited){
                    case EXPLORED:{
                        //depends upon can be finished
                        break;
                    }
                    case PARTIALLY://detects cycle
                    {
                        result = false;
                        break;
                    }
                    case UNEXPLORED: {
                        result = result & canFinishCourse(dependsUpon, prerequisites, discoveries);
                        break;
                    }
                }
            }
        }
        discoveries[source] = Discovery.EXPLORED;
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2|[[1,0]]|true",
            "2|[[1,0][0,1]]|false",
            "9|[[0,1],[0,8],[1,2],[2,3],[3,4],[3,1]]|false",
            "3|[]|true",
            "3|[[0,1][1,2]]|true",
            "3|[[0,1][1,2][2,0]]|false"
    })
    void test(int courses, String preReqStr, boolean expected){
        int[][] preRequisite = Arrays.stream(preReqStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    int first = Integer.parseInt(s.substring(0, 1));
                    int second = Integer.parseInt(s.substring(s.length() - 1));
                    return new int[]{first, second};
                }).toArray(int[][]::new);
        Assert.assertEquals(expected, canFinish(courses, preRequisite));
    }
}
