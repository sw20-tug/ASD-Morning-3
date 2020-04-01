package com.asdmorning3.basic;

//import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;

public class GUI {

    VocableDictionary vcb;
    JFrame frame;
    JPanel pane;
    JButton btnSubmit;
    JTextField txtFld1;
    JTextField txtFld2;
    JLabel lblLang1;
    JLabel lblLang2;
    JLabel lblWord1;
    JLabel lblWord2;
    public JComboBox<Vocable.Language> comboBoxLang1;
    public JComboBox<Vocable.Language> comboBoxLang2;

    public GUI(VocableDictionary v)
    {
        frame = new JFrame("Vocabulary Trainer");
        vcb = v;
        pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        btnSubmit = new JButton("Add");
        txtFld1 = new JTextField();
        txtFld2 = new JTextField();
        lblLang1 = new JLabel("Language:");
        lblLang2 = new JLabel("Language:");
        lblWord1 = new JLabel("Word:");
        lblWord2 = new JLabel("Word:");
        comboBoxLang1 = new JComboBox<>(Vocable.Language.values());
        comboBoxLang2 = new JComboBox<>(Vocable.Language.values());

        frame.setSize(600, 600);

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
        pane.add(comboBoxLang1, c);
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 10, 55);
        pane.add(comboBoxLang2, c);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(10, 55, 0, 100);
        pane.add(lblWord1, c);
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(10, 0, 0, 55);
        pane.add(lblWord2, c);

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(10, 55, 0, 100);
        pane.add(txtFld1, c);
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(10, 0, 0, 55);
        pane.add(txtFld2, c);

        c.gridx = 1;
        c.gridy = 4;
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    vcb.addVocable(new Vocable(txtFld1.getText(), (Vocable.Language) Objects.requireNonNull(comboBoxLang1.getSelectedItem())),
                            new Vocable(txtFld2.getText(), (Vocable.Language) Objects.requireNonNull(comboBoxLang2.getSelectedItem())));
                }
                catch(NullPointerException ex)
                {
                    System.out.println("one of the objects is null");
                }
             }
        });
        pane.add(btnSubmit, c);

        frame.add(pane);
        frame.setVisible(true);
    }

    /* testing
    public static void main(String args[])
    {
        VocableDictionary d = new VocableDictionary();
        d.addVocable(new Vocable("hallo", Vocable.Language.GER),
                new Vocable("hello", Vocable.Language.ENG));
        GUI g = new GUI(d);
    }*/
}
