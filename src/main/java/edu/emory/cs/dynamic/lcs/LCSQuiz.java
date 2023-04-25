package edu.emory.cs.dynamic.lcs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LCSQuiz extends LCSDynamic {
    public Set<String> solveAll(String a, String b) {
        char[] c = a.toCharArray();
        char[] d = b.toCharArray();
        int[][] table = new int[c.length + 1][d.length + 1];
        for (int[] row : table) {
            Arrays.fill(row, -1);
        }
        LCSRecursive(c, d, c.length, d.length, table);
        return findAllLCS(c, d, c.length, d.length, table);
    }

    private int LCSRecursive(char[] c, char[] d, int i, int j, int[][] table) {
        if (i == 0 || j == 0) {
            table[i][j] = 0;
            return 0;
        }

        if (c[i - 1] == d[j - 1]) {
            table[i][j] = 1 + LCSRecursive(c, d, i - 1, j - 1, table);
        } else {
            table[i][j] = Math.max(LCSRecursive(c, d, i - 1, j, table),
                    LCSRecursive(c, d, i, j - 1, table));
        }
        return table[i][j];
    }

    private Set<String> findAllLCS(char[] c, char[] d, int i, int j, int[][] table) {
        if (i == 0 || j == 0) {
            Set<String> emptySet = new HashSet<>();
            emptySet.add("");
            return emptySet;
        }

        if (c[i - 1] == d[j - 1]) {
            Set<String> lcsSet = findAllLCS(c, d, i - 1, j - 1, table);
            Set<String> result = new HashSet<>();
            for (String s : lcsSet) {
                result.add(s + c[i - 1]);
            }
            return result;
        } else {
            Set<String> lcsSet = new HashSet<>();
            if (table[i - 1][j] >= table[i][j - 1]) {
                lcsSet.addAll(findAllLCS(c, d, i - 1, j, table));
            }
            if (table[i][j - 1] >= table[i - 1][j]) {
                lcsSet.addAll(findAllLCS(c, d, i, j - 1, table));
            }
            return lcsSet;
        }
    }
}