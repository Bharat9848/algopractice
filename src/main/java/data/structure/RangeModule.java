package data.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Design a Range Module data structure that effectively tracks ranges of numbers using half-open intervals and allows querying these ranges. A half-open interval [left,right)[left,right) includes all real numbers nn where left≤n<rightleft≤n<right.
 *
 * Implement the RangeModule class with the following specifications:
 *
 *     Constructor(): Initializes a new instance of the data structure.
 *
 *     Add Range(): Adds the half-open interval [left, right)[left, right) to the ranges being tracked. If the interval overlaps with existing ranges, it should add only the numbers within [left, right)[left, right) that are not already being tracked.
 *
 *     Query Range(): Returns true if every real number within the interval [left, right)[left, right) is currently being tracked, and false otherwise.
 *
 *     Remove Range(): Removes tracking for every real number within the half-open interval [left, right)[left, right).
 *
 * Constraints:
 *
 *     1≤ left < right ≤104
 *
 *     At most, 103 calls will be made to Add Range(), Query Range(), and Remove Range().
 */
public class RangeModule {
    ArrayList<Pair<Integer,Integer>> sortedIntervals;
    public RangeModule() {
        sortedIntervals = new ArrayList<>();
    }

    public void addRange(int left, int right) {
        Pair<Boolean, Integer> indexleft = findIntervalIndex(0, left);
        Pair<Boolean, Integer> indexRight = findIntervalIndex(indexleft.getSec(), right);
        var combineLeft = indexleft.getFirst();
        var combineRight = indexRight.getFirst();
        var newLeft = combineLeft ? sortedIntervals.get(indexleft.getSec()).getFirst(): left;
        var newRight = combineRight ? sortedIntervals.get(indexRight.getSec()).getSec(): right;
        if (combineLeft && combineRight) {
            if(indexleft.getSec() ==  indexRight.getSec()){
              return;
            } else {
                for (int i = indexleft.getSec(); i < indexRight.getSec() && indexleft.getSec() < sortedIntervals.size(); i++) {
                    sortedIntervals.remove(indexleft.getSec().intValue());
                }
            }
        } else if (combineLeft) {
            sortedIntervals.remove(indexleft.getSec().intValue());
        } else if (combineRight) {
            sortedIntervals.remove(indexRight.getSec().intValue());
        }
        if(indexleft.getSec() == sortedIntervals.size()){
            sortedIntervals.add(new Pair<>(newLeft, newRight));
        } else {
            sortedIntervals.add(indexleft.getSec(), new Pair<>(newLeft, newRight));
        }
        System.out.println(sortedIntervals);
    }

    private Pair<Boolean, Integer> findIntervalIndex(int startingIndex, int target) {
        if(sortedIntervals.isEmpty()){
            return new Pair<>(false, 0);
        } else if (startingIndex == sortedIntervals.size()) {
            return new Pair<>(false, sortedIntervals.size());
        } else {
            int beg = startingIndex;
            int end = sortedIntervals.size() - 1;
            while (beg < end){
                int mid = beg + (end - beg)/2;
                Pair<Integer, Integer> midInterval = sortedIntervals.get(mid);
                if(target >= midInterval.getFirst() && target <= midInterval.getSec()){
                    return new Pair<>(true, mid);
                } else if(target > midInterval.getSec()) {
                    beg = mid + 1;
                } else {
                   end = mid;
                }
            }
            if(beg == sortedIntervals.size() - 1 && target > sortedIntervals.get(beg).getSec() ){
                return new Pair<>(false, sortedIntervals.size());
            } else {
                return new Pair<>(target <= sortedIntervals.get(beg).getSec(), beg);
            }
        }

    }

    public boolean queryRange(int left, int right) {
        Pair<Boolean, Integer> indexleft = findIntervalIndex(0, left);
        Pair<Boolean, Integer> indexRight = findIntervalIndex(indexleft.getSec(), right);
        return (sortedIntervals.size() > indexleft.getSec() && left >= sortedIntervals.get(indexleft.getSec()).getFirst() && left < sortedIntervals.get(indexleft.getSec()).getSec())
                && (indexleft.getSec().intValue() == indexRight.getSec().intValue());
    }



