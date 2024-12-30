package sliding.window.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;
import java.util.PriorityQueue;

/**
 * Given a string s and an integer k, find the length of the longest substring in s, where all characters are identical, after replacing, at most, k characters with any other lowercase English character.
 *
 * Constraints:
 *
 *     1≤ s.length ≤103
 *     s consists of only lowercase English characters
 *     0≤ k ≤ s.length
 */

/**
 * TODO educative - It can be done with simple adding and remove character in case its not meeting criteria
 */
public class LongestRepeatingCharWithReplacement {
    public static int longestRepeatingCharacterReplacement(String s, int k) {
        return -1;
    }

    private static void addChar(char ch, int feq, PriorityQueue<HeapNode> maxHeap, Map<Character, HeapNode> charFreqMap){
        var node  = new HeapNode(feq, ch);
        maxHeap.offer(node);
        charFreqMap.put(ch, node);
    }

    private static void updateFreq(char ch, int i, PriorityQueue<HeapNode> maxHeap, Map<Character, HeapNode> charFreqMap) {
        var heapNode = charFreqMap.get(ch);
        if(i > 0){
            var newHeapNode = new HeapNode(heapNode.count + i, ch);
            maxHeap.remove(heapNode);
            maxHeap.offer(newHeapNode);
            charFreqMap.put(ch, newHeapNode);
        } else if(i < 0 && i < heapNode.count) {
            var newHeapNode = new HeapNode(heapNode.count + i, ch);
            maxHeap.remove(heapNode);
            maxHeap.offer(newHeapNode);
            charFreqMap.put(ch, newHeapNode);
        } else if (i < 0 && i == heapNode.count){
            maxHeap.remove(heapNode);
            charFreqMap.remove(ch);
        }
    }

    private static class HeapNode{
        int count;
        char ch;
        public HeapNode(int count, char ch){
            this.count = count;
            this.ch = ch;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "roller|2|4",
            "dippitydip|4|6",
            "xxxxx|1|5",
            "fzfzfz|6|6",
            "aabccbb|2|5",
            "aaabbbbc|1|5",
            "aaabbbb|1|5",
            "aaabbc|1|4",
            "lmno|2|3",
            "abab|2|4"
    })
    void test(String s, int k, int expected){
        Assertions.assertEquals(expected, longestRepeatingCharacterReplacement(s, k));
    }

}
