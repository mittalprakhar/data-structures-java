package com.prakharmittal.list;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArrayListTest {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;
    private ArrayList<String> listZero;

    @Before
    public void setUp() {
        list = new ArrayList<>(5);
        listZero = new ArrayList<>(0);
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, list.size());
        assertArrayEquals(new String[5], list.getBackingArray());

        assertEquals(0, listZero.size());
        assertArrayEquals(new String[1], listZero.getBackingArray());
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
    public void t03_AddAtIndexNoResize() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.addAtIndex(0, null);
        });

        list.addAtIndex(0, "B");
        assertEquals(1, list.size());

        String[] expected = {"B", null, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());

        list.addAtIndex(1, "C");
        assertEquals(2, list.size());

        expected[1] = "C";
        assertArrayEquals(expected, list.getBackingArray());

        list.addAtIndex(0, "A");
        assertEquals(3, list.size());

        expected[0] = "A";
        expected[1] = "B";
        expected[2] = "C";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t04_AddAtIndexResize() {
        list.addAtIndex(0, "A");
        list.addAtIndex(1, "B");
        list.addAtIndex(2, "C");
        list.addAtIndex(3, "D");
        list.addAtIndex(4, "E");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addAtIndex(0, null);
        });

        list.addAtIndex(5, "F");
        assertEquals(6, list.size());

        String[] expected = {"A", "B", "C", "D", "E", "F", null, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t05_AddAtIndexZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            listZero.addAtIndex(0, null);
        });

        listZero.addAtIndex(0, "A");
        assertEquals(1, listZero.size());

        String[] expected = {"A"};
        assertArrayEquals(expected, listZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t06_AddToFrontNoResize() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.addToFront(null);
        });

        list.addToFront("B");
        assertEquals(1, list.size());

        String[] expected = {"B", null, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());

        list.addToFront("A");
        assertEquals(2, list.size());

        expected[0] = "A";
        expected[1] = "B";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t07_AddToFrontResize() {
        list.addAtIndex(0, "B");
        list.addAtIndex(1, "C");
        list.addAtIndex(2, "D");
        list.addAtIndex(3, "E");
        list.addAtIndex(4, "F");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addToFront(null);
        });

        list.addToFront("A");
        assertEquals(6, list.size());

        String[] expected = {"A", "B", "C", "D", "E", "F", null, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t08_AddToFrontZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            listZero.addToFront(null);
        });

        listZero.addToFront("A");
        assertEquals(1, listZero.size());

        String[] expected = {"A"};
        assertArrayEquals(expected, listZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t09_AddToBackNoResize() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.addToBack(null);
        });

        list.addToBack("A");
        assertEquals(1, list.size());

        String[] expected = {"A", null, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());

        list.addToBack("B");
        assertEquals(2, list.size());

        expected[1] = "B";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t10_AddToBackResize() {
        list.addAtIndex(0, "A");
        list.addAtIndex(1, "B");
        list.addAtIndex(2, "C");
        list.addAtIndex(3, "D");
        list.addAtIndex(4, "E");

        assertThrows(IllegalArgumentException.class, () -> {
            list.addToBack(null);
        });

        list.addToBack("F");
        assertEquals(6, list.size());

        String[] expected = {"A", "B", "C", "D", "E", "F", null, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t11_AddToBackZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            listZero.addToBack(null);
        });

        listZero.addToBack("A");
        assertEquals(1, listZero.size());

        String[] expected = {"A"};
        assertArrayEquals(expected, listZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t12_RemoveAtIndexInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            listZero.removeAtIndex(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listZero.removeAtIndex(0);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listZero.removeAtIndex(1);
        });

        list.addAtIndex(0, "A");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeAtIndex(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeAtIndex(1);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t13_RemoveAtIndex() {
        String a = new String("A");
        String b = new String("B");

        list.addAtIndex(0, "A");
        list.addAtIndex(1, a);
        list.addAtIndex(2, b);
        assertEquals(3, list.size());

        assertSame("A", list.removeAtIndex(0));
        assertEquals(2, list.size());

        String[] expected = {a, b, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());

        assertSame(b, list.removeAtIndex(1));
        assertEquals(1, list.size());

        expected[0] = a;
        expected[1] = null;
        assertArrayEquals(expected, list.getBackingArray());

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeAtIndex(1);
        });

        assertSame(a, list.removeAtIndex(0));
        assertEquals(0, list.size());

        expected[0] = null;
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t14_RemoveFromFrontInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeFromFront();
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listZero.removeFromFront();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t15_RemoveFromFront() {
        String a = new String("A");
        String b = new String("B");

        list.addAtIndex(0, "A");
        list.addAtIndex(1, a);
        list.addAtIndex(2, b);
        assertEquals(3, list.size());

        assertSame("A", list.removeFromFront());
        assertEquals(2, list.size());

        String[] expected = {a, b, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());

        assertSame(a, list.removeFromFront());
        assertEquals(1, list.size());

        expected[0] = b;
        expected[1] = null;
        assertArrayEquals(expected, list.getBackingArray());

        assertSame(b, list.removeFromFront());
        assertEquals(0, list.size());

        expected[0] = null;
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t16_RemoveFromBackInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeFromBack();
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listZero.removeFromBack();
        });
    }

    @Test(timeout = TIMEOUT)
    public void t17_RemoveFromBack() {
        String a = new String("A");
        String b = new String("B");

        list.addAtIndex(0, "A");
        list.addAtIndex(1, a);
        list.addAtIndex(2, b);
        assertEquals(3, list.size());

        assertSame(b, list.removeFromBack());
        assertEquals(2, list.size());

        String[] expected = {"A", a, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());

        assertSame(a, list.removeFromBack());
        assertEquals(1, list.size());

        expected[1] = null;
        assertArrayEquals(expected, list.getBackingArray());

        assertSame("A", list.removeFromBack());
        assertEquals(0, list.size());

        expected[0] = null;
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t18_Get() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(1);
        });

        String a = new String("A");
        String b = new String("B");

        list.addAtIndex(0, "A");
        list.addAtIndex(1, "B");
        list.addAtIndex(2, "C");
        list.addAtIndex(3, a);
        list.addAtIndex(4, b);
        assertEquals(5, list.size());

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(5);
        });

        assertSame("A", list.get(0));
        assertSame("B", list.get(1));
        assertSame("C", list.get(2));
        assertSame(a, list.get(3));
        assertSame(b, list.get(4));
    }

    @Test(timeout = TIMEOUT)
    public void t19_LastIndexOfZeroSize() {
        assertEquals(-1, list.lastIndexOf("A"));
    }

    @Test(timeout = TIMEOUT)
    public void t20_LastIndexOfFound() {
        list.addAtIndex(0, "A");
        list.addAtIndex(1, "B");
        list.addAtIndex(2, "C");
        list.addAtIndex(3, "B");
        list.addAtIndex(4, "A");

        assertThrows(IllegalArgumentException.class, () -> {
            list.lastIndexOf(null);
        });

        assertEquals(4, list.lastIndexOf("A"));
        assertEquals(3, list.lastIndexOf("B"));
        assertEquals(2, list.lastIndexOf("C"));
    }

    @Test(timeout = TIMEOUT)
    public void t21_LastIndexOfNotFound() {
        list.addAtIndex(0, "A");
        list.addAtIndex(1, "B");
        list.addAtIndex(2, "C");
        list.addAtIndex(3, "B");
        list.addAtIndex(4, "A");

        assertThrows(IllegalArgumentException.class, () -> {
            list.lastIndexOf(null);
        });

        assertEquals(-1, list.lastIndexOf("D"));
    }

    @Test(timeout = TIMEOUT)
    public void t22_IsEmpty() {
        assertTrue(list.isEmpty());

        list.addAtIndex(0, "A");
        assertEquals(1, list.size());

        assertFalse(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t23_Clear() {
        list.addAtIndex(0, "A");
        assertEquals(1, list.size());

        String[] expected = {"A", null, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());

        list.clear();
        assertEquals(0, list.size());

        expected[0] = null;
        assertArrayEquals(expected, list.getBackingArray());

        listZero.addAtIndex(0, "A");
        listZero.addAtIndex(1, "B");
        assertEquals(2, listZero.size());

        String[] expectedZero = {"A", "B"};
        assertArrayEquals(expectedZero, listZero.getBackingArray());

        listZero.clear();
        assertEquals(0, listZero.size());

        expectedZero[0] = null;
        expectedZero[1] = null;
        assertArrayEquals(expectedZero, listZero.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void t24_Iterator() {
        list.addAtIndex(0, "A");
        list.addAtIndex(1, "B");
        list.addAtIndex(2, "C");
        list.addAtIndex(3, "D");
        list.addAtIndex(4, "E");

        int i = 0;
        for (String element: list) {
            assertEquals(element, list.get(i));
            i++;
        }
    }

    @Test(timeout = TIMEOUT)
    public void t25_EqualsNotArrayList() {
        assertNotEquals(5, list);
    }

    @Test(timeout = TIMEOUT)
    public void t26_EqualsSame() {
        assertEquals(list, list);
    }

    @Test(timeout = TIMEOUT)
    public void t27_EqualsEmpty() {
        ArrayList<String> list2 = new ArrayList<>(5);
        assertEquals(list, list2);
        assertEquals(list2, list);
    }

    // TODO: Different length, Unequal, Equal, Parent ?, Different T
    @Test(timeout = TIMEOUT)
    public void t28_Equals() {

    }

    // TODO
    @Test(timeout = TIMEOUT)
    public void t29_ToString() {

    }
}
