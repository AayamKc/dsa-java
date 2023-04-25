package edu.emory.cs.dynamic.hanoi;

import java.util.ArrayList;
import java.util.List;

public class HanoiRecursive extends Hanoi {
    private int run;

    public HanoiRecursive() {
        this.run = 0;
    }

    public int getRunCount() {
        return run;
    }

    @Override
    public List<String> solve(int n, char source, char intermediate, char destination) {
        List<String> list = new ArrayList<>();
        solve(list, n, source, intermediate, destination);
        return list;
    }

    private void solve(List<String> list, int n, char source, char intermediate, char destination) {
        run++;
        if (n == 0) return;
        solve(list, n - 1, source, destination, intermediate);
        list.add(getKey(n, source, destination));
        solve(list, n - 1, intermediate, source, destination);
    }
    public static void main(String[] args) {
        int numberOfRings = 7;
        HanoiRecursive hanoiRecursive = new HanoiRecursive();
        char source = 'A';
        char intermediate = 'B';
        char destination = 'C';

        // Solve the Tower of Hanoi problem with the given number of rings
        hanoiRecursive.solve(numberOfRings, source, intermediate, destination);

        // Print the number of runs (calls to the solve method)
        System.out.println("Number of runs for HanoiRecursive with " + numberOfRings + " rings: " + hanoiRecursive.getRunCount());
    }
}