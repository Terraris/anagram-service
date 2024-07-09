package io.beyonnex.service;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AnagramFindrTest {
    private final AnagramFindr anagramFindr = new AnagramFindr();

    @Test
    public void testAreAnagrams_providesForFeatureOne() {
        assertTrue(anagramFindr.areAnagrams("New York Times", "monkeys write"));
        assertTrue(anagramFindr.areAnagrams("Church of Scientology", "rich-chosen goofy cult"));
        assertTrue(anagramFindr.areAnagrams("McDonald's restaurants", "Uncle Sam's standard rot"));
        assertTrue(anagramFindr.areAnagrams("coronavirus", "carnivorous"));
        assertTrue(anagramFindr.areAnagrams("She Sells Sanctuary", "Santa; shy, less cruel"));
        assertTrue(anagramFindr.areAnagrams("evil", "vile"));
        assertTrue(anagramFindr.areAnagrams("a gentleman", "elegant man"));
        assertTrue(anagramFindr.areAnagrams("silent", "listen"));
        assertTrue(anagramFindr.areAnagrams("restful", "fluster"));
        assertTrue(anagramFindr.areAnagrams("cheater", "teacher"));
        assertTrue(anagramFindr.areAnagrams("funeral", "real fun"));
        assertTrue(anagramFindr.areAnagrams("adultery", "true lady"));
        assertTrue(anagramFindr.areAnagrams("forty five", "over fifty"));
        assertTrue(anagramFindr.areAnagrams("Santa", "Satan"));
        assertTrue(anagramFindr.areAnagrams("William Shakespeare", "I am a weakish speller"));
        assertTrue(anagramFindr.areAnagrams("Madam Curie", "Radium came"));
        assertTrue(anagramFindr.areAnagrams("George Bush", "He bugs Gore"));
        assertTrue(anagramFindr.areAnagrams("Tom Marvolo Riddle", "I am Lord Voldemort"));

        assertFalse(anagramFindr.areAnagrams("This is", "not an anagram"));
    }

    @Test
    public void testGetAnagrams_doesContainItselfIfQueriedInOtherCase() {
        anagramFindr.areAnagrams("Evil", "Vile");
        Set<String> anagramSetOfEvil = anagramFindr.getAnagrams("evil");
        assertEquals(2, anagramSetOfEvil.size()); // ist Evil != evil in anagram sprech'?
        assertTrue(anagramSetOfEvil.contains("Evil"));
        assertTrue(anagramSetOfEvil.contains("Vile"));
    }

    @Test
    public void testGetAnagrams_doesNotContainItself() {
        anagramFindr.areAnagrams("silent", "listen");
        Set<String> anagramSetOfListen = anagramFindr.getAnagrams("listen");
        assertEquals(1, anagramSetOfListen.size());
        assertTrue(anagramSetOfListen.contains("silent"));
        assertFalse(anagramSetOfListen.contains("listen"));
    }

    @Test
    public void testHypotheticalInvocations_providesForFeatureTwo() {
        String a = "Evil";
        String b = "Vile";
        String c = "life";
        String d = "live";

        anagramFindr.areAnagrams(a, b);
        anagramFindr.areAnagrams(a, c);
        anagramFindr.areAnagrams(a, d);

        Set<String> anagramSetOfEvil = anagramFindr.getAnagrams(a);
        assertThat(anagramSetOfEvil)
                .isNotNull()
                .hasSize(2)
                .containsOnly(b, d);

        Set<String> anagramSetOfVile = anagramFindr.getAnagrams(b);
        assertThat(anagramSetOfVile)
                .isNotNull()
                .hasSize(2)
                .containsOnly(a, d);

        Set<String> anagramSetOfLife = anagramFindr.getAnagrams(c);
        assertThat(anagramSetOfLife)
                .isNotNull()
                .isEmpty();


    }

    @Test
    public void testDuplicatesInAnagrams() {
        String a = "ana";
        String b = "naa";
        String c = "aan";
        assertTrue(anagramFindr.areAnagrams(a, b));
        assertTrue(anagramFindr.areAnagrams(a, c));

        Set<String> anagramSetOfAna = anagramFindr.getAnagrams(a);
        assertEquals(2, anagramSetOfAna.size());
        assertTrue(anagramSetOfAna.contains(b));
        assertTrue(anagramSetOfAna.contains(c));
        assertFalse(anagramSetOfAna.contains(a));
    }

    @Test
    public void testEmptyStringAnagram() {
        String a = "";
        String b = "";
        assertTrue(anagramFindr.areAnagrams(a, b));

        Set<String> anagramSetOfEmpty = anagramFindr.getAnagrams(a);
        assertTrue(anagramSetOfEmpty.isEmpty());
    }

}