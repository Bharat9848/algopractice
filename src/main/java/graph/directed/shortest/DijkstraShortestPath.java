package graph.directed.shortest;

import core.ds.Graph;
import core.ds.Graph.Edge;
import org.junit.Test;
import util.Pair;
import util.PrintUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class DijkstraShortestPath {

    public Pair<Integer[], Integer[]> shortestPathAll(List<Edge>[] edges, int source, int noOfVertices) {
        Integer[] minPathFromIndexedNode = new Integer[noOfVertices];
        Integer[] parent = new Integer[noOfVertices];
        for (int i = 0; i < minPathFromIndexedNode.length; i++) {
            minPathFromIndexedNode[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }
        Boolean[] inTree = new Boolean[noOfVertices];
        for (int i = 0; i < inTree.length; i++) {
            inTree[i] = FALSE;
        }
        minPathFromIndexedNode[source] = 0;
        parent[source] = source;
        int candidate = source;
        while (!inTree[candidate]){
            inTree[candidate] = TRUE;

            for (Edge edge: edges[candidate]) {
                int destination = edge.destination;
                int weight = edge.weight;
                if(!inTree[destination] && minPathFromIndexedNode[destination] > (minPathFromIndexedNode[candidate] + weight)){
                    minPathFromIndexedNode[destination] = minPathFromIndexedNode[candidate] + weight;
                    parent[destination] = candidate;
                }
            }

            int min = Integer.MAX_VALUE;
            for (int i = 0; i < minPathFromIndexedNode.length; i++) {
                if(!inTree[i] && min > minPathFromIndexedNode[i]){
                    min = minPathFromIndexedNode[i];
                    candidate = i;
                }
            }
        }
        return new Pair<Integer[], Integer[]>(minPathFromIndexedNode,parent);
    }

    @Test
    public void testShortestPath(){
        ArrayList<Edge>[] edges = (ArrayList<Edge>[]) Array.newInstance(ArrayList.class,5);
        edges[0] = new ArrayList<Edge>(){{add(new Edge(1,4));add(new Edge(2,1));}};
        edges[1] = new ArrayList<Edge>(){{add(new Edge(0,4));add(new Edge(2,1)); add(new Edge(3,2)); add(new Edge(4,1));}};
        edges[2] = new ArrayList<Edge>(){{ add(new Edge(0,1)); add(new Edge(1,1)); add(new Edge(3,8));}};
        edges[3] = new ArrayList<Edge>(){{ add(new Edge(1,2)); add(new Edge(2,8));}};
        edges[4] = new ArrayList<Edge>(){{ add(new Edge(1,1));}};
        Graph g = new Graph<ArrayList<Edge>>(5,edges);
        Pair<Integer[], Integer[]> res = shortestPathAll(edges, 0, 5);
        System.out.println();
        PrintUtil.printArray(res.getFirst());
        System.out.println();
        PrintUtil.printArray(res.getSec());
    }

}
