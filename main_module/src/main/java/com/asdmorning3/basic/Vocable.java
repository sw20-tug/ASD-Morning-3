package com.asdmorning3.basic;

//import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Vocable implements Serializable {

	public Vocable(/*@NotNull*/ String word, /*@NotNull*/ Language language) {
		if (word.length() == 0)
		{
			throw new IllegalArgumentException("Word has to be at least of length one."); //TODO constant for interface language
		}
		this.word_ = word;
		this.language_ = language;
		this.translation_ = new HashMap<Language, Vocable>();
		this.tags_ = new ArrayList<Tags>();
		this.rating_ = Difficulty.MEDIUM;
		//this.rating_2_ = Rating.NORMAL;
	}

	public void increaseDifficulty() {
		System.out.print("increasing difficulty for: ");
		for (int i = 0; i < Language.class.getEnumConstants().length; i++)
		{
			try{
				System.out.print("("+ Language.class.getEnumConstants()[i].toString() +"): " + getTranslation(Language.class.getEnumConstants()[i]).word_ + "//");
			}
			catch (IllegalArgumentException e)
			{
				System.out.print("("+ Language.class.getEnumConstants()[i].toString() +"): " + word_ + "//");
			}
			catch (Exception ignored){}
		}
		System.out.print("\n");
		if(rating_ == Difficulty.EASY)
			rating_ = Difficulty.MEDIUM;
		else if(rating_ == Difficulty.MEDIUM)
			rating_ = Difficulty.HARD;
		else if (rating_ == Difficulty.HARD)
			rating_ = Difficulty.EASY;
	}

	public void decreaseDifficulty() {
		System.out.print("decreasing difficulty for: ");
		for (int i = 0; i < Language.class.getEnumConstants().length; i++)
		{
			try{
				System.out.print("("+ Language.class.getEnumConstants()[i].toString() +"): " + getTranslation(Language.class.getEnumConstants()[i]).word_ + "//");
			}
			catch (IllegalArgumentException e)
			{
				System.out.print("("+ Language.class.getEnumConstants()[i].toString() +"): " + word_ + "//");
			}
			catch (Exception ignored){}
		}
		if(rating_ == Difficulty.HARD)
			rating_ = Difficulty.MEDIUM;
		else if(rating_ == Difficulty.MEDIUM)
			rating_ = Difficulty.EASY;
		else if(rating_ == Difficulty.EASY)
			rating_ = Difficulty.HARD;
	}


	public enum Language {
		ENG,
		GER,
		FRA
	}


	public enum Difficulty{
		EASY,
		MEDIUM,
		HARD
	}

	private String word_;

	private Language language_ ;

	private HashMap<Language, Vocable> translation_;

	private ArrayList<Tags> tags_;

	private Difficulty rating_;

	public boolean contain(Language lang){
		return translation_.containsKey(lang);
	}

	public Vocable getTranslation(/*@NotNull*/ Language language) throws IndexOutOfBoundsException, NullPointerException, IllegalArgumentException
	{
		if (language == language_)
		{
			throw new IllegalArgumentException("Translation to same language not supported."); //TODO constant for interface language
		}
		return translation_.get(language);
	}

	public void addTranslation(/*@NotNull*/ Vocable vocable) throws IllegalArgumentException
	{
		//System.out.println(vocable.getLanguage() + "/" + language_ + "\t" + vocable.getWord()+"/"+word_);
		if (vocable.getLanguage() == language_)
		{
			throw new IllegalArgumentException("Can't have a Translation from " + language_ + " to " + vocable.getLanguage()); //TODO constant for interface language
		}
		System.out.println("Translation: " + language_ + "->" + vocable.getLanguage());
		translation_.put(vocable.getLanguage(), vocable);
		vocable.viceVersaTranslation(language_, this);
	}

	public void viceVersaTranslation(Language language, Vocable vocable)
	{
		System.out.println("Translation: " + language_ + "->" + vocable.getLanguage());
		translation_.put(language, vocable);
	}

	public void editTranslation(Language language, Vocable vocable)
	{
		translation_.remove(language);
		translation_.put(language, vocable);
	}
	public void editTranslation(Language language, String vocable)
	{
		Vocable tmp_vcb = translation_.get(language), new_vcb;
		if(this.language_ == language)
			word_ = vocable;
		else {
			translation_.remove(language);
			translation_.put(language, (new_vcb = new Vocable(vocable, language)));

			for(Vocable.Language l : Vocable.Language.values())
			{
				if(l != language)
				{
					try {
						if(tmp_vcb.getTranslation(l) != null)
							new_vcb.addTranslation(tmp_vcb.getTranslation(l));
					}
					catch(NullPointerException ex) {}
				}
			}
		}
	}

	public void removeTranslation(Language language)
	{
		translation_.remove(language);
	}

	public String getWord() {
		return word_;
	}

	public String getWord(Language language) {
		try {
			return getTranslation(language).getWord();
		}
		catch(IllegalArgumentException e) {
			return word_;
		}
		catch(Exception e)
		{
			return "";
		}
	}

	public void setWord_(String word_) {
		this.word_ = word_;
	}

	public Language getLanguage() {
		return language_;
	}

	public void setLanguage_(Language language_) {
		this.language_ = language_;
	}

	public boolean equals(Vocable vocable)
	{
		return vocable.getWord().equals(word_) && vocable.getLanguage().equals(language_);
	}

	public static String getLanguageWord(Language language)
	{
		switch(language)
		{
			case GER:
				return "Deutsch";
			case ENG:
				return "English";
			case FRA:
				return "Français";
			default:
				return "Language not Implemented";
		}
	}

  public String getDifficultyString()
  {
  	switch (rating_)
	{
		case EASY:
			return "Easy";
		case MEDIUM:
			return "Medium";
		case HARD:
			return "Hard";
	}
	return "Not rated!";
  }

	public Difficulty getRating_()
	{
		return rating_;
	}


	public boolean addTag(Tags addTag)
	{
		for(Tags tag : tags_)
		{
			if(tag.getDescription().equals(addTag.getDescription()))
			{
				return false;
			}
		}
		tags_.add(addTag);
		return true;
	}

	public boolean removeTag(Tags removeTag)
	{
		for(Tags tag : tags_)
		{
			if(tag.getDescription().equals(removeTag.getDescription()))
			{
				tags_.remove(tag);
				return true;
			}
		}
		return false;
	}

	public ArrayList<Tags> getTags() { return tags_; }

	public boolean hasTag(Tags hasTag)
	{
		for(Tags tag : tags_)
		{
			if(tag.getDescription().equals(hasTag.getDescription()))
			{
				return true;
			}
		}
		return false;
	}

	/*public void changeRating(Rating rating)
	{
		this.rating_2_ = rating;
	}*/

	public void changeDifficulty(Difficulty rating)
	{
		this.rating_ = rating;
	}

	public Difficulty getDifficluty()
	{
		return this.rating_;
	}

}
