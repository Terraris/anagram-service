package io.beyonnex.service;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static io.beyonnex.service.replacements.ModeType.LATIN;
import static io.beyonnex.service.replacements.ModeType.MODERN;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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
    public void testGetAnagrams_doesContainItselfIfQueriedInOtherCase() {
        AnagramService anagramService = new AnagramService();
        anagramService.areAnagrams("Evil", "Vile");

        Set<String> anagramSetOfEvil = anagramService.getAnagrams("evil");

        assertThat(anagramSetOfEvil)
                .hasSize(2)
                .contains("Evil", "Vile");
    }

    @Test
    public void testGetAnagrams_doesContainItselfIfQueriedInOtherCase2() {
        AnagramService anagramService = new AnagramService();

        anagramService.activateMode(LATIN);

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
    public void testHypotheticalInvocations_providesForFeatureTwo() {
        AnagramService anagramService = new AnagramService();
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
    public void testEmptyStringAnagram() {
        AnagramService anagramService = new AnagramService();

        String a = "";
        String b = "";
        assertThat(anagramService.areAnagrams(a, b)).isTrue();

        Set<String> anagramSetOfEmpty = anagramService.getAnagrams(a);
        assertThat(anagramSetOfEmpty).isEmpty();
    }
}