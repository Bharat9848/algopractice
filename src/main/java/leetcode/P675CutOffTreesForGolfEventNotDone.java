package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * You are asked to cut off all the trees in a forest for a golf event. The forest is represented as an m x n matrix. In this matrix:
 *
 *     0 means the cell cannot be walked through.
 *     1 represents an empty cell that can be walked through.
 *     A number greater than 1 represents a tree in a cell that can be walked through, and this number is the tree's height.
 *
 * In one step, you can walk in any of the four directions: north, east, south, and west. If you are standing in a cell with a tree, you can choose whether to cut it off.
 *
 * You must cut off the trees in order from shortest to tallest. When you cut off a tree, the value at its cell becomes 1 (an empty cell).
 *
 * Starting from the point (0, 0), return the minimum steps you need to walk to cut off all the trees. If you cannot cut off all the trees, return -1.
 *
 * You are guaranteed that no two trees have the same height, and there is at least one tree needs to be cut off.
 * Input: forest = [[1,2,3],[0,0,4],[7,6,5]]
 * Output: 6
 * Explanation: Following the path above allows you to cut off the trees from shortest to tallest in 6 steps.
 *
 *
 * Input: forest = [[1,2,3],[0,0,0],[7,6,5]]
 * Output: -1
 * Explanation: The trees in the bottom row cannot be accessed as the middle row is blocked.
 *
 *
 * Input: forest = [[2,3,4],[0,0,5],[8,7,6]]
 * Output: 6
 * Explanation: You can follow the same path as Example 1 to cut off all the trees.
 * Note that you can cut off the first tree at (0, 0) before making any steps.
 *
 *
 *1->2->3->4 BFS = DFS
 *
 * Constraints:
 *
 *     m == forest.length
 *     n == forest[i].length
 *     1 <= m, n <= 50
 *     0 <= forest[i][j] <= 10^9
 *
 *  cut off all the trees I need to visit the trees in an order fashion which is in ordered fashion. that mean from a tree of a height 8 I can only go to tree of height 9 not 10 or 11. It is a sparse Graph as there is constraint of walking only in strict height order fashion. All edges of with edges 1
 */
public class P675CutOffTreesForGolfEventNotDone {
    private enum DiscoverEnum{
        EXPLORED, DISCOVERED, UNDISCOVERED
    }
    int cutOffTree(List<List<Integer>> forest) {
        DiscoverEnum[][] discovered = new DiscoverEnum[forest.size()][forest.get(0).size()];
        Arrays.stream(discovered).forEach(row -> Arrays.fill(row, DiscoverEnum.UNDISCOVERED));
        discovered[0][0] = DiscoverEnum.DISCOVERED;
//        List<Pair<Integer,Integer>> discovedNodes = new LinkedList<>();
        Pair<Integer, Integer> current = new Pair<>(0,0);
        int result=0;
        while (current != null){
            Integer row = current.getFirst();
            Integer column = current.getSec();
            discovered[row][column] = DiscoverEnum.DISCOVERED;
            if(canIVisitLeft(forest, current)){
                result+=1;
                current = new Pair<>(row, column -1);
            }else if(canIVisitRight(forest, current)){
                current = new Pair<>(row, column +1);
                result+=1;
            }else if(canIVistUp(forest, current)){
                current = new Pair<>(row-1, column);
                result+=1;
            }else if(canIVisitDown(forest, current)){
                current = new Pair<>(row+1, column);
                result+=1;
            }else {
                current = null;
            }

        }
        return visitedAll(discovered, forest)? result:-1;
    }

    private boolean visitedAll(DiscoverEnum[][] discovered, List<List<Integer>> forest) {
        boolean visitedAll = true;
        for (int i = 0; i < forest.size(); i++) {
            for (int j = 0; j < forest.get(0).size(); j++) {
                if(forest.get(i).get(j) > 1){
                    visitedAll &= discovered[i][j] == DiscoverEnum.DISCOVERED;
                }
            }
        }
        return visitedAll;
    }

    private boolean canIVistUp(List<List<Integer>> forest, Pair<Integer, Integer> current) {
        Integer row = current.getFirst();
        Integer column = current.getSec();
        return row > 0 &&  forest.get(row-1).get(column) > forest.get(row).get(column);
    }

    private boolean canIVisitDown(List<List<Integer>> forest, Pair<Integer, Integer> current) {
        Integer row = current.getFirst();
        Integer column = current.getSec();
        return row < forest.size()-1 && forest.get(row+1).get(column)> forest.get(row).get(column);
    }


    private boolean canIVisitRight(List<List<Integer>> forest, Pair<Integer, Integer> current) {
        Integer row = current.getFirst();
        Integer column = current.getSec();
        return column < forest.get(0).size()-1 && forest.get(row).get(column+1)> forest.get(row).get(column);
    }

    private boolean canIVisitLeft(List<List<Integer>> forest, Pair<Integer, Integer> current) {
        Integer row = current.getFirst();
        Integer column = current.getSec();
        return column > 0 && forest.get(row).get(column-1) > forest.get(row).get(column);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1]]|0",
            "[[2,3,4]]|2",
            "[[2,3,4],[0,0,5]]|3",
            "[[2,3,4],[0,0,5],[8,7,6]]|6",
            "[[1,2,3],[0,0,4],[7,6,5]]|6",
            "[[1,2,3],[0,0,0],[7,6,5]]|-1",
            "[[54581641,64080174,24346381,69107959],[86374198,61363882,68783324,79706116],[668150,92178815,89819108,94701471],[83920491,22724204,46281641,47531096],[89078499,18904913,25462145,60813308]]|57"

    })
    void testHappy(String graphStr, int expected){
        List<List<Integer>> graph = Arrays.stream(graphStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(row -> Arrays.stream(row.split(",")).map(Integer::valueOf).collect(Collectors.toList())).collect(Collectors.toList());
        Assert.assertEquals(expected, cutOffTree(graph));
    }

}