package com.prakharmittal.linear;

import java.util.NoSuchElementException;

public class DequeArray<T> {

    private T[] backingArray;
    private int front;
    private int size;

    public static final int INITIAL_CAPACITY = 10;

    public DequeArray() {
        this(INITIAL_CAPACITY);
    }

    public DequeArray(int initialCapacity) {
        backingArray = (T[]) new Object[Math.max(1, initialCapacity)];
    }

    private void resizeArray(int shift) {
        T[] originalArray = backingArray;
        backingArray = (T[]) new Object[originalArray.length * 2];
        for (int i = 0; i < originalArray.length; i++) {
            backingArray[i + shift] = originalArray[(front + i) % originalArray.length];
        }
        front = 0;
    }

    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added cannot be null.");
        }
        if (size == backingArray.length) {
            resizeArray(1);
        } else if (--front == -1) {
            front = backingArray.length - 1;
        }
        backingArray[front] = data;
        size++;
    }

    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added cannot be null.");
        }
        if (size == backingArray.length) {
            resizeArray(0);
        }
        backingArray[(size + front) % backingArray.length] = data;
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }
        T out = backingArray[front];
        backingArray[front] = null;
        front = (front + 1) % backingArray.length;
        size--;
        return out;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }
        int back = (front + size - 1) % backingArray.length;
        T out = backingArray[back];
        backingArray[back] = null;
        size--;
        return out;
    }

    public T getFirst() {
        if (isEmpty()) {
            return null;
        }
        return backingArray[front];
    }

    public T getLast() {
        if (isEmpty()) {
            return null;
        }
        return backingArray[(front + size - 1) % backingArray.length];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        backingArray = (T[]) new Object[backingArray.length];
        front = size = 0;
    }

    public int size() {
        return size;
    }

    Object[] getBackingArray() {
        return backingArray;
    }

    public int getFront() {
        return front;
    }
}
