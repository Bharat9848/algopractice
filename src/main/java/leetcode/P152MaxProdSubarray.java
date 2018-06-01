package leetcode;

/**
 * Find the contiguous subarray within an array (containing at least one number) which has the largest product.

 For example, given the array [2,3,-2,4],
 the contiguous subarray [2,3] has the largest product = 6
 */
public class P152MaxProdSubarray {


    public int maxProduct(int[] nums) {
        int n = nums.length;
        if(n==0){return 0;}
        int [][] prod = new int[n-1][n-1];
        int maxProd = nums[0];
        for (int i = 1; i < n ; i++) {

        }
        return 0;
    }


}
