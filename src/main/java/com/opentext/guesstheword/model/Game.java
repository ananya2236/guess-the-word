package com.opentext.guesstheword.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    private LocalDate gameDate;

    private boolean won;

    private int guessCount;

    public Game() {
    }

    public Game(User user, Word word) {
        this.user = user;
        this.word = word;
        this.gameDate = LocalDate.now();
        this.won = false;
        this.guessCount = 0;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Word getWord() {
        return word;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public boolean isWon() {
        return won;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void setGuessCount(int guessCount) {
        this.guessCount = guessCount;
    }
}