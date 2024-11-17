package union.find;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import union.find.core.UnionFind;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LastDayToCross {
    public int lastDayToCross(int rows, int cols, int[][] waterCells) {
        UnionFind<Cell> sets = new UnionFind<>();
        for (int i = waterCells.length-1; i >= 0; i--) {
          var landCell = waterCells[i];
          var x = landCell[0];
          var y = landCell[1];
            Cell current = new Cell(x, y);
            sets.add(current);
            Cell down = new Cell(x + 1, y);
            Cell up = new Cell(x - 1, y);
            Cell left = new Cell(x, y - 1);
            Cell right = new Cell(x, y + 1);
            if(x+1 <= rows && sets.getNodes().containsKey(down)){
              sets.union(current, down);
          }
            if(x-1 > 0 && sets.getNodes().containsKey(up)){
                sets.union(current, up);
            }

            if(y+1 <= cols && sets.getNodes().containsKey(right)){
                sets.union(current, right);
            }

            if(y-1 > 0 && sets.getNodes().containsKey(left)){
                sets.union(current, left);
            }
            if(ableToCross(sets.getNodes().keySet(), rows)){
                return i;
            }
        }
        return -1;
    }

    private boolean ableToCross(Set<Cell> cells,int rows) {
        Map<Cell, Boolean> visited = new HashMap<>();
        for(Cell c: cells){
            if(!visited.containsKey(c)){
                List<Cell> neighbours = new ArrayList<>();
                var visitedRows = IntStream.rangeClosed(1, rows).boxed().collect(Collectors.toSet());
                neighbours.add(c);
                List<Cell> nextGen = new ArrayList<>();
                while (!neighbours.isEmpty()){
                    Cell current = neighbours.removeFirst();
                    visited.put(current, true);
                    var x = current.row;
                    var y = current.col;
                    visitedRows.remove(x);
                    if(visitedRows.isEmpty()){
                        return true;
                    }
                    Cell down = new Cell(x + 1, y);
                    Cell up = new Cell(x - 1, y);
                    Cell left = new Cell(x, y - 1);
                    Cell right = new Cell(x, y + 1);
                    if(!visited.containsKey(down) && cells.contains(down)){
                        nextGen.add(down);
                    }
                    if(!visited.containsKey(up) && cells.contains(up)){
                        nextGen.add(up);
                    }
                    if(!visited.containsKey(left) && cells.contains(left)){
                        nextGen.add(left);
                    }
                    if(!visited.containsKey(right) && cells.contains(right)){
                        nextGen.add(right);
                    }
                    if(neighbours.isEmpty() && !nextGen.isEmpty()){
                        neighbours = nextGen;
                    }
                }

            }
            List<Cell> neighbours = new ArrayList<>();
            var visitedRows = IntStream.rangeClosed(1, rows).boxed().collect(Collectors.toSet());
            neighbours.add(c);
            while (!neighbours.isEmpty()){
                Cell current = neighbours.removeFirst();
                visited.put(current, true);
                List<Cell> nextGen = new ArrayList<>();
                var x = current.row;
                var y = current.col;
                visitedRows.remove(x);
                Cell down = new Cell(x + 1, y);
                Cell up = new Cell(x - 1, y);
                Cell left = new Cell(x, y - 1);
                Cell right = new Cell(x, y + 1);
                if(!visited.containsKey(down) && cells.contains(down)){
                    nextGen.add(down);
                }
                if(!visited.containsKey(up) && cells.contains(up)){
                    nextGen.add(up);
                }
                if(!visited.containsKey(left) && cells.contains(left)){
                    nextGen.add(left);
                }
                if(!visited.containsKey(right) && cells.contains(right)){
                    nextGen.add(right);
                }
                if(neighbours.isEmpty() && !nextGen.isEmpty()){
                    neighbours = nextGen;
                    if(visitedRows.isEmpty()){
                        return true;
                    }
                }
            }



        }
        return false;
    }

    record Cell(int row, int col){
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && col == cell.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|1|[[1,1]]|0",
            "2|1|[[1,1][2,1]]|0",
            "1|2|[[1,1][1,2]]|1",
            "3|3|[[1,1],[1,2],[3,3],[2,2],[2,1],[3,1],[2,3],[3,2],[1,3]]|3",
            "3|3|[[1,1],[3,1],[2,1],[2,3],[3,3],[3,2],[2,2],[1,2],[1,3]]|5",
            "3|3|[[3,3],[1,3],[3,2],[1,2],[1,1],[2,1],[2,2],[2,3],[3,3]]|4",
            "2|4|[[1,2],[1,4],[2,1],[1,1],[2,4],[1,3],[2,3],[2,2]]|5",
            "3|3|[ [3,2], [1,3], [2,2], [3,1], [1,1], [1,2], [2,3], [3,3], [2,1]] |3",
            "4|3|[ [2,3], [1,1], [2,1], [4,3], [3,3], [4,1], [4,2], [1,3], [3,2], [1,2], [2,2], [3,1] ]|6"
    })
    void test(int rows, int col, String daysStr, int expected){
        var pattern = Pattern.compile("\\[\\d,\\d\\]");
        Matcher matcher = pattern.matcher(daysStr);
        int[][] waterCells = matcher.results().map(pair -> matcher.group()).
                map(pair -> {
                    var token = pair.replace("[", "").replace("]", "").split(",");
                    return new int[]{Integer.parseInt(token[0]), Integer.parseInt(token[1])};
                }).toArray(int[][]:: new);
        Assertions.assertEquals(expected, lastDayToCross(rows, col, waterCells));
    }
}
