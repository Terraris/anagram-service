package io.beyonnex.service;

import io.beyonnex.service.error.FindrException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static io.beyonnex.service.error.FindrException.INVALID_INPUT_ERROR;
import static io.beyonnex.service.replacements.ModeType.LATIN;
import static io.beyonnex.service.replacements.ModeType.MODERN;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnagramServiceTest {


    @Test
    public void testAreAnagrams_providesForFeatureOne() {
        AnagramService anagramService = new AnagramService();

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
        AnagramService anagramService = new AnagramService();

        String a = "evil";
        String b = "vile";
        String c = "life";
        String d = "live";

        // Given these hypothetical invocations
        boolean valueF1_AB = anagramService.areAnagrams(a, b);
        assertThat(valueF1_AB).isTrue();
        boolean valueF1_BC = anagramService.areAnagrams(b, c);
        assertThat(valueF1_BC).isFalse();
        boolean valueF1_AD = anagramService.areAnagrams(a, d);
        assertThat(valueF1_AD).isTrue();

        // f2(A) should return [B, D]
        Set<String> valueF2_A = anagramService.getAnagrams(a);
        assertThat(valueF2_A)
                .isNotNull()
                .hasSize(2)
                .containsOnly(b, d);

        // f2(B) should return [A, D]
        Set<String> valueF2_B = anagramService.getAnagrams(b);
        assertThat(valueF2_B)
                .isNotNull()
                .hasSize(2)
                .containsOnly(a, d);

        // f2(C) should return []
        Set<String> valueF2_C = anagramService.getAnagrams(c);
        assertThat(valueF2_C)
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void testAreAnagrams_throwExceptionWhenNullInputs() {
        AnagramService anagramService = new AnagramService();

        assertThrows(FindrException.class, () -> anagramService.areAnagrams(null, "someWord"),
                INVALID_INPUT_ERROR);

        assertThrows(FindrException.class, () -> anagramService.areAnagrams("someWord", null),
                INVALID_INPUT_ERROR);

        assertThrows(FindrException.class, () -> anagramService.areAnagrams(null, null),
                INVALID_INPUT_ERROR);
    }

    @Test
    public void testModes_canBeActivatedAndDeactivated() {
        AnagramService anagramService = new AnagramService();

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
        AnagramService anagramService = new AnagramService();

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
        AnagramService anagramService = new AnagramService();

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
        AnagramService anagramService = new AnagramService();

        anagramService.areAnagrams("Evil", "Vile");

        Set<String> anagramSetOfEvil = anagramService.getAnagrams("evil");

        assertThat(anagramSetOfEvil)
                .hasSize(2)
                .contains("Evil", "Vile");
    }

    @Test
    public void testGetAnagrams_theSameStringIsNotRearrangedAndThereforeNotAnAnagram() {
        AnagramService anagramService = new AnagramService();

        anagramService.areAnagrams("Evil", "Evil");

        Set<String> anagramSetOfEvil = anagramService.getAnagrams("Evil");

        assertThat(anagramSetOfEvil)
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void testGetAnagrams_doesNotContainItself() {
        AnagramService anagramService = new AnagramService();

        anagramService.areAnagrams("silent", "listen");

        Set<String> anagramSetOfListen = anagramService.getAnagrams("listen");

        assertThat(anagramSetOfListen)
                .hasSize(1)
                .contains("silent")
                .doesNotContain("listen");
    }

    @Test
    public void testDuplicatesInAnagrams() {
        AnagramService anagramService = new AnagramService();

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
        AnagramService anagramService = new AnagramService();

        assertThat(anagramService.areAnagrams("Listen ", "SiLEnT")).isTrue();
    }

    @Test
    public void testAreAnagrams_withEmptyStrings() {
        AnagramService anagramService = new AnagramService();

        assertThat(anagramService.areAnagrams("", "")).isTrue();
        assertThat(anagramService.areAnagrams("", "notempty")).isFalse();
        assertThat(anagramService.areAnagrams("notempty", "")).isFalse();
    }

    @Test
    public void testGetAnagrams_EmptyResultForNonExistenceWord() {
        AnagramService anagramService = new AnagramService();

        assertThat(anagramService.getAnagrams("nonexistenceword")).isEmpty();
    }

}