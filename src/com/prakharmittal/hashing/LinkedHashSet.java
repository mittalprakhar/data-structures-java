package com.prakharmittal.hashing;

import com.prakharmittal.list.ArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedHashSet<K>  implements Iterable<K> {

    private Entry<K>[] table;
    private int size;

    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;

    public LinkedHashSet() {
        this(INITIAL_CAPACITY);
    }

    public LinkedHashSet(int initialCapacity) {
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
        Entry<K> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return;
            }
            current = current.getNext();
        }
        table[index] = new Entry<>(key, table[index]);
        size++;
    }

    public void remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be removed must not be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        Entry<K> current = table[index];
        Entry<K> previous = null;
        while (current != null) {
            if (current.getKey().equals(key)) {
                if (previous == null) {
                    table[index] = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                return;
            }
            previous = current;
            current = current.getNext();
        }
        throw new NoSuchElementException("The key '" + key + "' is not found in the hashmap.");
    }

    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be searched must not be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        Entry<K> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public ArrayList<K> toArrayList() {
        ArrayList<K> output = new ArrayList<>(size);
        for (Entry<K> entry: table) {
            if (entry != null) {
                Entry<K> current = entry;
                while (current != null) {
                    output.addToBack(current.getKey());
                    current = current.getNext();
                }
            }
            if (output.size() == size) {
                break;
            }
        }
        return output;
    }

    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Length should be greater than " + size + ".");
        }
        Entry<K>[] newTable = new Entry[length];
        int count = 0;
        for (Entry<K> entry: table) {
            if (entry != null) {
                Entry<K> current = entry;
                while (current != null) {
                    int index = Math.abs(current.getKey().hashCode() % newTable.length);
                    newTable[index] = new Entry<>(current.getKey(), newTable[index]);
                    count++;
                    current = current.getNext();
                }
            }
            if (count == size) {
                break;
            }
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
        } else if (o instanceof LinkedHashSet<?>) {
            LinkedHashSet<Object> other = (LinkedHashSet<Object>) o;
            if (size == other.size) {
                int count = 0;
                for (Entry<K> entry: table) {
                    if (entry != null) {
                        Entry<K> current = entry;
                        while (current != null) {
                            try {
                                if (!other.contains(current.getKey())) {
                                    return false;
                                }
                            } catch (Exception e) {
                                return false;
                            }
                            count++;
                            current = current.getNext();
                        }
                    }
                    if (count == size) {
                        break;
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
        int count = 0;
        for (Entry<K> entry: table) {
            if (entry != null) {
                Entry<K> current = entry;
                while (current != null) {
                    hashCode ^= current.getKey().hashCode();
                    count++;
                    current = current.getNext();
                }
            }
            if (count == size) {
                break;
            }
        }
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String prefix = "";
        builder.append("[");
        int count = 0;
        for (Entry<K> entry: table) {
            if (entry != null) {
                Entry<K> current = entry;
                while (current != null) {
                    builder.append(prefix);
                    prefix = ", ";
                    builder.append(current.getKey().toString());
                    count++;
                    current = current.getNext();
                }
            }
            if (count == size) {
                break;
            }
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
        return toArrayList().iterator();
    }

    static class Entry<K> {

        private final K key;
        private Entry<K> next;

        Entry(K key) {
            this(key, null);
        }

        private Entry(K key, Entry<K> next) {
            this.key = key;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public Entry<K> getNext() {
            return next;
        }

        private void setNext(Entry<K> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return key.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Entry<?>) {
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
}
