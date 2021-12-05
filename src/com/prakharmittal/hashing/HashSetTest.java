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
public class HashSetTest {

    private static final int TIMEOUT = 200;
    private HashSet<Integer> set;
    private HashSet<Integer> setZero;

    @Before
    public void setUp() {
        set = new HashSet<>(5);
        setZero = new HashSet<>(0);
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, set.size());
        assertArrayEquals(new HashSet.Entry[5], set.getTable());

        assertEquals(0, setZero.size());
        assertArrayEquals(new HashSet.Entry[3], setZero.getTable());
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

        HashSet.Entry<Integer>[] expected = new HashSet.Entry[5];
        expected[0] = new HashSet.Entry<>(0);
        expected[1] = new HashSet.Entry<>(55);
        expected[4] = new HashSet.Entry<>(4);
        assertArrayEquals(expected, set.getTable());

        set.add(2);
        set.add(10);
        set.add(13);
        set.add(21);
        assertEquals(7, set.size());

        expected = new HashSet.Entry[11];
        expected[0] = new HashSet.Entry<>(0);
        expected[1] = new HashSet.Entry<>(55);
        expected[2] = new HashSet.Entry<>(2);
        expected[3] = new HashSet.Entry<>(13);
        expected[4] = new HashSet.Entry<>(4);
        expected[5] = new HashSet.Entry<>(21);
        expected[10] = new HashSet.Entry<>(10);
        assertArrayEquals(expected, set.getTable());

        set.remove(13);
        assertEquals(6, set.size());
        assertTrue(set.getTable()[3].getRemoved());

        set.add(32);

