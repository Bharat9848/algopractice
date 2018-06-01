package leetcode;


import org.junit.Test;
import util.PrintUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by bharat on 4/4/18.
 * Equations are given in the format A / B = k, where A and B are variables represented as strings,
 * and k is a real number (floating point number). Given some queries, return the answers.
 * If the answer does not exist, return -1.0.

 Example:
 Given a / b = 2.0, b / c = 3.0.
 queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
 return [6.0, 0.5, -1.0, 1.0, -1.0 ].

 The input is: vector<pair<string, string>> equations, vector<double>& values,
 vector<pair<string, string>> queries , where equations.size() == values.size(),
 and the values are positive. This represents the equations. Return vector<double>.

 According to the example above:

 equations = [ ["a", "b"], ["b", "c"] ],
 values = [2.0, 3.0],
 queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
 The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.
 */
public class P399EvaluateDivision {

    @Test
    public void test(){
        P399EvaluateDivision x = new P399EvaluateDivision();
        /*System.out.println( Arrays.toString(
                x.calcEquation(new String[][]{{"a","b"},{"b","c"},{"c","e"}},new double[]{2.0,3.0,4.0},
                        new String[][]{{"a","c"},{"b","a"},{"a","e"}})));*/
        System.out.println( Arrays.toString(
                x.calcEquation(new String[][]{{"a","b"},{"c","d"}},new double[]{1.0,1.0},
                        new String[][]{{"a","c"},{"b","d"},{"b","a"},{"d","c"}})));
    }

    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        double[] result = new double[queries.length];
        HashMap<String, List<Pair<String, Double>>> eq = initEqGraph(equations, values);
        for (int i=0; i< queries.length; i++) {

            String num = queries[i][0];
            String den = queries[i][1];
            if (!eq.keySet().contains(num)||!eq.keySet().contains(den) ) {
                result[i] = -1.0;
            } else if (num.equals(den)) {
                result[i] = 1.0;
            } else {
                Map<String, Boolean> visited = initVisited(eq.keySet());
                Map<String, Boolean> finished = initReached(eq.keySet());
                visited.put(num, true);

                List<String> queue = new ArrayList<>();
                queue.add(num);

                boolean reachedDeno = false;
                Map<String, Double> calVector = new HashMap<>();
                calVector.put(num,1.0);
                while (!queue.isEmpty() && !reachedDeno) {
                    String source = queue.remove(0);
                    finished.put(source, true);
                    for (Pair<String, Double> edge : eq.get(source)) {
                        String vertex = edge.getFirst();
                        if (!visited.get(vertex) || !finished.get(vertex)) {
                            if(calVector.get(source)==null){
                                calVector.put(source,1.0);
                            }
                            Double cal = calVector.get(source) * edge.getSec();
                            calVector.put(vertex, cal);
                            visited.put(vertex, true);
                            queue.add(vertex);
                            if (vertex.equals(den)) {
                                reachedDeno = true;
                            }
                        }
                    }
                }
                result[i] = calVector.getOrDefault(den,-1.0);
            }
        }
            return result;
        }



    private Map<String, Boolean> initReached(Set<String> vertexes) {
        return vertexes.stream().collect(Collectors.toMap(v -> v,v-> Boolean.FALSE));

    }

    private Map<String, Boolean> initVisited(Set<String> vertices) {
        return vertices.stream().collect(Collectors.toMap(v->v,v->Boolean.FALSE));
    }

    private HashMap<String,List<Pair<String,Double>>> initEqGraph(String[][] equations, double[] values) {
       HashMap<String,List<Pair<String,Double>>> eqGraph = new HashMap<>(equations.length);
        for (int i = 0; i < equations.length; i++) {
            String s = equations[i][0];
            String d = equations[i][1];
            eqGraph.putIfAbsent(s, new ArrayList<>());
            eqGraph.putIfAbsent(d, new ArrayList<>());
            eqGraph.get(s).add(new Pair<>(d,values[i]));
            eqGraph.get(d).add(new Pair<>(s,1/values[i]));
        }
        return eqGraph;
    }

    private class Pair<K,V>{
        final K first;
        final V sec;

        public Pair(K first, V sec) {
            this.first = first;
            this.sec = sec;
        }

        public K getFirst() {
            return first;
        }

        public V getSec() {
            return sec;
        }
    }
}
