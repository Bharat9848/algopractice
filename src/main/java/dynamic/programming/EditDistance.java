package dynamic.programming;

/*
 * Given you can remove/add/substitute one character which will incur distance 1. Given two strings find the between the two.
 * Bonus: if we recreate the edit distance.
 * */

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

class EditDistance {
    Integer editDistanceNonDP(String pattern, String text) {
        return editDistanceCharNonDP(pattern, text, pattern.length()-1, text.length() -1);
    }

    private Integer editDistanceCharNonDP(String pattern, String text, int p, int t) {
        if(p==0 && t==0) { return matchCost(pattern.charAt(p),text.charAt(t));}
        if(p==0){return t;}
        if(t==0){ return p; }
        int matchCost = editDistanceCharNonDP(pattern, text, p-1,t-1) + matchCost(pattern.charAt(p), text.charAt(t));
        int insertCost = editDistanceCharNonDP(pattern, text, p-1, t) + 1;
        int deleteCost = editDistanceCharNonDP(pattern, text, p, t-1) + 1;
        int finalCost = matchCost;
        if(finalCost > insertCost){
            finalCost = insertCost;
        }
        if(finalCost > deleteCost) {
            finalCost = deleteCost;
        }
        return finalCost;
    }

    private int matchCost(char p, char t) {
        return p == t ? 0 : 1;
    }

    Integer editDistanceDP(String pattern, String text){
        int[][] cost = new int[pattern.length()+1][text.length()+1];
        int[][] parent = new int[pattern.length()+1][text.length()+1];
        Arrays.stream(cost).forEach(row -> Arrays.fill(row,-1));
        cost[0][0] = 0;
        Arrays.stream(parent).forEach(row -> Arrays.fill(row,-1));
        Arrays.stream(parent).forEach(row -> row[0] = 2);
        Arrays.fill(parent[0],1);
        parent[0][0] = 0;
        Integer result = editDistanceDPChar(pattern, text, pattern.length(), text.length(), cost, parent);
        printAndParse(parent, pattern, text);
        return result;
    }

    private void printAndParse(int[][] parent, String pattern, String text) {
        int row = parent.length-1, col = parent[0].length-1;
        StringBuilder edit = new StringBuilder("");
        while(row >=0 && col >= 0){
            String editType = transalateToEditType(parent[row][col]);
            edit.insert(0, editType);
            switch (editType){
                case "I" : row--;
                break;
                case "M":
                case "S" : row--; col--;
                break;
                case "D" : col--;
            }

        }
        System.out.println(pattern);
        System.out.println(edit.toString());
        System.out.println(text);
    }

    private String transalateToEditType(int editType) {
        String result = "";
        switch (editType){
            case 1 : result ="I";
            break;
            case 0 : result = "M";
            break;
            case 2 : result = "D";
            break;
            case 3: result = "S";
            break;
            default: throw  new RuntimeException("no idea");
        }
        return result;
    }

    private Integer editDistanceDPChar(String pattern, String text, int p, int t, int[][] cost, int[][] parent) {
        if(cost[p][t] != -1) return cost[p][t];
        if(p==0){return t;}
        if(t==0){ return p; }
        int matchCost = editDistanceDPChar(pattern, text, p-1,t-1, cost, parent) + matchCost(pattern.charAt(p-1), text.charAt(t-1));
        int insertCost = editDistanceDPChar(pattern, text, p, t-1, cost, parent) + 1;
        int deleteCost = editDistanceDPChar(pattern, text, p-1, t, cost, parent) + 1;
        int finalCost = matchCost;
        parent[p][t] = (matchCost(pattern.charAt(p-1), text.charAt(t-1)) != 0? 3 : 0); //represent match
        if(finalCost > insertCost){
            finalCost = insertCost;
            parent[p][t] = 1; //represent insert
        }
        if(finalCost > deleteCost) {
            finalCost = deleteCost;
            parent[p][t] = 2; //represent match
        }
        cost[p][t] = finalCost;
        return finalCost;
    }


    @ParameterizedTest
    @CsvSource({"you,thou, 2","you should not,thou shalt not, 5","abbccddeeffgg,abbccdeeeffff, 3","catpillarrrrlq, patpillaerrols, 4", "hal, hal, 0", "advise, advice, 1", "he, she, 1", "a, b, 1"})
    public void testDP(String pat, String text, String distance) {
        Assert.assertEquals(editDistanceDP(pat, text), Integer.valueOf(distance));
    }
    @ParameterizedTest
    @CsvSource({"abbccddeeffgg,abbccdeeeffff, 3","catpillarrrrlq, patpillaerrols, 4", "hal, hal, 0", "advise, advice, 1", "he, she, 1"})
    public void test(String pat, String text, String distance) {
        Assert.assertEquals(editDistanceNonDP(pat, text), Integer.valueOf(distance));
    }
}
