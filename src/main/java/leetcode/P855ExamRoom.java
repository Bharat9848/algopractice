package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * There is an exam room with n seats in a single row labeled from 0 to n - 1.
 *
 * When a student enters the room, they must sit in the seat that maximizes the distance to the closest person. If there are multiple such seats, they sit in the seat with the lowest number. If no one is in the room, then the student sits at seat number 0.
 *
 * Design a class that simulates the mentioned exam room.
 *
 * Implement the ExamRoom class:
 *
 *     ExamRoom(int n) Initializes the object of the exam room with the number of the seats n.
 *     int seat() Returns the label of the seat at which the next student will set.
 *     void leave(int p) Indicates that the student sitting at seat p will leave the room. It is guaranteed that there will be a student sitting at seat p.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["ExamRoom", "seat", "seat", "seat", "seat", "leave", "seat"]
 * [[10], [], [], [], [], [4], []]
 * Output
 * [null, 0, 9, 4, 2, null, 5]
 *
 * Explanation
 * ExamRoom examRoom = new ExamRoom(10);
 * examRoom.seat(); // return 0, no one is in the room, then the student sits at seat number 0.
 * examRoom.seat(); // return 9, the student sits at the last seat number 9.
 * examRoom.seat(); // return 4, the student sits at the last seat number 4.
 * examRoom.seat(); // return 2, the student sits at the last seat number 2.
 * examRoom.leave(4);
 * examRoom.seat(); // return 5, the student sits at the last seat number 5.
 *
 *
 *
 * Constraints:
 *
 *     1 <= n <= 109
 *     It is guaranteed that there is a student sitting at seat p.
 *     At most 104 calls will be made to seat and leave.
 */
public class P855ExamRoom {

    private MaxHeapNodeComparator comparator;

