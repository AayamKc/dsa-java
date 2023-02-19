package edu.emory.cs.sort;

import java.util.Arrays;

public abstract class RadixSort extends BucketSort<Integer> {
    public RadixSort() {
        super(10);
    }

    protected int getMaxBit(Integer[] array, int beginIndex, int endIndex) {
        Integer max = Arrays.stream(array, beginIndex, endIndex).reduce(Integer::max).orElse(null);
        return (max != null && max > 0) ? (int)Math.log10(max) + 1 : 0;
    }
}
