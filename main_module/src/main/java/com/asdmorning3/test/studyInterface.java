package com.asdmorning3.test;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


class studyInterface {

    static JFrame frame;
    private static JDialog d;
    static JList list;
    JLabel lblLang1;
    JLabel lblLang2;

    public studyInterface() {

        frame = new JFrame("Study Interface");
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        frame.setSize(600, 600);


        String[] languages = {"English", "German"};
        final String[] english = {"house", "dog", "cat", "mouse"};
        final String[] german = {"Haus", "Hund", "Katze", "Maus"};
        final JComboBox fromLanguage = new JComboBox(languages);
        final JComboBox toLanguage = new JComboBox(languages);
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
                //d = new JDialog(frame, currentLanguage);
                //d.setSize(400, 400);

                if(currentLanguage == "English") {
                    list.setListData(english);
                }
                else
                    list.setListData(german);

                //pane.add(list, c);

                //d.add(list);
                //d.setVisible(true);
            }

        });

            /*
        list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()){
                    JList source = (JList)event.getSource();
                    String selected = source.getSelectedValue().toString();
                }
            }

        }); */

        frame.setVisible(true);

    }
}
