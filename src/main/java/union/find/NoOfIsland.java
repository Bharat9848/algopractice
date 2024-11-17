package union.find;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import union.find.core.UnionFind;
import util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Let’s consider a scenario with an (m×n)(m×n) 2D grid containing binary numbers, where '0' represents water and '1' represents land. If any '1' cells are connected to each other horizontally or vertically (not diagonally), they form an island. Your task is to return the total number of islands in the grid.
 * <p>
 * Constraints:
 * <p>
 * 1≤1≤ grid.length ≤50≤50
 * <p>
 * 1≤1≤ grid[i].length ≤50≤50
 * <p>
 * grid[i][j] is either '0' or '1'.
 */
public class NoOfIsland {

    private int noOfIsland(List<List<Character>> grid) {
        int count = 0;
        UnionFind<Pair<Integer, Integer>> unionFind = new UnionFind<>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                Character current = grid.get(i).get(j);
                if (current == '1') {
                    count++;
                }
                unionFind.add(new Pair<>(i, j));
            }
        }
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                Character current = grid.get(i).get(j);
                if (current == '1') {
                    if (j + 1 < grid.get(0).size() && grid.get(i).get(j + 1) == '1') {
                        Pair<Integer, Integer> v1 = new Pair<>(i, j);
                        Pair<Integer, Integer> v2 = new Pair<>(i, j + 1);
                        if (!unionFind.connected(v1, v2)) {
                            unionFind.union(v1, v2);
                            count--;
                        }
                    }
                    if (i + 1 < grid.size() && grid.get(i + 1 ).get(j) == '1') {
                        Pair<Integer, Integer> v1 = new Pair<>(i, j);
                        Pair<Integer, Integer> v2 = new Pair<>(i + 1, j);
                        if (!unionFind.connected(v1, v2)) {
                            unionFind.union(v1, v2);
                            count--;
                        }
                    }
                }

            }
        }

        return count;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[0,0,0,1],[1,1,0,1],[1,1,1,1],[0,1,1,1],[0,1,1,1]]|1",
            "[[0,1],[1,0]]|2",
            "[[1,0,1,0,0],[1,0,1,1,1],[1,0,1,1,1],[1,0,0,1,0]]|2",
            "[[1,0,1],[1,0,1]]|2",
            "[[1,1,1],[1,1,1]]|1",
            "[[1,1,1],[1,1,1],[1,1,1]]|1",
            "[[0,0,0,0],[0,1,1,1],[0,1,1,1],[0,1,1,1]]|1",
            "[[0]]|0",
    })
    void test(String arrStr, int expected){
        var pattern = Pattern.compile("(\\[(\\d,)+\\d\\])");
        var matcher = pattern.matcher(arrStr);
        var grid = matcher.results()
                .map(matchResult -> matcher.group())
                .map(row -> {
                    var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).map(ch -> Character.valueOf(ch.charAt(0))).toList();
                        }
                        )
                .toList();
        Assertions.assertEquals(expected, noOfIsland(grid));
    }
}
