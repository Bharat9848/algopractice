package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
Given an integer n and an integer array rounds. We have a circular track which consists of n sectors labeled from 1 to n. A marathon will be held on this track, the marathon consists of m rounds. The ith round starts at sector rounds[i - 1] and ends at sector rounds[i]. For example, round 1 starts at sector rounds[0] and ends at sector rounds[1]

Return an array of the most visited sectors sorted in ascending order.

Notice that you circulate the track in ascending order of sector numbers in the counter-clockwise direction (See the first example).



Example 1:


Input: n = 4, rounds = [1,3,1,2]
Output: [1,2]
Explanation: The marathon starts at sector 1. The order of the visited sectors is as follows:
1 --> 2 --> 3 (end of round 1) --> 4 --> 1 (end of round 2) --> 2 (end of round 3 and the marathon)
We can see that both sectors 1 and 2 are visited twice and they are the most visited sectors. Sectors 3 and 4 are visited only once.
Example 2:

Input: n = 2, rounds = [2,1,2,1,2,1,2,1,2]
Output: [2]
Example 3:

Input: n = 7, rounds = [1,3,5,7]
Output: [1,2,3,4,5,6,7]


Constraints:

2 <= n <= 100
1 <= m <= 100
rounds.length == m + 1
1 <= rounds[i] <= n
rounds[i] != rounds[i + 1] for 0 <= i < m
 */
public class P1450MostVisitedCircularTrack {
    public List<Integer> mostVisited(int n, int[] rounds) {
        int secSize = n;
        int[] secToFreq = new int[secSize];
        int start = wrap(rounds[0] + 1, secSize);
        secToFreq[rounds[0]-1] = 1;
        for(int i = 0; i < rounds.length - 1; i++){
            for(int j = start ; j < start + roundLength(rounds[i], rounds[i+1], secSize); j++){
                int nextsec = wrap(j, secSize) ;
                secToFreq[nextsec-1] += 1;
            }
            start = wrap(rounds[i+1] + 1, secSize);
        }
        return findMaxIndices(secToFreq);
    }

    private int wrap(int sector, int secSize) {
        if(sector <= secSize){
            return sector;
        }else{
            return (sector % secSize);
        }
    }

    private List<Integer> findMaxIndices(int[] secToFreq) {
        List<Integer> ret = new ArrayList<>();
        int maxFreq = secToFreq[0];
        ret.add(0 + 1);
        for (int i = 1; i <secToFreq.length; i++) {
            if(secToFreq[i] > maxFreq){
                maxFreq = secToFreq[i];
                final int sector =  i;
                ret = new ArrayList<>(){{add(sector+1);}};
            }else if( secToFreq[i] == maxFreq){
                ret.add(i+1);
            }
        }
        return ret;
    }

    private int roundLength(int start, int end, int size) {
        return start < end ? end-start: (end + size - start);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ':', value = {
            "4: 1,3,1,2 : 1,2",
            "7: 1,3,5,7: 1,2,3,4,5,6,7",
            "2: 2,1,2,1,2,1,2,1,2: 2"
    })
    void test(int sector, String rounds, String result){
        int[] roundsInt = Arrays.stream(rounds.split(",")).mapToInt(Integer::parseInt).toArray();
        List<Integer> expected = Arrays.stream(result.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> actual = mostVisited(sector, roundsInt);
        System.out.println(actual);
        Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
}
