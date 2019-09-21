package graph;

import core.ds.Graph;
import org.junit.Assert;
import org.junit.Test;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bharat on 26/5/18.
 */
public class GraphPractice {

    public boolean mazeHasSolution(int[][] matrix){
        Graph.Label[][] lableMatrix = initLableMatrix(matrix);
        int startI = 0,startJ = 0;
        lableMatrix[startI][startJ] = Graph.Label.DISCOVERED;
        int endI = matrix.length-1, endJ = matrix[0].length-1;

        Pair<Integer,Integer> temp = new Pair<>(startI,startJ);
        List<Pair<Integer,Integer>> stack = new ArrayList<>();
        stack.add(temp);

        boolean allProcessed = true;
        while(!stack.isEmpty()){
            Pair<Integer,Integer> t = stack.remove(0);
            for (int i = 0; i < 2; i++) {
                if(t.getFirst()==endI&&t.getSec()==endJ){return true;}

                if(t.getSec()+1<matrix[0].length && lableMatrix[t.getFirst()][t.getSec()+1].equals(Graph.Label.UNDISCOVERED) && matrix[t.getFirst()][t.getSec()+1]==1){//checking Right cell
                       allProcessed = false;
                       stack.add(0,new Pair<>(t.getFirst(),t.getSec()+1));
                       lableMatrix[t.getFirst()][t.getSec() +1] = Graph.Label.DISCOVERED;
                       break;
                }
                if(t.getFirst()+1<matrix.length && lableMatrix[t.getFirst()+1][t.getSec()].equals(Graph.Label.UNDISCOVERED) && matrix[t.getFirst()+1][t.getSec()]==1){//check below cell
                    allProcessed = true;
                    stack.add(0,new Pair<>(t.getFirst()+1,t.getSec()));
                    lableMatrix[t.getFirst()+1][t.getSec() ] = Graph.Label.DISCOVERED;
                    break;
                }

            }
            if(allProcessed){
                lableMatrix[t.getFirst()][t.getSec()] = Graph.Label.PROCESSED;
            }

        }
        return false;
    }

    private Graph.Label[][] initLableMatrix(int[][] matrix) {
        Graph.Label[][] matrixB = new Graph.Label[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrixB[i][j] = Graph.Label.UNDISCOVERED;
            }
        }
        return matrixB;
    }

    @Test
    public void test1213(){
        Assert.assertFalse(mazeHasSolution(new int[][]{{1,0,0},{1,1,0},{0,1,0}}));
        Assert.assertFalse(mazeHasSolution(new int[][]{{1,0,0},{1,1,0},{0,0,0}}));
        Assert.assertTrue(mazeHasSolution(new int[][]{{1,1,1},{1,0,1},{0,0,1}}));
    }

}
