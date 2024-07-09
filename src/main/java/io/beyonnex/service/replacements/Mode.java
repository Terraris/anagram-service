package io.beyonnex.service.replacements;

/**
 * The Mode interface is designed to provide a standard way of defining
 * the various transformation modes that can be used throughout the anagram checker.
 * <p>
 * It provides a blueprint for defining how input strings can be transformed and
 * getting the name of the particular mode. Modes can be anything from "latin" to "case-insensitive".
 * <p>
 * Extending this interface will allow you to create custom transformations for the input string.
 * Each mode should define its unique name that is used for identification purposes and
 * the transformation logic that is applied on the input string.
 * <p>
 * Developers should consider adding new transformations as a new implementation
 * of the Mode interface.
 */
public interface Mode {

    /**
     * Method defined to obtain the name of the mode. The name should uniquely identify the mode.
     * It is used for representation and identification purposes.
     *
     * @return The name of the mode
     */
    String name();

    /**
     * Method defined to transform the input. In its implementation, this method will define how an input
     * string will be transformed based on the complex or simple rules that make up the mode.
     *
     * @param input The input string that needs to be transformed
     * @return The transformed input string
     */
    String transform(String input);
}