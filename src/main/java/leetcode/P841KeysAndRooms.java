package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * There are N rooms and you start in room 0.  Each room has a distinct number in 0, 1, 2, ..., N-1, and each room may have some keys to access the next room.

 Formally, each room i has a list of keys rooms[i], and each key rooms[i][j] is an integer in [0, 1, ..., N-1] where N = rooms.length.  A key rooms[i][j] = v opens the room with number v.

 Initially, all the rooms start locked (except for room 0).

 You can walk back and forth between rooms freely.

 Return true if and only if you can enter every room.

 Example 1:

 Input: [[1],[2],[3],[]]
 Output: true
 Explanation:
 We start in room 0, and pick up key 1.
 We then go to room 1, and pick up key 2.
 We then go to room 2, and pick up key 3.
 We then go to room 3.  Since we were able to go to every room, we return true.
 Example 2:

 Input: [[1,3],[3,0,1],[2],[0]]
 Output: false
 Explanation: We can't enter the room with number 2.
 Note:

 1 <= rooms.length <= 1000
 0 <= rooms[i].length <= 1000
 The number of keys in all rooms combined is at most 3000.

 * Created by bharat on 9/6/18.
 */
 class P841KeysAndRooms {
     boolean canVisitAllRooms(List<List<Integer>> rooms) {
         boolean[] visited = new boolean[rooms.size()];
         canVisit(0, rooms, visited);
         boolean reached = true;
         for (int i = 0; i < visited.length && reached; i++) {
             reached = visited[i];
         }
         return reached;
     }

    private void canVisit(int src, List<List<Integer>> rooms, boolean[] visited) {
        List<Integer> keys = rooms.get(src);
        visited[src] = true;
        for (Integer key: keys){
            if(!visited[key]){
                canVisit(key, rooms, visited);
            }
        }
     }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,3],[3,0,1],[2],[0]]|false",
            "[[1],[2],[3],[]]|true",
    })
     void test(String keysStr, boolean expected){
        List<List<Integer>> rooms = Arrays.stream(keysStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .map(s -> s.isEmpty()? Collections.<Integer>emptyList():Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
        Assertions.assertEquals(expected, canVisitAllRooms(rooms));
     }
}
