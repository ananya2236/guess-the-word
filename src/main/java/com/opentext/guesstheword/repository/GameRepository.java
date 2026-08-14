package com.opentext.guesstheword.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opentext.guesstheword.model.Game;
import com.opentext.guesstheword.model.User;

public interface GameRepository extends JpaRepository<Game, Long> {

    long countByUserAndGameDate(User user, LocalDate gameDate);

    long countByUserAndGameDateAndWon(
            User user,
            LocalDate gameDate,
            boolean won
    );

    List<Game> findByGameDate(LocalDate gameDate);

    List<Game> findByUser(User user);
}