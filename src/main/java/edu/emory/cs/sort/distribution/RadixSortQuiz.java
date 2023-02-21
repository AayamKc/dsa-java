package edu.emory.cs.sort.distribution;
import java.util.ArrayList;
import java.util.Deque;
import java.util.function.Function;
public class RadixSortQuiz extends RadixSort {
    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int CurrentPOW = getMaxBit(array, beginIndex, endIndex);
        MSDSort(array, beginIndex, endIndex, CurrentPOW);
    }

    //MSD algorithm (if you are reading, I really hope this code is running)
    public void MSDSort(Integer[] array, int beginIndex, int endIndex, int CurrentPOW) {
        if (CurrentPOW == 0) return;
        int div = (int) Math.pow(10, CurrentPOW - 1);
        ArrayList<Integer> indexes = BucketSortMod(array, beginIndex, endIndex, key -> (key / div) % 10);
        for (int i = 9; i >= 0; i--) {
            MSDSort(array, endIndex - indexes.get(i), endIndex, CurrentPOW - 1);
            endIndex -= indexes.get(i);
        }
    }
    //pretty much the same implementation as BucketSort
    //returns an array of bucketSizes to make partitioning the array for recursive calls easier
    private ArrayList<Integer> BucketSortMod(Integer[] array, int beginIndex, int endIndex, Function<Integer, Integer> bucketIndex) {
        // add each element in the input array to the corresponding bucket
        ArrayList<Integer> bucketSizes = new ArrayList<>();
        for (int i = beginIndex; i < endIndex; i++)
            buckets.get(bucketIndex.apply(array[i])).add(array[i]);
        // merge elements in all buckets to the input array
        for (Deque<Integer> bucket : buckets) {
            bucketSizes.add(bucket.size());
            while (!bucket.isEmpty()) {
                array[beginIndex++] = bucket.remove();
            }
        }
        return bucketSizes;
    }
}