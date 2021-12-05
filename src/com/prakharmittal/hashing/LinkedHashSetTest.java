package com.prakharmittal.hashing;

import com.prakharmittal.list.ArrayList;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LinkedHashSetTest {

    private static final int TIMEOUT = 200;
    private LinkedHashSet<Integer> set;
    private LinkedHashSet<Integer> setZero;

    @Before
    public void setUp() {
        set = new LinkedHashSet<>(5);
        setZero = new LinkedHashSet<>(0);
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, set.size());
        assertArrayEquals(new LinkedHashSet.Entry[5], set.getTable());

        assertEquals(0, setZero.size());
        assertArrayEquals(new LinkedHashSet.Entry[3], setZero.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t02_AddNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            set.add(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t03_Add() {
        set.add(0);
        set.add(4);
        set.add(55);
        assertEquals(3, set.size());

        LinkedHashSet.Entry<Integer>[] expected = new LinkedHashSet.Entry[5];
        expected[0] = new LinkedHashSet.Entry<>(55);
        expected[4] = new LinkedHashSet.Entry<>(4);
        assertArrayEquals(expected, set.getTable());

        assertEquals(new LinkedHashSet.Entry<>(0), set.getTable()[0].getNext());

        set.add(1);
        set.add(12);
        set.add(5);
        set.add(23);
        assertEquals(7, set.size());

        expected = new LinkedHashSet.Entry[11];
        expected[0] = new LinkedHashSet.Entry<>(0);
        expected[1] = new LinkedHashSet.Entry<>(23);
        expected[4] = new LinkedHashSet.Entry<>(4);
        expected[5] = new LinkedHashSet.Entry<>(5);
        assertArrayEquals(expected, set.getTable());

        assertEquals(new LinkedHashSet.Entry<>(55), set.getTable()[0].getNext());
        assertEquals(new LinkedHashSet.Entry<>(12), set.getTable()[1].getNext());
        assertEquals(new LinkedHashSet.Entry<>(1), set.getTable()[1].getNext().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void t04_AddExisting() {
        set.add(0);
        set.add(4);
        set.add(55);
        set.add(1);
        set.add(12);
        set.add(23);
        assertEquals(6, set.size());

        set.add(0);
        set.add(12);
        set.add(1);
        assertEquals(6, set.size());

        LinkedHashSet.Entry<Integer>[] expected = new LinkedHashSet.Entry[11];
        expected[0] = new LinkedHashSet.Entry<>(0);
        expected[1] = new LinkedHashSet.Entry<>(23);
        expected[4] = new LinkedHashSet.Entry<>(4);
        assertArrayEquals(expected, set.getTable());

        assertEquals(new LinkedHashSet.Entry<>(55), set.getTable()[0].getNext());
        assertEquals(new LinkedHashSet.Entry<>(12), set.getTable()[1].getNext());
        assertEquals(new LinkedHashSet.Entry<>(1), set.getTable()[1].getNext().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void t05_RemoveNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            set.remove(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t06_RemoveNullEntry() {
        set.add(0);
        assertEquals(1, set.size());

        assertThrows(NoSuchElementException.class, () -> {
            set.remove(1);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t07_RemoveNotFound() {
        set.add(0);
        set.add(5);
        set.add(10);
        assertEquals(3, set.size());

        assertThrows(NoSuchElementException.class, () -> {
            set.remove(15);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t08_RemoveFirstNode() {
        set.add(0);
        set.add(1);
        set.add(6);
        assertEquals(3, set.size());

        set.remove(6);

        LinkedHashSet.Entry<Integer>[] expected = new LinkedHashSet.Entry[5];
        expected[0] = new LinkedHashSet.Entry<>(0);
        expected[1] = new LinkedHashSet.Entry<>(1);
        assertArrayEquals(expected, set.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t09_RemoveMiddleNode() {
        set.add(1);
        set.add(6);
        set.add(11);
        assertEquals(3, set.size());

        set.remove(6);

        LinkedHashSet.Entry<Integer>[] expected = new LinkedHashSet.Entry[5];
        expected[1] = new LinkedHashSet.Entry<>(11);
        assertArrayEquals(expected, set.getTable());

        assertEquals(new LinkedHashSet.Entry<>(1), set.getTable()[1].getNext());

        set.remove(1);

        assertArrayEquals(expected, set.getTable());

        set.getTable()[1].getNext();
    }

    @Test(timeout = TIMEOUT)
    public void t10_ContainsNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            set.contains(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t11_Contains() {
        set.add(0);
        set.add(4);
        set.add(55);
        set.add(1);
        set.add(12);
        set.add(5);
        set.add(23);
        assertEquals(7, set.size());

        assertTrue(set.contains(0));
        assertTrue(set.contains(4));
        assertTrue(set.contains(55));
        assertTrue(set.contains(1));
        assertTrue(set.contains(12));
        assertTrue(set.contains(5));
        assertTrue(set.contains(23));

        assertFalse(set.contains(11));
        assertFalse(set.contains(34));
        assertFalse(set.contains(2));
        assertFalse(set.contains(10));
    }

    @Test(timeout = TIMEOUT)
    public void t12_toArrayList() {
        assertEquals(new ArrayList<Integer>(0), set.toArrayList());

        set.add(0);
        set.add(4);
        set.add(55);
        set.add(10);
        set.add(13);
        set.add(21);
        assertEquals(6, set.size());

        ArrayList<Integer> expected = new ArrayList<>(6);
        expected.addToBack(0);
        expected.addToBack(55);
        expected.addToBack(13);
        expected.addToBack(4);
        expected.addToBack(21);
        expected.addToBack(10);
        assertEquals(expected, set.toArrayList());
    }

    @Test(timeout = TIMEOUT)
    public void t13_ResizeIllegalLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            set.resizeBackingTable(-1);
        });

        set.add(0);
        set.add(4);
        set.add(55);
        assertEquals(3, set.size());

        assertThrows(IllegalArgumentException.class, () -> {
            set.resizeBackingTable(2);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t14_ResizeZeroLength() {
        set.resizeBackingTable(0);

        LinkedHashSet.Entry<Integer>[] expected = new LinkedHashSet.Entry[0];
        assertArrayEquals(expected, set.getTable());

        set.add(0);

        expected = new LinkedHashSet.Entry[1];
        expected[0] = new LinkedHashSet.Entry<>(0);
        assertArrayEquals(expected, set.getTable());

        set.add(4);

        expected = new LinkedHashSet.Entry[3];
        expected[0] = new LinkedHashSet.Entry<>(0);
        expected[1] = new LinkedHashSet.Entry<>(4);
        assertArrayEquals(expected, set.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t15_ResizeDown() {
        set.add(0);
        set.add(4);
        set.add(55);
        set.add(1);
        set.add(12);
        set.add(5);
        set.add(23);
        assertEquals(7, set.size());

        set.resizeBackingTable(7);

        LinkedHashSet.Entry<Integer>[] expected = new LinkedHashSet.Entry[7];
        expected[0] = new LinkedHashSet.Entry<>(0);
        expected[1] = new LinkedHashSet.Entry<>(1);
        expected[2] = new LinkedHashSet.Entry<>(23);
        expected[4] = new LinkedHashSet.Entry<>(4);
        expected[5] = new LinkedHashSet.Entry<>(5);
        expected[6] = new LinkedHashSet.Entry<>(55);
        assertArrayEquals(expected, set.getTable());

        assertEquals(new LinkedHashSet.Entry<>(12), set.getTable()[5].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void t16_IsEmpty() {
        assertTrue(set.isEmpty());

        set.add(0);
        assertEquals(1, set.size());

        assertFalse(set.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t17_Clear() {
        set.add(0);
        assertEquals(1, set.size());

        LinkedHashSet.Entry<Integer>[] expected = new LinkedHashSet.Entry[5];
        expected[0] = new LinkedHashSet.Entry<>(0);
        assertArrayEquals(expected, set.getTable());

        set.clear();
        assertEquals(0, set.size());

        expected[0] = null;
        assertArrayEquals(expected, set.getTable());

        setZero.add(0);
        setZero.add(1);
        setZero.add(2);
        assertEquals(3, setZero.size());

        LinkedHashSet.Entry<Integer>[] expectedZero = new LinkedHashSet.Entry[7];
        expectedZero[0] = new LinkedHashSet.Entry<>(0);
        expectedZero[1] = new LinkedHashSet.Entry<>(1);
        expectedZero[2] = new LinkedHashSet.Entry<>(2);
        assertArrayEquals(expectedZero, setZero.getTable());

        setZero.clear();
        assertEquals(0, setZero.size());

        expectedZero[0] = null;
        expectedZero[1] = null;
        expectedZero[2] = null;
        assertArrayEquals(expectedZero, setZero.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t18_Iterator() {
        set.add(0);
        set.add(4);
        set.add(55);
        set.add(1);
        set.add(12);
        set.add(5);
        set.add(23);
        assertEquals(7, set.size());

        LinkedHashSet<Integer> actual = new LinkedHashSet<>();
        for (Integer key: set) {
            actual.add(key);
        }
        assertEquals(set, actual);
    }

    @Test(timeout = TIMEOUT)
    public void t19_EqualsNotHashSet() {
        assertNotEquals(5, set);
    }

    @Test(timeout = TIMEOUT)
    public void t20_EqualsSame() {
        assertEquals(set, set);
    }

    @Test(timeout = TIMEOUT)
    public void t21_EqualsEmpty() {
        assertEquals(set, setZero);
    }

    @Test(timeout = TIMEOUT)
    public void t22_EqualsDifferentSize() {
        set.add(0);
        set.add(5);
        assertEquals(2, set.size());

        setZero.add(0);
        assertEquals(1, setZero.size());

        assertNotEquals(set, setZero);
    }

    @Test(timeout = TIMEOUT)
    public void t23_EqualsDifferentKey() {
        set.add(0);
        assertEquals(1, set.size());

        setZero.add(1);
        assertEquals(1, setZero.size());

        assertNotEquals(set, setZero);
    }

    @Test(timeout = TIMEOUT)
    public void t24_EqualsDifferentT() {
        set.add(0);
        assertEquals(1, set.size());

        LinkedHashSet<String> set2 = new LinkedHashSet<>();
        set2.add("A");
        assertEquals(1, set.size());

        assertNotEquals(set, set2);

        LinkedHashSet<Object> set3 = new LinkedHashSet<>();
        set3.add(0);
        assertEquals(1, set3.size());

        assertEquals(set, set3);

        set.add(1);
        assertEquals(2, set.size());

        set3.add("B");
        assertEquals(2, set3.size());

        assertNotEquals(set, set3);
    }

    @Test(timeout = TIMEOUT)
    public void t25_Equals() {
        set.add(0);
        set.add(4);
        set.add(55);
        set.add(1);
        set.add(12);
        set.add(5);
        set.add(23);
        assertEquals(7, set.size());

        setZero.add(0);
        setZero.add(4);
        setZero.add(55);
        setZero.add(1);
        setZero.add(12);
        setZero.add(5);
        setZero.add(23);
        assertEquals(7, setZero.size());

        assertEquals(set, setZero);
    }

    @Test(timeout = TIMEOUT)
    public void t26_HashCode() {
        set.add(0);
        set.add(4);
        set.add(55);
        set.add(1);
        set.add(12);
        set.add(5);
        set.add(23);
        assertEquals(7, set.size());

        setZero.add(0);
        setZero.add(4);
        setZero.add(55);
        setZero.add(1);
        setZero.add(12);
        setZero.add(5);
        setZero.add(23);
        assertEquals(7, setZero.size());

        assertEquals(set.hashCode(), setZero.hashCode());
    }
}