    private class MaxHeapNode{
        int startSeat;
        int endSeat;
        int totalSeats;
        public MaxHeapNode(int startSeat, int endSeat, int totalSeats) {
            this.totalSeats = totalSeats;
            this.startSeat = startSeat;
            this.endSeat = endSeat;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MaxHeapNode that = (MaxHeapNode) o;
            return startSeat == that.startSeat && endSeat == that.endSeat && totalSeats == that.totalSeats;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startSeat, endSeat, totalSeats);
        }
    }
    private class MaxHeapNodeComparator implements Comparator<MaxHeapNode>{

        @Override
        public int compare(MaxHeapNode node1, MaxHeapNode node2) {
            int maxDistance1 = maxDistance(node1);
            int maxDistance2 = maxDistance(node2);

            if(maxDistance1 == maxDistance2){
                return node1.startSeat - node2.startSeat;
            }else {
                return -(maxDistance1 - maxDistance2);
            }
        }

        private int maxDistance(MaxHeapNode node){
            int maxDistanceFromBoth1;
            if(node.startSeat == -1){
                maxDistanceFromBoth1 = node.endSeat-1;
            }else if(node.endSeat == -1){
                maxDistanceFromBoth1 = node.totalSeats-1 -node.startSeat-1;
            }
            else{
                int middleEle1 = node.startSeat + (node.endSeat - node.startSeat)/2;
                maxDistanceFromBoth1 = Math.min(middleEle1- node.startSeat-1, node.endSeat -middleEle1-1);
            }
            return maxDistanceFromBoth1;
        }
    }
    public P855ExamRoom(){}
    Map<Integer, List<MaxHeapNode>> seatIndex = new HashMap<>();
    PriorityQueue<MaxHeapNode> freeSeatsRanges;
    private int totalSeats;
    public void examRoom(int n) {
        totalSeats = n;
        comparator = new MaxHeapNodeComparator();
        freeSeatsRanges = new PriorityQueue<>(comparator);
    }

    public int seat() {
            if(freeSeatsRanges.isEmpty()){
                MaxHeapNode range = new MaxHeapNode(0, -1, totalSeats);
                freeSeatsRanges.offer(range);
                seatIndex.putIfAbsent(0, new ArrayList<>());
                seatIndex.get(0).add(range);
                return 0;
            } else {
                MaxHeapNode node = freeSeatsRanges.remove();
                if(node.startSeat == -1){
                    int seat = 0;
                    MaxHeapNode newNode = new MaxHeapNode(seat, node.endSeat, totalSeats);
                    addNewRangeWithSeat(newNode, seat, node);
                    return 0;
                }
                if(node.endSeat == -1){
                    int endSeat = totalSeats - 1;
                    MaxHeapNode newNode = new MaxHeapNode(node.startSeat, endSeat, totalSeats);
                    addNewRangeWithSeat(newNode, endSeat, node);
                    return endSeat;
                }
                int midSeat = node.startSeat+ (node.endSeat - node.startSeat)/2;
                MaxHeapNode firstNode = new MaxHeapNode(node.startSeat, midSeat, totalSeats);
                MaxHeapNode secondNode = new MaxHeapNode(midSeat, node.endSeat, totalSeats);
                addNewRangeWithSeat(firstNode, midSeat, node);
                addNewRangeWithSeat(secondNode, midSeat, node);
                return midSeat;
            }
    }

    private void addNewRangeWithSeat(MaxHeapNode node, int seat, MaxHeapNode oldNode) {
        freeSeatsRanges.offer(node);
        seatIndex.putIfAbsent(seat, new ArrayList<>());
        seatIndex.get(seat).add(node);
        int otherSeat;
        if(seat == node.startSeat){
            otherSeat = node.endSeat;
        }else{
            otherSeat = node.startSeat;
        }
        if(otherSeat != -1){
            seatIndex.get(otherSeat).remove(oldNode);
            seatIndex.get(otherSeat).add(node);
        }
    }

    public void leave(int p) {
        List<MaxHeapNode> nodesWithSeat = seatIndex.get(p);
        if(p == 0 || p == totalSeats-1){
            if(!nodesWithSeat.isEmpty()){
                MaxHeapNode maxHeapNode = nodesWithSeat.get(0);
                freeSeatsRanges.remove(maxHeapNode);
                MaxHeapNode newNode;
                if(p==0){
                    newNode = new MaxHeapNode(-1, maxHeapNode.endSeat, maxHeapNode.totalSeats);
                }else{
                    newNode = new MaxHeapNode(maxHeapNode.startSeat, -1, maxHeapNode.totalSeats);
                }
                removeIndicesForP(newNode, p, maxHeapNode);
                freeSeatsRanges.offer(newNode);
            }
        }else {
            MaxHeapNode node1 = nodesWithSeat.get(0);
            MaxHeapNode node2 = nodesWithSeat.get(1);
            freeSeatsRanges.remove(node1);
            freeSeatsRanges.remove(node2);
            if(!(node1.startSeat == -1 && node2.endSeat == -1)){
                int otherSeatNode1 = node1.startSeat != p ? node1.startSeat : node1.endSeat;
                int otherSeatNode2 = node2.startSeat != p ? node2.startSeat : node2.endSeat;
                MaxHeapNode newNode = new MaxHeapNode(Math.min(otherSeatNode1, otherSeatNode2), Math.max(otherSeatNode1, otherSeatNode2), totalSeats);
                removeIndicesForP(newNode, p, node1);
                removeIndicesForP(newNode, p, node2);
                freeSeatsRanges.offer(newNode);
            }
        }

    }

    private void removeIndicesForP(MaxHeapNode newNode, int seat, MaxHeapNode oldNode){
        seatIndex.remove(seat);
        int otherSeat;
        if(oldNode.startSeat == seat){
            otherSeat = oldNode.endSeat;
        }else {
            otherSeat = oldNode.startSeat;
        }
        if(otherSeat != -1){
            seatIndex.get(otherSeat).remove(oldNode);
            seatIndex.get(otherSeat).add(newNode);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "ExamRoom,seat,leave,seat,seat,seat|3,null,0,null,null,null|null,0,null,0,2,1",
            "ExamRoom,seat,seat,seat,leave,leave,seat,seat,seat,seat,seat,seat,seat,seat,seat,leave,leave,seat,seat,leave,seat,leave,seat,leave,seat,leave,seat,leave,leave,seat,seat,leave,leave,seat,seat,leave|10,null,null,null,0,4,null,null,null,null,null,null,null,null,null,0,4,null,null,7,null,3,null,3,null,9,null,0,8,null,null,0,8,null,null,2|null,0,9,4,null,null,0,4,2,6,1,3,5,7,8,null,null,0,4,null,7,null,3,null,3,null,9,null,null,0,8,null,null,0,8,null",

            "ExamRoom,seat,seat,seat,seat,seat|10,null,null,null,null,null|null,0,9,4,2,6",
            "ExamRoom,seat,seat,seat,seat,leave,seat|10,null,null,null,null,4,null|null,0,9,4,2,null,5",
            "ExamRoom,seat,seat,seat,seat,leave,leave,seat|4,null,null,null,null,1,3,null|null,0,3,1,2,null,null,1",
    })
    void test(String operationStr, String argumentstr, String expected){
        P855ExamRoom examRoom = new P855ExamRoom();
        String[] operations = operationStr.split(",");
        String[] arguments = argumentstr.split(",");
        String[] expection = expected.split(",");
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            switch (operation) {
                case "ExamRoom": {
                    examRoom.examRoom(Integer.parseInt(arguments[i]));
                    break;
                }
                case "seat": {
                    Assertions.assertEquals(Integer.parseInt(expection[i]), examRoom.seat());
                    break;
                }
                case "leave": {
                    examRoom.leave(Integer.parseInt(arguments[i]));
                    break;
                }
            }

        }
    }
}
