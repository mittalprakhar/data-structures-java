package com.prakharmittal.algo;

import com.prakharmittal.list.SinglyLinkedList;

import java.util.Comparator;
import java.util.Random;

public class Sorting {

    private static <T> void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array to be sorted must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int maxIndex = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }
            swap(arr, i, maxIndex);
        }
    }

    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array to be sorted must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        }
        boolean swapped = true;
        int end = arr.length - 1;
        while (swapped) {
            swapped = false;
            int swapIndex = -1;
            for (int i = 0; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    swapped = true;
                    swapIndex = i;
                }
            }
            end = swapIndex;
        }
    }

    public static <T> void cocktailShakerSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array to be sorted must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        }
        boolean swapped = true;
        int start = 0;
        int end = arr.length - 1;
        while (swapped) {
            swapped = false;
            int swapIndex = -1;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    swapped = true;
                    swapIndex = i;
                }
            }
            end = swapIndex;
            if (swapped) {
                swapped = false;
                swapIndex = -1;
                for (int i = end; i > start; i--) {
                    if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                        swap(arr, i, i - 1);
                        swapped = true;
                        swapIndex = i;
                    }
                }
                start = swapIndex;
            }
        }
    }

    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array to be sorted must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            T current = arr[i];
            while (j >= 0 && comparator.compare(current, arr[j]) < 0) {
                arr[j + 1] = arr[j--];
            }
            arr[j + 1] = current;
        }
    }

    public static <T> void heapSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array to be sorted must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        }
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            downheap(arr, comparator, i, arr.length);
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, 0, i);
            downheap(arr, comparator, 0, i);
        }
    }

    private static <T> void downheap(T[] arr, Comparator<T> comparator, int i, int end) {
        while (2 * i + 1 < end) {
            int j = 2 * i + 1;
            if (j + 1 < end && comparator.compare(arr[j + 1], arr[j]) > 0) {
                j++;
            }
            if (comparator.compare(arr[i], arr[j]) > 0) {
                return;
            }
            swap(arr, i, j);
            i = j;
        }
    }

    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array to be sorted must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        }
        mergeSortHelper(arr, comparator);
    }

    private static <T> void mergeSortHelper(T[] arr, Comparator<T> comparator) {
        if (arr.length > 1) {
            T[] left = (T[]) new Object[arr.length / 2];
            T[] right = (T[]) new Object[arr.length - left.length];
            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int i = 0; i < right.length; i++) {
                right[i] = arr[left.length + i];
            }
            mergeSortHelper(left, comparator);
            mergeSortHelper(right, comparator);
            merge(arr, left, right, comparator);
        }
    }

    private static <T> void merge(T[] arr, T[] left, T[] right, Comparator<T> comparator) {
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("The array to be sorted must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        } else if (rand == null) {
            throw new IllegalArgumentException("The random object must not be null.");
        }
        quickSortHelper(arr, comparator, rand, 0, arr.length - 1);
    }

    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator, Random rand,
                                            int start, int end) {
        if (start >= end) {
            return;
        }
        int j = partition(arr, comparator, rand, start, end);
        quickSortHelper(arr, comparator, rand, start, j - 1);
        quickSortHelper(arr, comparator, rand, j + 1, end);
    }

    public static <T> T quickSelect(int k, T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("The array to be searched must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        } else if (rand == null) {
            throw new IllegalArgumentException("The random object must not be null.");
        } else if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException(arr.length == 0
                    ? "The array to be searched must not have zero length."
                    : "The value of k (" + k + ") is not in the range 1 to " + arr.length + ".");
        }
        return quickSelectHelper(k, arr, comparator, rand, 0, arr.length - 1);
    }

    private static <T> T quickSelectHelper(int k, T[] arr, Comparator<T> comparator,
                                              Random rand, int start, int end) {
        int j = partition(arr, comparator, rand, start, end);
        if (j == k - 1) {
            return arr[j];
        } else if (j > k - 1) {
            return quickSelectHelper(k, arr, comparator, rand, start, j - 1);
        } else {
            return quickSelectHelper(k, arr, comparator, rand, j + 1, end);
        }
    }

    private static <T> int partition(T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivotValue = arr[pivotIndex];
        swap(arr, start, pivotIndex);
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotValue) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotValue) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, start, j);
        return j;
    }

    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array to be sorted must not be null.");
        }
        if (arr.length <= 1) {
            return;
        }
        SinglyLinkedList<Integer>[] buckets = new SinglyLinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new SinglyLinkedList<>();
        }
        int max = arr[0];
        int k = 1;
        for (int num: arr) {
            if (num <= -1000000000 || num >= 1000000000) {
                k = 10;
                break;
            } else if (Math.abs(num) > max) {
                max = Math.abs(num);
            }
        }
        if (k != 10) {
            while (max > 9) {
                max /= 10;
                k++;
            }
        }
        int divisor = 1;
        for (int i = 0; i < k; i++) {
            for (int num: arr) {
                buckets[num / divisor % 10 + 9].addToBack(num);
            }
            int current = 0;
            for (SinglyLinkedList<Integer> bucket: buckets) {
                while (!bucket.isEmpty()) {
                    arr[current] = bucket.removeFromFront();
                    current++;
                }
            }
            divisor *= 10;
        }
    }
}
