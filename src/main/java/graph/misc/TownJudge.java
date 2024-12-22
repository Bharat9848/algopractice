package graph.misc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * There are n people numbered from 11 to n in a town. There's a rumor that one of these people is secretly the town judge. If there is a town judge, they must meet these conditions:
 *
 * There are n people numbered from 11 to n in a town. There’s a rumor that one of these people is secretly the town judge. A town judge must meet the following conditions:
 *
 *     The judge doesn’t trust anyone.
 *
 *     Everyone else (except the judge) trusts the judge.
 *
 *     Only one person in the town can meet both of these conditions.
 *
 * Given there is n and a two-dimensional array called trust,  each element trust[i] has two elements [ai,bi][ai​,bi​], it means that a trusts person b. If there is a judge, return their number, return −1−1 otherwise.
 *
 * Constraints:
 *
 *     1≤1≤n ≤1000≤1000
 *
 *     0≤0≤trust.length ≤104≤104
 *
 *     trust[i].length ==2==2
 *
 *     ai ≠= bi
 *
 *     1≤1≤ai , bi ≤≤n
 *
 *     All the pairs in trust are unique.
 */
public class TownJudge {

    public static int findJudge(int n, int[][] trust) {
        Map<Integer, Pair<List<Integer>, List<Integer>>> adjList = new HashMap<>();
        for (int i = 0; i < trust.length; i++) {
            int[] t = trust[i];
            adjList.putIfAbsent(t[1], new Pair<>(new ArrayList<>(), new ArrayList<>()));
            adjList.putIfAbsent(t[0], new Pair<>(new ArrayList<>(), new ArrayList<>()));
            var destIncomingOutgoing = adjList.get(t[1]);
            destIncomingOutgoing.getFirst().add(t[0]);
            var srcIncomingOutgoing = adjList.get(t[0]);
            srcIncomingOutgoing.getSec().add(t[1]);
        }
        int judge = -1;
        for (var incomingList : adjList.entrySet()) {
            int node = incomingList.getKey();
            int incomingEdges = incomingList.getValue().getFirst().size();
            int outgoingEdges = incomingList.getValue().getSec().size();
            if(incomingEdges == n -1 && outgoingEdges == 0){
                if(judge == -1){
                    judge = node;
                } else{
                    judge = -1;
                    break;
                }
            }
        }
        return judge;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2|[[1,2]]|2",
            "2|[[1,2],[2,1]]|-1",
            "3|[[1,3],[2,3],[3,1]]|-1",
            "4|[[1,2],[3,2],[4,2],[3,4]]|2"
    })
    void test(int n, String trustStr, int expected){
        var pattern = Pattern.compile("\\[\\d,\\d\\]");
        var matcher = pattern.matcher(trustStr);
        int[][] trusts = matcher.results().map(matchResult -> matcher.group()).map(str -> {
            var tokens =  str.split(",");
            int first = Integer.parseInt(tokens[0].replace("[", ""));
            int second = Integer.parseInt(tokens[1].replace("]", ""));
            return new int[] {first, second};
        }).toArray(int[][]::new);
        Assertions.assertEquals(expected, findJudge(n, trusts));
    }
}
