package graph;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

class KahnAlgoForTopologicalSorting {
    List<Integer> topologicalSorting(int n,  int[][]edges){
        Map<Integer, Pair<List<Integer>, List<Integer>>> adjMap = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];

            adjMap.putIfAbsent(src, new Pair<>(new ArrayList<>(), new ArrayList<>()));
            adjMap.putIfAbsent(dest, new Pair<>(new ArrayList<>(), new ArrayList<>()));
            adjMap.get(src).getSec().add(dest);
            adjMap.get(dest).getFirst().add(src);
        }
        boolean[] visited = new boolean[n];
        int vertex = nonVisitedVertexWithZeroIndegree(visited, adjMap);
        if(vertex == -1){
            throw new RuntimeException("cyclic graph");
        }else{
            List<Integer> result = new ArrayList<>();
            Queue<Integer> queue = new LinkedList<>();
            queue.add(vertex);
            while(!queue.isEmpty()){
                int current = queue.remove();
                result.add(current);
                visited[current] = true;
                removeCurrFromNeighInbound(current, adjMap);
                current = nonVisitedVertexWithZeroIndegree(visited, adjMap);
                if(current == -1){
                    throw new RuntimeException("cyclic graph");
                }else if(current < visited.length){
                    queue.add(current);
                }
            }
            return result;
        }
    }

    private int nonVisitedVertexWithZeroIndegree(boolean[] visited, Map<Integer, Pair<List<Integer>, List<Integer>>> adjMap) {
        boolean allVisited = true;
        for (int i = 0; i < visited.length; i++) {
            if(!visited[i]){
                if(adjMap.getOrDefault(i, new Pair<>(new ArrayList<>(), new ArrayList<>())).getFirst().size() == 0){
                return i;
                }
                allVisited =false;
            }}
        return !allVisited ? -1: visited.length;
    }

    private void removeCurrFromNeighInbound(int current, Map<Integer, Pair<List<Integer>, List<Integer>>> adjMap) {
        List<Integer> outbound = adjMap.get(current).getSec();
        for(Integer dest: outbound){
            adjMap.get(dest).getFirst().removeIf(ele -> ele == current);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3|[[0,1],[1,2]|0,1,2",
            "4|[[0,1][0,2],[1,3],[2,3]]|0,1,2,3",
    })
    void test(int n, String edgesStr, String expectedStr){
        List<Integer> expected = expectedStr == null ? new ArrayList<>():Arrays.stream(expectedStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        int[][] edges = Arrays.stream(edgesStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);
        Assertions.assertEquals(expected, topologicalSorting(n, edges));
    }
}
