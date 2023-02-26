package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;
import edu.emory.cs.sort.divide_conquer.QuickSort;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {
    private final AbstractSort<T> engine;

    public HybridSortHW() {
        engine = new IntroSort<T>(new ShellSortKnuth<T>());
    }

    @Override
    public T[] sort(T[][] input) {
        sortRows(input);
        return mergeRows(input);
    }

    public static <T extends Comparable<T>> void sortRows(T[][] arr) {
        AbstractSort<T> engine = new IntroSort<>(new ShellSortKnuth<T>());
        for (T[] ts : arr) {
            engine.sort(ts);
        }
    }
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> T[] mergeRows(T[][] input) {
        int numRows = input.length;
        int largestRowSize = 0;

        // find the largest row size
        for (int i = 0; i < numRows; i++) {
            largestRowSize = Math.max(largestRowSize, input[i].length);
        }

        // initialize the pointers array to all zeros
        int[] pointers = new int[numRows];

        // initialize the output array
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), largestRowSize * numRows);
        int outputIndex = 0;

        while (outputIndex < output.length) {
            T smallest = null;
            int smallestIndex = -1;

            // find the smallest element across all rows at the current pointer position
            for (int i = 0; i < numRows; i++) {
                int pointer = pointers[i];

                if (pointer < input[i].length) {
                    T current = input[i][pointer];

                    if (smallest == null || current.compareTo(smallest) < 0) {
                        smallest = current;
                        smallestIndex = i;
                    }
                }
            }

            // add the smallest element to the output array and increment the corresponding pointer
            output[outputIndex++] = smallest;
            pointers[smallestIndex]++;
        }

        // trim the output array to the correct size
        return Arrays.copyOf(output, outputIndex);
    }
}
