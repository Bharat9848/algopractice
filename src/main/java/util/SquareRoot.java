package util;

import org.junit.Test;

/**
 * Created by bharat on 22/5/18.
 */
public class SquareRoot {

    public double sqrt(int no){

        double beg =1, end = no/2;
        double mid = beg + (end-beg)/2;

        while(beg<end){
            mid = (end+ beg)/2;
            if(mid*mid==no){
                return mid;
            }else if(mid*mid>no){
                end=mid-0.01;
            }else{
                beg = mid+0.01;
            }
        }
        System.out.println(mid);
        return mid;
    }

    @Test
    public void testCase(){
        sqrt(4);
    }
    // 10 5 3 7 20 15 25
}
