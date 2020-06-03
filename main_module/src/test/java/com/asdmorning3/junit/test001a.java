package com.asdmorning3.junit;

import com.asdmorning3.basic.GUI;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;


public class test001a {

	@Test
	@DisplayName("test ComboBox Input")
	void testCombobox()
	{
		VocableDictionary dictionary = new VocableDictionary();
		dictionary.addVocable(new Vocable("hallo", Vocable.Language.GER),
				new Vocable("hello", Vocable.Language.ENG));
		GUI g = new GUI(dictionary);
		assert(g.comboBoxLang1.getItemCount() == 2);
	}
}
