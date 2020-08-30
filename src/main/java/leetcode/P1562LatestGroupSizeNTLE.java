package leetcode;
/*
Given an array arr that represents a permutation of numbers from 1 to n. You have a binary string of size n that initially has all its bits set to zero.

At each step i (assuming both the binary string and arr are 1-indexed) from 1 to n, the bit at position arr[i] is set to 1. You are given an integer m and you need to find the latest step at which there exists a group of ones of length m. A group of ones is a contiguous substring of 1s such that it cannot be extended in either direction.

Return the latest step at which there exists a group of ones of length exactly m. If no such group exists, return -1.



Example 1:

Input: arr = [3,5,1,2,4], m = 1
Output: 4
Explanation:
Step 1: "00100", groups: ["1"]
Step 2: "00101", groups: ["1", "1"]
Step 3: "10101", groups: ["1", "1", "1"]
Step 4: "11101", groups: ["111", "1"]
Step 5: "11111", groups: ["11111"]
The latest step at which there exists a group of size 1 is step 4.
Example 2:

Input: arr = [3,1,5,4,2], m = 2
Output: -1
Explanation:
Step 1: "00100", groups: ["1"]
Step 2: "10100", groups: ["1", "1"]
Step 3: "10101", groups: ["1", "1", "1"]
Step 4: "10111", groups: ["1", "111"]
Step 5: "11111", groups: ["11111"]
No group of size 2 exists during any step.
Example 3:

Input: arr = [1], m = 1
Output: 1
Example 4:

Input: arr = [2,1], m = 2
Output: 2

Constraints:

n == arr.length
1 <= n <= 10^5
1 <= arr[i] <= n
All integers in arr are distinct.
1 <= m <= arr.length
Notes:have to optimize for the arrays bit grouping
Use map
 */

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

public class P1562LatestGroupSizeNTLE {
    private class Pair<K,V>{
        K first;
        V sec;

        public Pair(K first, V sec) {
            this.first = first;
            this.sec = sec;
        }

        public void setFirst(K first1){
            this.first = first1;
        }

        public void setSec(V sec1){
            this.sec = sec1;
        }


        public K getFirst() {
            return first;
        }

        public V getSec() {
            return sec;
        }

        public String toString(){
            return "("+first+ ", " + sec +")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair<?, ?> pair = (Pair<?, ?>) o;

            if (!first.equals(pair.first)) return false;
            return sec.equals(pair.sec);
        }

        @Override
        public int hashCode() {
            int result = first.hashCode();
            result = 31 * result + sec.hashCode();
            return result;
        }
    }
    public int findLatestStep(int[] arr, int m) {
        int result = -1;
        int groupSize = m;
       List<Pair<Integer, Integer>> parsedPair = new LinkedList<>(){{add(new Pair<Integer,Integer>(1, arr.length+1));}};
        Long start = System.currentTimeMillis();
        for (int i = arr.length-1; i >= 0 ; i--) {
            if(containGroupSize(parsedPair, groupSize)){
                result = i+1;
                break;
            }
            System.out.println(parsedPair);
            breakUsingBS(new Pair<>(arr[i], arr[i]+1), parsedPair);
            System.out.println(parsedPair);
        }
//        System.out.println("time taken = " + (System.currentTimeMillis() - start) + "ms");
        return result;
    }

    private boolean containGroupSize(List<Pair<Integer, Integer>> parsedPair, int groupSize) {
        return parsedPair.stream().anyMatch(p -> (p.sec - p.first) == groupSize);
    }

