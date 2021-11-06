package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
Implement FreqStack, a class which simulates the operation of a stack-like data structure.

FreqStack has two functions:

push(int x), which pushes an integer x onto the stack.
pop(), which removes and returns the most frequent element in the stack.
If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.


Example 1:

Input:
["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
[[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
Output: [null,null,null,null,null,null,null,5,7,5,4]
Explanation:
After making six .push operations, the stack is [5,7,5,7,4,5] from bottom to top.  Then:

pop() -> returns 5, as 5 is the most frequent.
The stack becomes [5,7,5,7,4].

pop() -> returns 7, as 5 and 7 is the most frequent, but 7 is closest to the top.
The stack becomes [5,7,5,4].

pop() -> returns 5.
The stack becomes [5,7,4].

pop() -> returns 4.
The stack becomes [5,7].


Note:

Calls to FreqStack.push(int x) will be such that 0 <= x <= 10^9.
It is guaranteed that FreqStack.pop() won't be called if the stack has zero elements.
The total number of FreqStack.push calls will not exceed 10000 in a single test case.
The total number of FreqStack.pop calls will not exceed 10000 in a single test case.
The total number of FreqStack.push and FreqStack.pop calls will not exceed 150000 across all test cases.

["FreqStack","push","push","push","push","push","push","pop","push","pop","push","pop","push","pop","push","pop","pop","pop","pop","pop","pop"]
[[],[4],[0],[9],[3],[4],[2],[],[6],[],[1],[],[1],[],[4],[],[],[],[],[],[]]
[n,n,   n,   n,  n, n,  n,  4, n,  6,  n ,1 ,n , 1 , n , 4, 2, 3, 9, 0, 4]

SortedDescFreqArrayFreq: (frequency1,elementValue1) -> (frequency2,elementValue2)
 Map<elementValue, freq>
LinkedList

 Difficult case to handle :
 */
class P895MinimumFreqStackNotDoneTLE {
    PriorityQueue<Map.Entry<Integer, Integer>> freqToElemVal = new PriorityQueue<>((e1, e2) -> -(e1.getKey().compareTo(e2.getKey())));
    Map<Integer, Map.Entry<Integer, Integer>> elementToFreq = new HashMap<>();
    List<Integer> stack = new LinkedList<>();
    public void push(int x) {
        stack.add(0, x);
        Map.Entry<Integer, Integer> freqVal = elementToFreq.get(x);
        Map.Entry<Integer, Integer> newFreqEntry;
        if(freqVal == null){
            newFreqEntry = new AbstractMap.SimpleEntry<>(1, x);
            freqToElemVal.offer(newFreqEntry);
        } else {
            freqToElemVal.remove(freqVal);
            newFreqEntry = new AbstractMap.SimpleEntry<>(freqVal.getKey() + 1, x);
            freqToElemVal.offer(newFreqEntry);
        }
        elementToFreq.put(x, newFreqEntry);
    }

    public int pop() {
//        System.out.printf("freqQueue = %s \n",freqToElemVal);
//        System.out.printf("stack == %s \n" ,stack);
        for (int i = 0; i < stack.size(); i++) {
            Integer element = stack.get(i);
            Map.Entry<Integer, Integer> freqEntry = elementToFreq.get(element);
            if(freqEntry.getKey().equals(freqToElemVal.peek().getKey())){
               freqToElemVal.remove(freqEntry);
               elementToFreq.remove(element);
               Map.Entry<Integer, Integer> newEntry = new AbstractMap.SimpleEntry<>(freqEntry.getKey()-1, freqEntry.getValue());
               if(newEntry.getKey() > 0){
                   elementToFreq.put(element, newEntry);
                   freqToElemVal.offer(newEntry);
               }
               stack.remove(element);
               return element;
            }
        }
        throw new RuntimeException();
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "push 5|push 7|push 5|push 7|push 4|push 5|pop|pop|pop|pop;5|7|5|4",
            "push 4|push 0|push 9|push 3|push 4|push 2|pop|push 6|pop|push 1|pop|push 1|pop|push 4|pop|pop|pop|pop|pop|pop;4|6|1|1|4|2|3|9|0|4"
    })
    void test(String operations, String popResult){
        int[] pops = Arrays.stream(popResult.split("\\|")).mapToInt(Integer::parseInt).toArray();
        String[] operationArr = operations.split("\\|");
        int popCount = 0;
        P895MinimumFreqStackNotDoneTLE freqStack =  new P895MinimumFreqStackNotDoneTLE();
        for(String op: operationArr){
            if("pop".equals(op)){
                int result = freqStack.pop();
                System.out.print(result+"===>");
                Assert.assertEquals(pops[popCount], result);
                popCount++;
            }else{
                freqStack.push(Integer.parseInt(op.split(" ")[1]));
            }
        }
    }

}
