package string;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by bharat on 11/3/18.
 */
public class ReverseString {

    String reverseWordOrderRecursiveP1(String str) {
        int lastSpace = -1;
        for (int i = str.length()-1; i >= 0 ; i--) {
            if (str.charAt(i) == ' ') {
                lastSpace = i;
                break;
            }
        }
        if (lastSpace == -1) {
            return str;
        } else {
            return reverseWordOrderRecursiveP1(str.substring(lastSpace + 1)) + " " + str.substring(0, lastSpace);
        }

    }

    String reverseWordOrderIterativeP1(String x) {
        String result = "";
        int lastSpaceIndex = x.length();
        for (int i = x.length() - 1; i >= 0; i--) {
            if (x.charAt(i) == ' ') {
                result = result + x.substring(i + 1, lastSpaceIndex) + " ";
                lastSpaceIndex = i;
            }
        }
        result = result + x.substring(0, lastSpaceIndex);
        return result;

    }


    @Test
    public void testP1() {
        Assert.assertEquals("jlpo kiii", reverseWordOrderRecursiveP1("kiii jlpo"));
        Assert.assertEquals("jlpokiii", reverseWordOrderRecursiveP1("jlpokiii"));
        Assert.assertEquals("jlpo kiii", reverseWordOrderIterativeP1("kiii jlpo"));
        Assert.assertEquals("jlpokiii", reverseWordOrderIterativeP1("jlpokiii"));
    }
}
