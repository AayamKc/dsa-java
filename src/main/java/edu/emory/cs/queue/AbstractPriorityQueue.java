package edu.emory.cs.queue;

import java.util.Comparator;

public abstract class AbstractPriorityQueue <T extends Comparable<T>>{
    protected final Comparator<T> priority;
    //Comparators: naturalOrder(), reverseOrder()

    public AbstractPriorityQueue(Comparator<T> priority){
        this.priority = priority;
    }

    abstract public void add(T key);

    abstract public T remove();

    abstract public int size();

    public boolean isEmpty(){
        return size() == 0;
        //it is using an abstract method -- this is a benefit of an abstract method since you know
        // you want to use the size method but haven't implemented it yet
    }
}
