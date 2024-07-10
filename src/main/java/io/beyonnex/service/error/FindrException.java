package io.beyonnex.service.error;

/**
 * FindrException is a class that extends the IllegalArgumentException.
 * This custom exception class is used to handle specific exceptions
 * related to the operations performed in the AnagramFinder application.
 */
public class FindrException extends IllegalArgumentException {

    public static final String INVALID_INPUT_ERROR = "Input words must not be null";

    /**
     * Constructs a new FindrException with the specified detail message.
     *
     * @param message the detail message related to the exception.
     */
    public FindrException(String message) {
        super(message);
    }
}