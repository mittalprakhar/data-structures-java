package com.prakharmittal.tree;

import com.prakharmittal.list.ArrayList;

import java.util.NoSuchElementException;

public class BTree<T extends Comparable<? super T>> {

    private Node<T> root;
    private int size;
    private int minChildren;
    private int maxChildren;

    public static final int MIN_CHILDREN = 2;
    public static final int MAX_CHILDREN = 4;

    public BTree() {
        this(MIN_CHILDREN, MAX_CHILDREN);
    }

    public BTree(int minChildren, int maxChildren) {
        int valid = minChildren * 2;
        if (maxChildren != valid - 1 && maxChildren != valid) {
            throw new IllegalArgumentException("The maximum children must be "
                    + (valid - 1) + " or " + valid + ".");
        }
        this.minChildren = minChildren;
        this.maxChildren = maxChildren;
    }

    public BTree(ArrayList<T> data) {
        this(data, MIN_CHILDREN, MAX_CHILDREN);
    }

    public BTree(ArrayList<T> data, int minChildren, int maxChildren) {
        this(minChildren, maxChildren);
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("The data to be added must not be null.");
            }
            add(element);
        }
    }

    public void add(T element) {
        if (element == null) {
            throw new IllegalArgumentException("The element to be added must not be null.");
        }
        if (isEmpty()) {
            root = new Node<>(null, maxChildren);
            root.addElement(element);
        } else {
            addHelper(element, root);
        }
        size++;
    }

    private void addHelper(T element, Node<T> node) {
        if (node.isLeaf()) {
            node.addElement(element);
            if (node.getDataSize() >= maxChildren) {
                split(node);
            }
            return;
        }
        if (element.compareTo(node.getElement(0)) <= 0) {
            addHelper(element, node.getChild(0));
        }
        for (int i = 1; i < node.getDataSize(); i++) {
            if (element.compareTo(node.getElement(i - 1)) > 0
                    && element.compareTo(node.getElement(i)) <= 0) {
                addHelper(element, node.getChild(i));
            }
        }
        addHelper(element, node.getChild(node.getChildrenSize() - 1));
    }

    private void split(Node<T> node) {
        int dataSize = node.getDataSize();
        int childrenSize = node.getChildrenSize();
        int middle = dataSize / 2;

        T[] tempData = node.getData();
        Node<T> left = new Node<>(node.getParent(), maxChildren);
        for (int i = middle; i < dataSize; i++) {
            tempData[i] = null;
        }
        left.updateData(tempData, dataSize / 2);

        tempData = node.getData();
        Node<T> right = new Node<>(node.getParent(), maxChildren);
        for (int i = 0; i < middle; i++) {
            tempData[i] = null;
        }
        right.updateData(tempData, (dataSize - 1) / 2);

        Node<T>[] tempChildren = node.getChildren();
        if (!node.isLeaf()) {
            for (int i = middle + 1; i < childrenSize; i++) {
                tempChildren[i] = null;
            }
            left.updateChildren(tempChildren, (childrenSize + 1) / 2);

            tempChildren = node.getChildren();
            for (int i = 0; i <= middle; i++) {
                tempChildren[i] = null;
            }
            right.updateChildren(tempChildren, childrenSize / 2);
        }
        
        if (node == root) {
            root = new Node<>(null, maxChildren);
            node.setParent(root);
        }

        Node<T> parent = node.getParent();
        int index = parent.addElement(node.getElement(middle));

        tempChildren = parent.getChildren();
        for (int i = parent.getChildrenSize() - 1; i > index; i--) {
            tempChildren[i + 1] = tempChildren[i];
        }
        tempChildren[index] = left;
        tempChildren[index + 1] = right;
        parent.updateChildren(tempChildren, parent.getChildrenSize() + 1);

        if (parent.getDataSize() >= maxChildren) {
            split(parent);
        }
    }

    // TODO
    public T remove(T element) {
        return null;
    }

    public Node<T> get(T element) {
        if (element == null) {
            throw new IllegalArgumentException("The element to be searched must not be null.");
        }
        Node<T> output = getHelper(element, root);
        if (output == null) {
            throw new NoSuchElementException("No node with the element '" + element + "' exists in the B-tree.");
        }
        return output;
    }

    private Node<T> getHelper(T element, Node<T> node) {
        if (node == null) {
            return null;
        }
        if (element.compareTo(node.getElement(0)) < 0) {
            return getHelper(element, node.getChild(0));
        }
        for (int i = 0; i < node.getDataSize(); i++) {
            if (element.compareTo(node.getElement(i)) == 0) {
                return node;
            }
            if (i + 1 < node.getDataSize()
                    && element.compareTo(node.getElement(i)) > 0
                    && element.compareTo(node.getElement(i + 1)) < 0) {
                return getHelper(element, node.getChild(i + 1));
            }
        }
        return getHelper(element, node.getChild(node.getChildrenSize() - 1));
    }

    public boolean contains(T element) {
        if (element == null) {
            throw new IllegalArgumentException("The element to be searched must not be null.");
        }
        return getHelper(element, root) != null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    // TODO
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof BTree<?>) {
            BTree<?> other = (BTree<?>) o;
        }
        return false;
    }

    // TODO
    @Override
    public int hashCode() {
        int hashCode = 0;
        return hashCode;
    }

    static class Node<T extends Comparable<? super T>> {

        private Node<T> parent;
        private T[] data;
        private int dataSize;
        private Node<T>[] children;
        private int childrenSize;

        Node(Node<T> parent, int maxChildren) {
            this.parent = parent;
            this.data = (T[]) new Comparable[maxChildren];
            this.children = new Node[maxChildren + 1];
        }

        private T getElement(int index) {
            return data[index];
        }

        private int addElement(T element) {
            int i;
            for (i = dataSize - 1; i >= 0 && data[i].compareTo(element) > 0; i--) {
                data[i + 1] = data[i];
            }
            data[i + 1] = element;
            dataSize++;
            return i + 1;
        }

        private void updateData(T[] data, int dataSize) {
            this.data = data;
            this.dataSize = dataSize;
        }

        private Node<T> getChild(int index) {
            return children[index];
        }

        private void updateChildren(Node<T>[] children, int childrenSize) {
            this.children = children;
            this.childrenSize = childrenSize;
        }

        private boolean isLeaf() {
            return childrenSize == 0;
        }

        public Node<T> getParent() {
            return parent;
        }

        public T[] getData() {
            return data;
        }

        public int getDataSize() {
            return dataSize;
        }

        public Node<T>[] getChildren() {
            return children;
        }

        public int getChildrenSize() {
            return childrenSize;
        }

        void setParent(Node<T> parent) {
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Node containing: " + java.util.Arrays.toString(data);
        }
    }
}
