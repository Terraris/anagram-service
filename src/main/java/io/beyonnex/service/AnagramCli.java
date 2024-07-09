package io.beyonnex.service;

import io.beyonnex.Main;
import io.beyonnex.service.error.FindrException;
import io.beyonnex.service.replacements.Mode;
import io.beyonnex.service.replacements.ModeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.Set;

import static io.beyonnex.service.Message.ACTIVE_REPLACEMENT_MODES;
import static io.beyonnex.service.Message.ADD_REPLACEMENT_MODE;
import static io.beyonnex.service.Message.ARE_ANAGRAMS;
import static io.beyonnex.service.Message.BACK_TO_MAIN;
import static io.beyonnex.service.Message.BACK_TO_MAIN_MENU;
import static io.beyonnex.service.Message.CHOOSE_OPTION;
import static io.beyonnex.service.Message.CURRENT_REPLACEMENTS;
import static io.beyonnex.service.Message.ENTER_STRING;
import static io.beyonnex.service.Message.ENTER_TEXTS;
import static io.beyonnex.service.Message.ERROR;
import static io.beyonnex.service.Message.EXITING;
import static io.beyonnex.service.Message.HEADER_LINE;
import static io.beyonnex.service.Message.INSTRUCTIONS;
import static io.beyonnex.service.Message.INVALID_OPTION;
import static io.beyonnex.service.Message.INVALID_REPLACEMENT_MODE_ADD;
import static io.beyonnex.service.Message.INVALID_REPLACEMENT_MODE_REMOVE;
import static io.beyonnex.service.Message.KNOWN_ANAGRAMS;
import static io.beyonnex.service.Message.MODE_ACTIVATED;
import static io.beyonnex.service.Message.MODE_DEACTIVATED;
import static io.beyonnex.service.Message.MODE_REPLACEMENTS;
import static io.beyonnex.service.Message.NOT_ANAGRAMS;
import static io.beyonnex.service.Message.NO_ANAGRAMS;
import static io.beyonnex.service.Message.REMOVE_REPLACEMENT_MODE;
import static io.beyonnex.service.Message.WELCOME_TO_FINDER;

/**
 * AnagramCli is a Command Line Interface (CLI) for users to interact with the AnagramService.
 * It gives users the ability to check anagrams, retrieve known anagrams, and modify mode settings.
 */
public class AnagramCli {

    /**
     * Application Logger used for informational, debug, and error message logging.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * runAnagramFinder is the main interaction method of the AnagramCli.
     * It manages user input and output to perform various anagram-related operations.
     * Operations include: checking if two strings are anagrams, fetching known anagrams,
     * adding and removing replacement modes, and exiting the application.
     * Errors and exceptions are also caught and logged in this method.
     */
    public void runAnagramFinder() {
        AnagramService anagramService = new AnagramService();
        Scanner scanner = new Scanner(System.in);

        LOGGER.info(HEADER_LINE.get());
        LOGGER.info(WELCOME_TO_FINDER.get());
        LOGGER.info(CURRENT_REPLACEMENTS.get());
        for (ModeType type : ModeType.values()) {
            Mode mode = type.getInstance();
            LOGGER.info(MODE_REPLACEMENTS.format(mode.name(), mode));
        }

        while (true) {
            try {
                LOGGER.info(ACTIVE_REPLACEMENT_MODES.format(anagramService.getActiveModes()));
                LOGGER.info(CHOOSE_OPTION.get());
                LOGGER.info(INSTRUCTIONS.get());
                String option = scanner.nextLine().trim();

                switch (option) {
                    case "1":
                        LOGGER.info(ENTER_TEXTS.get());
                        String firstText = scanner.nextLine();
                        String secondText = scanner.nextLine();
                        if (anagramService.areAnagrams(firstText, secondText)) {
                            LOGGER.info(ARE_ANAGRAMS.format(firstText, secondText));
                        } else {
                            LOGGER.info(NOT_ANAGRAMS.format(firstText, secondText));
                        }
                        LOGGER.info(BACK_TO_MAIN.get());
                        break;

                    case "2":
                        LOGGER.info(ENTER_STRING.get());
                        String inputString = scanner.nextLine();
                        Set<String> anagrams = anagramService.getAnagrams(inputString);
                        if (anagrams.isEmpty()) {
                            LOGGER.info(NO_ANAGRAMS.format(inputString));
                        } else {
                            LOGGER.info(KNOWN_ANAGRAMS.format(inputString, anagrams));
                        }
                        break;

                    case "3":
                        LOGGER.info(ADD_REPLACEMENT_MODE.get());
                        String modeToAdd = scanner.nextLine().toUpperCase();
                        if (!modeToAdd.equals("LATIN") && !modeToAdd.equals("MODERN")) {
                            throw new FindrException(INVALID_REPLACEMENT_MODE_ADD.format(modeToAdd));
                        }

                        ModeType modeType = ModeType.valueOf(modeToAdd);
                        anagramService.activateMode(modeType);
                        LOGGER.info(MODE_ACTIVATED.format(modeType.name()));
                        break;

                    case "4":
                        LOGGER.info(REMOVE_REPLACEMENT_MODE.get());
                        String modeToRemove = scanner.nextLine().toUpperCase();
                        if (!modeToRemove.equals("LATIN") && !modeToRemove.equals("MODERN")) {
                            throw new FindrException(INVALID_REPLACEMENT_MODE_REMOVE.format(modeToRemove));
                        }

                        ModeType modeTypeToRemove = ModeType.valueOf(modeToRemove);
                        anagramService.deactivateMode(modeTypeToRemove);
                        LOGGER.info(MODE_DEACTIVATED.format(modeTypeToRemove.name()));
                        break;

                    case "5":
                        LOGGER.info(EXITING.get());
                        scanner.close();
                        return;

                    default:
                        throw new FindrException(INVALID_OPTION.format(option));
                }
            } catch (Exception e) {
                LOGGER.error(ERROR.format(e.getMessage()));
                LOGGER.info(BACK_TO_MAIN_MENU.get());
            }
        }
    }
}
