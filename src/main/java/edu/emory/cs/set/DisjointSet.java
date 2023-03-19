package edu.emory.cs.set;

import java.util.Arrays;

public class DisjointSet {
    private final int[] subsets;

    public DisjointSet(int size) {
        subsets = new int[size];
        Arrays.fill(subsets, -1);
    }

    public DisjointSet(DisjointSet set) {
        subsets = Arrays.copyOf(set.subsets, set.subsets.length);
    }

    public int find(int id) {
        return (subsets[id] < 0) ? id : find(subsets[id]);
    }

    public int findEf(int id) {
        return (subsets[id] < 0) ? id : (subsets[id] = find(subsets[id]));
    }

    public boolean inSameSet(int key1, int key2) {
        return find(key1) == find(key2);
    }

    public int union(int key1, int key2) {
        int r1 = find(key1);
        int r2 = find(key2);
        if (r1 == r2) return r1;

        if (subsets[r1] < subsets[r2]) {
            subsets[r1] += subsets[r2];
            subsets[r2] = r1;
            return r1;
        }
        else {
            subsets[r2] += subsets[r1];
            subsets[r1] = r2;
            return r2;
        }
    }

    public String printSub(){
        return Arrays.toString(subsets);
    }
}
