package com.opentext.guesstheword.service;

import java.util.List;

public record GuessResult(
        String guessedWord,
        List<String> result,
        boolean correct,
        int guessCount,
        boolean gameWon,
        boolean gameLost
) {
}