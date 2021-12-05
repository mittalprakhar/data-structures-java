package com.prakharmittal.list;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SinglyLinkedListTest {

    private static final int TIMEOUT = 200;
    private SinglyLinkedList<String> list;

    @Before
    public void setUp() {
        list = new SinglyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, list.size());
        assertNull(list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void t02_AddAtIndexInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.addAtIndex(-1, "A");
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.addAtIndex(1, "A");
        });
    }

    @Test(timeout = TIMEOUT)
    public void t03_AddAtIndexSizeZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.addAtIndex(0, null);
        });

        list.addAtIndex(0, "A");
        assertEquals(1, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t04_AddAtIndexSizeOneIndexZero() {
        list.addAtIndex(0, "B");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addAtIndex(0, null);
        });

        list.addAtIndex(0, "A");
        assertEquals(2, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t05_AddAtIndexSizeOneIndexOne() {
        list.addAtIndex(0, "A");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addAtIndex(1, null);
        });

        list.addAtIndex(1, "B");
        assertEquals(2, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t06_AddAtIndexSizeTwoIndexZero() {
        list.addAtIndex(0, "B");
        list.addAtIndex(1, "C");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addAtIndex(0, null);
        });

        list.addAtIndex(0, "A");
        assertEquals(3, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
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
    public void t07_AddAtIndexSizeTwoIndexOne() {
        list.addAtIndex(0, "A");
        list.addAtIndex(1, "C");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addAtIndex(1, null);
        });

        list.addAtIndex(1, "B");
        assertEquals(3, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
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
    public void t08_AddAtIndexSizeTwoIndexTwo() {
        list.addAtIndex(0, "A");
        list.addAtIndex(1, "B");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addAtIndex(2, null);
        });

        list.addAtIndex(2, "C");
        assertEquals(3, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
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
    public void t09_AddToFrontSizeZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.addToFront(null);
        });

        list.addToFront("A");
        assertEquals(1, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t10_AddToFrontSizeOne() {
        list.addToFront("B");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addToFront(null);
        });

        list.addToFront("A");
        assertEquals(2, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t11_AddToFrontSizeTwo() {
        list.addToFront("C");
        list.addToFront("B");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addToFront(null);
        });

        list.addToFront("A");
        assertEquals(3, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
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
    public void t12_AddToBackSizeZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.addToBack(null);
        });

        list.addToBack("A");
        assertEquals(1, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t13_AddToBackSizeOne() {
        list.addToBack("A");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addToBack(null);
        });

        list.addToBack("B");
        assertEquals(2, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t14_AddToBackSizeTwo() {
        list.addToBack("A");
        list.addToBack("B");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addToBack(null);
        });

        list.addToBack("C");
        assertEquals(3, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
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
    public void t15_RemoveAtIndexSizeZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeAtIndex(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeAtIndex(0);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeAtIndex(1);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t16_RemoveAtIndexSizeOne() {
        list.addToFront("A");
        assertEquals(1, list.size());

        assertSame("A", list.removeAtIndex(0));
        assertEquals(0, list.size());
        assertNull(list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void t17_RemoveAtIndexSizeTwoIndexZero() {
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(2, list.size());

        assertSame("A", list.removeAtIndex(0));
        assertEquals(1, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t18_RemoveAtIndexSizeTwoIndexOne() {
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(2, list.size());

        assertSame("B", list.removeAtIndex(1));
        assertEquals(1, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t19_RemoveAtIndexSizeThreeIndexOne() {
        list.addToFront("C");
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(3, list.size());

        assertSame("B", list.removeAtIndex(1));
        assertEquals(2, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("C", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t20_RemoveFromFrontSizeZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeFromFront();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t21_RemoveFromFrontSizeOne() {
        list.addToFront("A");
        assertEquals(1, list.size());

        assertSame("A", list.removeFromFront());
        assertEquals(0, list.size());
        assertNull(list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void t22_RemoveFromFrontSizeTwo() {
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(2, list.size());

        assertSame("A", list.removeFromFront());
        assertEquals(1, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t23_RemoveFromBackSizeZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeFromBack();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t24_RemoveFromBackSizeOne() {
        list.addToFront("A");
        assertEquals(1, list.size());

        assertSame("A", list.removeFromBack());
        assertEquals(0, list.size());
        assertNull(list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void t25_RemoveFromBackSizeTwo() {
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(2, list.size());

        assertSame("B", list.removeFromBack());
        assertEquals(1, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t26_RemoveLastOccurrenceNullData() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.removeLastOccurrence(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t27_RemoveLastOccurrenceSizeZero() {
        assertThrows(NoSuchElementException.class, () -> {
            list.removeLastOccurrence("A");
        });
    }

    @Test(timeout = TIMEOUT)
    public void t28_RemoveLastOccurrenceSizeOneFound() {
        list.addToFront("A");
        assertEquals(1, list.size());

        assertSame("A", list.removeLastOccurrence("A"));
        assertEquals(0, list.size());
        assertNull(list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void t29_RemoveLastOccurrenceSizeOneNotFound() {
        list.addToFront("A");
        assertEquals(1, list.size());

        assertThrows(NoSuchElementException.class, () -> {
            list.removeLastOccurrence("B");
        });

        assertEquals(1, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t30_RemoveLastOccurrenceSizeTwoFoundIndexOne() {
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(2, list.size());

        assertSame("A", list.removeLastOccurrence("A"));
        assertEquals(1, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t31_RemoveLastOccurrenceSizeTwoFoundIndexTwo() {
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(2, list.size());

        assertSame("B", list.removeLastOccurrence("B"));
        assertEquals(1, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t32_RemoveLastOccurrenceSizeTwoNotFound() {
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(2, list.size());

        assertThrows(NoSuchElementException.class, () -> {
            list.removeLastOccurrence("C");
        });

        assertEquals(2, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t33_RemoveLastOccurrenceSizeFiveWithDuplicates() {
        String a = new String("A");
        String b = new String("B");

        list.addToFront("A");
        list.addToFront("B");
        list.addToFront(a);
        list.addToFront(b);
        list.addToFront("A");
        assertEquals(5, list.size());

        assertSame("A", list.removeLastOccurrence("A"));
        assertEquals(4, list.size());

        SinglyLinkedList.Node<String> cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        assertSame(a, list.removeLastOccurrence("A"));
        assertEquals(3, list.size());

        cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        assertSame("B", list.removeLastOccurrence("B"));
        assertEquals(2, list.size());

        cur = list.getHead();
        assertNotNull(cur);
        assertEquals("A", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);

        assertSame("A", list.removeLastOccurrence("A"));
        assertEquals(1, list.size());

        cur = list.getHead();
        assertNotNull(cur);
        assertEquals("B", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void t34_GetInvalidIndex() {
        list.addToFront("A");
        assertEquals(1, list.size());

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(1);
        });

    }

    @Test(timeout = TIMEOUT)
    public void t35_GetSizeZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(1);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t36_GetSizeOne() {
        list.addToFront("A");
        assertEquals(1, list.size());

        assertEquals("A", list.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void t37_GetSizeTwoIndexZero() {
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(2, list.size());

        assertEquals("A", list.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void t38_GetSizeTwoIndexOne() {
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(2, list.size());

        assertEquals("B", list.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void t39_GetSizeThreeIndexTwo() {
        list.addToFront("C");
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(3, list.size());

        assertEquals("C", list.get(2));
    }

    @Test(timeout = TIMEOUT)
    public void t40_ToArraySizeZero() {
        String[] expected = {};
        assertArrayEquals(expected, list.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void t41_ToArraySizeOne() {
        list.addToFront("A");
        assertEquals(1, list.size());

        String[] expected = {"A"};
        assertArrayEquals(expected, list.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void t42_ToArraySizeFive() {
        list.addToFront("E");
        list.addToFront("D");
        list.addToFront("C");
        list.addToFront("B");
        list.addToFront("A");
        assertEquals(5, list.size());

        String[] expected = {"A", "B", "C", "D", "E"};
        assertArrayEquals(expected, list.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void t43_IsEmpty() {
        assertTrue(list.isEmpty());

        list.addToFront("A");
        assertEquals(1, list.size());

        assertFalse(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t44_Clear() {
        list.addToFront("A");
        assertEquals(1, list.size());

        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void t45_Iterator() {
        list.addToFront("E");
        list.addToFront("D");
        list.addToFront("C");
        list.addToFront("B");
        list.addToFront("A");

        int i = 0;
        for (String element: list) {
            assertEquals(element, list.get(i));
            i++;
        }
    }

    @Test(timeout = TIMEOUT)
    public void t46_EqualsNotSLL() {
        assertNotEquals(5, list);
    }

    @Test(timeout = TIMEOUT)
    public void t47_EqualsSame() {
        assertEquals(list, list);
    }

    @Test(timeout = TIMEOUT)
    public void t48_EqualsEmpty() {
        SinglyLinkedList<String> list2 = new SinglyLinkedList<>();
        assertEquals(list, list2);
        assertEquals(list2, list);
    }

    // TODO: Different length, Unequal, Equal, Parent ?, Different T
    @Test(timeout = TIMEOUT)
    public void t49_Equals() {

    }

    // TODO
    @Test(timeout = TIMEOUT)
    public void t50_ToString() {

    }
}
