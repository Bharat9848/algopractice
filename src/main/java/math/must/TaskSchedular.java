package math.must;

/**
 * Given a character array tasks, where each character represents a unique task and a positive integer n that represents the cooling period between any two identical tasks, find the minimum number of time units the CPU will need to complete all the given tasks. Each task requires one unit to perform, and the CPU must wait for at least n units of time before it can repeat the same task. During the cooling period, the CPU can perform other tasks or remain idle.
 *
 * Constraints:
 *
 *     1≤1≤ tasks.length ≤1000≤1000
 *
 *     0≤0≤ n ≤100≤100
 *
 *     tasks consists of uppercase English letters
 */

// todo can be done in a more optimized way by thinking holistically. Hint calculate idle time using highest frequency char and try to fill the idle time using less frequency ones. It can be done in 0(n)
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * create max frequency heap of tasks
 * try remove n + 1 node from the heap.
 * if heap is empty before/after n+1 removal then increase the result by empty count; increase count by n+1
 * after each removal decrease frequency by 1 and add it  temp heap.
 * if heap is not empty merge temp heap and new heap and repeat
 */
public class TaskSchedular {
    public static int leastTime(char[] tasks, int n) {
        if( n==0) {
            return tasks.length;
        }
        Map<Character, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < tasks.length; i++) {
            freqMap.putIfAbsent(tasks[i], 0);
            freqMap.put(tasks[i], freqMap.get(tasks[i]) + 1);
        }
        PriorityQueue<FreqNode> heap = new PriorityQueue<>((node1, node2) -> -Integer.compare(node1.freq, node2.freq));
        freqMap.forEach((ch, freq) -> heap.offer(new FreqNode(ch, freq)));
        int minCount = 0;
        while (!heap.isEmpty()){
            int lastHeapSize = heap.size();
            List<FreqNode> tempQueue = new ArrayList<>();
            for (int i = 0; i < n+1 && !heap.isEmpty(); i++) {
                var node = heap.poll();
                if(node.freq-1 > 0){
                    tempQueue.add(new FreqNode(node.ch, node.freq-1));
                }
            }
            for (int i = 0; i < tempQueue.size(); i++) {
                    heap.offer(tempQueue.get(i));
            }
            if(tempQueue.isEmpty() && heap.isEmpty()){
                minCount = minCount + lastHeapSize;
            } else {
                minCount = minCount + n + 1;
            }
        }
        return minCount;
    }

    private static class FreqNode{
        public int freq;
        public char ch;
        public FreqNode(Character ch, int freq){
            this.ch = ch;
            this.freq = freq;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value={
            "A,B,C|1|3",
            "A,B,A,B|1|4",
            "A,A,A,B,B,C,C|1|7",
            "A,B,A,B|0|4",
            "A,B,A,B|2|5",
            "A,A,A,B|3|9",
            "A,A,A,A,B,B,B,C,C,D|2|10"
    })
    void test(String chStr, int n, int expected){
        char[] tasks = chStr.replaceAll(",", "").toCharArray();
        Assertions.assertEquals(expected, leastTime(tasks, n));
    }
}
