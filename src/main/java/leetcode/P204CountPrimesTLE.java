package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
Count the number of prime numbers less than a non-negative number, n.

Example 1:

Input: n = 10
Output: 4
Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
Input: n = 0
Output: 0

Input: n = 1
Output: 0
Constraints:

    0 <= n <= 5 * 106
    2 3 4 5 6 7 8 9 10

HitAndTrial: check each no less than n and count <- TimeLimitExceed

Prime no theory: sieve of eratosthenes
 */
class P204CountPrimesTLE {
    int countPrimes(int n){
        int count=0;
        List<Integer> primes = new LinkedList<>(){{add(2);}};
        for (int i = 3; i < n; i=i+2) {
            if(isPrime(i, primes)) {
                count++;
                primes.add(i);
            }
        }
        return count;
    }

    private boolean isPrime(int no, List<Integer> primes){
        boolean isPrime = true;
        for (int i = 0; isPrime && i < primes.size(); i++) {
            int prime = primes.get(i);
            if(prime>no/2){
                break;
            }
            isPrime = !(no % prime == 0);
        }
        return isPrime;
//        return primes.stream().filter(i-> i < no/2).noneMatch(pr -> no%pr==0);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
//            "1;0",
//            "0;0",
//            "10;4",
//            "17;6",
//            "21;8",
//            "29;9",
//            "2;0",
            "499979;234"
    })
    void test(int n, int expected){
        Assert.assertEquals(expected, countPrimes(n));
    }
}
