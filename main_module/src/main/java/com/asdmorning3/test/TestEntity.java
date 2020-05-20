package com.asdmorning3.test;

import com.asdmorning3.basic.Vocable;

import java.util.ArrayList;
import java.util.HashMap;
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

    TestEntity(ArrayList<Vocable> test_array, int number_of_reps,
               Vocable.Language from_language, Vocable.Language to_language)
    {
        this.test_array = test_array;
        this.number_of_reps = number_of_reps;
        this.from_language = from_language;
        this.to_language = to_language;
        current_round = 1;
        current_pos = 0;
        test_finished = false;
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

    public void showStats()
    {

    }

}