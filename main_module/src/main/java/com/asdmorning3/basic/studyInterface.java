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
    public JLabel lblLang1;
    public JLabel lblLang2;
    VocableDictionary dictionary;
    JTextField t1;
    HashMap <String, Vocable.Language> language_list = new HashMap();
    Vocable test_2;

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
            language_list.put(language.name(), language);
        }

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

        c.gridx = 1;
        c.gridy = 5;
        list = new JList();
        JScrollPane scrollPane = new JScrollPane(list);

        pane.add(scrollPane, c);

        c.gridx = 0;
        c.gridy = 5;
        t1 = new JTextField();
        pane.add(t1, c);
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
                    vocableList = dictionary.getAllFromLanguage(language_list.get(currentLanguage));
                    int size = vocableList.size();
                    String[] words = new String[size];
                    for(int counter = 0; counter < vocableList.size(); counter++)
                    {
                        words[counter] = vocableList.get(counter).getWord();
                    }
                    final String[] final_list = words;
                    list.setListData(final_list);
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
                Object obj = fromLanguage.getSelectedItem();
                String currentLanguage = String.valueOf(obj);
                if (!event.getValueIsAdjusting()){
                    JList source = (JList)event.getSource();
                    String selected = source.getSelectedValue().toString();
                    HashSet<Vocable> test = dictionary.getVocableList();
                    for(Vocable i : test)
                        if(i.getWord().equals(selected))
                        {
                          t1.setText(i.getTranslation(language_list.get(currentLanguage)).getWord());
                        }
                    }
            }

        });

        frame.setVisible(true);

    }
}
