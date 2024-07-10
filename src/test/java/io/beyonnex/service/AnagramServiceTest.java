package io.beyonnex.service;

import io.beyonnex.service.error.FindrException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static io.beyonnex.service.error.FindrException.INVALID_INPUT_ERROR;
import static io.beyonnex.service.replacements.ModeType.LATIN;
import static io.beyonnex.service.replacements.ModeType.MODERN;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnagramServiceTest {

    private AnagramService anagramService = null;

    @BeforeEach
    public void setup() {
        anagramService = new AnagramService();
        anagramService.clearAllModes();
    }

    @Test
    public void testAreAnagrams_providesForFeatureOne() {
        assertThat(anagramService.areAnagrams("anagram", "nag a ram")).isTrue();

        assertThat(anagramService.areAnagrams("New York Times", "monkeys write")).isTrue();
        assertThat(anagramService.areAnagrams("Church of Scientology", "rich-chosen goofy cult")).isTrue();
        assertThat(anagramService.areAnagrams("McDonald's restaurants", "Uncle Sam's standard rot")).isTrue();
        assertThat(anagramService.areAnagrams("coronavirus", "carnivorous")).isTrue();
        assertThat(anagramService.areAnagrams("She Sells Sanctuary", "Santa; shy, less cruel")).isTrue();

        assertThat(anagramService.areAnagrams("evil", "vile")).isTrue();
        assertThat(anagramService.areAnagrams("a gentleman", "elegant man")).isTrue();
        assertThat(anagramService.areAnagrams("silent", "listen")).isTrue();

        assertThat(anagramService.areAnagrams("restful", "fluster")).isTrue();
        assertThat(anagramService.areAnagrams("cheater", "teacher")).isTrue();
        assertThat(anagramService.areAnagrams("funeral", "real fun")).isTrue();
        assertThat(anagramService.areAnagrams("adultery", "true lady")).isTrue();
        assertThat(anagramService.areAnagrams("forty five", "over fifty")).isTrue();
        assertThat(anagramService.areAnagrams("Santa", "Satan")).isTrue();

        assertThat(anagramService.areAnagrams("William Shakespeare", "I am a weakish speller")).isTrue();
        assertThat(anagramService.areAnagrams("Madam Curie", "Radium came")).isTrue();
        assertThat(anagramService.areAnagrams("George Bush", "He bugs Gore")).isTrue();
        assertThat(anagramService.areAnagrams("Tom Marvolo Riddle", "I am Lord Voldemort")).isTrue();

        assertThat(anagramService.areAnagrams("Anagrams", "Ars magna")).isTrue();

        assertThat(anagramService.areAnagrams("Ave Maria, gratia plena, Dominus tecum", "Virgo serena, pia, munda et immaculata")).isTrue();
        assertThat(anagramService.areAnagrams("Quid est veritas?", "Est vir qui adest")).isTrue();
        assertThat(anagramService.areAnagrams("Elissabet Anglorum Regina", "Multa regnabis ense gloria")).isTrue();
        assertThat(anagramService.areAnagrams("Elizabeth Anglorum Regina", "Multa regnabis ense gloria")).isFalse();

        assertThat(anagramService.areAnagrams("gestat honorem", "Thomas Egerton")).isTrue();
        assertThat(anagramService.areAnagrams("Georgius Ent", "genio surget")).isTrue();
        assertThat(anagramService.areAnagrams("James Stuart", "a just master")).isTrue();

        assertThat(anagramService.areAnagrams("Dame Eleanor Davies", "Never soe mad a ladie")).isTrue();

        assertThat(anagramService.areAnagrams("This is", "not an anagram")).isFalse();
    }

    @Test
    public void testHypotheticalInvocations_providesForFeatureTwo() {
        String a = "Evil";
        String b = "Vile";
        String c = "life";
        String d = "live";

        anagramService.areAnagrams(a, b);
        anagramService.areAnagrams(a, c);
        anagramService.areAnagrams(a, d);

        Set<String> anagramSetOfEvil = anagramService.getAnagrams(a);
        assertThat(anagramSetOfEvil)
                .isNotNull()
                .hasSize(2)
                .containsOnly(b, d);

        Set<String> anagramSetOfVile = anagramService.getAnagrams(b);
        assertThat(anagramSetOfVile)
                .isNotNull()
                .hasSize(2)
                .containsOnly(a, d);

        Set<String> anagramSetOfLife = anagramService.getAnagrams(c);
        assertThat(anagramSetOfLife)
                .isNotNull()
                .isEmpty();


    }

    @Test
    public void testAreAnagrams_throwExceptionWhenNullInputs() {
        assertThrows(FindrException.class, () -> anagramService.areAnagrams(null, "someWord"),
                INVALID_INPUT_ERROR);

        assertThrows(FindrException.class, () -> anagramService.areAnagrams("someWord", null),
                INVALID_INPUT_ERROR);

        assertThrows(FindrException.class, () -> anagramService.areAnagrams(null, null),
                INVALID_INPUT_ERROR);
    }

    @Test
    public void testModes_canBeActivatedAndDeactivated() {
        assertThat(anagramService.getActiveModes()).isEmpty();

        anagramService.activateMode(LATIN);
        assertThat(anagramService.getActiveModes()).containsExactlyInAnyOrder(LATIN);

        anagramService.activateMode(MODERN);
        assertThat(anagramService.getActiveModes()).containsExactlyInAnyOrder(LATIN, MODERN);

        anagramService.deactivateMode(MODERN);
        assertThat(anagramService.getActiveModes()).containsExactlyInAnyOrder(LATIN);
    }

    @Test
    public void testAreAnagrams_differentWhenLatinModeIsApplied() {
        assertThat(anagramService.areAnagrams("Thomas Overburie", "O! O! a busie murther")).isFalse();
        assertThat(anagramService.areAnagrams("ij ij ij ij", "iiii iiii")).isFalse();
        assertThat(anagramService.areAnagrams("wuhuw", "vvvhvvv")).isFalse();

        anagramService.activateMode(LATIN);

        assertThat(anagramService.areAnagrams("Thomas Overburie", "O! O! a busie murther")).isTrue();
        assertThat(anagramService.areAnagrams("ij ij ij ij", "iiii iiii")).isTrue();
        assertThat(anagramService.areAnagrams("wuhuw", "vvvhvvv")).isTrue();
    }

    @Test
    public void testAreAnagrams_differentWhenModernModeIsApplied() {
        assertThat(anagramService.areAnagrams("wwww", "vvvv")).isFalse();
        assertThat(anagramService.areAnagrams("wssw", "vzzv")).isFalse();
        assertThat(anagramService.areAnagrams("wccc", "vkkk")).isFalse();

        anagramService.activateMode(MODERN);

        assertThat(anagramService.areAnagrams("wwww", "vvvv")).isTrue();
        assertThat(anagramService.areAnagrams("wssw", "vzzv")).isTrue();
        assertThat(anagramService.areAnagrams("wccc", "vkkk")).isTrue();
    }

    @Test
    public void testGetAnagrams_doesContainItselfIfQueriedInOtherCase() {
        anagramService.areAnagrams("Evil", "Vile");

        Set<String> anagramSetOfEvil = anagramService.getAnagrams("evil");

        assertThat(anagramSetOfEvil)
                .hasSize(2)
                .contains("Evil", "Vile");
    }

    @Test
    public void testGetAnagrams_theSameStringIsNotRearrangedAndThereforeNotAnAnagram() {
        anagramService.areAnagrams("Evil", "Evil");

        Set<String> anagramSetOfEvil = anagramService.getAnagrams("Evil");

        assertThat(anagramSetOfEvil)
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void testGetAnagrams_doesNotContainItself() {
        anagramService.areAnagrams("silent", "listen");

        Set<String> anagramSetOfListen = anagramService.getAnagrams("listen");

        assertThat(anagramSetOfListen)
                .hasSize(1)
                .contains("silent")
                .doesNotContain("listen");
    }

    @Test
    public void testDuplicatesInAnagrams() {
        String a = "ana";
        String b = "naa";
        String c = "aan";

        assertThat(anagramService.areAnagrams(a, b)).isTrue();
        assertThat(anagramService.areAnagrams(a, c)).isTrue();

        Set<String> anagramSetOfAna = anagramService.getAnagrams(a);
        assertThat(anagramSetOfAna)
                .hasSize(2)
                .contains(b, c)
                .doesNotContain(a);
    }

    @Test
    public void testAreAnagrams_ignoreWhiteSpaceAndCaseSensitivity() {
        assertThat(anagramService.areAnagrams("Listen ", "SiLEnT")).isTrue();
    }

    @Test
    public void testAreAnagrams_withEmptyStrings() {
        assertThat(anagramService.areAnagrams("", "")).isTrue();
        assertThat(anagramService.areAnagrams("", "notempty")).isFalse();
        assertThat(anagramService.areAnagrams("notempty", "")).isFalse();
    }

    @Test
    public void testGetAnagrams_EmptyResultForNonExistenceWord() {
        assertThat(anagramService.getAnagrams("nonexistenceword")).isEmpty();
    }

}