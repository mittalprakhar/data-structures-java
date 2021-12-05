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
public class HashMapTest {

    private static final int TIMEOUT = 200;
    private HashMap<Integer, String> map;
    private HashMap<Integer, String> mapZero;

    @Before
    public void setUp() {
        map = new HashMap<>(5);
        mapZero = new HashMap<>(0);
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new HashMap.Entry[5], map.getTable());

        assertEquals(0, mapZero.size());
        assertArrayEquals(new HashMap.Entry[3], mapZero.getTable());
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
        assertNull(map.put(55, "AA"));
        assertEquals(3, map.size());

        HashMap.Entry<Integer, String>[] expected = new HashMap.Entry[5];
        expected[0] = new HashMap.Entry<>(0, "A");
        expected[1] = new HashMap.Entry<>(55, "AA");
        expected[4] = new HashMap.Entry<>(4, "E");
        assertArrayEquals(expected, map.getTable());

        assertNull(map.put(2, "C"));
        assertNull(map.put(10, "K"));
        assertNull(map.put(13, "AAA"));
        assertNull(map.put(21, "KK"));
        assertEquals(7, map.size());

        expected = new HashMap.Entry[11];
        expected[0] = new HashMap.Entry<>(0, "A");
        expected[1] = new HashMap.Entry<>(55, "AA");
        expected[2] = new HashMap.Entry<>(2, "C");
        expected[3] = new HashMap.Entry<>(13, "AAA");
        expected[4] = new HashMap.Entry<>(4, "E");
        expected[5] = new HashMap.Entry<>(21, "KK");
        expected[10] = new HashMap.Entry<>(10, "K");
        assertArrayEquals(expected, map.getTable());

        assertEquals("AAA", map.remove(13));
        assertEquals(6, map.size());
        assertTrue(map.getTable()[3].getRemoved());

        assertNull(map.put(32, "KKK"));

        expected[3] = new HashMap.Entry<>(32, "KKK");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t05_PutReplace() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(55, "A"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(10, "K"));
        assertNull(map.put(21, "K"));
        assertNull(map.put(11, "A"));
        assertEquals(6, map.size());

        assertEquals("A", map.put(0, "AA"));
        assertEquals("A", map.put(11, "AAA"));
        assertEquals("K", map.put(21, "KK"));
        assertEquals(6, map.size());

        HashMap.Entry<Integer, String>[] expected = new HashMap.Entry[11];
        expected[0] = new HashMap.Entry<>(0, "AA");
        expected[1] = new HashMap.Entry<>(55, "A");
        expected[2] = new HashMap.Entry<>(2, "C");
        expected[3] = new HashMap.Entry<>(21, "KK");
        expected[4] = new HashMap.Entry<>(11, "AAA");
        expected[10] = new HashMap.Entry<>(10, "K");
        assertArrayEquals(expected, map.getTable());

        assertEquals("C", map.remove(2));
        assertEquals(5, map.size());
        assertTrue(map.getTable()[2].getRemoved());

        assertEquals("KK", map.put(21, "KKK"));

        expected[3] = new HashMap.Entry<>(21, "KKK");
        assertArrayEquals(expected, map.getTable());

        assertNull(map.put(32, "K"));

