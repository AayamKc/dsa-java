package edu.emory.cs.dynamic;

import java.util.ArrayList;
import java.util.List;

public class HanoiRecursive extends Hanoi {
    @Override
    public List<String> solve(int n, char source, char intermediate, char destination) {
        List<String> list = new ArrayList<>();
        solve(list, n, source, intermediate, destination);
        return list;
    }

    private void solve(List<String> list, int n, char source, char intermediate, char destination) {
        if (n == 0) return;
        solve(list, n - 1, source, destination, intermediate);
        list.add(getKey(n, source, destination));
        solve(list, n - 1, intermediate, source, destination);
    }
}