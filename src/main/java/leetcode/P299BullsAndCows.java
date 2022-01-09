package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * You are playing the Bulls and Cows game with your friend.
 *
 * You write down a secret number and ask your friend to guess what the number is. When your friend makes a guess, you provide a hint with the following info:
 *
 *     The number of "bulls", which are digits in the guess that are in the correct position.
 *     The number of "cows", which are digits in the guess that are in your secret number but are located in the wrong position. Specifically, the non-bull digits in the guess that could be rearranged such that they become bulls.
 *
 * Given the secret number secret and your friend's guess guess, return the hint for your friend's guess.
 *
 * The hint should be formatted as "xAyB", where x is the number of bulls and y is the number of cows. Note that both secret and guess may contain duplicate digits.
 *
 *
 *
 * Example 1:
 *
 * Input: secret = "1807", guess = "7810"
 * Output: "1A3B"
 * Explanation: Bulls are connected with a '|' and cows are underlined:
 * "1807"
 *   |
 * "7810"
 *
 * Example 2:
 *
 * Input: secret = "1123", guess = "0111"
 * Output: "1A1B"
 * Explanation: Bulls are connected with a '|' and cows are underlined:
 * "1123"        "1123"
 *   |      or     |
 * "0111"        "0111"
 * Note that only one of the two unmatched 1s is counted as a cow since the non-bull digits can only be rearranged to allow one 1 to be a bull.
 *
 *
 *
 * Constraints:
 *
 *     1 <= secret.length, guess.length <= 1000
 *     secret.length == guess.length
 *     secret and guess consist of digits only.
 */
class P299BullsAndCows {
    String getHint(String secret, String guess) {
        Map<Integer, int[]> digitToFreqBullCowTriplet = new HashMap<>();
        for (int i = 0; i < secret.length(); i++) {
            int digit = Integer.parseInt(secret.charAt(i) + "");
            digitToFreqBullCowTriplet.putIfAbsent(digit, new int[]{0,0,0});
            int[] freqArr = digitToFreqBullCowTriplet.get(digit);
            freqArr[0] = freqArr[0] + 1;
        }

        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            Integer guessDigit = Integer.parseInt(guessChar + "");
            if(guessChar == secret.charAt(i)){
                int[] freqBullCowTriplet = digitToFreqBullCowTriplet.get(guessDigit);
                freqBullCowTriplet[1] += 1;
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            Integer guessDigit = Integer.parseInt(guessChar + "");
            if(guessChar != secret.charAt(i) && digitToFreqBullCowTriplet.get(guessDigit) != null){
                int[] freqBullCowTriplet = digitToFreqBullCowTriplet.get(guessDigit);
                if(freqBullCowTriplet[0] > freqBullCowTriplet[1] + freqBullCowTriplet[2]){
                    freqBullCowTriplet[2] += 1;
                }
            }
        }
        int bulls =0, cows =0;
        for (int[] triplet: digitToFreqBullCowTriplet.values()) {
            bulls += triplet[1];
            cows += triplet[2];
        }
        return bulls+ "A" + cows + "B";
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"1123|0111|1A1B",
            "1807|7810|1A3B",
            "1122|1222|3A0B"
    })
    void test(String secret, String guess, String expeceted){
        Assert.assertEquals(expeceted,getHint(secret, guess));
    }
}
