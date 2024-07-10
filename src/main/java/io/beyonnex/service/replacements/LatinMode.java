package io.beyonnex.service.replacements;

import java.util.Map;
import java.util.stream.Collectors;


/**
 * The LatinMode class is a representation of a mode that can be used to transform
 * an input string following certain "Latin" rules defined in the transformRules map.
 * <p>
 * This class represents the concept of a 'translation' or 'transformation' mode,
 * where each character is replaced according to Latin cultural and lingual norms.
 * <p>
 * For extension, various other linguistic or custom transformation rules can be added
 * to the dictionary. You could also have the translation rule map be loaded from an
 * external configuration file which would allow changing of the rules without modifying code.
 */
public class LatinMode implements Mode {

    /**
     * A map containing transformation rules where each key-value pair represents a character
     * from the input string and its replacement respectively.
     */
    private final Map<Character, String> transformRules = Map.of(
            'i', "j",
            'u', "v",
            'w', "vv"
    );

    /**
     * Returns the name of this mode which is used for representation
     * and identification purposes.
     *
     * @return the name of this mode
     */
    @Override
    public String name() {
        return "LATIN";
    }

    /**
     * Takes an input string and returns a transformed version of it where each character
     * is replaced according to this mode's transformRules.
     * <p>
     * If more complex rules are needed, such as context dependent translations,
     * they can be implemented within this method.
     *
     * @param input the string to be transformed
     * @return the transformed string
     */
    @Override
    public String transform(String input) {
        return input.chars()
                .mapToObj(c -> transformRules.getOrDefault((char) c, String.valueOf((char) c)))
                .collect(Collectors.joining(""));
    }

    /**
     * Returns a string representation of the current mode and its transformation rule.
     * <p>
     * This could be extended to provide more verbose information about the mode
     * and its characteristics.
     *
     * @return the string representation of the current mode and its rules
     */
    @Override
    public String toString() {
        return transformRules.toString();
    }

}