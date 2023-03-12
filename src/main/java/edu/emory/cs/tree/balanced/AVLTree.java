package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.balanced.AVLNode;
import edu.emory.cs.tree.balanced.AbstractBalancedBinarySearchTree;

public class AVLTree<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, AVLNode<T>> {
    @Override
    public AVLNode<T> createNode(T key) {
        return new AVLNode<>(key);
    }

    @Override
    protected void rotateLeft(AVLNode<T> node) {
        super.rotateLeft(node);
        node.resetHeights();
    }

    @Override
    protected void rotateRight(AVLNode<T> node) {
        super.rotateRight(node);
        node.resetHeights();
    }

    @Override
    protected void balance(AVLNode<T> node) {
        if (node == null) return;
        int bf = node.getBalanceFactor();

        if (bf == 2) {
            AVLNode<T> child = node.getLeftChild();

            if (child.getBalanceFactor() == -1)
                rotateLeft(child);

            rotateRight(node);
        }
        else if (bf == -2) {
            AVLNode<T> child = node.getRightChild();

            if (child.getBalanceFactor() == 1)
                rotateRight(child);

            rotateLeft(node);
        }
        else
            balance(node.getParent());
    }
}