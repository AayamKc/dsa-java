package edu.emory.cs.sort.distribution;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Deque;
import java.util.function.Function;

public class RadixSortQuiz extends RadixSort{


    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        if(endIndex - beginIndex <= 1){ return; }
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        int div = (int) Math.pow(10, (maxBit - 1));
        ArrayList<Integer> indexes = msdSort(array, beginIndex, endIndex, key -> (key / div) % 10);
        for (Integer index : indexes) {
            sort(array, beginIndex, beginIndex + index);
        }

    }

    private ArrayList<Integer> msdSort(Integer[] array, int beginIndex, int endIndex, Function<Integer, Integer> bucketIndex) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        // add each element in the input array to the corresponding bucket
        for (int i = beginIndex; i < endIndex; i++)
            buckets.get(bucketIndex.apply(array[i])).add(array[i]);

        // merge elements in all buckets to the input array
        for (Deque<Integer> bucket : buckets) {
            if(!bucket.isEmpty())
                indexes.add(bucket.size());
            while (!bucket.isEmpty())
                array[beginIndex++] = bucket.remove();
        }
        return indexes;
    }



}
