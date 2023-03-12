package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.AbstractBinaryNode;

public class RedBlackNode<T extends Comparable<T>> extends AbstractBinaryNode<T, RedBlackNode<T>> {
    private boolean is_red;

    public RedBlackNode(T key) {
        super(key);
        setToRed();
    }

    public void setToRed() { is_red = true; }

    public void setToBlack() { is_red = false; }

    public boolean isRed() { return is_red; }

    public boolean isBlack() { return !is_red; }
}
