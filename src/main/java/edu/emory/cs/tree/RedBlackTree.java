package edu.emory.cs.tree;

public class RedBlackTree<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, RedBlackNode<T>> {
    @Override
    public RedBlackNode<T> createNode(T key) {
        return new RedBlackNode<T>(key);
    }

    @Override
    protected void balance(RedBlackNode<T> node) {
        if (isRoot(node))
            node.setToBlack();
        else if (node.getParent().isRed()) {
            RedBlackNode<T> uncle = node.getUncle();

            if (uncle != null && uncle.isRed())
                balanceWithRedUncle(node, uncle);
            else
                balanceWithBlackUncle(node);
        }
    }

    private void balanceWithRedUncle(RedBlackNode<T> node, RedBlackNode<T> uncle) {
        node.getParent().setToBlack();
        uncle.setToBlack();
        RedBlackNode<T> grandParent = node.getGrandParent();
        grandParent.setToRed();
        balance(grandParent);
    }
    private void balanceWithBlackUncle(RedBlackNode<T> node) {
        RedBlackNode<T> grandParent = node.getGrandParent();

        if (grandParent != null) {
            RedBlackNode<T> parent = node.getParent();

            if (grandParent.isLeftChild(parent) && parent.isRightChild(node)) {
                rotateLeft(parent);
                node = parent;
            }
            else if (grandParent.isRightChild(parent) && parent.isLeftChild(node)) {
                rotateRight(parent);
                node = parent;
            }

            node.getParent().setToBlack();
            grandParent.setToRed();

            if (node.getParent().isLeftChild(node))
                rotateRight(grandParent);
            else
                rotateLeft(grandParent);
        }
    }
}