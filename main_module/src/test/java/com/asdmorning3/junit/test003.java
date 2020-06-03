package com.asdmorning3.junit;

import com.asdmorning3.basic.Edit;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.test.InterfaceLanguages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.mockito.*;

public class test003 {

	@Test
	@DisplayName("test replacing vocables in VocableDictionary")
	void testVocableDictionary_replaceVocabulary()
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
	@DisplayName("test adding vocables in VocableDictionary")
	void testVocableDictionary_addVocabulary(){
		VocableDictionary d = new VocableDictionary();
		d.addVocable(new Vocable("der Garten", Vocable.Language.GER),
				new Vocable("the garden", Vocable.Language.ENG));

		d.addTranslation(new Vocable("le jardin", Vocable.Language.FRA),
				d.findVocable("der Garten", Vocable.Language.GER).get(0));


		Assert.assertFalse(d.findVocable(Vocable.Language.FRA).isEmpty());
		Assert.assertFalse(d.findVocable(Vocable.Language.GER).isEmpty());
		Assert.assertFalse(d.findVocable(Vocable.Language.ENG).isEmpty());
		d.findVocable( "der Garten", Vocable.Language.GER).get(0);
		d.findVocable( "der Garten", Vocable.Language.GER).get(0).getTranslation(Vocable.Language.FRA).getWord();

		//assert german Translations
		Assert.assertEquals("the garden",
				d.findVocable( "der Garten", Vocable.Language.GER).get(0).getTranslation(Vocable.Language.ENG).getWord());
		Assert.assertEquals("le jardin",
				d.findVocable( "der Garten", Vocable.Language.GER).get(0).getTranslation(Vocable.Language.FRA).getWord());

		//assert french Translations
		Assert.assertEquals("der Garten",
				d.findVocable( "le jardin", Vocable.Language.FRA).get(0).getTranslation(Vocable.Language.GER).getWord());
		Assert.assertEquals("the garden",
				d.findVocable( "le jardin", Vocable.Language.FRA).get(0).getTranslation(Vocable.Language.ENG).getWord());

		//assert english Translations
		Assert.assertEquals("der Garten",
				d.findVocable( "the garden", Vocable.Language.ENG).get(0).getTranslation(Vocable.Language.GER).getWord());
		Assert.assertEquals("le jardin",
				d.findVocable( "the garden", Vocable.Language.ENG).get(0).getTranslation(Vocable.Language.FRA).getWord());

	}

	@Test
	@DisplayName("test adding german(std) in VocableDictionary")
	void testVocableDictionary_addVocabularyGer(){
		VocableDictionary d = new VocableDictionary();
		d.addVocable(new Vocable("pendant", Vocable.Language.FRA),
				new Vocable("during", Vocable.Language.ENG));

		//there must be a default german vocable
		Assert.assertFalse(d.findVocable(Vocable.Language.GER).isEmpty());

		Assert.assertTrue(
				d.findVocable("pendant", Vocable.Language.FRA).get(0).
						getTranslation(Vocable.Language.GER) != null);
		d.addTranslation(new Vocable("während", Vocable.Language.GER),
				d.findVocable("pendant", Vocable.Language.FRA).get(0).getTranslation(Vocable.Language.GER));
		Assert.assertFalse(d.findVocable(Vocable.Language.GER).isEmpty());

		//assert german Translations
		Assert.assertEquals("during",
				d.findVocable( "während", Vocable.Language.GER).get(0).getTranslation(Vocable.Language.ENG).getWord());
		Assert.assertEquals("pendant",
				d.findVocable( "während", Vocable.Language.GER).get(0).getTranslation(Vocable.Language.FRA).getWord());

		//assert french Translations
		Assert.assertEquals("während",
				d.findVocable( "pendant", Vocable.Language.FRA).get(0).getTranslation(Vocable.Language.GER).getWord());
		Assert.assertEquals("during",
				d.findVocable( "pendant", Vocable.Language.FRA).get(0).getTranslation(Vocable.Language.ENG).getWord());

		//assert english Translations
		Assert.assertEquals("während",
				d.findVocable( "during", Vocable.Language.ENG).get(0).getTranslation(Vocable.Language.GER).getWord());
		Assert.assertEquals("pendant",
				d.findVocable( "during", Vocable.Language.ENG).get(0).getTranslation(Vocable.Language.FRA).getWord());


	}

	@Test
	@DisplayName("test adding two Vocables in VocableDictionary")
	void testVocableDictionary_addTwoVocabules(){
		VocableDictionary d = new VocableDictionary();
		d.addVocable(new Vocable("évidemment", Vocable.Language.FRA));

		d.addTranslation(new Vocable("offensichtlich", Vocable.Language.GER),
				d.findVocable(Vocable.Language.GER).get(0));
		d.addTranslation(new Vocable("obviously", Vocable.Language.ENG),
				d.findVocable(Vocable.Language.GER).get(0));

		//assert german Translations
		Assert.assertEquals("obviously",
				d.findVocable( "offensichtlich", Vocable.Language.GER).get(0).getTranslation(Vocable.Language.ENG).getWord());
		Assert.assertEquals("évidemment",
				d.findVocable( "offensichtlich", Vocable.Language.GER).get(0).getTranslation(Vocable.Language.FRA).getWord());

		//assert french Translations
		Assert.assertEquals("offensichtlich",
				d.findVocable( "évidemment", Vocable.Language.FRA).get(0).getTranslation(Vocable.Language.GER).getWord());
		Assert.assertEquals("obviously",
				d.findVocable( "évidemment", Vocable.Language.FRA).get(0).getTranslation(Vocable.Language.ENG).getWord());

		//assert english Translations
		Assert.assertEquals("offensichtlich",
				d.findVocable( "obviously", Vocable.Language.ENG).get(0).getTranslation(Vocable.Language.GER).getWord());
		Assert.assertEquals("évidemment",
				d.findVocable( "obviously", Vocable.Language.ENG).get(0).getTranslation(Vocable.Language.FRA).getWord());

	}

	@Test
	@DisplayName("test .edit() returnValue")
	void testVocableOverview_returnOnEdit()
	{
		VocableDictionary d = new VocableDictionary();
		d.addVocable(new Vocable("hallo", Vocable.Language.GER),
				new Vocable("hello", Vocable.Language.ENG), new Vocable("salut", Vocable.Language.FRA));
		Edit e = Mockito.mock(Edit.class);
		Mockito.when(e.edit()).thenReturn(d);
		Assert.assertEquals(d, e.edit());
	}
}
