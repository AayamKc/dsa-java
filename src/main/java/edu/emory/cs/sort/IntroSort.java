package edu.emory.cs.sort;

import edu.emory.cs.utils.Utils;

import java.util.Comparator;

public class IntroSort<T extends Comparable<T>> extends QuickSort<T> {
    private final AbstractSort<T> engine;

    public IntroSort(AbstractSort<T> engine) {
        this(engine, Comparator.naturalOrder());
    }

    public IntroSort(AbstractSort<T> engine, Comparator<T> comparator) {
        super(comparator);
        this.engine = engine;
    }

    @Override
    public void resetCounts() {
        super.resetCounts();
        if (engine != null) engine.resetCounts();
    }

    @Override
    public void sort(T[] array, int beginIndex, int endIndex) {
        final int maxdepth = getMaxDepth(beginIndex, endIndex);
        sort(array, beginIndex, endIndex, maxdepth);
        comparisons += engine.getComparisonCount();
        assignments += engine.getAssignmentCount();
    }

    protected int getMaxDepth(int beginIndex, int endIndex) {
        //return 2 * (int)Utils.log2(endIndex - beginIndex);
        return 0; //still gotta figure this out later

    }

    private void sort(T[] array, int beginIndex, int endIndex, int maxdepth) {
        if (beginIndex >= endIndex) return;

        if (maxdepth == 0)    // encounter the worst case
            engine.sort(array, beginIndex, endIndex);
        else {
            int pivotIndex = partition(array, beginIndex, endIndex);
            sort(array, beginIndex, pivotIndex, maxdepth - 1);
            sort(array, pivotIndex + 1, endIndex, maxdepth - 1);
        }
    }



}