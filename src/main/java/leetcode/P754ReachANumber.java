package leetcode;

import org.junit.Assert;
import org.junit.Test;
import util.Pair;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * You are standing at position 0 on an infinite number line. There is a goal at position target.

 On each move, you can either go left or right. During the n-th move (starting from 1), you take n steps.

 Return the minimum number of steps required to reach the destination.
 * Created by bharat on 15/4/18.
 */
public class P754ReachANumber {

    public int reachNumber(int target) {
        int steps = 0;
        ArrayList<Integer> reached = new ArrayList<>();
        reached.add(0);
        boolean targetReached = false;
        while(!targetReached){
            steps++;
            ArrayList<Integer> next = new ArrayList<>();
            while(!reached.isEmpty()){
                Integer x = reached.remove(0);

                int pos = x + steps;

                int neg = x - steps;
                if(pos==target||neg==target){
                    targetReached = true;
                    break;
                }
                next.add(pos);
                next.add(neg);
            }
            reached = next;
        }
        return steps;
    }



    @Test
    public void test(){
        Assert.assertEquals(4,reachNumber(10));
        Assert.assertEquals(2,reachNumber(3));
        Assert.assertEquals(3,reachNumber(2));
        Assert.assertEquals(5,reachNumber(11));
        Assert.assertEquals(7,reachNumber(12));
        Assert.assertEquals(5,reachNumber(13));
        Assert.assertEquals(7,reachNumber(14));
        Assert.assertEquals(5,reachNumber(15));
        System.out.println(reachNumber(-1000000000));
    }
}
