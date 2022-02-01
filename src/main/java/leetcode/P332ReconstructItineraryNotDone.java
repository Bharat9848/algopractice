package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival airports of one flight. Reconstruct the itinerary in order and return it.
 *
 * All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.
 *
 *     For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
 *
 * You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.
 *
 *
 *
 * Example 1:
 *
 * Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
 * Output: ["JFK","MUC","LHR","SFO","SJC"]
 *
 * Example 2:
 *
 * Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
 * Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical order.
 *
 *
 *
 * Constraints:
 *
 *     1 <= tickets.length <= 300
 *     tickets[i].length == 2
 *     fromi.length == 3
 *     toi.length == 3
 *     fromi and toi consist of uppercase English letters.
 *     fromi != toi
 */
public class P332ReconstructItineraryNotDone {

    List<String> findItinerary(List<List<String>> tickets) {
        List<String> itinerary = new ArrayList<>();
        Map<Pair<String, String>,Integer> allTickets = new HashMap<>();
        Map<String, List<String>> adjMap = new HashMap<>();
        for(List<String> ticket: tickets){
            String src = ticket.get(0);
            String dest = ticket.get(1);
            adjMap.putIfAbsent(src, new ArrayList<>());
            adjMap.get(src).add(dest);
            Pair<String, String> ticket1 = new Pair<>(src, dest);
            allTickets.putIfAbsent(ticket1,0);
            allTickets.put(ticket1, allTickets.get(ticket1) +1);
        }
        for (List<String> edges: adjMap.values()){
            Collections.sort(edges);
        }
        itinerary.add("JFK");
        startItineraryFrom("JFK", adjMap, itinerary, allTickets);
        return itinerary;
    }

    private boolean startItineraryFrom(String node, Map<String, List<String>> adjMap, List<String> itinerary, Map<Pair<String, String>, Integer> allTickets) {
        List<String> neighbours = adjMap.getOrDefault(node, new ArrayList<>());
        if(neighbours.isEmpty()){
            return allTickets.isEmpty();
        }
        boolean canComplete = true;
        for (String next : neighbours) {
            Pair<String, String> leg = new Pair<>(node, next);
            if (allTickets.containsKey(leg) && allTickets.get(leg) > 0) {
                Integer ticketCount = allTickets.get(leg);
                if(ticketCount == 1){
                    allTickets.remove(leg);
                }else{
                    allTickets.put(leg, ticketCount-1);
                }
                itinerary.add(next);
                canComplete = startItineraryFrom(next, adjMap, itinerary, allTickets);
                if (!canComplete) {
                    if(ticketCount == 1){
                        allTickets.put(leg, 1);
                    }else{
                        allTickets.put(leg, ticketCount+1);
                    }
                    itinerary.remove(next);
                } else {
                    return true;
                }
            }
        }
        return canComplete;
    }

    List<String> findItineraryTLE(List<List<String>> tickets) {
        Set<Pair<String, String>> allTickets = new HashSet<>();
        Map<String, PriorityQueue<String>> adjMap = new HashMap<>();
        for(List<String> ticket: tickets){
            String src = ticket.get(0);
            String dest = ticket.get(1);
            adjMap.putIfAbsent(src, new PriorityQueue<>((str1,str2)->(str1.compareTo(str2))));
            adjMap.get(src).offer(dest);
            allTickets.add(new Pair<>(src, dest));
        }
        Stack<String> stations = new Stack<>();
        stations.add("JFK");
        while (!allTickets.isEmpty()){
            String curr = stations.peek();
            PriorityQueue<String> ticketsFromCurr = adjMap.get(curr);
            if(ticketsFromCurr != null){
                String nextStations = ticketsFromCurr.remove();
                stations.push(nextStations);
                allTickets.remove(new Pair<>(curr, nextStations));
            }else{
                while(!stations.isEmpty()){
                    String stationToReuse = stations.pop();
                    String prevToReuse = stations.peek();
                    allTickets.add(new Pair<>(prevToReuse, stationToReuse));
                    PriorityQueue<String> prevStations = adjMap.get(prevToReuse);
                    if(prevStations.isEmpty()){
                        adjMap.put(prevToReuse, new PriorityQueue<>());
                        adjMap.get(prevToReuse).offer(stationToReuse);
                    }else{
                       String secondTop = prevStations.remove();
                       stations.push(secondTop);
                       allTickets.remove(new Pair<>(prevToReuse, secondTop));
                       prevStations.add(stationToReuse);
                       break;
                    }
                }
            }


        }
        return stations;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[[EZE,TIA],[EZE,HBA],[AXA,TIA],[JFK,AXA],[ANU,JFK],[ADL,ANU],[TIA,AUA],[ANU,AUA],[ADL,EZE],[ADL,EZE],[EZE,ADL],[AXA,EZE],[AUA,AXA],[JFK,AXA],[AXA,AUA],[AUA,ADL],[ANU,EZE],[TIA,ADL],[EZE,ANU],[AUA,ANU]]|JFK,AXA,AUA,ADL,ANU,AUA,ANU,EZE,ADL,EZE,ANU,JFK,AXA,EZE,TIA,AUA,AXA,TIA,ADL,EZE,HBA",


            "[EZE,AXA],[TIA,ANU],[ANU,JFK],[JFK,ANU],[ANU,EZE],[TIA,ANU],[AXA,TIA],[TIA,JFK],[ANU,TIA],[JFK,TIA]]|JFK,ANU,EZE,AXA,TIA,ANU,JFK,TIA,ANU,TIA,JFK",
            "[[MUC,LHR],[JFK,MUC],[SFO,SJC],[LHR,SFO]]|JFK,MUC,LHR,SFO,SJC",
            "[[JFK,SFO],[JFK,ATL],[SFO,ATL],[ATL,JFK],[ATL,SFO]]|JFK,ATL,JFK,SFO,ATL,SFO",
            "[[JFK,KUL],[JFK,NRT],[NRT,JFK]]|JFK,NRT,JFK,KUL",


            })
    void test(String ticketsStr, String expectedStr){
        List<List<String>> tickets = Arrays.stream(ticketsStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).collect(Collectors.toList())).collect(Collectors.toList());
        List<String> expected = Arrays.stream(expectedStr.split(",")).collect(Collectors.toList());
        Assertions.assertEquals(expected, findItinerary(tickets));
    }
}
