package com.prakharmittal.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private int size;

    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index entered (" + index
                    + ") is not in the range 0 to " + size + ".");
        }
        if (index == 0) {
            head = new Node<T>(data, head);
        } else {
            Node<T> current = head;
            for (int i = 1; i < index; i++) {
                current = current.getNext();
            }
            current.setNext(new Node<T>(data, current.getNext()));
        }
        size++;
    }

    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(isEmpty()
                    ? "The list is empty thus the index entered (" + index + ") is out of bounds."
                    : "The index entered (" + index + ") is not in the range 0 to " + (size - 1) + ".");
        }
        T data;
        if (index == 0) {
            data = head.getData();
            head = head.getNext();
        } else {
            Node<T> current = head;
            for (int i = 1; i < index; i++) {
                current = current.getNext();
            }
            data = current.getNext().getData();
            current.setNext(current.getNext().getNext());
        }
        size--;
        return data;
    }

    public T removeFromFront() {
        return removeAtIndex(0);
    }

    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }
    
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be removed must not be null.");
        } else if (isEmpty()) {
            throw new NoSuchElementException("The list is empty thus no element exists to be removed.");
        }
        Node<T> beforeLastFound = null;
        for (Node<T> current = head; current.getNext() != null; current = current.getNext()) {
            if (current.getNext().getData().equals(data)) {
                beforeLastFound = current;
            }
        }
        if (beforeLastFound != null) {
            T output = beforeLastFound.getNext().getData();
            beforeLastFound.setNext(beforeLastFound.getNext().getNext());
            size--;
            return output;
        } else if (head.getData().equals(data)) {
            return removeFromFront();
        }
        throw new NoSuchElementException("No element with the data '" + data + "' exists in the list.");
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(isEmpty()
                    ? "The list is empty thus the index entered (" + index + ") is out of bounds."
                    : "The index entered (" + index + ") is not in the range 0 to " + (size - 1) + ".");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    public Object[] toArray() {
        T[] array = (T[]) new Object[size];
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    // TODO
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof SinglyLinkedList<?>) {
            SinglyLinkedList<?> other = (SinglyLinkedList<?>) o;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (T data: this) {
            hashCode ^= data.hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String prefix = "";
        builder.append("[");
        for (T data: this) {
            builder.append(prefix);
            prefix = ", ";
            builder.append(data.toString());
        }
        builder.append("]");
        return builder.toString();
    }

    public int size() {
        return size;
    }

    public Node<T> getHead() {
        return head;
    }

    @Override
    public Iterator<T> iterator() {
        return new SLLIterator();
    }

    static class Node<T> {

        private final T data;
        private Node<T> next;

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

        private void setNext(Node<T> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node containing: " + data;
        }
    }

    private class SLLIterator implements Iterator<T> {

        private Node<T> current;

        private SLLIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T data = current.getData();
                current = current.getNext();
                return data;
            }
            return null;
        }
    }
}
