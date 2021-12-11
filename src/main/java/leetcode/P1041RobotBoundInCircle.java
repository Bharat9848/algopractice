package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * On an infinite plane, a robot initially stands at (0, 0) and faces north. The robot can receive one of three instructions:
 *
 *     "G": go straight 1 unit;
 *     "L": turn 90 degrees to the left;
 *     "R": turn 90 degrees to the right.
 *
 * The robot performs the instructions given in order, and repeats them forever.
 *
 * Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.
 *
 *
 *
 * Example 1:
 *
 * Input: instructions = "GGLLGG"
 * Output: true
 * Explanation: The robot moves from (0,0) to (0,2), turns 180 degrees, and then returns to (0,0).
 * When repeating these instructions, the robot remains in the circle of radius 2 centered at the origin.
 *
 * Example 2:
 *
 * Input: instructions = "GG"
 * Output: false
 * Explanation: The robot moves north indefinitely.
 *
 * Example 3:
 *
 * Input: instructions = "GL"
 * Output: true
 * Explanation: The robot moves from (0, 0) -> (0, 1) -> (-1, 1) -> (-1, 0) -> (0, 0) -> ...
 * Constraints:
 *
 *     1 <= instructions.length <= 100
 *     instructions[i] is 'G', 'L' or, 'R'.
 *
 *     TODO Not solved: The robot stays in the circle iff (looking at the final vector) it changes direction (ie. doesn't stay pointing north), or it moves 0. =
 *                           b <- ae <-d
 *                           |       |
 *                      d <- c         c <- b
 *                      |                   |
 *                     ea                    ea'
 *                      |                     |
 *                c <- b              c -> d
 *                |                     |
 *                d-               a -> b
 *
 *               GLGRGL
 */
 class P1041RobotBoundInCircle {
     enum Direction {NORTH, SOUTH, EAST, WEST}

     boolean isRobotBounded(String instructions) {
         int[] startingPosition = new int[]{0,0};
         int[] currentPosition = new int[]{0,0};
         Direction currentDirection = Direction.NORTH;
         for (int i = 0; i < instructions.length(); i++) {
            char instruction =  instructions.charAt(i);
            switch (instruction){
                case 'G' : currentPosition = moveOneUnit(currentDirection, currentPosition);
                break;
                case 'L' : currentDirection = turnNintyDegree(currentDirection, true);
                break;
                case 'R' : currentDirection = turnNintyDegree(currentDirection, false);
                break;
                default: throw new RuntimeException("Invalid instruction" +  instruction);
            }
         }

        return Arrays.equals(startingPosition, currentPosition) || (!Arrays.equals(currentPosition, startingPosition) && currentDirection != Direction.NORTH);
    }

    private Direction turnNintyDegree(Direction currentDirection, boolean isLeft) {
         Direction newDirection;
         switch (currentDirection){
             case EAST: newDirection = isLeft ? Direction.NORTH : Direction.SOUTH;
             break;
             case WEST: newDirection = isLeft ? Direction.SOUTH : Direction.NORTH;
             break;
             case SOUTH: newDirection = isLeft ? Direction.EAST : Direction.WEST;
             break;
             case NORTH: newDirection = isLeft ? Direction.WEST : Direction.EAST;
             break;
             default: throw new RuntimeException("Invalid state");
         }
        return newDirection;
    }

    private int[] moveOneUnit(Direction currentDirection, int[] currentPosition) {
         int[] newPosition;
         switch (currentDirection){
             case EAST: newPosition = new int[]{currentPosition[0], currentPosition[1]+1};
             break;
             case WEST: newPosition = new int[]{currentPosition[0], currentPosition[1]-1};
             break;
             case SOUTH:newPosition = new int[]{currentPosition[0]-1, currentPosition[1]};
             break;
             case NORTH:newPosition = new int[]{currentPosition[0]+1, currentPosition[1]};
             break;
             default: throw new RuntimeException("Invalid state");
         }
        return newPosition;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "GGLLGG|true",
            "GG|false",
            "L|true",
            "GRRG|true",
            "GLGLGLG|true",
            "GLGRGLGLGLGRGLG|true",
            "LG|true",
            "LGR|false"

    })
    void testSome(String instruction, boolean expected){
        Assert.assertEquals(expected, isRobotBounded(instruction));
    }
}
