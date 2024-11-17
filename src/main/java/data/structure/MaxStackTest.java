package data.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

public class MaxStackTest{

    private MaxStack myStack = null;
    @BeforeEach
    void setup(){
        myStack = new MaxStack(10);
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "push,push,max,pop,max|4,2,,,|,,4,2,4",
            "push,push,push,max,pop,max|24,6,72,,,|,,,72,72,24"

    })
    void test(String operationStr, String argStr, String expectedStr){
        String[] operations =  operationStr.split(",");
        int [] argument = Arrays.stream(argStr.split(",")).mapToInt(str -> {if(str.isEmpty()){return -1;}else{return Integer.parseInt(str);}}).toArray();
        int [] expected = Arrays.stream(expectedStr.split(",")).mapToInt(str -> {if(str.isEmpty()){return -1;}else{return Integer.parseInt(str);}}).toArray();
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            switch (operation){
                case "push":  myStack.push(argument[i]);
                    break;
                case "pop":
                    Assertions.assertEquals(expected[i], myStack.pop());
                    break;
                case "max": Assertions.assertEquals(expected[i], myStack.max());
                    break;
                default: throw new RuntimeException("illegal opeation");

            }
        }
    }
}