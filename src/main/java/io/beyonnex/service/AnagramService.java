package io.beyonnex.service;

import io.beyonnex.service.replacements.Mode;
import io.beyonnex.service.replacements.ModeType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is responsible for performing anagram search operations. It allows you to add or remove anagram match
 * replacement modes, check if two strings are anagrams and look up anagrams for any given string.
 */
public class AnagramService {

    private static final String ALPHABET_ONLY_REGEX = "[^a-z]";
    private final Map<String, Set<String>> anagramDictionary = new HashMap<>();
    private final Set<ModeType> activeModes = new HashSet<>();

    /**
     * Activates a given anagram match replacement mode.
     *
     * @param modeType - type of mode to be activated
     */
    public void activateMode(ModeType modeType) {
        activeModes.add(modeType);
    }

    /**
     * Deactivates a given anagram match replacement mode.
     *
     * @param modeType - type of mode to be deactivated
     */
    public void deactivateMode(ModeType modeType) {
        activeModes.remove(modeType);
    }

    /**
     * Returns a set of currently active anagram match replacement modes.
     *
     * @return Set of active modes
     */
    public Set<ModeType> getActiveModes() {
        return new HashSet<>(activeModes);
    }

    /**
     * Checks if the two input strings are anagrams considering the currently active modes.
     * Runtime complexity of this method could be at most O(n log n), where n is the length of the longer string. This is dominated by the sort operation inside normalizeString.
     * To extend, you can add more complex replacement modes by creating a new Mode implementation and a corresponding ModeType enum value.
     *
     * @param firstWord  - first string to be checked
     * @param secondWord - second string to be checked
     * @return boolean - true if strings are anagrams, false otherwise
     * @throws IllegalArgumentException - if either of the inputs is null
     */
    public boolean areAnagrams(String firstWord, String secondWord) {
        if (firstWord == null || secondWord == null) {
            throw new IllegalArgumentException("Input words must not be null");
        }

        String transformedA = applyModes(firstWord);
        String transformedB = applyModes(secondWord);
        String normalizedA = normalizeString(transformedA);
        String normalizedB = normalizeString(transformedB);

        addStringToAnagramMap(normalizedA, firstWord);
        addStringToAnagramMap(normalizedB, secondWord);

        return normalizedA.equals(normalizedB);
    }

    /**
     * Gets a collection of anagrams from a precomputed anagram dictionary for a given string.
     *
     * @param word - string to be checked
     * @return Set<String> - Set of anagrams for the string, or empty set if no matches found or word is null
     * <p>
     * To add additional ways of fetching anagrams, one could create methods to fetch anagrams given more than one string,
     * or methods that use more complex criteria to find anagrams.
     */
    public Set<String> getAnagrams(String word) {
        String transformedWord = applyModes(word);
        String normalizedWord = normalizeString(transformedWord);
        return anagramDictionary.getOrDefault(normalizedWord, new HashSet<>())
                .stream()
                .filter(anagram -> !anagram.equals(word))
                .collect(Collectors.toSet());
    }

    /**
     * Method to apply currently active transformation modes to the input string.
     * It sequentially applies the transformation associated with each active mode.
     *
     * @param input - the string to be transformed
     * @return the transformed string
     * <p>
     * To extend, one could apply transformations selectively based on additional criteria
     * or enable users to decide the ordering in which multiple transformations are applied.
     */
    private String applyModes(String input) {
        for (ModeType modeType : activeModes) {
            Mode mode = modeType.getInstance();
            input = mode.transform(input);
        }
        return input;
    }

    /**
     * Method to normalize an input string for easier comparison with other strings.
     * It converts the string to lower case, removes all non-alphabet characters and sorts it alphabetically.
     *
     * @param word - the string to be normalized
     * @return the normalized string
     * <p>
     * To extend, one could also remove common stop words or stemming the words before sorting.
     */
    private String normalizeString(String word) {
        String lowerCase = word.toLowerCase().replaceAll(ALPHABET_ONLY_REGEX, "");
        char[] chars = lowerCase.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    // @formatter:off
    /**
     * Method to add the original string to the anagram dictionary under its sorted key.
     * If the key is already present in the dictionary, the original string is added to the
     * existing set associated with that key. If not, it creates a new set and adds the original
     * string to it.
     *
     * @param sorted   - the normalized version of the original string
     * @param original - the original string
     * <p>
     * To extend, one could categorize anagrams not only based on the sorted character sequence, but also on other
     * factors like length of the string, frequency of certain characters etc.
     *
     */
    // @formatter:on
    private void addStringToAnagramMap(String sorted, String original) {
        anagramDictionary.computeIfAbsent(sorted, k -> new HashSet<>()).add(original);
    }
}