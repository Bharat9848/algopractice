package leetcode;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Implement wildcard pattern matching with support for '?' and '*'.

 '?' Matches any single character.
 '*' Matches any sequence of characters (including the empty sequence).

 The matching should cover the entire input string (not partial).

 The function prototype should be:
 bool isMatch(const char *s, const char *p)

 Some examples:
 isMatch("aa","a") → false
 isMatch("aa","aa") → true
 isMatch("aaa","aa") → false
 isMatch("aa", "*") → true
 isMatch("aa", "a*") → true
 isMatch("ab", "?*") → true
 isMatch("aab", "c*a*b") → false
 * Created by bharat on 7/4/18.
 */
public class P44WildCardMatchingTLE {
    public boolean isMatch(String s, String p) {

        return isMatching(s,p,s.length()-1,p.length()-1);
    }

    private boolean isMatching(String s, String p, int si, int pi){
        if(si==-1){
            if(pi == -1){
                return true;
            }else {
                return isRecurrenceMatcher(getNextPattern(p,pi)) && isMatching(s,p,si,pi -1);
            }
        }
        if(si>=0 && pi<0){
            return false;
        }
        String pat = getNextPattern(p, pi);
        if(isRecurrenceMatcher(pat)){
            boolean found = false;
            if(pi-1>=0){
                while(si >= lengthOfNonRecPattern(p,pi-1)-1){
                    found = found | isMatching(s,p,si,pi-1);
                    si--;
                }
                return found;
            }else{
                return true;
            }

        }else{
            return (p.charAt(pi)=='?' ||s.charAt(si) == p.charAt(pi)) && isMatching(s, p, si-1,pi-1);
        }
    }

    private int lengthOfNonRecPattern(String p, int i) {
        int sum =0;
        for (int j = i; j >= 0 ; j--) {
            if(!isRecurrenceMatcher(p.substring(j,j+1))){
                sum+=1;
            }
        }
        return sum;
    }


    private boolean isRecurrenceMatcher(String pat) {
        return  pat.charAt(0) == '*';
    }

    private String getNextPattern(String p, int i) {
        return p.substring(i,i+1);
    }


    @Test
    public void test(){
        assertTrue(isMatch("a","a"));
        assertFalse(isMatch("","a*"));
        assertFalse(isMatch("a",""));
        assertTrue(isMatch("aaaa","a*"));
        assertTrue(isMatch("anadf","?*"));
        assertFalse(isMatch("ab","a"));
        assertTrue(isMatch("abbbbc","ab*bc"));
        assertFalse(isMatch("aab", "c*a*b"));
        assertTrue(isMatch("a","*a*"));
    }
}
