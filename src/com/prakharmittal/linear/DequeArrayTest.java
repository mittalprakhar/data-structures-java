package com.prakharmittal.linear;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DequeArrayTest {

    private static final int TIMEOUT = 200;
    private DequeArray<String> deque;
    private DequeArray<String> dequeZero;

    @Before
    public void setUp() {
        deque = new DequeArray<>(5);
        dequeZero = new DequeArray<>(0);
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, deque.size());
        assertEquals(0, deque.getFront());
        assertArrayEquals(new String[5], deque.getBackingArray());

        assertEquals(0, dequeZero.size());
        assertEquals(0, deque.getFront());
        assertArrayEquals(new String[1], dequeZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t02_AddFirstResize() {
        assertThrows(IllegalArgumentException.class, () -> {
            deque.addFirst(null);
        });

        deque.addFirst("F");
        assertEquals(1, deque.size());
        assertEquals(4, deque.getFront());

        String[] expected = {null, null, null, null, "F"};
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addFirst("E");
        assertEquals(2, deque.size());
        assertEquals(3, deque.getFront());

        expected[3] = "E";
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addFirst("D");
        assertEquals(3, deque.size());
        assertEquals(2, deque.getFront());

        expected[2] = "D";
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addFirst("C");
        assertEquals(4, deque.size());
        assertEquals(1, deque.getFront());

        expected[1] = "C";
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addFirst("B");
        assertEquals(5, deque.size());
        assertEquals(0, deque.getFront());

        expected[0] = "B";
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addFirst("A");
        assertEquals(6, deque.size());
        assertEquals(0, deque.getFront());

        String[] expected2 = {"A", "B", "C", "D", "E", "F", null, null, null, null};
        assertArrayEquals(expected2, deque.getBackingArray());

        deque.addFirst("J");
        assertEquals(7, deque.size());
        assertEquals(9, deque.getFront());

        expected2[9] = "J";
        assertArrayEquals(expected2, deque.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t03_AddFirstZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            dequeZero.addFirst(null);
        });

        dequeZero.addFirst("C");
        assertEquals(1, dequeZero.size());
        assertEquals(0, dequeZero.getFront());

        String[] expected = {"C"};
        assertArrayEquals(expected, dequeZero.getBackingArray());

        dequeZero.addFirst("B");
        assertEquals(2, dequeZero.size());
        assertEquals(0, dequeZero.getFront());

        String[] expected2 = {"B", "C"};
        assertArrayEquals(expected2, dequeZero.getBackingArray());

        dequeZero.addFirst("A");
        assertEquals(3, dequeZero.size());
        assertEquals(0, dequeZero.getFront());

        String[] expected3 = {"A", "B", "C", null};
        assertArrayEquals(expected3, dequeZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t04_AddLastResize() {
        assertThrows(IllegalArgumentException.class, () -> {
            deque.addFirst(null);
        });

        deque.addLast("A");
        assertEquals(1, deque.size());
        assertEquals(0, deque.getFront());

        String[] expected = {"A", null, null, null, null};
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addLast("B");
        assertEquals(2, deque.size());
        assertEquals(0, deque.getFront());

        expected[1] = "B";
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addLast("C");
        assertEquals(3, deque.size());
        assertEquals(0, deque.getFront());

        expected[2] = "C";
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addLast("D");
        assertEquals(4, deque.size());
        assertEquals(0, deque.getFront());

        expected[3] = "D";
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addLast("E");
        assertEquals(5, deque.size());
        assertEquals(0, deque.getFront());

        expected[4] = "E";
        assertArrayEquals(expected, deque.getBackingArray());

        assertSame("A", deque.removeFirst());
        assertEquals(4, deque.size());
        assertEquals(1, deque.getFront());

        expected[0] = null;
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addLast("F");
        assertEquals(5, deque.size());
        assertEquals(1, deque.getFront());

        expected[0] = "F";
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addLast("G");
        assertEquals(6, deque.size());
        assertEquals(0, deque.getFront());

        String[] expected2 = {"B", "C", "D", "E", "F", "G", null, null, null, null};
        assertArrayEquals(expected2, deque.getBackingArray());

        deque.addLast("H");
        assertEquals(7, deque.size());
        assertEquals(0, deque.getFront());

        expected2[6] = "H";
        assertArrayEquals(expected2, deque.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t05_AddLastZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            dequeZero.addLast(null);
        });

        dequeZero.addLast("A");
        assertEquals(1, dequeZero.size());

        String[] expected = {"A"};
        assertArrayEquals(expected, dequeZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t06_RemoveFirstInvalid() {
        assertThrows(NoSuchElementException.class, () -> {
            deque.removeFirst();
        });

        assertThrows(NoSuchElementException.class, () -> {
            dequeZero.removeFirst();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t07_RemoveFirst() {
        String a = new String("A");
        String b = new String("B");

        deque.addLast("A");
        deque.addLast(a);
        deque.addLast(b);
        assertEquals(3, deque.size());

        assertSame("A", deque.removeFirst());
        assertEquals(2, deque.size());
        assertEquals(1, deque.getFront());

        String[] expected = {null, a, b, null, null};
        assertArrayEquals(expected, deque.getBackingArray());

        assertSame(a, deque.removeFirst());
        assertEquals(1, deque.size());
        assertEquals(2, deque.getFront());

        expected[1] = null;
        assertArrayEquals(expected, deque.getBackingArray());

        assertSame(b, deque.removeFirst());
        assertEquals(0, deque.size());
        assertEquals(3, deque.getFront());

        expected[2] = null;
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addLast("A");
        deque.addLast(a);
        deque.addLast(b);
        assertEquals(3, deque.size());

        expected[3] = "A";
        expected[4] = a;
        expected[0] = b;
        assertArrayEquals(expected, deque.getBackingArray());

        assertSame("A", deque.removeFirst());
        assertEquals(2, deque.size());
        assertEquals(4, deque.getFront());

        expected[3] = null;
        assertArrayEquals(expected, deque.getBackingArray());

        assertSame(a, deque.removeFirst());
        assertEquals(1, deque.size());
        assertEquals(0, deque.getFront());

        expected[4] = null;
        assertArrayEquals(expected, deque.getBackingArray());

        assertSame(b, deque.removeFirst());
        assertEquals(0, deque.size());
        assertEquals(1, deque.getFront());

        expected[0] = null;
        assertArrayEquals(expected, deque.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t08_RemoveLastInvalid() {
        assertThrows(NoSuchElementException.class, () -> {
            deque.removeLast();
        });

        assertThrows(NoSuchElementException.class, () -> {
            dequeZero.removeLast();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t09_RemoveLast() {
        String a = new String("A");
        String b = new String("B");

        deque.addLast("A");
        deque.addLast(a);
        deque.addLast(b);
        assertEquals(3, deque.size());

        assertSame(b, deque.removeLast());
        assertEquals(2, deque.size());
        assertEquals(0, deque.getFront());

        String[] expected = {"A", a, null, null, null};
        assertArrayEquals(expected, deque.getBackingArray());

        deque.addLast("B");
        deque.addLast(b);
        deque.addLast("C");
        assertEquals(5, deque.size());

        assertSame("A", deque.removeFirst());
        assertEquals(4, deque.size());
        assertEquals(1, deque.getFront());

        deque.addLast("D");
        assertEquals(5, deque.size());

        expected[2] = "B";
        expected[3] = b;
        expected[4] = "C";
        expected[0] = "D";
        assertArrayEquals(expected, deque.getBackingArray());

        assertSame("D", deque.removeLast());
        assertEquals(4, deque.size());
        assertEquals(1, deque.getFront());

        expected[0] = null;
        assertArrayEquals(expected, deque.getBackingArray());

        assertSame("C", deque.removeLast());
        assertEquals(3, deque.size());
        assertEquals(1, deque.getFront());

        expected[4] = null;
        assertArrayEquals(expected, deque.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t10_GetFirst() {
        assertNull(deque.getFirst());
        assertNull(dequeZero.getFirst());

        String a = new String("A");
        String b = new String("B");

        deque.addLast("A");
        assertSame("A", deque.getFirst());

        deque.addLast(a);
        assertSame("A", deque.getFirst());

        deque.removeFirst();
        assertSame(a, deque.getFirst());

        deque.addLast(b);
        assertSame(a, deque.getFirst());

        deque.removeFirst();
        assertSame(b, deque.getFirst());
    }

    @Test(timeout = TIMEOUT)
    public void t11_GetLast() {
        assertNull(deque.getLast());
        assertNull(deque.getLast());

        String a = new String("A");
        String b = new String("B");

        deque.addFirst("A");
        assertSame("A", deque.getLast());

        deque.addFirst(a);
        assertSame("A", deque.getLast());

        deque.addLast("C");
        assertSame("C", deque.getLast());

        deque.addLast(b);
        assertSame(b, deque.getLast());

        deque.addLast("D");
        assertSame("D", deque.getLast());

        deque.addLast("D");
        assertSame("D", deque.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void t12_IsEmpty() {
        assertTrue(deque.isEmpty());

        deque.addLast("A");
        assertEquals(1, deque.size());

        assertFalse(deque.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t13_Clear() {
        deque.addLast("A");
        assertEquals(1, deque.size());

        String[] expected = {"A", null, null, null, null};
        assertArrayEquals(expected, deque.getBackingArray());

        deque.clear();
        assertEquals(0, deque.size());

        expected[0] = null;
        assertArrayEquals(expected, deque.getBackingArray());

        dequeZero.addLast("A");
        dequeZero.addLast("B");
        assertEquals(2, dequeZero.size());

        String[] expectedZero = {"A", "B"};
        assertArrayEquals(expectedZero, dequeZero.getBackingArray());

        dequeZero.clear();
        assertEquals(0, dequeZero.size());

        expectedZero[0] = null;
        expectedZero[1] = null;
        assertArrayEquals(expectedZero, dequeZero.getBackingArray());
    }
}
