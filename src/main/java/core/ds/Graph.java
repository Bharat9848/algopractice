package core.ds;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

/**
 * Created by bharat on 26/5/18.
 */
public class Graph<T extends List> {

    public enum Label{
        DISCOVERED,UNDISCOVERED,PROCESSED
    }
    public final int noOfVertices;
    public final T[] edges;
    public Graph(int noOFVertices, T[] edges){
        this.edges = edges;
        this.noOfVertices = noOFVertices;
    }
    public static class Edge{
        public final int destination;
        public final int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

}
