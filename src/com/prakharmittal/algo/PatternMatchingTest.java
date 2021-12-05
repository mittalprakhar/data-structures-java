package com.prakharmittal.algo;

import com.prakharmittal.list.ArrayList;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PatternMatchingTest {

    private static final int TIMEOUT = 200;

    private static final int STRESS_TEST_TIMEOUT = 1000;
    private static final int STRESS_TEST_CASES = 100;
    private static final int STRESS_TEST_TEXT_LENGTH = 2000;
    private static final int STRESS_TEST_PATTERN_LENGTH = 5;

    private static Random rand = new Random();
    private static String[][] stressTests = new String[STRESS_TEST_CASES][2];
    private static ArrayList<Integer> stressTestAnswers = new ArrayList<>(STRESS_TEST_CASES);
    private static PatternMatching.CharacterComparator comparator;

    @Before
    public void setUp() {
        comparator = new PatternMatching.CharacterComparator();
    }

    @Test(timeout = TIMEOUT)
    public void t01_Exceptions() {
                assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.boyerMoore(null, "text", comparator);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.boyerMoore("", "text", comparator);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.boyerMoore("pattern", null, comparator);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.boyerMoore("pattern", "text", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.buildLastTable(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.kmp(null, "text", comparator);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.kmp("", "text", comparator);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.kmp("pattern", null, comparator);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.kmp("pattern", "text", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.buildFailureTable(null, comparator);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.buildFailureTable("pattern", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.rabinKarp(null, "text", comparator);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.rabinKarp("", "text", comparator);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.rabinKarp("pattern", null, comparator);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            PatternMatching.rabinKarp("pattern", "text", null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t02_BM_LongerPattern() {
        test("bm", "pattern", "pat",
                new int[] {}, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t03_BM_PatternEqualsText() {
        test("bm", "equals", "equals",
                new int[] {0}, 6);
    }

    @Test(timeout = TIMEOUT)
    public void t04_BM_CharNotFound() {
        test("bm", "a", "simple",
                new int[] {}, 6);
    }

    @Test(timeout = TIMEOUT)
    public void t05_BM_CharOnce() {
        test("bm", "a", "sample",
                new int[] {1}, 6);
    }

    @Test(timeout = TIMEOUT)
    public void t06_BM_CharMultiple() {
        test("bm", "a", "abcacababaaaba",
                new int[] {0, 3, 5, 7, 9, 10, 11, 13}, 14);
    }

    @Test(timeout = TIMEOUT)
    public void t07_BM_SimpleNotFound() {
        test("bm", "abacab", "abacaabaccabacacaabb",
                new int[] {}, 20);
    }

    @Test(timeout = TIMEOUT)
    public void t08_BM_SimpleOnce() {
        test("bm", "abacab", "abacaabaccabacabaabb",
                new int[] {10}, 26);
    }

    @Test(timeout = TIMEOUT)
    public void t09_BM_SimpleMultiple() {
        test("bm", "abacab", "abacaabaccabacabaababacababacac",
                new int[] {10, 19}, 43);
    }

    @Test(timeout = TIMEOUT)
    public void t10_BM_NestedNoBreaks() {
        test("bm", "aaa", "aaaaaaaaaa",
                new int[] {0, 1, 2, 3, 4, 5, 6, 7}, 24);
    }

    @Test(timeout = TIMEOUT)
    public void t11_BM_NestedBreaks() {
        test("bm", "abab", "ababababbabababaabababaabab",
                new int[] {0, 2, 4, 9, 11, 16, 18, 23}, 56);
    }

    @Test(timeout = TIMEOUT)
    public void t12_BM_BadCharacter() {
        test("bm", "abab", "ababababcabababadbababaabab",
                new int[] {0, 2, 4, 9, 11, 18, 23}, 41);
    }

    @Test(timeout = TIMEOUT)
    public void t13_KMP_LongerPattern() {
        test("kmp", "pattern", "pat",
                new int[] {}, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t14_KMP_PatternEqualsText() {
        ArrayList<Integer> answer = new ArrayList<>();
        answer.addToBack(0);
        assertEquals(answer, PatternMatching.kmp("equals", "equals", comparator));
        assertTrue(comparator.getCount() == 5 || comparator.getCount() == 11);
    }

    @Test(timeout = TIMEOUT)
    public void t15_KMP_CharNotFound() {
        test("kmp", "a", "simple",
                new int[] {}, 0 + 6);
    }

    @Test(timeout = TIMEOUT)
    public void t16_KMP_CharOnce() {
        test("kmp", "a", "sample",
                new int[] {1}, 0 + 6);
    }

    @Test(timeout = TIMEOUT)
    public void t17_KMP_CharMultiple() {
        test("kmp", "a", "abcacababaaaba",
                new int[] {0, 3, 5, 7, 9, 10, 11, 13}, 0 + 14);
    }

    @Test(timeout = TIMEOUT)
    public void t18_KMP_SimpleNotFound() {
        test("kmp", "abacab", "abacaabaccabacacaabb",
                new int[] {}, 6 + 20);
    }

    @Test(timeout = TIMEOUT)
    public void t19_KMP_SimpleOnce() {
        test("kmp", "abacab", "abacaabaccabacabaabb",
                new int[] {10}, 6 + 21);
    }

    @Test(timeout = TIMEOUT)
    public void t20_KMP_SimpleMultiple() {
        test("kmp", "abacab", "abacaabaccabacabaababacababacac",
                new int[] {10, 19}, 6 + 38);
    }

    @Test(timeout = TIMEOUT)
    public void t21_KMP_NestedNoBreaks() {
        test("kmp", "aaa", "aaaaaaaaaa",
                new int[] {0, 1, 2, 3, 4, 5, 6, 7}, 2 + 10);
    }

    @Test(timeout = TIMEOUT)
    public void t22_KMP_NestedBreaks() {
        test("kmp", "abab", "ababababbabababaabababaabab",
                new int[] {0, 2, 4, 9, 11, 16, 18, 23}, 3 + 32);
    }

    @Test(timeout = TIMEOUT)
    public void t23_KMP_BadCharacter() {
        test("kmp", "abab", "ababababcabababadbababaabab",
                new int[] {0, 2, 4, 9, 11, 18, 23}, 3 + 32);
    }

    @Test(timeout = TIMEOUT)
    public void t24_RK_LongerPattern() {
        test("rk", "pattern", "pat",
                new int[] {}, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t25_RK_PatternEqualsText() {
        test("rk", "equals", "equals",
                new int[] {0}, 6);
    }

    @Test(timeout = TIMEOUT)
    public void t26_RK_CharNotFound() {
        test("rk", "a", "simple",
                new int[] {}, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t27_RK_CharOnce() {
        test("rk", "a", "sample",
                new int[] {1}, 1);
    }

    @Test(timeout = TIMEOUT)
    public void t28_RK_CharMultiple() {
        test("rk", "a", "abcacababaaaba",
                new int[] {0, 3, 5, 7, 9, 10, 11, 13}, 8);
    }

    @Test(timeout = TIMEOUT)
    public void t29_RK_SimpleNotFound() {
        test("rk", "abacab", "abacaabaccabacacaabb",
                new int[] {}, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t30_RK_SimpleOnce() {
        test("rk", "abacab", "abacaabaccabacabaabb",
                new int[] {10}, 6);
    }

    @Test(timeout = TIMEOUT)
    public void t31_RK_SimpleMultiple() {
        test("rk", "abacab", "abacaabaccabacabaababacababacac",
                new int[] {10, 19}, 12);
    }

    @Test(timeout = TIMEOUT)
    public void t32_RK_NestedNoBreaks() {
        test("rk", "aaa", "aaaaaaaaaa",
                new int[] {0, 1, 2, 3, 4, 5, 6, 7}, 24);
    }

    @Test(timeout = TIMEOUT)
    public void t33_RK_NestedBreaks() {
        test("rk", "abab", "ababababbabababaabababaabab",
                new int[] {0, 2, 4, 9, 11, 16, 18, 23}, 32);
    }

    @Test(timeout = TIMEOUT)
    public void t34_RK_BadCharacter() {
        test("rk", "abab", "ababababcabababadbababaabab",
                new int[] {0, 2, 4, 9, 11, 18, 23}, 28);
    }

    @Test(timeout = TIMEOUT)
    public void t35_RK_EqualHash() {
        test("rk", "\u0001\u0072", "\u0002\u0001\u0072",
                new int[] {1}, 3);
    }

    @Test(timeout = STRESS_TEST_TIMEOUT)
    public void t36_StressTest() {
        for (int i = 0; i < STRESS_TEST_CASES; i++) {
            String pattern = generateString(STRESS_TEST_PATTERN_LENGTH - 1) + "!";
            String text = generateString(STRESS_TEST_TEXT_LENGTH);
            int index = rand.nextInt(STRESS_TEST_TEXT_LENGTH - STRESS_TEST_PATTERN_LENGTH + 1);
            text = text.substring(0, index) + pattern + text.substring(index + STRESS_TEST_PATTERN_LENGTH);
            stressTests[i][0] = text;
            stressTests[i][1] = pattern;
            stressTestAnswers.addToBack(index);
        }
        for (int i = 0; i < STRESS_TEST_CASES; i++) {
            assertEquals(stressTestAnswers.get(i),
                    PatternMatching.boyerMoore(stressTests[i][1], stressTests[i][0], comparator).get(0));
        }
        int bmComparisons = comparator.getCount();
        for (int i = 0; i < STRESS_TEST_CASES; i++) {
            assertEquals(stressTestAnswers.get(i),
                    PatternMatching.kmp(stressTests[i][1], stressTests[i][0], comparator).get(0));
        }
        int kmpComparisons = comparator.getCount() - bmComparisons;
        for (int i = 0; i < STRESS_TEST_CASES; i++) {
            assertEquals(stressTestAnswers.get(i),
                    PatternMatching.rabinKarp(stressTests[i][1], stressTests[i][0], comparator).get(0));
        }
        int rkComparisons = comparator.getCount() - bmComparisons - kmpComparisons;
        System.out.println("\nSTRESS TEST RESULT\n");
        System.out.print("Text Length: " + STRESS_TEST_TEXT_LENGTH);
        System.out.print("\tPattern Length: " + STRESS_TEST_PATTERN_LENGTH);
        System.out.println("\tTest Cases: " + STRESS_TEST_CASES);
        System.out.printf("%-22s %8d %10s", "\nBoyer Moore:", bmComparisons, "comparisons");
        System.out.printf("%-22s %8d %10s", "\nKnuth Morris Pratt:", kmpComparisons, "comparisons");
        System.out.printf("%-22s %8d %10s %n", "\nRabin Karp:", rkComparisons, "comparisons");
    }

    private void test(String algorithm, String pattern, String text, int[] matches, int comparisons) {
        ArrayList<Integer> answer = new ArrayList<>();
        for (int match: matches) {
            answer.addToBack(match);
        }
        if (algorithm.equalsIgnoreCase("kmp")) {
            assertEquals(answer, PatternMatching.kmp(pattern, text, comparator));
        } else if (algorithm.equalsIgnoreCase("bm")) {
            assertEquals(answer, PatternMatching.boyerMoore(pattern, text, comparator));
        } else {
            assertEquals(answer, PatternMatching.rabinKarp(pattern, text, comparator));
        }
        assertEquals(comparisons, comparator.getCount());
    }

    private String generateString(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int j = 0; j < length; j++) {
            int randInt = 97 + rand.nextInt(122 - 97 + 1);
            builder.append((char) randInt);
        }
        return builder.toString();
    }
}
