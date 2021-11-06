package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
In a directed graph, we start at some node and every turn, walk along a directed edge of the graph.  If we reach a node that is terminal (that is, it has no outgoing directed edges), we stop.

Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node.  More specifically, there exists a natural number K so that for any choice of where to walk, we must have stopped at a terminal node in less than K steps.

Which nodes are eventually safe?  Return them as an array in sorted order.

The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.  The graph is given in the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph.

Example:
Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
Output: [2,4,5,6]
Here is a diagram of the above graph.

Illustration of graph

Note:

graph will have length at most 10000.
The number of edges in the graph will not exceed 32000.
Each graph[i] will be a sorted list of different integers, chosen within the range [0, graph.length - 1].
 */
public class P802FindEventualSafeStates {
    private enum DiscoverEnum{
        EXPLORED, DISCOVERED, UNDISCOVERED
    }
    enum TerminalStatus {
        TERMINAL, UNKNOWN, NONTERMINAL
    }
    public List<Integer> eventualSafeNodes(int[][] graph) {
        DiscoverEnum[] discoverEnums = initDiscover(graph.length);
        TerminalStatus[] terminalStatuses = initTerminal(graph.length);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            if(traverseBFS(i, graph, discoverEnums, terminalStatuses)){
                result.add(i);
            }
        }
        return result;
    }

    private boolean traverseBFS(int node, int[][] graph, DiscoverEnum[] discoverEnums, TerminalStatus[] terminalStatuses){
        int[] edges = graph[node];
        discoverEnums[node] = DiscoverEnum.DISCOVERED;
        if(edges.length == 0){
            discoverEnums[node] = DiscoverEnum.EXPLORED;
            terminalStatuses[node] = TerminalStatus.TERMINAL;
            return true;
        }else{
            boolean terminal = true;
            for (int j = 0; j < edges.length; j++) {
                int dest = edges[j];
                if(DiscoverEnum.UNDISCOVERED.equals(discoverEnums[dest])){
                    discoverEnums[dest] = DiscoverEnum.DISCOVERED;
                    traverseBFS(dest, graph, discoverEnums, terminalStatuses);
                    terminal = terminal && TerminalStatus.TERMINAL.equals(terminalStatuses[dest]);
                }else if(DiscoverEnum.DISCOVERED.equals(discoverEnums[dest])) {
                    terminal = false;
                    terminalStatuses[dest] = TerminalStatus.NONTERMINAL;
                }else {
                    terminal = terminal && (TerminalStatus.TERMINAL.equals(terminalStatuses[dest]));
                }
            }
            terminalStatuses[node] = terminal ? TerminalStatus.TERMINAL : TerminalStatus.NONTERMINAL;
            discoverEnums[node] = DiscoverEnum.EXPLORED;
            return terminal;
        }
    }

    private TerminalStatus[] initTerminal(int length) {
        TerminalStatus[] terminalStatuses = new TerminalStatus[length];
        Arrays.fill(terminalStatuses, TerminalStatus.UNKNOWN);
        return terminalStatuses;
    }

    private DiscoverEnum[] initDiscover(int length) {
        DiscoverEnum[] discoverEnums = new DiscoverEnum[length];
        Arrays.fill(discoverEnums, DiscoverEnum.UNDISCOVERED);
        return discoverEnums;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
//            "[1,2],[2,3],[5],[0],[5],[],[];2,4,5,6",
            "[1,2,3,4],[1,2],[3,4],[0,4],[]; "
    })
    void test(String input, String expectedStr){
        String[] rowStr = input.split("],");
        int[][] graph = new int[rowStr.length][];
        for (int i = 0; i < rowStr.length; i++) {
            String trimedRow = rowStr[i].replace("[", "").replace("]", "");
            if(trimedRow.isEmpty()){
                graph[i] = new int[0];
            }else {
                graph[i] = Arrays.stream(trimedRow.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }
        }

        List<Integer> expected = Arrays.stream(Optional.ofNullable(expectedStr).orElse("").split(","))
                .filter(str -> !str.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Assert.assertEquals(expected, eventualSafeNodes(graph));
    }

}
