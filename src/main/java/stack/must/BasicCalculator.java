package stack.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Given a string containing an arithmetic expression, implement a basic calculator that evaluates the expression string. The expression string can contain integer numeric values and should be able to handle the “+” and “-” operators, as well as “()” parentheses.
 *
 * Constraints:
 *
 * Let s be the expression string. We can assume the following constraints:
 *
 *     1≤1≤ s.length ≤3×103≤3×103
 *     s consists of digits, “+”, “-”, “(”, and “)”.
 *     s represents a valid expression.
 *     “+” is not used as a unary operation ( +1+1 and +(2+3)+(2+3) are invalid).
 *     “-” could be used as a unary operation (−1−1 and −(2+3)−(2+3) are valid).
 *     There will be no two consecutive operators in the input.
 *     Every number and running calculation will fit in a signed 32-bit integer.
 */
public class BasicCalculator {

    private interface expression {
        int evaluate();
    }

    static String numberRegex = "[+-]?\\d+";
    static Pattern numberPattern = Pattern.compile(numberRegex);

    private static class Number implements expression {
        int number;
        public Number(String str){
            var pattern = Pattern.compile(numberRegex);
            var matcher = pattern.matcher(str);
            if(matcher.matches()){
                number = Integer.parseInt(str);
            }
        }
        public int evaluate(){
           return number;
        }
    }


    private static class Expression implements expression{

        @Override
        public int evaluate() {
            return 0;
        }
    }
    private static class Term implements expression{
        Number left;
        Number right;
        String operator;

        public Term(Number left, Number right, String operator){
            this.left = left;
            this.right = right;
            this.operator = operator;
        }
        public int evaluate(){
            if(operator.equals("+")){
                return left.evaluate() + right.evaluate();
            } else {
                return left.evaluate() - right.evaluate();
            }
        }
    }

    public static int calculator(String expression) {
        String token = "";
        return 0;
    }

   private static class ExpressionParser{
        Stack<Integer> openParan = new Stack<>();
        public static Expression parse(String str){

            for (int i = -1; i < str.length(); i++) {
                if(str.charAt(i) == ')'){

                } else if ( str.charAt(i) == '('){

                }

            }
            return null;
        }
   }
// (2+1) Expression(factor)
// expression = exp + exp | factor
// factor  = factor + factor | Terminal
// Terminal = no


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "12|12",
//            "(13 + 50) + (56 - 29 - (7 + 2))|32"
    })
    void test(String expression, int expected){
        Assertions.assertEquals(expected, calculator(expression));
    }
}