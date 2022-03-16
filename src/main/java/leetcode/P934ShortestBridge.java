package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;

/**
 * ou are given an n x n binary matrix grid where 1 represents land and 0 represents water.
 *
 * An island is a 4-directionally connected group of 1's not connected to any other 1's. There are exactly two islands in grid.
 *
 * You may change 0's to 1's to connect the two islands to form one island.
 *
 * Return the smallest number of 0's you must flip to connect the two islands.
 *
 * Input: grid = [[0,1],[1,0]]
 * Output: 1
 * Input: grid = [[0,1,0],[0,0,0],[0,0,1]]
 * Output: 2
 * Input: grid = [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
 * Output: 1
 *
 *     n == grid.length == grid[i].length
 *     2 <= n <= 100
 *     grid[i][j] is either 0 or 1.
 *     There are exactly two islands in grid.
 *
 *
 *     Strategy will be to discover all nodes for both island using DFS and then use BFS to discover the shortest path between two island
 *
 */
public class P934ShortestBridge {
    int shortestBridge(int[][] grid) {
        List<Set<Pair<Integer,Integer>>> islands = new ArrayList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(!visited[i][j] && grid[i][j] == 1){
                    islands.add(findIslandUsingDFS(i, j, grid, visited));
                }
            }
        }
        if(islands.size() != 2){
            return -1;
        }else {
            Set<Pair<Integer, Integer>> island1 = islands.get(0);
            System.out.println(island1);
            Set<Pair<Integer, Integer>> island2 = islands.get(1);
            System.out.println(island2);
            return searchForShortestPath(island1, island2, grid);
        }
    }

    private int searchForShortestPath(Set<Pair<Integer, Integer>> island1, Set<Pair<Integer, Integer>> island2, int[][] grid) {
        int steps = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        while (!island1.isEmpty() && !island2.isEmpty()){
            Set<Pair<Integer, Integer>> island1Next = new HashSet<>();
            for (Pair<Integer, Integer> cell: island1){
                int row = cell.getFirst();
                int col = cell.getSec();
                int[][] neighbours = new int[][]{{row+1,col},{row-1,col},{row,col+1},{row,col-1}};
                for(int[] neigh : neighbours){
                    int r = neigh[0];
                    int c =  neigh[1];
                    if(r >=0 && c >=0 && r < grid.length && c < grid[0].length && grid[r][c] == 0 && !visited[r][c]){
                        island1Next.add(new Pair<>(r, c));
                        visited[r][c] = true;
                    }
                }
            }
            Set<Pair<Integer, Integer>> island2Next = new HashSet<>();
            Set<Pair<Integer, Integer>> island2NextVisited = new HashSet<>();
            for (Pair<Integer, Integer> cell: island2){
                int row = cell.getFirst();
                int col = cell.getSec();
                int[][] neighbours = new int[][]{{row+1,col},{row-1,col},{row,col+1},{row,col-1}};
                for(int[] neigh : neighbours){
                    int r = neigh[0];
                    int c =  neigh[1];
                    if(r >=0 && c >=0 && r < grid.length && c < grid[0].length && grid[r][c] == 0){
                        if(visited[r][c]){
                            island2NextVisited.add(new Pair<>(r,c));
                        }else {
                            island2Next.add(new Pair<>(r, c));
                            visited[r][c] = true;
                        }
                    }
                }
            }
            if(island2NextVisited.stream().anyMatch(island1::contains)){
                return steps;
            }
            if(island2NextVisited.stream().anyMatch(island1Next::contains)){
                return steps+1;
            }
            steps+=2;
            island1 = island1Next;
            island2 = island2Next;

        }
        return steps;
    }

    private Set<Pair<Integer, Integer>> findIslandUsingDFS(int row1, int col1, int[][] grid, boolean[][] visited) {
        Set<Pair<Integer, Integer>> island = new HashSet<>();
        Stack<Pair<Integer, Integer>> toProcess = new Stack<>();
        toProcess.add(new Pair<>(row1, col1));
        while (!toProcess.isEmpty()){
            Pair<Integer, Integer> current = toProcess.peek();
            Integer row = current.getFirst();
            Integer col = current.getSec();
            visited[row][col] = true;
            boolean allNeighbourVisited = true;
            int[][] neighbours = new int[][]{{row+1,col}, {row -1, col},{row, col+1},{row, col-1}};
            for (int[] neigh: neighbours){
                int r = neigh[0];
                int c = neigh[1];
                if(r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && !visited[r][c] && grid[r][c] == 1){
                    toProcess.add(new Pair<>(r,c));
                    allNeighbourVisited = false;
                    break;
                }
            }
            if (allNeighbourVisited){
                island.add(current);
                toProcess.pop();
            }
        }

        return island;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0,1],[1,0]]|1|all path are smallest",
            "[[0,1,0],[0,0,0],[0,0,1]]|2|multiple path are smallest",
            "[[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]|1|One island enclosed other island"
    })
    void test(String graphStr, int expected, String msg){
        int[][] grid = Arrays.stream(graphStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        System.out.println(msg);
        Assertions.assertEquals(expected,shortestBridge(grid));
    }
}