    private Boolean colease(Pair<Integer,Integer> newRange, List<Pair<Integer, Integer>> parsedPairs) {
        if(parsedPairs.size() == 0){
            parsedPairs.add(newRange);
            return false;
        }
        boolean isMerged = false;
        List<Pair<Integer,Integer>> mergedList = new ArrayList<>();
        for (int i = 0; i < parsedPairs.size(); i++) {
            Pair<Integer,Integer> current = parsedPairs.get(i);
            if(!isMerged && intersect(newRange, current) && i < parsedPairs.size() - 1 && intersect(newRange, parsedPairs.get(i+1))){
                Pair<Integer,Integer> merged = mergeThree(newRange, current, parsedPairs.get(i+1));
                mergedList.add(merged);
                isMerged = true;
                i+=1;
            } else if(!isMerged && intersect(newRange, current)){
                isMerged = true;
                mergedList.add(merge(newRange, current));
            }else if(!isMerged && before(newRange, current)){
                mergedList.add(newRange);
                mergedList.add(current);
                isMerged = true;
            }else if(!isMerged && i == parsedPairs.size() - 1){
                isMerged = true;
                mergedList.add(current);
                mergedList.add(newRange);
            }else{
                mergedList.add(current);
            }
        }
        return isMerged;
    }
    private void breakUsingBS(Pair<Integer,Integer> newRange, List<Pair<Integer, Integer>> parsedPairs){
        int beg = 0, end = parsedPairs.size()-1;
        while (beg <= end){
            int mid = beg + ((end - beg)/2);
            Pair<Integer, Integer> current =  parsedPairs.get(mid);
            if(intersect(current, newRange)){
                List<Pair<Integer, Integer>> brokenPairs = removePair(current, newRange);
                parsedPairs.remove(current);
                parsedPairs.addAll(mid, brokenPairs);
                break;
            }else if(before(newRange, current)){
                end = mid - 1;
            }else{
                beg = mid + 1;
            }
        }
    }

    private List<Pair<Integer, Integer>> removePair(Pair<Integer, Integer> bigger, Pair<Integer, Integer> smaller) {

        ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
        int fstStart = bigger.getFirst();
        int fstEnd =  smaller.getFirst();
        if(fstStart != fstEnd) {
            pairs.add( new Pair<>(fstStart, fstEnd));
        }
        int secStart =  smaller.getSec();
        int secEnd = bigger.getSec();
        if(secEnd != secStart){
            pairs.add(new Pair<>(secStart, secEnd));
        }
        return pairs;
    }

    private void coleaseUsingBinarySearch(Pair<Integer,Integer> newRange, List<Pair<Integer, Integer>> parsedPairs){
        if(parsedPairs.size() == 0){
            parsedPairs.add(newRange);
            return;
        }
        int beg = 0, end = parsedPairs.size() - 1;
        int loc = -1;
        boolean merged = false;
        while (beg <= end && !merged){
            int mid = beg + ((end - beg)/2);
            Pair<Integer, Integer> midElem =parsedPairs.get(mid);
            loc = mid;
            if(intersect(midElem, newRange)){
                 Pair<Integer, Integer> mergedPair = merge(midElem, newRange);
                 parsedPairs.remove(midElem);
                 parsedPairs.add(mid, mergedPair);

                merged = true;
            }else if(before(newRange, midElem)){
                end = mid - 1;

            }else{
                beg = mid + 1;

            }
        }
        if(!merged){
            if(loc < 0){ parsedPairs.add(0, newRange);}
            else if(loc == parsedPairs.size()) {
                parsedPairs.add(newRange);
            }else {
                if(before(newRange, parsedPairs.get(loc))) {
                    parsedPairs.add(loc, newRange);
                }else{
                    parsedPairs.add(loc +1, newRange);
                }
            }
        }else{
            if(loc -1 >= 0 && intersect(parsedPairs.get(loc-1), parsedPairs.get(loc))){
                Pair<Integer, Integer> prev = parsedPairs.get(loc - 1);
                Pair<Integer, Integer> mergePair = parsedPairs.get(loc);
                Pair<Integer, Integer> mergeP = merge(prev,mergePair);
                parsedPairs.remove(prev);
                parsedPairs.remove(mergePair);
                parsedPairs.add(loc-1, mergeP);
            }else if(loc+1 < parsedPairs.size() && intersect(parsedPairs.get(loc +1), newRange)) {
                Pair<Integer, Integer> next = parsedPairs.get(loc + 1);
                Pair<Integer, Integer> mergePair = parsedPairs.get(loc);
                Pair<Integer, Integer> mergeP = merge(next,mergePair);
                parsedPairs.remove(next);
                parsedPairs.remove(mergePair);
                parsedPairs.add(loc, mergeP);
            }
        }
    }
    private List<Pair<Integer, Integer>> coleaseNew(Pair<Integer,Integer> newRange, List<Pair<Integer, Integer>> parsedPairs) {
        if(parsedPairs.size() == 0){
            return new LinkedList<>(){{add(newRange);}};
        }
        boolean isMerged = false;
        for (int i = 0; i < parsedPairs.size() && !isMerged; i++) {
            Pair<Integer,Integer> current = parsedPairs.get(i);
            if(!isMerged && intersect(newRange, current) && i < parsedPairs.size() - 1 && intersect(newRange, parsedPairs.get(i+1))){
                Pair<Integer,Integer> next = parsedPairs.get(i+1);
                Pair<Integer,Integer> merged = mergeThree(newRange, current, next);
                parsedPairs.remove(current);
                parsedPairs.remove(next);
                parsedPairs.add(i, merged);
                isMerged = true;
                i+=1;
            } else if(intersect(newRange, current)){
                isMerged = true;
                parsedPairs.remove(i);
                parsedPairs.add(i , merge(newRange, current));
            }else if(before(newRange, current)){
                parsedPairs.add(i, newRange);
                isMerged = true;
            }else if(i == parsedPairs.size() - 1){
                isMerged = true;
                parsedPairs.add(newRange);
            }
        }
        return parsedPairs;
    }

