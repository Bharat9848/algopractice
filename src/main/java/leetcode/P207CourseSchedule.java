package leetcode;

import org.junit.Assert;
import org.junit.Test;

import javax.naming.PartialResultException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
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
Check all component.

a->b a->g b-c c->d c->a e->f


 */
public class P207CourseSchedule {
    private enum Discovery{UNEXPLORED,PARTIALLY,EXPLORED}

    public boolean canFinish(int numCourses, int[][] prerequisites) {
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

    @Test
    public void test121(){
        Assert.assertFalse(canFinish(2, new int[][]{{0,1},{1,0}}));
        Assert.assertTrue(canFinish(2, new int[][]{{1,0}}));
        Assert.assertFalse(canFinish(9, new int[][]{{0,1},{0,8},{1,2},{2,3},{3,4},{3,1}}));
    }
}
