package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.HeapSort;
import edu.emory.cs.sort.comparison.InsertionSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.comparison.ShellSortQuiz;
import edu.emory.cs.sort.divide_conquer.IntroSort;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;


public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {
    private static final int DEFAULT_PARALLELISM = 8;
    private final AbstractSort<T> engine;

    public HybridSortHW() {
        engine = new IntroSort<>(new ShellSortKnuth<T>());
    }

    @Override
    public T[] sort(T[][] input) {
        sortRows(input);
        return mergeRows(input);
    }

    public void sortRows(T[][] arr) {
        int threshold = 15;
        for (T[] row : arr) {
            mergeSort(row, threshold);
        }
    }

    private void mergeSort(T[] arr, int threshold) {
        if (arr.length <= threshold) {
            new InsertionSort<T>().sort(arr);
            return;
        }

        int mid = arr.length / 2;
        T[] left = Arrays.copyOfRange(arr, 0, mid);
        T[] right = Arrays.copyOfRange(arr, mid, arr.length);

        mergeSort(left, threshold);
        mergeSort(right, threshold);

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

    public T[] mergeRows(T[][] input) {
        int numRows = input.length;
        int totalSize = Arrays.stream(input).mapToInt(row -> row.length).sum();

        // Create an array to hold the values from each row
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), totalSize);

        // Create a merge heap to hold the first value from each row
        MergeHeap<T> heap = new MergeHeap<>(numRows);
        for (int i = 0; i < numRows; i++) {
            T[] row = input[i];
            if (row.length > 0) {
                heap.add(new IndexedValue<>(row[0], i, 0));
            }
        }

        // Merge the rows by repeatedly removing the smallest value from the merge heap
        int outputIndex = 0;
        while (!heap.isEmpty()) {
            IndexedValue<T> value = heap.removeMin();
            output[outputIndex++] = value.getValue();

            // If there are more values in the same row, add the next value to the merge heap
            int colIndex = value.getColIndex() + 1;
            if (colIndex < input[value.getRowIndex()].length) {
                heap.add(new IndexedValue<>(input[value.getRowIndex()][colIndex],
                        value.getRowIndex(), colIndex));
            }
        }

        return output;
    }

    // A merge heap implementation
    private static class MergeHeap<T extends Comparable<T>> {
        private final IndexedValue<T>[] heap;
        private int size;

        @SuppressWarnings("unchecked")
        public MergeHeap(int capacity) {
            heap = (IndexedValue<T>[]) Array.newInstance(IndexedValue.class, capacity + 1);
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void add(IndexedValue<T> value) {
            heap[++size] = value;
            swim(size);
        }

        public IndexedValue<T> removeMin() {
            IndexedValue<T> min = heap[1];
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
            IndexedValue<T> temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }


    // A helper class to hold a value along with its row and column indices
    private static class IndexedValue<T extends Comparable<T>> implements Comparable<IndexedValue<T>> {
        public final T value;
        public final int rowIndex;
        public final int colIndex;

        public IndexedValue(T value, int rowIndex, int colIndex) {
            this.value = value;
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
        }

        @Override
        public int compareTo(IndexedValue<T> o) {
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
}