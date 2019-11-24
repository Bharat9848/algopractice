package leetcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

/**
 * Given a string S and a character C, return an array of integers representing the shortest distance from the character C in the string.

 Example 1:

 Input: S = "loveleetcode", C = 'e'
 Output: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]


 Note:

 S string length is in [1, 10000].
 C is a single character, and guaranteed to be in string S.
 All letters in S and C are lowercase.
 * Created by bharat on 6/5/18.
 */
public class P821ShortestDistanceToChar {
    public int[] shortestToChar(String S, char C) {
        int[] result = new int[S.length()];
        int lastCPos = -1;
        for(int i=0;i<S.length();i++){
            if(S.charAt(i) == C){
                result[i] = 0;
                if(lastCPos==-1){
                    for(int j= i-1; j>=0 ; j--){
                        result[j] = result[j+1]+1;
                    }

                }else {
                    int eleBetween = i - lastCPos -1;
                    int pos = eleBetween%2==0? eleBetween/2:eleBetween/2 +1;

                    for(int k = lastCPos+1,l= i-1; k <= lastCPos + pos; k++,l--){
                        result[k] = result[k-1] + 1;
                        result[l] = result[l+1] + 1;
                    }
                }
                lastCPos = i;
            }
        }
        for(int i=lastCPos+1;i<S.length();i++){
            result[i] = result[i-1] +1;
        }
        return result;
    }

    @Test
    public void test1(){
        assertEquals(Arrays.toString(new int[]{3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0}),Arrays.toString(shortestToChar("loveleetcode",'e')));
        assertEquals(Arrays.toString(new int[]{3, 2, 1, 0, 0}),Arrays.toString(shortestToChar("lovee",'e')));
        assertEquals(Arrays.toString(new int[]{0, 1, 2, 3}),Arrays.toString(shortestToChar("elov",'e')));
    }
}
