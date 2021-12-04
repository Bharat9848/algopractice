package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Given two positive integers n and k.
 * A factor of an integer n is defined as an integer i where n % i == 0.
 * Consider a list of all factors of n sorted in ascending order, return the kth factor in this list or return -1 if n has less than k factors.
 * Example 1:
 *
 * Input: n = 12, k = 3
 * Output: 3
 * Explanation: Factors list is [1, 2, 3, 4, 6, 12], the 3rd factor is 3.
 *
 * Example 2:
 *
 * Input: n = 7, k = 2
 * Output: 7
 * Explanation: Factors list is [1, 7], the 2nd factor is 7.
 *
 * Example 3:
 *
 * Input: n = 4, k = 4
 * Output: -1
 * Explanation: Factors list is [1, 2, 4], there is only 3 factors. We should return -1.
 *
 * Example 4:
 *
 * Input: n = 1, k = 1
 * Output: 1
 * Explanation: Factors list is [1], the 1st factor is 1.
 *
 * Example 5:
 *
 * Input: n = 1000, k = 3
 * Output: 4
 * Explanation: Factors list is [1, 2, 4, 5, 8, 10, 20, 25, 40, 50, 100, 125, 200, 250, 500, 1000].
 *
 * Constraints:
 * 35*35 = 175+1050 = 1225
 *
 *     1 <= k <= n <= 1000
 */
 class P1492TheKthFactorOfN {
     int kthFactor(int n, int k) {
        List<Integer> factors = new ArrayList<>();
        int nextFactor = 1;
        factors.add(nextFactor);
        double limit = Math.sqrt(n);
        for (int i = 2; i <= limit; i++) {
            if(n%i==0){
                factors.add(i);
            }
            if(factors.size()==k){
                return factors.get(k-1);
            }
//            nextFactor = genFactor(nextFactor, factors);
        }
        if(factors.size()>=k){
            return factors.get(k-1);
        }
        //TODO  can be calculated without loop n/factor.get(factor.size()-k). Very BAD implementation.
        for (int i = factors.size()-1; i>=0; i--){
            int nextFacor = n / factors.get(i);
            if(nextFacor==factors.get(i)){
                continue;
            }
            factors.add(nextFacor);
            if(factors.size()==k){
                return factors.get(k-1);
            }
        }
        return -1;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
          "12|3|3",
            "7|2|7",
            "4|4|-1",
            "1|1|1",
            "1000|3|4",
            "4|1|1"
    })
    void test(int n, int k, int expected){
        Assert.assertEquals(expected, kthFactor(n,k));
    }
}
