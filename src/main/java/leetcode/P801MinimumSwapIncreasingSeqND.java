package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/*
We have two integer sequences A and B of the same non-zero length.

We are allowed to swap elements A[i] and B[i].  Note that both elements are in the same index position in their respective sequences.

At the end of some number of swaps, A and B are both strictly increasing.  (A sequence is strictly increasing if and only if A[0] < A[1] < A[2] < ... < A[A.length - 1].)

Given A and B, return the minimum number of swaps to make both sequences strictly increasing.  It is guaranteed that the given input always makes it possible.

Example:
Input: A = [1,3,5,4], B = [1,2,3,7]
Output: 1
Explanation:
Swap A[3] and B[3].  Then the sequences are:
A = [1, 3, 5, 7] and B = [1, 2, 3, 4]
which are both strictly increasing.
Note:


[y1 y2 y3 y4] x 100+
[x1 x2 x3 x4] y 200+

22 21
20 11
if I swap or no swap

200 201 202 110
100 102 103 210



A, B are arrays with the same length, and that length will be in the range [1, 1000].
A[i], B[i] are integer values in the range [0, 2000].
18601231122

at i
if(increasing and swappble)
    if(weCanReachSol){
        try
    }else{
        swap
        try
    }
else if(not increasing and swappable)
    swapping
    if(canSol){
        return try
    }else{
        reverseSwap
        return false;
    }
else if (increasing and not swappabele){
    return try;
}else{
    return false;
}

 */
class P801MinimumSwapIncreasingSeqND {
    int minSwap(int[] A, int[] B) {
        if(A.length != B.length || A.length < 2){
            return 0;
        }
        int swap = 0;
        for (int i = 1; i < A.length ; i++) {
            if(bothIncreasing(A, B, i)){
                continue;
            }else{
                if(swappable(A, B, i)){
                    doSwap(A, B, i);
                    swap+=1;
                    if(canReachSol(A,B)){
                        return swap;
                    }
                }else{
                    return 0;
                }
            }
        }
        return swap;
    }

    private void doSwap(int[] a, int[] b, int i) {
        int temp = a[i];
        a[i] = b[i];
        b[i] = temp;
    }

    private boolean bothIncreasing(int[] arrA, int[] arrB, int index){
        return increasingAtIndex(arrA, index) && increasingAtIndex(arrB, index);
    }

    private boolean increasingAtIndex(int[] arr, int index) {
        return index == 0 || arr[index] > arr[index -1];
    }

    private boolean swappable(int[] arrA, int[] arrB, int index){
        return index == 0 || (arrA[index] > arrB[index - 1] && arrB[index] > arrA [index-1]);
    }

    private boolean canReachSol(int[] A, int[] B){
        boolean increasing = true;
        for (int i = 1; i < A.length && increasing; i++) {
            increasing = (A[i-1] < A[i]) && (B[i-1] < B[i]);
        }
        return increasing;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "11,13,13,14; 11,12,15,17; 2",
            "1, 3;1, 2; 0",
            "0,4,4,5,9;0,1,6,8,10;1",
            "10,2; 20,1; 0"
    })
    void test(String aStr, String bStr, int expected){
        int[] aArr = Arrays.stream(aStr.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        int[] bArr = Arrays.stream(bStr.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, minSwap(aArr, bArr));
    }
}
