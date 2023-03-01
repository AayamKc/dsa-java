package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.InsertionSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;
import edu.emory.cs.sort.divide_conquer.MergeSort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// removed all multithreading

public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {

    public HybridSortHW() {
    }

    @Override
    public T[] sort(T[][] input) {
        sortRows(input);
        return mergeRows(input);
    }

    public void sortRows(T[][] arr) {
        int threshold = 15;
        for (T[] row : arr) {
            hybridSort(row, threshold);
        }
    }

    private void hybridSort(T[] arr, int threshold) {

        //O(n) time if it's already sorted to check
        if (isSorted(arr)) return;

        //takes n time to check and n time to reverse
        //O(n) vs nlogn og mergeSort
        if(isSortedDescending(arr)){
            reverseArray(arr);
            return;
        }

        //insertion sort for smaller arrays
        if (arr.length <= threshold) {
            new InsertionSort<T>().sort(arr);
            return;
        }

        //mergeSort implementation... runs faster here than when I use the engine to mergeSort
        //average case is nlogn
        // tried introSort and mergeSort was always faster for me
        int mid = arr.length / 2;
        T[] left = Arrays.copyOfRange(arr, 0, mid);
        T[] right = Arrays.copyOfRange(arr, mid, arr.length);

        hybridSort(left, threshold);
        hybridSort(right, threshold);

        merge(arr, left, right);
    }

    private void merge(T[] arr, T[] left, T[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) <= 0) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }
        while (i < left.length) {
            arr[k++] = left[i++];
        }
        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }

    @SuppressWarnings("unchecked")
    public T[] mergeRows(T[][] input) {
        int numRows = input.length;
        int totalSize = Arrays.stream(input).mapToInt(row -> row.length).sum();

        // array to hold the values from each row
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), totalSize);

        // array to store current index for each row
        int[] index = new int[numRows];

        // Create a merge heap to hold the first value from each row
        MergeHeap<T> heap = new MergeHeap<>(numRows);
        for (int i = 0; i < numRows; i++) {
            T[] row = input[i];
            if (row.length > 0) {
                heap.add(new IndexOfValue<>(row[0], i, 0));
            }
        }

        //add values from input array to heap to output array
        int outputIndex = 0;
        while (!heap.isEmpty()) {
            IndexOfValue<T> value = heap.removeMin();
            output[outputIndex++] = value.getValue();

            // update index of row (row of removed value)
            index[value.getRowIndex()]++;

            // If there are more values in the same row, add the next value to the merge heap
            int colIndex = index[value.getRowIndex()];
            if (colIndex < input[value.getRowIndex()].length) {
                heap.add(new IndexOfValue<>(input[value.getRowIndex()][colIndex],
                        value.getRowIndex(), colIndex));
            }
        }

        return output;
    }

    // Min heap to merge arrays
    // brain too small to do recursion
    // all the heap methods do what they are titled
    private static class MergeHeap<T extends Comparable<T>> {
        private final IndexOfValue<T>[] heap;
        private int size;

        @SuppressWarnings("unchecked")
        public MergeHeap(int capacity) {
            heap = (IndexOfValue<T>[]) Array.newInstance(IndexOfValue.class, capacity + 1);
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void add(IndexOfValue<T> value) {
            heap[++size] = value;
            swim(size);
        }

        public IndexOfValue<T> removeMin() {
            IndexOfValue<T> min = heap[1];
            heap[1] = heap[size--];
            sink(1);
            return min;
        }

        private void swim(int i) {
            while (i > 1 && heap[i].compareTo(heap[i/2]) < 0) {
                swap(i, i/2);
                i /= 2;
            }
        }

        private void sink(int i) {
            while (2*i <= size) {
                int j = 2*i;
                if (j < size && heap[j+1].compareTo(heap[j]) < 0) j++;
                if (heap[j].compareTo(heap[i]) >= 0) break;
                swap(i, j);
                i = j;
            }
        }

        private void swap(int i, int j) {
            IndexOfValue<T> temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }


    // A helper class to hold a value along with its row and column indices
    private static class IndexOfValue<T extends Comparable<T>> implements Comparable<IndexOfValue<T>> {
        public final T value;
        public final int rowIndex;
        public final int colIndex;

        public IndexOfValue(T value, int rowIndex, int colIndex) {
            this.value = value;
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
        }

        @Override
        public int compareTo(IndexOfValue<T> o) {
            return value.compareTo(o.value);
        }

        public T getValue() {
            return value;
        }

        public int getColIndex() {
            return colIndex;
        }

        public int getRowIndex() {
            return rowIndex;
        }
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i-1].compareTo(arr[i]) > 0) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<T>> boolean isSortedDescending(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[i-1]) > 0) {
                return false;
            }
        }
        return true;
    }

    public static <T> void reverseArray(T[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            T temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
    
}