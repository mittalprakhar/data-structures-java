package com.prakharmittal.hashing;

import com.prakharmittal.list.ArrayList;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LinkedHashMapTest {

    private static final int TIMEOUT = 200;
    private LinkedHashMap<Integer, String> map;
    private LinkedHashMap<Integer, String> mapZero;

    @Before
    public void setUp() {
        map = new LinkedHashMap<>(5);
        mapZero = new LinkedHashMap<>(0);
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new LinkedHashMap.Entry[5], map.getTable());

        assertEquals(0, mapZero.size());
        assertArrayEquals(new LinkedHashMap.Entry[3], mapZero.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t02_PutNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.put(null, "A");
        });
    }

    @Test(timeout = TIMEOUT)
    public void t03_PutNullValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.put(0, null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t04_PutAdd() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "A"));
        assertEquals(3, map.size());

        LinkedHashMap.Entry<Integer, String>[] expected = new LinkedHashMap.Entry[5];
        expected[0] = new LinkedHashMap.Entry<>(55, "A");
        expected[4] = new LinkedHashMap.Entry<>(4, "E");
        assertArrayEquals(expected, map.getTable());

        assertEquals(new LinkedHashMap.Entry<>(0, "A"), map.getTable()[0].getNext());

        assertNull(map.put(1, "B"));
        assertNull(map.put(12, "B"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(23, "B"));
        assertEquals(7, map.size());

        expected = new LinkedHashMap.Entry[11];
        expected[0] = new LinkedHashMap.Entry<>(0, "A");
        expected[1] = new LinkedHashMap.Entry<>(23, "B");
        expected[4] = new LinkedHashMap.Entry<>(4, "E");
        expected[5] = new LinkedHashMap.Entry<>(5, "F");
        assertArrayEquals(expected, map.getTable());

        assertEquals(new LinkedHashMap.Entry<>(55, "A"), map.getTable()[0].getNext());
        assertEquals(new LinkedHashMap.Entry<>(12, "B"), map.getTable()[1].getNext());
        assertEquals(new LinkedHashMap.Entry<>(1, "B"), map.getTable()[1].getNext().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void t05_PutReplace() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(12, "B"));
        assertNull(map.put(23, "B"));
        assertEquals(6, map.size());

        assertEquals("A", map.put(0, "AA"));
        assertEquals("B", map.put(12, "BB"));
        assertEquals("B", map.put(1, "BBB"));
        assertEquals(6, map.size());

        LinkedHashMap.Entry<Integer, String>[] expected = new LinkedHashMap.Entry[11];
        expected[0] = new LinkedHashMap.Entry<>(0, "AA");
        expected[1] = new LinkedHashMap.Entry<>(23, "B");
        expected[4] = new LinkedHashMap.Entry<>(4, "E");
        assertArrayEquals(expected, map.getTable());

        assertEquals(new LinkedHashMap.Entry<>(55, "A"), map.getTable()[0].getNext());
        assertEquals(new LinkedHashMap.Entry<>(12, "BB"), map.getTable()[1].getNext());
        assertEquals(new LinkedHashMap.Entry<>(1, "BBB"), map.getTable()[1].getNext().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void t06_RemoveNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.remove(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t07_RemoveNullEntry() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        assertThrows(NoSuchElementException.class, () -> {
            map.remove(1);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t08_RemoveNotFound() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(5, "A"));
        assertNull(map.put(10, "A"));
        assertEquals(3, map.size());

        assertThrows(NoSuchElementException.class, () -> {
            map.remove(15);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t09_RemoveFirstNode() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "BB"));
        assertNull(map.put(6, "B"));
        assertEquals(3, map.size());

        assertEquals("B", map.remove(6));

        LinkedHashMap.Entry<Integer, String>[] expected = new LinkedHashMap.Entry[5];
        expected[0] = new LinkedHashMap.Entry<>(0, "A");
        expected[1] = new LinkedHashMap.Entry<>(1, "BB");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t10_RemoveMiddleNode() {
        assertNull(map.put(1, "BBB"));
        assertNull(map.put(6, "BB"));
        assertNull(map.put(11, "B"));
        assertEquals(3, map.size());

        assertEquals("BB", map.remove(6));

        LinkedHashMap.Entry<Integer, String>[] expected = new LinkedHashMap.Entry[5];
        expected[1] = new LinkedHashMap.Entry<>(11, "B");
        assertArrayEquals(expected, map.getTable());

        assertEquals(new LinkedHashMap.Entry<>(1, "BBB"), map.getTable()[1].getNext());

        assertEquals("BBB", map.remove(1));

        assertArrayEquals(expected, map.getTable());

        assertNull(map.getTable()[1].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void t11_GetNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.get(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t12_GetNullEntry() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        assertThrows(NoSuchElementException.class, () -> {
            map.get(1);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t13_GetNotFound() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(5, "A"));
        assertNull(map.put(10, "A"));
        assertEquals(3, map.size());

        assertThrows(NoSuchElementException.class, () -> {
            map.get(15);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t14_GetFound() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(1, "BBB"));
        assertNull(map.put(12, "BB"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(23, "B"));
        assertEquals(7, map.size());

        assertEquals("A", map.get(0));
        assertEquals("E", map.get(4));
        assertEquals("AA", map.get(55));
        assertEquals("BBB", map.get(1));
        assertEquals("BB", map.get(12));
        assertEquals("F", map.get(5));
        assertEquals("B", map.get(23));
    }

    @Test(timeout = TIMEOUT)
    public void t15_ContainsNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.contains(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t16_Contains() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(1, "BBB"));
        assertNull(map.put(12, "BB"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(23, "B"));
        assertEquals(7, map.size());

        assertTrue(map.contains(0));
        assertTrue(map.contains(4));
        assertTrue(map.contains(55));
        assertTrue(map.contains(1));
        assertTrue(map.contains(12));
        assertTrue(map.contains(5));
        assertTrue(map.contains(23));

        assertFalse(map.contains(11));
        assertFalse(map.contains(34));
        assertFalse(map.contains(2));
        assertFalse(map.contains(10));
    }

    @Test(timeout = TIMEOUT)
    public void t17_Entries() {
        assertEquals(new LinkedHashSet<LinkedHashMap.Entry<Integer, String>>(0), map.entries());

        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(1, "BBB"));
        assertNull(map.put(12, "BB"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(23, "B"));
        assertEquals(7, map.size());

        LinkedHashSet<LinkedHashMap.Entry<Integer, String>> expected = new LinkedHashSet<>(7);
        expected.add(new LinkedHashMap.Entry<>(0, "A"));
        expected.add(new LinkedHashMap.Entry<>(55, "AA"));
        expected.add(new LinkedHashMap.Entry<>(23, "B"));
        expected.add(new LinkedHashMap.Entry<>(12, "BB"));
        expected.add(new LinkedHashMap.Entry<>(1, "BBB"));
        expected.add(new LinkedHashMap.Entry<>(4, "E"));
        expected.add(new LinkedHashMap.Entry<>(5, "F"));

        assertEquals(expected, map.entries());
    }

    @Test(timeout = TIMEOUT)
    public void t18_Keys() {
        assertEquals(new LinkedHashSet<Integer>(0), map.keys());

        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(1, "BBB"));
        assertNull(map.put(12, "BB"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(23, "B"));
        assertEquals(7, map.size());

        LinkedHashSet<Integer> expected = new LinkedHashSet<>(7);
        expected.add(0);
        expected.add(55);
        expected.add(23);
        expected.add(12);
        expected.add(1);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, map.keys());
    }

    @Test(timeout = TIMEOUT)
    public void t19_Values() {
        assertEquals(new ArrayList<String>(0), map.values());

        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(1, "BBB"));
        assertNull(map.put(12, "BB"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(23, "B"));
        assertEquals(7, map.size());

        ArrayList<String> expected = new ArrayList<>(7);
        expected.addToBack("A");
        expected.addToBack("AA");
        expected.addToBack("B");
        expected.addToBack("BB");
        expected.addToBack("BBB");
        expected.addToBack("E");
        expected.addToBack("F");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void t20_ResizeIllegalLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.resizeBackingTable(-1);
        });

        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "A"));
        assertEquals(3, map.size());

        assertThrows(IllegalArgumentException.class, () -> {
            map.resizeBackingTable(2);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t21_ResizeZeroLength() {
        map.resizeBackingTable(0);

        LinkedHashMap.Entry<Integer, String>[] expected = new LinkedHashMap.Entry[0];
        assertArrayEquals(expected, map.getTable());

        assertNull(map.put(0, "A"));

        expected = new LinkedHashMap.Entry[1];
        expected[0] = new LinkedHashMap.Entry<>(0, "A");
        assertArrayEquals(expected, map.getTable());

        assertNull(map.put(4, "E"));

        expected = new LinkedHashMap.Entry[3];
        expected[0] = new LinkedHashMap.Entry<>(0, "A");
        expected[1] = new LinkedHashMap.Entry<>(4, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t22_ResizeDown() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(12, "B"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(23, "B"));
        assertEquals(7, map.size());

        map.resizeBackingTable(7);

        LinkedHashMap.Entry<Integer, String>[] expected = new LinkedHashMap.Entry[7];
        expected[0] = new LinkedHashMap.Entry<>(0, "A");
        expected[1] = new LinkedHashMap.Entry<>(1, "B");
        expected[2] = new LinkedHashMap.Entry<>(23, "B");
        expected[4] = new LinkedHashMap.Entry<>(4, "E");
        expected[5] = new LinkedHashMap.Entry<>(5, "F");
        expected[6] = new LinkedHashMap.Entry<>(55, "A");
        assertArrayEquals(expected, map.getTable());

        assertEquals(new LinkedHashMap.Entry<>(12, "B"), map.getTable()[5].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void t23_IsEmpty() {
        assertTrue(map.isEmpty());

        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        assertFalse(map.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t24_Clear() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        LinkedHashMap.Entry<Integer, String>[] expected = new LinkedHashMap.Entry[5];
        expected[0] = new LinkedHashMap.Entry<>(0, "A");
        assertArrayEquals(expected, map.getTable());

        map.clear();
        assertEquals(0, map.size());

        expected[0] = null;
        assertArrayEquals(expected, map.getTable());

        assertNull(mapZero.put(0, "A"));
        assertNull(mapZero.put(1, "B"));
        assertNull(mapZero.put(2, "C"));
        assertEquals(3, mapZero.size());

        LinkedHashMap.Entry<Integer, String>[] expectedZero = new LinkedHashMap.Entry[7];
        expectedZero[0] = new LinkedHashMap.Entry<>(0, "A");
        expectedZero[1] = new LinkedHashMap.Entry<>(1, "B");
        expectedZero[2] = new LinkedHashMap.Entry<>(2, "C");
        assertArrayEquals(expectedZero, mapZero.getTable());

        mapZero.clear();
        assertEquals(0, mapZero.size());

        expectedZero[0] = null;
        expectedZero[1] = null;
        expectedZero[2] = null;
        assertArrayEquals(expectedZero, mapZero.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t25_Iterator() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(1, "BBB"));
        assertNull(map.put(12, "BB"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(23, "B"));
        assertEquals(7, map.size());

        LinkedHashSet<LinkedHashMap.Entry<Integer, String>> actual = new LinkedHashSet<>();
        for (LinkedHashMap.Entry<Integer, String> entry: map) {
            actual.add(entry);
        }
        assertEquals(map.entries(), actual);
    }

    @Test(timeout = TIMEOUT)
    public void t26_EqualsNotHashMap() {
        assertNotEquals(5, map);
    }

    @Test(timeout = TIMEOUT)
    public void t27_EqualsSame() {
        assertEquals(map, map);
    }

    @Test(timeout = TIMEOUT)
    public void t28_EqualsEmpty() {
        assertEquals(map, mapZero);
    }

    @Test(timeout = TIMEOUT)
    public void t29_EqualsDifferentSize() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(5, "A"));
        assertEquals(2, map.size());

        assertNull(mapZero.put(0, "A"));
        assertEquals(1, mapZero.size());

        assertNotEquals(map, mapZero);
    }

    @Test(timeout = TIMEOUT)
    public void t30_EqualsDifferentKey() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        assertNull(mapZero.put(1, "A"));
        assertEquals(1, mapZero.size());

        assertNotEquals(map, mapZero);
    }

    @Test(timeout = TIMEOUT)
    public void t31_EqualsDifferentValue() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        assertNull(mapZero.put(0, "B"));
        assertEquals(1, mapZero.size());

        assertNotEquals(map, mapZero);
    }

    @Test(timeout = TIMEOUT)
    public void t32_EqualsDifferentT() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        LinkedHashMap<String, Integer> map2 = new LinkedHashMap<>();
        assertNull(map2.put("A", 0));
        assertEquals(1, map.size());

        assertNotEquals(map, map2);

        LinkedHashMap<Object, String> map3 = new LinkedHashMap<>();
        assertNull(map3.put(0, "A"));
        assertEquals(1, map3.size());

        assertEquals(map, map3);

        assertNull(map.put(1, "B"));
        assertEquals(2, map.size());

        assertNull(map3.put("B", "C"));
        assertEquals(2, map3.size());

        assertNotEquals(map, map3);
    }

    @Test(timeout = TIMEOUT)
    public void t33_Equals() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(1, "BBB"));
        assertNull(map.put(12, "BB"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(23, "B"));
        assertEquals(7, map.size());

        assertNull(mapZero.put(0, "A"));
        assertNull(mapZero.put(4, "E"));
        assertNull(mapZero.put(55, "AA"));
        assertNull(mapZero.put(1, "BBB"));
        assertNull(mapZero.put(12, "BB"));
        assertNull(mapZero.put(5, "F"));
        assertNull(mapZero.put(23, "B"));
        assertEquals(7, mapZero.size());

        assertEquals(map, mapZero);
    }

    @Test(timeout = TIMEOUT)
    public void t34_HashCode() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(1, "BBB"));
        assertNull(map.put(12, "BB"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(23, "B"));
        assertEquals(7, map.size());

        assertNull(mapZero.put(0, "A"));
        assertNull(mapZero.put(4, "E"));
        assertNull(mapZero.put(55, "AA"));
        assertNull(mapZero.put(1, "BBB"));
        assertNull(mapZero.put(12, "BB"));
        assertNull(mapZero.put(5, "F"));
        assertNull(mapZero.put(23, "B"));
        assertEquals(7, mapZero.size());

        assertEquals(map.hashCode(), mapZero.hashCode());
    }
}
