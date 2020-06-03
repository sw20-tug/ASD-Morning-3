package com.asdmorning3.test;

import com.asdmorning3.basic.Vocable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestEntity {

    ArrayList<Vocable> test_array = new ArrayList<>();

    Map<Vocable, Integer> guessed_vocabs = new HashMap<Vocable, Integer>();

    Vocable.Language from_language;
    Vocable.Language to_language;

    int number_of_reps;
    int current_round;
    int current_pos;
    boolean test_finished;
    int number_of_vocabs;

    public TestEntity(ArrayList<Vocable> test_array, int number_of_reps,
               Vocable.Language from_language, Vocable.Language to_language)
    {
        this.test_array = test_array;
        this.number_of_reps = number_of_reps;
        this.from_language = from_language;
        this.to_language = to_language;
        current_round = 1;
        current_pos = 0;
        test_finished = false;
        number_of_vocabs = test_array.size();
    }

    public Vocable.Language getFrom_language() {
        return from_language;
    }

    public Vocable.Language getTo_language() {
        return to_language;
    }

    public Vocable getNextVocable()
    {
        if(test_array.size() == 0) {
            test_finished = true;
            return null;
        }
        return test_array.get(current_pos);
    }

    public void guessedLastVocable(boolean guessed)
    {
        if(guessed)
        {
            guessed_vocabs.put(test_array.get(current_pos), current_round);
            test_array.remove(current_pos);
        }
        else
        {
            current_pos++;
        }

        if(current_pos >= test_array.size())
        {
            current_pos = 0;
            current_round++;

            if (current_round > number_of_reps)
            {
                test_finished = true;
            }
        }
    }

    public boolean getTestFinished()
    {
        return test_finished;
    }

    public Map<Vocable, Integer> getGuessedVocabs() {
        return guessed_vocabs;
    }

    public ArrayList<String> showStats()
    {
        ArrayList<String> results = new ArrayList<>();

        int rounds_all_vocabs = 0;
        int guessed_first_try = 0;
        Iterator it = guessed_vocabs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            rounds_all_vocabs += (Integer)pair.getValue();
            if((Integer)pair.getValue() == 1)
            {
                guessed_first_try++;
            }
            it.remove();
        }
        float tmp = ((number_of_vocabs - test_array.size()) / (float)(test_array.size() * number_of_reps + rounds_all_vocabs)) * 100;
        System.out.println(tmp);
        results.add(String.valueOf((int)tmp));

        results.add(String.valueOf(number_of_vocabs - test_array.size()));
        results.add(String.valueOf(guessed_first_try));
        results.add(String.valueOf(test_array.size()));
        tmp = ((float)(test_array.size() * number_of_reps + rounds_all_vocabs)) / number_of_vocabs;
        results.add(String.format("%.2f", tmp));

        String tmp_worst = "";
        if(test_array.size() == 0)
        {
            results.add("-");
        }
        else
        {
            for(int counter = 0; counter < test_array.size(); counter++)
            {
                tmp_worst += test_array.get(counter).getWord(from_language);
                tmp_worst += ", ";
            }
            tmp_worst= tmp_worst.substring(0, tmp_worst.length() - 2);
            results.add(tmp_worst);
        }

        return results;
    }
}