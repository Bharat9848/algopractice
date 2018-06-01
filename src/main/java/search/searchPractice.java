package search;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by bharat on 17/3/18.
 */
public class searchPractice {

    //problem 1 starts

    int binarySearchRecP1(int [] arr, int k){
        return doRecursiveBinarySearch(arr,k,0,arr.length-1);
    }

    private int doRecursiveBinarySearch(int[] arr, int k, int beg, int end) {
        if(beg>end){
            return -1;
        }
        int mid =  beg + (end-beg)/2;
        if(arr[mid]==k){
            return mid;
        }else if(k>arr[mid]){
            return doRecursiveBinarySearch(arr,k,mid+1,end);
        }else{
            return doRecursiveBinarySearch(arr, k, beg, mid-1);
        }
    }


    int binarySearchIterative(int [] arr, int k){
        int beg = 0, end = arr.length-1;
        while(beg<=end){
            int mid = beg + (end - beg)/2;
            if(k == arr[mid]){
                return mid;
            }else if(k>arr[mid]){
                beg = mid + 1;
            }else {
                end = mid -1;
            }
        }
        return -1;
    }

    @Test
    public void testP1(){
        Assert.assertEquals(binarySearchIterative(new int[]{7,18,45,90},7), 0);
        Assert.assertEquals(binarySearchIterative(new int[]{7,18,45,90},45), 2);
        Assert.assertEquals(binarySearchIterative(new int[]{7,12},100), -1);
        Assert.assertEquals(binarySearchIterative(new int[]{7,18,45,90},2), -1);
        Assert.assertEquals(binarySearchIterative(new int[]{7,18,45,90},10), -1);

        Assert.assertEquals(binarySearchRecP1(new int[]{7,12},100), -1);
        Assert.assertEquals(binarySearchRecP1(new int[]{7,18,45,90},2), -1);
        Assert.assertEquals(binarySearchRecP1(new int[]{7,18,45,90},45), 2);
        Assert.assertEquals(binarySearchRecP1(new int[]{7,18,45,90},7), 0);
        Assert.assertEquals(binarySearchRecP1(new int[]{7,18,45,90},10), -1);

    }
// problem 1 ends

    public int searchIndexSameAsElement(int[] arr){
        int beg = 0, end = arr.length-1;
        while(beg<=end){
            int mid = beg + (end - beg)/2;
            if(mid+1 == arr[mid]){
                return mid+1;
            }else if(mid+1>arr[mid]){
                end = mid - 1;
            }else {
                beg = mid + 1;
            }
        }
        return -1;
    }

    @Test
    public void problem2Test(){
        Assert.assertEquals(3,searchIndexSameAsElement(new int[]{-10,-3,3,5,7}) );
        Assert.assertEquals(-1,searchIndexSameAsElement(new int[]{-10,-3,4,5,7}) );
    }

    //problem2 ends

    //problem3 starts

    public int findSmallestMissingNumberInSorted(int[] arr, int m){
        if(arr.length==m){
            throw new IllegalArgumentException();
        }
        int beg = 0, end = arr.length-1;
        while(beg<=end){
            int mid = beg + (end - beg)/2;
            if(mid+1 == arr[mid]){
                beg = mid + 1;
            }else if(mid+1<arr[mid]){
                end = mid - 1;
            }
        }
        return beg+1;

    }

    @Test
    public void testP3(){
        Assert.assertEquals(1, findSmallestMissingNumberInSorted(new int[]{2,4,6,8},8));
        Assert.assertEquals(5, findSmallestMissingNumberInSorted(new int[]{1,2,3,4},8));
        Assert.assertEquals(2, findSmallestMissingNumberInSorted(new int[]{1,3,4},8));
    }
    //problem 3 ends

}