    private boolean before(Pair<Integer, Integer> newRange, Pair<Integer, Integer> current) {
        return newRange.getFirst() < current.getFirst();
    }

    //(2,3) (3,4)
    private boolean intersect(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        return (first.getFirst() >= second.getFirst() && first.getFirst() <= second.getSec()) || (first.getSec() >=  second.getFirst() && first.getSec() <= second.getSec()) || (second.getFirst() >= first.getFirst() && second.getFirst() <= first.getSec()) ||(second.getSec() >= first.getFirst() && second.getSec()<=first.getSec());
    }

    private Pair<Integer, Integer> mergeThree(Pair<Integer, Integer> newRange, Pair<Integer, Integer> current, Pair<Integer, Integer> next) {
        return merge(merge(newRange,current), next);
    }

    private Pair<Integer, Integer> merge(Pair<Integer, Integer> newRange, Pair<Integer, Integer> pair) {
        int first = pair.getFirst() < newRange.getFirst()? pair.getFirst(): newRange.getFirst();
        int sec = pair.getSec() > newRange.getSec()? pair.getSec(): newRange.getSec();
        return new Pair<>(first, sec);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3,5,1,2,4; 1; 4",
            "1 ; 1; 1",
            "2,1; 2;2",
            "3,1,5,4,2; 2; -1",
            "3,2,5,6,10,8,9,4,1,7; 3; 9",
            "8,16,10,4,7,5,1,11,14,12,13,6,3,2,9,17,15,19,18;7;-1",
            "1,9,12,6,4,5,3,13,2,11,10,7,8;4;8",
            "5,3,4,7,8,14,11,9,2,12,1,13,10,6;6;-1"
    })
    void test(String permute, int size, int expected){
        int[] bitArr = Arrays.stream(permute.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, findLatestStep(bitArr, size));
//        System.out.println(merge( new Pair<>(1,2), new Pair<>(2, 3)));
        ArrayList<Pair<Integer, Integer>> parsedPairs = new ArrayList<>() {{
            add(new Pair<>(1, 5));
            add(new Pair<>(3, 4));
        }};
//        System.out.println(removePair(parsedPairs.get(0), parsedPairs.get(1)));
//        coleaseUsingBinarySearch( new Pair<>(2, 3), parsedPairs);
//        System.out.println(parsedPairs);
        System.out.println(intersect(new Pair<>(1,6), new Pair<>(2,3)));
//        System.out.println(before(new Pair<>(2,3), new Pair<>(1,2)));
    }
}
