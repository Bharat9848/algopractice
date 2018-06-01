package sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by bharat on 10/5/18.
 */
public class SortPractice {

    public static int[] quickSort(int[] arr){
        return quickSort(arr,0,arr.length-1);
    }

    private static int[] quickSort(int[] arr, int left, int right){
        if(left<right) {
            int pvIndex = pivotLocation(arr, left, left + 1, right);
            quickSort(arr, pvIndex + 1, right);
            quickSort(arr, left, pvIndex - 1);
        }
        return arr;
    }

    private static int pivotLocation(int[] arr,int pivot,int left, int right){
        while (left < right) {
            if(arr[left]<arr[pivot]){
                left++;
            }else{
                swap(arr,left,right);
                right--;
            }
        }
        if(arr[left]<arr[pivot]){
            swap(arr,left,pivot);
            return left;
        }else {
            swap(arr,left-1,pivot);
            return left - 1;
        }
    }

    private static void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    @Test
    public void testQSort(){
        Assert.assertEquals(Arrays.toString(new int[]{3,5,7,8,9}),Arrays.toString(quickSort(new int[]{7,5,8,3,9})));
        Assert.assertEquals(Arrays.toString(new int[]{3,5,7,7,7,8,9}),Arrays.toString(quickSort(new int[]{7,5,8,3,7,9,7})));
    }
}
