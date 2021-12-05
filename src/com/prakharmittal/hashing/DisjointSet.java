package com.prakharmittal.hashing;

public class DisjointSet<T> {

    private final HashMap<T, T> map = new HashMap();

    public DisjointSet(HashSet<T> keys) {
        if (keys == null) {
            throw new IllegalArgumentException("The keys must not be null.");
        }
        for (T key: keys) {
            map.put(key, key);
        }
    }

    public T find(T key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be found must not be null.");
        }
        if (map.get(key) == key) {
            return key;
        }
        T result = find(map.get(key));
        map.put(key, result);
        return result;
    }

    public void union(T key1, T key2) {
        if (key1 == null || key2 == null) {
            throw new IllegalArgumentException("The keys must not be null.");
        }
        map.put(find(key1), find(key2));
    }

    HashMap<T, T> getMap() {
        return map;
    }
}
