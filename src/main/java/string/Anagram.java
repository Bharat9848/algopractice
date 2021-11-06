package string;

import org.junit.Assert;
import org.junit.Test;
import util.Pair;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Anagram {

    Set<String> findAnagram(String str) {
        return IntStream.range(0, str.length())
                .mapToObj(index -> new Pair<>(str.charAt(index), cutCharAtIndex(str, index)))
                .map(p -> new Pair<>(p.getFirst(), findAnagramSubString(p.getSec())))
                .flatMap(p -> p.getSec().stream().map(s -> prependIndex(s, p.getFirst())).collect(Collectors.toSet()).stream())
                .collect(Collectors.toSet());
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

    @Test
    public void testEdgeCases(){
        Assert.assertTrue(findAnagram("").isEmpty());
        Assert.assertTrue(findAnagram("a").equals(new HashSet<>(){{add("a");}}));
    }

    @Test
    public void testNormalString(){
        System.out.println(findAnagram("abcde"));
        Assert.assertEquals(findAnagram("eat"), new HashSet<>() {{
            add("eat");
            add("ate");
            add("tea");
            add("aet");
            add("eta");
            add("tae");
        }});
    }
}