    public void removeRange(int left, int right) {
        Pair<Boolean, Integer> indexleft = findIntervalIndex(0, left);
        Pair<Boolean, Integer> indexRight = findIntervalIndex(indexleft.getSec(), right);
        var divideLeft = indexleft.getFirst();
        var divideRight = indexRight.getFirst();
        Pair<Integer, Integer> newLeftPair = null;
        Pair<Integer, Integer> nextPair = null;
        if (divideLeft) {
            newLeftPair = new Pair<>(sortedIntervals.get(indexleft.getSec()).getFirst(), left);
        }
        if(divideRight) {
            nextPair = new Pair<>(right, sortedIntervals.get(indexRight.getSec()).getSec());
        }
        // remove intervals
//        if(divideLeft && divideRight){
//
//        } else if(divideLeft){
//            sortedIntervals.remove(indexleft.getSec().intValue());
//        } else if(divideRight){
//            sortedIntervals.remove(indexRight.getSec().intValue());
//        }

        for (int i = indexleft.getSec(); i <= indexRight.getSec() && indexleft.getSec() < sortedIntervals.size(); i++) {
            sortedIntervals.remove(indexleft.getSec().intValue());
        }


        if(newLeftPair != null && indexleft.getSec() < sortedIntervals.size()){
            sortedIntervals.add(indexleft.getSec().intValue(), newLeftPair);
        } else if(newLeftPair != null){
            sortedIntervals.add(newLeftPair);
        }

        if(nextPair != null && indexleft.getSec() + 1 < sortedIntervals.size()){
            sortedIntervals.add(indexleft.getSec().intValue() + 1, nextPair);
        } else if(nextPair != null) {
            sortedIntervals.add(nextPair);
        }
        System.out.println(sortedIntervals);
    }



    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "add,remove|(1,4),(5,6)|-1,-1",
            "add,add,query,add,remove,query,query|(1,2),(1,4),(2,3),(2,3),(2,3),(1,2),(3,4)|-1,-1,true,-1,-1,true,true",
            "query,add,add,query,add,query|(1,2),(1,2),(3,4),(2,3),(2,3),(1,4)|false,-1,-1,false,-1,true",
    })
    void test(String opeationStr, String argumentStr, String expectedStr){
        RangeModule array = new RangeModule();
        String[] operations = opeationStr.split(",");
        String[] expecteds = expectedStr.split(",");
        var pattern = Pattern.compile("\\(\\d+,\\d+\\)");
        var matcher = pattern.matcher(argumentStr);
        var points = matcher.results().map(m -> matcher.group()).map(str -> {
            String[] comp = str.replace(")", "").replace("(", "").split(",");
            return new int[]{Integer.parseInt(comp[0]), Integer.parseInt(comp[1])};
        }).toArray(int[][]::new);
        for (int i = 0; i < operations.length; i++) {
            switch (operations[i]){
                case "query":
                    System.out.printf("query (%d,%d)\n", points[i][0], points[i][1]);
                    Assertions.assertEquals(Boolean.valueOf(expecteds[i]), array.queryRange(points[i][0], points[i][1]));
                break;
                case "add":
                    System.out.printf("add (%d,%d)\n", points[i][0], points[i][1]);
                    array.addRange(points[i][0], points[i][1]);
                    Assertions.assertTrue(array.queryRange(points[i][0], points[i][1]));
                    break;
                case "remove":
                    System.out.printf("remove (%d,%d)\n", points[i][0], points[i][1]);
                    array.removeRange(points[i][0], points[i][1]);
                    Assertions.assertFalse(array.queryRange(points[i][0], points[i][1]));
                    break;
                default: throw  new RuntimeException();
            }
        }
    }
}
