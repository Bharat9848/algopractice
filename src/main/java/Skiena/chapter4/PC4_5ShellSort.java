package Skiena.chapter4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/*

Given an original ordering of a turtle stack and a required ordering for the same turtle stack, your
job is to determine a minimal sequence of operations that rearranges the original stack into the required stack.
Input
The first line of the input consists of a single integer K giving the number of test cases. Each test
case consist on an integer n giving the number of turtles in the stack. The next n lines specify the
original ordering of the turtle stack. Each of the lines contains the name of a turtle, starting with the turtle
on the top of the stack and working down to the turtle at the bottom of the stack. Turtles have
unique names, each of which is a string of no more than eighty characters drawn from a character set
consisting of the alphanumeric characters, the space character and the period (‘.’). The next n lines
in the input gives the desired ordering of the stack, once again by naming turtles from top to bottom.
Each test case consists of exactly 2n + 1 lines in total. The number of turtles (n) will be less than or
equal to two hundred.
Output
For each test case, the output consists of a sequence of turtle names, one per line, indicating the order in
which turtles are to leave their positions in the stack and crawl to the top. This sequence of operations
should transform the original stack into the required stack and should be as short as possible. If more
than one solution of shortest length is possible, any of the solutions may be reported.
Print a blank line after each test case.
Sample Input
2
3
Yertle
Duke of Earl
Sir Lancelot
Duke of Earl
Yertle
Sir Lancelot
9
Yertle
Duke of Earl
Sir Lancelot
Elizabeth Windsor
Michael Eisner
Richard M. Nixon
Mr. Rogers
Ford Perfect
Mack
Yertle
Richard M. Nixon
Sir Lancelot
Duke of Earl
Elizabeth Windsor
Michael Eisner
Mr. Rogers
Ford Perfect
Mack

Sample Output
Duke of Earl

Sir Lancelot
Richard M. Nixon
Yertle
 */
public class PC4_5ShellSort {
   static  <T extends Comparable> List<T> shellSort(List<T> givenOrder, List<T> requiredOrder){
        //find the smallest element of increasing sequence of largest length
        int largestEleIndex = IntStream.range(0, givenOrder.size()).reduce(0, (a, b) ->  givenOrder.get(a).compareTo(givenOrder.get(b))> 1 ? a : b);
        int largestMinIndex = requiredOrder.size()-2;
        for (int i = largestEleIndex-1; i >= 0; i--) {
              if(givenOrder.get(i).equals(requiredOrder.get(largestMinIndex))){
                  largestMinIndex -= 1;
              }
        }
        largestMinIndex += 1;
        T largestMin = requiredOrder.get(largestMinIndex);
        System.out.println("debug ===" + givenOrder.get(largestMinIndex));
        int largestMinSortIndex = requiredOrder.indexOf(largestMin);
        //invoke move to top function for each string starting from largest non-order string
        List<T> result = new ArrayList<>();
        for (int i = largestMinIndex -1 ; i >=0 ; i--) {
            result.add(requiredOrder.get(i));
        }
        return  result;
    }

    static private int findLargestMin(int[] parentIndex, int index) {
        if(parentIndex[index] == -1){
            return index;
        }
        return findLargestMin(parentIndex, parentIndex[index]);
    }

    static private class KingPerson implements Comparable<KingPerson>{
        private String name;
        private List<KingPerson> heirarchy;
        public KingPerson(String name, List<KingPerson> requiredOrder){
            this.name = name;
            heirarchy = requiredOrder;
        }

        void setHierarchy(List<KingPerson> required){
            heirarchy = required;
        }

        @Override
        public int compareTo(KingPerson o) {
            Integer thisIndex = heirarchy.indexOf(this);
            Integer otherIndex = heirarchy.indexOf(o);
            return thisIndex.compareTo(otherIndex);
        }

        @Override
        public boolean equals(Object o){
            return this.name.equals(((KingPerson)o).name);
        }

        @Override
        public String toString(){
            return this.name;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int noTestCase = Integer.parseInt(sc.nextLine());
        List<KingPerson> currentOrder = new ArrayList<>();
        List<KingPerson> requiredOrder = new ArrayList<>();
        List<List<KingPerson>> result = new ArrayList<>(noTestCase);
        for (int i = 0; i < noTestCase; i++) {
            int personNo = Integer.parseInt(sc.nextLine());
            for (int j = 0; j < personNo; j++) {
                currentOrder.add(new KingPerson(sc.nextLine(), null));
            }
            for (int j = 0; j < personNo; j++) {
                requiredOrder.add(new KingPerson(sc.nextLine(), null));
            }
            for(KingPerson k : currentOrder){
                k.setHierarchy(requiredOrder);
            }
            result.add(shellSort(currentOrder, requiredOrder));
        }
        for (List<KingPerson> res: result) {
            for (int i = 0; i < res.size(); i++) {
                System.out.println(res.get(i));
            }
            System.out.println();
        }
    }
}
