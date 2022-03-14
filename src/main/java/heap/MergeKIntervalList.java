package heap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

class MergeKIntervalList {

    List<Pair<Integer, Integer>> mergeIntervalKList(List<List<Pair<Integer, Integer>>> kList){

        if(kList ==null|| kList.size() <= 1){
            return kList.get(0);
        }

        PriorityQueue<Pair<List<Pair<Integer, Integer>>, Pair<Integer, Integer>>> heap = new PriorityQueue<Pair<List<Pair<Integer, Integer>>, Pair<Integer, Integer>>>((pair1, pair2) -> { if(pair1.getSec().getFirst() < pair2.getSec().getFirst()){ return -1;}else if (pair1.getSec().getFirst() == pair2.getSec().getFirst()){
            return pair1.getSec().getSec() - pair2.getSec().getSec();
        }else{
            return 1;
        } });
        //init heap
        for (int i = 0; i < kList.size(); i++) {
            List<Pair<Integer,Integer>> list = kList.get(i);
            Pair<Integer, Integer> ele = list.remove(0);
            heap.offer(new Pair<>(list, ele));
        }
        List<Pair<Integer, Integer>> mergedList = new ArrayList<>();
        while(!heap.isEmpty()){
            Pair<List<Pair<Integer, Integer>>,Pair<Integer, Integer>> listToMinEle = heap.remove();
            Pair<Integer, Integer> minEle =listToMinEle.getSec();
            List<Pair<Integer, Integer>> list = listToMinEle.getFirst();
            mergeIntervals(mergedList, minEle);
            if(!list.isEmpty()){
                Pair<Integer, Integer> nextEle = list.remove(0);
                heap.offer(new Pair<>(list, nextEle));
            }
        }

        return mergedList;
    }

    private void mergeIntervals(List<Pair<Integer, Integer>> mergedList, Pair<Integer, Integer> element) {
        if(mergedList.isEmpty()){
            mergedList.add(element);
        }else{
            Pair<Integer, Integer> lastElement = mergedList.get(mergedList.size() - 1);
            if(overlapping(lastElement, element)){
                mergedList.remove(mergedList.size()-1);
                mergedList.add(new Pair<>(lastElement.getFirst(), element.getSec()));
            }else {
                mergedList.add(element);
            }
        }
    }

    private boolean overlapping(Pair<Integer, Integer> first, Pair<Integer, Integer>sec){
        return first.getSec() >= sec.getFirst();
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[[1,3],[6,9]]-[[2,5]]-[[9,11]]]|[[1,5],[6,11]]",
            "[[[1,3],[6,9]]-[[2,5]]]|[[1,5],[6,9]]",
    })
    public void test(String kListOfList, String expected){
        String[] klists = kListOfList.split("-");
        List<List<Pair<Integer,Integer>>> arg =
        Arrays.stream(klists).map(listStr -> Arrays.stream(listStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] numbers = s.split(",");
                    return new Pair<>(Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1]));
                }).collect(Collectors.toList())).collect(Collectors.toList());
        List<Pair<Integer, Integer>> expectation = Arrays.stream(expected.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] numbers = s.split(",");
                    return new Pair<>(Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1]));
                }).collect(Collectors.toList());
        Assertions.assertEquals(expectation, mergeIntervalKList(arg));
    }

}