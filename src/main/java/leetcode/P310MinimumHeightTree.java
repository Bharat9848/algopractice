package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.
 *
 * Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges where edges[i] = [ai, bi] indicates that there is an undirected edge between the two nodes ai and bi in the tree, you can choose any node of the tree as the root. When you select a node x as the root, the result tree has height h. Among all possible rooted trees, those with minimum height (i.e. min(h))  are called minimum height trees (MHTs).
 *
 * Return a list of all MHTs' root labels. You can return the answer in any order.
 *
 * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
 *
 * Input: n = 4, edges = [[1,0],[1,2],[1,3]]
 * Output: [1]
 * Explanation: As shown, the height of the tree is 1 when the root is the node with label 1 which is the only MHT.
 *
 * Input: n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]
 * Output: [3,4]
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 2 * 104
 *     edges.length == n - 1
 *     0 <= ai, bi < n
 *     ai != bi
 *     All the pairs (ai, bi) are distinct.
 *     The given input is guaranteed to be a tree and there will be no repeated edges.
 */
public class P310MinimumHeightTree {
    List<Integer> findMinHeightTreesTLE(int n, int[][] edges) {
        if(n==1){
            return Arrays.asList(0);
        }
        if(n == 2){
            return Arrays.asList(0,1);
        }
        Map<Integer, List<Integer>> adjMap = Arrays.stream(edges).flatMap( row -> allEdges(row).stream()).collect(Collectors.toMap(edge -> edge.get(0), edge -> {
            List<Integer> values = new ArrayList<>();
            values.add(edge.get(1));
            return values;
        }, (list1, list2) ->  {
            list1.addAll(list2);
            return list1;
        }));
        Map<Integer, List<Integer>> heightToRootList = new HashMap<>();
        int minHeight = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if(adjMap.getOrDefault(i, new ArrayList<>()).size() >=2){
                int height = calculateHeight(i, adjMap, n);
                heightToRootList.putIfAbsent(height, new ArrayList<>());
                heightToRootList.get(height).add(i);
                minHeight = Math.min(minHeight, height);
            }
        }
        return heightToRootList.get(minHeight);
    }

    private int calculateHeight(int root, Map<Integer, List<Integer>> adjMap, int n) {

        Queue<Integer> level = new LinkedList<>();
        level.add(root);
        boolean[] visited = new boolean[n];
        int levelCount = 0;
        while (!level.isEmpty()){
            Queue<Integer> newLevel = new LinkedList<>();
            levelCount++;
            while (!level.isEmpty()){
                int current = level.remove();
                visited[current] = true;
                for (Integer neighbour : adjMap.getOrDefault(current, new ArrayList<>())) {
                    if(!visited[neighbour]){
                        newLevel.add(neighbour);
                    }
                }
            }
            level = newLevel;
        }
        return levelCount -1;
    }

    private List<List<Integer>> allEdges(int[] row){
        int src = row[0];
        int dest = row[1];
        List<Integer> srcToDest  = Arrays.asList(src, dest);
        List<Integer> destToSrc = Arrays.asList(dest, src);
        List<List<Integer>> allEdges = Arrays.asList(srcToDest, destToSrc);
        return allEdges;
    }



    List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if(n==1){
            return Arrays.asList(0);
        }
        if(n == 2){
            return Arrays.asList(0,1);
        }
        Map<Integer, Set<Integer>> adjMap = Arrays.stream(edges).flatMap( row -> allEdges(row).stream()).collect(Collectors.toMap(edge -> edge.get(0), edge -> {
            Set<Integer> values = new HashSet<>();
            values.add(edge.get(1));
            return values;
        }, (list1, list2) ->  {
            list1.addAll(list2);
            return list1;
        }));
        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if(adjMap.get(i).size() == 1){
                leaves.add(i);
            }
        }
        List<Integer> result = new ArrayList<>();
        List<Integer> nextLeaves = new ArrayList<>();
        while (!leaves.isEmpty()){
            int leaf = leaves.remove(0);
            Set<Integer> neighbours = adjMap.get(leaf);
           if(neighbours.size()>0) {
                Integer neighbour = neighbours.iterator().next();
                adjMap.get(neighbour).remove(leaf);
                if(adjMap.get(neighbour).size() == 1){
                    nextLeaves.add(neighbour);
                }
            }
            adjMap.put(leaf, new HashSet<>());
            if(leaves.isEmpty()){
                leaves = nextLeaves;
                if(!nextLeaves.isEmpty()){
                    result = new ArrayList<>();
                    result.addAll(nextLeaves);
                }
                nextLeaves = new ArrayList<>();
            }
        }
        return result;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2|[[1,0]]|0,1",
            "6|[[3,0],[3,1],[3,2],[3,4],[5,4]]|3,4",
            "4|[[1,0],[1,2],[1,3]]|1",
            "1|[[]]|0",

    })
    void test(int n, String edgeStr, String expected){
        int[][] edges = Arrays.stream(edgeStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);

        List<Integer> expectedList = Arrays.stream(expected.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        Assertions.assertEquals(expectedList, findMinHeightTrees(n, edges));
    }

}
