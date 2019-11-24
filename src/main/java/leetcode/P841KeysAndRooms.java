package leetcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
public class P841KeysAndRooms {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int nRoom = rooms.size();
        int[] visited = new int[nRoom];
        visited[0] = 1;
        return canVisitAllRooms(rooms, -1,0,visited);
    }

    private boolean canVisitAllRooms(List<List<Integer>> rooms, int src, int dest, int[] visited) {
        visited[dest] = 1;
        if(allvisited(visited)){return true;}
        for (Integer d : rooms.get(dest)){
            if(visited[d] == 0){
                if(canVisitAllRooms(rooms,dest,d,visited)){
                    return true;
                }
            }
        }
        visited[dest] = 2;
        return false;
    }

    private boolean allvisited(int[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if(visited[i]==0){return false;}
        }
        return true;
    }

    @Test
    public void test(){
        assertTrue(canVisitAllRooms(Arrays.asList(Arrays.asList(1),Arrays.asList(2),Arrays.asList(3), Collections.emptyList())));
        assertFalse(canVisitAllRooms(Arrays.asList(Arrays.asList(1,3),Arrays.asList(3,0,1),Arrays.asList(2), Arrays.asList(0))));
        assertTrue(canVisitAllRooms(Arrays.asList(Arrays.asList(2,3),Arrays.asList(),Arrays.asList(2), Arrays.asList(1,3,1))));
    }
}
