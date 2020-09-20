package leetcode.contest;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
An integer has sequential digits if and only if each digit in the number is one more than the previous digit.

Return a sorted list of all the integers in the range [low, high] inclusive that have sequential digits.



Example 1:

Input: low = 100, high = 300
Output: [123,234]
Example 2:

Input: low = 1000, high = 13000
Output: [1234,2345,3456,4567,5678,6789,12345]


Constraints:

10 <= low <= high <= 10^9
 */
public class SequentialList {
    public List<Integer> sequentialDigits(int low, int high) {
        int start = getSeq(low);
        return IntStream.iterate(start , i -> i <= high , last -> genSeqNo(noOfDigits(last), last))
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());
    }

    private int getSeq(int seed) {
        int noOfDigit = noOfDigits(seed);
        int highestDigit = seed/((int)Math.pow(10, noOfDigit-1));
        int lastSeq = getSeq(highestDigit-1, noOfDigit);
        if(seed <= lastSeq) { return lastSeq; }else { return genSeqNo(noOfDigit, lastSeq);}
    }

    private int noOfDigits(int last) {
        int count=0;
        while (last>0){
            count++;
            last = last/10;
        }
        return count;
    }

    private boolean isSeq(int no) {
        boolean result = true;
        int last = no%10 + 1;
        while (no > 0 && result){
            int current = no%10;
            no = no/10;
            result = current + 1 == last;
            last = current;
        }
        return result;
    }
    
    private int genSeqNo(int noOfDigit, int lastSeqNo){
        int highDigit = lastSeqNo / (int)Math.pow(10, noOfDigit-1);
        return getSeq(highDigit, noOfDigit);
    }

    private int getSeq(int highDigit, int noOfDigit) {
        if(noOfDigit == 10) return Integer.MAX_VALUE;
        int result = 0;
        int start = highDigit;
        for (int i = 0; i < noOfDigit; i++) {
            if(start == 10){
                break;
            }else{
                start = start + 1;
                result = (result*10)+ start ;
            }
        }
        if(start == 10){
            result = getSeq(0, noOfDigit+1);
        }
        return result;
    }


    @ParameterizedTest
    @CsvSource(delimiter = ';',
            value = {
            "1000;13000;1234,2345,3456,4567,5678,6789,12345",
                    "123;300;123,234",
                    "23456789;1000000000;23456789,123456789"
    }
    )
    void test(int low, int high, String expectedStr){
        List<Integer> expected = Arrays.stream(expectedStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        Assert.assertEquals(expected, sequentialDigits(low, high));
        System.out.println(sequentialDigits(10,100000000));
        System.out.println(getSeq(0, 9));
    }
}
