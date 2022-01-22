package leetcode;


import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import javax.print.DocFlavor;
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
 Example 2:

 Input: equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
 Output: [3.75000,0.40000,5.00000,0.20000]

 Example 3:

 Input: equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
 Output: [0.50000,2.00000,-1.00000,-1.00000]



 Constraints:

 1 <= equations.length <= 20
 equations[i].length == 2
 1 <= Ai.length, Bi.length <= 5
 values.length == equations.length
 0.0 < values[i] <= 20.0
 1 <= queries.length <= 20
 queries[i].length == 2
 1 <= Cj.length, Dj.length <= 5
 Ai, Bi, Cj, Dj consist of lower case English letters and digits.



 The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.
 */
 class P399EvaluateDivisionNotDone {

    private class UnionFind {
        private Map<String,String> parent;
        private Map<String, Integer> rank;
        private int noOfRoots;
        UnionFind(Set<String> variables) {
            int size = variables.size();
            this.parent = variables.stream().collect(Collectors.toMap(a->a,a->a));
            this.rank = variables.stream().collect(Collectors.toMap(a->a,a->1));
            noOfRoots = size;
        }

        public String find(String node) {
            if (parent.get(node).equals(node)) {
                return node;
            }
            String root = find(parent.get(node));
            parent.put(node, root);
            return root;
        }

        public boolean connected(String i, String j) {
            return find(i).equals(find(j));
        }

        public void union(String i, String j) {
            String rooti = find(i);
            String rootj = find(j);
            if (!rooti.equals(rootj)) {
                int rankI = rank.get(i);
                int rankJ = rank.get(j);
                if (rankI > rankJ) {
                    parent.put(rootj, rooti);
                } else if (rankI < rankJ) {
                    parent.put(rooti, rootj);
                } else {
                    parent.put(rooti, rootj);
                    rank.put(rootj, rank.get(rootj)+ 1);
                }
                noOfRoots--;
            }
        }

        public int noOfComponents(){
            return noOfRoots;
        }
    }


     double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
         double[] result = new double[queries.size()];
         Set<String> variables = new HashSet<>();
         for (int i = 0; i < equations.size(); i++) {
             List<String> equation = equations.get(i);
             variables.add(equation.get(0));
            variables.add(equation.get(1));
         }
         UnionFind variableGroup = new UnionFind(variables);
         Map<String, Double> equationsMap = new HashMap<>();
         for (int i = 0; i < equations.size(); i++) {
             List<String> equation = equations.get(i);
             String src = equation.get(0);
             String dest = equation.get(1);
             if(!variableGroup.connected(src, dest)){
                 variableGroup.union(src, dest);
             }
             equationsMap.put(src+","+dest, values[i]);
             equationsMap.put(dest+","+src, 1/values[i]);
         }
         Map<String, Map<String, List<String>>> rootToAdjMap = new HashMap<>();
         for (int i = 0; i < queries.size(); i++) {
             String num = queries.get(i).get(0);
             String denom = queries.get(i).get(1);
             if(!variables.contains(num) || (!variables.contains(denom))){
                 result[i] = -1.0;
             }else if(num.equals(denom)){
                 result[i] = 1.0;
             }else{
                 if(!variableGroup.find(num).equals(variableGroup.find(denom))){
                     result[i] = -1.0;
                 }else{
                     String root = variableGroup.find(num);
                     Map<String, List<String>> variableGraphMap = rootToAdjMap.get(root);
                     List<String> vertices = variables.stream().filter(str -> variableGroup.find(str).equals(root)).collect(Collectors.toList());
                     if(variableGraphMap == null){
                         Map<String, List<String>> adjMap = createMap(vertices, equations);
                         rootToAdjMap.put(root, adjMap);
                     }
                     double res = calculate(num, denom, equationsMap, rootToAdjMap.get(root), vertices.stream().collect(Collectors.toMap(x->x, x -> Boolean.FALSE)));
                     result[i] = res;
                 }
             }
         }
         return result;
     }

    private double calculate(String num, String denom, Map<String, Double> equationsMap, Map<String, List<String>> variableGraphMap, Map<String, Boolean> visited) {
        double result = 1.0;
        Stack<String> queue = new Stack<>();
        queue.push(num);
        visited.put(num, true);
        while(!queue.isEmpty()){
            String curr = queue.peek();
            if(curr.equals(denom)){
                return result;
            }
            List<String> edges = variableGraphMap.get(curr);
            int i = 0;
            for (; i < edges.size(); i++) {
                String dest = edges.get(i);
                if(!visited.get(dest)){
                    result *= equationsMap.get(curr+"," + dest);
                    visited.put(dest, true);
                    queue.push(dest);
                    break;
                }
            }
            if(i == edges.size()){
                queue.pop();
                result *= equationsMap.get(curr + "," + queue.peek());
            }
        }
        return result;
    }

    private Map<String, List<String>> createMap(List<String> vertices, List<List<String>> equations) {
        Map<String, List<String>> result = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            String num = equations.get(i).get(0);
            String deno = equations.get(i).get(1);
            if(vertices.contains(num) && vertices.contains(deno)){
                result.putIfAbsent(num, new ArrayList<>());
                result.putIfAbsent(deno, new ArrayList<>());
                result.get(num).add(deno);
                result.get(deno).add(num);
            }
        }
        return result;
    }


    @ParameterizedTest
     @CsvSource(delimiter = '|', value = {
             "[[x1,x2],[x2,x3],[x3,x4],[x4,x5]]|3.0,4.0,5.0,6.0|[[x1,x5],[x5,x2],[x2,x4],[x2,x2],[x2,x9],[x9,x9]]|360.00000,0.00833,20.00000,1.00000,-1.00000,-1.00000",
             "[[a,b],[c,d]]|1.0,1.0|[[a,c],[b,d],[b,a],[d,c]]|-1.00,-1.00,1.00,1.00",
             "[[a,b]]|0.5|[[a,b],[b,a],[a,c],[x,y]]|0.50000,2.00000,-1.00000,-1.00000",
             "[[a,b],[b,c]]|2.0,3.0,|[[a,c],[b,a],[a,e],[a,a],[x,x]]|6.0000,0.5000,-1.000,1.000,-1.000",
             "[[a,b],[b,c],[bc,cd]]|1.5,2.5,5.0|[[a,c],[c,b],[bc,cd],[cd,bc]]|3.75000,0.40000,5.00000,0.20000",

     })
     void test(String equationStr, String valuesStr, String queriesStr, String expectedStr){
        List<List<String>> equations = Arrays.stream(equationStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s1 -> !s1.isEmpty())
                .map(s1 -> Arrays.stream(s1.split(",")).collect(Collectors.toList())).collect(Collectors.toList());
        List<List<String>> queries = Arrays.stream(queriesStr.split("],\\["))
                .map(str -> str.replace("[", "").replace("]", "").trim())
                .filter(s1 -> !s1.isEmpty())
                .map(s1 -> Arrays.stream(s1.split(",")).collect(Collectors.toList())).collect(Collectors.toList());
        double[] expected = Arrays.stream(expectedStr.split(",")).mapToDouble(Double::parseDouble).toArray();
        double[] values = Arrays.stream(valuesStr.split(",")).mapToDouble(Double::parseDouble).toArray();
        Assert.assertArrayEquals(expected, calcEquation(equations, values, queries),0.00001);
    }






    public double[] calcEquationDFS(String[][] equations, double[] values, String[][] queries) {
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

}
