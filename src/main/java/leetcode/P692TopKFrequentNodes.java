package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given an array of strings words and an integer k, return the k most frequent strings.
 *
 * Return the answer sorted by the frequency from highest to lowest. Sort the words with the same frequency by their lexicographical order.
 *
 * Input: words = ["i","love","leetcode","i","love","coding"], k = 2
 * Output: ["i","love"]
 * Explanation: "i" and "love" are the two most frequent words.
 * Note that "i" comes before "love" due to a lower alphabetical order.
 *
 * Input: words = ["the","day","is","sunny","the","the","the","sunny","is","is"], k = 4
 * Output: ["the","is","sunny","day"]
 * Explanation: "the", "is", "sunny" and "day" are the four most frequent words, with the number of occurrence being 4, 3, 2 and 1 respectively.
 * Constraints:
 *
 *     1 <= words.length <= 500
 *     1 <= words[i] <= 10
 *     words[i] consists of lowercase English letters.
 *     k is in the range [1, The number of unique words[i]]
 *
 *
 *
 * Follow-up: Could you solve it in O(n log(k)) time and O(n) extra space?
 */
public class P692TopKFrequentNodes {
    List<String> topKFrequent(String[] words, int k) {
        if(words.length == 0 || k==0){
            return Collections.emptyList();
        }
        Map<String, Integer> wordToHeapNodeMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String current = words[i];
            wordToHeapNodeMap.putIfAbsent(current, 0);
            wordToHeapNodeMap.put(current, wordToHeapNodeMap.get(current)+1);
        }
        PriorityQueue<Pair<String, Integer>> wordWithFreqHeap = new PriorityQueue<>(this::wordWithFreqComparator);
        for (Map.Entry<String, Integer> wordToFreq: wordToHeapNodeMap.entrySet()) {
            String current = wordToFreq.getKey();
            Integer freq = wordToFreq.getValue();
            if(wordWithFreqHeap.size() < k){
                wordWithFreqHeap.offer(new Pair<>(current, freq));
            }else{
                Pair<String, Integer> topEle = wordWithFreqHeap.peek();
                if(wordWithFreqComparator(new Pair<>(current, freq), topEle) > 0){
                    wordWithFreqHeap.remove();
                    wordWithFreqHeap.offer(new Pair<>(current, freq));
                }
            }
        }
        List<String> result = new LinkedList<>();
        while (wordWithFreqHeap.size()>0){
            result.add(0, wordWithFreqHeap.remove().getFirst());
        }
        return result;
    }

    int wordWithFreqComparator(Pair<String, Integer> word1, Pair<String, Integer> word2){
        if(word1.getSec() == word2.getSec()){
            return -(word1.getFirst().compareTo(word2.getFirst()));
        }else {
            return word1.getSec() - word2.getSec();
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "i,love,leetcode,i,love,coding|2|i,love|final words are equal in freq",
            "i,love,leetcode,i,love,coding|10|i,love,coding,leetcode|k is large",
            "i,love,leetcode,i,love,coding|4|i,love,coding,leetcode|k equals to words.length",
            "the,day,is,sunny,the,the,the,sunny,is,is|4|the,is,sunny,day|words with different freq",
    })
    void test(String wordStr, int k, String expectedStr){
        List<String> expected = Arrays.stream(expectedStr.split(",")).collect(Collectors.toList());
        Assertions.assertEquals(expected, topKFrequent(wordStr.split(","), k));
    }
}
