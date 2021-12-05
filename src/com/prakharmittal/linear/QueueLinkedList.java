package com.prakharmittal.linear;

import java.util.NoSuchElementException;

public class QueueLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be enqueued cannot be null.");
        }
        if (isEmpty()) {
            head = tail = new Node<T>(data);
        } else {
            tail.setNext(new Node<T>(data));
            tail = tail.getNext();
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }
        T out = head.getData();
        head = head.getNext();
        if (--size == 0) {
            tail = null;
        }
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
        private Node<T> next;

        private Node(T data) {
            this.data = data;
            this.next = null;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
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
