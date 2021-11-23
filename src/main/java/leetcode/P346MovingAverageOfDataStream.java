package leetcode;

import core.ds.ListNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.
 *
 * Implement the MovingAverage class:
 *
 *     MovingAverage(int size) Initializes the object with the size of the window size.
 *     double next(int val) Returns the moving average of the last size values of the stream.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["MovingAverage", "next", "next", "next", "next"]
 * [[3], [1], [10], [3], [5]]
 * Output
 * [null, 1.0, 5.5, 4.66667, 6.0]
 *
 * Explanation
 * MovingAverage movingAverage = new MovingAverage(3);
 * movingAverage.next(1); // return 1.0 = 1 / 1
 * movingAverage.next(10); // return 5.5 = (1 + 10) / 2
 * movingAverage.next(3); // return 4.66667 = (1 + 10 + 3) / 3
 * movingAverage.next(5); // return 6.0 = (10 + 3 + 5) / 3
 *
 *
 *
 * Constraints:
 *
 *     1 <= size <= 1000
 *     -10^5 <= val <= 10^5
 *     At most 10^4 calls will be made to next.
 */
public class P346MovingAverageOfDataStream {
    private class MovingAverage {

        int size;
        ListNode head, tail;
        final int MAX_SIZE;
        double total;
        public MovingAverage(int size) {
            MAX_SIZE = size;
            //dummyNode
            head = new ListNode(Integer.MAX_VALUE);
            tail = head;
            total = 0;
        }

        public double next(int val) {
            if(size < MAX_SIZE){
                size++;
            }else{
                int valToSubtract = deleteHeadNode(head);
                total -= valToSubtract;
            }
            total +=val;
            tail.next = new ListNode(val);
            tail = tail.next;
            return total/size;
        }

        private int deleteHeadNode(ListNode head) {
            ListNode toDelete = head.next;
            head.next = toDelete.next;
            toDelete.next = null;
            return toDelete.val;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "MovingAverage,next,next,next,next|3,1,10,3,5|null,1.0,5.5,4.66667,6.0"
    })
    void test(String operationStr, String argStr, String expectedStr){
        MovingAverage movingAvg = null;
        String[] operations = operationStr.split(",");
        String[] input = argStr.split(",");
        String[] expected = expectedStr.split(",");
        for (int i = 0; i < operations.length; i++) {
            String opr = operations[i];
            switch (opr) {
                case "MovingAverage" :{
                     movingAvg = new MovingAverage(Integer.parseInt(input[i]));
                    break;
                }
                case "next": {
                    Assert.assertEquals(Double.parseDouble(expected[i]), movingAvg.next(Integer.parseInt(input[i])),0.0001);
                    break;
                }
            }
        }

    }

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */
}
