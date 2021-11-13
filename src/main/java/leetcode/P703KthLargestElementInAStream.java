package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.PriorityQueue;

public class P703KthLargestElementInAStream {

    private final PriorityQueue<Integer> heap;
    private final int heapMaxSize;

    P703KthLargestElementInAStream(int k, int[] nums) {
        heap = new PriorityQueue<>(k, Integer::compareTo);
        this.heapMaxSize = k;
        for (int i = 0; i < nums.length; i++) {
            this.add(nums[i]);
        }
    }

    public int add(int val) {
        if(heap.size() == heapMaxSize){
            if (heap.peek() <= val) {
                heap.remove();
                heap.add(val);
            }
        }else{
            heap.add(val);
        }
        return heap.peek();
    }

    /**
     *  k=1 nums = [] add{5}
     *  k=3 nums =[4,5,8,2] add(5)
     *  k=3 nums =[4]
     */
    @Test
    public void test(){
        P703KthLargestElementInAStream heap = new P703KthLargestElementInAStream(1, new int[0]);
        P703KthLargestElementInAStream heap2 = new P703KthLargestElementInAStream(1, new int[0]);
        P703KthLargestElementInAStream heap3 = new P703KthLargestElementInAStream(1, new int[0]);
        Assert.assertEquals(5, heap.add(5));
    }

}
