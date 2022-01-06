package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;

/**
 * Given the postfix tokens of an arithmetic expression, build and return the binary expression tree that represents this expression.
 *
 * Postfix notation is a notation for writing arithmetic expressions in which the operands (numbers) appear before their operators. For example, the postfix tokens of the expression 4*(5-(7+2)) are represented in the array postfix = ["4","5","7","2","+","-","*"].
 *
 * The class Node is an interface you should use to implement the binary expression tree. The returned tree will be tested using the evaluate function, which is supposed to evaluate the tree's value. You should not remove the Node class; however, you can modify it as you wish, and you can define other classes to implement it if needed.
 *
 * A binary expression tree is a kind of binary tree used to represent arithmetic expressions. Each node of a binary expression tree has either zero or two children. Leaf nodes (nodes with 0 children) correspond to operands (numbers), and internal nodes (nodes with two children) correspond to the operators '+' (addition), '-' (subtraction), '*' (multiplication), and '/' (division).
 *
 * It's guaranteed that no subtree will yield a value that exceeds 109 in absolute value, and all the operations are valid (i.e., no division by zero).
 *
 * Follow up: Could you design the expression tree such that it is more modular? For example, is your design able to support additional operators without making changes to your existing evaluate implementation?
 *
 *
 *
 * Example 1:
 *
 * Input: s = ["3","4","+","2","*","7","/"]
 * Output: 2
 * Explanation: this expression evaluates to the above binary tree with expression ((3+4)*2)/7) = 14/7 = 2.
 *
 * Example 2:
 *
 * Input: s = ["4","5","2","7","+","-","*"]
 * Output: -16
 * Explanation: this expression evaluates to the above binary tree with expression 4*(5-(2+7)) = 4*(-4) = -16.
 *
 * Example 3:
 *
 * Input: s = ["4","2","+","3","5","1","-","*","+"]
 * Output: 18
 *
 * Example 4:
 *
 * Input: s = ["100","200","+","2","/","5","*","7","+"]
 * Output: 757
 *
 *
 *
 * Constraints:
 *
 *     1 <= s.length < 100
 *     s.length is odd.
 *     s consists of numbers and the characters '+', '-', '*', and '/'.
 *     If s[i] is a number, its integer representation is no more than 105.
 *     It is guaranteed that s is a valid expression.
 *     The absolute value of the result and intermediate values will not exceed 109.
 *     It is guaranteed that no expression will include division by zero.
 */
public class P1628DesignExpressionTree {
    /* This is the interface for the expression tree Node.
            * You should not remove it, and you can define some classes to implement it.
 */

    private abstract class Node {
        public abstract int evaluate();
        // define your fields here
    };

    class LeafNode extends Node{
        final int num;
        LeafNode(int num){
            this.num = num;
        }
        @Override
        public int evaluate() {
            return num;
        }
    }

    abstract class OperatorNode extends Node {
        Node left, right;
        @Override
        public int evaluate() {
            int leftVal = left.evaluate();
            int rightVal = right.evaluate();
            return applyOperation(leftVal, rightVal);
        }
        abstract int applyOperation(int left, int right);
    }

    class PlusOperation extends OperatorNode{
        PlusOperation(Node left, Node right){
            this.left = left;
            this.right = right;
        }
        @Override
        int applyOperation(int left, int right) {
            return left + right;
        }
    }

    class MinusOperation extends OperatorNode{
        MinusOperation(Node left, Node right){
            this.left = left;
            this.right = right;
        }
        @Override
        int applyOperation(int left, int right) {
            return left - right;
        }
    }

    class MultiplyOperation extends OperatorNode{
        MultiplyOperation(Node left, Node right){
            this.left = left;
            this.right = right;
        }
        @Override
        int applyOperation(int left, int right) {
            return left * right;
        }
    }

    class DivisionOperation extends OperatorNode{
        DivisionOperation(Node left, Node right){
            this.left = left;
            this.right = right;
        }
        @Override
        int applyOperation(int left, int right) {
            return left / right;
        }
    }

    /**
     * This is the TreeBuilder class.
     * You can treat it as the driver code that takes the postinfix input
     * and returns the expression tree represnting it as a Node.
     */

    class TreeBuilder {
        Node buildTree(String[] postfix) {
            Stack<Node> operandStack = new Stack<>();
            for (int i = 0; i < postfix.length; i++) {
                String ele = postfix[i];
                try{
                    int operand = Integer.parseInt(ele);
                    operandStack.push(new LeafNode(operand));
                }catch (NumberFormatException ex){
                    char operator = ele.charAt(0);
                    Node right = operandStack.pop();
                    Node left = operandStack.pop();
                    Node newNode;
                    switch (operator){
                        case '+': newNode = new PlusOperation(left, right); break;
                        case '-': newNode = new MinusOperation(left, right); break;
                        case '*': newNode = new MultiplyOperation(left, right); break;
                        case '/': newNode = new DivisionOperation(left, right); break;
                        default: throw new RuntimeException("illegal operator");
                    }
                    operandStack.push(newNode);
                }
            }
            return operandStack.pop();
        }
    }


/**
 * Your TreeBuilder object will be instantiated and called as such:
 * TreeBuilder obj = new TreeBuilder();
 * Node expTree = obj.buildTree(postfix);
 * int ans = expTree.evaluate();
 */

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3,4,+|7",
            "1|1",
            "15,5,/|3",
            "3,4,+,2,*,7,/|2",
            "4,5,2,7,+,-,*|-16",
            "4,2,+,3,5,1,-,*,+|18",
            "100,200,+,2,/,5,*,7,+|757"
    })
    void test(String postfixStr, int expected){
        String[] postfix = postfixStr.split(",");
        TreeBuilder obj = new TreeBuilder();
        Node expTree = obj.buildTree(postfix);
        Assert.assertEquals(expected, expTree.evaluate());
    }
}
