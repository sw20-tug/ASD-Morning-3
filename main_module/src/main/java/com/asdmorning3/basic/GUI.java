package com.asdmorning3.basic;


import com.asdmorning3.components.VocableOverview;
import com.asdmorning3.test.InterfaceLanguages;
import com.asdmorning3.test.studyInterface;
import com.asdmorning3.test.TestVocabulary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class GUI {

    VocableDictionary vcb;
    JFrame frame;
    JTabbedPane tabbedPane;
    JPanel pane;
    JButton btnSubmit;
    JMenuBar menuBar;

    JMenu menuFile;
    JMenuItem itemSave;
    JMenuItem itemLoad;
    InterfaceLanguages languages;

    JMenu menuLang;
    HashMap<JMenuItem, InterfaceLanguages.Languages> itemLangs;

    JTextField txtFld1;
    JTextField txtFld2;
    JLabel lblLang1;
    JLabel lblLang2;
    JLabel lblWord1;
    JLabel lblWord2;
    JLabel lblIntLang;
    public JComboBox<Vocable.Language> comboBoxLang1;
    public JComboBox<Vocable.Language> comboBoxLang2;
    InterfaceLanguages.Languages lang;

    VocableOverview overview;
    studyInterface study;
    TestVocabulary test;

    public GUI(VocableDictionary v)
    {
        languages = new InterfaceLanguages();
        vcb = v;
        tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        //btnOverview = new JButton();
        btnSubmit = new JButton();


        menuLang = new JMenu();
        itemLangs = new HashMap<>();

        menuBar = new JMenuBar();
        menuFile = new JMenu();
        itemSave = new JMenuItem();
        itemLoad = new JMenuItem();
        //btnSave = new JButton();
        txtFld1 = new JTextField();
        txtFld2 = new JTextField();
        lblLang1 = new JLabel();
        lang = (InterfaceLanguages.Languages) InterfaceLanguages.Languages.DE;
        overview = new VocableOverview(vcb, lang, this);
        study = new studyInterface(vcb, lang);
        test = new TestVocabulary(vcb, lang);
        frame = new JFrame();
        lblLang2 = new JLabel();
        lblWord1 = new JLabel();
        lblWord2 = new JLabel();
        lblIntLang = new JLabel();
        comboBoxLang1 = new JComboBox<>(Vocable.Language.values());
        comboBoxLang2 = new JComboBox<>(Vocable.Language.values());
        //i really dislike having to change language when starting
        comboBoxLang1.setSelectedIndex(0);
        comboBoxLang2.setSelectedIndex(1);

        frame.setSize(600, 600);

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,35,111,0);
        pane.add(lblIntLang, c);
        c.gridx = 1;
        c.insets = new Insets(0,0,11,0);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy++;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = .05;
        c.insets = new Insets(10, 55, 0, 100);
        pane.add(lblLang1, c);
        c.gridx = 1;
        c.insets = new Insets(10, 0, 0, 55);
        pane.add(lblLang2, c);
        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 55, 10, 100);
        pane.add(comboBoxLang1, c);
        c.gridx = 1;
        c.insets = new Insets(10, 0, 10, 55);
        pane.add(comboBoxLang2, c);

        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 55, 0, 100);
        pane.add(lblWord1, c);
        c.gridx = 1;
        c.insets = new Insets(10, 0, 0, 55);
        pane.add(lblWord2, c);

        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 55, 0, 100);
        pane.add(txtFld1, c);
        c.gridx = 1;
        c.insets = new Insets(10, 0, 0, 55);
        pane.add(txtFld2, c);


        //study interface
        //vocabulary overview

        c.gridx = 1;
        c.gridy++;
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    vcb.addVocable(new Vocable(txtFld1.getText(), (Vocable.Language) Objects.requireNonNull(comboBoxLang1.getSelectedItem())),
                            new Vocable(txtFld2.getText(), (Vocable.Language) Objects.requireNonNull(comboBoxLang2.getSelectedItem())));
                    txtFld1.setText("");
                    txtFld2.setText("");
                }
                catch(NullPointerException | IllegalArgumentException ex)
                {
                    //System.out.println("(btnGUI)one of the objects is null");
                    //System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(frame,
                            translate(ex.getMessage()),
                            "WARNING",
                            JOptionPane.WARNING_MESSAGE);
                }
                refreshTabs();
            }
        });
        pane.add(btnSubmit, c);

        c.gridx = 2;
        c.gridy = 0;
        /*btnOverview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new VocableOverview(vcb, InterfaceLanguages.Languages.DE);
                        }
                    });
                }
                catch(NullPointerException ex)
                {
                    System.out.println("one of the objects is null");
                }
            }
        });*/
        c.insets = new Insets(10, 0, 0, 55);
        //pane.add(btnOverview, c);

        itemSave.addActionListener(actionEvent -> {
            try {
                JFileChooser tosave = new JFileChooser();
                if(tosave.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
                {
                    vcb.save(tosave.getSelectedFile().getPath());
                }

            }
            catch(NullPointerException | IOException ex)
            {
                System.out.println("(menuSaveGUI)one of the objects is null");
            }
        });
        menuFile.add(itemSave);

        itemLoad.addActionListener(actionEvent -> {
            try {
                JFileChooser toload = new JFileChooser();
                if(toload.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
                {
                    vcb.load(toload.getSelectedFile().getPath());
                    refreshTabs();
                }

            }
            catch(NullPointerException | IOException | ClassNotFoundException ex)
            {
                System.out.println("one of the objects is null");
            }
        });
        menuFile.add(itemLoad);


        for(InterfaceLanguages.Languages i : InterfaceLanguages.Languages.class.getEnumConstants())
        {
            JMenuItem temp = new JMenuItem(i.toString());
            temp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    lang = itemLangs.get(temp);
                    setIntLang(lang);
                    overview.setIntLang(lang);
                    study.setIntLang(lang);
                    test.setIntLang(lang);
                }
            });
            itemLangs.put(temp, i);
            menuLang.add(temp);
        }

        menuBar.add(menuFile);
        menuBar.add(menuLang);

        frame.add(menuBar, BorderLayout.NORTH);
        tabbedPane.addTab(languages.getString(lang, "add"), pane);
        tabbedPane.addTab(languages.getString(lang, "overview"), overview.getContent());
        tabbedPane.addTab(languages.getString(lang, "study-interface"), study.getContent());
        tabbedPane.addTab(languages.getString(lang, "test"), test.getContent());
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    JFileChooser tosave = new JFileChooser();
                    if(tosave.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
                    {
                        vcb.save(tosave.getSelectedFile().getPath());
                    }

                }
                catch(NullPointerException | IOException ex)
                {
                    System.out.println("(windowCloseGUI)one of the objects is null");
                }
                super.windowClosing(e);
                System.exit(0);
            }
        });

        setIntLang(this.lang);
        frame.setVisible(true);
    }

    //testing
    public static void main(String args[])
    {
        VocableDictionary d = new VocableDictionary();
        d.addVocable(new Vocable("hallo", Vocable.Language.GER),
                new Vocable("hello", Vocable.Language.ENG));
        GUI g = new GUI(d);
    }

    public String translate(String message)
    {
        if(lang == InterfaceLanguages.Languages.EN)
            return message;
        else if(lang == InterfaceLanguages.Languages.DE)
            return "Das Wort muss mindestens eins sein.";
        else
            return "Le mot doit être au moins de longueur un.";
    }

    private void setIntLang(InterfaceLanguages.Languages lang)
    {
        menuLang.setText(languages.getString(lang, "interfacelanguage"));
        lblLang1.setText(languages.getString(lang, "language"));
        lblLang2.setText(languages.getString(lang, "language"));
        lblWord1.setText(languages.getString(lang, "word"));
        lblWord2.setText(languages.getString(lang, "word"));
        btnSubmit.setText(languages.getString(lang, "add"));
        itemSave.setText(languages.getString(lang, "save"));
        frame.setTitle(languages.getString(lang, "vocab-trainer"));
        menuFile.setText(languages.getString(lang, "file"));
        itemLoad.setText(languages.getString(lang, "load"));
        tabbedPane.setTitleAt(0, languages.getString(lang, "add"));
        tabbedPane.setTitleAt(1, languages.getString(lang, "overview"));
        tabbedPane.setTitleAt(2, languages.getString(lang, "study"));
        tabbedPane.setTitleAt(3, languages.getString(lang, "test"));
        overview.setIntLang(lang);
    }

    private void refreshTabs()
    {
        Component component;
        overview = new VocableOverview(vcb, lang, this);
        study = new studyInterface(vcb, lang);
        tabbedPane.setComponentAt(1, overview.getContent());
        tabbedPane.setComponentAt(2, study.getContent());
    }

    public void update(VocableDictionary best){
        test.updateDict(best);
    }
}
