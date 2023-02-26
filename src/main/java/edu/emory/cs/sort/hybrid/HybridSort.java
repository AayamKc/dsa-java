package edu.emory.cs.sort.hybrid;

public interface HybridSort<T extends Comparable<T>> {
    T[] sort(T[][] input);
}

