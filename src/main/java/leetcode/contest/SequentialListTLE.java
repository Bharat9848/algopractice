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
public class SequentialListTLE {
    public List<Integer> sequentialDigits(int low, int high) {
        return IntStream.rangeClosed(low, high).filter(this::isSeq).mapToObj(Integer::valueOf).collect(Collectors.toList());
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
    @ParameterizedTest
    @CsvSource(delimiter = ';',
            value = {"1000;13000;1234,2345,3456,4567,5678,6789,12345",
                    "100;300;123,234"
    }
    )
    void test(int low, int high, String expectedStr){
        Assert.assertTrue(isSeq(12345));
        List<Integer> expected = Arrays.stream(expectedStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        Assert.assertEquals(expected, sequentialDigits(low, high));
    }
}
