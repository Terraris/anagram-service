package io.beyonnex.service.replacements;

/**
 * The ModeType enum is designed to facilitate the creation of Mode instances.
 * This acts as a factory, each enum value is directly connected with a specific mode implementation,
 * this eliminates the need to call the new keyword within your code, simplifying mode instantiation.
 * <p>
 * If a new mode class implementation is created, you should also add a new enum constant here.
 *
 * @see Mode
 */
public enum ModeType {

    /**
     * Each LatinMode instance creation is associated with this enum constant.
     * LatinMode is a transformation strategy for character-replacement following Latin norms.
     */
    LATIN {
        @Override
        public Mode getInstance() {
            return new LatinMode();
        }
    },
    /**
     * Each ModernMode instance creation is associated with this enum constant.
     * ModernMode provides a transformation strategy of character replacement according to Modern linguistic and contextual rules.
     */
    MODERN {
        @Override
        public Mode getInstance() {
            return new ModernMode();
        }
    };

    /**
     * Abstract function that is implemented by each enum constant to return a new instance of the correct Mode.
     * This allows for switching between modes with ease and promotes the open-closed principle
     * as new Modes can be added without modifying the existing code.
     * This abstract method acts as a factory method, abstracting the instantiation process.
     *
     * @return new instance of Mode implementation
     */
    public abstract Mode getInstance();
}