package leetcode;

import core.ds.Graph;
import org.junit.Assert;
import org.junit.Test;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

 Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

 Example 1:
 [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
 Example 2:
 [[0,0,0,0,0,0,0,0]]
 Given the above grid, return 0.
 Note: The length of each dimension in the given grid does not exceed 50.
 * Created by bharat on 27/5/18.
 */
public class P695MaxAreaIsland {


    public int maxAreaOfIsland(int[][] grid) {
        int area = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                if(grid[i][j]==0||(j-1 >= 0 && grid[i][j]==1 && grid[i][j-1]==1)
                        ||(i-1 >= 0 && grid[i][j]==1 && grid[i-1][j]==1)){
                    continue;
                }else{
                    int temp = calArea(grid,i,j);
                    if(temp>area){
                        area = temp;
                    }
                }

            }
        }
        return area;
    }

    private int calArea(int[][] grid, int i, int j) {
        int area=1;
        HashMap<Pair<Integer,Integer>,Graph.Label> seenGraph= new HashMap<>();
        seenGraph.put(new Pair<>(i,j), Graph.Label.DISCOVERED);
        List<Pair<Integer,Integer>> queue = new ArrayList<>();
        queue.add(new Pair<>(i,j));
        while (! queue.isEmpty()){
            Pair<Integer,Integer> u = queue.remove(0);
            if(u.getFirst()+1<grid.length && grid[u.getFirst()+1][u.getSec()]==1 && seenGraph.get(new Pair<>(u.getFirst()+1, u.getSec()))==null){
                area+=1;
                seenGraph.put(new Pair<>(u.getFirst()+1, u.getSec()), Graph.Label.DISCOVERED);
                queue.add(new Pair<>(u.getFirst()+1,u.getSec()));
            }

            if(u.getSec()+1<grid[0].length && grid[u.getFirst()][u.getSec()+1]==1 && seenGraph.get(new Pair<>(u.getFirst(), u.getSec()+1))==null){
                area+=1;
                seenGraph.put(new Pair<>(u.getFirst(), u.getSec()+1), Graph.Label.DISCOVERED);
                queue.add(new Pair<>(u.getFirst(),u.getSec()+1));
            }

            if(u.getFirst()-1>=0 && grid[u.getFirst()-1][u.getSec()]==1 && seenGraph.get(new Pair<>(u.getFirst()-1, u.getSec()))==null){
                area+=1;
                seenGraph.put(new Pair<>(u.getFirst()-1, u.getSec()), Graph.Label.DISCOVERED);
                queue.add(new Pair<>(u.getFirst()-1,u.getSec()));
            }

            if(u.getSec()-1>=0 && grid[u.getFirst()][u.getSec()-1]==1 && seenGraph.get(new Pair<>(u.getFirst(), u.getSec()-1))==null){
                area+=1;
                seenGraph.put(new Pair<>(u.getFirst(), u.getSec()-1), Graph.Label.DISCOVERED);
                queue.add(new Pair<>(u.getFirst(),u.getSec()-1));
            }
        }
        return area;
    }

    @Test
    public void test123(){
        Assert.assertEquals(4, maxAreaOfIsland(new int[][]{{0,0,1,0,0,0,0,1,0},{0,0,0,0,0,0,1,1,1}}));
        Assert.assertEquals(0, maxAreaOfIsland(new int[][]{}));
        Assert.assertEquals(0, maxAreaOfIsland(new int[0][]));
        Assert.assertEquals(2, maxAreaOfIsland(new int[][]{{1,1,0,1}}));
    }
}
