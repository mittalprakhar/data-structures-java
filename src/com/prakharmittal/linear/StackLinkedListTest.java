package com.prakharmittal.linear;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StackLinkedListTest {

    private static final int TIMEOUT = 200;
    private StackLinkedList<String> stack;

    @Before
    public void setUp() {
        stack = new StackLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, stack.size());
        assertNull(stack.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void t02_PushSizeZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            stack.push(null);
        });

        stack.push("A");
        assertEquals(1, stack.size());

        StackLinkedList.Node<String> cur = stack.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t03_PushSizeOne() {
        stack.push("B");

        assertThrows(IllegalArgumentException.class, () -> {
            stack.push(null);
        });

        stack.push("A");
        assertEquals(2, stack.size());

        StackLinkedList.Node<String> cur = stack.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t04_PushSizeTwo() {
        stack.push("C");
        stack.push("B");

        assertThrows(IllegalArgumentException.class, () -> {
            stack.push(null);
        });

        stack.push("A");
        assertEquals(3, stack.size());

        StackLinkedList.Node<String> cur = stack.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("C", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t05_PopSizeZero() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t06_PopSizeOne() {
        stack.push("A");
        assertEquals(1, stack.size());

        assertSame("A", stack.pop());
        assertEquals(0, stack.size());
        assertNull(stack.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void t07_RemoveFromFrontSizeTwo() {
        stack.push("B");
        stack.push("A");
        assertEquals(2, stack.size());

        assertSame("A", stack.pop());
        assertEquals(1, stack.size());

        StackLinkedList.Node<String> cur = stack.getHead();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t08_Peek() {
        assertNull(stack.peek());

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
    public void t09_IsEmpty() {
        assertTrue(stack.isEmpty());

        stack.push("A");
        assertEquals(1, stack.size());

        assertFalse(stack.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t10_Clear() {
        stack.push("A");
        assertEquals(1, stack.size());

        stack.clear();
        assertEquals(0, stack.size());
        assertNull(stack.getHead());
    }
}
