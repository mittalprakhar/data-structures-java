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
public class QueueArrayTest {

    private static final int TIMEOUT = 200;
    private QueueArray<String> queue;
    private QueueArray<String> queueZero;

    @Before
    public void setUp() {
        queue = new QueueArray<>(5);
        queueZero = new QueueArray<>(0);
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, queue.size());
        assertEquals(0, queue.getFront());
        assertArrayEquals(new String[5], queue.getBackingArray());

        assertEquals(0, queueZero.size());
        assertEquals(0, queue.getFront());
        assertArrayEquals(new String[1], queueZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t02_EnqueueNoResize() {
        assertThrows(IllegalArgumentException.class, () -> {
            queue.enqueue(null);
        });

        queue.enqueue("B");
        assertEquals(1, queue.size());

        String[] expected = {"B", null, null, null, null};
        assertArrayEquals(expected, queue.getBackingArray());

        queue.enqueue("C");
        assertEquals(2, queue.size());

        expected[1] = "C";
        assertArrayEquals(expected, queue.getBackingArray());

        queue.enqueue("A");
        assertEquals(3, queue.size());

        expected[2] = "A";
        assertArrayEquals(expected, queue.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t03_EnqueueResize() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("E");
        assertEquals(5, queue.size());

        String[] expected = {"A", "B", "C", "D", "E"};
        assertArrayEquals(expected, queue.getBackingArray());

        assertSame("A", queue.dequeue());
        assertEquals(4, queue.size());
        assertEquals(1, queue.getFront());

        expected[0] = null;
        assertArrayEquals(expected, queue.getBackingArray());

        queue.enqueue("F");
        assertEquals(5, queue.size());
        assertEquals(1, queue.getFront());

        expected[0] = "F";
        assertArrayEquals(expected, queue.getBackingArray());

        queue.enqueue("G");
        assertEquals(6, queue.size());
        assertEquals(0, queue.getFront());

        String[] expected2 = {"B", "C", "D", "E", "F", "G", null, null, null, null};
        assertArrayEquals(expected2, queue.getBackingArray());

        queue.enqueue("H");
        assertEquals(7, queue.size());
        assertEquals(0, queue.getFront());

        expected2[6] = "H";
        assertArrayEquals(expected2, queue.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t04_EnqueueZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            queueZero.enqueue(null);
        });

        queueZero.enqueue("A");
        assertEquals(1, queueZero.size());

        String[] expected = {"A"};
        assertArrayEquals(expected, queueZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t05_DequeueInvalid() {
        assertThrows(NoSuchElementException.class, () -> {
            queue.dequeue();
        });

        assertThrows(NoSuchElementException.class, () -> {
            queueZero.dequeue();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t06_Dequeue() {
        String a = new String("A");
        String b = new String("B");

        queue.enqueue("A");
        queue.enqueue(a);
        queue.enqueue(b);
        assertEquals(3, queue.size());

        assertSame("A", queue.dequeue());
        assertEquals(2, queue.size());
        assertEquals(1, queue.getFront());

        String[] expected = {null, a, b, null, null};
        assertArrayEquals(expected, queue.getBackingArray());

        assertSame(a, queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals(2, queue.getFront());

        expected[1] = null;
        assertArrayEquals(expected, queue.getBackingArray());

        assertSame(b, queue.dequeue());
        assertEquals(0, queue.size());
        assertEquals(3, queue.getFront());

        expected[2] = null;
        assertArrayEquals(expected, queue.getBackingArray());

        queue.enqueue("A");
        queue.enqueue(a);
        queue.enqueue(b);
        assertEquals(3, queue.size());

        expected[3] = "A";
        expected[4] = a;
        expected[0] = b;
        assertArrayEquals(expected, queue.getBackingArray());

        assertSame("A", queue.dequeue());
        assertEquals(2, queue.size());
        assertEquals(4, queue.getFront());

        expected[3] = null;
        assertArrayEquals(expected, queue.getBackingArray());

        assertSame(a, queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals(0, queue.getFront());

        expected[4] = null;
        assertArrayEquals(expected, queue.getBackingArray());

        assertSame(b, queue.dequeue());
        assertEquals(0, queue.size());
        assertEquals(1, queue.getFront());

        expected[0] = null;
        assertArrayEquals(expected, queue.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t07_Peek() {
        assertNull(queue.peek());
        assertNull(queueZero.peek());

        String a = new String("A");
        String b = new String("B");

        queue.enqueue("A");
        assertSame("A", queue.peek());

        queue.enqueue(a);
        assertSame("A", queue.peek());

        queue.dequeue();
        assertSame(a, queue.peek());

        queue.enqueue(b);
        assertSame(a, queue.peek());

        queue.dequeue();
        assertSame(b, queue.peek());
    }

    @Test(timeout = TIMEOUT)
    public void t08_IsEmpty() {
        assertTrue(queue.isEmpty());

        queue.enqueue("A");
        assertEquals(1, queue.size());

        assertFalse(queue.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t09_Clear() {
        queue.enqueue("A");
        assertEquals(1, queue.size());

        String[] expected = {"A", null, null, null, null};
        assertArrayEquals(expected, queue.getBackingArray());

        queue.clear();
        assertEquals(0, queue.size());

        expected[0] = null;
        assertArrayEquals(expected, queue.getBackingArray());

        queueZero.enqueue("A");
        queueZero.enqueue("B");
        assertEquals(2, queueZero.size());

        String[] expectedZero = {"A", "B"};
        assertArrayEquals(expectedZero, queueZero.getBackingArray());

        queueZero.clear();
        assertEquals(0, queueZero.size());

        expectedZero[0] = null;
        expectedZero[1] = null;
        assertArrayEquals(expectedZero, queueZero.getBackingArray());
    }
}
