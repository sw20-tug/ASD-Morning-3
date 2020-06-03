package com.asdmorning3.junit;

import com.asdmorning3.basic.Tags;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.test.InterfaceLanguages;
import com.asdmorning3.test.studyInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


public class test005b {

    Vocable vocable1 = null;
    Vocable vocable2 = null;
    Vocable vocable3 = null;
    VocableDictionary dictionary = null;

    @Test
    @DisplayName("Sort Test")
    void sortTest()
    {
        dictionary = new VocableDictionary();
        dictionary.addVocable(new Vocable("b", Vocable.Language.ENG), new Vocable("B", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("c", Vocable.Language.ENG), new Vocable("C", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("a", Vocable.Language.ENG), new Vocable("A", Vocable.Language.GER));

        ArrayList<Vocable> sortedVocables = new ArrayList<>();
        sortedVocables = dictionary.sortVocablesByAlhpabet(dictionary.getVocableList());

        assert(sortedVocables.get(0).getWord(Vocable.Language.ENG).charAt(0) <
                sortedVocables.get(2).getWord(Vocable.Language.ENG).charAt(0));
    }


    @Test
    @DisplayName("Sort JList Test")
    void sortJlistTest()
    {
        dictionary = new VocableDictionary();
        dictionary.addVocable(new Vocable("b", Vocable.Language.ENG), new Vocable("B", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("c", Vocable.Language.ENG), new Vocable("C", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("a", Vocable.Language.ENG), new Vocable("A", Vocable.Language.GER));
        studyInterface interfac = new studyInterface(dictionary, InterfaceLanguages.Languages.DE);

        JList list = new JList();
        String[] strings = {"a", "b", "d", "c"};
        list.setListData(strings);


        list.setModel(interfac.sortJlistAlphabeticallyAsc(list));


        assert(list.getModel().getElementAt(0).toString().charAt(0) <
                list.getModel().getElementAt(1).toString().charAt(0));
    }

    @Test
    @DisplayName("Filter tag Test")
    void filterTagTest()
    {
        dictionary = new VocableDictionary();

        dictionary.addVocable(new Vocable("b", Vocable.Language.ENG), new Vocable("B", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("c", Vocable.Language.ENG), new Vocable("C", Vocable.Language.GER));
        dictionary.addVocable(new Vocable("a", Vocable.Language.ENG), new Vocable("A", Vocable.Language.GER));

        Tags tag = dictionary.createTag("tag1", Color.red);
        Tags tag2 = dictionary.createTag("tag2", Color.black);

        dictionary.addTagToVocable(tag, dictionary.findVocable(Vocable.Language.ENG).get(0));
        dictionary.addTagToVocable(tag2, dictionary.findVocable(Vocable.Language.ENG).get(1));

        studyInterface interfac = new studyInterface(dictionary, InterfaceLanguages.Languages.DE);

        JList list = new JList();
        String[] strings = {"b", "c", "a"};
        list.setListData(strings);

        list.setModel(interfac.filterJlistByTag(list, tag));

        for (int i = 0; i < list.getModel().getSize(); i++)
        {
            Object item = list.getModel().getElementAt(i);
            Vocable x = dictionary.findVocable(item.toString(), Vocable.Language.ENG).get(0);
            assert(dictionary.findVocable(item.toString(), Vocable.Language.ENG).get(0).getTags().get(0).getDescription().equals(tag.getDescription()));
        }

    }
}
