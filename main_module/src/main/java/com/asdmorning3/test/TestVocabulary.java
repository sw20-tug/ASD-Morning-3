package com.asdmorning3.test;

import com.asdmorning3.basic.Tags;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TestVocabulary {
    static JFrame frame;
    private static JDialog d;
    static JList list_vocabs;
    static JList list_test;
    public JLabel lblVocableList = new JLabel();
    public JLabel lblTestList = new JLabel();
    public JLabel lblRepetition = new JLabel();
    public JLabel lblLanguages = new JLabel();
    public JButton button_start = new JButton();
    public JButton button_show = new JButton();
    public JButton button_add_all = new JButton();
    public JButton button_remove_all = new JButton();
    public JComboBox<Vocable.Language> comboBoxFromLang;
    public JComboBox<Vocable.Language> comboBoxToLang;
    public JComboBox<String> comboBoxTags;
    public JComboBox<String> comboBoxRating;
    public JTextField txtNumber = new JTextField();
    VocableDictionary dictionary;
    InterfaceLanguages.Languages interface_languages;
    InterfaceLanguages languages;
    //JTextField t1;
    HashMap<String, Vocable.Language> language_list = new HashMap();
    ArrayList<Vocable> vocab_array = new ArrayList<>();
    ArrayList<Vocable> test_array = new ArrayList<>();

    private java.util.List<Vocable> vocableList;

    public TestVocabulary(VocableDictionary dictionary_, InterfaceLanguages.Languages lang) {
        String select_all = "ALL";
        languages = new InterfaceLanguages();
        dictionary = dictionary_;
        interface_languages = lang;
        frame = new JFrame("Test Interface");
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        ArrayList<String> languages = new ArrayList<String>();
        for (Vocable.Language language : Vocable.Language.class.getEnumConstants()) {
            languages.add(language.name());
            language_list.put(language.name(), language);
        }

        comboBoxFromLang = new JComboBox(new DefaultComboBoxModel<String>(languages.toArray(new String[0])));
        comboBoxToLang = new JComboBox(new DefaultComboBoxModel<String>(languages.toArray(new String[0])));
        comboBoxTags = new JComboBox(dictionary.getTagsString().toArray(new String[0]));
        comboBoxTags.addItem(select_all);

        comboBoxRating = new JComboBox(Vocable.Difficulty.values());
        comboBoxRating.addItem(select_all);

        comboBoxFromLang.setSelectedIndex(0);
        comboBoxToLang.setSelectedIndex(1);

        final JComboBox setAnotherLanguage;

        frame.setSize(600, 600);
        c.insets = new Insets(10, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 0;
        pane.add(comboBoxTags, c);

        c.gridx = 1;
        c.gridy = 0;
        pane.add(comboBoxRating, c);

        //c.anchor = GridBagConstraints.FIRST_LINE_START;

        c.gridx = 2;
        c.gridy = 0;
        pane.add(button_show, c);

        c.gridx = 0;
        c.gridy = 1;
        pane.add(lblLanguages, c);

        c.gridx = 1;
        c.gridy = 1;
        pane.add(comboBoxFromLang, c);

        c.gridx = 2;
        c.gridy = 1;
        pane.add(comboBoxToLang, c);

        c.gridx = 0;
        c.gridy = 2;
        pane.add(lblVocableList, c);

        c.gridx = 2;
        c.gridy = 2;
        pane.add(lblTestList, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 5;
        list_vocabs = new JList();
        JScrollPane scroll_vocabs = new JScrollPane(list_vocabs);
        scroll_vocabs.setPreferredSize(new Dimension(235, 170));
        pane.add(scroll_vocabs, c);

        c.gridx = 2;
        c.gridy = 3;
        c.gridheight = 5;
        list_test = new JList();
        JScrollPane scroll_test = new JScrollPane(list_test);
        scroll_test.setPreferredSize(new Dimension(235, 170));
        pane.add(scroll_test, c);
        c.insets = new Insets(10, 0, 10, 0);
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 9;
        pane.add(button_add_all, c);

        c.gridx = 2;
        c.gridy = 9;
        pane.add(button_remove_all, c);

        c.gridx = 0;
        c.gridy = 10;
        pane.add(lblRepetition, c);

        c.gridx = 0;
        c.gridy = 11;
        txtNumber.setPreferredSize(new Dimension(30, 20));
        pane.add(txtNumber, c);

        c.gridx = 2;
        c.gridy = 11;
        pane.add(button_start, c);


        frame.add(pane);
        button_show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = comboBoxFromLang.getSelectedItem();
                String currentLanguage = String.valueOf(obj);

                Object obj2 = comboBoxToLang.getSelectedItem();
                String secondCurrentLanguage = String.valueOf(obj2);

                String rating = comboBoxRating.getSelectedItem().toString();
                String tags = comboBoxTags.getSelectedItem().toString();

                if (currentLanguage != secondCurrentLanguage) {
                    ArrayList<Vocable> vocabs_all = new ArrayList<>(dictionary.getAllFromLanguage(language_list.get(currentLanguage)));
                    vocab_array = new ArrayList<>();
                    for(Vocable v : vocabs_all){
                        if(v.getLanguage() != Vocable.Language.GER)
                            v = v.getTranslation(Vocable.Language.GER);
                        //rating
                        if(!rating.equals(select_all)){
                            if(!v.getDifficultyString().toUpperCase().equals(rating)) {
                                continue;
                            }
                        }
                        //tags
                        if(!tags.equals(select_all)){
                            for(Tags t : v.getTags()){
                                if(t.getDescription().equals(tags)){
                                    vocab_array.add(v);
                                    continue;
                                }
                            }
                        }
                        else{
                            vocab_array.add(v);
                        }
                    }
                    int size = vocab_array.size();
                    String[] words = new String[size];
                    for (int counter = 0; counter < vocab_array.size(); counter++) {
                        words[counter] = vocab_array.get(counter).getWord();
                    }
                    final String[] final_list = words;
                    list_vocabs.setListData(final_list);
                } else
                    JOptionPane.showMessageDialog(frame,
                            "Select two different languages!",
                            "WARNING",
                            JOptionPane.WARNING_MESSAGE);
            }

        });

        button_add_all.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                test_array = (ArrayList<Vocable>)vocab_array.clone();
                String[] words = new String[test_array.size()];
                for (int counter = 0; counter < test_array.size(); counter++) {
                    words[counter] = test_array.get(counter).getWord();
                }
                final String[] final_list = words;
                list_vocabs.setListData(final_list);
                printTestVocabs();
            }

        });

        button_remove_all.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                test_array.clear();
                String[] words = new String[0];

                final String[] final_list = words;
                printTestVocabs();
            }
        });

        button_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(test_array.size() == 0)
                    return;

                JFrame frame2 = new JFrame("Test");
                JPanel pane = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();

                JTextField vocable_field = new JTextField();
                JTextField translation_field = new JTextField();
                JButton button_show = new JButton();
                JButton button_right = new JButton();
                JButton button_wrong = new JButton();

                InterfaceLanguages languages;
                languages = new InterfaceLanguages();

                button_show.setText(languages.getString(lang, "show"));
                button_right.setText(languages.getString(lang, "right"));
                button_wrong.setText(languages.getString(lang, "wrong"));

                frame2.setSize(300, 500);

                c.gridx = 0;
                c.gridy = 0;
                c.gridwidth = 2;
                c.insets = new Insets(20, 20, 20, 20);
                vocable_field.setPreferredSize(new Dimension(220, 50));
                vocable_field.setEditable(false);
                pane.add(vocable_field, c);

                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 2;
                //button_show.setPreferredSize(new Dimension(75, 25));
                pane.add(button_show, c);

                c.gridx = 0;
                c.gridy = 2;
                c.gridwidth = 2;
                translation_field.setPreferredSize(new Dimension(220, 50));
                translation_field.setEditable(false);
                pane.add(translation_field, c);

                c.gridx = 0;
                c.gridy = 3;
                c.gridwidth = 1;
                //button_right.setPreferredSize(new Dimension(75, 25));
                pane.add(button_right, c);

                c.gridx = 1;
                c.gridy = 3;
                c.gridwidth = 1;
                //button_wrong.setPreferredSize(new Dimension(75, 25));
                pane.add(button_wrong, c);

                frame2.add(pane);
                frame2.setVisible(true);

                int reps;
                try
                {
                    reps = Integer.parseInt(txtNumber.getText().trim());
                }
                catch (NumberFormatException nfe)
                {
                    reps = 1;
                }
                Vocable.Language from = Vocable.Language.valueOf(comboBoxFromLang.getSelectedItem().toString());
                Vocable.Language to = Vocable.Language.valueOf(comboBoxToLang.getSelectedItem().toString());
                TestEntity ti = new TestEntity(test_array, reps, from, to);

                Vocable nextVocable = ti.getNextVocable();
                vocable_field.setText(nextVocable.getWord(from));
                vocable_field.setHorizontalAlignment(JTextField.CENTER);
                vocable_field.setForeground(Color.BLACK);
                translation_field.setText(nextVocable.getWord(to));
                translation_field.setForeground(translation_field.getBackground());
                translation_field.setHorizontalAlignment(JTextField.CENTER);

                button_show.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        translation_field.setForeground(Color.BLACK);
                    }
                });

                button_right.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ti.guessedLastVocable(true);

                        Vocable nextVocable = ti.getNextVocable();
                        if(ti.getTestFinished())
                        {
                            frame2.dispose();
                            showResults(ti, lang);
                            return;
                        }

                        vocable_field.setText(nextVocable.getWord(from));
                        translation_field.setText(nextVocable.getWord(to));
                        translation_field.setForeground(translation_field.getBackground());


                    }
                });

                button_wrong.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ti.guessedLastVocable(false);
                        if(ti.getTestFinished())
                        {
                            frame2.dispose();
                            showResults(ti, lang);
                            return;
                        }
                        Vocable nextVocable = ti.getNextVocable();
                        vocable_field.setText(nextVocable.getWord(from));
                        translation_field.setText(nextVocable.getWord(to));
                        translation_field.setForeground(translation_field.getBackground());

                    }
                });



            }
        });

        list_vocabs.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                Object obj = comboBoxFromLang.getSelectedItem();
                String currentLanguage = String.valueOf(obj);
                if (!event.getValueIsAdjusting()) {
                    JList source = (JList) event.getSource();
                    String selected = source.getSelectedValue().toString();
                    HashSet<Vocable> test = dictionary.getVocableList();
                    for (Vocable i : test)
                        if (i.getWord().equals(selected)) {
                            if(test_array.contains(i))
                            {
                                return;
                            }
                            test_array.add(i);
                        }
                    printTestVocabs();
                }
            }

        });



        list_test.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                Object obj = comboBoxToLang.getSelectedItem();
                String currentLanguage = String.valueOf(obj);
                if (!event.getValueIsAdjusting()) {
                    JList source = (JList) event.getSource();
                    String selected = source.getSelectedValue().toString();
                    HashSet<Vocable> test = dictionary.getVocableList();
                    for (Vocable i : test)
                        if (i.getWord().equals(selected)) {
                        }
                }
            }

        });
        setIntLang(lang);

    }

    public void setIntLang(InterfaceLanguages.Languages lang) {
        lblVocableList.setText(languages.getString(lang, "vocablist"));
        lblTestList.setText(languages.getString(lang, "testlist"));
        lblRepetition.setText(languages.getString(lang, "repetition"));
        lblLanguages.setText(languages.getString(lang, "languages"));
        frame.setTitle(languages.getString(lang, "test"));
        button_start.setText(languages.getString(lang, "start"));
        button_show.setText(languages.getString(lang, "show"));
        button_add_all.setText(languages.getString(lang, "addall"));
        button_remove_all.setText(languages.getString(lang, "removeall"));
    }

    public void printTestVocabs()
    {
        Object obj = comboBoxFromLang.getSelectedItem();
        String currentLanguage = String.valueOf(obj);

        int size = test_array.size();
        String[] words = new String[size];
        for (int counter = 0; counter < test_array.size(); counter++) {
            words[counter] = test_array.get(counter).getWord();
        }
        final String[] final_list = words;
        list_test.setListData(final_list);

    }

    public void setVisible(boolean visible)
    {
        frame.setVisible(visible);
    }

    public Component getContent()
    {
        return frame.getContentPane();
    }

    public void showResults(TestEntity ti, InterfaceLanguages.Languages lang)
    {
        ArrayList<String> results = new ArrayList<>();
        results = ti.showStats();
        JFrame frame3 = new JFrame("Test results");
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel accuracy = new JLabel();
        JLabel vocable_guessed = new JLabel();
        JLabel vocable_guessed_first_try = new JLabel();
        JLabel vocable_wrong = new JLabel();
        JLabel average_tries = new JLabel();
        JLabel learn_again = new JLabel();
        JTextArea worst_vocables = new JTextArea(2, 20);

        frame3.setSize(300, 500);

        accuracy.setText(languages.getString(lang, "accuracy") + " " + results.get(0) + "%");
        vocable_guessed.setText(languages.getString(lang, "guessed") + " " + results.get(1));
        vocable_guessed_first_try.setText(languages.getString(lang, "guessedfirst") + " " + results.get(2));
        vocable_wrong.setText(languages.getString(lang, "notguessed") + " " + results.get(3));
        average_tries.setText(languages.getString(lang, "averagetries") + " " + results.get(4));
        learn_again.setText(languages.getString(lang, "worstvocabs"));
        worst_vocables.setText(results.get(5));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 10, 0);
        pane.add(accuracy, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        pane.add(vocable_guessed, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        pane.add(vocable_guessed_first_try, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        pane.add(vocable_wrong, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        pane.add(average_tries, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        pane.add(learn_again, c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 0);
        worst_vocables.setFont(UIManager.getFont("Label.font"));
        worst_vocables.setBorder(UIManager.getBorder("Label.border"));
        worst_vocables.setBackground(frame3.getBackground());
        worst_vocables.setWrapStyleWord(true);
        worst_vocables.setLineWrap(true);
        worst_vocables.setEditable(false);
        pane.add(worst_vocables, c);

        frame3.add(pane);
        frame3.setVisible(true);
    }

    public void updateDict(VocableDictionary newx){
        dictionary = newx;
        comboBoxTags.removeAllItems();
        comboBoxTags.addItem("ALL");
        for(String s : dictionary.getTagsString()){
            comboBoxTags.addItem(s);
        }

        System.out.println("updated TEST");
    }
}

