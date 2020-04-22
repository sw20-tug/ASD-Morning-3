package com.asdmorning3.basic;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class VocableDictionary implements Serializable {

	public VocableDictionary()
	{
		vocableList = new HashSet<>();
		tagsList = new ArrayList<Tags>();
	}

	private HashSet<Vocable> vocableList;

	private ArrayList<Tags> tagsList;

	public HashSet<Vocable> getVocableList()
	{
		return vocableList;
	}

	public List<Vocable> findVocable(String word, Vocable.Language language)
	{
		try{
			return vocableList.stream().filter(
					(vocable) -> (vocable.getWord().equals(word) && vocable.getLanguage().equals(language)))
					.collect(Collectors.toList());
		}
		catch (NullPointerException e)
		{
			return new ArrayList<>();
		}

	}

	public void save(String path) throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
		oos.writeObject(vocableList);
		oos.writeObject(tagsList);
		oos.close();
	}

	public void load(String path) throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		vocableList = (HashSet<Vocable>) ois.readObject();
		tagsList = (ArrayList<Tags>) ois.readObject();
	}

	public void addVocable(Vocable ... vocables)
	{
		for (Vocable vocable: vocables)
		{
			for (Vocable translation: vocables)
			{
				if (vocable.getLanguage() != translation.getLanguage())
				{
					try{
						if (!vocable.getTranslation(translation.getLanguage()).getWord().equals(translation.getWord()))
						{
							throw new NullPointerException();
						}
					}
					catch (NullPointerException e)
					{
						try{
							vocable.addTranslation(translation);
						}
						catch (IllegalArgumentException ex)
						{
							System.out.println("This should never happen.");
						}
					}
				}
			}
		}
		vocableList.addAll(Arrays.asList(vocables));
	}

	public Tags createTag(String description, Color color)
	{
		Tags tag = getTagByDescription(description);
		if(tag != null)
			return tag;

		tag = new Tags(description, color);
		tagsList.add(tag);
		return tag;
	}

	public Tags getTagByDescription(String description)
	{
		for(Tags tag : tagsList)
		{
			if(tag.getDescription().equals(description))
				return tag;
		}

		return null;
	}

	public ArrayList<Tags> getTagsList()
	{
		return tagsList;
	}

	public void addTagToVocable(Tags tag, Vocable vocable)
	{
		vocable.addTag(tag);
	}

	public void removeTagToVocable(Tags tag, Vocable vocable)
	{
		vocable.removeTag(tag);
	}

	public ArrayList<Vocable> getVocablesByTag(Tags tag, HashSet<Vocable> list)
	{
		ArrayList<Vocable> vocables = new ArrayList<>();
		for (Vocable vocable : list)
		{
			if(vocable.hasTag(tag))
				vocables.add(vocable);
		}
		return  vocables;
	}

	public void changeVocableRating(Vocable.Rating rating, Vocable vocable)
	{
		vocable.changeRating(rating);
	}

	public Vocable.Rating getVocableRating(Vocable vocable)
	{
		return vocable.getRating();
	}

	public ArrayList<Vocable> getVocablesSortedAsc(HashSet<Vocable> list)
	{
		ArrayList<Vocable> sortedVocables = new ArrayList<>();
		for(Vocable.Rating rating : Vocable.Rating.values())
		{
			for (Vocable vocable : list)
			{
				if(vocable.getRating().equals(rating))
					sortedVocables.add(vocable);
			}
		}

		return  sortedVocables;
	}

	public ArrayList<Vocable> getVocablesSortedDesc(HashSet<Vocable> list)
	{
		ArrayList<Vocable> sortedVocables = new ArrayList<>();

		for(int index = Vocable.Rating.values().length; index >= 0; index--)
		{
			for (Vocable vocable : list)
			{
				if(vocable.getRating().equals(Vocable.Rating.values()[index]))
					sortedVocables.add(vocable);
			}
		}

		return  sortedVocables;
	}

	public ArrayList<Vocable> getVocablesByRating(Vocable.Rating rating, HashSet<Vocable> list)
	{
		ArrayList<Vocable> vocables = new ArrayList<>();
		for (Vocable vocable : list)
		{
			if(vocable.getRating().equals(rating))
				vocables.add(vocable);
		}
		return  vocables;
	}

}
