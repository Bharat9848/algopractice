package graph;

import core.ds.Graph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BreadthFirstTraversal {

    public void BST(int noOfNodes, int src, List<Integer>[] edges){
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

    private void processNode(int node) {
        System.out.print(node+", ");
    }


    @Test
    public void testBST(){
        List<Integer>[] edges = new List[7];
        edges[0] = new ArrayList<Integer>(){{add(2);add(4);}};
        edges[1] = new ArrayList<Integer>();
        edges[2] = new ArrayList<Integer>(){{add(3);}};
        edges[3] = new ArrayList<Integer>(){{add(6);add(5);}};
        edges[4] = new ArrayList<Integer>();
        edges[5] = new ArrayList<Integer>();
        edges[6] = new ArrayList<Integer>();


        BST(7,0,edges);
    }

}
