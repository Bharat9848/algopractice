package heap.easy;

/**
 * Given an array of strings, nums, where each string represents an integer without leading zeros, and an integer k, your task is to find and return the string representing the kthth largest integer in the array.
 *
 *     Note: Treat duplicate integers as distinct entities. For instance, if nums =[“2”, “3”, “3”]=[“2”, “3”, “3”], the first largest integer is “3”“3”, the second largest is also “3”“3”, and the third largest is “2”“2”.
 *
 * Constraints:
 *
 *     1<=1<= k <=<= nums.length <=103<=103
 *
 *     1<=1<= nums[i].length <=100<=100
 *
 *     nums[i] consists of only digits.
 *
 *     nums[i] will not have any leading zeros.
 *
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * ## key Insight:
 * Since num[i].length can be a very large number which can not be parsed in a Long, we can use BigInteger class to parse it. Rest of the question is simple and can be done using minHeapMaxNumber
 * ## time: O(100)*nums.length for parsing, N*logK for finding k th largest.
 * ## space
 */

public class FindKLargestNum {
    public String kthLargestInteger (String[] nums, int k)
    {
        if(k > nums.length || k <= 0 ){
            return "";
        }
        PriorityQueue<BigInteger> minHeapMaxNumbers = new PriorityQueue<>(k, Comparator.comparing(a -> a));
        for(int i =0; i< nums.length; i++){
            if(minHeapMaxNumbers.size() < k){
                minHeapMaxNumbers.offer(new BigInteger(nums[i], 10));
            } else {
                if(minHeapMaxNumbers.peek().compareTo(new BigInteger(nums[i], 10)) < 0){
                    minHeapMaxNumbers.poll();
                    minHeapMaxNumbers.offer(new BigInteger(nums[i], 10));
                }
            }
        }
        return minHeapMaxNumbers.peek().toString();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "24,1,13,56|1|56",
            "24,24,1,13,56|3|24",
            "24,1,13|10|",
    })
    void test(String numsStr, int k, String expected){
        if(expected == null){
            expected = "";
        }
        Assertions.assertEquals(expected, kthLargestInteger(numsStr.split(","), k));
    }

}
