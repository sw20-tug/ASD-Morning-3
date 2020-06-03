package com.asdmorning3.junit;

import com.asdmorning3.basic.Edit;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.test.InterfaceLanguages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
//import org.mockito.*;

public class test003 {

	@Test
	@DisplayName("test editing Vocabulary")
	void testVocableDictionary_editVocabulary()
	{
		//https://bit.ly/2LpZeuw
		VocableDictionary d = new VocableDictionary();
		d.addVocable(new Vocable("hallo", Vocable.Language.GER),
				new Vocable("hello", Vocable.Language.ENG), new Vocable("salut", Vocable.Language.FRA));

		d.replace("salut", "bonjour", Vocable.Language.FRA);
		Assert.assertTrue(d.exists(new Vocable("bonjour", Vocable.Language.FRA)));

		Assert.assertEquals(d.findVocable("hallo", Vocable.Language.GER).get(0).getTranslation(Vocable.Language.FRA).getWord(),
				new Vocable("bonjour", Vocable.Language.FRA).getWord());
		Assert.assertEquals(d.findVocable("hello", Vocable.Language.ENG).get(0).getTranslation(Vocable.Language.FRA).getWord(),
				new Vocable("bonjour", Vocable.Language.FRA).getWord());

		d.replace("bonjour", "salut", Vocable.Language.FRA);
		Assert.assertTrue(d.exists(new Vocable("salut", Vocable.Language.FRA)));


		Assert.assertEquals(d.findVocable("hallo", Vocable.Language.GER).get(0).getTranslation(Vocable.Language.FRA).getWord(),
				new Vocable("salut", Vocable.Language.FRA).getWord());
		Assert.assertEquals(d.findVocable("hello", Vocable.Language.ENG).get(0).getTranslation(Vocable.Language.FRA).getWord(),
				new Vocable("salut", Vocable.Language.FRA).getWord());
	}

	@Test
	@DisplayName("test .edit() returnValue")
	void testVocableOverview_returnOnEdit()
	{
		VocableDictionary d = new VocableDictionary();
		d.addVocable(new Vocable("hallo", Vocable.Language.GER),
				new Vocable("hello", Vocable.Language.ENG), new Vocable("salut", Vocable.Language.FRA));
		//Edit e = Mockito.mock(Edit.class);
		//Mockito.when(e.edit()).thenReturn(d);
		//Assert.assertEquals(d, e.edit());
	}
}
