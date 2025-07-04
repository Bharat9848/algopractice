package data.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*
Design a data structure that takes in a stream of numbers and can check if any two numbers add up to a specific value.

Implement the TwoSum class with the following constructor and methods:

    Constructor: Sets up the TwoSum object with an empty list at the start.

    void add(int number): Adds a new number to the list.

    boolean find(int value): Returns TRUE if any two numbers in the list add up to the given value. If not, it returns FALSE.

Constraints:

    −105≤−105≤number ≤105≤105

    −231≤−231≤value ≤231−1≤231−1

    At most, 104104 calls will be made to add and find methods.
 */
public class TwoSumIIIRetry {
    private class TwoSum {
        public TwoSum() {
            // Replace this placeholder return statement with your code
        }

        // Method to add a number to the dictionary
        public void add(int number) {
          return;
        }
        // Method to find the pair of a target number
        public boolean find(int value) {
            return false;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "TwoSum,add,add,find,find|,9,4,13,12|,,,true,false",
            "TwoSum,add,add,find,find|,9,4,13,12|,,,true,false",
            "TwoSum,add,add,add,find,find|,5,6,4,11,9|,,,,true,true"
    })
        void test(String operStr, String argStr, String expectedStr){
            String[] operations = operStr.split(",");
            String[] arguments = argStr.split(",");
            String[] expected = expectedStr.split(",");
            TwoSum twoSum = new TwoSum();
            for (int i = 1; i< operations.length; i++){
                if(operations[i].equals("find")){
                    Assertions.assertEquals(Boolean.parseBoolean(expected[i]), twoSum.find(Integer.parseInt(arguments[i])));
                } else {
                    twoSum.add(Integer.parseInt(arguments[i]));
                }
            }
        }

}
