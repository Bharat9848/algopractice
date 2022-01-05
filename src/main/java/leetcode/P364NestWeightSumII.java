package leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements may also be integers or other lists.
 *
 * The depth of an integer is the number of lists that it is inside of. For example, the nested list [1,[2,2],[[3],2],1] has each integer's value set to its depth. Let maxDepth be the maximum depth of any integer.
 *
 * The weight of an integer is maxDepth - (the depth of the integer) + 1.
 *
 * Return the sum of each integer in nestedList multiplied by its weight.
 *
 * Example 1:
 *
 * Input: nestedList = [[1,1],2,[1,1]]
 * Output: 8
 * Explanation: Four 1's with a weight of 1, one 2 with a weight of 2.
 * 1*1 + 1*1 + 2*2 + 1*1 + 1*1 = 8
 *
 * Example 2:
 *
 * Input: nestedList = [1,[4,[6]]]
 * Output: 17
 * Explanation: One 1 at depth 3, one 4 at depth 2, and one 6 at depth 1.
 * 1*3 + 4*2 + 6*1 = 17
 *
 * Constraints:
 *
 *     1 <= nestedList.length <= 50
 *     The values of the integers in the nested list is in the range [-100, 100].
 *     The maximum depth of any integer is less than or equal to 50.
 */
public class P364NestWeightSumII {
    private class NestedInteger {
        // Constructor initializes an empty nested list.
        public NestedInteger(){}
        private boolean isInteger;
        private Integer value;
        List<NestedInteger> nestedIntegers;
        // Constructor initializes a single integer.
        public NestedInteger(int value){
            this.value = value;
            isInteger = true;
        }

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger(){
            return isInteger;
        }

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger(){
            return value;
        }

        // Set this NestedInteger to hold a single integer.
        public void setInteger(int value){
            this.value = value;
            this.isInteger = true;
        }

        // Set this NestedInteger to hold a nested list and adds a nested integer to it.
        public void add(NestedInteger ni){
            if(nestedIntegers == null){
                this.isInteger = false;
                nestedIntegers = new ArrayList<>();
            }
            nestedIntegers.add(ni);
        }

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList(){
            return nestedIntegers;
        }
    }
    int depthSumInverse(List<NestedInteger> nestedList) {
        int maxDepth = 0;
        for (int i = 0; i < nestedList.size(); i++) {
            NestedInteger current = nestedList.get(i);
            maxDepth = Math.max(findDepth(current,1), maxDepth);
        }
        int sum = 0;
        for (int i = 0; i < nestedList.size(); i++) {
            sum += depthSumInverse(nestedList.get(i), maxDepth, 1);
        }
        return sum;
    }

    private int depthSumInverse(NestedInteger nestedInteger, int maxDepth, int parentDepth) {
        if(nestedInteger.isInteger()){
            return (maxDepth - parentDepth + 1)* nestedInteger.getInteger();
        }
        int sum = 0;
        List<NestedInteger> subList = nestedInteger.getList();
        for (int i = 0; i < subList.size(); i++) {
            NestedInteger current = subList.get(i);
            if(current.isInteger()) {
                sum += (maxDepth - (parentDepth+1) + 1)* current.getInteger();
            }else {
                sum += depthSumInverse(current, maxDepth, parentDepth+1);
            }
        }
        return sum;
    }

    private int findDepth(NestedInteger current, int parentDepth) {
        if(current.isInteger()){
            return parentDepth;
        }
        int currentDepth = parentDepth;
        List<NestedInteger> subList = current.getList();
        for (int i = 0; i < subList.size(); i++) {
            NestedInteger nestedInteger = subList.get(i);
            if(nestedInteger.isInteger()){
                currentDepth = Math.max(currentDepth, parentDepth+1);
            }else{
                currentDepth = Math.max(currentDepth, findDepth(nestedInteger, parentDepth+1));
            }
        }
        return currentDepth;
    }

    @Test
    void test(){
        List<NestedInteger> simpleNestedIntegers = List.of(new NestedInteger(3), new NestedInteger(4), new NestedInteger(7));
        Assert.assertEquals(14, depthSumInverse(simpleNestedIntegers));
        NestedInteger complexNestedIntegers = new NestedInteger();
        complexNestedIntegers.add(new NestedInteger(3));
        complexNestedIntegers.add(new NestedInteger(4));
        NestedInteger third = new NestedInteger();
        third.add(new NestedInteger(5));
        complexNestedIntegers.add(third);
        Assert.assertEquals(6+8+5, depthSumInverse(List.of(complexNestedIntegers)));
    }

    @Test
    void testExample2(){
        List<NestedInteger> arg = new ArrayList<>();
        arg.add(new NestedInteger(1));
        NestedInteger second = new NestedInteger();
        second.add(new NestedInteger(4));
        NestedInteger thirdLevel = new NestedInteger();
        thirdLevel.add(new NestedInteger(6));
        second.add(thirdLevel);
        arg.add(second);
        Assert.assertEquals(3+8+6, depthSumInverse(arg));
    }

    @Test
    void testExample3(){
//        [[1,1],2,[1,1]]
        List<NestedInteger> arg = new ArrayList<>();
        arg.add(new NestedInteger(2));
        NestedInteger second = new NestedInteger();
        second.add(new NestedInteger(1));
        second.add(new NestedInteger(1));
        arg.add(second);
        NestedInteger third = new NestedInteger();
        third.add(new NestedInteger(1));
        third.add(new NestedInteger(1));
        arg.add(third);
        Assert.assertEquals(4+2+2, depthSumInverse(arg));
    }

    @Test
    void testExample4(){
        List<NestedInteger> arg = new ArrayList<>();
        arg.add(new NestedInteger(0));
        Assert.assertEquals(0, depthSumInverse(arg));
    }
}
