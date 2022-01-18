package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * There are n cities labeled from 1 to n. You are given the integer n and an array connections where connections[i] = [xi, yi, costi] indicates that the cost of connecting city xi and city yi (bidirectional connection) is costi.
 *
 * Return the minimum cost to connect all the n cities such that there is at least one path between each pair of cities. If it is impossible to connect all the n cities, return -1,
 *
 * The cost is the sum of the connections' costs used.
 *
 * Example 1:
 *
 * Input: n = 3, connections = [[1,2,5],[1,3,6],[2,3,1]]
 * Output: 6
 * Explanation: Choosing any 2 edges will connect all cities so we choose the minimum 2.
 *
 * Example 2:
 *
 * Input: n = 4, connections = [[1,2,3],[3,4,4]]
 * Output: -1
 * Explanation: There is no way to connect all cities even if all edges are used.
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 10^4
 *     1 <= connections.length <= 10^4
 *     connections[i].length == 3
 *     1 <= xi, yi <= n
 *     xi != yi
 *     0 <= costi <= 10^5
 */
class P1135MinimumCostConnectingTwoCities {
    int minimumCost(int n, int[][] connections){
        Arrays.sort(connections, Comparator.comparingInt(con -> con[2]));
        Map<Integer, UnionFind> unionFinds = createUnionFindNodes(n);
        int totalSum=0;
        for (int[] edge: connections){
            int src = edge[0];
            int dest = edge[1];
            int cost = edge[2];
            UnionFind unionFindSrc = unionFinds.get(src);
            UnionFind unionFindDest = unionFinds.get(dest);
            UnionFind rootSrc = unionFindSrc.findRoot();
            UnionFind rootDest = unionFindDest.findRoot();
            if(rootSrc != rootDest){
                if(rootSrc.size() < rootDest.size()){
                    rootDest.join(rootSrc);
                }else {
                    rootSrc.join(rootDest);
                }
                totalSum+=cost;
            }
        }
        return unionFinds.get(1).findRoot().size() < n ? -1 : totalSum;
    }

    private Map<Integer, UnionFind> createUnionFindNodes(int n) {
     return IntStream.range(1, n+1)
             .mapToObj(nodeNo -> new Pair<>(nodeNo, new UnionFind(nodeNo)))
             .collect(Collectors.toMap(Pair::getFirst, Pair::getSec));
    }

    private class UnionFind{
        private UnionFind parent;
        private int val;
        private int size;
        UnionFind(int val){
            this.val = val;
            this.size = 1;
        }
        void join(UnionFind subRoot){
            subRoot.parent = this;
            this.size = this.size + subRoot.size;
        }

        UnionFind findRoot(){
            UnionFind temp = this;
            while(temp.parent != null)
            {
                temp = temp.parent;
            }
            return temp;
        }

        int size() {
            return size;
        }
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4|[[1,2,3],[3,4,4]]|-1",
            "4|[[1,2,3]]|-1",
            "3|[[1,2,5],[1,3,6],[2,3,1]]|6",
            "7|[[2,1,87129],[3,1,14707],[4,2,34505],[5,1,71766],[6,5,2615],[7,2,37352]]|248074"
    })
    void test(int noOfNodes, String edgesStr, int expected){
        int[][] connections = Arrays.stream(edgesStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assert.assertEquals(expected, minimumCost(noOfNodes, connections));
    }
}
