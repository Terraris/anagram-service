package io.beyonnex;

import io.beyonnex.service.AnagramCli;

/**
 * The Main class is the main entry point for the application. It reads user inputs
 * and makes necessary calls to the AnagramFindr service in response to the inputs.
 * <p>
 * The application offers options for checking anagrams, getting anagrams of a string,
 * operating (activating, deactivating) the modes of operation to match anagrams,
 * and exiting the application.
 * <p>
 * The application uses LOGGER to display the outcome of user options and any notifications or errors.
 */
public class Main {

    /**
     * The main method is where the program execution begins.
     *
     * @param args Command line arguments provided when the application was invoked (not used)
     */
    public static void main(String[] args) {
        AnagramCli anagramCli = new AnagramCli();
        anagramCli.runAnagramFinder();
    }


}