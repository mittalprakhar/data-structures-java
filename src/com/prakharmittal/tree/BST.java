package com.prakharmittal.tree;

import com.prakharmittal.linear.QueueLinkedList;
import com.prakharmittal.list.ArrayList;

import java.util.NoSuchElementException;

public class BST<T extends Comparable<? super T>> {

    private Node<T> root;
    private int size;

    public BST() {
    }

    public BST(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        }
        for (T element: data) {
            add(element);
        }
    }

    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        }
        root = addHelper(data, root);
    }

    private Node<T> addHelper(T data, Node<T> node) {
        if (node == null) {
            size++;
            return new Node<>(data);
        }
        int compareResult = data.compareTo(node.getData());
        if (compareResult < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (compareResult > 0) {
            node.setRight(addHelper(data, node.getRight()));
        }
        return node;
    }

    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be removed must not be null.");
        }
        Node<T> removed = new Node<>(null);
        root = removeHelper(data, root, removed);
        return removed.getData();
    }

    private Node<T> removeHelper(T data, Node<T> node, Node<T> removed) {
        if (node == null) {
            throw new NoSuchElementException("No element with the data '" + data + "' exists in the BST.");
        }
        int compareResult = data.compareTo(node.getData());
        if (compareResult == 0) {
            size--;
            removed.setData(node.getData());
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            Node<T> successor = new Node<>(null);
            node.setRight(successor(node.getRight(), successor));
            node.setData(successor.getData());
        } else if (compareResult < 0) {
            node.setLeft(removeHelper(data, node.getLeft(), removed));
        } else {
            node.setRight(removeHelper(data, node.getRight(), removed));
        }
        return node;
    }

    private Node<T> successor(Node<T> node, Node<T> successor) {
        if (node.getLeft() == null) {
            successor.setData(node.getData());
            return node.getRight();
        }
        node.setLeft(successor(node.getLeft(), successor));
        return node;
    }

    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be searched must not be null.");
        }
        T output = getHelper(data, root);
        if (output == null) {
            throw new NoSuchElementException("No element with the data '" + data + "' exists in the BST.");
        }
        return output;
    }

    private T getHelper(T data, Node<T> node) {
        if (node == null) {
            return null;
        }
        int compareResult = data.compareTo(node.getData());
        if (compareResult == 0) {
            return node.getData();
        } else if (compareResult < 0) {
            return getHelper(data, node.getLeft());
        }
        return getHelper(data, node.getRight());
    }

    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be searched must not be null.");
        }
        return getHelper(data, root) != null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public T min() {
        if (root == null) {
            throw new NoSuchElementException("The BST is empty thus no min element exists.");
        }
        Node<T> current = root;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getData();
    }

    public T max() {
        if (root == null) {
            throw new NoSuchElementException("The BST is empty thus no max element exists.");
        }
        Node<T> current = root;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current.getData();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof BST<?>) {
            BST<?> other = (BST<?>) o;
            return equalsHelper(this.root, other.root);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (T data: this.inorder()) {
            hashCode ^= data.hashCode();
        }
        return hashCode;
    }

    private boolean equalsHelper(Node<?> thisNode, Node<?> otherNode) {
        if (thisNode == null && otherNode == null) {
            return true;
        } else if (thisNode == null || otherNode == null) {
            return false;
        }
        return thisNode.getData().equals(otherNode.getData())
                && equalsHelper(thisNode.getLeft(), otherNode.getLeft())
                && equalsHelper(thisNode.getRight(), otherNode.getRight());
    }

    public ArrayList<T> preorder() {
        ArrayList<T> output = new ArrayList<>();
        preorderHelper(output, root);
        return output;
    }

    private void preorderHelper(ArrayList<T> list, Node<T> node) {
        if (node != null) {
            list.addToBack(node.getData());
            preorderHelper(list, node.getLeft());
            preorderHelper(list, node.getRight());
        }
    }

    public ArrayList<T> inorder() {
        ArrayList<T> output = new ArrayList<>();
        inorderHelper(output, root);
        return output;
    }

    private void inorderHelper(ArrayList<T> list, Node<T> node) {
        if (node != null) {
            inorderHelper(list, node.getLeft());
            list.addToBack(node.getData());
            inorderHelper(list, node.getRight());
        }
    }

    public ArrayList<T> postorder() {
        ArrayList<T> output = new ArrayList<>();
        postorderHelper(output, root);
        return output;
    }

    private void postorderHelper(ArrayList<T> list, Node<T> node) {
        if (node != null) {
            postorderHelper(list, node.getLeft());
            postorderHelper(list, node.getRight());
            list.addToBack(node.getData());
        }
    }

    public ArrayList<T> levelorder() {
        QueueLinkedList<Node<T>> queue = new QueueLinkedList<>();
        ArrayList<T> output = new ArrayList<>();
        if (root != null) {
            queue.enqueue(root);
            while (!queue.isEmpty()) {
                Node<T> current = queue.dequeue();
                output.addToBack(current.getData());
                if (current.getLeft() != null) {
                    queue.enqueue(current.getLeft());
                }
                if (current.getRight() != null) {
                    queue.enqueue(current.getRight());
                }
            }
        }
        return output;
    }

    public void balance() {
        ArrayList<T> sorted = inorder();
        root = balanceHelper(sorted, 0, sorted.size() - 1);
    }

    private Node<T> balanceHelper(ArrayList<T> sorted, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = (left + right) / 2;
        Node<T> node = new Node<>(sorted.get(mid));
        node.setLeft(balanceHelper(sorted, left, mid - 1));
        node.setRight(balanceHelper(sorted, mid + 1, right));
        return node;
    }

    public int size() {
        return size;
    }

    public Node<T> getRoot() {
        return root;
    }

    public int height() {
        return height(root);
    }

    public static <T extends Comparable<? super T>> int height(Node<T> node) {
        return heightHelper(node);
    }

    private static <T extends Comparable<? super T>> int heightHelper(Node<T> node) {
        return node == null ? -1 : Math.max(heightHelper(node.getLeft()), heightHelper(node.getRight())) + 1;
    }

    public static <T extends Comparable<? super T>> boolean isBST(Node<T> root) {
        return isBSTHelper(root, null, null);
    }

    private static <T extends Comparable<? super T>> boolean isBSTHelper(Node<T> node, T min, T max) {
        if (node != null) {
            if (min != null && node.getData().compareTo(min) <= 0
                    || max != null && node.getData().compareTo(max) >= 0) {
                return false;
            }
            return isBSTHelper(node.getLeft(), min, node.getData())
                    && isBSTHelper(node.getRight(), node.getData(), max);
        }
        return true;
    }

    static class Node<T extends Comparable<? super T>> {

        private T data;
        private Node<T> left;
        private Node<T> right;

        Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }

        void setData(T data) {
            this.data = data;
        }

        void setLeft(Node<T> left) {
            this.left = left;
        }

        void setRight(Node<T> right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node containing: " + data;
        }
    }
}
