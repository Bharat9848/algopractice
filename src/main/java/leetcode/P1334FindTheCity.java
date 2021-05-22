package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
There are n cities numbered from 0 to n-1. Given the array edges where edges[i] = [fromi, toi, weighti] represents a bidirectional and weighted edge between cities fromi and toi, and given the integer distanceThreshold.

Return the city with the smallest number of cities that are reachable through some path and whose distance is at most distanceThreshold, If there are multiple such cities, return the city with the greatest number.

Notice that the distance of a path connecting cities i and j is equal to the sum of the edges' weights along that path.

Example 1:

Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
Output: 3
Explanation: The figure above describes the graph.
The neighboring cities at a distanceThreshold = 4 for each city are:
City 0 -> [City 1, City 2]
City 1 -> [City 0, City 2, City 3]
City 2 -> [City 0, City 1, City 3]
City 3 -> [City 1, City 2]
Cities 0 and 3 have 2 neighboring cities at a distanceThreshold = 4, but we have to return city 3 since it has the greatest number.

Example 2:

Input: n = 5, edges = [[0,1,2],[0,4,8],[1,2,3],[1,4,2],[2,3,1],[3,4,1]], distanceThreshold = 2
Output: 0
Explanation: The figure above describes the graph.
The neighboring cities at a distanceThreshold = 2 for each city are:
City 0 -> [City 1]
City 1 -> [City 0, City 4]
City 2 -> [City 3, City 4]
City 3 -> [City 2, City 4]
City 4 -> [City 1, City 2, City 3]
The city 0 has 1 neighboring city at a distanceThreshold = 2.

Constraints:

    2 <= n <= 100
    1 <= edges.length <= n * (n - 1) / 2
    edges[i].length == 3
    0 <= fromi < toi < n
    1 <= weighti, distanceThreshold <= 10^4
    All pairs (fromi, toi) are distinct.


 */
public class P1334FindTheCity {
     private int findTheCity(int n, int[][] edges, int distanceThreshold) {
             Map<Integer, List<Integer>> result = new HashMap<>();
             for (int i = 0; i < n; i++) {
                //find all city in distanceThreshold for city i
                 List<Integer> neighbours = citiesWithinThreshold(n, i, distanceThreshold, edges, result);
                 System.out.printf("from %d within distance threshold %s \n", i, neighbours);
                 result.put(i, neighbours);
             }
             return result.entrySet().stream().reduce((e1, e2) -> {
                 if (e1.getValue().size() == e2.getValue().size()) {
                     return e1.getKey() > e2.getKey() ? e1 : e2;
                 } else if (e1.getValue().size() < e2.getValue().size()) {
                     return e1;
                 } else return e2;

             }).get().getKey();
        }

        private List<Integer> citiesWithinThreshold(int totalNodes, int source, int distanceThreshold, int[][] edges, Map<Integer, List<Integer>> resultSoFar) {
         int[] distance = IntStream.range(0, totalNodes).map(i -> Integer.MAX_VALUE).toArray();
         distance[source] = 0;
         Set<Integer> inTree = new HashSet<>();
         int current = source;
         while(!inTree.contains(current)){
             inTree.add(current);
             final int temp = current;
             int[][] currentEdges = Arrays.stream(edges).filter(edge -> (edge[0] == temp) || (edge[1] == temp)).toArray(int[][]::new);
             for (int[] edge :currentEdges) {
                 int dest = edge[0] != current ? edge[0] : edge[1];
                 int dist = edge[2];
                 if(distance[current] + dist < distance[dest]){
                     distance[dest] = distance[current] + dist;
                 }
             }
             current = IntStream.range(0, distance.length).filter(node-> !inTree.contains(node) && distance[node] != Integer.MAX_VALUE).reduce((n1, n2) -> distance[n1] < distance[n2]? n1: n2).orElse(source);
         }

         return IntStream.range(0, distance.length).filter(node -> distance[node] <= distanceThreshold && node != source).boxed().collect(Collectors.toList());
    }


    @Test
    public void testAlgo(){
//       Assert.assertEquals(0, findTheCity(5, new int[][] {{0,1,2},{0,4,8},{1,2,3},{1,4,2},{2,3,1},{3,4,1}}, 2));
        /* example 1
        Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
Output: 3
Explanation: The figure above describes the graph.
The neighboring cities at a distanceThreshold = 4 for each city are:
City 0 -> [City 1, City 2]
City 1 -> [City 0, City 2, City 3]
City 2 -> [City 0, City 1, City 3]
City 3 -> [City 1, City 2]
Cities 0 and 3 have 2 neighboring cities at a distanceThreshold = 4, but we have to return city 3 since it has the greatest number.
         */
//        Assert.assertEquals(3, findTheCity(4, new int[][]{{0,1,3},{1,2,1}, {1,3,4},{2,3,1}},4));
        /* example 2
        6
[[0,3,7],[2,4,1],[0,1,5],[2,3,10],[1,3,6],[1,2,1]]
417

         */
        Assert.assertEquals(5, findTheCity(6, new int[][]{{0,3,7},{2,4,1},{0,1,5},{2,3,10},{1,3,6},{1,2,1}},417));
        /*
        6
[[0,1,10],[0,2,1],[2,3,1],[1,3,1],[1,4,1],[4,5,10]]
20

         */
        Assert.assertEquals(5, findTheCity(6, new int[][]{{0,1,10},{0,2,1},{2,3,1},{1,3,1},{1,4,1},{4,5,10}},20));
    }
}
