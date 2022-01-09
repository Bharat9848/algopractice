package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * You have n processes forming a rooted tree structure. You are given two integer arrays pid and ppid, where pid[i] is the ID of the ith process and ppid[i] is the ID of the ith process's parent process.
 *
 * Each process has only one parent process but may have multiple children processes. Only one process has ppid[i] = 0, which means this process has no parent process (the root of the tree).
 *
 * When a process is killed, all of its children processes will also be killed.
 *
 * Given an integer kill representing the ID of a process you want to kill, return a list of the IDs of the processes that will be killed. You may return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: pid = [1,3,10,5], ppid = [3,0,5,3], kill = 5
 * Output: [5,10]
 * Explanation: The processes colored in red are the processes that should be killed.
 *
 * Example 2:
 *
 * Input: pid = [1], ppid = [0], kill = 1
 * Output: [1]
 *
 *
 *
 * Constraints:
 *
 *     n == pid.length
 *     n == ppid.length
 *     1 <= n <= 5 * 104
 *     1 <= pid[i] <= 5 * 104
 *     0 <= ppid[i] <= 5 * 104
 *     Only one process has no parent.
 *     All the values of pid are unique.
 *     kill is guaranteed to be in pid.
 */
class P528KillProcess {
    List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        Map<Integer, List<Integer>> graph =  createAdjMatrix(pid, ppid);
        List<Integer> result = doBfsAndGatherAllDescendent(kill, graph);
        return result;
    }

    private List<Integer> doBfsAndGatherAllDescendent(int kill, Map<Integer, List<Integer>> graph) {
        List<Integer> result = new ArrayList<>();
        List<Integer> level = new LinkedList<>();
        level.add(kill);
        while (!level.isEmpty()){
            List<Integer> subLevel  = new LinkedList<>();
            while (!level.isEmpty()){
                Integer node = level.remove(0);
                result.add(node);
                subLevel.addAll(graph.get(node));
            }
            level = subLevel;
        }
        return result;
    }

    private Map<Integer, List<Integer>> createAdjMatrix(List<Integer> pid, List<Integer> ppid) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < pid.size(); i++) {
            int process = pid.get(i);
            int parentProcess = ppid.get(i);
            graph.putIfAbsent(process, new ArrayList<>());
            graph.putIfAbsent(parentProcess, new ArrayList<>());
            graph.get(parentProcess).add(process);
        }
        return graph;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|0|1|1",
            "1,3,10,5|3,0,5,3|5|5,10"
    })
    void test(String pidStr, String ppidStr, int kill, String expectedStr){
        List<Integer> pids = Arrays.stream(pidStr.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        List<Integer> ppids = Arrays.stream(ppidStr.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        List<Integer> expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        Assert.assertEquals(expected, killProcess(pids, ppids, kill));
    }
}
