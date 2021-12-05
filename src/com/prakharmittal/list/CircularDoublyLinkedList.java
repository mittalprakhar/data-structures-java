package com.prakharmittal.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularDoublyLinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private int size;

    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index entered (" + index
                    + ") is not in the range 0 to " + size + ".");
        }
        if (isEmpty()) {
            head = new Node<>(data, null, null);
            head.setNext(head);
            head.setPrevious(head);
        } else if (index == 0) {
            head.setPrevious(new Node<>(data, head.getPrevious(), head));
            head = head.getPrevious();
            head.getPrevious().setNext(head);
        } else if (index == size) {
            head.setPrevious(new Node<>(data, head.getPrevious(), head));
            head.getPrevious().getPrevious().setNext(head.getPrevious());
        } else if (index < size / 2) {
            Node<T> current = head;
            for (int i = 1; i < index; i++) {
                current = current.getNext();
            }
            current.setNext(new Node<>(data, current, current.getNext()));
            current.getNext().getNext().setPrevious(current.getNext());
        } else {
            Node<T> current = head.getPrevious();
            for (int i = 1; i < size - index; i++) {
                current = current.getPrevious();
            }
            current.setPrevious(new Node<>(data, current.getPrevious(), current));
            current.getPrevious().getPrevious().setNext(current.getPrevious());
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
        if (size == 1) {
            data = head.getData();
            head = null;
        } else if (index == 0) {
            data = head.getData();
            head = head.getNext();
            head.setPrevious(head.getPrevious().getPrevious());
            head.getPrevious().setNext(head);
        } else if (index == size - 1) {
            data = head.getPrevious().getData();
            head.setPrevious(head.getPrevious().getPrevious());
            head.getPrevious().setNext(head);
        } else if (index < size / 2) {
            Node<T> current = head;
            for (int i = 1; i < index; i++) {
                current = current.getNext();
            }
            data = current.getNext().getData();
            current.setNext(current.getNext().getNext());
            current.getNext().setPrevious(current);
        } else {
            Node<T> current = head.getPrevious();
            for (int i = 1; i < size - 1 - index; i++) {
                current = current.getPrevious();
            }
            data = current.getPrevious().getData();
            current.setPrevious(current.getPrevious().getPrevious());
            current.getPrevious().setNext(current);
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
        if (head.getPrevious().getData().equals(data)) {
            return removeFromBack();
        }
        if (size >= 2) {
            for (Node<T> current = head.getPrevious(); current.getPrevious() != head;
                 current = current.getPrevious()) {
                T output = current.getPrevious().getData();
                if (output.equals(data)) {
                    current.setPrevious(current.getPrevious().getPrevious());
                    current.getPrevious().setNext(current);
                    size--;
                    return output;
                }
            }
            if (head.getData().equals(data)) {
                return removeFromFront();
            }
        }
        throw new NoSuchElementException("No element with the data '" + data + "' exists in the list.");
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(isEmpty()
                    ? "The list is empty thus the index entered (" + index + ") is out of bounds."
                    : "The index entered (" + index + ") is not in the range 0 to " + (size - 1) + ".");
        }
        if (index < size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getData();
        } else {
            Node<T> current = head.getPrevious();
            for (int i = 0; i < size - 1 - index; i++) {
                current = current.getPrevious();
            }
            return current.getData();
        }
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
        } else if (o instanceof CircularDoublyLinkedList<?>) {
            CircularDoublyLinkedList<?> other = (CircularDoublyLinkedList<?>) o;
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
        return new CDLIterator();
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

    private class CDLIterator implements Iterator<T> {

        private Node<T> current;
        private boolean start;

        private CDLIterator() {
            current = head;
            start = true;
        }

        @Override
        public boolean hasNext() {
            return (!isEmpty() && (start || current != head));
        }

        @Override
        public T next() {
            if (hasNext()) {
                T data = current.getData();
                current = current.getNext();
                start = false;
                return data;
            }
            return null;
        }
    }
}
