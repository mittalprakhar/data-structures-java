package com.prakharmittal.linear;

import java.util.NoSuchElementException;

public class StackArray<T> {

    private T[] backingArray;
    private int size;

    public static final int INITIAL_CAPACITY = 10;

    public StackArray() {
        this(INITIAL_CAPACITY);
    }

    public StackArray(int initialCapacity) {
        backingArray = (T[]) new Object[Math.max(1, initialCapacity)];
    }

    private void resizeArray() {
        T[] originalArray = backingArray;
        backingArray = (T[]) new Object[originalArray.length * 2];
        for (int i = 0; i < originalArray.length; i++) {
            backingArray[i] = originalArray[i];
        }
    }

    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be pushed cannot be null.");
        }
        if (size == backingArray.length) {
            resizeArray();
        }
        backingArray[size++] = data;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("The stack is empty.");
        }
        T out = backingArray[size - 1];
        backingArray[--size] = null;
        return out;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return backingArray[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        backingArray = (T[]) new Object[backingArray.length];
        size = 0;
    }

    public int size() {
        return size;
    }

    Object[] getBackingArray() {
        return backingArray;
    }
}
