package com.asdmorning3.test;


import javax.swing.*;
import com.asdmorning3.basic.studyInterface;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
public class main {

	public static void main (String[] args)
	{

		VocableDictionary dictionary = new VocableDictionary();
		dictionary.addVocable(new Vocable("hallo", Vocable.Language.GER),
				new Vocable("hello", Vocable.Language.ENG));
		dictionary.addVocable(new Vocable("Katze", Vocable.Language.GER),
				new Vocable("cat", Vocable.Language.ENG));
		dictionary.addVocable(new Vocable("Hund", Vocable.Language.GER),
				new Vocable("dog", Vocable.Language.ENG));
		studyInterface Interface = new studyInterface(dictionary);

	}

}
