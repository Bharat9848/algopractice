package util;

import java.util.List;

/**
 * Created by bharat on 7/4/18.
 */
public class PrintUtil {

    public static <T> void printArray(T[] arr){
        for (T ele : arr) {
            System.out.print(ele +", ");
        }
    }

    public static <T> void printList(List<T> list) {
        for (T ele : list) {
            System.out.print(ele +", ");
        }
    }
}
