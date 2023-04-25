package edu.emory.cs.dynamic.fibonacci;

import java.util.Arrays;

public class FibonacciDynamic implements Fibonacci {
    @Override
    public int get(int k) {
        return getAux(k, createTable(k));
    }

    private int[] createTable(int k) {
        int[] table = new int[k + 1];
        table[0] = 0;
        table[1] = 1;
        Arrays.fill(table, 2, k + 1, -1);
        return table;
    }

    private int getAux(int k, int[] table) {
        if (table[k] == 0)
            table[k] = getAux(k - 1, table) + getAux(k - 2, table);
        return table[k];
    }
}
