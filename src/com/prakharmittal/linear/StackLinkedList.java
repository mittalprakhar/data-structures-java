package com.prakharmittal.linear;

import java.util.NoSuchElementException;

public class StackLinkedList<T> {

    private Node<T> head;
    private int size;

    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be pushed cannot be null.");
        }
        head = new Node<T>(data, head);
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("The stack is empty.");
        }
        T out = head.getData();
        head = head.getNext();
        size--;
        return out;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return head.getData();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public Node<T> getHead() {
        return head;
    }

    static class Node<T> {

        private final T data;
        private final Node<T> next;

        private Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

        @Override
        public String toString() {
            return "Node containing: " + data;
        }
    }
}
