package advance.algo.and.data.struct.book;
/* Invariants: 1. Every node can have at most two children
 * 2. The tree is complete and left adjusted. It means if tree height is H then leaf can have a height of H and H-1.
 * Leaf at height H will be on left hand side of the tree and leaf at right hand of tree have a height of H-1
 * 3. Node at a particular level will be minimum or maximum of its subtrees.
 *
 * Left adjusted balance binary tree using array : Index calculation For Terniary tree 1->3,4,5 (3i, 3i+1, 3i+2) 2->n 6,7,8(3i, 3i+1, 3i+2) similarly 3-> 9,10,11 Observation: Not a child index is colliding for any parent index using the formula (3i, 3i+1, 3i+2). Why: When a parent index is multiplied with (no-of-child) multiplier then In between resultant child indices for two consecutive parent indices there is a gap of no of child. e.g 3 -> 6; 4 -> 8  6 and 8 have a gap of 2. We are using this property to denote the child indices formula i.e. in case of two child policy first child index will be 2i and second will be 2i+1.
 * For a binary tree the same formula will be (2i, 2i+1)
 * */

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HeapPriorityQueue implements P1PriorityQueueAPI<Integer> {

    private ArrayList<Pair<Integer, Integer>> priorityToElementPair;
    private int branchingFactor;
    private boolean isMinHeap;

    public HeapPriorityQueue() {
    }

    public boolean isMinHeap() {
        return isMinHeap;
    }

    public void setMinHeap(boolean minHeap) {
        isMinHeap = minHeap;
    }

    public void setSize(int size) {
        priorityToElementPair = new ArrayList<>(size);
    }

    public void setBranchingFactor(int branchingFactor) {
        this.branchingFactor = branchingFactor;
    }

    /**
     * at most Logd(N) where d is the branching factor.
     * No of assignment = 3*Height of heap
     *
     * @param index
     */
    void bubbleUp(int index) {
        int temp = index;
        while (temp > 0) {
            int parent = getParentIndex(temp, branchingFactor);
            Integer parentPrior = priorityToElementPair.get(parent).getFirst();
            Integer childPrior = priorityToElementPair.get(temp).getFirst();
            if (!isMinHeap) {
                if (parentPrior < childPrior) {
                    swapEle(temp, parent);
                    temp = parent;
                } else {
                    break;
                }
            } else {
                if (parentPrior > childPrior) {
                    swapEle(temp, parent);
                    temp = parent;
                } else {
                    break;
                }
            }
        }
    }

    private int getParentIndex(int temp, int branchingFactor) {
        return temp % 2 == 0 ? (temp / branchingFactor) - 1 : temp / branchingFactor;
    }

    private void swapEle(int childIndex, int parent) {
        Pair<Integer, Integer> temp = priorityToElementPair.get(childIndex);
        priorityToElementPair.set(childIndex, priorityToElementPair.get(parent));
        priorityToElementPair.set(parent, temp);
    }

    /**
     * @param index
     * @TODO optimized the number of assignment.
     */
    void bubbleUpOptimized(int index) {

    }

    public static HeapPriorityQueue createHeap(int size, int branchingFactor) {
        HeapPriorityQueue heapPriorityQueue = new HeapPriorityQueue();
        heapPriorityQueue.setBranchingFactor(branchingFactor);
        heapPriorityQueue.setSize(size);
        return heapPriorityQueue;
    }

    /**
     * proof: that no of swaps will not be more than 2n ? Though it may appear the performance will be n * log n
     *
     * @param list
     * @param branchingFactor
     */
    public void heapify(List<Pair<Integer, Integer>> list, int branchingFactor) {
        priorityToElementPair = new ArrayList<>(list);
        this.branchingFactor = branchingFactor;
        int lastNonLeafIndex = getParentIndex(priorityToElementPair.size() - 1, branchingFactor);
        IntStream.range(0, lastNonLeafIndex).forEach(this::pushdown);
    }

    /**
     * TODO
     * comparison D*logD(N)
     *
     * @param index
     */
    void pushdown(int index) {
        int temp = index;
        int firstLeafIndex = (getParentIndex(priorityToElementPair.size() - 1, 2)) + 1;
        while (temp < firstLeafIndex) {
            Pair<Integer, Integer> candidate = priorityToElementPair.get(temp);
            int highestChildIndex = chooseRightfulChild(temp).orElseThrow();
            Pair<Integer, Integer> highestChild = priorityToElementPair.get(highestChildIndex);
            if (!isMinHeap) {
                if (candidate.getFirst() < highestChild.getFirst()) {
                    swapEle(temp, highestChildIndex);
                    temp = highestChildIndex;
                } else {
                    break;
                }
            } else {
                if (candidate.getFirst() > highestChild.getFirst()) {
                    swapEle(temp, highestChildIndex);
                    temp = highestChildIndex;
                } else {
                    break;
                }
            }
        }
    }

    private Optional<Integer> chooseRightfulChild(int temp) {
        IntStream childIndexStream = IntStream.range(branchingFactor * temp + 1, branchingFactor * temp + branchingFactor + 1)
                .filter(index -> index < priorityToElementPair.size());
        if (branchingFactor * temp + 1 >= priorityToElementPair.size()) {
            return Optional.empty();
        }
        Optional<Pair<Integer, Pair<Integer, Integer>>> indexToPairData;
        if (!isMinHeap) {
            indexToPairData = childIndexStream.mapToObj(index -> new Pair<>(index, priorityToElementPair.get(index))).max(Comparator.comparingInt(pair -> pair.getSec().getFirst()));
        } else {
            indexToPairData = childIndexStream.mapToObj(index -> new Pair<>(index, priorityToElementPair.get(index))).min(Comparator.comparingInt(pair -> pair.getSec().getFirst()));
        }
        return indexToPairData.map(Pair::getFirst);
    }

    /**
     * TODO to optimize the number of assignment.
     * comparison D*logD(N)
     *
     * @param index
     */
    void pushdownOptimized(int index) {

    }

    @Override
    public void insert(Integer element, int priority) {
        Pair<Integer, Integer> newElement = new Pair<>(priority, element);
        priorityToElementPair.add(newElement);
        bubbleUp(priorityToElementPair.size() - 1);
    }

    @Override
    public Integer peek() {
        return priorityToElementPair.get(0).getSec();
    }

    @Override
    public void update(Integer element, int priority) {
        int index = IntStream.range(0, priorityToElementPair.size())
                .filter(i -> priorityToElementPair.get(i).getSec().equals(element)).findFirst().orElseThrow();
        priorityToElementPair.set(index, new Pair<>(priority, element));
        int parentIndex = getParentIndex(index, branchingFactor);
        if (parentIndex >= 0) {
            Pair<Integer, Integer> parent = priorityToElementPair.get(parentIndex);
            if (parent.getFirst() < priority) {
                bubbleUp(index);
            }
        }
        Optional<Integer> highestChildIndexOpt = chooseRightfulChild(index);
        highestChildIndexOpt.ifPresent(highestChildIndex -> {
            Pair<Integer, Integer> maxChild = priorityToElementPair.get(highestChildIndex);
            if (maxChild.getFirst() > priority) {
                pushdown(index);
            }
        });
    }

    public void updateAssumeDuplicate(Integer element, int priority) {
    }

    @Override
    public void remove(Integer element) {

    }

    @Override
    public Integer top() {
        if (priorityToElementPair.size() > 0) {
            Pair<Integer, Integer> topElement = priorityToElementPair.remove(0);
            if (priorityToElementPair.size() > 0) {
                Pair<Integer, Integer> lastElement = priorityToElementPair.remove(priorityToElementPair.size() - 1);
                priorityToElementPair.add(0, lastElement);
                pushdown(0);
            }
            return topElement.getSec();
        } else {
            return -1;
        }
    }

    @Override
    public boolean isEmpty() {
        return priorityToElementPair.size() == 0;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,0;2,1;87,86;12,11;27,26;19,18|true|1,2,12,19,27,87",
            "1,0;2,1;87,86;12,11;27,26;19,18|false|87,27,19,12,2,1"
    })
    void insertTest(String input, boolean minHeap, String expectedArr) {
        HeapPriorityQueue heapPriorityQueue = new HeapPriorityQueue();
        int[] expected = Arrays.stream(expectedArr.split(",")).mapToInt(Integer::parseInt).toArray();
        heapPriorityQueue.setSize(expected.length);
        heapPriorityQueue.setBranchingFactor(2);
        heapPriorityQueue.setMinHeap(minHeap);

        Arrays.stream(input.split(";")).map(str -> {
            String[] noToPrior = str.split(",");
            return new Pair<>(Integer.parseInt(noToPrior[0]), Integer.parseInt(noToPrior[1]));
        }).forEach(noToPrior -> heapPriorityQueue.insert(noToPrior.getFirst(), noToPrior.getSec()));

        int[] actual = IntStream.range(0, expected.length).map(i -> heapPriorityQueue.top()).toArray();
        Assert.assertArrayEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "22:22,1:1,11:11,7:7,9:9,5:5,24:24,21:21|11|6|24,22,21,9,7,11,5,1",
            "22:22,1:1,11:11,7:7,9:9,5:5,24:24,21:21|24|2|22,21,11,9,7,5,24,1",
            "22:22,1:1,11:11,7:7,9:9,5:5,24:24,21:21|5|48|5,24,22,21,11,9,7,1"
    })
    void updateTest(String heapArrStr, int oldEle, int newPriorty, String expected) {
        List<Pair<Integer, Integer>> noToPriorityList = Arrays.stream(heapArrStr.split(",")).map(noStr -> {
            String[] noToPriority = noStr.split(":");
            int ele = Integer.parseInt(noToPriority[0]);
            int priority = Integer.parseInt(noToPriority[1]);
            return new Pair<>(ele, priority);
        }).collect(Collectors.toList());
        HeapPriorityQueue heap = noToPriorityList.stream().
                collect(() -> {
                    HeapPriorityQueue heapPriorityQueue = new HeapPriorityQueue();
                    heapPriorityQueue.setSize(0);
                    heapPriorityQueue.setBranchingFactor(2);
                    return heapPriorityQueue;
                }, (heap1, ele) -> heap1.insert(ele.getFirst(), ele.getSec()), (heap1, heap2) -> {
                    while (!heap2.isEmpty()) {
                        int top = heap2.top();
                        heap1.insert(top, top);
                    }
                });
        heap.update(oldEle, newPriorty);
        int[] expectedArr = Arrays.stream(expected.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] actualArr = IntStream.range(0, expectedArr.length).map(i -> heap.top()).toArray();
        Assert.assertArrayEquals(expectedArr, actualArr);
    }


}
