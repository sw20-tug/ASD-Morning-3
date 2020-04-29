package com.asdmorning3.junit;

import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.basic.studyInterface;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class test004 {
    public test004(){}

    @Test
    @DisplayName("Test")
    public void testStudyInterface()
    {
        VocableDictionary dictionary = new VocableDictionary();
        dictionary.addVocable(new Vocable("hallo", Vocable.Language.GER),
                new Vocable("hello", Vocable.Language.ENG));
        dictionary.addVocable(new Vocable("Katze", Vocable.Language.GER),
                new Vocable("cat", Vocable.Language.ENG));
        dictionary.addVocable(new Vocable("Hund", Vocable.Language.GER),
                new Vocable("dog", Vocable.Language.ENG));

        studyInterface Interface = new studyInterface(dictionary);
        assert(!Interface.lblLang1.getText().equals(""));
        assert(!Interface.lblLang2.getText().equals(""));
    }
}