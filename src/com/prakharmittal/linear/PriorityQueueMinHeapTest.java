package com.prakharmittal.linear;

import com.prakharmittal.list.ArrayList;
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
public class PriorityQueueMinHeapTest {

    private static final int TIMEOUT = 200;
    private PriorityQueueMinHeap<Integer> minHeap;
    private PriorityQueueMinHeap<Integer> minHeapZero;

    @Before
    public void setUp() {
        minHeap = new PriorityQueueMinHeap<>(5);
        minHeapZero = new PriorityQueueMinHeap<>(0);
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, minHeap.size());
        assertArrayEquals(new Integer[5], minHeap.getBackingArray());

        assertEquals(0, minHeapZero.size());
        assertArrayEquals(new Integer[2], minHeapZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t02_ConstructorNullData() {
        assertThrows(IllegalArgumentException.class, () -> {
            minHeap = new PriorityQueueMinHeap<>(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t03_ConstructorValid() {
        ArrayList<Integer> data = new ArrayList<>();
        data.addToBack(5);
        data.addToBack(3);
        data.addToBack(7);
        data.addToBack(9);
        data.addToBack(1);
        data.addToBack(6);
        data.addToBack(8);
        data.addToBack(2);

        minHeap = new PriorityQueueMinHeap<>(data);
        assertEquals(8, minHeap.size());

        Integer[] expected = new Integer[minHeap.getBackingArray().length];
        expected[1] = 1;
        expected[2] = 2;
        expected[3] = 6;
        expected[4] = 5;
        expected[5] = 3;
        expected[6] = 7;
        expected[7] = 8;
        expected[8] = 9;
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t04_AddNullData() {
        assertThrows(IllegalArgumentException.class, () -> {
            minHeap.add(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t05_Add() {
        minHeap.add(4);
        minHeap.add(10);
        minHeap.add(8);
        minHeap.add(3);
        minHeap.add(11);
        minHeap.add(9);
        minHeap.add(5);
        minHeap.add(7);
        minHeap.add(12);
        minHeap.add(6);
        minHeap.add(1);
        minHeap.add(2);
        assertEquals(12, minHeap.size());

        Integer[] expected = new Integer[minHeap.getBackingArray().length];
        expected[1] = 1;
        expected[2] = 3;
        expected[3] = 2;
        expected[4] = 7;
        expected[5] = 4;
        expected[6] = 5;
        expected[7] = 8;
        expected[8] = 10;
        expected[9] = 12;
        expected[10] = 11;
        expected[11] = 6;
        expected[12] = 9;
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t06_RemoveEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            minHeap.remove();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t07_Remove() {
        Integer i = new Integer(150);
        Integer j = new Integer(175);
        Integer k = new Integer(200);

        minHeap.add(300);
        minHeap.add(350);
        minHeap.add(225);
        minHeap.add(275);
        minHeap.add(325);
        minHeap.add(250);
        minHeap.add(i);
        minHeap.add(j);
        minHeap.add(k);
        assertEquals(9, minHeap.size());

        Integer[] expected = new Integer[minHeap.getBackingArray().length];
        expected[1] = 150;
        expected[2] = 175;
        expected[3] = 225;
        expected[4] = 200;
        expected[5] = 325;
        expected[6] = 300;
        expected[7] = 250;
        expected[8] = 350;
        expected[9] = 275;
        assertArrayEquals(expected, minHeap.getBackingArray());

        assertSame(i, minHeap.remove());
        assertEquals(8, minHeap.size());

        expected[1] = 175;
        expected[2] = 200;
        expected[4] = 275;
        expected[9] = null;
        assertArrayEquals(expected, minHeap.getBackingArray());

        assertSame(j, minHeap.remove());
        assertEquals(7, minHeap.size());

        expected[1] = 200;
        expected[2] = 275;
        expected[4] = 350;
        expected[8] = null;
        assertArrayEquals(expected, minHeap.getBackingArray());

        assertSame(k, minHeap.remove());
        assertEquals(6, minHeap.size());

        expected[1] = 225;
        expected[3] = 250;
        expected[4] = 350;
        expected[7] = null;
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t08_Min() {
        assertNull(minHeap.min());

        Integer i = new Integer(200);
        Integer j = new Integer(150);

        minHeap.add(i);
        assertEquals(1, minHeap.size());
        assertSame(i, minHeap.min());

        minHeap.add(j);
        minHeap.add(250);
        assertEquals(3, minHeap.size());
        assertSame(j, minHeap.min());
    }

    @Test(timeout = TIMEOUT)
    public void t09_IsEmpty() {
        assertTrue(minHeap.isEmpty());

        minHeap.add(0);
        assertEquals(1, minHeap.size());

        assertFalse(minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t10_Clear() {
        minHeap.add(2);
        minHeap.add(0);
        minHeap.add(1);
        assertEquals(3, minHeap.size());

        minHeap.clear();
        assertEquals(0, minHeap.size());

        Integer[] expected = new Integer[minHeap.getBackingArray().length];
        assertArrayEquals(expected, minHeap.getBackingArray());
    }
}
