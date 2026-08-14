package com.opentext.guesstheword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.opentext.guesstheword.model.Word;

public interface WordRepository extends JpaRepository<Word, Long> {

    @Query(value = "SELECT * FROM words ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Word findRandomWord();
}