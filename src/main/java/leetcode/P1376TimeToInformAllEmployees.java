package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A company has n employees with a unique ID for each employee from 0 to n - 1. The head of the company is the one with headID.
 *
 * Each employee has one direct manager given in the manager array where manager[i] is the direct manager of the i-th employee, manager[headID] = -1. Also, it is guaranteed that the subordination relationships have a tree structure.
 *
 * The head of the company wants to inform all the company employees of an urgent piece of news. He will inform his direct subordinates, and they will inform their subordinates, and so on until all employees know about the urgent news.
 *
 * The i-th employee needs informTime[i] minutes to inform all of his direct subordinates (i.e., After informTime[i] minutes, all his direct subordinates can start spreading the news).
 *
 * Return the number of minutes needed to inform all the employees about the urgent news.
 *
 * Input: n = 1, headID = 0, manager = [-1], informTime = [0]
 * Output: 0
 * Explanation: The head of the company is the only employee in the company.
 *
 * Example 2:
 *
 * Input: n = 6, headID = 2, manager = [2,2,-1,2,2,2], informTime = [0,0,1,0,0,0]
 * Output: 1
 * Explanation: The head of the company with id = 2 is the direct manager of all the employees in the company and needs 1 minute to inform them all.
 * The tree structure of the employees in the company is shown.
 *     1 <= n <= 105
 *     0 <= headID < n
 *     manager.length == n
 *     0 <= manager[i] < n
 *     manager[headID] == -1
 *     informTime.length == n
 *     0 <= informTime[i] <= 1000
 *     informTime[i] == 0 if employee i has no subordinates.
 *     It is guaranteed that all the employees can be informed.
 */
public class P1376TimeToInformAllEmployees {
    int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        Map<Integer, List<Integer>> adjList = createEmployeeHierarchy(headID, manager);
        Queue<Pair<Integer,Integer>> levelEmployee = new LinkedList<>();
        Queue<Pair<Integer,Integer>> lowestHierarchyEmployee = new LinkedList<>();
        int time = 0;
        levelEmployee.add(new Pair<>(headID, 0));
        while (!levelEmployee.isEmpty()){
            Queue<Pair<Integer,Integer>> nextLevelEmployee = new LinkedList<>();
            for (Pair<Integer,Integer> currentManagerToTime: levelEmployee){
                int currentManager = currentManagerToTime.getFirst();
                int totalTime = currentManagerToTime.getSec();
                if(informTime[currentManager] == 0){
                   time = Math.max(totalTime, time);
                    continue;
                }
                List<Integer> subOrdinates = adjList.get(currentManager);
                for(Integer subOrdinate:subOrdinates){
                    nextLevelEmployee.add(new Pair<>(subOrdinate, totalTime + informTime[currentManager]));
                }
            }
            if(nextLevelEmployee.isEmpty()){
                lowestHierarchyEmployee = levelEmployee;
            }
            levelEmployee = nextLevelEmployee;
        }
        return Math.max(time, lowestHierarchyEmployee.stream().mapToInt(pair-> pair.getSec()).max().orElse(-1));
    }

    private Map<Integer, List<Integer>> createEmployeeHierarchy(int headID, int[] manager) {
        Map<Integer, List<Integer>> empMap = new HashMap<>();
        for (int i = 0; i < manager.length; i++) {
            empMap.putIfAbsent(manager[i] , new ArrayList<>());
            empMap.get(manager[i]).add(i);
        }
        return empMap;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|0|-1|0|0|single employee",
            "6|2|2,2,-1,2,2,2|0,0,1,0,0,0|1| one hierarchy",
            "6|0|-1,0,0,1,2,2|1,2,3,0,0,0|4| two level hierarchy",
            "15|0|-1,0,0,1,1,2,2,3,3,4,4,5,5,6,6|1,1,1,1,1,1,1,0,0,0,0,0,0,0,0|3|3 level hierarchy",
            "11|4|5,9,6,10,-1,8,9,1,9,3,4|0,213,0,253,686,170,975,0,261,309,337|2560| level are not uniform, short depth can have highest"
    })
    void test(int n, int headId, String managerStr, String informTimeStr, int expected, String msg){
        System.out.println(msg);
        int[] manager = Arrays.stream(managerStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] time = Arrays.stream(informTimeStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, numOfMinutes(n, headId, manager, time));
    }
}
