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
public class PriorityQueueMaxHeapTest {

    private static final int TIMEOUT = 200;
    private PriorityQueueMaxHeap<Integer> maxHeap;
    private PriorityQueueMaxHeap<Integer> maxHeapZero;

    @Before
    public void setUp() {
        maxHeap = new PriorityQueueMaxHeap<>(5);
        maxHeapZero = new PriorityQueueMaxHeap<>(0);
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, maxHeap.size());
        assertArrayEquals(new Integer[5], maxHeap.getBackingArray());

        assertEquals(0, maxHeapZero.size());
        assertArrayEquals(new Integer[2], maxHeapZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t02_ConstructorNullData() {
        assertThrows(IllegalArgumentException.class, () -> {
            maxHeap = new PriorityQueueMaxHeap<>(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t03_ConstructorValid() {
        ArrayList<Integer> data = new ArrayList<>();
        data.addToBack(6);
        data.addToBack(8);
        data.addToBack(4);
        data.addToBack(2);
        data.addToBack(10);
        data.addToBack(5);
        data.addToBack(3);
        data.addToBack(9);
        
        maxHeap = new PriorityQueueMaxHeap<>(data);
        assertEquals(8, maxHeap.size());

        Integer[] expected = new Integer[maxHeap.getBackingArray().length];
        expected[1] = 10;
        expected[2] = 9;
        expected[3] = 5;
        expected[4] = 6;
        expected[5] = 8;
        expected[6] = 4;
        expected[7] = 3;
        expected[8] = 2;
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t04_AddNullData() {
        assertThrows(IllegalArgumentException.class, () -> {
            maxHeap.add(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t05_Add() {
        maxHeap.add(9);
        maxHeap.add(3);
        maxHeap.add(5);
        maxHeap.add(10);
        maxHeap.add(2);
        maxHeap.add(4);
        maxHeap.add(8);
        maxHeap.add(6);
        maxHeap.add(1);
        maxHeap.add(7);
        maxHeap.add(12);
        maxHeap.add(11);
        assertEquals(12, maxHeap.size());

        Integer[] expected = new Integer[maxHeap.getBackingArray().length];
        expected[1] = 12;
        expected[2] = 10;
        expected[3] = 11;
        expected[4] = 6;
        expected[5] = 9;
        expected[6] = 8;
        expected[7] = 5;
        expected[8] = 3;
        expected[9] = 1;
        expected[10] = 2;
        expected[11] = 7;
        expected[12] = 4;
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t06_RemoveEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            maxHeap.remove();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t07_Remove() {
        Integer i = new Integer(250);
        Integer j = new Integer(225);
        Integer k = new Integer(200);

        maxHeap.add(100);
        maxHeap.add(50);
        maxHeap.add(175);
        maxHeap.add(125);
        maxHeap.add(75);
        maxHeap.add(150);
        maxHeap.add(i);
        maxHeap.add(j);
        maxHeap.add(k);
        assertEquals(9, maxHeap.size());

        Integer[] expected = new Integer[maxHeap.getBackingArray().length];
        expected[1] = 250;
        expected[2] = 225;
        expected[3] = 175;
        expected[4] = 200;
        expected[5] = 75;
        expected[6] = 100;
        expected[7] = 150;
        expected[8] = 50;
        expected[9] = 125;
        assertArrayEquals(expected, maxHeap.getBackingArray());

        assertSame(i, maxHeap.remove());
        assertEquals(8, maxHeap.size());

        expected[1] = 225;
        expected[2] = 200;
        expected[4] = 125;
        expected[9] = null;
        assertArrayEquals(expected, maxHeap.getBackingArray());

        assertSame(j, maxHeap.remove());
        assertEquals(7, maxHeap.size());

        expected[1] = 200;
        expected[2] = 125;
        expected[4] = 50;
        expected[8] = null;
        assertArrayEquals(expected, maxHeap.getBackingArray());

        assertSame(k, maxHeap.remove());
        assertEquals(6, maxHeap.size());

        expected[1] = 175;
        expected[3] = 150;
        expected[4] = 50;
        expected[7] = null;
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t08_Max() {
        assertNull(maxHeap.max());

        Integer i = new Integer(200);
        Integer j = new Integer(250);

        maxHeap.add(i);
        assertEquals(1, maxHeap.size());
        assertSame(i, maxHeap.max());

        maxHeap.add(j);
        maxHeap.add(150);
        assertEquals(3, maxHeap.size());
        assertSame(j, maxHeap.max());
    }

    @Test(timeout = TIMEOUT)
    public void t09_IsEmpty() {
        assertTrue(maxHeap.isEmpty());

        maxHeap.add(0);
        assertEquals(1, maxHeap.size());

        assertFalse(maxHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t10_Clear() {
        maxHeap.add(2);
        maxHeap.add(0);
        maxHeap.add(1);
        assertEquals(3, maxHeap.size());

        maxHeap.clear();
        assertEquals(0, maxHeap.size());

        Integer[] expected = new Integer[maxHeap.getBackingArray().length];
        assertArrayEquals(expected, maxHeap.getBackingArray());
    }
}
