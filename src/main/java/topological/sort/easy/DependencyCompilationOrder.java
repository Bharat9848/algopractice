package topological.sort.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.*;

/**
 * There are a total of nn classes labeled with the English alphabet (AA, BB, CC, and so on). Some classes are dependent on other classes for compilation. For example, if class BB extends class AA, then BB has a dependency on AA. Therefore, AA must be compiled before BB.
 *
 * Given a list of the dependency pairs, find the order in which the classes should be compiled.
 *
 * Constraints:
 *
 *     Class name should be an uppercase character.
 *     0≤0≤ dependencies.length ≤676≤676
 *     dependencies[i].length =2=2
 *     All dependency pairs should be unique.
 */
public class DependencyCompilationOrder {
    public static List<Character> findCompilationOrder(List<List<Character>> dependencies) {
        List<Character> result = new ArrayList<>();
        Map<Character, Integer> inDegree = new HashMap<>();
        Map<Character, List<Character>> outboundList = new HashMap<>();
        for (int i = 0; i < dependencies.size(); i++) {
            Character src = dependencies.get(i).get(1);
            Character dest = dependencies.get(i).get(0);
            outboundList.putIfAbsent(src, new ArrayList<>());
            outboundList.putIfAbsent(dest, new ArrayList<>());
            outboundList.get(src).add(dest);
            inDegree.putIfAbsent(dest, 0);
            inDegree.putIfAbsent(src, 0);
            inDegree.put(dest, inDegree.get(dest)+1);
        }
        List<Character> currentSetOfNodes = new ArrayList<>();
        for (var inboundVertex: inDegree.entrySet()) {
            Character node = inboundVertex.getKey();
            if(inboundVertex.getValue() == 0){
                currentSetOfNodes.add(node);
            }
        }
        while(!currentSetOfNodes.isEmpty()){
            Character node = currentSetOfNodes.remove(0);
            result.add(node);
            List<Character> outList = outboundList.getOrDefault(node, new ArrayList<>());
            for (Character depended: outList){
                inDegree.put(depended, inDegree.get(depended) - 1);
            }
            inDegree.remove(node);
            outboundList.remove(node);
            if(currentSetOfNodes.isEmpty()){
                List<Character> newNodes = new ArrayList<>();
                for (var inboundVertex: inDegree.entrySet()) {
                    Character n = inboundVertex.getKey();
                    if(inboundVertex.getValue() == 0){
                        newNodes.add(n);
                    }
                }
                currentSetOfNodes = newNodes;
            }
        }
        if(!inDegree.isEmpty()){
            return new ArrayList<>();
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[C,A],[B,A],[D,C],[E,B],[E,D]|A,B,C,D,E",
            "[C,A],[B,A],[B,C],[D,C],[D,B],[E,B],[E,D]|A,C,B,D,E",
            "[A,B],[B,C],[A,D]|C,D,B,A",
            " [A,B],[B,C],[C,D]|D,C,B,A",
            " [W,X],[X,Z],[Z,Y],[Y,X]|",
            "[B,A],[C,B],[D,C],[C,D]|"
    })
    void test(String dependencyStr, String expectedStr){
        List<Character> expected = Arrays.stream((expectedStr==null?"":expectedStr).split(",")).filter(s -> !s.isEmpty()).map(str -> str.trim()).map(str -> str.charAt(0)).toList();
        List<List<Character>> courseList = StringParser.parseStringAsListOfList(dependencyStr, "\\[\\w,\\w\\]", s -> s.charAt(0));
        Assertions.assertEquals(expected, findCompilationOrder(courseList));
    }
}
