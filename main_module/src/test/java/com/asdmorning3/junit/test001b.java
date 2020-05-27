package com.asdmorning3.junit;

import com.asdmorning3.basic.Tags;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.test.InterfaceLanguages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class test001b {

	static Stream<Arguments> stringStream () {
		return Stream.of(   Arguments.of("what", "was"),
							Arguments.of("how", null),
							Arguments.of(null, "wie"),
							Arguments.of(null, null),
							Arguments.of("", "hallo"));
	}

	static Stream<Arguments> tagsStream () {
		return Stream.of(   Arguments.of("easy", Color.green),
				Arguments.of("normal", Color.orange),
				Arguments.of("difficult", Color.red));
	}

	Vocable vocable1 = null;
	Vocable vocable2 = null;
	Vocable vocable3 = null;
	VocableDictionary dictionary = null;


	void initVocables(String english, String german) throws IllegalArgumentException
	{
		vocable1 = null;
		vocable2 = null;
		vocable3 = null;
		dictionary = new VocableDictionary();
		String errorMessage = null;
		try{
			vocable1 = new Vocable(english, Vocable.Language.ENG);
		}
		catch (IllegalArgumentException e)
		{
			errorMessage = "vocable 1 triggered: " + e.getMessage();
			throw new IllegalArgumentException(errorMessage);
		}
		try{
			vocable2 = new Vocable(german, Vocable.Language.GER);

		}
		catch (IllegalArgumentException e)
		{
			errorMessage = "vocable 2 triggered: " + e.getMessage();
			throw new IllegalArgumentException(errorMessage);
		}
		try{
			vocable3 = new Vocable(german, null);
		}
		catch (IllegalArgumentException ignored) { }
	}


	@ParameterizedTest
	@DisplayName("Vocable Test Basic")
	@MethodSource("stringStream")
	void vocableTest(String english, String german)
	{
		try{
			initVocables(english, german);
		}
		catch (NullPointerException | IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			return;
		}
		vocable1.addTranslation(vocable2);
		assert (vocable1.getTranslation(Vocable.Language.GER) == vocable2);
	}



	@ParameterizedTest
	@DisplayName("Dictionary Test")
	@MethodSource ("stringStream")
	void dictionaryTest(String english, String german) throws IOException {
		try{
			initVocables(english, german);
		}
		catch (NullPointerException | IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			return;
		}
		dictionary.addVocable(vocable1, vocable2);
		assert(vocable1.getTranslation(vocable2.getLanguage()).equals(vocable2));
		assert(vocable2.getTranslation(vocable1.getLanguage()).equals(vocable1));
		dictionary.addVocable(vocable2, vocable2);
		dictionary.addVocable(vocable1, vocable2);
		assert(dictionary.getVocableList().size() == 2);
		assert(dictionary.getVocableList().contains(vocable1));
		assert(dictionary.getVocableList().contains(vocable2));

		try{
			vocable2.getTranslation(vocable2.getLanguage());
		}
		catch (IllegalArgumentException e)
		{
			try{
				dictionary.save("dictionary.save");
			}
			catch (IOException ex)
			{
				System.out.println("save did not work: " + ex.getMessage());
			}
			try
			{
				dictionary.load("dictionary.save");
			} catch (ClassNotFoundException|IOException ex)
			{
				System.out.println("open did not work: " + ex.getMessage());
				assert(false);
			}
			assert(dictionary.getVocableList().size() == 2);
			for(Vocable vocable: dictionary.getVocableList())
			{
				assert (vocable1.equals(vocable) || vocable2.equals(vocable));
			}
			assert(vocable1.getTranslation(vocable2.getLanguage()).equals(vocable2));
			assert(vocable2.getTranslation(vocable1.getLanguage()).equals(vocable1));

			try
			{
				dictionary.load("this_file_does_not_exist.exe");
			}
			catch (ClassNotFoundException | IOException ex)
			{
				try
				{
					dictionary.load("pom.xml");
				} catch (ClassNotFoundException | IOException exception)
				{
					exception.printStackTrace();
					return;
				}
			}
		}
		assert(false);
	}

	@ParameterizedTest
	@DisplayName("Find Vocable")
	@MethodSource("stringStream")
	void findVocable(String english, String german) {
		try {
			initVocables(english, german);
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		try{
			dictionary.addVocable(vocable1, vocable2);
		}
		catch (NullPointerException e)
		{
			return;
		}
		String word_ = vocable1.getWord();
		Vocable.Language language_ = vocable1.getLanguage();
		assert (word_.length() >= 1);
		assert (language_ != null);
		List<Vocable> results = dictionary.findVocable(word_, language_);
		System.out.println("found " + results.size() + " results");
		for (Vocable vocable : results) {
			assert (vocable.equals(vocable1));
		}
	}

	void setUpDict()
	{
		dictionary.addVocable(new Vocable("hello", Vocable.Language.ENG), new Vocable("hallo", Vocable.Language.GER));
		dictionary.addVocable(new Vocable("test", Vocable.Language.ENG), new Vocable("Test", Vocable.Language.GER));
		dictionary.addVocable(new Vocable("test1", Vocable.Language.ENG), new Vocable("Test1", Vocable.Language.GER));
	}

	@Test
	@DisplayName("create Table")
	void createTable()
	{
		dictionary = new VocableDictionary();
		InterfaceLanguages interfaceLanguages = new InterfaceLanguages();
		setUpDict();

		System.out.println(Arrays.deepToString(dictionary.getTable(interfaceLanguages, InterfaceLanguages.Languages.EN)));
  }

  
	@ParameterizedTest
	@DisplayName("Tags Test")
	@MethodSource("tagsStream")
	void tagsTest(String description, Color color)
	{
		try
		{
			initVocables("Hey", "Hallo");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			return;
		}

		dictionary.addVocable(vocable1, vocable2);
		Tags tag = dictionary.createTag(description, color);
		dictionary.addTagToVocable(tag, dictionary.findVocable(vocable1.getWord(), vocable1.getLanguage()).get(0));
		Tags tag2 = dictionary.createTag("verb", Color.black);
		dictionary.addTagToVocable(tag2, dictionary.findVocable(vocable1.getWord(), vocable1.getLanguage()).get(0));

		try{
			dictionary.save("dictionary.save");
		}
		catch (IOException ex)
		{
			System.out.println("save did not work: " + ex.getMessage());
		}
		try
		{
			dictionary.load("dictionary.save");
		} catch (ClassNotFoundException|IOException ex)
		{
			System.out.println("open did not work: " + ex.getMessage());
			assert(false);
		}

		Vocable vocable = dictionary.findVocable(vocable1.getWord(), vocable1.getLanguage()).get(0);

		assert(vocable.getTags().size() == 2);
		assert (vocable.getTags().get(0).getDescription().equals(tag.getDescription()));
		assert (vocable.getTags().get(1).getDescription().equals(tag2.getDescription()));
		assert(dictionary.getTagByDescription("verb").getDescription().equals(tag2.getDescription()));

		dictionary.removeTagToVocable(dictionary.getTagByDescription(description), vocable);
		assert(vocable.getTags().size() == 1);
	}
}

