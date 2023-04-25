package edu.emory.cs.lcs;

import edu.emory.cs.dynamic.lcs.LCSQuiz;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class LCSQuizTest {
    @Test
    public static void main(String[] args) {
        LCSQuiz lcsQuiz = new LCSQuiz();

        String a = "AGGTAB";
        String b = "GXTXAYB";

        Set<String> result = lcsQuiz.solveAll(a, b);
        System.out.println("All Longest Common Subsequences for \"" + a + "\" and \"" + b + "\":");
        for (String s : result) {
            System.out.println(s);
        }

        String c = "ABCBDAB";
        String d = "BDCAB";

        Set<String> result2 = lcsQuiz.solveAll(c, d);
        System.out.println("\nAll Longest Common Subsequences for \"" + c + "\" and \"" + d + "\":");
        for (String s : result2) {
            System.out.println(s);
        }
    }
}