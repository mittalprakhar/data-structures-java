package com.prakharmittal.hashing;

import com.prakharmittal.list.ArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedHashMap<K, V>  implements Iterable<LinkedHashMap.Entry<K, V>> {

    private Entry<K, V>[] table;
    private int size;

    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;

    public LinkedHashMap() {
        this(INITIAL_CAPACITY);
    }

    public LinkedHashMap(int initialCapacity) {
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
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                V output = current.getValue();
                current.setValue(value);
                return output;
            }
            current = current.getNext();
        }
        table[index] = new Entry<>(key, value, table[index]);
        size++;
        return null;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be removed must not be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;
        while (current != null) {
            if (current.getKey().equals(key)) {
                V output = current.getValue();
                if (previous == null) {
                    table[index] = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                return output;
            }
            previous = current;
            current = current.getNext();
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
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be searched must not be null.");
        }
        return getHelper(key) != null;
    }

    public LinkedHashSet<Entry<K, V>> entries() {
        LinkedHashSet<Entry<K, V>> output = new LinkedHashSet<>(size);
        for (Entry<K, V> entry: table) {
            if (entry != null) {
                Entry<K, V> current = entry;
                while (current != null) {
                    output.add(current);
                    current = current.getNext();
                }
            }
            if (output.size() == size) {
                break;
            }
        }
        return output;
    }

    public LinkedHashSet<K> keys() {
        LinkedHashSet<K> output = new LinkedHashSet<>(size);
        for (Entry<K, V> entry: table) {
            if (entry != null) {
                Entry<K, V> current = entry;
                while (current != null) {
                    output.add(current.getKey());
                    current = current.getNext();
                }
            }
            if (output.size() == size) {
                break;
            }
        }
        return output;
    }

    public ArrayList<V> values() {
        ArrayList<V> output = new ArrayList<>(size);
        for (Entry<K, V> entry: table) {
            if (entry != null) {
                Entry<K, V> current = entry;
                while (current != null) {
                    output.addToBack(current.getValue());
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
        Entry<K, V>[] newTable = new Entry[length];
        int count = 0;
        for (Entry<K, V> entry: table) {
            if (entry != null) {
                Entry<K, V> current = entry;
                while (current != null) {
                    int index = Math.abs(current.getKey().hashCode() % newTable.length);
                    newTable[index] = new Entry<>(current.getKey(), current.getValue(), newTable[index]);
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
        } else if (o instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<Object, Object> other = (LinkedHashMap<Object, Object>) o;
            if (size == other.size) {
                int count = 0;
                for (Entry<K, V> entry: table) {
                    if (entry != null) {
                        Entry<K, V> current = entry;
                        while (current != null) {
                            try {
                                if (!other.get(entry.getKey()).equals(entry.getValue())) {
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
        for (Entry<K, V> entry: table) {
            if (entry != null) {
                Entry<K, V> current = entry;
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
        for (Entry<K, V> entry: table) {
            if (entry != null) {
                Entry<K, V> current = entry;
                while (current != null) {
                    builder.append(prefix);
                    prefix = ", ";
                    builder.append(current.toString());
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

    Entry<K, V>[] getTable() {
        return table;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return entries().iterator();
    }

    static class Entry<K, V> {

        private final K key;
        private V value;
        private Entry<K, V> next;

        Entry(K key, V value) {
            this(key, value, null);
        }

        private Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }
        
        public V getValue() {
            return value;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        private void setValue(V value) {
            this.value = value;
        }

        private void setNext(Entry<K, V> next) {
            this.next = next;
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
}
