package sliding.window;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a string, dna, that represents a DNA subsequence, and a number kk, return all the contiguous subsequences (substrings) of length kk that occur more than once in the string. The order of the returned subsequences does not matter. If no repeated substring is found, the function should return an empty set.
 *
 *     The DNA sequence is composed of a series of nucleotides abbreviated as AA, CC, GG, and TT. For example, ACGAATTCCGACGAATTCCG is a DNA sequence. When studying DNA, it is useful to identify repeated sequences in it.
 *
 * Constraints:
 *
 *     1 ≤ dna.length ≤ 10^3
 *     dna[i] is either A, C, G, or T.
 *
 *     1≤k≤10
 */
public class DNASequence {
    public static Set<String> findRepeatedSequences(String dna, int k) {
        if(k > dna.length()){
            return new HashSet<>();
        }
        char[] dnaChars = dna.toCharArray();
        int left = 0, right = k-1;
        Map<String, Integer> seqToCount = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = left; i < right+1; i++) {
            sb.append(dnaChars[i]);
        }
        seqToCount.put(sb.toString(), 1);
        for (int i = k; i < dna.length(); i++) {
            sb.deleteCharAt(0);
            sb.append(dnaChars[i]);
            String current = sb.toString();
            seqToCount.putIfAbsent(current, 0);
            seqToCount.put(current, seqToCount.get(current) + 1);
        }
        HashSet<String> result = new HashSet<String>();
        for (String seq : seqToCount.keySet()) {
            if(seqToCount.get(seq) > 1){
                result.add(seq);
            }
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "CAGTTAG|6|",
            "CAGTTAG|10|",
            "CAGTTAG|2|AG",
            "CAGTTAGTT|3|AGT,GTT"
    })
    void test(String sequence, int k, String expectedStr){
        Set<String> repeatedSequences = findRepeatedSequences(sequence, k);
        if(expectedStr == null){
            Assertions.assertTrue(repeatedSequences.isEmpty());
        } else {
            Set<String>  expected = Arrays.stream(expectedStr.split(",")).collect(Collectors.toSet());
            Assertions.assertEquals(expected, repeatedSequences);
        }
    }
}
