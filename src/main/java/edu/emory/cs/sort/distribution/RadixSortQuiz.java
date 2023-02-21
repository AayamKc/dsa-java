package edu.emory.cs.sort.distribution;

import org.w3c.dom.ls.LSOutput;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.function.Function;

public class RadixSortQuiz extends RadixSort {


    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int MAX = getMaxBit(array, beginIndex, endIndex);
        do {
            int maxBit = getMaxBit(array, beginIndex, endIndex);
            if (maxBit == 0) return;
            int div = (int) Math.pow(10, (maxBit - 1));
            ArrayList<Integer> indexes = msdSort(array, beginIndex, endIndex, key -> (key / div) % 10);
            for (int i = 9; i >= 0; i--) {
                if (indexes.get(i) < 0)
                    sort(array, i + indexes.get(i), endIndex - indexes.get(i), MAX = MAX - 1);
            }

        }
        while(MAX > 1);
    }

    public void sort(Integer[] array, int beginIndex, int endIndex, int MAX){
        if (beginIndex >= endIndex || MAX <= 1) return;
        int div = (int) Math.pow(10, (MAX - 1));
        ArrayList<Integer> indexes = msdSort(array, beginIndex, endIndex, key -> (key / div) % 10);
        for (int i = 9; i >= 0; i--) {
            if (indexes.get(i) != 0)
                sort(array, i + indexes.get(i), endIndex - indexes.get(i));
        }

    }


    private ArrayList<Integer> msdSort(Integer[] array, int beginIndex, int endIndex, Function<Integer, Integer> bucketIndex) {
        ArrayList<Integer> indexes = new ArrayList<>();
        int size = endIndex - beginIndex;
        Integer[] temp = new Integer[size];

        // add each element in the input array to the corresponding bucket
        for (int i = beginIndex; i < endIndex; i++)
            buckets.get(bucketIndex.apply(array[i])).add(array[i]);
        // merge elements in all buckets to the temporary array
        int pos = 0;
        for (Deque<Integer> bucket : buckets) {
            if(!bucket.isEmpty())
                indexes.add(bucket.size());
            else
                indexes.add(0);
            while (!bucket.isEmpty())
                temp[pos++] = bucket.remove();
        }

        // copy the sorted elements from the temporary array back to the input array
        System.arraycopy(temp, 0, array, beginIndex, size);

        return indexes;
    }

}
