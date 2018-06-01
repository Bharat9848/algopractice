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


    void BST(int noOfNodes, int src, List<Integer>[] edges){
        Graph.Label[] labels = new Graph.Label[noOfNodes];
        for(int i=0;i<noOfNodes;i++){
            labels[i] = Graph.Label.UNDISCOVERED;
        }
        ArrayList<Integer> queue = new ArrayList<>();
        queue.add(src);
        labels[src] = Graph.Label.DISCOVERED;

        while(!queue.isEmpty()){
            int node = queue.remove(0);
            processNode(node);
            List<Integer> edgesNode = edges[node];
            for(Integer dest: edgesNode){
                if(labels[dest].equals(Graph.Label.UNDISCOVERED)){
                    queue.add(dest);
                    labels[dest] = Graph.Label.DISCOVERED;
                }
            }
            labels[node] = Graph.Label.PROCESSED;
        }
    }

    void DST(int noOfNodes, int src, List<Integer>[] edges){
        Graph.Label[] labels = new Graph.Label[noOfNodes];
        for(int i=0;i<noOfNodes;i++){
            labels[i] = Graph.Label.UNDISCOVERED;
        }
        ArrayList<Integer> stack = new ArrayList<>();
        stack.add(src);
        labels[src] = Graph.Label.DISCOVERED;

        while(!stack.isEmpty()){
            int node = stack.remove(0);

            List<Integer> edgeNode = edges[node];
            boolean allProcessed = true;
            for(Integer dest : edgeNode){
                if (labels[dest].equals(Graph.Label.UNDISCOVERED)){
                    stack.add(0,node);
                    stack.add(0,dest);
                    labels[dest] = Graph.Label.DISCOVERED;
                    allProcessed = false;
                    break;
                }
            }
            if(allProcessed){
                labels[node] = Graph.Label.PROCESSED;
                processNode(node);
            }
        }
    }

    private void processNode(int node) {
        System.out.print(node+", ");
    }

    @Test
    public void test(){
        List<Integer>[] edges = new List[7];
        edges[0] = new ArrayList<Integer>(){{add(2);add(4);}};
        edges[1] = new ArrayList<Integer>();
        edges[2] = new ArrayList<Integer>(){{add(3);}};
        edges[3] = new ArrayList<Integer>(){{add(6);add(5);}};
        edges[4] = new ArrayList<Integer>();
        edges[5] = new ArrayList<Integer>();
        edges[6] = new ArrayList<Integer>();


//        BST(7,0,edges);
        DST(7,0,edges);
    }

    public boolean mazeHasSolution(int[][] matrix){
        Graph.Label[][] matrixB = new Graph.Label[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrixB[i][j] = Graph.Label.UNDISCOVERED;
            }
        }
        int startI = 0,startJ = 0;
        matrixB[startI][startJ] = Graph.Label.DISCOVERED;
        int endI = matrix.length-1, endJ = matrix[0].length-1;
        Pair<Integer,Integer> temp = new Pair<>(startI,startJ);
        List<Pair<Integer,Integer>> stack = new ArrayList<>();
        stack.add(temp);
        boolean allProcessed = true;
        while(!stack.isEmpty()){
            Pair<Integer,Integer> t = stack.remove(0);
            for (int i = 0; i < 2; i++) {
                if(t.getFirst()==endI&&t.getSec()==endJ){return true;}
                   if(t.getSec()+1<matrix[0].length && matrixB[t.getFirst()][t.getSec()+1].equals(Graph.Label.UNDISCOVERED) && matrix[t.getFirst()][t.getSec()+1]==1){
                       allProcessed = false;
                       stack.add(0,t);
                       stack.add(0,new Pair<>(t.getFirst(),t.getSec()+1));
                       matrixB[t.getFirst()][t.getSec() +1] = Graph.Label.DISCOVERED;
                       break;
                   }
                if(t.getFirst()+1<matrix.length && matrixB[t.getFirst()+1][t.getSec()].equals(Graph.Label.UNDISCOVERED) && matrix[t.getFirst()+1][t.getSec()]==1){
                    allProcessed = true;
                    stack.add(0,t);
                    stack.add(0,new Pair<>(t.getFirst()+1,t.getSec()));
                    matrixB[t.getFirst()+1][t.getSec() ] = Graph.Label.DISCOVERED;
                    break;
                }

            }
            if(allProcessed){
                matrixB[t.getFirst()][t.getSec()] = Graph.Label.PROCESSED;
            }

        }
        return false;
    }

    @Test
    public void test1213(){
        Assert.assertFalse(mazeHasSolution(new int[][]{{1,0,0},{1,1,0},{0,1,0}}));
        Assert.assertFalse(mazeHasSolution(new int[][]{{1,0,0},{1,1,0},{0,0,0}}));
        Assert.assertTrue(mazeHasSolution(new int[][]{{1,1,1},{1,0,1},{0,0,1}}));
    }

}
