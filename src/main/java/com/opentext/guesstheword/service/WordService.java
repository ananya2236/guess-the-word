package com.opentext.guesstheword.service;

import org.springframework.stereotype.Service;

import com.opentext.guesstheword.model.Word;
import com.opentext.guesstheword.repository.WordRepository;

@Service
public class WordService {

    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word getRandomWord() {
        return wordRepository.findRandomWord();
    }
}