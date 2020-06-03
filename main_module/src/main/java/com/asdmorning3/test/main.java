package com.asdmorning3.test;
import com.asdmorning3.basic.GUI;
import com.asdmorning3.basic.VocableDictionary;

public class main {

	public static void main (String[] args)
	{
		VocableDictionary vocableDictionary = new VocableDictionary();
		GUI gui = new GUI(vocableDictionary);
		return;
	}

}
