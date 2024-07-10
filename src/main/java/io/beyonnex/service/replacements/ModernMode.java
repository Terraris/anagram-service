package io.beyonnex.service.replacements;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * The ModernMode class represents a mode that carries out the transformation on
 * the input string based on certain "Modern" replacement rules defined in the transformRules map.
 * <p>
 * This class embodies the notion of a 'transformation' mode, replacing each character in accordance
 * with the modern linguistic and contextual norms.
 * <p>
 * For extending the class, you could consider adding additional transformation rules to the map.
 * The translation rule map could also be configured from an external file, allowing for dynamic rule changes.
 */
public class ModernMode implements Mode {

    /**
     * The map contains transformation rules where each key-value pair represent a character
     * in the input string and its corresponding modern replacement.
     */
    private final Map<Character, Character> transformRules = Map.of(
            'v', 'w',
            's', 'z',
            'c', 'k'
    );

    /**
     * Returns the name "MODERN" which represents the type of mode this implementation represents.
     *
     * @return the name of this mode
     */
    @Override
    public String name() {
        return "MODERN";
    }

    /**
     * Transforms an input string by replacing each character following the rules defined in the transformRules dictionary.
     * <p>
     * More complex translation rule, such as multi-character replacements, can be implemented inside this method.
     *
     * @param input string to be transformed
     * @return the transformed string
     */
    @Override
    public String transform(String input) {
        return input.chars()
                .mapToObj(c -> transformRules.getOrDefault((char) c, (char) c))
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }

    /**
     * Provides a string representation of the currently defined transformation rules.
     * <p>
     * This method could be enhanced to give more detailed information about the characteristics of this mode.
     *
     * @return string representation of the transformation rules
     */
    @Override
    public String toString() {
        return transformRules.toString();
    }
}