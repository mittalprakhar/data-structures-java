package com.prakharmittal.algo;

import com.prakharmittal.hashing.LinkedHashMap;
import com.prakharmittal.list.ArrayList;

import java.util.Comparator;

public class PatternMatching {

    private static final int BASE = 113;

    public static ArrayList<Integer> boyerMoore(CharSequence pattern,
                                                CharSequence text,
                                                CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern must not be null.");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern must not have zero length.");
        } else if (text == null) {
            throw new IllegalArgumentException("The text must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        }
        ArrayList<Integer> found = new ArrayList<>();
        if (text.length() < pattern.length()) {
            return found;
        }
        LinkedHashMap<Character, Integer> lastTable = buildLastTable(pattern);
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j == -1) {
                found.addToBack(i++);
            } else {
                int shift;
                try {
                    shift = lastTable.get(text.charAt(i + j));
                } catch (Exception e) {
                    shift = -1;
                }
                if (shift < j) {
                    i += j - shift;
                } else {
                    i++;
                }
            }
        }
        return found;
    }

    public static LinkedHashMap<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern must not be null.");
        }
        LinkedHashMap<Character, Integer> lastTable = new LinkedHashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            lastTable.put(pattern.charAt(i), i);
        }
        return lastTable;
    }

    public static ArrayList<Integer> kmp(CharSequence pattern, CharSequence text,
                                         CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern must not be null.");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern must not have zero length.");
        } else if (text == null) {
            throw new IllegalArgumentException("The text must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        }
        ArrayList<Integer> found = new ArrayList<>();
        if (text.length() < pattern.length()) {
            return found;
        }
        int[] failureTable = buildFailureTable(pattern, comparator);
        int i = 0;
        int j = 0;
        while (i <= text.length() - pattern.length() + j) {
            if (comparator.compare(text.charAt(i), pattern.charAt(j)) == 0) {
                i++;
                if (++j == pattern.length()) {
                    found.addToBack(i - j);
                    j = failureTable[j - 1];
                }
            } else if (j == 0) {
                i++;
            } else {
                j = failureTable[j - 1];
            }
        }
        return found;
    }

    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        }
        int[] failureTable = new int[pattern.length()];
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                failureTable[j++] = ++i;
            } else if (i == 0) {
                failureTable[j++] = 0;
            } else {
                i = failureTable[i - 1];
            }
        }
        return failureTable;
    }

    public static ArrayList<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern must not be null.");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern must not have zero length.");
        } else if (text == null) {
            throw new IllegalArgumentException("The text must not be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator must not be null.");
        }
        ArrayList<Integer> found = new ArrayList<>();
        if (text.length() < pattern.length()) {
            return found;
        }
        int patternHash = 0;
        int textHash = 0;
        int runningFactor = 1;
        for (int i = pattern.length() - 1; i > 0; i--) {
            patternHash += pattern.charAt(i) * runningFactor;
            textHash += text.charAt(i) * runningFactor;
            runningFactor *= BASE;
        }
        patternHash += pattern.charAt(0) * runningFactor;
        textHash += text.charAt(0) * runningFactor;
        int i = 0;
        int j = 0;
        while (i <= text.length() - pattern.length()) {
            if (patternHash == textHash) {
                while (j < pattern.length()
                        && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == pattern.length()) {
                    found.addToBack(i);
                }
            }
            if (++i <= text.length() - pattern.length()) {
                textHash = (textHash - text.charAt(i - 1) * runningFactor) * BASE
                        + text.charAt(i - 1 + pattern.length());
            }
            j = 0;
        }
        return found;
    }

    static class CharacterComparator implements Comparator<Character> {

        private int count;

        @Override
        public int compare(Character c1, Character c2) {
            count++;
            return c1 - c2;
        }

        public int getCount() {
            return count;
        }
    }
}
