package edu.emory.cs.tree;

public class BinaryNode<T extends Comparable<T>> extends AbstractBinaryNode<T, BinaryNode<T>> {
    public BinaryNode(T key) {
        super(key);
    }
}