        expected[3] = new HashSet.Entry<>(32);
        assertArrayEquals(expected, set.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t04_AddExisting() {
        set.add(0);
        set.add(55);
        set.add(2);
        set.add(10);
        set.add(21);
        set.add(11);
        assertEquals(6, set.size());

        set.add(0);
        set.add(11);
        set.add(21);
        assertEquals(6, set.size());

        HashSet.Entry<Integer>[] expected = new HashSet.Entry[11];
        expected[0] = new HashSet.Entry<>(0);
        expected[1] = new HashSet.Entry<>(55);
        expected[2] = new HashSet.Entry<>(2);
        expected[3] = new HashSet.Entry<>(21);
        expected[4] = new HashSet.Entry<>(11);
        expected[10] = new HashSet.Entry<>(10);
        assertArrayEquals(expected, set.getTable());

        set.remove(2);
        assertEquals(5, set.size());
        assertTrue(set.getTable()[2].getRemoved());

        set.add(21);

        expected[3] = new HashSet.Entry<>(21);
        assertArrayEquals(expected, set.getTable());

        set.add(32);

        expected[2] = new HashSet.Entry<>(32);
        assertArrayEquals(expected, set.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t05_AddFull() {
        set.add(0);
        set.add(6);
        set.add(7);
        set.add(13);
        set.add(14);
        set.add(20);
        assertEquals(6, set.size());

        set.resizeBackingTable(6);

        HashSet.Entry<Integer>[] expected = new HashSet.Entry[6];
        expected[0] = new HashSet.Entry<>(0);
        expected[1] = new HashSet.Entry<>(13);
        expected[2] = new HashSet.Entry<>(14);
        expected[3] = new HashSet.Entry<>(6);
        expected[4] = new HashSet.Entry<>(7);
        expected[5] = new HashSet.Entry<>(20);
        assertArrayEquals(expected, set.getTable());

        set.remove(7);
        set.remove(13);
        set.remove(14);
        assertEquals(3, set.size());
        assertTrue(set.getTable()[1].getRemoved());
        assertTrue(set.getTable()[2].getRemoved());
        assertTrue(set.getTable()[4].getRemoved());

        set.add(20);

        expected[5] = new HashSet.Entry<>(20);
        assertArrayEquals(expected, set.getTable());

        set.add(12);

        expected[1] = new HashSet.Entry<>(12);
        assertArrayEquals(expected, set.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t06_RemoveNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            set.remove(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t07_RemoveNotFound() {
        set.add(4);
        set.add(9);
        set.add(1);
        assertEquals(3, set.size());

        assertThrows(NoSuchElementException.class, () -> {
            set.remove(2);
        });

        assertThrows(NoSuchElementException.class, () -> {
            set.remove(14);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t08_RemoveNotFoundEfficient() {
        set.add(0);
        assertEquals(1, set.size());

        set.getTable()[2] = new HashSet.Entry<>(1);

        assertThrows(NoSuchElementException.class, () -> {
            set.remove(1);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t09_RemoveFound() {
        set.add(4);
        set.add(9);
        set.add(14);
        assertEquals(3, set.size());

        set.remove(9);
        assertEquals(2, set.size());
        assertTrue(set.getTable()[0].getRemoved());

        set.remove(14);
        assertEquals(1, set.size());
        assertTrue(set.getTable()[1].getRemoved());
    }

    @Test(timeout = TIMEOUT)
    public void t10_RemoveFull() {
        set.add(0);
        set.add(15);
        set.add(6);
        assertEquals(3, set.size());

        set.resizeBackingTable(3);

        HashSet.Entry<Integer>[] expected = new HashSet.Entry[3];
        expected[0] = new HashSet.Entry<>(0);
        expected[1] = new HashSet.Entry<>(15);
        expected[2] = new HashSet.Entry<>(6);
        assertArrayEquals(expected, set.getTable());

        assertThrows(NoSuchElementException.class, () -> {
            set.remove(7);
        });

        set.remove(15);
        assertEquals(2, set.size());
        assertTrue(set.getTable()[1].getRemoved());

        set.remove(6);
        assertEquals(1, set.size());
        assertTrue(set.getTable()[1].getRemoved());
    }

    @Test(timeout = TIMEOUT)
    public void t11_ContainsNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            set.contains(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t12_Contains() {
        set.add(0);
        set.add(4);
        set.add(55);
        set.add(2);
        set.add(10);
        set.add(13);
        set.add(21);
        assertEquals(7, set.size());

        set.remove(21);
        assertEquals(6, set.size());
        assertTrue(set.getTable()[5].getRemoved());

        set.remove(2);
        assertEquals(5, set.size());
        assertTrue(set.getTable()[5].getRemoved());

        set.add(7);
        assertEquals(6, set.size());

        assertTrue(set.contains(0));
        assertTrue(set.contains(4));
        assertTrue(set.contains(55));
        assertTrue(set.contains(10));
        assertTrue(set.contains(13));
        assertTrue(set.contains(7));

        assertFalse(set.contains(21));
        assertFalse(set.contains(2));
        assertFalse(set.contains(9));
        assertFalse(set.contains(11));

        set.resizeBackingTable(6);

        assertTrue(set.contains(0));
        assertTrue(set.contains(4));
        assertTrue(set.contains(55));
        assertTrue(set.contains(10));
        assertTrue(set.contains(13));
        assertTrue(set.contains(7));

        assertFalse(set.contains(21));
        assertFalse(set.contains(2));
        assertFalse(set.contains(9));
        assertFalse(set.contains(11));
    }

    @Test(timeout = TIMEOUT)
    public void t13_toArrayList() {
        assertEquals(new ArrayList<Integer>(0), set.toArrayList());

        set.add(0);
        set.add(4);
        set.add(55);
        set.add(2);
        set.add(10);
        set.add(13);
        set.add(21);
        assertEquals(7, set.size());

        set.remove(2);
        assertEquals(6, set.size());
        assertTrue(set.getTable()[2].getRemoved());

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
    public void t14_ResizeIllegalLength() {
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
    public void t15_ResizeZeroLength() {
        set.resizeBackingTable(0);

        HashSet.Entry<Integer>[] expected = new HashSet.Entry[0];
        assertArrayEquals(expected, set.getTable());

        set.add(0);

        expected = new HashSet.Entry[1];
        expected[0] = new HashSet.Entry<>(0);
        assertArrayEquals(expected, set.getTable());

        set.add(4);

        expected = new HashSet.Entry[3];
        expected[0] = new HashSet.Entry<>(0);
        expected[1] = new HashSet.Entry<>(4);
        assertArrayEquals(expected, set.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t16_ResizeDown() {
        set.add(0);
        set.add(4);
        set.add(55);
        set.add(2);
        set.add(10);
        set.add(13);
        set.add(21);
        assertEquals(7, set.size());

        set.remove(2);
        assertEquals(6, set.size());
        assertTrue(set.getTable()[2].getRemoved());

        set.resizeBackingTable(7);

        HashSet.Entry<Integer>[] expected = new HashSet.Entry[7];
        expected[0] = new HashSet.Entry<>(0);
        expected[1] = new HashSet.Entry<>(13);
        expected[2] = new HashSet.Entry<>(21);
        expected[3] = new HashSet.Entry<>(10);
        expected[4] = new HashSet.Entry<>(4);
        expected[6] = new HashSet.Entry<>(55);
        assertArrayEquals(expected, set.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t17_IsEmpty() {
        assertTrue(set.isEmpty());

        set.add(0);
        assertEquals(1, set.size());

        assertFalse(set.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t18_Clear() {
        set.add(0);
        assertEquals(1, set.size());

        HashSet.Entry<Integer>[] expected = new HashSet.Entry[5];
        expected[0] = new HashSet.Entry<>(0);
        assertArrayEquals(expected, set.getTable());

        set.clear();
        assertEquals(0, set.size());

        expected[0] = null;
        assertArrayEquals(expected, set.getTable());

        setZero.add(0);
        setZero.add(1);
        setZero.add(2);
        assertEquals(3, setZero.size());

        HashSet.Entry<Integer>[] expectedZero = new HashSet.Entry[7];
        expectedZero[0] = new HashSet.Entry<>(0);
        expectedZero[1] = new HashSet.Entry<>(1);
        expectedZero[2] = new HashSet.Entry<>(2);
        assertArrayEquals(expectedZero, setZero.getTable());

        setZero.clear();
        assertEquals(0, setZero.size());

        expectedZero[0] = null;
        expectedZero[1] = null;
        expectedZero[2] = null;
        assertArrayEquals(expectedZero, setZero.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t19_Iterator() {
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);
        assertEquals(4, set.size());

        int i = 0;
        for (Integer key: set) {
            assertEquals((Integer) i, key);
            i++;
        }
    }

    @Test(timeout = TIMEOUT)
    public void t20_EqualsNotHashSet() {
        assertNotEquals(5, set);
    }

    @Test(timeout = TIMEOUT)
    public void t21_EqualsSame() {
        assertEquals(set, set);
    }

    @Test(timeout = TIMEOUT)
    public void t22_EqualsEmpty() {
        assertEquals(set, setZero);
    }

    @Test(timeout = TIMEOUT)
    public void t23_EqualsDifferentSize() {
        set.add(0);
        set.add(5);
        assertEquals(2, set.size());

        setZero.add(0);
        assertEquals(1, setZero.size());

        assertNotEquals(set, setZero);
    }

    @Test(timeout = TIMEOUT)
    public void t24_EqualsDifferentKey() {
        set.add(0);
        assertEquals(1, set.size());

        setZero.add(1);
        assertEquals(1, setZero.size());

        assertNotEquals(set, setZero);
    }

    @Test(timeout = TIMEOUT)
    public void t25_EqualsDifferentT() {
        set.add(0);
        assertEquals(1, set.size());

        HashSet<String> set2 = new HashSet<>();
        set2.add("A");
        assertEquals(1, set.size());

        assertNotEquals(set, set2);

        HashSet<Object> set3 = new HashSet<>();
        set3.add(0);
        assertEquals(1, set3.size());

        assertEquals(set, set3);

        set.add(1);
        assertEquals(2, set.size());

        set3.add("A");
        assertEquals(2, set3.size());

        assertNotEquals(set, set3);
    }

    @Test(timeout = TIMEOUT)
    public void t26_Equals() {
        set.add(0);
        set.add(4);
        set.add(55);
        set.add(1);
        assertEquals(4, set.size());

        setZero.add(0);
        setZero.add(4);
        setZero.add(55);
        setZero.add(1);
        assertEquals(4, setZero.size());

        assertEquals(set, setZero);
    }

    @Test(timeout = TIMEOUT)
    public void t27_HashCode() {
        set.add(0);
        set.add(4);
        set.add(55);
        set.add(1);
        assertEquals(4, set.size());

        setZero.add(0);
        setZero.add(4);
        setZero.add(55);
        setZero.add(1);
        assertEquals(4, setZero.size());

        assertEquals(set.hashCode(), setZero.hashCode());
    }
}
