package data.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class MovingAverageStream {
    class MovingAverage {
        double sum;
        int size;
        int currentSize;
        Deque<Integer> list;
        public MovingAverage(int size) {
            this.size = size;
            this.list = new LinkedList<>();
            this.sum = 0;
            this.currentSize = 0;
        }

        public double next(int val) {
            if(currentSize == size){
                var removed = list.removeFirst();
                sum -= removed;
                currentSize--;
            }
            currentSize++;
            list.addLast(val);
            sum += val;
            return sum/currentSize;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "movingAverage,next|3,1,2|null,1.0,1.5",
            "movingAverage,next|3,1,2,3,4,5|null,1.0,1.5,2.0,3.5,4.0",
    })
    void test(String operStr, String argStr, String expectedStr){
        String[] operations = operStr.split(",");
        int[] arguments = Arrays.stream(argStr.split(",")).map(s -> s == null? "-1":s).mapToInt(Integer::parseInt).toArray();
        double[] expected = Arrays.stream(expectedStr.split(",")).map(s -> s.equalsIgnoreCase("null")? "-1":s).mapToDouble(Double::parseDouble).toArray();
        MovingAverage movingAverage = new MovingAverage(arguments[0]);
        for(int i =1;  i< operations.length; i++){
            var answer = movingAverage.next(arguments[i]);
            Assertions.assertEquals(answer, expected[i]);
        }
    }
}
