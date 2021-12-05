package com.prakharmittal.hashing;

import com.prakharmittal.list.ArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashSet<K> implements Iterable<K> {

    private Entry<K>[] table;
    private int size;

    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;

    public HashSet() {
        this(INITIAL_CAPACITY);
    }

    public HashSet(int initialCapacity) {
        table = new Entry[Math.max(3, initialCapacity)];
    }

    public void add(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be added must not be null.");
        }
        if ((size + 1.0) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
        int index = Math.abs(key.hashCode() % table.length);
        int p = 0;
        int firstRemoved = -1;
        while (table[index] != null && p <= table.length) {
            if (table[index].getRemoved() && firstRemoved < 0) {
                firstRemoved = index;
            } else if (!table[index].getRemoved() && table[index].getKey().equals(key)) {
                return;
            }
            p++;
            index = (index + 1) % table.length;
        }
        table[firstRemoved < 0 ? index : firstRemoved] = new Entry<>(key);
        size++;
    }

    public void remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be removed must not be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        int p = 0;
        while (table[index] != null && p <= table.length) {
            if (!table[index].getRemoved() && table[index].getKey().equals(key)) {
                table[index].setRemoved(true);
                size--;
                return;
            }
            p++;
            index = (index + 1) % table.length;
        }
        throw new NoSuchElementException("The key '" + key + "' is not found in the hashmap.");
    }
    
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be searched must not be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        int p = 0;
        while (table[index] != null && p <= table.length) {
            if (!table[index].getRemoved() && table[index].getKey().equals(key)) {
                return true;
            }
            p++;
            index = (index + 1) % table.length;
        }
        return false;
    }

    public ArrayList<K> toArrayList() {
        ArrayList<K> output = new ArrayList<>(size);
        for (K key: this) {
            output.addToBack(key);
        }
        return output;
    }

    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Length should be greater than " + size + ".");
        }
        Entry<K>[] newTable = new Entry[length];
        for (K key: this) {
            int index = Math.abs(key.hashCode() % length);
            while (newTable[index] != null) {
                index = (index + 1) % length;
            }
            newTable[index] = new Entry<>(key);
        }
        table = newTable;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        table = new Entry[table.length];
        size = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof HashSet<?>) {
            HashSet<Object> other = (HashSet<Object>) o;
            if (size == other.size) {
                for (K key: this) {
                    try {
                        if (!other.contains(key)) {
                            return false;
                        }
                    } catch (Exception e) {
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
        for (K key: this) {
            hashCode ^= key.hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String prefix = "";
        builder.append("[");
        for (K key: this) {
            builder.append(prefix);
            prefix = ", ";
            builder.append(key.toString());
        }
        builder.append("]");
        return builder.toString();
    }

    public int size() {
        return size;
    }

    Entry<K>[] getTable() {
        return table;
    }

    @Override
    public Iterator<K> iterator() {
        return new HashSetIterator();
    }

    static class Entry<K> {

        private final K key;
        private boolean removed;

        Entry(K key) {
            this.key = key;
        }

        public K getKey() {
            return key;
        }

        public boolean getRemoved() {
            return removed;
        }

        void setRemoved(boolean removed) {
            this.removed = removed;
        }

        @Override
        public String toString() {
            return key.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Entry) {
                Entry<?> e = (Entry<?>) o;
                return e.getKey().equals(key);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }
    }

    private class HashSetIterator implements Iterator<K> {

        private int i;
        private int j;

        private HashSetIterator() {
            i = 0;
            j = 0;
        }

        @Override
        public boolean hasNext() {
            if (j < size) {
                while (i < table.length) {
                    if (table[i] != null && !table[i].getRemoved()) {
                        return true;
                    }
                    i++;
                }
            }
            return false;
        }

        @Override
        public K next() {
            if (hasNext()) {
                j++;
                return table[i++].getKey();
            }
            return null;
        }
    }
}
