package Skiena;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Give an algorithm that will return true that if a parentheses is properly nested and balanced parentheses and false if otherwise for full credit identify the position of the first offending parenthesis if the String is not properly nested and balanced.
 */
public class Ex3_1BalanceParentheses {

    Integer parenthesesBalancedIndex(String parentheses){
        Pair<Integer,List<Integer>> stack = IntStream.range(0, parentheses.length())
                .mapToObj(i -> Integer.valueOf(i))
                .collect(() -> new Pair<>(-1,new ArrayList<Integer>()),
                        (Pair<Integer,List<Integer>> tuple, Integer index) -> {
                            if(tuple.getFirst() != -1){
                                return;
                            }
                            Character ch = parentheses.charAt(index);
                            List<Integer> oldStack = tuple.getSec();
                            if (ch.equals('(')) {
                                oldStack.add(index);
                            } else {
                                if(!oldStack.isEmpty()) {
                                    oldStack.remove(oldStack.size() - 1);
                                }else{
                                    tuple.setFirst(index);
                                }
                            } }, (stack1,stack2) -> {});
        int result = -1;
        if (!stack.getSec().isEmpty() || !stack.getFirst().equals(-1)) {
            if (!stack.getFirst().equals(-1)) {
                result = stack.getFirst();
            }else {
                result = stack.getSec().remove(stack.getSec().size() - 1 );
            }
        }
        return result;
    }


    @ParameterizedTest(name = "is balanced {0} = {1}")
    @CsvSource({
            "(()), -1, nested all balanced",
            ")()(, 0, extra opening" ,
            "((()))()(()), -1, mix nested and concatenated",
            "((())))()(), 6, stack will be empty and no match for opening",
            "((()(), 1, stack will not be empty and string will be fully parsed"
    })
    void testAllData1(String first, Integer expectedResult, String message) {
        Ex3_1BalanceParentheses algo = new Ex3_1BalanceParentheses();
        assertEquals(expectedResult, algo.parenthesesBalancedIndex(first),
                () -> message);
    }
}

