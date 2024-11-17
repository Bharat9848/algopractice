package searching;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * implement a fast integer square root function that takes
 * in a 32-bit unsigned
 * and returns another 32-bit unsigned integer
 * that is the floor of the square root of the input.
 *
 * x*x = x^2
 *
 * x/2 * x/2 = x^2 to x^2/4
 */
public class SquareRoot {
    int squareRoot(int no) {
        int beg = 2, end = no/2;
        while (beg < end){
            int mid = beg + (end-beg)/2;
            if(mid*mid == no) {
                return mid;
            } else if( mid*mid > no){
              end = mid ;
            } else {
                beg = mid + 1;
            }
        }
        return beg-1;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "81|9",
            "82|9",
            "83|9",
            "7|2",
            "24|4"
    })
    public void test(int no, int expected) {
        Assertions.assertEquals(expected, squareRoot(no));
    }
}
