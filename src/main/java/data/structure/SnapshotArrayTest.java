package data.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.regex.Pattern;

public class SnapshotArrayTest {
    @ParameterizedTest
    @CsvSource(delimiter = '|',
            value = {
            "put,put,put,put,snapshot,get,put,put,put,snapshot,get,get,get|(0,2),(1,5),(2,9),(3,14),(0,0),(2,0),(4,20),(5,21),(2,10),(0,0),(5,1),(2,1),(6,1)|-1,-1,-1,-1,0,9,-1,-1,-1,1,21,10,0",
                    "put,snapshot,put,get,put,snapshot,get|(0,5),(0,0),(0,6),(0,0),(0,100),(0,0),(1,1)|-1,0,-1,5,-1,1,0",
                    "put,put,put,snapshot,get,get|(1,1),(2,2),(1,122469647),(0,0),(1,0),(2,0)|,,,0,122469647,2",
                    "put,put,snapshot,put,snapshot,get,get|(1,1),(2,2),(0,0),(1,2),(0,0),(1,0),(1,1)|,,0,,1,1,2",
                    "put,put,snapshot,put,snapshot,get,get,put,snapshot,put,put,put,snapshot,put,snapshot,put,put,get,get,get,get|(1,1),(2,2),(0,0),(1,2),(0,0),(1,0),(1,1),(1,3),(0,0),(1,4),(2,4),(1,5),(0,0),(1,6),(0,0),(1,7),(2,6),(2,1),(2,2),(2,3),(2,4)|-1,-1,0,-1,1,1,2,-1,2,-1,-1,-1,3,-1,4,-1,-1,2,2,4,4",
            })
    void test(String opeationStr, String argumentStr, String expectedStr){
        SnapshotArrayNotDone array = new SnapshotArrayNotDone(10);
        String[] operations = opeationStr.split(",");
        String[] expecteds = expectedStr.split(",");
        var pattern = Pattern.compile("\\(\\d+,\\d+\\)");
        var matcher = pattern.matcher(argumentStr);
        var points = matcher.results().map(m -> matcher.group()).map(str -> {
            String[] comp = str.replace(")", "").replace("(", "").split(",");
            return new int[]{Integer.parseInt(comp[0]), Integer.parseInt(comp[1])};
        }).toArray(int[][]::new);
        for (int i = 0; i < operations.length; i++) {
            switch(operations[i]){
                case "put": array.setValue(points[i][0], points[i][1]);
                    break;
                case "snapshot":
                    Assertions.assertEquals(Integer.parseInt(expecteds[i]), array.snapshot());
                    break;
                case "get":
                    System.out.printf("calling get for(%d, %d)\n ", points[i][0], points[i][1]);
                    Assertions.assertEquals(Integer.parseInt(expecteds[i]), array.getValue(points[i][0], points[i][1]));
            }
        }
    }


}
