package io.beyonnex.service;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.beyonnex.service.error.FindrException;
import io.beyonnex.service.replacements.Mode;
import io.beyonnex.service.replacements.ModeType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.beyonnex.service.error.FindrException.INVALID_INPUT_ERROR;

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
     * Runtime complexity of this method could be at most O(n log n), where n is the length of the longer string.
     * This is dominated by the sort operation inside normalizeString.
     * To extend, you can add more complex replacement modes by creating a new Mode implementation and a corresponding ModeType enum value.
     *
     * @param firstWord  - first string to be checked
     * @param secondWord - second string to be checked
     * @return boolean - true if strings are anagrams, false otherwise
     * @throws FindrException - if either of the inputs is null
     */
    public boolean areAnagrams(String firstWord, String secondWord) {
        if (firstWord == null || secondWord == null) {
            throw new FindrException(INVALID_INPUT_ERROR);
        }

        String transformedA = applyModes(firstWord);
        String transformedB = applyModes(secondWord);
        Multiset<Character> normalizedA = getNormalizedMultiset(transformedA);
        Multiset<Character> normalizedB = getNormalizedMultiset(transformedB);

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
        Multiset<Character> normalizedWord = getNormalizedMultiset(transformedWord);
        String key = generateKey(normalizedWord);
        return anagramDictionary.getOrDefault(key, new HashSet<>())
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
     * Retrieves a normalized multiset for a given word.
     *
     * @param word The input word to be processed
     * @return The multiset containing the normalized characters of the word
     */
    private Multiset<Character> getNormalizedMultiset(String word) {
        String lowerCase = word.toLowerCase().replaceAll(ALPHABET_ONLY_REGEX, "");
        Multiset<Character> multiset = HashMultiset.create();

        for (char c: lowerCase.toCharArray()) {
            multiset.add(c);
        }

        return multiset;
    }

    /**
     * Adds a string to the anagram dictionary map.
     *
     * @param sorted   The multiset of characters in the string.
     * @param original The original string.
     */
    private void addStringToAnagramMap(Multiset<Character> sorted, String original) {
        String key = sorted.stream()
                .map(String::valueOf)
                .sorted()
                .collect(Collectors.joining());
        anagramDictionary.computeIfAbsent(key, k -> new HashSet<>()).add(original);
    }

    /**
     * Generates a key for a given Multiset of characters.
     *
     * @param multiset the Multiset of characters
     * @return the generated key as a string
     */
    private String generateKey(Multiset<Character> multiset) {
        return multiset.stream()
                .map(String::valueOf)
                .sorted()
                .collect(Collectors.joining());
    }
}