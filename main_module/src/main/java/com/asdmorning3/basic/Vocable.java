package com.asdmorning3.basic;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vocable implements Serializable {

	public Vocable(@NotNull String word, @NotNull Language language) {
		if (word.length() == 0)
		{
			throw new IllegalArgumentException("Word has to be at least of lentght one."); //TODO constant for interface language
		}
		this.word_ = word;
		this.language_ = language;
		this.translation_ = new HashMap<Language, Vocable>();
		this.tags_ = new ArrayList<Tags>();
		this.rating_ = Rating.NORMAL;

	}

	public enum Language {
		ENG,
		GER
	}

	public enum Rating
	{
		VERY_EASY,
		EASY,
		NORMAL,
		DIFFICULT,
		VERY_DIFFICULT
	}

	private String word_;

	private Language language_ ;

	private Rating rating_;

	private HashMap<Language, Vocable> translation_;

	private ArrayList<Tags> tags_;

	public Vocable getTranslation(@NotNull Language language) throws IndexOutOfBoundsException, NullPointerException, IllegalArgumentException
	{
		if (language == language_)
		{
			throw new IllegalArgumentException("Translation to same language not supported."); //TODO constant for interface language
		}
		return translation_.get(language);
	}

	public void addTranslation(@NotNull Vocable vocable) throws IllegalArgumentException
	{
		if (vocable.getLanguage() == language_)
		{
			throw new IllegalArgumentException("Can't have a Translation from " + language_ + " to " + vocable.getLanguage()); //TODO constant for interface language
		}
		translation_.put(vocable.getLanguage(), vocable);
		vocable.viceVersaTranslation(language_, this);
	}

	public void viceVersaTranslation(Language language, Vocable vocable)
	{
		translation_.put(language, vocable);
	}

	public void editTranslation(Language language, Vocable vocable)
	{
		translation_.remove(language);
		translation_.put(language, vocable);
	}

	public void removeTranslation(Language language)
	{
		translation_.remove(language);
	}

	public String getWord() {
		return word_;
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

	public void changeRating(Rating rating)
	{
		this.rating_ = rating;
	}

	public Rating getRating()
	{
		return this.rating_;
	}

}
