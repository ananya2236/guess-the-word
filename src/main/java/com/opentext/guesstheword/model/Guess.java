package com.opentext.guesstheword.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "guesses")
public class Guess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false, length = 5)
    private String guessedWord;

    @Column(nullable = false)
    private LocalDate guessDate;

    private int guessNumber;

    public Guess() {
    }

    public Guess(Game game, String guessedWord, int guessNumber) {
        this.game = game;
        this.guessedWord = guessedWord;
        this.guessDate = LocalDate.now();
        this.guessNumber = guessNumber;
    }

    public Long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    public LocalDate getGuessDate() {
        return guessDate;
    }

    public int getGuessNumber() {
        return guessNumber;
    }
}