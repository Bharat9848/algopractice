package graph;

import core.ds.Graph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DST {

    void DST(int noOfNodes, int src, List<Integer>[] edges){
        Graph.Label[] labels = new Graph.Label[noOfNodes];
        for(int i=0;i<noOfNodes;i++){
            labels[i] = Graph.Label.UNDISCOVERED;
        }
        ArrayList<Integer> stack = new ArrayList<>();
        stack.add(src);
        labels[src] = Graph.Label.DISCOVERED;

        while(!stack.isEmpty()){
            int node = stack.get(0);

            List<Integer> edgeNode = edges[node];
            boolean allProcessed = true;
            for(Integer dest : edgeNode){
                if (labels[dest].equals(Graph.Label.UNDISCOVERED)){
                    stack.add(0,dest);
                    labels[dest] = Graph.Label.DISCOVERED;
                    allProcessed = false;
                    break;
                }
            }
            if(allProcessed){
                labels[node] = Graph.Label.PROCESSED;
                processNode(node);
                stack.remove(0);
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


        DST(7,0,edges);
    }

}
