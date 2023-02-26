package edu.emory.cs.tree;

import edu.emory.cs.tree.AbstractBinarySearchTree;
import edu.emory.cs.tree.BinaryNode;

public class BinarySearchTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T, BinaryNode<T>> {
    /**
     * @param key the key of this node.
     * @return a binary node with the specific key.
     */
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<T>(key);
    }
}