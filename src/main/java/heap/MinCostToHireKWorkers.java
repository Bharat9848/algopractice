package heap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * You are given nn workers, each characterized by two attributes:
 *
 *     quality[i]: Represents the work quality of the ithith worker.
 *
 *     wage[i]: Represents the minimum wage expectation of the ithith worker.
 *
 * You want to hire exactly k workers to form a paid group, and you must follow these payment rules:
 *
 *     Wage expectation: Every worker in the group must be paid at least their minimum wage expectation.
 *
 *     Proportional pay: The pay for each worker must be directly proportional to their quality. For example, if one worker’s quality is twice that of another, they must be paid twice as much.
 *
 * Your goal is to determine the least amount of money required to hire exactly k workers while satisfying the above conditions.
 *
 * Constraints
 *
 *     n ==== quality.length ==== wage.length
 *
 *     1≤1≤ k ≤≤ n ≤103≤103
 *
 *     1≤1≤ quality[i], wage[i] ≤103≤103
 */
/*
 @todo
    ## key insight: wage per quality ratio to choose k worker did not work as it was greedy but not lead to global solution.
     Stuck in the logic for a day. Not able to comprehend.
    ## time complexity
    ## space complexity

 */
public class MinCostToHireKWorkers {

    public double minCostToHireWorkers(int[] quality, int[] wage, int k) {
        return 0.0;

    }


    /*What is the output for the input given below?

quality = [4, 5, 6]

wage = [8, 10, 12]

k = 2

     */
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4,6,3,8|24,42,15,72|2|42",
            "4,5,6|8,10,12|2|18",
            "10,10,10|50,60,70|2|120",
            "1|1|1|1"
    })
    void test(String qualityStr, String wageStr, int k, double expected){
        int[] qualities = Arrays.stream(qualityStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] wages = Arrays.stream(wageStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, minCostToHireWorkers(qualities, wages, k));
    }
}
