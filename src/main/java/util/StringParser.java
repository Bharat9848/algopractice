package util;

import java.util.Arrays;
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
