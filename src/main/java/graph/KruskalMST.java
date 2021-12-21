package graph;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KruskalMST {
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

    class UnionFind{
        private UnionFind parent;
        private int val;
        private int size;
        public UnionFind(int val){
            this.val = val;
            this.size = 1;
        }
        public void join(UnionFind subRoot){
            subRoot.parent = this;
            this.size = this.size + subRoot.size;
        }

        public UnionFind findRoot(){
            UnionFind temp = this;
            while(temp.parent != null)
            {
                temp = temp.parent;
            }
            return temp;
        }

        public int size() {
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
