package com.prakharmittal.list;

import java.util.Iterator;

public class ArrayList<T> implements Iterable<T> {

    private T[] backingArray;
    private int size;

    public static final int INITIAL_CAPACITY = 10;

    public ArrayList() {
        this(INITIAL_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        backingArray = (T[]) new Object[Math.max(1, initialCapacity)];
    }

    private void resizeArray() {
        T[] originalArray = backingArray;
        backingArray = (T[]) new Object[originalArray.length * 2];
        for (int i = 0; i < originalArray.length; i++) {
            backingArray[i] = originalArray[i];
        }
    }

    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index (" + index
                    + ") is not in the range 0 to " + size + ".");
        }
        if (size == backingArray.length) {
            resizeArray();
        }
        for (int i = size; i > index; i--) {
            backingArray[i] = backingArray[i - 1];
        }
        backingArray[index] = data;
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
        T data = backingArray[index];
        for (int i = index; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[--size] = null;
        return data;
    }

    public T removeFromFront() {
        return removeAtIndex(0);
    }

    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index (" + index
                    + ") is not in the range 0 to " + size + ".");
        }
        return backingArray[index];
    }

    public int lastIndexOf(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be searched must not be null.");
        }
        for (int i = size - 1; i >= 0; i--) {
            if (backingArray[i].equals(data)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        backingArray = (T[]) new Object[backingArray.length];
        size = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ArrayList<?>) {
            ArrayList<?> other = (ArrayList<?>) o;
            if (size == other.size) {
                for (int i = 0; i < size; i++) {
                    if (!backingArray[i].equals(other.backingArray[i])) {
                        return false;
                    }
                }
                return true;
            }
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

    Object[] getBackingArray() {
        return backingArray;
    }

    @Override
    public Iterator<T> iterator() {
        return new ALIterator();
    }

    private class ALIterator implements Iterator<T> {

        private int i;

        private ALIterator() {
            i = 0;
        }

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return backingArray[i++];
            }
            return null;
        }
    }
}
