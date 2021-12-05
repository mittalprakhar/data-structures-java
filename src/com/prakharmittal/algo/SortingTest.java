package com.prakharmittal.algo;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SortingTest {

    private static final int TIMEOUT = 400;
    private static final Random rand = new Random();
    private static final int n = 2000;

    private Student[] students = new Student[n];
    private Student[] studentsByName = new Student[n];
    private Student[] studentsByUniversity = new Student[n];
    private Student[] studentsById = new Student[n];
    private Student[] studentsBySeniority = new Student[n];

    private ComparatorWithCount<Student> comparatorName;
    private ComparatorWithCount<Student> comparatorUniversity;
    private ComparatorWithCount<Student> comparatorId;
    private ComparatorWithCount<Student> comparatorSeniority;

    private static Object[][] steps = new Object[8][5];

    @Before
    public void setUp() throws IOException {
        comparatorName = Student.getNameComparator();
        comparatorUniversity = Student.getUniversityComparator();
        comparatorId = Student.getIdComparator();
        comparatorSeniority = Student.getSeniorityComparator();

        for (int i = 0; i < n; i++) {
            StringBuilder builder = new StringBuilder(4);
            for (int j = 0; j < 4; j++) {
                int randInt = 97 + rand.nextInt(122 - 97 + 1);
                builder.append((char) randInt);
            }
            String name = builder.toString();
            String university = "Georgia Tech";
            int id = i + 1;
            int seniority = n - i;

            Student student = new Student(name, university, id, seniority);

            students[i] = student;
            studentsByName[i] = student;
            studentsByUniversity[i] = student;
            studentsById[i] = student;
            studentsBySeniority[n - 1 - i] = student;
        }

        Arrays.sort(studentsByName, comparatorName);

        comparatorName.resetCount();
        comparatorUniversity.resetCount();
        comparatorId.resetCount();
        comparatorSeniority.resetCount();
    }

    @Test(timeout = TIMEOUT)
    public void t01_Exceptions() {
        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.selectionSort(null, comparatorName);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.selectionSort(students, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.bubbleSort(null, comparatorName);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.bubbleSort(students, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.cocktailShakerSort(null, comparatorName);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.cocktailShakerSort(students, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.insertionSort(null, comparatorName);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.insertionSort(students, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.heapSort(null, comparatorName);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.heapSort(students, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.mergeSort(null, comparatorName);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.mergeSort(students, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.quickSort(null, comparatorName, rand);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.quickSort(students, null, rand);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.quickSort(students, comparatorName, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.quickSelect(-1, students, comparatorName, rand);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.quickSelect(Integer.MAX_VALUE, students, comparatorName, rand);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.quickSelect(1, null, comparatorName, rand);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.quickSelect(1, students, null, rand);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.quickSelect(1, students, comparatorName, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Sorting.lsdRadixSort(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t02_SelectionSortZero() {
        Sorting.selectionSort(new Student[0], comparatorName);
    }

    @Test(timeout = TIMEOUT)
    public void t03_SelectionSortRandom() {
        Sorting.selectionSort(students, comparatorName);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsByName[i].name, students[i].name);
        }
        steps[1][1] = comparatorName.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t04_SelectionSortUniform() {
        Sorting.selectionSort(students, comparatorUniversity);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsByUniversity[i].university, students[i].university);
        }
        steps[1][2] = comparatorUniversity.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t05_SelectionSortSorted() {
        Sorting.selectionSort(students, comparatorId);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsById[i].id, students[i].id);
        }
        steps[1][3] = comparatorId.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t06_SelectionSortReverse() {
        Sorting.insertionSort(students, comparatorSeniority);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsBySeniority[i].seniority, students[i].seniority);
        }
        steps[1][4] = comparatorSeniority.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t07_BubbleSortZero() {
        Sorting.bubbleSort(new Student[0], comparatorName);
    }

    @Test(timeout = TIMEOUT)
    public void t08_BubbleSortRandom() {
        Sorting.bubbleSort(students, comparatorName);
        assertArrayEquals(studentsByName, students);
        steps[2][1] = comparatorName.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t09_BubbleSortUniform() {
        Sorting.bubbleSort(students, comparatorUniversity);
        assertArrayEquals(studentsByUniversity, students);
        steps[2][2] = comparatorUniversity.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t10_BubbleSortSorted() {
        Sorting.bubbleSort(students, comparatorId);
        assertArrayEquals(studentsById, students);
        steps[2][3] = comparatorId.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t11_BubbleSortReverse() {
        Sorting.bubbleSort(students, comparatorSeniority);
        assertArrayEquals(studentsBySeniority, students);
        steps[2][4] = comparatorSeniority.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t12_BubbleSortStable() {
        Integer i = new Integer(200);
        Integer j = new Integer(200);
        Integer[] unsortedArray = new Integer[] {1, 5, 2, i, 3, 4, j, 7, 6};
        Integer[] sortedArray   = new Integer[] {1, 2, 3, 4, 5, 6, 7, i, j};
        Sorting.bubbleSort(unsortedArray, Comparator.<Integer>naturalOrder());
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void t13_CocktailSortZero() {
        Sorting.cocktailShakerSort(new Student[0], comparatorName);
    }

    @Test(timeout = TIMEOUT)
    public void t14_CocktailSortRandom() {
        Sorting.cocktailShakerSort(students, comparatorName);
        assertArrayEquals(studentsByName, students);
        steps[3][1] = comparatorName.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t15_CocktailSortUniform() {
        Sorting.cocktailShakerSort(students, comparatorUniversity);
        assertArrayEquals(studentsByUniversity, students);
        steps[3][2] = comparatorUniversity.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t16_CocktailSortSorted() {
        Sorting.cocktailShakerSort(students, comparatorId);
        assertArrayEquals(studentsById, students);
        steps[3][3] = comparatorId.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t17_CocktailSortReverse() {
        Sorting.cocktailShakerSort(students, comparatorSeniority);
        assertArrayEquals(studentsBySeniority, students);
        steps[3][4] = comparatorSeniority.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t18_CocktailSortStable() {
        Integer i = new Integer(200);
        Integer j = new Integer(200);
        Integer[] unsortedArray = new Integer[] {1, 5, 2, i, 3, 4, j, 7, 6};
        Integer[] sortedArray   = new Integer[] {1, 2, 3, 4, 5, 6, 7, i, j};
        Sorting.cocktailShakerSort(unsortedArray, Comparator.<Integer>naturalOrder());
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void t19_InsertionSortZero() {
        Sorting.insertionSort(new Student[0], comparatorName);
    }

    @Test(timeout = TIMEOUT)
    public void t20_InsertionSortRandom() {
        Sorting.insertionSort(students, comparatorName);
        assertArrayEquals(studentsByName, students);
        steps[4][1] = comparatorName.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t21_InsertionSortUniform() {
        Sorting.insertionSort(students, comparatorUniversity);
        assertArrayEquals(studentsByUniversity, students);
        steps[4][2] = comparatorUniversity.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t22_InsertionSortSorted() {
        Sorting.insertionSort(students, comparatorId);
        assertArrayEquals(studentsById, students);
        steps[4][3] = comparatorId.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t23_InsertionSortReverse() {
        Sorting.insertionSort(students, comparatorSeniority);
        assertArrayEquals(studentsBySeniority, students);
        steps[4][4] = comparatorSeniority.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t24_InsertionSortStable() {
        Integer i = new Integer(200);
        Integer j = new Integer(200);
        Integer[] unsortedArray = new Integer[] {1, 5, 2, i, 3, 4, j, 7, 6};
        Integer[] sortedArray   = new Integer[] {1, 2, 3, 4, 5, 6, 7, i, j};
        Sorting.insertionSort(unsortedArray, Comparator.<Integer>naturalOrder());
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void t25_HeapSortZero() {
        Sorting.heapSort(new Student[0], comparatorName);
    }

    @Test(timeout = TIMEOUT)
    public void t26_HeapSortRandom() {
        Sorting.heapSort(students, comparatorName);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsByName[i].name, students[i].name);
        }
        steps[5][1] = comparatorName.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t27_HeapSortUniform() {
        Sorting.heapSort(students, comparatorUniversity);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsByUniversity[i].university, students[i].university);
        }
        steps[5][2] = comparatorUniversity.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t28_HeapSortSorted() {
        Sorting.heapSort(students, comparatorId);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsById[i].id, students[i].id);
        }
        steps[5][3] = comparatorId.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t29_HeapSortReverse() {
        Sorting.insertionSort(students, comparatorSeniority);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsBySeniority[i].seniority, students[i].seniority);
        }
        steps[5][4] = comparatorSeniority.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t30_MergeSortZero() {
        Sorting.mergeSort(new Student[0], comparatorName);
    }

    @Test(timeout = TIMEOUT)
    public void t31_MergeSortRandom() {
        Sorting.mergeSort(students, comparatorName);
        assertArrayEquals(studentsByName, students);
        steps[6][1] = comparatorName.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t32_MergeSortUniform() {
        Sorting.mergeSort(students, comparatorUniversity);
        assertArrayEquals(studentsByUniversity, students);
        steps[6][2] = comparatorUniversity.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t33_MergeSortSorted() {
        Sorting.mergeSort(students, comparatorId);
        assertArrayEquals(studentsById, students);
        steps[6][3] = comparatorId.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t34_MergeSortReverse() {
        Sorting.mergeSort(students, comparatorSeniority);
        assertArrayEquals(studentsBySeniority, students);
        steps[6][4] = comparatorSeniority.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t35_MergeSortStable() {
        Integer i = new Integer(200);
        Integer j = new Integer(200);
        Integer[] unsortedArray = new Integer[] {1, i, 5, j, 3, 4, 2, 7, 6};
        Integer[] sortedArray   = new Integer[] {1, 2, 3, 4, 5, 6, 7, i, j};
        Sorting.mergeSort(unsortedArray, Comparator.<Integer>naturalOrder());
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void t36_QuickSortZero() {
        Sorting.quickSort(new Student[0], comparatorName, rand);
    }

    @Test(timeout = TIMEOUT)
    public void t37_QuickSortRandom() {
        Sorting.quickSort(students, comparatorName, rand);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsByName[i].name, students[i].name);
        }
        steps[7][1] = comparatorName.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t38_QuickSortUniform() {
        Sorting.quickSort(students, comparatorUniversity, rand);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsByUniversity[i].university, students[i].university);
        }
        steps[7][2] = comparatorUniversity.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t39_QuickSortSorted() {
        Sorting.quickSort(students, comparatorId, rand);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsById[i].id, students[i].id);
        }
        steps[7][3] = comparatorId.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t40_QuickSortReverse() {
        Sorting.quickSort(students, comparatorSeniority, rand);
        for (int i = 0; i < n; i++) {
            assertEquals(studentsBySeniority[i].seniority, students[i].seniority);
        }
        steps[7][4] = comparatorSeniority.getCount();
    }

    @Test(timeout = TIMEOUT)
    public void t41_QuickSelect() {
        assertEquals(studentsByName[29].name,
                Sorting.quickSelect(30, students, comparatorName, rand).name);

        assertEquals(studentsByUniversity[29].university,
                Sorting.quickSelect(30, students, comparatorUniversity, rand).university);

        assertEquals(studentsById[349].id,
                Sorting.quickSelect(350, students, comparatorId, rand).id);

        assertEquals(studentsBySeniority[99].seniority,
                Sorting.quickSelect(100, students, comparatorSeniority, rand).seniority);
    }

    @Test(timeout = TIMEOUT)
    public void t42_LsdRadixSortZero() {
        Sorting.lsdRadixSort(new int[0]);
    }

    @Test(timeout = TIMEOUT)
    public void t43_LsdRadixSortRandom() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, 85,   3};
        int[] sortedArray   = new int[] { 3, 20, 28, 54, 58,  84, 85, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void t44_LsdRadixSortNegative() {
        int[] unsortedArray = new int[] {- 54, -28, -58, -84, -20, -122, -85, -3};
        int[] sortedArray   = new int[] {-122, -85, -84, -58, -54, - 28, -20, -3};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void t45_LsdRadixSortMixed() {
        int[] unsortedArray = new int[] {  54,  28, -58, -84, 20, -122, 85, - 3};
        int[] sortedArray   = new int[] {-122, -84, -58, - 3, 20,   28, 54,  85};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void t46_LsdRadixSortMinMax() {
        int[] unsortedArray = new int[] {Integer.MAX_VALUE, -54, -28,  58,  84, -20, 122, -85,   3, Integer.MIN_VALUE};
        int[] sortedArray   = new int[] {Integer.MIN_VALUE, -85, -54, -28, -20,   3,  58,  84, 122, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void t47_LsdRadixSortTrick() {
        int[] unsortedArray = new int[] {-504,  208, -508,  804, -200, 1202, -805,     3};
        int[] sortedArray   = new int[] {-805, -508, -504, -200,    3,  208,  804,  1202};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @AfterClass
    public static void completed() {
        System.out.println("\nComparisons Made (n = " + n + "):\n");
        steps[0] = new Object[] {"", "Random", "Uniform", "Sorted", "Reverse"};
        steps[1][0] = "Selection";
        steps[2][0] = "Bubble";
        steps[3][0] = "Cocktail";
        steps[4][0] = "Insertion";
        steps[5][0] = "Heap";
        steps[6][0] = "Merge";
        steps[7][0] = "Quick";
        for (Object[] row: steps) {
            System.out.printf("%15s %10s %10s %10s %10s %n", row);
        }
    }

    private static class Student implements Serializable {

        private final String name;          // Random
        private final String university;    // Uniform
        private final int id;               // Sorted
        private final int seniority;        // Reverse Sorted

        public Student(String name, String university, int id, int seniority) {
            this.name = name;
            this.university = university;
            this.id = id;
            this.seniority = seniority;
        }

        public static ComparatorWithCount<Student> getNameComparator() {
            return new ComparatorWithCount<>() {
                @Override
                public int compare(Student student1, Student student2) {
                    incrementCount();
                    return student1.name.compareTo(student2.name);
                }
            };
        }

        public static ComparatorWithCount<Student> getUniversityComparator() {
            return new ComparatorWithCount<>() {
                @Override
                public int compare(Student student1, Student student2) {
                    incrementCount();
                    return student1.university.compareTo(student2.university);
                }
            };
        }

        public static ComparatorWithCount<Student> getIdComparator() {
            return new ComparatorWithCount<>() {
                @Override
                public int compare(Student student1, Student student2) {
                    incrementCount();
                    return Integer.compare(student1.id, student2.id);
                }
            };
        }

        public static ComparatorWithCount<Student> getSeniorityComparator() {
            return new ComparatorWithCount<>() {
                @Override
                public int compare(Student student1, Student student2) {
                    incrementCount();
                    return Integer.compare(student1.seniority, student2.seniority);
                }
            };
        }

        @Override
        public String toString() {
            return String.format("(%s, %s, %d, %d)", name, university, id, seniority);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Student) {
                Student s = (Student) o;
                return s.name.equals(name)
                        && s.university.equals(university)
                        && s.id == id
                        && s.seniority == seniority;
            }
            return false;
        }
    }

    private abstract static class ComparatorWithCount<T> implements Comparator<T> {

        private int count;

        public int getCount() {
            return count;
        }

        public void incrementCount() {
            count++;
        }

        public void resetCount() {
            count = 0;
        }
    }
}
