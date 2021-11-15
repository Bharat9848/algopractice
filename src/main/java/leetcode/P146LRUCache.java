package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 *
 * Implement the LRUCache class:
 *
 *     LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 *     int get(int key) Return the value of the key if the key exists, otherwise return -1.
 *     void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 *
 * The functions get and put must each run in O(1) average time complexity.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 *
 * Explanation
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // cache is {1=1}
 * lRUCache.put(2, 2); // cache is {1=1, 2=2}
 * lRUCache.get(1);    // return 1
 * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
 * lRUCache.get(2);    // returns -1 (not found)
 * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
 * lRUCache.get(1);    // return -1 (not found)
 * lRUCache.get(3);    // return 3
 * lRUCache.get(4);    // return 4
 *
 *
 *
 * Constraints:
 *
 *     1 <= capacity <= 3000
 *     0 <= key <= 104
 *     0 <= value <= 105
 *     At most 2 * 105 calls will be made to get and put.
 */
public class P146LRUCache {
        private class DoublyListNode{
            int key;
            int val;
            DoublyListNode forward;
            DoublyListNode backward;
        }
        DoublyListNode headDummy;
        DoublyListNode tailDummy;
        int capacity;
        int size;
        Map<Integer, DoublyListNode> keyToNodePairs;

        public void init(int capacity) {
            this.capacity = capacity;
            keyToNodePairs = new HashMap<>();
            headDummy = new DoublyListNode();
            headDummy.key = -1;
            headDummy.val = -1;
            tailDummy = new DoublyListNode();
            tailDummy.key = -1;
            tailDummy.val = -1;
            headDummy.forward = tailDummy;
            tailDummy.backward = headDummy;
        }

    public P146LRUCache() {
    }

    /**
     * 0,1 ,normal
     * @param key
     * @return
     *
     * headdummy-2-1-taildummy
     */

    public int get(int key) {
            DoublyListNode node = keyToNodePairs.get(key);
            if(node == null){
                return -1;
            }else {
                //snip
                node.backward.forward = node.forward;
                node.forward.backward = node.backward;
                //placement
                insertAtHead(node);
            }
            return node.val;
    }

    private void insertAtHead(DoublyListNode node) {
        DoublyListNode oldHead = headDummy.forward;
        oldHead.backward = node;
        node.forward = oldHead;
        node.backward = headDummy;
        headDummy.forward = node;
    }

    //size == capacity size < capacity
        public void put(int key, int value) {
            DoublyListNode node= keyToNodePairs.get(key);
            if(node != null){
                node.val = value;
                get(key);
            }else{
                if(size == capacity){
                    DoublyListNode toRemove = tailDummy.backward;
                    toRemove.backward.forward = tailDummy;
                    tailDummy.backward = toRemove.backward;
                    toRemove.forward = null;
                    toRemove.backward = null;
                    //BUG
                    keyToNodePairs.remove(toRemove.key);
                    size--;
                }
                size++;
                DoublyListNode newNode = new DoublyListNode();
                newNode.val = value;
                newNode.key = key;
                keyToNodePairs.put(key, newNode);
                insertAtHead(newNode);
            }
        }


    @Test
    public void testHappy(){
        P146LRUCache cache = new P146LRUCache();
        cache.init(2);
        cache.put(1,1);
        cache.put(2,2);
        Assert.assertEquals(1, cache.get(1));
        cache.put(3,3);
        Assert.assertEquals(-1,  cache.get(2));
        cache.put(4,4);
        Assert.assertEquals(-1,  cache.get(1));
        Assert.assertEquals(3,  cache.get(3));
        Assert.assertEquals(4,  cache.get(4));
    }

    @Test
    public void testExit(){
        P146LRUCache cache = new P146LRUCache();
        cache.init(1);
        cache.put(1,1);
        cache.put(2,2);
        Assert.assertEquals(-1, cache.get(1));
    }

}
