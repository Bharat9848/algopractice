package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An integer has sequential digits if and only if each digit in the number is one more than the previous digit.
 *
 * Return a sorted list of all the integers in the range [low, high] inclusive that have sequential digits.
 *
 *
 *
 * Example 1:
 *
 * Input: low = 100, high = 300
 * Output: [123,234]
 *
 * Example 2:
 *
 * Input: low = 1000, high = 13000
 * Output: [1234,2345,3456,4567,5678,6789,12345]
 *
 *
 *
 * Constraints:
 *
 *     10 <= low <= high <= 10^9
 */
public class P1291SequentialDigit {

    List<Integer> sequentialDigits(int low, int high) {
        int noOfDigit = calNoOfDigits(low);
        int firstDigit = low/(int)Math.pow(10, noOfDigit-1);
        List<Integer> result = new ArrayList<>();
        int[] firstToNoOfDigits = nextSeqDimension(firstDigit, noOfDigit);
        int curr = genSeqDigits(firstToNoOfDigits[0], firstToNoOfDigits[1]);
        while (curr < low){
            firstToNoOfDigits = nextSeqDimension(firstToNoOfDigits[0]+1, firstToNoOfDigits[1]);
            curr = genSeqDigits(firstToNoOfDigits[0], firstToNoOfDigits[1]);
        }
        while (curr <= high ){
            result.add(curr);
            firstToNoOfDigits = nextSeqDimension(firstToNoOfDigits[0]+1, firstToNoOfDigits[1]);
            curr = genSeqDigits(firstToNoOfDigits[0], firstToNoOfDigits[1]);
        }
        return result;
    }

    private int genSeqDigits(int first, int noOfDigit) {
        int result = 0;
        for (int i = 0; i < noOfDigit; i++) {
            result = result*10 + first;
            first = first + 1;
        }
        return result;
    }

    private int[] nextSeqDimension(int firstDigit, int noOfDigit) {
        int[] result = new int[2];
        if(firstDigit + noOfDigit -1 > 9){
            result[0] = 1;
            result[1] = noOfDigit +1;
        }else{
            result[0] = firstDigit;
            result[1] = noOfDigit;
        }
        return result;
    }

    private int calNoOfDigits(int low) {
        int count = 0;
        while (low > 0) {
            low = low / 10;
            count++;
        }
        return count;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "10|1000|12,23,34,45,56,67,78,89,123,234,345,456,567,678,789",
            "100|300|123,234",
            "1000|13000|1234,2345,3456,4567,5678,6789,12345",
            "100|120|",
            "56|155|56,67,78,89,123",
            "58|155|67,78,89,123",
    })
    void test(int low, int high, String expectedStr){
        if(expectedStr == null){
            Assert.assertEquals(Collections.emptyList(), sequentialDigits(low, high));
        }else
        Assert.assertEquals(Arrays.stream(expectedStr.split(",")).map(Integer::valueOf).collect(Collectors.toList()), sequentialDigits(low, high));
    }
}
