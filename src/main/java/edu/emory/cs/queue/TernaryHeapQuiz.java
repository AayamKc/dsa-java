package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }

    private int compare(int k1, int k2) {
        return priority.compare(keys.get(k1), keys.get(k2));
    }

    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
    }

    //comments to help me do the conditions
    //index of right most child is equal to (indexParent * 3) + 3
    // thus, the parent of the rightmost node is (indexChild - 2) / 3
    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());

    }

    private void swim(int k) {
        int parent = (k - 1) / 3;
        while (k > 0 && compare(parent, k) < 0) {
            Collections.swap(keys, parent, k);
            k = parent;
            parent = (k - 1) / 3;
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 0, size());
        T max = keys.remove(size());
        sink();
        return max;
    }

    private void sink() {
        int k = 0;
        while (k < size() / 3) {
            int leftChild = 3 * k + 1;
            int middleChild = 3 * k + 2;
            int rightChild = 3 * k + 3;
            int largest = k;
            if (leftChild < size() && compare(leftChild, largest) > 0)
                largest = leftChild;
            if (middleChild < size() && compare(middleChild, largest) > 0)
                largest = middleChild;
            if (rightChild < size() && compare(rightChild, largest) > 0)
                largest = rightChild;
            if (largest == k) break;
            Collections.swap(keys, largest, k);
            k = largest;
        }
    }
    @Override
    public int size() {
        return keys.size() - 1;
    }

    public String toString() {
        return keys.toString();
    }

}