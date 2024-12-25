package util;

import java.util.Arrays;
import java.util.regex.Pattern;

public class IntervalParser {
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
}
