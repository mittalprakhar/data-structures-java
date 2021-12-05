package com.prakharmittal.tree;

import com.prakharmittal.linear.QueueLinkedList;
import com.prakharmittal.list.ArrayList;

import java.util.NoSuchElementException;

public class SplayTree<T extends Comparable<? super T>> {

    private Node<T> root;
    private int size;

    public SplayTree() {
    }

    public SplayTree(ArrayList<T> data) {
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

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> newParent = node.getRight();
        node.setRight(newParent.getLeft());
        newParent.setLeft(node);
        return newParent;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> newParent = node.getLeft();
        node.setLeft(newParent.getRight());
        newParent.setRight(node);
        return newParent;
    }

    private Node<T> splay(T data, Node<T> node) {
        if (node != null) {
            int compareResult = data.compareTo(node.getData());
            if (compareResult < 0) {
                if (node.getLeft() == null) {
                    return node;
                }
                compareResult = data.compareTo(node.getLeft().getData());
                if (compareResult < 0) {
                    node.getLeft().setLeft(splay(data, node.getLeft().getLeft()));
                    node = rotateRight(node);
                } else if (compareResult > 0) {
                    node.getLeft().setRight(splay(data, node.getLeft().getRight()));
                    if (node.getLeft().getRight() != null) {
                        node.setLeft(rotateLeft(node.getLeft()));
                    }
                }
                return node.getLeft() == null ? node : rotateRight(node);
            } else if (compareResult > 0) {
                if (node.getRight() == null) {
                    return node;
                }
                compareResult = data.compareTo(node.getRight().getData());
                if (compareResult < 0) {
                    node.getRight().setLeft(splay(data, node.getRight().getLeft()));
                    if (node.getRight().getLeft() != null) {
                        node.setLeft(rotateRight(node.getRight()));
                    }
                } else if (compareResult > 0) {
                    node.getRight().setRight(splay(data, node.getRight().getRight()));
                    node = rotateLeft(node);
                }
                return node.getRight() == null ? node : rotateLeft(node);
            }
        }
        return node;
    }

    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        }
        root = splay(data, root);
        int compareResult = data.compareTo(root.getData());
        if (compareResult < 0) {
            Node<T> newRoot = new Node<>(data);
            newRoot.setLeft(root.getLeft());
            newRoot.setRight(root);
            root.setLeft(null);
            root = newRoot;
        } else if (compareResult > 0) {
            Node<T> newRoot = new Node<>(data);
            newRoot.setRight(root.getRight());
            newRoot.setLeft(root);
            root.setRight(null);
            root = newRoot;
        }
    }

    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be removed must not be null.");
        }
        root = splay(data, root);
        if (data.equals(root.getData())) {
            if (root.getLeft() == null) {
                root = root.getRight();
            } else {
                Node<T> rightSubTree = root.getRight();
                root = root.getLeft();
                splay(data, root);
                root.setRight(rightSubTree);
            }
        }
        throw new NoSuchElementException("No element with the data '" + data + "' exists in the splay tree.");
    }

    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be searched must not be null.");
        }
        T output = getHelper(data);
        if (output == null) {
            throw new NoSuchElementException("No element with the data '" + data + "' exists in the splay tree.");
        }
        return output;
    }

    private T getHelper(T data) {
        root = splay(data, root);
        if (data.equals(root.getData())) {
            return root.getData();
        }
        return null;
    }

    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be searched must not be null.");
        }
        return getHelper(data) != null;
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
            throw new NoSuchElementException("The splay tree is empty thus no min element exists.");
        }
        Node<T> current = root;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getData();
    }

    public T max() {
        if (root == null) {
            throw new NoSuchElementException("The splay tree is empty thus no max element exists.");
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
        } else if (o instanceof SplayTree<?>) {
            SplayTree<?> other = (SplayTree<?>) o;
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
        return node == null
                ? -1
                : Math.max(heightHelper(node.getLeft()), heightHelper(node.getRight())) + 1;
    }

    public static <T extends Comparable<? super T>> boolean isSplayTree(Node<T> root) {
        return isSplayTreeHelper(root, null, null);
    }

    private static <T extends Comparable<? super T>> boolean isSplayTreeHelper(Node<T> node, T min, T max) {
        if (node != null) {
            if (min != null && node.getData().compareTo(min) <= 0
                    || max != null && node.getData().compareTo(max) >= 0) {
                return false;
            }
            return isSplayTreeHelper(node.getLeft(), min, node.getData())
                    && isSplayTreeHelper(node.getRight(), node.getData(), max);
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
