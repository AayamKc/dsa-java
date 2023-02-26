package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.InsertionSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {
    private static final int DEFAULT_PARALLELISM = Runtime.getRuntime().availableProcessors();
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
        int threshold = 10;
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            T[] row = arr[i];
            if (row.length > threshold) {
                engine.sort(row);
            } else {
                new InsertionSort<T>().sort(row);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T[] mergeRows(T[][] input) {
        int numRows = input.length;
        int totalSize = Arrays.stream(input).mapToInt(row -> row.length).sum();

        // Create a priority queue to hold the values from each row
        PriorityQueue<IndexedValue<T>> queue = new PriorityQueue<>(numRows,
                Comparator.comparing(IndexedValue::getValue));

        // Add the first value from each row to the queue
        for (int i = 0; i < numRows; i++) {
            T[] row = input[i];
            if (row.length > 0) {
                queue.add(new IndexedValue<>(row[0], i, 0));
            }
        }

        // Merge the rows by repeatedly removing the smallest value from the queue
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), totalSize);
        int outputIndex = 0;
        while (!queue.isEmpty()) {
            IndexedValue<T> value = queue.poll();
            output[outputIndex++] = value.getValue();

            // If there are more values in the same row, add the next value to the queue
            int colIndex = value.getColIndex() + 1;
            if (colIndex < input[value.getRowIndex()].length) {
                queue.add(new IndexedValue<>(input[value.getRowIndex()][colIndex],
                        value.getRowIndex(), colIndex));
            }
        }

        return output;
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
