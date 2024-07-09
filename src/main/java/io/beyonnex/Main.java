package io.beyonnex;

import io.beyonnex.service.AnagramFindr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.Set;

/**
 * Main class of the program. Serves as the entry point into the application.
 * It provides a simple command-line interface where the user can check if two strings are anagrams and
 * get anagrams for a given string from the list of known anagrams, or exit the program.
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);


    /**
     * The main method of the application.
     * Implements a loop that continues until the user chooses to exit.
     * Within the loop, it first displays a menu and asks for user input.
     * Depending on the input, it either calls a method to check if two strings are anagrams,
     * gets anagrams for a given string, or exits the program.
     *
     * @param args - not used.
     */
    public static void main(String[] args) {
        AnagramFindr anagramFindr = new AnagramFindr();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            LOGGER.info("Choose an option (confirm with return):");
            LOGGER.info("[1] Check if two strings are anagrams");
            LOGGER.info("[2] Get anagrams of a string");
            LOGGER.info("[3] Exit");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    LOGGER.info("Enter two texts (hit enter to confirm after each text):");
                    String a = scanner.nextLine();
                    String b = scanner.nextLine();
                    if (anagramFindr.areAnagrams(a, b)) {
                        LOGGER.info("{} and {} are anagrams!", a, b);
                    } else {
                        LOGGER.info("{} and {} are not anagrams!", a, b);
                    }
                    break;

                case "2":
                    LOGGER.info("Enter a string to find its anagrams in the list of known anagrams:");
                    String s = scanner.nextLine();
                    Set<String> anagrams = anagramFindr.getAnagrams(s);
                    if (anagrams.isEmpty()) {
                        LOGGER.info("No known anagrams for {}", s);
                    } else {
                        LOGGER.info("Known anagrams for {}: {}", s, anagrams);
                    }
                    break;

                case "3":
                    LOGGER.info("Exiting...");
                    scanner.close();
                    return;

                default:
                    LOGGER.info("Invalid option");
                    break;
            }
        }
    }
}