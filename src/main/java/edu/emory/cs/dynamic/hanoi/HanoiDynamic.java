package edu.emory.cs.dynamic.hanoi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HanoiDynamic extends Hanoi {
    private int runCounter;

    public HanoiDynamic() {
        runCounter = 0;
    }

    @Override
    public List<String> solve(int n, char source, char intermediate, char destination) {
        List<String> list = new ArrayList<>();
        solve(list, n, source, intermediate, destination, new HashMap<>());
        return list;
    }

    private void solve(List<String> list, int n, char source, char intermediate, char destination, Map<String, int[]> map) {
        runCounter++;
        if (n == 0) return;
        int fromIndex = list.size();
        int[] sub = map.get(getKey(n - 1, source, intermediate));

        if (sub != null) addAll(list, sub[0], sub[1]);
        else solve(list, n - 1, source, destination, intermediate, map);

        String key = getKey(n, source, destination);
        list.add(key);
        sub = map.get(getKey(n - 1, intermediate, destination));

        if (sub != null) addAll(list, sub[0], sub[1]);
        else solve(list, n - 1, intermediate, source, destination, map);

        if (!map.containsKey(key))
            map.put(key, new int[]{fromIndex, list.size()});
    }

    private void addAll(List<String> list, int fromIndex, int toIndex) {
        for (int i = fromIndex; i < toIndex; i++)
            list.add(list.get(i));
    }

    public int getRunCount() {
        return runCounter;
    }

    public static void main(String[] args) {
        for (int n = 1; n <= 10; n++) {
            HanoiDynamic hanoiDynamic = new HanoiDynamic();
            hanoiDynamic.solve(n, 'A', 'B', 'C');
            int runCount = hanoiDynamic.getRunCount();
            System.out.printf("n = %d: Number of calls to solve: %d%n", n, runCount);
        }
    }
}