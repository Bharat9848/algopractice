package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * There is a car with capacity empty seats. The vehicle only drives east (i.e., it cannot turn around and drive west).
 * <p>
 * You are given the integer capacity and an array trips where trips[i] = [numPassengersi, fromi, toi] indicates that the ith trip has numPassengersi passengers and the locations to pick them up and drop them off are fromi and toi respectively. The locations are given as the number of kilometers due east from the car's initial location.
 * <p>
 * Return true if it is possible to pick up and drop off all passengers for all the given trips, or false otherwise.
 * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
 * Output: false
 * Input: trips = [[2,1,5],[3,3,7]], capacity = 5
 * Output: true
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= trips.length <= 1000
 * trips[i].length == 3
 * 1 <= numPassengersi <= 100
 * 0 <= fromi < toi <= 1000
 * 1 <= capacity <= 105
 */
class P1094CarPooling {

    private class Trip{
        private int passengers;
        private int from;
        private int to;

        public Trip(int passengers, int from, int to) {
            this.passengers = passengers;
            this.from = from;
            this.to = to;
        }

        public int getPassengers() {
            return passengers;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }
    }
    public boolean carPooling(int[][] trips, int capacity) {
        PriorityQueue<Trip> queue = Arrays.stream(trips).map(trip -> new Trip(trip[0], trip[1], trip[2])).collect(() -> new PriorityQueue<Trip>(Comparator.comparingInt(Trip::getFrom).thenComparing(Trip::getTo)), PriorityQueue::offer, AbstractQueue::addAll);
        Trip last = queue.remove();
        int currentCapacity = last.getPassengers();
        while (currentCapacity <= capacity && !queue.isEmpty()){
            Trip current = queue.remove();
            if(current.getFrom() < last.getTo()){
                if(last.getTo() > current.getTo()){
                    queue.offer(new Trip(currentCapacity+ current.getPassengers(), current.getFrom(), current.getTo()));
                    queue.offer(new Trip(currentCapacity, current.getTo(), last.getTo()));
                    last = new Trip(currentCapacity, last.getFrom(), current.getFrom());
                }else if(last.getTo() == current.getTo()){
                    queue.offer(new Trip(currentCapacity+ current.getPassengers(), current.getFrom(), current.getTo()));
                    last = new Trip(currentCapacity, last.getFrom(), current.getFrom());
                }else {
                    queue.offer(new Trip(currentCapacity+ current.getPassengers(), current.getFrom(), last.getTo()));
                    queue.offer(new Trip(current.getPassengers(), last.getTo(), current.getTo()));
                    last = new Trip(currentCapacity, last.getFrom(), current.getFrom());
                }
            }else{
                last = current;
            }
            currentCapacity = last.getPassengers();
        }
        return currentCapacity <= capacity;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[8,1,7],[4,2,9],[9,3,6],[8,4,9],[6,6,8]]|28|false",
            "[[9,1,7],[4,2,4],[9,3,4],[7,4,5]]|23|true",
            "[[3,2,7],[3,7,9],[8,3,9]]|11|true",
            "[[2,1,4],[3,2,5],[5,5,8],[1,7,8]]|5|false",
            "[[2,1,5],[3,3,7]]|4|false",
            "[[2,1,5],[3,3,7]]|5|true",
            "[[2,1,5],[3,3,7]]|5|true",
            "[[6,1,5]]|5|false"
    })
    void test(String tripStr, int capacity, boolean expected) {
        int[][] grid = Arrays.stream(tripStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        Assertions.assertEquals(expected, carPooling(grid, capacity));

    }
}
