package recursion;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Anagram {

    Set<String> findAnagram1(String str) {
        return IntStream.range(0, str.length())
                .mapToObj(index -> new Pair<>(str.charAt(index), cutCharAtIndex(str, index)))
                .map(p -> new Pair<>(p.getFirst(), findAnagramSubString(p.getSec())))
                .flatMap(p -> p.getSec().stream().map(s -> prependIndex(s, p.getFirst())).collect(Collectors.toSet()).stream())
                .collect(Collectors.toSet());
    }

    Set<String> findAnagram(String str) {
        return calAllAnagramsRec(str, 0);
    }
    // abc -> acb cba bca    n!
    private Set<String> calAllAnagramsRec(String word, int index){
        if(index == word.length()){
            return Collections.emptySet();

        }
        Set<String> subList = calAllAnagramsRec(word, index +1);
        Set<String> result = new HashSet<>();
        if(subList.isEmpty()){
            return new HashSet<>(){{add(word.charAt(index)+"");}};
        }
        for(String ana : subList){
            for(int i=0; i<ana.length(); i++){
                String before = ana.substring(0, i);
                String after = ana.substring(i);
                result.add(before + word.charAt(index) + after);
            }
            result.add(ana + word.charAt(index));
        }
        return result;
    }

    private String cutCharAtIndex(String str, int index) {
        return str.substring(0, index) + (index < str.length() ? str.substring(index + 1) : "");
    }

    private String prependIndex(String str, char ch) {
        return ch + "" + str;
    }

    private Set<String> findAnagramSubString(String str) {
        Set<String> result = new HashSet<String>();
        result.add(str);
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                result.add(swap(str, i, j));
            }
        }
        return result;
    }

    private String swap(String str, int srcIndex, int destIndex) {
        return str.substring(0, srcIndex) + str.charAt(destIndex) + str.substring(srcIndex+1, destIndex) + str.charAt(srcIndex) + str.substring(destIndex+1);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|',value = {
            "ab|ba,ab",
            "eat|eat,tae,ate,tea,aet,eta",
            "a|a"
    })
    void testNormalString(String word, String expectedStr){
//        System.out.println(findAnagram("abcde"));
        Assert.assertEquals(Arrays.stream(expectedStr.split(",")).collect(Collectors.toSet()),findAnagram(word)) ;
    }
}
