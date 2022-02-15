package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Design an iterator to flatten a 2D vector. It should support the next and hasNext operations.
 *
 * Implement the Vector2D class:
 *
 *     Vector2D(int[][] vec) initializes the object with the 2D vector vec.
 *     next() returns the next element from the 2D vector and moves the pointer one step forward. You may assume that all the calls to next are valid.
 *     hasNext() returns true if there are still some elements in the vector, and false otherwise.
 *
 * Input
 * ["Vector2D", "next", "next", "next", "hasNext", "hasNext", "next", "hasNext"]
 * [[[[1, 2], [3], [4]]], [], [], [], [], [], [], []]
 * Output
 * [null, 1, 2, 3, true, true, 4, false]
 *
 * Explanation
 * Vector2D vector2D = new Vector2D([[1, 2], [3], [4]]);
 * vector2D.next();    // return 1
 * vector2D.next();    // return 2
 * vector2D.next();    // return 3
 * vector2D.hasNext(); // return True
 * vector2D.hasNext(); // return True
 * vector2D.next();    // return 4
 * vector2D.hasNext(); // return False
 * Constraints:
 *
 *     0 <= vec.length <= 200
 *     0 <= vec[i].length <= 500
 *     -500 <= vec[i][j] <= 500
 *     At most 105 calls will be made to next and hasNext.
 *
 *
 *
 * Follow up: As an added challenge, try to code it using only iterators in C++ or iterators in Java.
 */
public class P251Flatten2DVector {
    private int[][] data;
    private int currentElePosition;
    private int currentSubElePosition;
    void init(int[][] vec) {
        if(vec == null){
            vec = new int[0][0];
        }
        this.data = vec;
        int startindex = nextPositionToNonEmptyVector(0);
        this.currentElePosition = startindex;
        this.currentSubElePosition = 0;
    }

    private int nextPositionToNonEmptyVector(int index){
        for (int i = index; i < data.length; i++) {
            if(data[i].length != 0){
                return i;
            }
        }
        return data.length;
    }


    public int next() {
        int toReturn = data[currentElePosition][currentSubElePosition];
        if(currentSubElePosition+1 == data[currentElePosition].length){
            int nextNonEmptyIndex = nextPositionToNonEmptyVector(currentElePosition+1);
            this.currentElePosition = nextNonEmptyIndex;
            this.currentSubElePosition =0;
        }else{
            currentSubElePosition++;
        }
        return toReturn;
    }

    public boolean hasNext() {
        return currentElePosition < data.length && currentSubElePosition < data[currentElePosition].length;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "Vector2D,next,next,next,hasNext,hasNext,next,hasNext|[[1>2],[3],[4]];;;;;;;|null,1,2,3,true,true,4,false",
            "Vector2D,hasNext|[[]];|null,false",
            "Vector2D,hasNext|;|null,false",
            "Vector2D,hasNext,next,hasNext|[[],[3]];;;|null,true,3,false",
            "Vector2D,hasNext,next,hasNext,next,hasNext|[[1],[],[3]];;;;;|null,true,1,true,3,false"
    })
    void test(String operationStr, String argmentStr, String expectedStr){
        String[] operation = operationStr.split(",");
        String[] argument = argmentStr.split(";");
        String[] expected = expectedStr.split(",");
        P251Flatten2DVector vector2D = new P251Flatten2DVector();
        for (int i = 0; i < operation.length; i++) {
            switch (operation[i]){
                  case "Vector2D": {
                    int[][] arg = argument.length == 0 ? new int[1][0] :Arrays.stream(argument[i].substring(1, argument[i].length()-1).split(","))
                            .filter(s -> !s.isEmpty())
                            .map(s ->"[]".equals(s)? new int[0]: Arrays.stream(s.replace("[","").replace("]","").split(">")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
                    vector2D.init(arg);
                }
                break;
                case "next": {
                    Assertions.assertEquals(Integer.parseInt(expected[i]), vector2D.next());
                }
                break;
                case "hasNext": {
                    Assertions.assertEquals(Boolean.parseBoolean(expected[i]), vector2D.hasNext());
                }
                break;
                default: throw new RuntimeException("invalid operation");
            }
        }
    }
}
