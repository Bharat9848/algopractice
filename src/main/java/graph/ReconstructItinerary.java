package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a list of airline tickets where tickets[i] = [fromi, toi] represent a departure airport and an arrival airport of a single flight, reconstruct the itinerary in the correct order and return it.
 *
 * The person who owns these tickets always starts their journey from "JFK". Therefore, the itinerary must begin with "JFK". If there are multiple valid itineraries, you should prioritize the one with the smallest lexical order when considering a single string.
 *
 *     For example, the itinerary ["JFK", "EDU"] has a smaller lexical order than ["JFK", "EDX"].
 *
 *     Note: You may assume all tickets form at least one valid itinerary. You must use all the tickets exactly once.
 *
 * Constraints:
 *
 *     1≤1≤ tickets.length ≤300≤300
 *
 *     tickets[i].length =2=2
 *
 *     fromi.length =3=3
 *
 *     toi.length =3=3
 *
 *     fromi ≠= toi
 *
 *     fromi and toi consist of uppercase English letters.
 *
 *     TODO educative
 */
public class ReconstructItinerary {
    public static List<String> findItinerary(List<List<String>> tickets) {
        String start = "JFK";
        Map<String, List<String>> adjList = new HashMap<>();
        for (List<String> ticket: tickets){
            String src = ticket.get(0);
            String dest = ticket.get(1);
            adjList.putIfAbsent(src, new ArrayList<>());
            adjList.get(src).add(dest);
        }
        return new ArrayList<>();
    }
}
