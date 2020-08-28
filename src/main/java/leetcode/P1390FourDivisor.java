package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
Given an integer array nums, return the sum of divisors of the integers in that array that have exactly four divisors.

If there is no such integer in the array, return 0.



Example 1:

Input: nums = [21,4,7]
Output: 32
Explanation:
21 has 4 divisors: 1, 3, 7, 21
4 has 3 divisors: 1, 2, 4
7 has 2 divisors: 1, 7
The answer is the sum of divisors of 21 only.


Constraints:

1 <= nums.length <= 10^4
1 <= nums[i] <= 10^5
 */
public class P1390FourDivisor {
    public int sumFourDivisors(int[] nums) {
        return IntStream.range(0, nums.length)
                .mapToObj(i -> nums[i])
                .map(num -> divisrsWithLimit(num, 5))
                .peek(System.out::println)
                .filter(divisors -> divisors.size() == 4)
                .flatMap(ds -> ds.stream())
                .mapToInt(no-> no.intValue())
                .sum();
    }

    private List<Integer> divisrsWithLimit(Integer num, int limit) {
        List<Integer> divisors = new ArrayList<>();
        divisors.add(1);
        divisors.add(num);
        IntStream.rangeClosed(2, ((Double) Math.sqrt(num)).intValue())
                .filter(d -> num%d == 0)
                .limit(limit-2)
                .collect(() -> divisors, (dList, div) -> {dList.add(div);
                if(num/div != div) dList.add(num/div);}, (ds1, ds2) -> ds1.addAll(ds2));

        return divisors;
    }

    @Test
    public void testSimple(){
        Assert.assertEquals(32, sumFourDivisors(new int[]{21, 4, 7}));
        Assert.assertEquals(45, sumFourDivisors(new int[]{1,2,3,4,5,6,7,8,9,10}));
    }
}
