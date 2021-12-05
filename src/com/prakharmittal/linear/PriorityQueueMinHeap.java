package com.prakharmittal.linear;

import com.prakharmittal.list.ArrayList;

import java.util.NoSuchElementException;

public class PriorityQueueMinHeap<T extends Comparable<? super T>> {

    private T[] backingArray;
    private int size;

    public static final int INITIAL_CAPACITY = 9;

    public PriorityQueueMinHeap() {
        this(INITIAL_CAPACITY);
    }

    public PriorityQueueMinHeap(int initialCapacity) {
        backingArray = (T[]) new Comparable[Math.max(2, initialCapacity)];
    }

    public PriorityQueueMinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        }
        size = data.size();
        backingArray = (T[]) new Comparable[size * 2];
        for (int i = 0; i < size; i++) {
            T element = data.get(i);
            if (element == null) {
                throw new IllegalArgumentException("The data to be added must not be null.");
            }
            backingArray[i + 1] = element;
        }
        for (int i = size / 2; i > 0; i--) {
            downheap(i);
        }
    }

    private void resizeArray() {
        T[] originalArray = backingArray;
        backingArray = (T[]) new Comparable[originalArray.length * 2];
        for (int i = 0; i < originalArray.length; i++) {
            backingArray[i] = originalArray[i];
        }
    }

    private void swap(int i, int j) {
        T tmp = backingArray[i];
        backingArray[i] = backingArray[j];
        backingArray[j] = tmp;
    }

    private void downheap(int i) {
        while (2 * i <= size) {
            int j = 2 * i;
            if (j < size && backingArray[j + 1].compareTo(backingArray[j]) < 0) {
                j++;
            }
            if (backingArray[i].compareTo(backingArray[j]) < 0) {
                return;
            }
            swap(i, j);
            i = j;
        }
    }

    private void upheap(int i) {
        while (i > 1 && backingArray[i].compareTo(backingArray[i / 2]) < 0) {
            swap(i, i / 2);
            i = i / 2;
        }
    }

    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data to be added must not be null.");
        }
        if (++size == backingArray.length) {
            resizeArray();
        }
        backingArray[size] = data;
        upheap(size);
    }

    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("The heap is empty thus no element exists to be removed.");
        }
        T output = backingArray[1];
        backingArray[1] = null;
        swap(1, size--);
        downheap(1);
        return output;
    }

    public T min() {
        if (isEmpty()) {
            return null;
        }
        return backingArray[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        backingArray = (T[]) new Comparable[backingArray.length];
        size = 0;
    }

    public int size() {
        return size;
    }

    Object[] getBackingArray() {
        return backingArray;
    }
}
