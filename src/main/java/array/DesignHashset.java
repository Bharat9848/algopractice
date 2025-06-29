package array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Design a MyHashSet class without using any built-in hash table libraries and implement the following methods in it:
 *
 *     void add(key): Inserts the value key into the HashSet.
 *
 *     bool contains(key): Returns TRUE if the key exists in the HashSet, FALSE otherwise.
 *
 *     void remove(key): Removes the value key if it exists in the HashSet.
 *
 * Constraints:
 *
 *     00 ≤≤ key ≤106≤106
 *
 *     At most, 104104 calls will be made to add, contains, and remove methods.
 */
public class DesignHashset {
    private class MyHashSet{
        private int keyRange;
        private Integer[] bucketArray = new Integer[1000001];

        public MyHashSet() {
            // Replace this placeholder statement with your code
        }

        public void add(int key) {
            if(bucketArray[key] == null){
                bucketArray[key] = key;
            }
        }

        public void remove(int key) {
            if(bucketArray[key] != null){
                bucketArray[key] = null;
            }
        }

        public boolean contains(int key) {
            if(bucketArray[key]!= null){
                return true;
            }
            return false;
        }

        private int hash(int key) {
            return key % keyRange;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "MyHashSet,add,add,contains,contains,remove|,9,4,3,9,9|,,,false,true,,",
            "MyHashSet,add,add,contains,contains,remove|,9,3,3,9,9|,,,true,true,,",
    })
    void test(String operStr, String argStr, String expectedStr){
        String[] operations = operStr.split(",");
        String[] arguments = argStr.split(",");
        String[] expected = expectedStr.split(",");
        MyHashSet set = new MyHashSet();
        for (int i = 1; i< operations.length; i++){
            if(operations[i].equals("contains")){
                Assertions.assertEquals(Boolean.parseBoolean(expected[i]), set.contains(Integer.parseInt(arguments[i])));
            } else if(operations[i].equals("add")) {
                   set.add(Integer.parseInt(arguments[i]));
               } else{
                set.remove(Integer.parseInt(arguments[i]));
            }
        }
    }

}
