package com.asdmorning3.basic;

import com.asdmorning3.test.InterfaceLanguages;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;
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

	public void save() throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir") + "/dictionary.save"));
		oos.writeObject(vocableList);
		oos.close();
	}

	public List<Vocable> findVocable(Vocable.Language language)
	{
		try{
			return vocableList.stream().filter(
					(vocable) -> (vocable.getLanguage().equals(language))
			).collect(Collectors.toList());
		}
		catch (NullPointerException e)
		{
			return new ArrayList<>();
		}
	}

	public List<String> findWord(Vocable.Language language)
	{
		try{
			return vocableList.stream().filter(
					(vocable) -> (vocable.getLanguage().equals(language))
			).map(Vocable::getWord).collect(Collectors.toList());
		}
		catch (NullPointerException e)
		{
			return new ArrayList<>();
		}
	}

	public String[][] getTable(InterfaceLanguages interfaceLanguage, InterfaceLanguages.Languages languages)
	{
		String[][] table = new String[findVocable(Vocable.Language.GER).size()][Vocable.Language.class.getEnumConstants().length + 1];
		int row = 0;
		int col = 0;
		for (Vocable vocab : findVocable(Vocable.Language.GER)) {
			col = 0;
			for (Vocable.Language language : Vocable.Language.class.getEnumConstants()) {
				table[row][col] = vocab.getWord(language);
				col++;
			}
			table[row][col] = interfaceLanguage.getString(languages, vocab.getDifficultyString());
			row++;
		}
		return table;
	}

	public String[][] getTable(InterfaceLanguages interfaceLanguage, InterfaceLanguages.Languages languages, Vocable.Language sortByLanguage, boolean sortAsc, boolean sortByRating) //TODO
	{
		ArrayList<Vocable> sortedVocables = null;

		if(sortByRating)
		{
			//sort by rating
			if(sortAsc)
				sortedVocables = getVocablesSortedAsc(vocableList);
			else
				sortedVocables = getVocablesSortedDesc(vocableList);
		}
		else
		{
			//sort by language
			if(sortAsc)
				sortedVocables = sortVocablesByAlhpabetAsc(vocableList, sortByLanguage);
			else
				sortedVocables = sortVocablesByAlhpabetDesc(vocableList, sortByLanguage);
		}



		String[][] table = new String[sortedVocables.size()][Vocable.Language.class.getEnumConstants().length + 1];
		int row = 0;
		int col = 0;
		for (Vocable vocab : sortedVocables) {
			col = 0;
			for (Vocable.Language language : Vocable.Language.class.getEnumConstants()) {
				table[row][col] = vocab.getWord(language);
				col++;
			}
			table[row][col] = interfaceLanguage.getString(languages, vocab.getDifficultyString());
			row++;
		}
		return table;
	}

	public String[][] getTable(ArrayList<Vocable> vocables)
	{
		String[][] table = new String[findVocable(Vocable.Language.GER).size()][Vocable.Language.class.getEnumConstants().length];
		int row = 0;
		int col = 0;
		ArrayList<Vocable> germanVocab = new ArrayList<Vocable>();

		for (Vocable vocab : vocables)
		{
			if(vocab.getLanguage() == Vocable.Language.GER)
			{
				germanVocab.add(vocab);
			}
		}

		for (Vocable vocab : germanVocab) {
			col = 0;
			for (Vocable.Language language : Vocable.Language.class.getEnumConstants()) {
				table[row][col] = vocab.getWord(language);
				col++;
			}
			row++;
		}
		return table;
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

	public void load() throws IOException, ClassNotFoundException
	{
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(System.getProperty("user.dir") + "/dictionary.save"));
			vocableList = (HashSet<Vocable>) ois.readObject();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("File not Found");
			System.out.println(ex.toString());
		}
	}

	public Vocable replace(String old_vocab, String new_vocab, Vocable.Language lang)
	{
		for(Vocable i : findVocable(old_vocab, lang))
		{
			i.editTranslation(lang, new_vocab);
			return i;
		}
		return null;
	}

	public void replace(Vocable old_vocab, String new_vocab, Vocable.Language lang)
	{
		old_vocab.editTranslation(lang, new_vocab);
	}

	public List<Vocable> getAllFromLanguage(Vocable.Language lang)
	{
		List<Vocable> vocables = new ArrayList<>();
		for(Vocable v : vocableList)
		{
			if(v.getLanguage() == lang)
				vocables.add(v);
		}
		return vocables;
	}

	public boolean exists(Vocable v)
	{
		for(Vocable d : getAllFromLanguage(v.getLanguage()))
			if(Objects.equals(d.getWord(), v.getWord()))
				return true;
		return false;
	}

	public void addVocable(Vocable ... vocables)
	{
		boolean german = false;
		for (Vocable vocable: vocables)
		{
			if (vocable.getLanguage() == Vocable.Language.GER)
			{
				german = true;
			}
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
				else if(!exists(vocable))
				{
					vocableList.addAll(Arrays.asList(vocables));
				}
			}
		}
		ArrayList<Vocable> list = new ArrayList<Vocable>(Arrays.asList(vocables));
		if (!german)
		{
			Vocable voc1 = new Vocable(" ", Vocable.Language.GER);
			vocableList.add(voc1);
			for (Vocable vcb : list)
			{
				vcb.addTranslation(voc1);
			}
			list.add(voc1);
		}
		vocableList.addAll(Arrays.asList(vocables));
	}

	public void addTranslation(Vocable new_vocab, Vocable ger_translation){
		if(new_vocab.getLanguage() == ger_translation.getLanguage()){
			replace(ger_translation.getWord(), new_vocab.getWord(), Vocable.Language.GER);
			return;
		}
		vocableList.add(new_vocab);
		ger_translation.addTranslation(new_vocab);
		for(Vocable.Language lang : Vocable.Language.class.getEnumConstants()){
			if(lang != new_vocab.getLanguage() && lang != ger_translation.getLanguage()) {
				//System.out.println(vocable.getLanguage().toString() + " != " + lang.toString());
				ger_translation.getTranslation(lang).
						addTranslation(new_vocab);
			}
		}

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

	public ArrayList<String> getTagsString() {
		ArrayList<String> tagsString = new ArrayList<String>();
		for(Tags tag : tagsList)
		{
			tagsString.add(tag.getDescription());
		}
		return tagsString;
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

	public ArrayList<Vocable> getVocablesByTag(Tags tag, ArrayList<Vocable> list)
	{
		ArrayList<Vocable> vocables = new ArrayList<>();
		for (Vocable vocable : list)
		{
			if(vocable.hasTag(tag))
				vocables.add(vocable);
		}
		return  vocables;
	}

	public void changeVocableRating(Vocable.Difficulty rating, Vocable vocable)
	{
		vocable.changeDifficulty(rating);
	}

	public Vocable.Difficulty getVocableDifficulty(Vocable vocable)
	{
		return vocable.getDifficluty();
	}

	public ArrayList<Vocable> getVocablesSortedAsc(HashSet<Vocable> list)
	{
		ArrayList<Vocable> sortedVocables = new ArrayList<>();

		ArrayList<Vocable> vocList = new ArrayList<>();

		for (Vocable vocable : list)
		{
			if(vocable.getLanguage() == Vocable.Language.GER)
				vocList.add(vocable);
		}

		for(Vocable.Difficulty rating : Vocable.Difficulty.values())
		{
			for (Vocable vocable : vocList)
			{
				if(vocable.getDifficluty().equals(rating))
					sortedVocables.add(vocable);
			}
		}

		return  sortedVocables;
	}

	public ArrayList<Vocable> getVocablesSortedAsc(ArrayList<Vocable> list)
	{
		ArrayList<Vocable> sortedVocables = new ArrayList<>();
		for(Vocable.Difficulty rating : Vocable.Difficulty.values())
		{
			for (Vocable vocable : list)
			{
				if(vocable.getDifficluty().equals(rating))
					sortedVocables.add(vocable);
			}
		}

		return  sortedVocables;
	}

	public ArrayList<Vocable> getVocablesSortedDesc(HashSet<Vocable> list)
	{
		ArrayList<Vocable> sortedVocables = new ArrayList<>();

		ArrayList<Vocable> vocList = new ArrayList<>();

		for (Vocable vocable : list)
		{
			if(vocable.getLanguage() == Vocable.Language.GER)
				vocList.add(vocable);
		}

		for(int index = Vocable.Difficulty.values().length - 1; index >= 0; index--)
		{
			for (Vocable vocable : vocList)
			{
				if(vocable.getDifficluty().equals(Vocable.Difficulty.values()[index]))
					sortedVocables.add(vocable);
			}
		}

		return  sortedVocables;
	}

	public ArrayList<Vocable> getVocablesSortedDesc(ArrayList<Vocable> list)
	{
		ArrayList<Vocable> sortedVocables = new ArrayList<>();

		for(int index = Vocable.Difficulty.values().length - 1; index >= 0; index--)
		{
			for (Vocable vocable : list)
			{
				if(vocable.getDifficluty().equals(Vocable.Difficulty.values()[index]))
					sortedVocables.add(vocable);
			}
		}

		return  sortedVocables;
	}

	public ArrayList<Vocable> getVocablesByRating(Vocable.Difficulty rating, HashSet<Vocable> list)
	{
		ArrayList<Vocable> vocables = new ArrayList<>();
		for (Vocable vocable : list)
		{
			if(vocable.getDifficluty().equals(rating))
				vocables.add(vocable);
		}
		return  vocables;
	}

	public ArrayList<Vocable> getVocablesByRating(Vocable.Difficulty rating, ArrayList<Vocable> list)
	{
		ArrayList<Vocable> vocables = new ArrayList<>();
		for (Vocable vocable : list)
		{
			if(vocable.getDifficluty().equals(rating))
				vocables.add(vocable);
		}
		return  vocables;
	}

	public ArrayList<Vocable> sortVocablesByAlhpabet(ArrayList<Vocable> list)
	{
		ArrayList<Vocable> sortedVocables = new ArrayList<>();

		for(char alphabet = 'a'; alphabet <='z'; alphabet++ )
		{
			for (Vocable vocable : list)
			{
				if(vocable.getWord(Vocable.Language.ENG).charAt(0) == alphabet )
					sortedVocables.add(vocable);
			}
		}
		for (Vocable vocable : list)
		{
			if(vocable.getWord(Vocable.Language.ENG).isEmpty())
				sortedVocables.add(vocable);
		}

		return  sortedVocables;
	}

	public ArrayList<Vocable> sortVocablesByAlhpabet(HashSet<Vocable> list)
	{
		ArrayList<Vocable> sortedVocables = new ArrayList<>();

		for(char alphabet = 'a'; alphabet <='z'; alphabet++ )
		{
			for (Vocable vocable : list)
			{
				if(vocable.getWord(Vocable.Language.ENG).charAt(0) == alphabet )
					sortedVocables.add(vocable);
			}
		}
		for (Vocable vocable : list)
		{
			if(vocable.getWord(Vocable.Language.ENG).isEmpty())
				sortedVocables.add(vocable);
		}

		return  sortedVocables;
	}

	public ArrayList<Vocable> sortVocablesByAlhpabetAsc(HashSet<Vocable> list, Vocable.Language sortBylanguage)
	{
		ArrayList<Vocable> sortedVocables = new ArrayList<>();

		ArrayList<Vocable> vocList = new ArrayList<>();

		for (Vocable vocable : list)
		{
			if(vocable.getLanguage() == Vocable.Language.GER)
				vocList.add(vocable);
		}

		for(char alphabet = 'a'; alphabet <='z'; alphabet++ )
		{
			for (Vocable vocable : vocList)
			{
				if(vocable.getWord(sortBylanguage).charAt(0) == alphabet )
					sortedVocables.add(vocable);
			}
		}
		for (Vocable vocable : vocList)
		{
			if(vocable.getWord(sortBylanguage).isEmpty())
				sortedVocables.add(vocable);
		}

		return  sortedVocables;
	}

	public ArrayList<Vocable> sortVocablesByAlhpabetDesc(HashSet<Vocable> list, Vocable.Language sortBylanguage)
	{
		ArrayList<Vocable> sortedVocables = new ArrayList<>();

		ArrayList<Vocable> vocList = new ArrayList<>();

		for (Vocable vocable : list)
		{
			if(vocable.getLanguage() == Vocable.Language.GER)
				vocList.add(vocable);
		}

		for(char alphabet = 'z'; alphabet >= 'a'; alphabet-- )
		{
			for (Vocable vocable : vocList)
			{
				if(vocable.getWord(sortBylanguage).charAt(0) == alphabet )
					sortedVocables.add(vocable);
			}
		}
		for (Vocable vocable : vocList)
		{
			if(vocable.getWord(sortBylanguage).isEmpty())
				sortedVocables.add(vocable);
		}

		return  sortedVocables;
	}

	public String changeDifficulty(List<String> row_strings, boolean decrease) {
		ArrayList<List<Vocable>> arrays = new ArrayList<>(row_strings.size());
		System.out.println("changing difficulty for :" + row_strings.get(0) + "//" + row_strings.get(1) + "//" + row_strings.get(2));
		int i = 0;
		int array_position = 0;
		for (String s : row_strings)
		{
			arrays.add(i, findVocable(s, Vocable.Language.class.getEnumConstants()[i]));
			if(Vocable.Language.class.getEnumConstants()[i] == Vocable.Language.GER)
				array_position = i;
			i++;
		}
		String difficulty = Vocable.Difficulty.MEDIUM.toString();
		int matches;
		for (int k = 0; k < arrays.get(array_position).size(); k++)
		{
			matches = 0;
			if (arrays.get(array_position).get(k).getWord().equals(row_strings.get(array_position)))
			{
				for (int j = 1; j < Vocable.Language.class.getEnumConstants().length; j++)
				{

					if (arrays.get(array_position).get(k).getWord(Vocable.Language.class.getEnumConstants()[j])
							.equals(row_strings.get(j)))
					{
						matches++;

					}
				}
			}
			if (matches == Vocable.Language.class.getEnumConstants().length - 1)
			{
				if(!decrease)
					arrays.get(array_position).get(k).increaseDifficulty();
				else
					arrays.get(array_position).get(k).decreaseDifficulty();
				difficulty = arrays.get(array_position).get(k).getDifficultyString();
				break;
			}
		}
		return difficulty;
	}

	public String getDifficulty(List<String> row_strings) {
		ArrayList<List<Vocable>> arrays = new ArrayList<>(row_strings.size());
		int i = 0;
		int array_position = 0;
		for (String s : row_strings)
		{
			arrays.add(i, findVocable(s, Vocable.Language.class.getEnumConstants()[i]));
			if(Vocable.Language.class.getEnumConstants()[i] == Vocable.Language.GER)
				array_position = i;
			i++;
		}
		String difficulty = Vocable.Difficulty.MEDIUM.toString();
		int matches;
		for (int k = 0; k < arrays.get(array_position).size(); k++)
		{
			matches = 0;
			if (arrays.get(array_position).get(k).getWord().equals(row_strings.get(array_position)))
			{
				for (int j = 1; j < Vocable.Language.class.getEnumConstants().length; j++)
				{

					if (arrays.get(array_position).get(k).getWord(Vocable.Language.class.getEnumConstants()[j])
							.equals(row_strings.get(j)))
					{
						matches++;

					}
				}
			}
			if (matches == Vocable.Language.class.getEnumConstants().length - 1)
			{
				difficulty = arrays.get(array_position).get(k).getDifficultyString();
				break;
			}
		}
		return difficulty;
	}
}

