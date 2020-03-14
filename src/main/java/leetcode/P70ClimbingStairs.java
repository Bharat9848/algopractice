package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
You are climbing a stair case. It takes n steps to reach to the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Note: Given n will be a positive integer.

Example 1:

Input: 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps

Example 2:

Input: 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
Algo: at a particular step i: it will be sum of solution(i-1) and solution(i-2)

 */
public class P70ClimbingStairs {
    public int climbStairs(int n) {
        int result = 0;
        int solMinusOne=0, solMinusTwo=0;
        for (int i = 1; i <= n; i++) {
            if( i-1 == 0 ) solMinusOne = 1;
            if( i-2 == 0 ) solMinusTwo = 1;
            result = solMinusOne + solMinusTwo;
            solMinusTwo=solMinusOne;
            solMinusOne=result;
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource({
            "3,3",
            "2,2",
            "1,1"
    })
    void test(String n, String sol){
        Assert.assertEquals(Integer.parseInt(sol), climbStairs(Integer.parseInt(n)));
    }
}
