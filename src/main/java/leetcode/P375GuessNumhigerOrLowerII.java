package leetcode;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * We are playing the Guess Game. The game is as follows:

 I pick a number from 1 to n. You have to guess which number I picked.

 Every time you guess wrong, I'll tell you whether the number I picked is higher or lower.

 However, when you guess a particular number x, and you guess wrong, you pay $x. You win the game when you guess the number I picked.
 Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.
 */
public class P375GuessNumhigerOrLowerII {

    public int getMoneyAmount(int n) {
        if(n == 1){ return 0;}
        int money =0;
        for (int i = 1; i <= n; i++) {
            int beg = 1, end = n;
            int guess = n/2;
            if(n%2==1){guess +=1;}
            int mon = 0;
            while(guess != i){
                mon+=guess;
                if(guess < i){
                    beg = guess + 1;
                }else if(guess>i){
                    end = guess -1;
                }
                guess = (beg + end)/2;
            }
            if(money<mon){
                money = mon;
            }
        }
        return money;
    }

    @Test
    public void test1(){
        P375GuessNumhigerOrLowerII x = new P375GuessNumhigerOrLowerII();
        assertEquals(x.getMoneyAmount(3),2);
        assertEquals(6, x.getMoneyAmount(5));

    }

}
