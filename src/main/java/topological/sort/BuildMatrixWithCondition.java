package topological.sort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.*;

/**
 * You are given a positive integer kk, and you’re also given two conditions:
 *
 *     A 2D integer array rowConditions of size nn, where rowConditions[i] = [above[i], below[i]]. This means that above[i] must appear in a row above below[i] in the final matrix.
 *
 *     A 2D integer array colConditions of size mm, where colConditions[i] = [left[i], right[i]]. This means that left[i] must appear in a column to the left of right[i] in the final matrix.
 *
 * Both arrays contain integers from 11 to kk.
 *
 * Your task is to build a k×kk×k matrix that contains all the integers from 11 to kk exactly once, while the remaining cells can be filled with zeros.
 *
 * The matrix should also satisfy the following conditions:
 *
 *     For each i from 0 to n−1, the integer above[i] must appear in a row strictly above below[i].
 *
 *     For each i from 0 to m−1, the integer left[i] must appear in a column strictly to the left of right[i].
 *
 * Return any matrix that meets these conditions. If no valid matrix exists, return an empty matrix.
 *
 * Constraints:
 *
 *     2≤k≤400
 *
 *     1≤1≤ rowConditions.length, colConditions.length ≤104≤104
 *
 *     rowConditions[i].length == colConditions[i].length =2=2
 *
 *     1≤1≤ above[i], below[i], left[i], right[i] ≤k≤k
 *
 *     above[i] ≠= below[i]
 *
 *     left[i] ≠= right[i]
 */

/**
 *  if above[i],below[i] is also present left[i]right[i]
 */
public class BuildMatrixWithCondition {
    public static int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {

        int[][] result = new int[k][k];
        Map<Integer, Integer> rowResults = sortOrder(k, rowConditions);
        Map<Integer, Integer> colResults = sortOrder(k, colConditions);
        if(rowResults == null || colResults == null){
            return new int[0][];
        }

        for (int i = 1; i <= k; i++) {
            result[rowResults.get(i)][colResults.get(i)] = i;
        }
        return result;
    }

    private static Map<Integer,Integer> sortOrder(int k, int[][] rowConditions) {
        Map<Integer, Integer> result = new HashMap<>();
        int level = 0;
        Map<Integer, Integer> incoming = new HashMap<>();
        Map<Integer, List<Integer>> outgoing = new HashMap<>();
        for (int i = 1; i <= k; i++) {
            incoming.putIfAbsent(i, 0);
            outgoing.putIfAbsent(i, new ArrayList<>());
        }
        for (int i = 0; i < rowConditions.length; i++) {
            int before = rowConditions[i][0];
            int after = rowConditions[i][1];
            incoming.put(after, incoming.get(after) +  1);
            outgoing.get(before).add(after);
        }
        List<Integer> queue = new ArrayList<>();
        for(var nodeToIncoming: incoming.entrySet()){
            var node = nodeToIncoming.getKey();
            var incomingVal = nodeToIncoming.getValue();
            if(incomingVal == 0){
                queue.add(node);
            }
        }
        while (!queue.isEmpty()){
            var current = queue.remove(0);
            result.put(current, level);
            level++;
            for(var outNodes: outgoing.get(current)){
                incoming.put(outNodes, incoming.get(outNodes) - 1);
                if(incoming.get(outNodes) == 0){
                    queue.add(outNodes);
                }
            }
            incoming.remove(current);
            outgoing.remove(current);
        }
        if(!incoming.isEmpty()){
            return null;
        }

        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3|[[1,2]]|[2,1]]|[[0,0,1],[0,3,0],[2,0,0]]",
            "3|[[1,2],[2,3],[3,1]]|[[2,1]]|",
            "3|[[1,2],[2,3],[3,1]]|[[1,2]]|",
            "3|[[1,2],[2,3]]|[[2,1]]|[[0,0,1],[2,0,0],[0,3,0]]",
            "4|[[1,2],[2,3],[3,4]]|[[1,3],[3,4],[2,1]]|[[0,1,0,0],[2,0,0,0],[0,0,3,0],[0,0,0,4]]",
    })
    void test(int k, String rowConditionStr, String colConditionStr, String exptectedStr){

        int[][] expected = exptectedStr==null ? new int[0][]: StringParser.parseIntArrayString(exptectedStr, "\\[(\\d+,)+\\d+\\]");
        int[][] rowConds = StringParser.parseIntArrayString(rowConditionStr, "\\[\\d+,\\d+\\]");
        int[][] colConds = StringParser.parseIntArrayString(colConditionStr, "\\[\\d+,\\d+\\]");
        int[][] actual = buildMatrix(k, rowConds, colConds);
        System.out.println();
        for (int i = 0; i < actual.length; i++) {
            System.out.println(Arrays.toString(actual[i]));
        }
        System.out.println();
        Assertions.assertArrayEquals(expected, actual);
    }
}
