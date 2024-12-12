package search.binary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * The latest version of a software product fails the quality check. Since each version is developed upon the previous one, all the versions created after a bad version are also considered bad.
 * <p>
 * Suppose you have n versions with the IDs [1,2,...,n], and you have access to an API function that returns TRUE if the argument is the ID of a bad version.
 * <p>
 * Find the first bad version that is causing all the later ones to be bad. Additionally, the solution should also return the number of API calls made during the process and should minimize the number of API calls too.
 * <p>
 * Constraints:
 * <p>
 * 1≤1≤ first bad version ≤≤ n ≤105≤105
 */
public class FirstBadVersion {
    public static int badVersion = -1;

    // -- DO NOT CHANGE THIS SECTION -----------------
    public static boolean isBadVersion(int v) { // isBadVersion() is the API function that returns true or false depending upon whether the provided version ID is bad or not
        return badVersion <= v;
    }
//-------------------------------------------------

    public static int[] firstBadVersion(int n) {
        int beg = 1, end = n;
        int noOfCalls = 0;
        boolean endMoved = false;
        while (beg < end) {
            endMoved = false;
            int mid = beg + (end - beg) / 2;
            var isbad = isBadVersion(mid);
            noOfCalls++;
            if (isbad) {
                if (mid - 1 == 0) {
                    return new int[]{mid, noOfCalls};
                }
                end = mid - 1;
                endMoved = true;
            } else {
                beg = mid + 1;
            }
        }
        if(beg == end) {
            boolean badVersionBeg = isBadVersion(beg);
            noOfCalls++;
            if(badVersionBeg){
                return  new int[]{beg, noOfCalls};
            } else {
                return new int[]{beg + 1, noOfCalls};
            }
        } else {
            return new int[]{beg , noOfCalls};
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "10|6|6|3",
            "8|6|6|3",
            "7|4|4|3",
            "100|67|67|7",
            "5|5|5|3",
            "2|2|2|2",
            "5|3|3|3",
            "5|1|1|2",
    })
    void test(int n, int badVer, int expectedVersion, int expectedCalls) {
        FirstBadVersion.badVersion = badVer;
        Assertions.assertArrayEquals(new int[]{expectedVersion, expectedCalls}, firstBadVersion(n));
    }
}
