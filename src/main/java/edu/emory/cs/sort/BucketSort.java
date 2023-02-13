package edu.emory.cs.sort;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BucketSort<T extends Comparable<T>> extends AbstractSort<T> {
    protected List<Deque<T>> buckets;

    /** @param numBuckets the total number of buckets. */
    public BucketSort(int numBuckets) {
        super(null);
        buckets = Stream.generate(ArrayDeque<T>::new).limit(numBuckets).collect(Collectors.toList());
    }
}
