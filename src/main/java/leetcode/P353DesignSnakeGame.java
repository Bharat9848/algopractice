package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;

/**
 * Design a Snake game that is played on a device with screen size height x width. Play the game online if you are not familiar with the game.
 *
 * The snake is initially positioned at the top left corner (0, 0) with a length of 1 unit.
 *
 * You are given an array food where food[i] = (ri, ci) is the row and column position of a piece of food that the snake can eat. When a snake eats a piece of food, its length and the game's score both increase by 1.
 *
 * Each piece of food appears one by one on the screen, meaning the second piece of food will not appear until the snake eats the first piece of food.
 *
 * When a piece of food appears on the screen, it is guaranteed that it will not appear on a block occupied by the snake.
 *
 * The game is over if the snake goes out of bounds (hits a wall) or if its head occupies a space that its body occupies after moving (i.e. a snake of length 4 cannot run into itself).
 *
 * Implement the SnakeGame class:
 *
 *     SnakeGame(int width, int height, int[][] food) Initializes the object with a screen of size height x width and the positions of the food.
 *     int move(String direction) Returns the score of the game after applying one direction move by the snake. If the game is over, return -1.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["SnakeGame", "move", "move", "move", "move", "move", "move"]
 * [[3, 2, [[1, 2], [0, 1]]], ["R"], ["D"], ["R"], ["U"], ["L"], ["U"]]
 * Output
 * [null, 0, 0, 1, 1, 2, -1]
 *
 * Explanation
 * SnakeGame snakeGame = new SnakeGame(3, 2, [[1, 2], [0, 1]]);
 * snakeGame.move("R"); // return 0
 * snakeGame.move("D"); // return 0
 * snakeGame.move("R"); // return 1, snake eats the first piece of food. The second piece of food appears at (0, 1).
 * snakeGame.move("U"); // return 1
 * snakeGame.move("L"); // return 2, snake eats the second food. No more food appears.
 * snakeGame.move("U"); // return -1, game over because snake collides with border
 *
 *
 *
 * Constraints:
 *
 *     1 <= width, height <= 104
 *     1 <= food.length <= 50
 *     food[i].length == 2
 *     0 <= ri < height
 *     0 <= ci < width
 *     direction.length == 1
 *     direction is 'U', 'D', 'L', or 'R'.
 *     At most 10^4 calls will be made to move.
 */
 class P353DesignSnakeGame {
    private enum CellDirection { UP, DOWN, LEFT, RIGHT}
    int nextfoodIndex = 0;
    int[][] food;
    int height;
    int width;
    int[][] grid;
    private  boolean alive = true;
    private int score = 0;
    Pair<Integer, Integer> head;
    List<Pair<Pair<Integer, Integer>, CellDirection>> body = new LinkedList<>();
    Map<Pair<Integer,Integer>, Boolean> bodyMap = new HashMap<>();
    P353DesignSnakeGame(){}

    void init(int width, int height, int[][] food) {
        this.width = width;
        this.food = food;
        this.height = height;
        grid = new int[height][width];
        head = new Pair<>(0,0);
    }

    public int move(String direction) {
        int x = head.getFirst();
        int y = head.getSec();
        CellDirection nextDirection;
        Pair<Integer, Integer> nextHead;
        switch (direction){
            case "U":{
                nextHead = new Pair<>(x-1, y);
                nextDirection = CellDirection.UP;
                break;
            }
            case "D":{
                nextHead = new Pair<>(x+1, y);
                nextDirection = CellDirection.DOWN;
                break;
            }
            case "L":{
                nextHead = new Pair<>(x, y-1);
                nextDirection = CellDirection.LEFT;
                break;
            }
            case "R":{
                nextHead = new Pair<>(x, y+1);
                nextDirection = CellDirection.RIGHT;
                break;
            }
            default: throw new RuntimeException("Invalid move " + direction);
        }
        if(reachedFood(nextHead)){
            nextfoodIndex++;
            score+=1;
            body.add(0, new Pair<>(head, nextDirection));
            bodyMap.put(head, true);
        } else {
            if(!body.isEmpty()){
                updateBody(nextDirection);
            }
            alive = checkCollision(nextHead);
        }
        head = nextHead;
        if(!alive){
            return -1;
        }else{
            return score;
        }
    }
    private void updateBody(CellDirection nextDirection){
        Pair<Pair<Integer, Integer>, CellDirection> lastCell = body.remove(body.size() - 1);
        bodyMap.remove(lastCell.getFirst());
        Pair<Pair<Integer, Integer>, CellDirection> newfirst = new Pair<>(head, nextDirection);
        body.add(0, newfirst);
        bodyMap.put(newfirst.getFirst(), true);
    }

    private boolean checkCollision(Pair<Integer, Integer> nextHead) {
           return nextHead.getFirst() >= 0 && nextHead.getFirst() < height && nextHead.getSec() >= 0 && nextHead.getSec() < width && !bodyMap.containsKey(nextHead);
    }

    private boolean reachedFood(Pair<Integer, Integer> nextHead) {
        if(nextfoodIndex < food.length){
            int[] foodXY = food[nextfoodIndex];
            return nextHead.getFirst() == foodXY[0] && nextHead.getSec() == foodXY[1];
        }
        return false;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
//            "SnakeGame1,move,move,move,move,move,move|3;2;[[1>2][0>1]],R,D,R,U,L,U|null,0,0,1,1,2,-1",
//            "SnakeGame1,move,move,move,move,move|2;2;[[1>0]],R,D,L,U,R|[null,0,0,1,1,1",
//            "SnakeGame1,move,move|2;2;[[1>1]],R,R|null,0,-1",
//            "SnakeGame1,move,move,move,move,move,move,move,move,move,move,move,move|3;3;[[2>0][0>0][0>2][2>2]],D,D,R,U,U,L,D,R,R,U,L,D|[null,0,1,1,1,1,2,2,2,2,3,3,3"
            "SnakeGame1,move,move,move,move,move,move,move,move,move,move,move,move|4;3;[[0>1][0>2][0>3][1>3][2>3][2>2][2>1][2>0][1>0]],R,R,R,D,D,L,L,L,U,R,R,R|null,1,2,3,4,5,6,7,8,9,9,9,-1"

            })
    void test(String callStr, String argStr, String expectedStr){
        String[] calls = callStr.split(",");
        String[] args = argStr.split(",");
        String[] expected = expectedStr.split(",");

        P353DesignSnakeGame snakeGame=null;
        for (int i = 0; i < calls.length ; i++) {
            switch(calls[i]){
                case "SnakeGame1" : {
                    String[] a = args[i].split(";");
                    int width = Integer.parseInt(a[0]);
                    int height = Integer.parseInt(a[1]);
                    int[][] food =  Arrays.stream(a[2].split("]\\["))
                            .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                            .filter(s -> !s.isEmpty())
                            .map(s -> Arrays.stream(s.split(">")).mapToInt(Integer::parseInt).toArray())
                            .toArray(int[][]::new);
                    snakeGame = new P353DesignSnakeGame();
                    snakeGame.init(width, height, food);
                    break;
                }
                case "move": Assert.assertEquals(Integer.parseInt(expected[i]), snakeGame.move(args[i]));
                    break;
                default: throw new RuntimeException("Invalid input");
            }
        }
    }
}
