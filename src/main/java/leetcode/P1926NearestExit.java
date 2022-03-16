package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;

/**
 * You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls (represented as '+'). You are also given the entrance of the maze, where entrance = [entrancerow, entrancecol] denotes the row and column of the cell you are initially standing at.
 *
 * In one step, you can move one cell up, down, left, or right. You cannot step into a cell with a wall, and you cannot step outside the maze. Your goal is to find the nearest exit from the entrance. An exit is defined as an empty cell that is at the border of the maze. The entrance does not count as an exit.
 *
 * Return the number of steps in the shortest path from the entrance to the nearest exit, or -1 if no such path exists.
 * Input: maze = [["+","+",".","+"],[".",".",".","+"],["+","+","+","."]], entrance = [1,2]
 * Output: 1
 * Input: maze = [["+","+","+"],[".",".","."],["+","+","+"]], entrance = [1,0]
 * Output: 2
 * Input: maze = [[".","+"]], entrance = [0,0]
 * Output: -1
 * Explanation: There are no exits in this maze.
 *
 *
 *
 * Constraints:
 *
 *     maze.length == m
 *     maze[i].length == n
 *     1 <= m, n <= 100
 *     maze[i][j] is either '.' or '+'.
 *     entrance.length == 2
 *     0 <= entrancerow < m
 *     0 <= entrancecol < n
 *     entrance will always be an empty cell.
 *
 *     a -> b <- c
 */
