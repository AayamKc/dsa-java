package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    public final List<T> keys;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }

    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
        keys.add(null);
    }

    private int compare(int k1, int k2) {
        return priority.compare(keys.get(k1), keys.get(k2));
    }

    //takes a parameter and adds it to the TernaryHeap; ensures declared priority is maintained
    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());
    }

    //compares two nodes (child and a parent)
    //swaps if heap is out of order
    //comparisons happen until the root of the tree is reached
    private void swim(int k) {
        for (; 1 < k && compare((k + 1) / 3, k) < 0; k = (k + 1) / 3)
            Collections.swap(keys, (k + 1) / 3, k);
    }

    @Override
    //removes the highest priority item from heap; ensures declared priority is maintained
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 1, size());
        T max = keys.remove(size());
        sink();
        return max;
    }
    //ensures that swapped node in remove() method ends up in the correct place in the heap
    //k starts at root and "i" is always assigned as the largest child node
    //if the largest child is higher priority than k, a swap is made
    private void sink() {
        for (int k = 1, i = 2; i <= size(); k = i, i = (i * 3) - 1) {
            int right = i + 1, middle = i + 2, left = i + 3;
            if (i < size() && compare(i, right) < 0) {
                i = right;
            }
            if (i < size()-1 && compare(i, middle) < 0) {
                i = middle;
            }
            if (i < size()-2 && compare(i, left) < 0) {
                i = left;
            }

            if (compare(k, i) >= 0) break;
            Collections.swap(keys, k, i);
        }
    }
    // leaving this comment because I need to commit.........

    @Override
    public int size() {
        return keys.size() - 1;
    }

}