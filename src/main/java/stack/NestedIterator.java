package stack;

import java.util.ArrayList;
import java.util.List;

/**
 * You’re given a nested list of integers. Each element is either an integer or a list whose elements may also be integers or other integer lists. Your task is to implement an iterator to flatten the nested list.
 *
 * You will have to implement the Nested Iterator class. This class has the following functions:
 *
 *     Constructor: This initializes the iterator with the nested list.
 *     Next (): This returns the next integer in the nested list.
 *     Has Next (): This returns TRUE if there are still some integers in the nested list. Otherwise, it returns FALSE.
 *
 * Constraints
 *
 *     The nested list length is between 11 and 200200.
 *     The nested list consists of integers between [1,104][1,104].
 */
public class NestedIterator {

        // NestedIterator constructor inializes the stack using the given nestedList list
    public NestedIterator(List<NestedInteger> nestedList) {
        // Write your code here
    }

        // hasNext() will return True if there are still some integers in the
        // stack (that has nested_list elements) and, otherwise, will return False.
        public boolean hasNext() {
        // Replace this placeholder return statement with your code
        return false;
    }

        // Check if there is still an integer in the stack
        public int next() {
        // Replace this placeholder return statement with your code
        return -1;
    }

        // ------ Please don't change the following function ----------
        // flattenList function is used for testing porpuses.
        // Your code will be tested using this function
        public static List<Integer> flattenList(NestedIterator obj){
        List<Integer> result = new ArrayList<Integer>();

        while (obj.hasNext()) {
            result.add(obj.next());
        }
        return result;
    }

    private class NestedInteger {
        List<NestedInteger> list;
        int file;

        // Constructor initializes an empty nested list.
        public NestedInteger(){
            this.list = new ArrayList<NestedInteger>();
        }

        // Constructor initializes a single file.
        public NestedInteger(int value){
            this.file = value;
        }
        // @return true if this NestedInteger holds an integer value.
        public boolean isInteger() {
            return isFile();
        }


        // @return true if this NestedDirectories holds a single file, rather than a nested list.
        public boolean isFile(){
            if(this.file != 0)
                return true;
            return false;
        }

        // @return the single file that this NestedDirectories holds, if it holds a single file
        // Return null if this NestedDirectories holds a nested list
        public int getFile(){
            return this.file;
        }

        // Set this NestedDirectories to hold a single file.
        public void setFile(int value){
            this.list = null;
            this.file =  value;
        }

        // Set this NestedDirectories to hold a nested list and adds a nested file to it.
        public void add(NestedInteger ni){
            if (this.file != 0){
                this.list = new ArrayList<NestedInteger>();
                this.list.add(new NestedInteger( this.file));
                this.file = 0;
            }
            this.list.add(ni);
        }

        // @return the nested list that this NestedDirectories holds, if it holds a nested list
        // Return null if this NestedDirectories holds a single file
        public List<NestedInteger> getList(){
            return this.list;
        }
    }
}
