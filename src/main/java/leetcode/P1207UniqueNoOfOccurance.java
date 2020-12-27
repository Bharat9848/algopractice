package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/*
Given an array of integers arr, write a function that returns true if and only if the number of occurrences of each value in the array is unique.



Example 1:

Input: arr = [1,2,2,1,1,3]
Output: true
Explanation: The value 1 has 3 occurrences, 2 has 2 and 3 has 1. No two values have the same number of occurrences.
Example 2:

Input: arr = [1,2]
Output: false
Example 3:

Input: arr = [-3,0,1,-3,1,1,1,-3,10,0]
Output: true


Constraints:

1 <= arr.length <= 1000
-1000 <= arr[i] <= 1000
 */
public class P1207UniqueNoOfOccurance {
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> freqMap = Arrays.stream(arr).boxed().collect(Collectors.toMap(key -> key, value -> 1, (value1, value2)-> value1+value2));
        BiConsumer<Map<Boolean, Set<Integer>>, Integer> biConsumer =  (Map<Boolean, Set<Integer>> map, Integer i) -> {
            //true represent uniqueKeySet
            map.computeIfAbsent(true, k -> new HashSet<>());
            map.computeIfAbsent(false, k -> new HashSet<>());
            boolean seenItBefore = map.get(true).contains(i);
            if(seenItBefore){
                map.get(false).add(i);
            }else{
                map.get(true).add(i);
            }

        };

        Map<Boolean, Set<Integer>> result =  freqMap.values().stream().collect(HashMap::new, biConsumer
                , (map1, map2) -> {
                    map1.get(true).addAll(map2.get(true));
                    map1.get(false).addAll(map2.get(false));
                });
        System.out.println(result.get(false).isEmpty());
        System.out.println("duplicated keys " + result.get(false));
        return result.get(false).isEmpty();
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2,2,1,1,3; true",
                        "1,2;false",
    "-3,0,1,-3,1,1,1,-3,10,0; true"}, delimiter = ';')
    void test(String intArrStr, boolean result){
        int[] arr = Arrays.stream(intArrStr.split(","))
                .mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(uniqueOccurrences(arr), result);
    }
}
