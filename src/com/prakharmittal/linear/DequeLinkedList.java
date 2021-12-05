package com.prakharmittal.linear;

import java.util.NoSuchElementException;

public class DequeLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        }
        if (isEmpty()) {
            head = tail = new Node<>(data, null, null);
        } else {
            head.setPrevious(new Node<>(data, null, head));
            head = head.getPrevious();
        }
        size++;
    }

    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        }
        if (isEmpty()) {
            head = tail = new Node<>(data, null, null);
        } else {
            tail.setNext(new Node<>(data, tail, null));
            tail = tail.getNext();
        }
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }
        T data = head.getData();
        if (--size == 0) {
            head = tail = null;
        } else {
            head = head.getNext();
            head.setPrevious(null);
        }
        return data;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }
        T data = tail.getData();
        if (--size == 0) {
            head = tail = null;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        return data;
    }

    public T getFirst() {
        if (isEmpty()) {
            return null;
        }
        return head.getData();
    }

    public T getLast() {
        if (isEmpty()) {
            return null;
        }
        return tail.getData();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    static class Node<T> {

        private final T data;
        private Node<T> previous;
        private Node<T> next;

        private Node(T data, Node<T> previous, Node<T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public Node<T> getNext() {
            return next;
        }

        private void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        private void setNext(Node<T> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node containing: " + data;
        }
    }
}
