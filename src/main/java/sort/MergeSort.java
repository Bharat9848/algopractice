package sort;

import java.util.HashMap;
import java.util.Map;

/*
Merge sort is stable. It do not reverse order of two equal word.
 */
public class MergeSort {

    private String[] wordsMergeSort(String[] words){
        Map<String, Integer> wordToLength = new HashMap<>();
        return wordMergeRecursive(words, wordToLength, 0, words.length -1);
    }

    private String[] wordMergeRecursive(String[] words, Map<String, Integer> wordToLength, int beg, int end) {
        if(beg < end){
            int mid = beg + ((end - beg)/2);
            String[] firstHalf = wordMergeRecursive(words, wordToLength, beg, mid);
            String[] secondhalf = wordMergeRecursive(words, wordToLength, mid+1, end);
            return mergeSortedHalf(firstHalf, secondhalf, wordToLength);
        }else{
            return new String[]{words[beg]};
        }
    }

    private String[] mergeSortedHalf(String[] first, String[] second, Map<String, Integer> wordToLength) {
        String[] wordsTemp = new String[first.length + second.length];
        int a = 0, b = 0, c=0;
        while( a < first.length && b < second.length){
            int aLength = wordToLength.computeIfAbsent(first[a], String::length);
            int bLength = wordToLength.computeIfAbsent(second[b], String::length);
            if(aLength <= bLength){
                wordsTemp[c] = first[a];
                c+=1;
                a+=1;
            }else {
                wordsTemp[c] = second[b];
                c+=1;
                b+=1;
            }
        }
        if(a == first.length){
            while (b < second.length) {
                wordsTemp[c] = second[b];
                b+=1;
                c+=1;
            }
        }
        if(b == second.length){
            while (a < first.length) {
                wordsTemp[c] = first[a];
                a+=1;
                c+=1;
            }
        }
        return wordsTemp;
    }
}
