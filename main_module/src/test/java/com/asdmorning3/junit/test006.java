package com.asdmorning3.junit;

import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.components.VocableOverview;
import com.asdmorning3.test.InterfaceLanguages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;

public class test006 {

	private static VocableOverview overview;

	@Test
	@BeforeAll
	static void init()
	{
		VocableDictionary dictionary = new VocableDictionary();
		Vocable v1 = new Vocable("hello", Vocable.Language.ENG);
		Vocable v2 = new Vocable("hallo", Vocable.Language.GER);

		Vocable v3 = new Vocable("hello", Vocable.Language.ENG);
		Vocable v4 = new Vocable("guten Tag", Vocable.Language.GER);
		Vocable v5 = new Vocable("bonjour", Vocable.Language.FRA);

		Vocable v6 = new Vocable("goodbye", Vocable.Language.ENG);
		Vocable v7 = new Vocable("au revoir", Vocable.Language.FRA);

		Vocable v8 = new Vocable("Ich bin dabei!", Vocable.Language.GER);
		Vocable v9 = new Vocable("I'm in!", Vocable.Language.ENG);

		Vocable v10 = new Vocable("Österreicherin", Vocable.Language.GER);
		Vocable v11 = new Vocable("l'autrichienne", Vocable.Language.FRA);

		Vocable v12 = new Vocable("le château", Vocable.Language.FRA);
		Vocable v13 = new Vocable("castle", Vocable.Language.ENG);
		dictionary.addVocable(v1, v2);
		dictionary.addVocable(v3, v4, v5);
		dictionary.addVocable(v6, v7);
		dictionary.addVocable(v8, v9);
		dictionary.addVocable(v10, v11);
		dictionary.addVocable(v12, v13);
		//overview = new VocableOverview(dictionary, InterfaceLanguages.Languages.DE, );
		assert(overview.getContent().getComponents().length > 0);
	}

	@Test
	void changeRating()
	{
		assert(overview.getContent().getComponents().length > 0);
		JScrollPane pane = overview.getContent();
		int rows = ((JTable)overview.getContent().getViewport().getComponent(0)).getRowCount();
		for (int i = 0; i < rows; i++)
		{
			System.out.println((String)((JTable)overview.getContent().getViewport().getComponent(0)).getValueAt(i, 3) + ":\t"+ overview.table_.getValueAt(i, 0) + "/" + overview.table_.getValueAt(i, 1) + "/" + overview.table_.getValueAt(i, 2));
			assert(((JTable)overview.getContent().getViewport().getComponent(0)).getValueAt(i, 3).equals("Mittel"));
		}
		for (int row = 0; row < rows; row++)
		{
			String difficulty;
			ArrayList<String> row_strings = new ArrayList<>(3);
			for (int col = 0 ; col < 3; col++)
			{
				row_strings.add(col,(String)overview.table_.getValueAt(row, col));
			}
			difficulty = overview.dict_.changeDifficulty(row_strings, false);
			overview.updateDifficulty(row, difficulty);
		}
		for (int i = 0; i < rows; i++)
		{
			System.out.println((String)((JTable)overview.getContent().getViewport().getComponent(0)).getValueAt(i, 3)  + ":\t"+ overview.table_.getValueAt(i, 0) + "/" + overview.table_.getValueAt(i, 1) + "/" + overview.table_.getValueAt(i, 2));
			assert(((JTable)overview.getContent().getViewport().getComponent(0)).getValueAt(i, 3).equals("Schwer"));
		}
		for (int row = 0; row < rows; row++)
		{
			String difficulty;
			ArrayList<String> row_strings = new ArrayList<>(3);
			for (int col = 0 ; col < 3; col++)
			{
				row_strings.add(col,(String)overview.table_.getValueAt(row, col));
			}
			difficulty = overview.dict_.changeDifficulty(row_strings, false);
			overview.updateDifficulty(row, difficulty);
		}
		overview.setIntLang(InterfaceLanguages.Languages.FR);
		for (int i = 0; i < rows; i++)
		{
			System.out.println((String)((JTable)overview.getContent().getViewport().getComponent(0)).getValueAt(i, 3)  + ":\t"+ overview.table_.getValueAt(i, 0) + "/" + overview.table_.getValueAt(i, 1) + "/" + overview.table_.getValueAt(i, 2));
			assert(((JTable)overview.getContent().getViewport().getComponent(0)).getValueAt(i, 3).equals("Facile"));
		}
	}




}
