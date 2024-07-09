package io.beyonnex.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The AnagramFindr class provides core functionality for finding anagrams.
 * It provides two main public functionalities: confirming if two strings are anagrams and
 * finding anagrams of a given string from previously queried strings.
 */
public class AnagramFindr {

    // Only allows lower case alphabets
    private static final String ALPHABET_ONLY_REGEX = "[^a-z]";

    // The String which will replace unmatched characters
    private static final String EMPTY_STRING = "";

    // Map to keep the list of anagrams
    private final Map<String, Set<String>> anagramDictionary = new HashMap<>();

    /**
     * Checks if two strings are anagrams of each other.
     * The strings are normalized (case in-sensitive and sorted), and then compared.
     * The normalization ensures that "listen" and "silent" are treated as same key in the anagram map.
     * Hence, can retrieve known anagrams in O(1) time complexity once they are computed.
     *
     * @param firstWord  - The first string to check.
     * @param secondWord - The second string to check.
     * @return true if the strings are anagrams of each other, false otherwise.
     */
    public boolean areAnagrams(String firstWord, String secondWord) {
        String normalizedA = normalizeString(firstWord);
        String normalizedB = normalizeString(secondWord);

        addStringToAnagramMap(normalizedA, firstWord);
        addStringToAnagramMap(normalizedB, secondWord);

        return normalizedA.equals(normalizedB);
    }

    /**
     * Retrieves the anagrams of the string if any.
     *
     * @param word - The string to find anagrams for.
     * @return Set&lt;String&gt; - A set containing all confirmed anagrams of the word.
     * Returns an empty set if no anagrams are found
     */
    public Set<String> getAnagrams(String word) {
        return Optional.ofNullable(word)
                .map(this::normalizeString)
                .map(normalizedWord -> anagramDictionary.getOrDefault(normalizedWord, new HashSet<>()))
                .map(HashSet::new)
                .map(anagrams -> excludeOriginalWordFromAnagrams(word, anagrams))
                .orElse(new HashSet<>(1));
    }

    /**
     * Normalizes the string by converting to lower case, removing non-alphabetic characters, and sorting.
     *
     * @param word - The original string to normalize.
     * @return String - The normalized version of string.
     */
    private String normalizeString(String word) {
        if (word == null) {
            return null;
        }
        String lowerCase = word.toLowerCase().replaceAll(ALPHABET_ONLY_REGEX, EMPTY_STRING);
        char[] chars = lowerCase.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * Removes the original word from a set of anagrams.
     *
     * @param word     - The original word to exclude from the anagrams.
     * @param anagrams - The set of anagrams to remove the original word from.
     * @return HashSet<String> - The updated set of anagrams without the original word.
     */
    private HashSet<String> excludeOriginalWordFromAnagrams(String word, HashSet<String> anagrams) {
        anagrams.remove(word);
        return anagrams;
    }

    /**
     * Adds original strings to the anagram map.
     *
     * @param normalizedString - The normalized string used as key in the map.
     * @param originalString   - The original string, saved in the set as a value in the map.
     */
    private void addStringToAnagramMap(String normalizedString, String originalString) {
        anagramDictionary.computeIfAbsent(normalizedString, k -> new HashSet<>()).add(originalString);
    }

}