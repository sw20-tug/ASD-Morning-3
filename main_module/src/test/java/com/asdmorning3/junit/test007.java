package com.asdmorning3.junit;

import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.test.TestEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

public class test007 {
    @Test
    @DisplayName("test vocabulary")
    void test1(){
        VocableDictionary dictionary = new VocableDictionary();
        dictionary.addVocable(new Vocable("hello", Vocable.Language.ENG), new Vocable("hallo", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("test", Vocable.Language.ENG), new Vocable("Test", Vocable.Language.GER), new Vocable("FTest", Vocable.Language.FRA));
        dictionary.addVocable(new Vocable("test1", Vocable.Language.ENG), new Vocable("Test1", Vocable.Language.GER), new Vocable("FTest1", Vocable.Language.FRA));
        dictionary.addVocable(new Vocable("test2", Vocable.Language.ENG), new Vocable("Test2", Vocable.Language.GER), new Vocable("FTest2", Vocable.Language.FRA));
        dictionary.addVocable(new Vocable("test3", Vocable.Language.ENG), new Vocable("Test3", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("test4", Vocable.Language.ENG), new Vocable("Test4", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("test5", Vocable.Language.ENG), new Vocable("Test5", Vocable.Language.GER));

    }
    static Stream<Arguments> stringStream () {
        return Stream.of(Arguments.of("~!hello", true),
                Arguments.of("   ", false),
                Arguments.of("\t\r", false),
                Arguments.of("\thello  !", true),
                Arguments.of("|", true),
                Arguments.of("€", true),
                Arguments.of("^", true),
                Arguments.of("é", true));
    }

    @ParameterizedTest
    @MethodSource("stringStream")
    @DisplayName("test is_human_readable")
    void test2(String string, boolean is_human_readable)
    {
        assert(Vocable.isHumanReadable(string) == is_human_readable);
    }

    @Test
    @DisplayName("test show stats result")
    void test3()
    {
        VocableDictionary dictionary = new VocableDictionary();
        dictionary.addVocable(new Vocable("hello", Vocable.Language.ENG), new Vocable("hallo", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("test", Vocable.Language.ENG), new Vocable("Test", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("test1", Vocable.Language.ENG), new Vocable("Test1", Vocable.Language.GER));
        int percentage = 0;
        int correct = 1;
        int correct_at_first = 2;
        int incorrect = 3;
        int avg_tries = 4;

        ArrayList<Vocable> vocables;
        int repititions = 2;
        Vocable.Language from = Vocable.Language.ENG;
        Vocable.Language to = Vocable.Language.GER;
        vocables = new ArrayList<>(dictionary.getAllFromLanguage(from));
        TestEntity testEntity = new TestEntity(vocables, repititions, from, to);
        Vocable t1 = testEntity.getNextVocable();
        testEntity.guessedLastVocable(true);

        Vocable t2 = testEntity.getNextVocable();
        testEntity.guessedLastVocable(false);
        Vocable t3 = testEntity.getNextVocable();
        testEntity.guessedLastVocable(false);
        Vocable t4 = testEntity.getNextVocable();
        testEntity.guessedLastVocable(false);
        Vocable t5 = testEntity.getNextVocable();
        testEntity.guessedLastVocable(true);
        assert(t2.getWord().equals(t4.getWord()));
        assert(t3.getWord().equals(t5.getWord()));
        ArrayList<String> results = testEntity.showStats();
        System.out.print("check\n");
        assert(results.get(percentage).equals("40"));
        assert(results.get(correct).equals("2"));
        assert(results.get(correct_at_first).equals("1"));
        assert(results.get(incorrect).equals("1"));
        assert(results.get(avg_tries).equals("1,67") || results.get(avg_tries).equals("1.67"));
        assert(results.get(5).equals(t4.getWord()));
        assert(results.size() == 6);
    }
}