class P1926NearestExit {
    int nearestExit(char[][] maze, int[] entrance) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        Set<Pair<Integer, Integer>> queue = new HashSet<>();
        queue.add(new Pair<>(entrance[0], entrance[1]));
        visited[entrance[0]][entrance[1]] = true;
        Set<Pair<Integer, Integer>> nonEntranceQueue = new HashSet<>();
        for(int i =0; i<maze.length; i++){
            if(maze[i][0] == '.' && notEqualToEntrance(entrance, i ,0)){
                nonEntranceQueue.add(new Pair<>(i, 0));
                visited[i][0] = true;
            }
            if(maze[i][maze[0].length-1] == '.' && notEqualToEntrance(entrance, i ,maze[0].length-1)){
                nonEntranceQueue.add(new Pair<>(i, maze[0].length-1));
                visited[i][maze[0].length-1] = true;
            }
        }
        for (int i = 0; i < maze[0].length; i++) {
            if(maze[0][i] == '.' && notEqualToEntrance(entrance, 0 ,i)){
                nonEntranceQueue.add(new Pair<>(0, i));
                visited[0][i] = true;
            }
            if(maze[maze.length-1][i] == '.' && notEqualToEntrance(entrance, maze.length-1,i)){
                nonEntranceQueue.add(new Pair<>(maze.length-1, i));
                visited[maze.length-1][i] = true;
            }
        }
        if(nonEntranceQueue.isEmpty()){
            return -1;
        }
        int steps = 0;
        while (!nonEntranceQueue.isEmpty() && !queue.isEmpty()){
            Set<Pair<Integer, Integer>> entranceNextLevel = new HashSet<>();
            Iterator<Pair<Integer, Integer>> entranceLevelIterator = queue.iterator();
            while (entranceLevelIterator.hasNext()){
                Pair<Integer, Integer> rowCol = entranceLevelIterator.next();
                int row = rowCol.getFirst();
                int col = rowCol.getSec();
                int[][] neighbours = new int[][]{{row+1,col}, {row-1,col}, {row,col-1}, {row, col+1}};
                for (int[] neigh: neighbours){
                    int r = neigh[0];
                    int c = neigh[1];
                    if( r >=0 && c >=0 && r < maze.length && c < maze[0].length && maze[r][c] == '.' && !visited[r][c]){
                        entranceNextLevel.add(new Pair<>(r,c));
                        visited[r][c] = true;
                    }
                }
            }
            Iterator<Pair<Integer, Integer>> queueIterator = nonEntranceQueue.iterator();
            Set<Pair<Integer, Integer>> nextLevel = new HashSet<>();
            Set<Pair<Integer, Integer>> nextLevelAlreadyVisited = new HashSet<>();
            while (queueIterator.hasNext()){
                Pair<Integer, Integer> curr = queueIterator.next();
                int row = curr.getFirst();
                int col = curr.getSec();
                int[][] neighbours = new int[][]{{row+1,col}, {row-1,col}, {row,col-1}, {row, col+1}};
                for (int[] neigh: neighbours){
                    int r = neigh[0];
                    int c = neigh[1];
                    if( r >=0 && c >=0 && r < maze.length && c < maze[0].length && maze[r][c] == '.'){
                        if(!visited[r][c]){
                            nextLevel.add(new Pair<>(r,c));
                            visited[r][c] = true;
                        }else{
                            nextLevelAlreadyVisited.add(new Pair<>(r,c));
                        }
                    }
                }
            }
            Set<Pair<Integer, Integer>> finalQueue = queue;
            if(nextLevelAlreadyVisited.stream().anyMatch(a -> finalQueue.contains(a))){
                return steps*2+1;
            }
            if(nextLevelAlreadyVisited.stream().anyMatch(a -> entranceNextLevel.contains(a))){
                return steps*2+2;
            }
            queue = entranceNextLevel;
            nonEntranceQueue = nextLevel;
            steps++;
        }
        return -1;
    }

    private boolean notEqualToEntrance(int[] entrance, int r, int c) {
        return  !(r == entrance[0] && c == entrance[1]);
    }

    int nearestExitTLE(char[][] maze, int[] entrance) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(entrance[0], entrance[1]));
        int steps = 0;
        while (!queue.isEmpty()){
            Queue<Pair<Integer, Integer>> nextLevel = new LinkedList<>();
            while (!queue.isEmpty()){
                Pair<Integer, Integer> curr = queue.remove();
                int row = curr.getFirst();
                int col = curr.getSec();
                if((row == 0 || row == maze.length-1 || col == 0 || col == maze[0].length-1) && !(row==entrance[0] && col == entrance[1])){
                    return steps;
                }
                int[][] neighbours = new int[][]{{row+1,col}, {row-1,col}, {row,col-1}, {row, col+1}};
                for (int[] neigh: neighbours){
                    int r = neigh[0];
                    int c = neigh[1];
                    if( r >=0 && c >=0 && r < maze.length && c < maze[0].length && maze[r][c] == '.' && !visited[r][c]){
                        nextLevel.add(new Pair<>(r,c));
                    }
                }
            }
            queue = nextLevel;
            steps++;
        }
        return -1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[+,.,.],[+,.,+],[.,.,+]]|2,1|1|mutiple answer choose the min path",
            "[[.,+,+,+],[+,+,+,+],[+,+,+,.],[+,.,.,+]]|0,0|-1|Path between two exit but not from entrance",
            "[[+,.,+,+,+,+,+],[+,.,+,.,.,.,+],[+,.,+,.,+,.,+],[+,.,.,.,+,.,+],[+,+,+,+,+,.,+]]|0,1|12|A long path",
            "[[.,.]]|0,1|1|entrance is at exit level",
            "[[+,+,.,+],[.,.,.,+],[+,+,+,.]]|1,2|1|entrance in middle of matrix",
            "[[+,+,+],[.,.,.],[+,+,+]]|1,0|2|entrance at outer layer",
            "[[.,+]]|0,0|-1|No exit",
            "[[.]]|0,0|-1|No exit",
    })
    void test(String mazeStr, String entranceStr, int expected, String msg){
        char[][] maze = Arrays.stream(mazeStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .map(s -> {
                    String[] charstr = s.split(",");
                    char[] chars = new char[charstr.length];
                    for (int i = 0; i < charstr.length; i++) {
                        if("+".equals(charstr[i])){
                            chars[i] = '+';
                        }else {
                            chars[i] = '.';
                        }
                    }
                    return chars;
                }).toArray(char[][]::new);
        int[] entrance = Arrays.stream(entranceStr.split(",")).mapToInt(Integer::parseInt).toArray();
        System.out.println(msg);
        Assertions.assertEquals(expected, nearestExit(maze, entrance));
    }
}
