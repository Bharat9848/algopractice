package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class P247StrobogammaticII {
    List<String> findStrobogrammatic(int n) {
        if(n==1){return Arrays.asList("0","1","8");}
        return findStrobogrammaticRec(n,0, n-1);
    }
    List<String> findStrobogrammaticRec(int n, int beg, int end) {
        if(beg == end){
            return Arrays.asList("1","8");
        }
        if(beg == end-1){
            return Arrays.asList("11","88", "69","96");
        }
        List<String> subList = findStrobogrammaticRec(n,beg + 1, end - 1);
        List<String> result = new ArrayList<>();
        for (String sub: subList) {
            result.add("1" + sub + "1");
            result.add("8" + sub + "8");
            result.add("6" + sub + "9");
            result.add("9" + sub + "6");
            if(beg>0){
                result.add("0" + sub + "0");
            }
        }
        String allZero = IntStream.range(beg+1, end).mapToObj(i -> "0").reduce("", (a, b) -> a +b);
        result.add("1" + allZero + "1");
        result.add("8" + allZero + "8");
        result.add("6" + allZero + "9");
        result.add("9" + allZero + "6");
        return result;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4|1001,1111,1691,1881,1961,6009,6119,6699,6889,6969,8008,8118,8698,8888,8968,9006,9116,9696,9886,9966",
            "2|11,69,88,96",
            "3|808,181,111,101,689,986,888,619,916,609,906,818",
    })
    void test(int n, String expectedStr){
        Assertions.assertEquals(Arrays.stream(expectedStr.split(",")).collect(Collectors.toSet()), new HashSet<>(findStrobogrammatic(n)));
    }
}
