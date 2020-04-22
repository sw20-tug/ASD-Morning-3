package com.asdmorning3.basic;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.Vocable.Language;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.test.InterfaceLanguages;
import junit.runner.Version;
import java.lang.*;


public class studyInterface {

    static JFrame frame;
    private static JDialog d;
    static JList list;
    JLabel lblLang1;
    JLabel lblLang2;
    InterfaceLanguages inter;
    VocableDictionary dictionary;

    private java.util.List<Vocable> vocableList;

    public studyInterface(VocableDictionary dictionary_) {

        dictionary = dictionary_;

        frame = new JFrame("Study Interface");
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        frame.setSize(600, 600);

        ArrayList<String> languages = new ArrayList<String>();
        for (Vocable.Language language: Vocable.Language.class.getEnumConstants())
        {
            languages.add(language.name());
        }
        vocableList = dictionary.getAllFromLanguage(Language.ENG);
        int size = vocableList.size();
        String[] eng_words = new String[size];
        for(int counter = 0; counter < vocableList.size(); counter++)
        {
            eng_words[counter] = vocableList.get(counter).getWord();
        }
        vocableList = dictionary.getAllFromLanguage(Language.GER);
        size = vocableList.size();
        String[] ger_words = new String[size];
        for(int counter = 0; counter < vocableList.size(); counter++)
        {
            ger_words[counter] = vocableList.get(counter).getWord();
        }

        //String[] languages = {"English", "German"};
        final String[] english = eng_words;
        final String[] german = ger_words;
        final JComboBox fromLanguage = new JComboBox(new DefaultComboBoxModel<String>(languages.toArray(new String[0])));
        final JComboBox toLanguage = new JComboBox(new DefaultComboBoxModel<String>(languages.toArray(new String[0])));
        final JComboBox setAnotherLanguage;

        lblLang1 = new JLabel("From:");
        lblLang2 = new JLabel("To:");
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = .05;
        c.insets = new Insets(10, 55, 0, 100);
        pane.add(lblLang1, c);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 0, 55);
        pane.add(lblLang2, c);

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 55, 10, 100);
        pane.add(fromLanguage, c);

        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 10, 55);
        pane.add(toLanguage, c);


        JButton button = new JButton("Show vocabulary");
        c.gridx = 1;
        c.gridy = 4;
        pane.add(button, c);

        c.gridx = 0;
        c.gridy = 5;
        list = new JList();
        JScrollPane scrollPane = new JScrollPane(list);

        pane.add(scrollPane, c);

        frame.add(pane);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Object obj = toLanguage.getSelectedItem();
                String currentLanguage = String.valueOf(obj);

                Object obj2 = fromLanguage.getSelectedItem();
                String secondCurrentLanguage = String.valueOf(obj2);

                if(currentLanguage != secondCurrentLanguage) {
                    if (currentLanguage == "ENG") {
                        list.setListData(english);
                    }
                    else if(currentLanguage == "GER") {
                        list.setListData(german);
                    }
                }
                else
                    JOptionPane.showMessageDialog(frame,
                            "Select two different languages!",
                            "WARNING",
                            JOptionPane.WARNING_MESSAGE);

            }

        });


        list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                Object obj = toLanguage.getSelectedItem();
                String currentLanguage = String.valueOf(obj);
                if (!event.getValueIsAdjusting()){
                    JList source = (JList)event.getSource();
                    String selected = source.getSelectedValue().toString();
                    if (currentLanguage == "ENG") {
                        int index = 0;
                        for(int counter = 0; counter < english.length; counter++)
                        {
                            if(english[counter].equals(selected))
                            {
                                index = counter;
                                break;
                            }
                        }
                        vocableList = dictionary.getAllFromLanguage(Language.GER);
                        Vocable eng = vocableList.get(index);
                        JOptionPane.showMessageDialog(frame, eng.getWord());
                    }
                    else if(currentLanguage == "GER") {
                        int index = 0;
                        for(int counter = 0; counter < german.length; counter++)
                        {
                            if(german[counter].equals(selected))
                            {
                                index = counter;
                                break;
                            }
                        }
                        vocableList = dictionary.getAllFromLanguage(Language.ENG);
                        Vocable ger = vocableList.get(index);
                        JOptionPane.showMessageDialog(frame, ger.getWord());
                    }
                }
            }

        });

        frame.setVisible(true);

    }
}
