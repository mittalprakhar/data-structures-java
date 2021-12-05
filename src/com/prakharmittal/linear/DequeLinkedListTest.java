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
public class DequeLinkedListTest {

    private static final int TIMEOUT = 200;
    private DequeLinkedList<String> deque;

    @Before
    public void setUp() {
        deque = new DequeLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, deque.size());
        assertNull(deque.getHead());
        assertNull(deque.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void t02_AddFirstSizeZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            deque.addFirst(null);
        });

        deque.addFirst("A");
        assertEquals(1, deque.size());

        DequeLinkedList.Node<String> cur = deque.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        cur = deque.getTail();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getPrevious();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t03_AddFirstSizeOne() {
        deque.addFirst("B");

        assertThrows(IllegalArgumentException.class, () -> {
            deque.addFirst(null);
        });

        deque.addFirst("A");
        assertEquals(2, deque.size());

        DequeLinkedList.Node<String> cur = deque.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        cur = deque.getTail();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getPrevious();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getPrevious();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t04_AddFirstSizeTwo() {
        deque.addFirst("C");
        deque.addFirst("B");

        assertThrows(IllegalArgumentException.class, () -> {
            deque.addFirst(null);
        });

        deque.addFirst("A");
        assertEquals(3, deque.size());

        DequeLinkedList.Node<String> cur = deque.getHead();
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

        cur = deque.getTail();
        assertNotNull(cur);
        assertEquals("C", cur.getData());

        cur = cur.getPrevious();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getPrevious();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getPrevious();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t05_AddLastSizeZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            deque.addLast(null);
        });

        deque.addLast("A");
        assertEquals(1, deque.size());

        DequeLinkedList.Node<String> cur = deque.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        cur = deque.getTail();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getPrevious();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t06_AddLastSizeOne() {
        deque.addLast("A");

        assertThrows(IllegalArgumentException.class, () -> {
            deque.addLast(null);
        });

        deque.addLast("B");
        assertEquals(2, deque.size());

        DequeLinkedList.Node<String> cur = deque.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        cur = deque.getTail();
        assertNotNull(cur);
        assertEquals("B", cur.getData());
        
        cur = cur.getPrevious();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getPrevious();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t07_AddLastSizeTwo() {
        deque.addLast("A");
        deque.addLast("B");

        assertThrows(IllegalArgumentException.class, () -> {
            deque.addLast(null);
        });

        deque.addLast("C");
        assertEquals(3, deque.size());

        DequeLinkedList.Node<String> cur = deque.getHead();
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

        cur = deque.getTail();
        assertNotNull(cur);
        assertEquals("C", cur.getData());

        cur = cur.getPrevious();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getPrevious();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getPrevious();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t08_RemoveFirstSizeZero() {
        assertThrows(NoSuchElementException.class, () -> {
            deque.removeFirst();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t09_RemoveFirstSizeOne() {
        deque.addLast("A");
        assertEquals(1, deque.size());

        assertSame("A", deque.removeFirst());
        assertEquals(0, deque.size());
        assertNull(deque.getHead());
        assertNull(deque.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void t10_RemoveFirstSizeTwo() {
        deque.addLast("A");
        deque.addLast("B");
        assertEquals(2, deque.size());

        assertSame("A", deque.removeFirst());
        assertEquals(1, deque.size());

        DequeLinkedList.Node<String> cur = deque.getHead();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        cur = deque.getTail();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getPrevious();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t11_RemoveLastSizeZero() {
        assertThrows(NoSuchElementException.class, () -> {
            deque.removeLast();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t12_RemoveLastSizeOne() {
        deque.addFirst("A");
        assertEquals(1, deque.size());

        assertSame("A", deque.removeLast());
        assertEquals(0, deque.size());
        assertNull(deque.getHead());
        assertNull(deque.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void t13_RemoveLastSizeTwo() {
        deque.addFirst("B");
        deque.addFirst("A");
        assertEquals(2, deque.size());

        assertSame("B", deque.removeLast());
        assertEquals(1, deque.size());

        DequeLinkedList.Node<String> cur = deque.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        cur = deque.getTail();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getPrevious();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t14_GetFirst() {
        assertNull(deque.getFirst());

        String a = new String("A");
        String b = new String("B");

        deque.addLast("A");
        assertSame("A", deque.getFirst());

        deque.addLast(a);
        assertSame("A", deque.getFirst());

        deque.addLast(b);
        assertSame("A", deque.getFirst());

        assertSame("A", deque.removeFirst());
        assertSame(a, deque.getFirst());

        assertSame(a, deque.removeFirst());
        assertSame(b, deque.getFirst());

        assertSame(b, deque.removeFirst());
        assertNull(deque.getFirst());
    }

    @Test(timeout = TIMEOUT)
    public void t15_GetLast() {
        assertNull(deque.getLast());

        String a = new String("A");
        String b = new String("B");

        deque.addLast("A");
        assertSame("A", deque.getLast());

        deque.addLast(a);
        assertSame(a, deque.getLast());

        deque.addLast(b);
        assertSame(b, deque.getLast());

        deque.addFirst("B");
        assertSame(b, deque.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void t16_IsEmpty() {
        assertTrue(deque.isEmpty());

        deque.addLast("A");
        assertEquals(1, deque.size());

        assertFalse(deque.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t17_Clear() {
        deque.addLast("A");
        assertEquals(1, deque.size());

        deque.clear();
        assertEquals(0, deque.size());
        assertNull(deque.getHead());
        assertNull(deque.getTail());
    }
}