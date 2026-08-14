package com.opentext.guesstheword.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opentext.guesstheword.model.Game;
import com.opentext.guesstheword.model.Guess;

public interface GuessRepository extends JpaRepository<Guess, Long> {

    List<Guess> findByGameOrderByGuessNumberAsc(Game game);

    long countByGame(Game game);
}