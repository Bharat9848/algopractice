package topological.sort.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * You are given a rooted tree with nn nodes, numbered from 00 to n−1n−1, where the tree is connected, undirected, and has no cycles. The tree is represented by a 0-indexed array parent of size nn, where parent[i] is the parent of node ii. The root node is node 00, so parent[0] =−1=−1.
 *
 * Additionally, you are provided a string s of length nn, where s[i] represents the character assigned to node i.i.
 *
 * Your task is to find the length of the longest path in the tree where no two consecutive nodes on the path share the same character. Return the length of this path.
 *
 * Constraints:
 *
 *     nn == parent.length == s.length
 *
 *     1≤n≤1051≤n≤105
 *
 *     0≤0≤ parent[i] ≤n−1≤n−1 for all i≥1i≥1
 *
 *     parent[0] =−1=−1
 *
 *     parent represents a valid tree.
 *
 *     s consists of only lowercase English letters.
 */

/**
 * todo reverse topological sort
 */
public class LongestPathForDifferentAdjChar {
    public static int longestPath(int[] parent, String s) {
        Map<Integer, Integer> incoming = new HashMap<>();
        Map<Integer, List<Integer>> outgoing = new HashMap<>();
        for (int i = 0; i < parent.length; i++) {
            incoming.put(i,0);
            outgoing.put(i, new ArrayList<>());
        }
        for (int i = 0; i < parent.length; i++) {
            int par = parent[i];
            int child = i;
            if(par != -1 && s.charAt(par) != s.charAt(child)){
                incoming.put(i, incoming.get(i) + 1);
                outgoing.get(par).add(child);
            }
        }
        int max = 0;
        for(var nodeToCount: incoming.entrySet()){
            var node = nodeToCount.getKey();
            var count = nodeToCount.getValue();
            if(count == 0){
                max =  Math.max(max , findTheLongestPath(node, outgoing));
            }
        }
        return max;
    }

    private static int findTheLongestPath(Integer node, Map<Integer, List<Integer>> outgoing) {
        QNode sol = findTheLongestPathRec(node, outgoing);
        return Math.max(sol.sumTillRoot, sol.sumWithoutRoot);
    }

    private static QNode findTheLongestPathRec(Integer node, Map<Integer, List<Integer>> outgoing) {
        if(outgoing.get(node).isEmpty()){
            return new QNode(node, 1,1);
        }
        List<Integer> outNodes = outgoing.get(node);
        List<QNode> nodeChilds = new ArrayList<>();
        for (int i = 0; i < outNodes.size(); i++) {
           QNode nodeChild = findTheLongestPathRec(outNodes.get(i), outgoing);
//            System.out.printf("nodeId=%d, tillRoot=%d, withoutRoot=%d \n", nodeChild.nodeId, nodeChild.sumTillRoot, nodeChild.sumWithoutRoot);
           nodeChilds.add(nodeChild);
        }
        int childWithoutRootMax = nodeChilds.stream().mapToInt(qNode -> qNode.sumWithoutRoot).max().getAsInt();
        QNode firstMax = nodeChilds.stream().max((qNode,node2) -> Integer.compare(qNode.sumTillRoot, node2.sumTillRoot)).get();
        Optional<QNode> secondMax = nodeChilds.stream().filter(node1 -> node1.nodeId != firstMax.nodeId).max((qNode,node2) -> Integer.compare(qNode.sumTillRoot, node2.sumTillRoot));
        return new QNode(node, firstMax.sumTillRoot + 1, Math.max(childWithoutRootMax, firstMax.sumTillRoot +  secondMax.map(n-> n.sumTillRoot).orElse(0) + 1));
    }


    private static class QNode {
        int nodeId;
        int sumTillRoot;
        int sumWithoutRoot;
        public QNode(int nodeId, int sumTillRoot, int sumWithoutRoot){
            this.nodeId = nodeId;
            this.sumTillRoot = sumTillRoot;
            this.sumWithoutRoot = sumWithoutRoot;
        }
    }

    //[-1,0,1,1,3,4,0,5,6,3,6   ,5,8,6,11,11,14,14,9,2,1,4,8,5,20,15,8,15,27,27,9,16,16,21,4,2,33,31,32,35,23,20,9,31,7] , "satfaimq  hkq   zzmadviziwzsmffpcurxpirnlavkydxcjx"
    //

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "-1,0,1,1,3,4,0,5,6,3,6,5,8,6,11,11,14,14,9,2,1,4,8,5,20,15,8,15,27,27,9,16,16,21,4,2,33,31,32,35,23,20,9,31,7|satfaimqhkqzzmadviziwzsmffpcurxpirnlavkydxcjx|13",
            "-1,0,1,1,3,4,0,5,6,3,6|satfaimqhjq|8",
            "-1,0,0,1|acbb|4",
            "-1,0,0,1,1|acbde|4",
            "-1,0,0,1,1,2|cacbad|3",
    })
    void test(String parentStr, String charStr, int expected){
        int[] parent = Arrays.stream(parentStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, longestPath(parent, charStr));
    }
}
