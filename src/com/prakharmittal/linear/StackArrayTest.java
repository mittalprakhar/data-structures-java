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
public class StackArrayTest {

    private static final int TIMEOUT = 200;
    private StackArray<String> stack;
    private StackArray<String> stackZero;

    @Before
    public void setUp() {
        stack = new StackArray<>(5);
        stackZero = new StackArray<>(0);
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, stack.size());
        assertArrayEquals(new String[5], stack.getBackingArray());

        assertEquals(0, stackZero.size());
        assertArrayEquals(new String[1], stackZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t02_PushNoResize() {
        assertThrows(IllegalArgumentException.class, () -> {
            stack.push(null);
        });

        stack.push("B");
        assertEquals(1, stack.size());

        String[] expected = {"B", null, null, null, null};
        assertArrayEquals(expected, stack.getBackingArray());

        stack.push("C");
        assertEquals(2, stack.size());

        expected[1] = "C";
        assertArrayEquals(expected, stack.getBackingArray());

        stack.push("A");
        assertEquals(3, stack.size());

        expected[2] = "A";
        assertArrayEquals(expected, stack.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t03_PushResize() {
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");
        stack.push("E");
        stack.push("F");
        assertEquals(6, stack.size());

        String[] expected = {"A", "B", "C", "D", "E", "F", null, null, null, null};
        assertArrayEquals(expected, stack.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t04_PushZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            stackZero.push(null);
        });

        stackZero.push("A");
        assertEquals(1, stackZero.size());

        String[] expected = {"A"};
        assertArrayEquals(expected, stackZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t05_PopInvalid() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });

        assertThrows(NoSuchElementException.class, () -> {
            stackZero.pop();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t06_Pop() {
        String a = new String("A");
        String b = new String("B");

        stack.push("A");
        stack.push(a);
        stack.push(b);
        assertEquals(3, stack.size());

        assertSame(b, stack.pop());
        assertEquals(2, stack.size());

        String[] expected = {"A", a, null, null, null};
        assertArrayEquals(expected, stack.getBackingArray());

        assertSame(a, stack.pop());
        assertEquals(1, stack.size());

        expected[1] = null;
        assertArrayEquals(expected, stack.getBackingArray());

        assertSame("A", stack.pop());
        assertEquals(0, stack.size());

        expected[0] = null;
        assertArrayEquals(expected, stack.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t07_Peek() {
        assertNull(stack.peek());
        assertNull(stackZero.peek());

        String a = new String("A");
        String b = new String("B");

        stack.push("A");
        assertSame("A", stack.peek());

        stack.push(a);
        assertSame(a, stack.peek());

        stack.push(b);
        assertSame(b, stack.peek());
    }

    @Test(timeout = TIMEOUT)
    public void t08_IsEmpty() {
        assertTrue(stack.isEmpty());

        stack.push("A");
        assertEquals(1, stack.size());

        assertFalse(stack.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t09_Clear() {
        stack.push("A");
        assertEquals(1, stack.size());

        String[] expected = {"A", null, null, null, null};
        assertArrayEquals(expected, stack.getBackingArray());

        stack.clear();
        assertEquals(0, stack.size());

        expected[0] = null;
        assertArrayEquals(expected, stack.getBackingArray());

        stackZero.push("A");
        stackZero.push("B");
        assertEquals(2, stackZero.size());

        String[] expectedZero = {"A", "B"};
        assertArrayEquals(expectedZero, stackZero.getBackingArray());

        stackZero.clear();
        assertEquals(0, stackZero.size());

        expectedZero[0] = null;
        expectedZero[1] = null;
        assertArrayEquals(expectedZero, stackZero.getBackingArray());
    }
}
