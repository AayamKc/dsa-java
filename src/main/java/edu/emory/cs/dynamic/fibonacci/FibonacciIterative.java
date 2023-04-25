package edu.emory.cs.dynamic.fibonacci;

public class FibonacciIterative implements Fibonacci {
    @Override
    public int get(int k) {
        if (k < 2) return k;
        int f0 = 0, f1 = 1, f2;

        for (int i = 2; i < k; i++) {
            f2 = f0 + f1;
            f0 = f1;
            f1 = f2;
        }

        return f0 + f1;
    }
}
