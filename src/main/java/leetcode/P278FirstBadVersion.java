package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.IntStream;

/**
 * You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad.
 *
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.
 *
 * You are given an API bool isBadVersion(version) which returns whether version is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.
 *
 * Input: n = 5, bad = 4
 * Output: 4
 * Input: n = 1, bad = 1
 * Output: 1
 *
 *     1 <= bad <= n <= 231 - 1
 */
class P278FirstBadVersion {
    int bad;
    boolean isBadVersion(int x){
        return x >= bad;
    }
    int firstBadVersion(int n) {
        int beg = 1, end = n;
        while (beg < end){
            int mid = beg + (end- beg)/2;
            if(isBadVersion(mid)){
                end = mid;
            }else {
                beg = mid+1;
            }
        }
        return beg;
    }
    @ParameterizedTest
    @CsvSource({
            "5,4,4",
            "1,1,1",
            "2,1,1",
            "2,2,2",
    })
    void test(int n, int firstBad, int expected){
        this.bad = firstBad;
        Assertions.assertEquals(expected, firstBadVersion(n));
    }
}
