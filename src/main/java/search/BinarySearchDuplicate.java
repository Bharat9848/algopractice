package search;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/** Given a sorted array with customerIds representing how many times he/she have visited amazon.com
 * return how many times customer Id have visited the amazon.com
 * @Interview Asked in Amazon phoneScreening
 */
class BinarySearchDuplicate {

    int customerVisited(int[] arr, int custId){
        int startIndex = binarySearchStartPosition(arr, custId);
        int endIndex = binarySearchEndPosition(arr, custId);
        if(startIndex != -1){
            return (endIndex-startIndex+1);
        }else {
            return 0;
        }
    }

    private int binarySearchStartPosition(int[] arr, int target) {
        int beg = 0, end = arr.length-1;
        while (beg<end){
            int mid = beg + (end-beg)/2;
            if(arr[mid] == target){
                if(mid-1>=0 && arr[mid-1]==target){
                    end = mid;
                }else {
                    return mid;
                }
            }else if(arr[mid] > target){
                end = mid;
            }else{
                beg = mid + 1;
            }
        }
        return arr[beg] == target ? beg:-1;
    }

    private int binarySearchEndPosition(int[] arr, int target) {
        int beg = 0, end = arr.length-1;
        while (beg<end){
            int mid = beg + (end-beg)/2;
            if(arr[mid] == target){
                if(mid+1 < arr.length && arr[mid+1]==target){
                    beg = mid+1;
                }else {
                    return mid;
                }
            }else if(arr[mid] > target){
                end = mid;
            }else{
                beg = mid + 1;
            }
        }
        return arr[beg] == target ? beg:-1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,1,1,1,1|1|5",
            "1,1,1,1,1|2|0",
            "1,2,2,3,3,3,10,10,10,10,10,10|10|6"
    })
    void test(String arrStr, int custId, int freq){
        int[] arr = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(freq, customerVisited(arr, custId));
    }
}
