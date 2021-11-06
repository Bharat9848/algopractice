package geekforgeek;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Remove minimum elements from array so that max <= 2 * min
 *
 * Given an array arr, the task is to remove minimum number of elements such that after their removal, max(arr) <= 2 * min(arr).
 *
 * Examples:
 *
 *     Input: arr[] = {4, 5, 3, 8, 3}
 *     Output: 1
 *     Remove 8 from the array.
 *
 *     Input: arr[] = {1, 2, 3, 4}
 *     Output: 1
 *     Remove 1 from the array.
 */
public class RemoveMinElementsToSatisfyFunction {
    int removeMinElement(int[] arr){
        if(arr == null || arr.length == 0) return -1;
        Arrays.sort(arr);
        Function<Integer, Integer> function = (Integer x) ->   2*x;
        int[] spanArr = new int[arr.length];
        spanArr[arr.length-1] = 1;
        for (int i = 0; i < arr.length-1; i++) {
            int spanIndex = binarySearchMaxLess(arr, i+1, function.apply(arr[i]));
            spanArr[i] = spanIndex -i +1;
        }
        System.out.println(Arrays.toString(arr) + Arrays.toString(spanArr));
        return arr.length - Arrays.stream(spanArr).max().orElse(-1);
    }

    private int binarySearchMaxLess(int[] arr, int beg, Integer cand) {
        int end = arr.length-1;
        while (beg<end){
            int mid = beg + (end-beg)/2;
            if(arr[mid] == cand){
                return mid;
            }else if( arr[mid] > cand){
                end = mid;
            }else {
                beg = mid + 1;
            }
        }
        return arr[beg] > cand ? beg -1 : beg;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3,4|1",
            "8,3,5,3,4|1",
            "22,12,1,4|2",
            "1,2,3,4,5,100|3"
    })
    void test(String intArr, int expected){
        int[] arr = Arrays.stream(intArr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, removeMinElement(arr));
    }
}
