package dynamic.programming;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
(n/k) means the number of ways to choose k things out of n possibilities.

 n  k  0 1 2 3 4 5...
 0     1
 1     1 1
 2     1 2 1
 3     1 3 3 1
 4     1 4 6 4 1
 5     1 5 10 5 1
 */
public class BinomialCoefficientDone {
    public int calculateNormalRecursion(int n, int k){
        if(n == k || n == 0 || k ==0 ) return 1;
        return calculateNormalRecursion( n-1, k-1) + calculateNormalRecursion(n-1, k);
    }

    public int cachedRecursionDriver(int n, int k){
        int[][] cached = new int[n + 1][k + 1];
        initialize(cached);
        return cachedRecursion(cached, n, k);
    }

    private int cachedRecursion(int[][] cached, int n, int k) {
        if(cached[n][k] == 0){
            int up = cachedRecursion(cached, n-1, k);
            int digUp = cachedRecursion(cached, n-1, k-1);
            cached[n][k] = up + digUp;
        }
        return cached[n][k];
    }

    private void initialize(int[][] cached) {
        for(int row=0; row < cached.length; row++ ){
            cached[row][0] = 1;
        }
        for(int dig = 0; dig < cached.length&& dig < cached[0].length; dig++){
            cached[dig][dig] = 1;
        }
    }


    @ParameterizedTest
    @CsvSource({"2,1,2",
                  "3, 1, 3",
    "5,2,10",
    "5,4,5"})
    public void test(String n, String k, String sol){
        Assert.assertEquals( Integer.parseInt(sol), calculateNormalRecursion(Integer.parseInt(n), Integer.parseInt(k)));
        Assert.assertEquals( Integer.parseInt(sol), cachedRecursionDriver(Integer.parseInt(n), Integer.parseInt(k)));
    }

}
