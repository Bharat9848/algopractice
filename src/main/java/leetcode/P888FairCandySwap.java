package leetcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/*
Alice and Bob have candy bars of different sizes: A[i] is the size of the i-th bar of candy that Alice has, and B[j] is the size of the j-th bar of candy that Bob has.

Since they are friends, they would like to exchange one candy bar each so that after the exchange, they both have the same total amount of candy.  (The total amount of candy a person has is the sum of the sizes of candy bars they have.)

Return an integer array ans where ans[0] is the size of the candy bar that Alice must exchange, and ans[1] is the size of the candy bar that Bob must exchange.

If there are multiple answers, you may return any one of them.  It is guaranteed an answer exists.



Example 1:

Input: A = [1,1], B = [2,2]
Output: [1,2]
Example 2:

Input: A = [1,2], B = [2,3]
Output: [1,2]
Example 3:

Input: A = [2], B = [1,3]
Output: [2,3]
Example 4:

Input: A = [1,2,5], B = [2,4]
Output: [5,4]


Note:

1 <= A.length <= 10000
1 <= B.length <= 10000
1 <= A[i] <= 100000
1 <= B[i] <= 100000
It is guaranteed that Alice and Bob have different total amounts of candy.
It is guaranteed there exists an answer.
 */
public class P888FairCandySwap {
    public int[] fairCandySwap(int[] A, int[] B) {
        int aliceSum = Arrays.stream(A).sum();
        int bobSum = Arrays.stream(B).sum();
        int halfSum = (aliceSum - bobSum)/2;
        //find any two nodes whcih A(i) - B(j) = halfsum
        Arrays.sort(A);
        Arrays.sort(B);
        return findTwoNoWith(A, B, halfSum);
    }

    private int[] findTwoNoWith(int[] sortedA, int[] sortedB, int halfSum) {
        int i =0 ,j =0;
        boolean found = false;
        while (i < sortedA.length && !found) {
            while(j < sortedB.length) {
                if (sortedA[i]-sortedB[j] == halfSum){
                    found = true;
                    break;
                }else{
                    j++;
                }
            }
            if(!found) {i++; j=0;}
        }
        return found? new int[]{sortedA[i], sortedB[j]}: new int[]{sortedA[0], sortedB[0]};
    }


    @ParameterizedTest(name = "is balanced {0} = {1}")
    @CsvSource({
            "1:1, 2:2, 1:2, wrong",
            "1:2, 2:3, 1:2, wrong",
            "2, 1:3, 2:3, wrong",
            "1:2:5, 2:4, 5:4, wrong"
    })
    void testAllData1(String aliceStr, String bobStr, String exchangeStr, String message) {
        P888FairCandySwap algo = new P888FairCandySwap();
        int[] alice = Arrays.stream(aliceStr.split(":")).mapToInt(Integer::valueOf).toArray();
        int[] bob = Arrays.stream(bobStr.split(":")).mapToInt(Integer::valueOf).toArray();
        int[] exchange = Arrays.stream(exchangeStr.split(":")).mapToInt(Integer::valueOf).toArray();
        assertArrayEquals(exchange, algo.fairCandySwap(alice, bob),
                () -> message);
    }
}
