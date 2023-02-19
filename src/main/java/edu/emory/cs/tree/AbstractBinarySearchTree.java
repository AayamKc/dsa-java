package edu.emory.cs.tree;

public abstract class AbstractBinarySearchTree<T extends Comparable<T>, N extends AbstractBinaryNode<T, N>> {
    protected N root;

    public AbstractBinarySearchTree() {
        setRoot(null);
    }

    /** @return a new node with the specific key. */
    abstract public N createNode(T key);

    public boolean isRoot(N node) { return root == node; }

    public N getRoot() { return root; }

    public void setRoot(N node) {
        if (node != null) node.setParent(null);
        root = node;
    }

    /** @return the node with the specific key if exists; otherwise, {@code null}. */
    public N get(T key) {
        return findNode(root, key);
    }

    /** @return the node with the specific key if exists; otherwise, {@code null}. */
    protected N findNode(N node, T key) {
        if (node == null) return null;
        int diff = key.compareTo(node.getKey());

        if (diff < 0)
            return findNode(node.getLeftChild(), key);
        else if (diff > 0)
            return findNode(node.getRightChild(), key);
        else
            return node;
    }

    /** @return the node with the minimum key under the subtree of {@code node}. */
    protected N findMinNode(N node) {
        return node.hasLeftChild() ? findMinNode(node.getLeftChild()) : node;
    }

    /** @return the node with the maximum key under the subtree of {@code node}. */
    protected N findMaxNode(N node) {
        return node.hasRightChild() ? findMaxNode(node.getRightChild()) : node;
    }

    public N add(T key) {
        N node = null;

        if (root == null)
            setRoot(node = createNode(key));
        else
            node = addAux(root, key);

        return node;
    }

    private N addAux(N node, T key) {
        int diff = key.compareTo(node.getKey());
        N child, newNode = null;

        if (diff < 0) {
            if ((child = node.getLeftChild()) == null)
                node.setLeftChild(newNode = createNode(key));
            else
                newNode = addAux(child, key);
        }
        else if (diff > 0) {
            if ((child = node.getRightChild()) == null)
                node.setRightChild(newNode = createNode(key));
            else
                newNode = addAux(child, key);
        }

        return newNode;
    }

    /** @return the removed node with the specific key if exists; otherwise, {@code null}. */
    public N remove(T key) {
        N node = findNode(root, key);

        if (node != null) {
            if (node.hasBothChildren()) removeHibbard(node);
            else removeSelf(node);
        }

        return node;
    }

    /** @return the lowest node whose subtree has been updatede. */
    protected N removeSelf(N node) {
        N parent = node.getParent();
        N child = null;

        if (node.hasLeftChild()) child = node.getLeftChild();
        else if (node.hasRightChild()) child = node.getRightChild();
        replaceChild(node, child);

        return parent;
    }

    private void replaceChild(N oldNode, N newNode) {
        if (isRoot(oldNode))
            setRoot(newNode);
        else
            oldNode.getParent().replaceChild(oldNode, newNode);
    }

    /** @return the lowest node whose subtree has been updatede. */
    protected N removeHibbard(N node) {
        N successor = node.getRightChild();
        N min = findMinNode(successor);
        N parent = min.getParent();

        min.setLeftChild(node.getLeftChild());

        if (min != successor) {
            parent.setLeftChild(min.getRightChild());
            min.setRightChild(successor);
        }

        replaceChild(node, min);
        return parent;
    }
}
