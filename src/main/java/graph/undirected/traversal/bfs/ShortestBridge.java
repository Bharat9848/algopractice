package graph.undirected.traversal.bfs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.regex.Pattern;

public class ShortestBridge {

    public static int shortestBridge(int[][] grid){

        var islandPair = findIsland(grid);
        var firstIslandQueue = new HashSet<>(islandPair.getFirst());
        var secondIslandQueue = new HashSet<>(islandPair.getSec());
        int level = 0;
        while (!firstIslandQueue.isEmpty()){
            var firstTempQueue = new HashSet<Pair<Integer, Integer>>();
            var secTempQueue = new HashSet<Pair<Integer, Integer>>();
            var firstIterator = firstIslandQueue.iterator();
            var secIterator = secondIslandQueue.iterator();
            while (firstIterator.hasNext() || secIterator.hasNext()){
                if(firstIterator.hasNext()){
                    var cell1 = firstIterator.next();
                    int x = cell1.getFirst();
                    int y = cell1.getSec();
                    if(secondIslandQueue.contains(cell1)){
                        return 2*level -1;
                    }
                    Integer level1 = processNode(grid, x, y, firstIslandQueue, secondIslandQueue, level, firstTempQueue);
                    if (level1 != null) return level1;
                }

                if(secIterator.hasNext()){
                    var cell2 = secIterator.next();
                    int x1 = cell2.getFirst();
                    int y1 = cell2.getSec();
                    if(firstIslandQueue.contains(cell2)){
                        return 2*level -1;
                    }
                    Integer level2 = processNode(grid, x1, y1, secondIslandQueue, firstIslandQueue, level, secTempQueue);
                    if (level2 != null) return level2;
                }

            }
            if(!firstTempQueue.isEmpty()){
                firstIslandQueue = firstTempQueue;
            }
            if(!secTempQueue.isEmpty()){
                secondIslandQueue = secTempQueue;
            }

            level++;
        }


        return -1;
    }

    private static Integer processNode(int[][] grid, int x, int y, HashSet<Pair<Integer, Integer>> firstIslandVisited, HashSet<Pair<Integer, Integer>> secondIslandVisited, int level, Set<Pair<Integer, Integer>> firstTempQueue) {
        if(x + 1 < grid.length && grid[x +1][y] == 0 && !firstIslandVisited.contains(new Pair<>(x +1, y))){
            if(secondIslandVisited.contains(new Pair<>(x+1, y))){
                    return 2 * level;
            }
            firstTempQueue.add(new Pair<>(x +1, y));
        }
        if(x - 1 >= 0 && grid[x -1][y] == 0 && !firstIslandVisited.contains(new Pair<>(x -1, y))){
            if(secondIslandVisited.contains(new Pair<>(x-1, y))){
                    return 2 * level;
            }
            firstTempQueue.add(new Pair<>(x -1, y));
        }
        if(y + 1 < grid[0].length && grid[x][y +1] == 0 && !firstIslandVisited.contains(new Pair<>(x, y +1))){
            if(secondIslandVisited.contains(new Pair<>(x, y+1))){
                    return 2 * level;
            }
            firstTempQueue.add(new Pair<>(x, y + 1));
        }
        if(y - 1 >= 0 && grid[x][y -1] == 0 && !firstIslandVisited.contains(new Pair<>(x, y -1))){
            if(secondIslandVisited.contains(new Pair<>(x, y-1))){
                    return 2 * level;
            }
            firstTempQueue.add(new Pair<>(x, y-1));
        }
        return null;
    }

    private static Pair<List<Pair<Integer, Integer>>, List<Pair<Integer, Integer>>> findIsland(int[][] grid){
        List<Pair<Integer, Integer>> first = null;
        List<Pair<Integer, Integer>> sec = null;
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == 1 && !visited.contains(new Pair<>(i,j))){
                    List<Pair<Integer, Integer>> island = discoverIsland(grid, i, j, visited);
                    if(first == null){
                        first = island;
                    }else {
                        sec = island;
                    }
                }
            }
        }
        return new Pair<>(first, sec);
    }

    private static List<Pair<Integer, Integer>> discoverIsland(int[][] grid, int i, int j, Set<Pair<Integer, Integer>> visited) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        Stack<Pair<Integer, Integer>> stack = new Stack<>();
        stack.add(new Pair<>(i, j));
        while (!stack.isEmpty()){
            var node = stack.peek();
            int x = node.getFirst();
            int y = node.getSec();
            visited.add(new Pair<>(x, y));
            if(x + 1 < grid.length && grid[x +1][y] == 1 && !visited.contains(new Pair<>(x +1, y))){
                stack.push(new Pair<>(x +1, y));
                continue;
            }
            if(x - 1 >= 0 && grid[x -1][y] == 1 && !visited.contains(new Pair<>(x -1, y))){
                stack.push(new Pair<>(x -1, y));
                continue;
            }
            if(y + 1 < grid[0].length && grid[x][y +1] == 1 && !visited.contains(new Pair<>(x, y +1))){
                stack.push(new Pair<>(x, y +1));
                continue;
            }
            if(y - 1 >= 0 && grid[x][y -1] == 1 && !visited.contains(new Pair<>(x, y -1))){
                stack.push(new Pair<>(x, y -1));
                continue;
            }
            result.add(stack.pop());
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0,1,1],[1,0,1],[1,0,1]]|1",
            "[[1,0,0,1]]|2",
            "[[1,0,0,0,1]]|3",
            "[[1,0,1],[1,0,1]]|1",
            "[[1,0,0],[0,0,1]]|2",
            "[[1,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,1]]|5",
    })
    void test(String arrStr, int expected){
        var pattern = Pattern.compile("(\\[(\\d,)+\\d\\])");
        var matcher = pattern.matcher(arrStr);
        int[][] grid = matcher.results()
                .map(matchResult -> matcher.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).mapToInt(ch -> Integer.parseInt(ch)).toArray();
                        }
                )
                .toArray(int[][]::new);
        Assertions.assertEquals(expected, shortestBridge(grid));
    }
}
