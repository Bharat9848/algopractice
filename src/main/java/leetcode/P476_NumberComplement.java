package leetcode;

/**
 * Created by bharat on 8/12/17.
 */
public class P476_NumberComplement {

    public int findComplement(int num) {
        if(num == 0){return 1;}
        int orig=0,comp=0;
        for(int count=0,i=num; i>=0 && orig != num ; i=i/2){
            if(i==0 && orig != num){
                comp += Math.pow(2,count);
            }
            int rem = i%2;
            if(rem==0){
                comp += Math.pow(2,count);
            }else{
                orig += Math.pow(2,count);
            }
            count++;
        }
        return comp;
    }

    public static void main(String[] args){
        P476_NumberComplement x = new P476_NumberComplement();
        System.out.println(x.findComplement(7));
        System.out.println(x.findComplement(5));
        System.out.println(x.findComplement(1));
        System.out.println(x.findComplement(0));
        System.out.println(x.findComplement(2));
        System.out.println(x.findComplement(3));
        System.out.println(x.findComplement(4));
        System.out.println(x.findComplement(6));
        System.out.println(x.findComplement(8));
        System.out.println(x.findComplement(9));
        System.out.println(x.findComplement(10));
        System.out.println(x.findComplement(11));


    }
}