        expected[2] = new HashMap.Entry<>(32, "K");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t06_PutFull() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(6, "A"));
        assertNull(map.put(7, "B"));
        assertNull(map.put(13, "B"));
        assertNull(map.put(14, "C"));
        assertNull(map.put(20, "C"));
        assertEquals(6, map.size());

        map.resizeBackingTable(6);

        HashMap.Entry<Integer, String>[] expected = new HashMap.Entry[6];
        expected[0] = new HashMap.Entry<>(0, "A");
        expected[1] = new HashMap.Entry<>(13, "B");
        expected[2] = new HashMap.Entry<>(14, "C");
        expected[3] = new HashMap.Entry<>(6, "A");
        expected[4] = new HashMap.Entry<>(7, "B");
        expected[5] = new HashMap.Entry<>(20, "C");
        assertArrayEquals(expected, map.getTable());

        assertEquals("B", map.remove(7));
        assertEquals("B", map.remove(13));
        assertEquals("C", map.remove(14));
        assertEquals(3, map.size());
        assertTrue(map.getTable()[1].getRemoved());
        assertTrue(map.getTable()[2].getRemoved());
        assertTrue(map.getTable()[4].getRemoved());

        assertEquals("C", map.put(20, "CC"));

        expected[5] = new HashMap.Entry<>(20, "CC");
        assertArrayEquals(expected, map.getTable());

        assertNull(map.put(12, "A"));

        expected[1] = new HashMap.Entry<>(12, "A");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t07_RemoveNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.remove(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t08_RemoveNotFound() {
        assertNull(map.put(4, "EE"));
        assertNull(map.put(9, "E"));
        assertNull(map.put(1, "B"));
        assertEquals(3, map.size());

        assertThrows(NoSuchElementException.class, () -> {
            map.remove(2);
        });

        assertThrows(NoSuchElementException.class, () -> {
            map.remove(14);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t09_RemoveNotFoundEfficient() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        map.getTable()[2] = new HashMap.Entry<>(1, "After Null Illegal");

        assertThrows(NoSuchElementException.class, () -> {
            map.remove(1);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t10_RemoveFound() {
        assertNull(map.put(4, "E"));
        assertNull(map.put(9, "EE"));
        assertNull(map.put(14, "EEE"));
        assertEquals(3, map.size());

        assertEquals("EE", map.remove(9));
        assertEquals(2, map.size());
        assertTrue(map.getTable()[0].getRemoved());

        assertEquals("EEE", map.remove(14));
        assertEquals(1, map.size());
        assertTrue(map.getTable()[1].getRemoved());
    }

    @Test(timeout = TIMEOUT)
    public void t11_RemoveFull() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(15, "AA"));
        assertNull(map.put(6, "B"));
        assertEquals(3, map.size());

        map.resizeBackingTable(3);

        HashMap.Entry<Integer, String>[] expected = new HashMap.Entry[3];
        expected[0] = new HashMap.Entry<>(0, "A");
        expected[1] = new HashMap.Entry<>(15, "AA");
        expected[2] = new HashMap.Entry<>(6, "B");
        assertArrayEquals(expected, map.getTable());

        assertThrows(NoSuchElementException.class, () -> {
            map.remove(7);
        });

        assertEquals("AA", map.remove(15));
        assertEquals(2, map.size());
        assertTrue(map.getTable()[1].getRemoved());

        assertEquals("B", map.remove(6));
        assertEquals(1, map.size());
        assertTrue(map.getTable()[1].getRemoved());
    }

    @Test(timeout = TIMEOUT)
    public void t12_GetNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.get(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t13_GetNullEntry() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        map.getTable()[2] = new HashMap.Entry<>(1, "After Null Illegal");

        assertThrows(NoSuchElementException.class, () -> {
            map.get(1);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t14_GetNotFound() {
        assertNull(map.put(4, "EE"));
        assertNull(map.put(9, "E"));
        assertNull(map.put(1, "B"));
        assertEquals(3, map.size());

        assertThrows(NoSuchElementException.class, () -> {
            map.get(2);
        });

        assertThrows(NoSuchElementException.class, () -> {
            map.get(14);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t15_GetFound() {
        assertNull(map.put(4, "EE"));
        assertNull(map.put(9, "E"));
        assertNull(map.put(14, "EEE"));
        assertEquals(3, map.size());

        assertEquals("EE", map.get(4));
        assertEquals("E", map.get(9));
        assertEquals("EEE", map.get(14));

        assertEquals("E", map.remove(9));
        assertEquals(2, map.size());
        assertTrue(map.getTable()[0].getRemoved());

        assertEquals("EEE", map.get(14));

        map.resizeBackingTable(2);

        assertEquals("EE", map.get(4));
        assertEquals("EEE", map.get(14));
    }

    @Test(timeout = TIMEOUT)
    public void t16_GetFull() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(15, "AA"));
        assertNull(map.put(6, "B"));
        assertEquals(3, map.size());

        map.resizeBackingTable(3);

        HashMap.Entry<Integer, String>[] expected = new HashMap.Entry[3];
        expected[0] = new HashMap.Entry<>(0, "A");
        expected[1] = new HashMap.Entry<>(15, "AA");
        expected[2] = new HashMap.Entry<>(6, "B");
        assertArrayEquals(expected, map.getTable());

        assertThrows(NoSuchElementException.class, () -> {
            map.get(7);
        });

        assertEquals("AA", map.get(15));

        assertEquals("AA", map.remove(15));
        assertEquals(2, map.size());
        assertTrue(map.getTable()[1].getRemoved());

        assertEquals("B", map.get(6));
    }

    @Test(timeout = TIMEOUT)
    public void t17_ContainsNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.contains(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t18_Contains() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(10, "K"));
        assertNull(map.put(13, "AAA"));
        assertNull(map.put(21, "KK"));
        assertEquals(7, map.size());

        assertEquals("KK", map.remove(21));
        assertEquals(6, map.size());
        assertTrue(map.getTable()[5].getRemoved());

        assertEquals("C", map.remove(2));
        assertEquals(5, map.size());
        assertTrue(map.getTable()[5].getRemoved());

        assertNull(map.put(7, "H"));
        assertEquals(6, map.size());

        assertTrue(map.contains(0));
        assertTrue(map.contains(4));
        assertTrue(map.contains(55));
        assertTrue(map.contains(10));
        assertTrue(map.contains(13));
        assertTrue(map.contains(7));

        assertFalse(map.contains(21));
        assertFalse(map.contains(2));
        assertFalse(map.contains(9));
        assertFalse(map.contains(11));

        map.resizeBackingTable(6);

        assertTrue(map.contains(0));
        assertTrue(map.contains(4));
        assertTrue(map.contains(55));
        assertTrue(map.contains(10));
        assertTrue(map.contains(13));
        assertTrue(map.contains(7));

        assertFalse(map.contains(21));
        assertFalse(map.contains(2));
        assertFalse(map.contains(9));
        assertFalse(map.contains(11));
    }

    @Test(timeout = TIMEOUT)
    public void t19_Entries() {
        assertEquals(new HashSet<HashMap.Entry<Integer, String>>(0), map.entries());

        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(10, "K"));
        assertNull(map.put(13, "AAA"));
        assertNull(map.put(21, "KK"));
        assertEquals(7, map.size());

        assertEquals("C", map.remove(2));
        assertEquals(6, map.size());
        assertTrue(map.getTable()[2].getRemoved());

        HashSet<HashMap.Entry<Integer, String>> expected = new HashSet<>();
        expected.add(new HashMap.Entry<>(0, "A"));
        expected.add(new HashMap.Entry<>(4, "E"));
        expected.add(new HashMap.Entry<>(55, "AA"));
        expected.add(new HashMap.Entry<>(10, "K"));
        expected.add(new HashMap.Entry<>(13, "AAA"));
        expected.add(new HashMap.Entry<>(21, "KK"));
        assertEquals(expected, map.entries());
    }

    @Test(timeout = TIMEOUT)
    public void t20_Keys() {
        assertEquals(new HashSet<Integer>(0), map.keys());

        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(10, "K"));
        assertNull(map.put(13, "AAA"));
        assertNull(map.put(21, "KK"));
        assertEquals(7, map.size());

        assertEquals("C", map.remove(2));
        assertEquals(6, map.size());
        assertTrue(map.getTable()[2].getRemoved());

        HashSet<Integer> expected = new HashSet<>();
        expected.add(0);
        expected.add(55);
        expected.add(13);
        expected.add(4);
        expected.add(21);
        expected.add(10);
        assertEquals(expected, map.keys());
    }

    @Test(timeout = TIMEOUT)
    public void t21_Values() {
        assertEquals(new ArrayList<Integer>(0), map.values());

        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(10, "K"));
        assertNull(map.put(13, "AAA"));
        assertNull(map.put(21, "KK"));
        assertEquals(7, map.size());

        assertEquals("C", map.remove(2));
        assertEquals(6, map.size());
        assertTrue(map.getTable()[2].getRemoved());

        ArrayList<String> expected = new ArrayList<>(6);
        expected.addToBack("A");
        expected.addToBack("AA");
        expected.addToBack("AAA");
        expected.addToBack("E");
        expected.addToBack("KK");
        expected.addToBack("K");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void t22_ResizeIllegalLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.resizeBackingTable(-1);
        });

        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertEquals(3, map.size());

        assertThrows(IllegalArgumentException.class, () -> {
            map.resizeBackingTable(2);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t23_ResizeZeroLength() {
        map.resizeBackingTable(0);

        HashMap.Entry<Integer, String>[] expected = new HashMap.Entry[0];
        assertArrayEquals(expected, map.getTable());

        assertNull(map.put(0, "A"));

        expected = new HashMap.Entry[1];
        expected[0] = new HashMap.Entry<>(0, "A");
        assertArrayEquals(expected, map.getTable());

        assertNull(map.put(4, "E"));

        expected = new HashMap.Entry[3];
        expected[0] = new HashMap.Entry<>(0, "A");
        expected[1] = new HashMap.Entry<>(4, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t24_ResizeDown() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(55, "AA"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(10, "K"));
        assertNull(map.put(13, "AAA"));
        assertNull(map.put(21, "KK"));
        assertEquals(7, map.size());

        assertEquals("C", map.remove(2));
        assertEquals(6, map.size());
        assertTrue(map.getTable()[2].getRemoved());

        map.resizeBackingTable(7);

        HashMap.Entry<Integer, String>[] expected = new HashMap.Entry[7];
        expected[0] = new HashMap.Entry<>(0, "A");
        expected[1] = new HashMap.Entry<>(13, "AAA");
        expected[2] = new HashMap.Entry<>(21, "KK");
        expected[3] = new HashMap.Entry<>(10, "K");
        expected[4] = new HashMap.Entry<>(4, "E");
        expected[6] = new HashMap.Entry<>(55, "AA");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t25_IsEmpty() {
        assertTrue(map.isEmpty());

        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        assertFalse(map.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t26_Clear() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        HashMap.Entry<Integer, String>[] expected = new HashMap.Entry[5];
        expected[0] = new HashMap.Entry<>(0, "A");
        assertArrayEquals(expected, map.getTable());

        map.clear();
        assertEquals(0, map.size());

        expected[0] = null;
        assertArrayEquals(expected, map.getTable());

        assertNull(mapZero.put(0, "A"));
        assertNull(mapZero.put(1, "B"));
        assertNull(mapZero.put(2, "C"));
        assertEquals(3, mapZero.size());

        HashMap.Entry<Integer, String>[] expectedZero = new HashMap.Entry[7];
        expectedZero[0] = new HashMap.Entry<>(0, "A");
        expectedZero[1] = new HashMap.Entry<>(1, "B");
        expectedZero[2] = new HashMap.Entry<>(2, "C");
        assertArrayEquals(expectedZero, mapZero.getTable());

        mapZero.clear();
        assertEquals(0, mapZero.size());

        expectedZero[0] = null;
        expectedZero[1] = null;
        expectedZero[2] = null;
        assertArrayEquals(expectedZero, mapZero.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void t27_Iterator() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertEquals(4, map.size());

        HashSet<HashMap.Entry<Integer, String>> actual = new HashSet<>();
        for (HashMap.Entry<Integer, String> entry: map) {
            actual.add(entry);
        }
        assertEquals(map.entries(), actual);
    }

    @Test(timeout = TIMEOUT)
    public void t28_EqualsNotHashMap() {
        assertNotEquals(5, map);
    }

    @Test(timeout = TIMEOUT)
    public void t29_EqualsSame() {
        assertEquals(map, map);
    }

    @Test(timeout = TIMEOUT)
    public void t30_EqualsEmpty() {
        assertEquals(map, mapZero);
    }

    @Test(timeout = TIMEOUT)
    public void t31_EqualsDifferentSize() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(5, "A"));
        assertEquals(2, map.size());

        assertNull(mapZero.put(0, "A"));
        assertEquals(1, mapZero.size());

        assertNotEquals(map, mapZero);
    }

    @Test(timeout = TIMEOUT)
    public void t32_EqualsDifferentKey() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        assertNull(mapZero.put(1, "A"));
        assertEquals(1, mapZero.size());

        assertNotEquals(map, mapZero);
    }

    @Test(timeout = TIMEOUT)
    public void t33_EqualsDifferentValue() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        assertNull(mapZero.put(0, "B"));
        assertEquals(1, mapZero.size());

        assertNotEquals(map, mapZero);
    }

    @Test(timeout = TIMEOUT)
    public void t34_EqualsDifferentT() {
        assertNull(map.put(0, "A"));
        assertEquals(1, map.size());

        HashMap<String, Integer> map2 = new HashMap<>();
        assertNull(map2.put("A", 0));
        assertEquals(1, map.size());

        assertNotEquals(map, map2);

        HashMap<Object, String> map3 = new HashMap<>();
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
    public void t35_Equals() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "B"));
        assertNull(map.put(55, "C"));
        assertNull(map.put(1, "D"));
        assertEquals(4, map.size());

        assertNull(mapZero.put(0, "A"));
        assertNull(mapZero.put(4, "B"));
        assertNull(mapZero.put(55, "C"));
        assertNull(mapZero.put(1, "D"));
        assertEquals(4, mapZero.size());

        assertEquals(map, mapZero);
    }

    @Test(timeout = TIMEOUT)
    public void t36_HashCode() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(4, "B"));
        assertNull(map.put(55, "C"));
        assertNull(map.put(1, "D"));
        assertEquals(4, map.size());

        assertNull(mapZero.put(0, "A"));
        assertNull(mapZero.put(4, "B"));
        assertNull(mapZero.put(55, "C"));
        assertNull(mapZero.put(1, "D"));
        assertEquals(4, mapZero.size());

        assertEquals(map.hashCode(), mapZero.hashCode());
    }
}
