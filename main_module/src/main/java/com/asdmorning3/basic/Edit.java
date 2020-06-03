package com.asdmorning3.basic;

import com.asdmorning3.test.InterfaceLanguages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Edit {

    VocableDictionary vocab_dic;
    Vocable vocable_;
    JFrame frame;
    JPanel pane;
    JButton btnSubmit;
    Object local_lock;

    JTextField txtFld1;
    JLabel lblLang1;
    JLabel lblWord1;
    public JComboBox<Vocable.Language> comboBoxLang1;
    InterfaceLanguages.Languages lang;
    InterfaceLanguages languages;
    GridBagConstraints c;
    JFrame parent_;
    boolean new_;

    public Edit(JFrame parent, VocableDictionary v, Vocable vc, InterfaceLanguages.Languages int_lang) {
        new_ = false;
        parent_ = parent;
        local_lock = new Object();
        languages = new InterfaceLanguages();
        vocab_dic = v;
        vocable_ = vc;
        pane = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        btnSubmit = new JButton();

        txtFld1 = new JTextField(vc.getWord());
        lblLang1 = new JLabel();
        lang = int_lang;
        frame = new JFrame();
        lblWord1 = new JLabel();
        setIntLang(this.lang);
        comboBoxLang1 = new JComboBox<>(Vocable.Language.class.getEnumConstants());
        comboBoxLang1.setSelectedItem(vocable_.getLanguage());

        frame.setSize(300, 300);
        c.gridx = 0;
        c.gridy++;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = .05;
        c.insets = new Insets(10, 15, 0, 20);
        pane.add(lblLang1, c);
        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 15, 10, 20);

        comboBoxLang1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (!((Vocable.Language) comboBoxLang1.getSelectedItem()).equals(vocable_.getLanguage())) {
                        if(vocable_.contain((Vocable.Language) comboBoxLang1.getSelectedItem())){
                            txtFld1.setText((vocable_.getTranslation((Vocable.Language) comboBoxLang1.getSelectedItem())).getWord());
                            vocable_ = vocable_.getTranslation((Vocable.Language) comboBoxLang1.getSelectedItem());
                            new_ = false;
                        }
                        else{
                            txtFld1.setText("");
                            new_ = true;
                        }
                    }
                    else{
                        if(txtFld1.getText().equals("") || new_){
                            txtFld1.setText(vocable_.getWord());
                            new_ = false;
                        }
                    }
                } catch (NullPointerException ex) {
                    txtFld1.setText("");
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });
        pane.add(comboBoxLang1, c);

        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 15, 0, 20);
        pane.add(lblWord1, c);

        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 15, 0, 20);
        pane.add(txtFld1, c);

        c.gridx = 1;
        c.gridy++;

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(!new_)
                        changeVocable(txtFld1.getText());
                    else{
                        //System.out.println("curr vocab: " +vocable.getWord() + vocable.getLanguage());
                        vocab_dic.addTranslation(new Vocable(txtFld1.getText(), (Vocable.Language) comboBoxLang1.getSelectedItem()),
                                vocable_.getLanguage() == Vocable.Language.GER ?
                                        vocable_ : vocable_.getTranslation(Vocable.Language.GER));
                        vocable_ = vocab_dic.findVocable(txtFld1.getText(), (Vocable.Language) comboBoxLang1.getSelectedItem()).get(0);
                        /*vocable.addTranslation(v);
                        for(Vocable.Language lang : Vocable.Language.class.getEnumConstants()){
                            if(lang != (Vocable.Language) comboBoxLang1.getSelectedItem() && lang != vocable.getLanguage()) {
                                //System.out.println(vocable.getLanguage().toString() + " != " + lang.toString());
                                vocable.getTranslation(lang).
                                        addTranslation(v);
                            }
                        }*/
                    }

                } catch (NullPointerException ex) {
                    System.out.println("(btnSaveEdit)one of the objects is null");
                }
            }
        });
        pane.add(btnSubmit, c);

    }

    public void setIntLang(InterfaceLanguages.Languages lang)
    {
        lblLang1.setText(languages.getString(lang, "language"));
        lblWord1.setText(languages.getString(lang, "word"));
        btnSubmit.setText(languages.getString(lang, "save"));
        frame.setTitle(languages.getString(lang, "edit"));
    }

    public VocableDictionary edit() {
        JDialog myDialog = new JDialog(parent_, "edit", Dialog.ModalityType.DOCUMENT_MODAL);
        myDialog.add(pane);
        myDialog.pack();
        myDialog.setVisible(true);
        return vocab_dic;
    }

    public void changeVocable(String new_vcb)
    {
        vocable_ = vocab_dic.replace(vocable_.getWord(),
                new_vcb, vocable_.getLanguage());
    }
}
