package dynamic.programming;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 *
 * Its a DP Problem
 *
 * Write recursion formula
 * Lets say we name the noOfElementsLessThanIndex(int k)
 *
 * sol = max(forAllIndex(noOfElementsLessThanIndex(int k)))
 *
 *
 * noOfElementsLessThanIndex(int k) = max(max(noOfElementsLessThanIndex(i)+1),1) //Hardest Part in DP
 * where 0<i<k and a[i]<a[k]
 *
 *
 *
 *
 * Created by bharat on 27/5/18.
 */
public class LongestIncreasingSubseq {

    int longIncreasingSubSeq(int[] numbers){

        return longIncreasingSubSeqRec(numbers,numbers.length-1);
    }


    private int longIncreasingSubSeqDP(int[] numbers) {
        int[] lis = new int[numbers.length];
        for (int i = 0; i < lis.length; i++) {
            lis[i] = 1;
        }

        for (int i = 1; i <numbers.length ; i++) {
            for (int j = i-1; j >= 0 ; j--) {
                if (lis[j] + 1 > lis[i] && numbers[j] < numbers[i]){
                    lis[i] = lis[j] + 1;
                }
            }
        }
        int max =1;
        for (int i = 0; i < lis.length; i++) {
            if (max < lis[i]) {
                max = lis[i];
            }
        }
        System.out.println(Arrays.toString(lis));
        return max;
    }

    private int longIncreasingSubSeqRec(int[] numbers, int index) {
        if(index==0){return 1;}
        int max = 1;
        for (int i = index-1; i >=0; i--) {
            int i1 = longIncreasingSubSeqRec(numbers, i);
            if(numbers[index] > numbers[i]){
                if(i1 +1 > max){
                    max = i1+1;
                }
            }
        }
        return max;
    }

    @Test
    public void testLong(){
        Assert.assertEquals(5,longIncreasingSubSeq(new int[]{19,5,24,90,100,8,9,10,200}));
        Assert.assertEquals(5,longIncreasingSubSeqDP(new int[]{19,5,24,90,100,8,9,10,200,1}));
    }
}
