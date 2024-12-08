package heap.two.heap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Create a data structure that can store a list of integers that can change in size over time and find the median from this dynamically growing list in constant time, O(1)O(1).
 * <p>
 * Implement a class, MedianOfStream, which should support the following operations:
 * <p>
 * Constructor(): This initializes the object of this class, which in turn creates the max and the min heap.
 * <p>
 * Insert Num(num): This adds an integer, num, to the data structure.
 * <p>
 * Find Median(): This finds the median of all elements seen so far. If there are an even number of elements, return the average of the two middle values.
 * <p>
 * Constraints:
 * <p>
 * −105<= num ≤105, where num is an integer received from the data stream.
 * <p>
 * There will be at least one element in the data structure before the median is computed.
 * <p>
 * At most, 500 calls will be made to the function that calculates the median.
 */
public class MedianOfStream {
    PriorityQueue<Integer> smallList;
    PriorityQueue<Integer> largeList;
    double currentMin = 0.0;

    public MedianOfStream() {
        smallList = new PriorityQueue<>(Comparator.comparingInt(Integer::intValue).reversed());
        largeList = new PriorityQueue<>(Comparator.comparingInt(Integer::intValue));
    }

    public void insertNum(int num) {
        if (smallList.isEmpty() && largeList.isEmpty()) {
            currentMin = num;
            smallList.offer(num);
        } else {
            if(currentMin < num){
                largeList.offer(num);
            } else {
                smallList.offer(num);
            }
            rebalance();
            calculateMedian();
        }
    }

    private void rebalance() {
       if(largeList.size() == smallList.size() + 2){
           smallList.offer(largeList.remove());
       }else if(largeList.size() + 2 == smallList.size()){
           largeList.offer(smallList.remove());
       }
    }

    private void calculateMedian() {
        var smallTop = !smallList.isEmpty() ? smallList.peek(): 0.0;
        var largeTop = !largeList.isEmpty() ? largeList.peek(): 0.0;
        if(largeList.size() == smallList.size()){
            currentMin = (smallTop + largeTop)/2.0;
        }else {
            currentMin = largeList.size() > smallList.size() ? largeTop: smallTop;
        }
    }

    public double findMedian() {
        return currentMin;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "insertNum,insertNum,findMedian,insertNum,findMedian|22,35,,30,,|,,28.5,,30",
            "insertNum,findMedian,insertNum,findMedian,insertNum,findMedian,insertNum,findMedian|15,,7,,3,,1|,15,,11,,7,,5",
            "insertNum,findMedian,insertNum,findMedian,insertNum,findMedian,insertNum,findMedian|15,,47,,31,,90|,15,,31,,31,,39.0",
            "insertNum,findMedian,insertNum,findMedian,insertNum,findMedian|22,,35,,30,|,22,,28.5,,30",
    })
    void test(String operationStr, String argumentStr, String expectedStr) {
        var operations = operationStr.split(",");
        var arguments = Arrays.stream(argumentStr.split(",")).map(s -> !s.isBlank()?s:"-1").mapToInt(Integer::parseInt).toArray();
        var expected = Arrays.stream(expectedStr.split(",")).map(s -> !s.isBlank()?s:"-1" ).mapToDouble(Double::parseDouble).toArray();
        var median = new MedianOfStream();
        for (int i = 0; i < operations.length; i++) {
            String op = operations[i];
            switch (op) {
                case "insertNum":
                    median.insertNum(arguments[i]);
                    break;
                case "findMedian":
                    Assertions.assertEquals(expected[i], median.findMedian());
            }
        }
    }
}
