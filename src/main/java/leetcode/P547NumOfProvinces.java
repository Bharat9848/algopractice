package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c, then city a is connected indirectly with city c.
 *
 * A province is a group of directly or indirectly connected cities and no other cities outside of the group.
 *
 * You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.
 *
 * Return the total number of provinces.
 *
 *
 *
 * Example 1:
 *
 * Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
 * Output: 2
 *
 * Example 2:
 *
 * Input: isConnected = [[1,0,0],[0,1,0],[0,0,1]]
 * Output: 3
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 200
 *     n == isConnected.length
 *     n == isConnected[i].length
 *     isConnected[i][j] is 1 or 0.
 *     isConnected[i][i] == 1
 *     isConnected[i][j] == isConnected[j][i]
 */
class P547NumOfProvinces {
    private class UnionFind{
        private int[] parent;
        private int[] rank;
        UnionFind(int size){
            this.parent = new int[size];
            this.rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        int find(int node){
            if(parent[node] == node){
                return node;
            }
            int root = find(parent[node]);
            parent[node] = root;
            return root;
        }

        boolean connected(int i, int j) {
            return find(i) == find(j);
        }

        void union(int i, int j) {
            int rooti = find(i);
            int rootj = find(j);
            if(rooti != rootj){
                int rankI = rank[i];
                int rankJ = rank[j];
                if(rankI > rankJ){
                    parent[rootj] = rooti;
                }else if(rankI < rankJ){
                    parent[rooti] = rootj;
                }else{
                    parent[rooti] = rootj;
                    rank[j] = rank[j] + 1;
                }
            }
        }
    }

    int findCircleNum(int[][] isConnected) {
        UnionFind allVertices = new UnionFind(isConnected.length);
        for (int i = 0; i < isConnected.length; i++) {
            for (int j = i+1; j < isConnected[0].length; j++) {
                if(isConnected[i][j] == 1 && !allVertices.connected(i, j)){
                    allVertices.union(i, j);
                }
            }
        }

        HashSet<Integer> parents = new HashSet<>();
        for (int i = 0; i < isConnected.length; i++) {
            parents.add(allVertices.find(i));
        }
        System.out.println(parents);
        return parents.size();
    }

    int findCircleNumDFS(int[][] isConnected) {
        int count = 0;
        boolean[] visited = new boolean[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) {
            if(!visited[i]){
                count++;
                checkAllNodesInProvince(i, isConnected, visited);
            }
        }
        return count;
    }

    private void checkAllNodesInProvince(int source, int[][] isConnected, boolean[] visited) {
        visited[source] = true;
        int[] edges = isConnected[source];
        for (int i = 0; i < edges.length; i++) {
            if(edges[i] == 1 && !visited[i]){
                checkAllNodesInProvince(i, isConnected, visited);
            }
        }
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[1,0,0,0,0,0,0,0,0,1,0,0,0,0,0],[0,1,0,1,0,0,0,0,0,0,0,0,0,1,0],[0,0,1,0,0,0,0,0,0,0,0,0,0,0,0],[0,1,0,1,0,0,0,1,0,0,0,1,0,0,0],[0,0,0,0,1,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,1,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,1,0,0,0,0,0,0,0,0],[0,0,0,1,0,0,0,1,1,0,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0,0,0],[1,0,0,0,0,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0,0,0],[0,0,0,1,0,0,0,0,0,0,0,1,0,0,0],[0,0,0,0,1,0,0,0,0,0,0,0,1,0,0],[0,1,0,0,0,0,0,0,0,0,0,0,0,1,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,1]]|8",
            "[[1,0,0],[0,1,0],[0,0,1]]|3",
    "[[1,1,0],[1,1,0],[0,0,1]]|2"})
    void test(String arrStr, int expected){
        int[][] arr= Arrays.stream(arrStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s ->
                    Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()
                ).toArray(int[][]::new);
        Assert.assertEquals(expected, findCircleNum(arr));
    }
}
