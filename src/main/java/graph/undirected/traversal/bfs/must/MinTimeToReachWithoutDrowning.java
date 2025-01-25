package graph.undirected.traversal.bfs.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.*;

/**
 * Given a m x n grid of the string land. It consists of the following types of cells:
 *
 *     S: Source cell where you are standing initially.
 *
 *     D: Destination cell where you have to reach.
 *
 *     .: These cells are empty.
 *
 *     X: These cells are stone.
 *
 *     *: These cells are flooded.
 *
 * Each second, you can move to a neighboring cell directly next to your current one. At the same time, any empty cell next to a flooded cell also becomes flooded. There are two challenges in your path:
 *
 *     You can’t step on stone cells.
 *
 *     You can’t step on flooded cells or cells that will flood right when you try to step on them because you’ll drown.
 *
 * Return the minimum time it takes you to reach the destination from the source in seconds, or −1−1 if no path exists between them.
 *
 *     Note: The destination will never be flooded.
 *
 * Constraints:
 *
 *     2≤2≤ m, n ≤100≤100
 *
 *     land consists only of S, D, ., * and X.
 *
 *     There is only one S and D cells in the land.
 */

/**
 * Two set of paths one for all the water cells and other from S
 * terminating condition - Reached D or no more current cells in S path
 *
 */
public class MinTimeToReachWithoutDrowning {
    private static List<Cell> findStartingCell(List<List<String>> land){
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < land.size(); i++) {
            for (int j = 0; j < land.get(0).size(); j++) {
                if("S".equals(land.get(i).get(j))){
                    cells.add(new Cell(i, j));
                    break;
                }
            }
        }
        return cells;
    }

    private static List<Cell> findAllWaterCells(List<List<String>> land){
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < land.size(); i++) {
            for (int j = 0; j < land.get(0).size(); j++) {
                if("*".equals(land.get(i).get(j))){
                    cells.add(new Cell(i, j));
                }
            }
        }
        return cells;
    }
    public static int minimumSeconds(List<List<String>> land) {
        List<Cell> currentCell = findStartingCell(land);
        List<Cell> waterCells = findAllWaterCells(land);
        int count = 0;
        Set<Cell> visited = new HashSet<>();
        while(!currentCell.isEmpty()){
            List<Cell> newWaterCells = new ArrayList<>();
            while(!waterCells.isEmpty()) {
               Cell wc = waterCells.remove(0);
               if(wc.x + 1 < land.size() && ".".equals(land.get(wc.x +1).get(wc.y))){
                   land.get(wc.x+1).set(wc.y, "*");
                   newWaterCells.add(new Cell(wc.x+1, wc.y));
                }
                if(wc.x - 1 >= 0 && ".".equals(land.get(wc.x - 1).get(wc.y))){
                    land.get(wc.x-1).set(wc.y, "*");
                    newWaterCells.add(new Cell(wc.x-1, wc.y));
                }
                if(wc.y + 1 < land.get(0).size() && ".".equals(land.get(wc.x).get(wc.y + 1))){
                    land.get(wc.x).set(wc.y + 1, "*");
                    newWaterCells.add(new Cell(wc.x, wc.y + 1));
                }
                if(wc.y - 1 >= 0 && ".".equals(land.get(wc.x).get(wc.y - 1))){
                    land.get(wc.x).set(wc.y - 1, "*");
                    newWaterCells.add(new Cell(wc.x, wc.y-1));
                }
                if(waterCells.isEmpty()){
                    waterCells = newWaterCells;
                    break;
                }
            }
            List<Cell> newCells = new ArrayList<>();
        while(!currentCell.isEmpty()) {
            Cell c = currentCell.remove(0);
            if(c.x + 1 < land.size()){
                if(!visited.contains(new Cell(c.x +1, c.y)) &&".".equals(land.get(c.x+1).get(c.y))){
                    visited.add(new Cell(c.x +1, c.y));
                    newCells.add(new Cell(c.x+1, c.y));
                } else if("D".equals(land.get(c.x + 1).get(c.y))) {
                    return count + 1;
                }
            }

                if(c.x - 1 >= 0){
                    if( !visited.contains(new Cell(c.x -1, c.y)) && ".".equals(land.get(c.x - 1).get(c.y))){
                        newCells.add(new Cell(c.x - 1, c.y));
                        visited.add(new Cell(c.x -1, c.y));
                    } else if("D".equals(land.get(c.x - 1).get(c.y))) {
                        return count + 1;
                    }
                }
                if(c.y + 1 < land.get(0).size()){
                    if(!visited.contains(new Cell(c.x, c.y + 1)) &&".".equals(land.get(c.x).get(c.y + 1))){
                        newCells.add(new Cell(c.x, c.y +1));
                        visited.add(new Cell(c.x, c.y + 1));
                    } else if("D".equals(land.get(c.x).get(c.y + 1))) {
                        return count + 1;
                    }
                }
                if(c.y - 1 >= 0){
                    if(!visited.contains(new Cell(c.x, c.y-1)) && ".".equals(land.get(c.x).get(c.y - 1))){
                        newCells.add(new Cell(c.x, c.y - 1));
                        visited.add(new Cell(c.x, c.y - 1));
                    } else if("D".equals(land.get(c.x).get(c.y - 1))) {
                        return count + 1;
                    }
                }
            if(currentCell.isEmpty()){
                count = count + 1;
                currentCell =  newCells;
                break;
            }
        }
        }
        return  -1;
    }

    private static class Cell {
        int x;
        int y;
        public Cell(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[S,.],[*,D]|2",
            "[[S,.,.,*,X,.,*,*],[.,.,X,*,.,.,*,.],[.,.,D,.,.,.,.,*]]|4",
            "[[S,X,.,*],[.,X,.,*],[.,.,.,D]]|-1",
            "[[S,.,.,.,.,D],[.,.,X,.,X,.],[*,*,.,.,.,*]|5"
    })
    void test(String gridStr, int expected){
        List<List<String>> grid = StringParser.parseStringAsListOfList(gridStr,"\\[(.,)*(.)\\]", s -> s);
        Assertions.assertEquals(expected, minimumSeconds(grid));
    }
}
