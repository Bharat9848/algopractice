package hash.map.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * You are playing a number guessing game called “Bulls and Cows” with a friend.
 * <p>
 * You write down a secret number, and your friend tries to guess it. After each guess, you provide a hint based on the following:
 * <p>
 * Bulls: The number of digits that are in the correct position in the guess.
 * <p>
 * Cows: The number of digits that are in both the secret and the guess but in different positions. (These are non-bull digits that could become bulls if rearranged.)
 * <p>
 * Your task is to return a hint for the guess, formatted as “xAyB”, where:
 * <p>
 * x is the number of bulls.
 * <p>
 * y is the number of cows.
 * <p>
 * Note: Both the secret number and the guess may contain duplicate digits.
 * <p>
 * Constraints:
 * <p>
 * 1<=1<= secret.length, guess.length <=103<=103
 * <p>
 * secret.length == guess.length
 * <p>
 * secret and guess consist of digits only.
 *
 *
 */
// Special case if bulls should not be counted as cows.



public class BullsAndCows {

    public static String getHint(String secret, String guess) {
        char[] secArr = secret.toCharArray();
        char[] guessArr = guess.toCharArray();
        Map<Integer, Set<Integer>> secMap = new HashMap<>();
        Map<Integer, Set<Integer>> guessMap = new HashMap<>();
        int bulls = 0, cows = 0;
        for (int i = 0; i < secArr.length; i++) {
            int digit = secArr[i] - '0';
            int guessDigit = guessArr[i] - '0';
            if (digit == guessDigit) {
                bulls++;
            } else {
                guessMap.putIfAbsent(guessDigit, new HashSet<>());
                guessMap.get(guessDigit).add(i);
                secMap.putIfAbsent(digit, new HashSet<>());
                secMap.get(digit).add(i);
            }
        }
        for (Integer guessDigits: guessMap.keySet()) {
            if (secMap.containsKey(guessDigits)) {
                int guessSize = guessMap.get(guessDigits).size();
                int secSize = secMap.get(guessDigits).size();
                cows += Math.min(guessSize, secSize);
            }

        }
        return bulls + "A" + cows + "B";
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "536713|565523|2A1B",
            "5567|5675|1A3B",
            "5567|5567|4A0B",
            "5567|7655|0A4B",
            "5567|4490|0A0B",
    })
    void test(String secret, String guess, String expected) {
        Assertions.assertEquals(expected, getHint(secret, guess));
    }
}
