package com.prakharmittal.linear;

import java.util.NoSuchElementException;

public class QueueArray<T> {

    private T[] backingArray;
    private int front;
    private int size;

    public static final int INITIAL_CAPACITY = 10;

    public QueueArray() {
        this(INITIAL_CAPACITY);
    }

    public QueueArray(int initialCapacity) {
        backingArray = (T[]) new Object[Math.max(1, initialCapacity)];
    }

    private void resizeArray() {
        T[] originalArray = backingArray;
        backingArray = (T[]) new Object[originalArray.length * 2];
        for (int i = 0; i < originalArray.length; i++) {
            backingArray[i] = originalArray[(front + i) % originalArray.length];
        }
        front = 0;
    }

    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be enqueued cannot be null.");
        }
        if (size == backingArray.length) {
            resizeArray();
        }
        backingArray[(front + size) % backingArray.length] = data;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }
        T out = backingArray[front];
        backingArray[front] = null;
        front = (front + 1) % backingArray.length;
        size--;
        return out;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return backingArray[front];
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
