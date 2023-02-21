package edu.emory.cs.sort.divide_conquer;

import java.util.Comparator;

public abstract class AbstractSort<T extends Comparable<T>> {
    private final Comparator<T> comparator;
    protected long comparisons; // number of comparisons happening -- makes it standardized across devices
    protected long assignments; // number of assignments happening -- once again, for standardization

    /** @param comparator specifies the precedence of comparable keys. */
    public AbstractSort(Comparator<T> comparator) {
        this.comparator = comparator;
        resetCounts();
    }

    /** @return the total number of comparisons performed during sort. */
    public long getComparisonCount() {
        return comparisons;
    }

    /** @return the total number of assignments performed during sort. */
    public long getAssignmentCount() {
        return assignments;
    }

    public void resetCounts() {
        comparisons = assignments = 0;
    }
    /**
     * @param array an array of comparable keys.
     * @param i     the index of the first key.
     * @param j     the index of the second key.
     * @return array[i].compareTo(array[j]).
     */
    // this compareTo method does not override the Comparable compareTo... check the params lol
    // this method makes it convenient to track the count
    //compareTo function utilizes Comparable
    protected int compareTo(T[] array, int i, int j) {
        comparisons++;
        return comparator.compare(array[i], array[j]);
    }

    /**
     * array[index] = value.
     * @param array an array of comparable keys.
     * @param index the index of the array to assign.
     * @param value the value to be assigned.
     */
    //similar to custom compareTo, assign keeps track of the count
    protected void assign(T[] array, int index, T value) {
        assignments++;
        array[index] = value;
    }

    /**
     * Swaps array[i] and array[j].
     * @param array an array of comparable keys.
     * @param i     the index of the first key.
     * @param j     the index of the second key.
     */
    protected void swap(T[] array, int i, int j) {
        T t = array[i];
        assign(array, i, array[j]);
        assign(array, j, t);
    }

    /**
     * Sorts the array in ascending order.
     * @param array an array of comparable keys.
     */
    public void sort(T[] array) {
        sort(array, 0, array.length);
    }

    /**
     * Sorts the array[beginIndex:endIndex].
     * @param array      an array of comparable keys.
     * @param beginIndex the index of the first key to be sorted (inclusive).
     * @param endIndex   the index of the last key to be sorted (exclusive).
     */
    abstract public void sort(T[] array, int beginIndex, int endIndex); //useful for homework number 1
}