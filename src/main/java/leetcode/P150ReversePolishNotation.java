package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

/**
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 *
 * Valid operators are +, -, *, and /. Each operand may be an integer or another expression.
 *
 * Note that division between two integers should truncate toward zero.
 *
 * It is guaranteed that the given RPN expression is always valid. That means the expression would always evaluate to a result, and there will not be any division by zero operation.
 *
 * Input: tokens = ["2","1","+","3","*"]
 * Output: 9
 * Input: tokens = ["4","13","5","/","+"]
 * Output: 6
 * Explanation: (4 + (13 / 5)) = 6
 * Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
 * Output: 22
 * Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 *     1 <= tokens.length <= 10^4
 *     tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in the range [-200, 200].
 */
public class P150ReversePolishNotation {
    private interface Node {
        int eval();
    }
    private class NodeTerniaryExpression implements Node {
        String operator;
        Node left, right;

        public NodeTerniaryExpression(String operator, Node left, Node right) {
            this.operator = operator;
            this.left = left;
            this.right = right;
        }

        @Override
        public int eval() {
            int result;
            switch (operator){
                case "+" : result = left.eval() + right.eval();
                break;
                case "-" : result = left.eval() - right.eval();
                break;
                case "*" : result = left.eval() * right.eval();
                break;
                case "/" : result = left.eval() / right.eval();
                break;
                default: throw new RuntimeException("wrong operator "+ operator);
            }
            return result;
        }
    }

    private class TerminalExpression implements Node{
        int number;

        public TerminalExpression(int number) {
            this.number = number;
        }

        @Override
        public int eval() {
            return number;
        }
    }

    public int evalRPN(String[] tokens) {
        Pair<Node, Integer> expression = parse(tokens, tokens.length -1);
        return expression.getFirst().eval();
    }

    private Pair<Node, Integer> parse(String[] tokens, int from) {
        if (isOperator(tokens[from])) {
            String operator = tokens[from];
            Pair<Node, Integer> rightNodeAndTillIndex = parse(tokens, from - 1);
            from = rightNodeAndTillIndex.getSec();
            Pair<Node, Integer> leftNodeAndTillIndex = parse(tokens, from - 1);
            from = leftNodeAndTillIndex.getSec();
            return new Pair<>(new NodeTerniaryExpression(operator, leftNodeAndTillIndex.getFirst(), rightNodeAndTillIndex.getFirst()), from);
        } else {
            return new Pair<>(new TerminalExpression(Integer.parseInt(tokens[from])), from);
        }
    }

    private boolean isOperator(String token) {
        return "+".equals(token) || "-".equals(token)||"*".equals(token)|| "/".equals(token);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,+|3",
            "2,1,+,3,*|9",
            "4,13,5,/,+|6",
            "10,6,9,3,+,-11,*,/,*,17,+,5,+|22"
    })
    void test(String polishStr, int expected){
        Assertions.assertEquals(expected, evalRPN(polishStr.split(",")));
    }
}
