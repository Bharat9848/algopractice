package leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a sorted integer array without duplicates, return the summary of its ranges.

 Example 1:

 Input:  [0,1,2,4,5,7]
 Output: ["0->2","4->5","7"]
 Explanation: 0,1,2 form a continuous range; 4,5 form a continuous range.
 Example 2:

 Input:  [0,2,3,4,6,8,9]
 Output: ["0","2->4","6","8->9"]
 Explanation: 2,3,4 form a continuous range; 8,9 form a continuous range.
 * Created by bharat on 10/5/18.
 */
public class P228SummaryRanges {
    public List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();
        int rangeStart = -1;
        int rangeEnd = -1;
        for (int i = 0; i < nums.length; i++) {
            if(i==0){
                rangeStart = 0;
            }
            if(i+1<nums.length && nums[i]+1 == nums[i+1]){
                continue;
            }else{
                rangeEnd = i;
                if(rangeStart==rangeEnd){
                    result.add(nums[rangeEnd]+"");
                }else{
                    result.add(nums[rangeStart]+ "->" + nums[rangeEnd]);
                }
                if(i+1<nums.length){
                    rangeStart = i+1;
                }
            }

        }
        return result;
    }

    @Test
    public void test(){
        System.out.println(summaryRanges(new int[]{0,2,3,4,6,8,9}));
        System.out.println(summaryRanges(new int[]{0,1,2,4,5,7}));
        System.out.println(summaryRanges(new int[]{}));
    }
}
