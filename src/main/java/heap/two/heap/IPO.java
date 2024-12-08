package heap.two.heap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * A busy investor with an initial capital, c, needs an automated investment program. They can select k distinct projects from a list of n projects with corresponding capitals requirements and expected profits. For a given project ii, its capital requirement is capitals[i]capitals[i] , and the profit it yields is profits[i]profits[i].
 * The goal is to maximize their cumulative capital by selecting a maximum of k distinct projects to invest in, subject to the constraint that the investor’s current capital must be greater than or equal to the capital requirement of all selected projects.
 *
 * When a selected project from the identified ones is finished, the pure profit from the project, along with the starting capital of that project is returned to the investor. This amount will be added to the total capital held by the investor. Now, the investor can invest in more projects with the new total capital. It is important to note that each project can only be invested once.
 *
 * As a basic risk-mitigation measure, the investor wants to limit the number of projects they invest in. For example, if k is 22, the program should identify the two projects that maximize the investor’s profits while ensuring that the investor’s capital is sufficient to invest in the projects.
 *
 * Overall, the program should help the investor to make informed investment decisions by picking a list of a maximum of k distinct projects to maximize the final profit while mitigating the risk.
 *
 * Constraints:
 *
 *     1≤1≤ k ≤103≤103
 *     0≤0≤ c ≤109≤109
 *     1≤1≤ n ≤103≤103
 *     k ≤≤ n
 *     n ==== profits.length
 *     n ==== capitals.length
 *     0≤0≤ profits[i] ≤104≤104
 *     0≤0≤ capitals[i] ≤109≤109
 */
// TODO: educative
public class IPO {

    public static int maximumCapital(int c, int k, int[] capitals,int[] profits) {
        return -1;
    }

    private static class ProjectNode{
        public int capital, profit;
        public ProjectNode(int c, int p){
            this.capital = c;
            this.profit = p;
        }

    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|1|3|4|4|3",
            "2|2|2|1,5|1,5|3",
            "4|2|1|1,2,2,3|2,4,11,8|14",
            "4|2|1|1,2,2,3|2,4,6,8|11",
            "5|3|2|1,3,4,5,6|1,2,3,4,5|9",
    })
    void test(int n, int k, int c, String capitalStr, String profitStr, int expected){
        int[] capitals = Arrays.stream(capitalStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] profits = Arrays.stream(profitStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, maximumCapital(c, k, capitals, profits));
    }
}
