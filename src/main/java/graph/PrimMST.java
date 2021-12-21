package graph;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

class PrimMST {
    int minimumCost(int n, int[][] connections){
        Map<Integer, List<int[]>> edgesMap = createMap(connections);
        Set<Integer> inTree = new HashSet<>();
        int node = connections[0][0];
        int minTotalCost = 0;
        int[] costSoFar = new int[n];
        Arrays.fill(costSoFar, Integer.MAX_VALUE);
        costSoFar[node-1] = 0;
        while (!inTree.contains(node)){
            inTree.add(node);
            minTotalCost+=costSoFar[node-1];
            List<int[]> edges = edgesMap.get(node);
            for (int[] edge: edges){
                int des = edge[0];
                int cost = edge[1];
                if(!inTree.contains(des) && costSoFar[des-1] >  cost){
                    costSoFar[des-1] = cost;
                }
            }
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < costSoFar.length; i++) {
                if(min > costSoFar[i] && !inTree.contains(i+1)){
                    min = costSoFar[i];
                    node = i+1;
                }
            }
        }
        if(inTree.size() < n){
            return -1;
        }
        return minTotalCost;
    }

    private Map<Integer, List<int[]>> createMap(int[][] connections) {
        Map<Integer, List<int[]>> result = new HashMap<>();
        for (int[] connection: connections) {
            int src = connection[0];
            int dest = connection[1];
            int cost = connection[2];
            result.putIfAbsent(src, new ArrayList<>());
            result.putIfAbsent(dest, new ArrayList<>());
            result.get(src).add(new int[]{dest, cost});
            result.get(dest).add(new int[]{src, cost});
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4|[[1,2,3],[3,4,4]]|-1",
            "4|[[1,2,3]]|-1",
            "3|[[1,2,5],[1,3,6],[2,3,1]]|6"
    })
    void test(int noOfNodes, String edgesStr, int expected){
        int[][] connections = Arrays.stream(edgesStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assert.assertEquals(expected, minimumCost(noOfNodes, connections));
    }
}
