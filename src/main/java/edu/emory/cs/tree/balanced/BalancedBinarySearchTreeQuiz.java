package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.BinaryNode;
import edu.emory.cs.tree.AbstractBinarySearchTree;

public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    protected void balance(BinaryNode<T> node) {
        if (node == null || node.getParent() == null || node.getParent().getParent() == null)
            return;

        BinaryNode<T> grandParent = node.getParent().getParent();
        BinaryNode<T> parent = node.getParent();
        BinaryNode<T> uncle = grandParent.getLeftChild() == parent ? grandParent.getRightChild() : grandParent.getLeftChild();

        if (uncle == null || (!uncle.hasLeftChild() && !uncle.hasRightChild())) {
            BinaryNode<T> subtreeRoot;
            if (node == parent.getRightChild() && parent == grandParent.getLeftChild()) {
                rotateLeft(parent);
                subtreeRoot = parent;
                parent = node;
            } else if (node == parent.getLeftChild() && parent == grandParent.getRightChild()) {
                rotateRight(parent);
                subtreeRoot = parent;
                parent = node;
            } else {
                subtreeRoot = node;
            }

            if (parent.getLeftChild() == subtreeRoot) {
                rotateRight(grandParent);
            } else {
                rotateLeft(grandParent);
            }
        }
    }

}