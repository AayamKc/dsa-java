package edu.emory.cs.tree;

public abstract class AbstractBinaryNode<T extends Comparable<T>, N extends AbstractBinaryNode<T, N>> {
    protected T key;
    protected N parent;
    protected N left_child;
    protected N right_child;

    public AbstractBinaryNode(T key) {
        setKey(key);
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean hasLeftChild() {
        return left_child != null;
    }

    public boolean hasRightChild() {
        return right_child != null;
    }

    public boolean hasBothChildren() {
        return hasLeftChild() && hasRightChild();
    }

    /**
     * @return true if the specific node is the left child of this node.
     */
    public boolean isLeftChild(N node) {
        return left_child == node;
    }

    /**
     * @return true if the specific node is the right child of this node.
     */
    public boolean isRightChild(N node) {
        return right_child == node;
    }

    public T getKey() {
        return key;
    }

    public N getParent() {
        return parent;
    }

    public N getLeftChild() {
        return left_child;
    }

    public N getRightChild() {
        return right_child;
    }

    public N getGrandParent() {
        return hasParent() ? parent.getParent() : null;
    }

    @SuppressWarnings("unchecked")
    public N getSibling() {
        if (hasParent()) {
            N parent = getParent();
            return parent.isLeftChild((N) this) ? parent.getRightChild() : parent.getLeftChild();
        }

        return null;
    }

    public N getUncle() {
        return hasParent() ? parent.getSibling() : null;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public void setParent(N node) {
        parent = node;
    }

    public void setLeftChild(N node) {
        replaceParent(node);
        left_child = node;
    }

    public void setRightChild(N node) {
        replaceParent(node);
        right_child = node;
    }

    /**
     * Replaces the parent of the specific node to be this node.
     *
     * @param node the node whose parent to be replaced.
     */
    @SuppressWarnings("unchecked")
    protected void replaceParent(N node) {
        if (node != null) {
            if (node.hasParent()) node.getParent().replaceChild(node, null);
            node.setParent((N) this);
        }
    }

    /**
     * Replaces the old child with the new child if exists.
     *
     * @param oldChild the old child of this node to be replaced.
     * @param newChild the new child to be added to this node.
     */
    public void replaceChild(N oldChild, N newChild) {
        if (isLeftChild(oldChild)) setLeftChild(newChild);
        else if (isRightChild(oldChild)) setRightChild(newChild);
    }
}
