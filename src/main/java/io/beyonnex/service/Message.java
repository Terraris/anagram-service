package io.beyonnex.service;

/**
 * The Message enum is used to store pre-determined messages that the application
 * will use. Each message may optionally contain string formatting specifiers,
 * which will be replaced with arguments provided in the format(Object... args) method.
 */
public enum Message {
    HEADER_LINE("***********************************************************************"),
    WELCOME_TO_FINDER("Welcome to the Anagram Finder!"),
    CURRENT_REPLACEMENTS("Here are the current replacements for each mode:"),
    ACTIVE_REPLACEMENT_MODES("Currently active replacement modes: %s"),
    CHOOSE_OPTION("Choose an option by entering its number and hitting return:"),
    INSTRUCTIONS("""
                    
            *******************************************************************************************************************
            [1] Check if two strings are anagrams
            [2] Get anagrams of a string
            [3] Add anagram match replacement mode: 'LATIN', 'MODERN'
            [4] Remove anagram match replacement mode: 'LATIN', 'MODERN'
            [5] Exit
            *******************************************************************************************************************
            """),
    ENTER_TEXTS("Please enter the two texts (hit enter to confirm after each text):"),
    ARE_ANAGRAMS("%s and %s are anagrams!"),
    NOT_ANAGRAMS("%s and %s are not anagrams!"),
    BACK_TO_MAIN("Back to main menu..."),
    ENTER_STRING("Please enter a string to find its anagrams in the list of known anagrams:"),
    NO_ANAGRAMS("No known anagrams for %s"),
    KNOWN_ANAGRAMS("Known anagrams for %s: %s"),
    ADD_REPLACEMENT_MODE("Please enter 'LATIN' or 'MODERN' to add as a replacement mode:"),
    INVALID_REPLACEMENT_MODE_ADD("' %s' is not a valid replacement mode. Please enter either 'LATIN' or 'MODERN'."),
    MODE_ACTIVATED("Mode '%s' has been activated."),
    MODE_REPLACEMENTS("Mode '%s' with replacements: %s"),
    REMOVE_REPLACEMENT_MODE("Please enter 'LATIN' or 'MODERN' to remove the replacement mode:"),
    INVALID_REPLACEMENT_MODE_REMOVE("' %s' is not a valid replacement mode. Please enter either 'LATIN' or 'MODERN'."),
    MODE_DEACTIVATED("Mode '%s' has been deactivated."),
    EXITING("Exiting..."),
    INVALID_OPTION("' %s' is not a valid option. Please enter a number from 1 to 5."),
    ERROR("Error: %s"),
    BACK_TO_MAIN_MENU("\nBack to main menu...\n");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    /**
     * Returns the message as a string.
     *
     * @return the string of the message
     */
    public String get() {
        return this.message;
    }

    /**
     * Formats the message by replacing format specifiers found in the message
     * with the provided arguments.
     *
     * @param args The arguments to be formatted into the message.
     * @return The fully formatted message.
     */
    public String format(Object... args) {
        return String.format(this.message, args);
    }
}