package edu.emory.cs.sort.divide_conquer;

import edu.emory.cs.sort.AbstractSort;

import java.util.Comparator;

public class QuickSort<T extends Comparable<T>> extends AbstractSort<T> {
    public QuickSort() {
        this(Comparator.naturalOrder());
    }

    public QuickSort(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    public void sort(T[] array, int beginIndex, int endIndex) {
        if (beginIndex >= endIndex) return;

        int pivotIndex = partition(array, beginIndex, endIndex);
        sort(array, beginIndex, pivotIndex);
        sort(array, pivotIndex + 1, endIndex);
    }

    protected int partition(T[] array, int beginIndex, int endIndex) {
        int fst = beginIndex, snd = endIndex;

        while (true) {
            while (++fst < endIndex && compareTo(array, beginIndex, fst) >= 0);
            while (--snd > beginIndex && compareTo(array, beginIndex, snd) <= 0);
            if (fst >= snd) break;
            swap(array, fst, snd);
        }

        swap(array, beginIndex, snd);
        return snd;
    }
}

