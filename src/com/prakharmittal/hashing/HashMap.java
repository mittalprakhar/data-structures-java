package com.prakharmittal.hashing;

import com.prakharmittal.list.ArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMap<K, V> implements Iterable<HashMap.Entry<K, V>> {

    private Entry<K, V>[] table;
    private int size;

    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;

    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    public HashMap(int initialCapacity) {
        table = new Entry[Math.max(3, initialCapacity)];
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be added must not be null.");
        } else if (value == null) {
            throw new IllegalArgumentException("The value to be added must not be null.");
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
                V output = table[index].getValue();
                table[index].setValue(value);
                return output;
            }
            p++;
            index = (index + 1) % table.length;
        }
        table[firstRemoved < 0 ? index : firstRemoved] = new Entry<>(key, value);
        size++;
        return null;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be removed must not be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        int p = 0;
        while (table[index] != null && p <= table.length) {
            if (!table[index].getRemoved() && table[index].getKey().equals(key)) {
                table[index].setRemoved(true);
                size--;
                return table[index].getValue();
            }
            p++;
            index = (index + 1) % table.length;
        }
        throw new NoSuchElementException("The key '" + key + "' is not found in the hashmap.");
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be searched must not be null.");
        }
        V output = getHelper(key);
        if (output == null) {
            throw new NoSuchElementException("The key '" + key + "' is not found in the hashmap.");
        }
        return output;
    }

    private V getHelper(K key) {
        int index = Math.abs(key.hashCode() % table.length);
        int p = 0;
        while (table[index] != null && p <= table.length) {
            if (!table[index].getRemoved() && table[index].getKey().equals(key)) {
                return table[index].getValue();
            }
            p++;
            index = (index + 1) % table.length;
        }
        return null;
    }

    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be searched must not be null.");
        }
        return getHelper(key) != null;
    }

    public HashSet<Entry<K, V>> entries() {
        HashSet<Entry<K, V>> output = new HashSet<>(size);
        for (Entry<K, V> entry: this) {
            output.add(entry);
        }
        return output;
    }

    public HashSet<K> keys() {
        HashSet<K> output = new HashSet<>(size);
        for (Entry<K, V> entry: this) {
            output.add(entry.getKey());
        }
        return output;
    }

    public ArrayList<V> values() {
        ArrayList<V> output = new ArrayList<>(size);
        for (Entry<K, V> entry: this) {
            output.addToBack(entry.getValue());
        }
        return output;
    }

    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Length should be greater than " + size + ".");
        }
        Entry<K, V>[] newTable = new Entry[length];
        for (Entry<K, V> entry: this) {
            int index = Math.abs(entry.getKey().hashCode() % length);
            while (newTable[index] != null) {
                index = (index + 1) % length;
            }
            newTable[index] = new Entry<>(entry.getKey(), entry.getValue());
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
        } else if (o instanceof HashMap<?, ?>) {
            HashMap<Object, Object> other = (HashMap<Object, Object>) o;
            if (size == other.size) {
                for (Entry<K, V> entry: this) {
                    try {
                        if (!other.get(entry.getKey()).equals(entry.getValue())) {
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
        for (Entry<K, V> entry: this) {
            hashCode ^= entry.getKey().hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String prefix = "";
        builder.append("[");
        for (Entry<K, V> entry: this) {
            builder.append(prefix);
            prefix = ", ";
            builder.append(entry.toString());
        }
        builder.append("]");
        return builder.toString();
    }

    public int size() {
        return size;
    }

    Entry<K, V>[] getTable() {
        return table;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    static class Entry<K, V> {

        private final K key;
        private V value;
        private boolean removed;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public boolean getRemoved() {
            return removed;
        }

        private void setValue(V value) {
            this.value = value;
        }

        void setRemoved(boolean removed) {
            this.removed = removed;
        }

        @Override
        public String toString() {
            return String.format("%s=%s", key.toString(), value.toString());
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Entry<?, ?>) {
                Entry<?, ?> e = (Entry<?, ?>) o;
                return e.getKey().equals(key) && e.getValue().equals(value);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return key.hashCode() ^ value.hashCode();
        }
    }

    private class HashMapIterator implements Iterator<Entry<K, V>> {

        private int i;
        private int j;

        private HashMapIterator() {
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
        public Entry<K, V> next() {
            if (hasNext()) {
                j++;
                return table[i++];
            }
            return null;
        }
    }
}
