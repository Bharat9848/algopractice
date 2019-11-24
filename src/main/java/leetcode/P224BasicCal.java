package leetcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**Implement a basic calculator to evaluate a simple expression string.

 The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

 You may assume that the given expression is always valid.

 Some examples:
 "1 + 1" = 2
 " 2-1 + 2 " = 3
 "(1+(4+5+2)-3)+(6+8)" = 23
 Note: Do not use the eval built-in library function.


 * Created by bharat on 4/4/18.
 */
public class P224BasicCal {

        public int calculate(String s) {
            List<String> stack = new ArrayList<>();
            for(int i = 0; i<s.length();i++){

                Character ch = s.charAt(i);

                if(ch == ' '){
                    continue;
                }

                int num = -1;
                if(Character.isDigit(ch)){
                    num = Character.digit(ch,10);
                    while( i+1 < s.length()  && Character.isDigit(s.charAt(i + 1))){
                        num = num*10 + Character.digit(s.charAt(i + 1),10);
                        i++;
                    }
                }


                if(num != -1 && stack.size()>0 && isOpreator(stack.get(stack.size()-1))){
                    String op = pop(stack);
                    int one  = Integer.parseInt(pop(stack));
                    stack.add(doOperation(op,one,num) + "");
                }else if (')' == ch ){
                    String x = pop(stack);
                    pop(stack);
                    if(stack.size()>0 && isOpreator(stack.get(stack.size() -1))){
                        String op = pop(stack);
                        int one = Integer.parseInt(pop(stack));
                        int two = Integer.parseInt(x);
                        stack.add(doOperation(op,one,two)+"");
                    }else {
                        stack.add(x);
                    }
                }else if(num != -1){
                    stack.add("" + num);
                }else{
                    stack.add("" + ch);
                }


            }
            return stack.size()>0 ? Integer.parseInt(pop(stack)):0;
        }

    private String pop(List<String> stack) {
        return stack.remove(stack.size()-1);
    }

    private boolean isOpreator(String  ch) {
        if ("+".equalsIgnoreCase(ch) || "-".equalsIgnoreCase(ch) ) {
            return true;
        }
        return false;
    }
    private int doOperation(String operator, int one,  int two){
        switch (operator)
        {
            case "+" : return one + two;
            case "-" : return one - two;
            default:throw new RuntimeException();
        }
    }

    @Test
    public void test(){
        assertEquals(33,calculate("2 + 31"));
        assertEquals(24,calculate("(2 + 3) +   19 "));
        assertEquals(3,calculate("2-1 + 2 "));
        assertEquals(23,calculate("(1+(4+5+2)-3)+(6+8)"));
        assertEquals(7,calculate("(7) "));
        assertEquals(0,calculate(" "));


    }
}
