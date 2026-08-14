package com.opentext.guesstheword;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opentext.guesstheword.model.Word;
import com.opentext.guesstheword.repository.WordRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final WordRepository wordRepository;

    public DataInitializer(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void run(String... args) {

        if (wordRepository.count() == 0) {

            String[] words = {
                "APPLE",
                "HOUSE",
                "TRAIN",
                "PLANT",
                "MOUSE",
                "CHAIR",
                "TABLE",
                "WATER",
                "CLOUD",
                "BRAIN",
                "LIGHT",
                "STONE",
                "RIVER",
                "WORLD",
                "GREEN",
                "BLACK",
                "SWEET",
                "PHONE",
                "MUSIC",
                "DREAM"
            };

            for (String word : words) {
                wordRepository.save(new Word(word));
            }
        }
    }
}