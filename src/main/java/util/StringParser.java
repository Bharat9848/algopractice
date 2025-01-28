package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public class StringParser {
    public static int[][] parseIntArrayString(String arrStr, String regex) {
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(arrStr);
        return matcher.results()
                .map(matchResult -> matcher.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            return Arrays.stream(tokens).mapToInt(ch -> Integer.parseInt(ch)).toArray();
                        }
                )
                .toArray(int[][]::new);
    }

    public static char[][] parseCharArrayString(String arrStr, String regex) {
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(arrStr);
        return matcher.results()
                .map(matchResult -> matcher.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            var chars = new char[tokens.length];
                             for (int i = 0; i < chars.length; i++) {
                                chars[i] = tokens[i].charAt(0);
                             }
                             return chars;
                        }
                )
                .toArray(char[][]::new);
    }

    public static <T> List<List<T>> parseStringAsListOfList(String arrStr, String regex, Function<String, T> transformer) {
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(arrStr);
        return matcher.results()
                .map(matchResult -> matcher.group())
                .map(row -> {
                            var tokens = row.replace("[", "").replace("]", "").split(",");
                            List<T> sublist = Arrays.stream(tokens).map(String::trim).map(ch -> transformer.apply(ch)).collect(ArrayList<T>::new, (list, str)-> list.add(str), (list1, list2) -> list1.addAll(list2));
                            return sublist;
                    }
                )
                .toList();
    }

    public static LinkedListNode parseLinkedList(String listStr){
        var values = listStr.split(",");
        if(values.length == 0){
            return null;
        }
        LinkedListNode head = new LinkedListNode(Integer.parseInt(values[0]));
        for (int i = 1; i < values.length; i++) {
            head.next = new LinkedListNode(Integer.parseInt(values[i]));
        }
        return head;
    }

    public static class LinkedListNode {
        public int data;
        public LinkedListNode next;
        // LinkedListNode() will be used to make a LinkedListNode type object.
        public LinkedListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }
}
