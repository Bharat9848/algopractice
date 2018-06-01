package util;

/**
 * Created by bharat on 7/4/18.
 */
public class PrintUtil {

    public static <T> void printArray(T[] arr){
        for (T ele : arr) {
            System.out.print(ele +", ");
        }
    }
}
