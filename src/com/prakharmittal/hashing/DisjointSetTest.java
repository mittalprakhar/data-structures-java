package com.prakharmittal.hashing;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DisjointSetTest {

    private static final int TIMEOUT = 200;
    private DisjointSet<Integer> set;

    @Test(timeout = TIMEOUT)
    public void t01_Exceptions() {
        assertThrows(IllegalArgumentException.class, () -> {
            set = new DisjointSet<>(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DisjointSet<>(makeHashSet(new int[] {})).find(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DisjointSet<>(makeHashSet(new int[] {})).union(null, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DisjointSet<>(makeHashSet(new int[] {})).union(0, null);
        });

        assertThrows(NoSuchElementException.class, () -> {
            new DisjointSet<>(makeHashSet(new int[] {1, 2})).find(3);
        });

        assertThrows(NoSuchElementException.class, () -> {
            new DisjointSet<>(makeHashSet(new int[] {1, 2})).union(1, 3);
        });

        assertThrows(NoSuchElementException.class, () -> {
            new DisjointSet<>(makeHashSet(new int[] {1, 2})).union(3, 1);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t02_Constructor() {
        set = new DisjointSet<>(makeHashSet(new int[] {}));
        assertArrayEquals(new HashMap<>().getTable(), set.getMap().getTable());

        set = new DisjointSet<>(makeHashSet(new int[] {1}));
        assertArrayEquals(makeHashMap(new int[] {1}, new int[] {1}).getTable(), set.getMap().getTable());

        set = new DisjointSet<>(makeHashSet(new int[] {1, 2, 3, 4, 5}));
        assertArrayEquals(makeHashMap(new int[] {1, 2, 3, 4, 5}, new int[] {1, 2, 3, 4, 5}).getTable(),
                set.getMap().getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t03_FindAndUnion() {
        set = new DisjointSet<>(makeHashSet(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));

        set.union(1, 2);
        assertEquals((Integer) 2, set.find(1));
        assertEquals((Integer) 2, set.find(2));

        set.union(2, 3);
        assertEquals((Integer) 3, set.find(1));
        assertEquals((Integer) 3, set.find(2));
        assertEquals((Integer) 3, set.find(3));

        set.union(4, 5);
        assertEquals((Integer) 5, set.find(4));
        assertEquals((Integer) 5, set.find(5));

        set.union(4, 6);
        assertEquals((Integer) 6, set.find(4));
        assertEquals((Integer) 6, set.find(5));
        assertEquals((Integer) 6, set.find(6));

        set.union(1, 5);
        assertEquals((Integer) 6, set.find(1));
        assertEquals((Integer) 6, set.find(2));
        assertEquals((Integer) 6, set.find(3));
    }

    private static HashSet<Integer> makeHashSet(int[] keys) {
        HashSet<Integer> output = new HashSet<>();
        for (int key: keys) {
            output.add(key);
        }
        return output;
    }

    private static HashMap<Integer, Integer> makeHashMap(int[] keys, int[] values) {
        HashMap<Integer, Integer> output = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            output.put(keys[i], i < values.length ? values[i] : null);
        }
        return output;
    }
}
