package stack;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NestedIteratorTest {
    @Test
    public void test12(){
        List<Integer> expected = Arrays.asList(new Integer[]{1,2,3});

        NestedIterator.NestedInteger one = new NestedIterator.NestedInteger();
        one.setFile(1);

        NestedIterator.NestedInteger two = new NestedIterator.NestedInteger();
        two.setFile(2);

        NestedIterator.NestedInteger three = new NestedIterator.NestedInteger();
        three.setFile(3);

        NestedIterator.NestedInteger threeList = new NestedIterator.NestedInteger();
        threeList.add(three);

        NestedIterator.NestedInteger twothree = new NestedIterator.NestedInteger();
        twothree.add(two);
        twothree.add(threeList);

        NestedIterator iterator = new NestedIterator(new ArrayList<>(){{add(one); add(twothree);}});
        List<Integer> result = iterator.flattenList(iterator);
        Assert.assertEquals(expected, result);
    }
}
