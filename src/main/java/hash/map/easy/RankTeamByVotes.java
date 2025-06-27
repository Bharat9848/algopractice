package hash.map.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * Assume a ranking system where each gives a rank to all competing teams from highest to lowest. The final ranking is decided on the basis of the number of first-place votes they receive. If there’s a tie for first place, the second-place votes are used to break the tie; if there’s still a tie, the third-place votes are considered, and this continues until all positions have been evaluated. If teams remain tied after all positions have been reviewed, they are ranked alphabetically by their team names.
 *
 * Given an array of strings votes that represents the rankings given by all voters, sort the teams according to the specified ranking system and return a string that lists all the teams in the ranked order.
 *
 * Constraints:
 *
 *     1≤1≤ votes.length ≤1000≤1000
 *
 *     1≤1≤ votes[i].length ≤26≤26
 *
 *     votes[i].length == votes[j].length for 0≤0≤ i, j << votes.length
 *
 *     votes[i][j] is an uppercase English letter.
 *
 *     All characters of votes[i] are unique.
 *
 *     All characters present in votes[0] are also found in votes[i] where 1≤1≤ i << votes.length.
 */
public class RankTeamByVotes {
    record NameAndScore(Character ch, Map<Integer, Integer> score){
    }
    public static String rankTeams(String[] votes) {
        Map<Character, Map<Integer, Integer>> teamScoreMap = new HashMap<>();
        for (String vote: votes){
            var teamPosition = vote.toCharArray();
            for (int pos = 0; pos < teamPosition.length; pos++) {
                var positionCountMap = teamScoreMap.getOrDefault(teamPosition[pos], new HashMap<>());
                positionCountMap.put(pos, positionCountMap.getOrDefault(pos, 0) + 1);
                teamScoreMap.put(teamPosition[pos], positionCountMap);
            }
        }
        Comparator<NameAndScore> scoreComparator = (NameAndScore f1, NameAndScore f2) -> {
            for (int i = 0; i < votes[0].length(); i++) {
                if(f1.score().getOrDefault(i, 0) > f2.score().getOrDefault(i, 0)){
                    return -1;
                } else if (f1.score().getOrDefault(i, 0) < f2.score().getOrDefault(i, 0)){
                    return 1;
                }
            }
            return f1.ch() - f2.ch();
        };

        PriorityQueue<NameAndScore> sortedList = new PriorityQueue<>(scoreComparator);
        for (Character team: teamScoreMap.keySet()){
            sortedList.offer(new NameAndScore(team, teamScoreMap.get(team)));
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < votes[0].length(); i++){
            builder.append(sortedList.remove().ch);
        }
        return builder.toString();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "AB,BA|AB",
            "MNOPQ|MNOPQ",
            "DEF,FED,EDF|EDF",
            "X|X",
            "XYZ,ZXY,XZY|XZY"
    })
    void test(String votesStr, String finalListExpected){
        Assertions.assertEquals(finalListExpected, rankTeams(votesStr.split(",")));
    }
}
