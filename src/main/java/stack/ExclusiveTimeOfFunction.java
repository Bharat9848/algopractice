package stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * We are given an integer number, n, representing the number of functions running in a single-threaded CPU, and an execution log, which is essentially a list of strings. Each string has the format {function id}:{"start" | "end"}:{timestamp}, indicating that the function with function id either started or stopped execution at the time identified by the timestamp value. Each function has a unique ID between 00 and n−1n−1. Compute the exclusive time of the functions in the program.
 *
 *     Note: The exclusive time is the sum of the execution times for all the calls to a specific function.
 *
 * Constraints:
 *
 *     1≤1≤ n ≤100≤100
 *     1≤1≤ logs.length ≤500≤500
 *     0≤0≤ function id << n
 *     0≤0≤ timestamp ≤103≤103
 *     No two start events and two end events will happen at the same timestamp.
 *     Each function has an end log entry for each start log entry.
 */
public class ExclusiveTimeOfFunction {

    public static List<Integer> exclusiveTime(int n, List<String> events) {
        var  exclusiveTimeList = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            exclusiveTimeList.add(0);
        }
        var stack = new Stack<FunctionMeta>();
        int lastFunctionEndTime = -1;
        int lastFunctionStartTime = -1;
        for (int i = 0; i < events.size(); i++) {
            String event = events.get(i);
            String[] tokens = event.split(":");
            if(tokens[1].equals("start")){
                if(!stack.empty()){
                    var temp = stack.pop();
                    if(temp.endTime == -1){
                        stack.push(new FunctionMeta(temp.id, temp.startTime, Integer.parseInt(tokens[2]) -1));
                    } else {
                        stack.push(temp);
                    }
                }
                stack.push(new FunctionMeta(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[2]), -1));
            } else {
                var functionMeta = stack.pop();
                var timeTaken = 0;
               if(functionMeta.endTime == -1){
                  timeTaken = Integer.parseInt(tokens[2]) - functionMeta.startTime + 1;
               } else {
                   var firstSliceTime = functionMeta.endTime - functionMeta.startTime + 1;
                   var secondSliceTime = Integer.parseInt(tokens[2]) - lastFunctionEndTime ;
                   timeTaken = firstSliceTime + secondSliceTime;
               }
               lastFunctionEndTime = Integer.parseInt(tokens[2]);
                exclusiveTimeList.set(functionMeta.id, exclusiveTimeList.get(functionMeta.id)+ timeTaken);
            }
        }
        return exclusiveTimeList;
    }
    record FunctionMeta(int id, int startTime, int endTime){}

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0:start:0,0:end:0,1:start:1,1:end:1,2:start:2,2:end:2,2:start:3,2:end:3|3|1,1,2",
        "0:start:0,1:start:6,1:end:6,0:end:7|2|7,1",
         "0:start:0,1:start:2,1:end:3,2:start:4,2:end:7,0:end:8|3|3,2,4"
    })
    void test(String execLogStr, int noOfProcess, String expectedStr){
        List<Integer> expected = Arrays.stream(expectedStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<String> events = Arrays.stream(execLogStr.split(",")).toList();
        Assertions.assertEquals(expected, exclusiveTime(noOfProcess, events));
    }
}
