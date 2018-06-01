package misc;

import org.junit.Test;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bharat on 4/3/18.
 */
public class Solution {

    List<Pair<Integer,Integer>> Problem1commonFloorsRange(List<Pair<Integer,Integer>>lift1, List<Pair<Integer,Integer>>lift2) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        if (lift1.size() == 0 || lift2.size() == 0) {
            return result;
        }
        Pair<Integer, Integer> l = null;
        Pair<Integer, Integer> r = null;
        for (int i = 0, j = 0; i < lift1.size() && j < lift2.size(); ) {
            if (l == null) {
                l = lift1.get(0);
                r = lift2.get(0);
            }
            if (l.getFirst() < r.getFirst() && l.getSec() < r.getFirst()) {
                i++;
                if (i < lift1.size())
                    l = lift1.get(i);
            } else if (r.getFirst() < l.getFirst() && r.getSec() < l.getFirst()) {
                j++;
                if (j < lift2.size())
                    r = lift2.get(j);
            } else if (l.getFirst() >= r.getFirst() && l.getSec() < r.getSec()) {
                result.add(l);
                r = new Pair<>(l.getSec() + 1, r.getSec());
                i++;
                if (i < lift1.size())
                    l = lift1.get(i);
            } else if (r.getFirst() >= l.getFirst() && r.getSec() < l.getSec()) {
                result.add(r);
                l = new Pair<>(r.getSec() + 1, r.getFirst());
                j++;
                if (j < lift2.size())
                    r = lift1.get(j);
            } else if (l.getFirst() >= r.getFirst() && l.getSec() > r.getSec()) {
                result.add(new Pair<>(l.getFirst(), r.getSec()));
                l = new Pair<>(r.getSec() + 1, l.getSec());
                j++;
                if (j < lift2.size())
                    r = lift2.get(j);
            } else if (l.getFirst() <= r.getFirst() && l.getSec() < r.getSec()) {
                result.add(new Pair<>(r.getFirst(), l.getSec()));
                r = new Pair<>(l.getSec() + 1, r.getSec());
                i++;
                if (i < lift1.size())
                    l = lift1.get(i);
            } else {
                result.add(l);
                i++;
                j++;
                if (i < lift1.size())
                    l = lift1.get(i);
                if (j < lift2.size())
                    r = lift2.get(j);
            }
        }
        return result;
    }

    @Test
    public void test1(){
        List<Pair<Integer,Integer>>lift1= new ArrayList<>();
        lift1.add(new Pair<>(1,1));
        lift1.add(new Pair<>(3,5));
        lift1.add(new Pair<>(6,10));
        List<Pair<Integer,Integer>>lift2 = new ArrayList<>();
        lift2.add(new Pair<>(1,2));
        lift2.add(new Pair<>(3,6));
        System.out.println(Problem1commonFloorsRange(lift1,lift2));
    }

}
