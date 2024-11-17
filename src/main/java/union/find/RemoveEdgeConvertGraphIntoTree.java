package union.find;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import union.find.core.UnionFind;

import java.util.regex.Pattern;

public class RemoveEdgeConvertGraphIntoTree {
    public int[] redundantConnection(int[][] edges) {
        UnionFind<Integer> unionFind = new UnionFind<>();
        int[] result = null;
        for (int i = 0; i < edges.length; i++) {
            int[] current = edges[i];
            int firstNode = current[0];
            int secondNode = current[1];
            unionFind.add(firstNode);
            unionFind.add(secondNode);
            if(unionFind.connected(firstNode,secondNode)){
                result = current;
            }else {
                unionFind.union(firstNode, secondNode);
            }
        }
        // Replace this placeholder return statement with your code
        return result;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "(1,2),(2,3),(1,3)|(1,3)",
            "(1,2),(2,3),(1,3),(1,4),(2,4)|(2,4)"
    })
    void test(String edgesStr, String expectedStr){
        var pattern = Pattern.compile("\\(\\d,\\d\\)");
        var matcher = pattern.matcher(edgesStr);
        int[][] edges = matcher.results().map(matchResult -> matcher.group()).map(str -> {
           var tokens =  str.split(",");
           int first = Integer.parseInt(tokens[0].replace("(", ""));
           int second = Integer.parseInt(tokens[1].replace(")", ""));
           return new int[] {first, second};
        }).toArray(int[][]::new);
        int[] result = redundantConnection(edges);
        var matcher1 = pattern.matcher(expectedStr);
        int[] expected = matcher1.results().map(matchResult -> matcher1.group()).map(str -> {
            var tokens =  str.split(",");
            int first = Integer.parseInt(tokens[0].replace("(", ""));
            int second = Integer.parseInt(tokens[1].replace(")", ""));
            return new int[] {first, second};
        }).findFirst().get();
        Assertions.assertArrayEquals(expected, result);//Process elements in the stream
    }
}
