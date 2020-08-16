package sort;

import java.util.HashMap;
import java.util.Map;

/*
bubble sort is stable.
 */
public class BubbleSort {
    void sortWordsBubble(String[] words) {
        Map<String, Integer> wordToLength =  new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            for (int j = 1 ; j < words.length - i ; j++) {
                int iLength = wordToLength.computeIfAbsent(words[j-1], String::length);
                int jLength = wordToLength.computeIfAbsent(words[j], String::length);
                if(iLength > jLength){
                    swap(words, j-1 , j);
                }
            }
        }
    }

    private void swap(String[] words, int left, int right) {
        String temp = words[left];
        words[left] = words[right];
        words[right] = temp;
    }

}
