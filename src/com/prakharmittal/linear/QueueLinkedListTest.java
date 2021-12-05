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
public class QueueLinkedListTest {

    private static final int TIMEOUT = 200;
    private QueueLinkedList<String> queue;

    @Before
    public void setUp() {
        queue = new QueueLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, queue.size());
        assertNull(queue.getHead());
        assertNull(queue.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void t02_EnqueueSizeZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            queue.enqueue(null);
        });

        queue.enqueue("A");
        assertEquals(1, queue.size());

        QueueLinkedList.Node<String> cur = queue.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        cur = queue.getTail();
        assertNotNull(cur);
        assertEquals("A", cur.getData());
    }

    @Test(timeout = TIMEOUT)
    public void t03_EnqueueSizeOne() {
        queue.enqueue("A");

        assertThrows(IllegalArgumentException.class, () -> {
            queue.enqueue(null);
        });

        queue.enqueue("B");
        assertEquals(2, queue.size());

        QueueLinkedList.Node<String> cur = queue.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        cur = queue.getTail();
        assertNotNull(cur);
        assertEquals("B", cur.getData());
    }

    @Test(timeout = TIMEOUT)
    public void t04_EnqueueSizeTwo() {
        queue.enqueue("A");
        queue.enqueue("B");

        assertThrows(IllegalArgumentException.class, () -> {
            queue.enqueue(null);
        });

        queue.enqueue("C");
        assertEquals(3, queue.size());

        QueueLinkedList.Node<String> cur = queue.getHead();
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

        cur = queue.getTail();
        assertNotNull(cur);
        assertEquals("C", cur.getData());
    }

    @Test(timeout = TIMEOUT)
    public void t05_DequeueSizeZero() {
        assertThrows(NoSuchElementException.class, () -> {
            queue.dequeue();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t06_DequeueSizeOne() {
        queue.enqueue("A");
        assertEquals(1, queue.size());

        assertSame("A", queue.dequeue());
        assertEquals(0, queue.size());
        assertNull(queue.getHead());
        assertNull(queue.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void t07_DequeueSizeTwo() {
        queue.enqueue("A");
        queue.enqueue("B");
        assertEquals(2, queue.size());

        assertSame("A", queue.dequeue());
        assertEquals(1, queue.size());

        QueueLinkedList.Node<String> cur = queue.getHead();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        cur = queue.getTail();
        assertNotNull(cur);
        assertEquals("B", cur.getData());
    }

    @Test(timeout = TIMEOUT)
    public void t08_Peek() {
        assertNull(queue.peek());

        String a = new String("A");
        String b = new String("B");

        queue.enqueue("A");
        assertSame("A", queue.peek());

        queue.enqueue(a);
        assertSame("A", queue.peek());

        queue.enqueue(b);
        assertSame("A", queue.peek());

        assertSame("A", queue.dequeue());
        assertSame(a, queue.peek());

        assertSame(a, queue.dequeue());
        assertSame(b, queue.peek());

        assertSame(b, queue.dequeue());
        assertNull(queue.peek());
    }

    @Test(timeout = TIMEOUT)
    public void t09_IsEmpty() {
        assertTrue(queue.isEmpty());

        queue.enqueue("A");
        assertEquals(1, queue.size());

        assertFalse(queue.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t10_Clear() {
        queue.enqueue("A");
        assertEquals(1, queue.size());

        queue.clear();
        assertEquals(0, queue.size());
        assertNull(queue.getHead());
        assertNull(queue.getTail());
    }
}
