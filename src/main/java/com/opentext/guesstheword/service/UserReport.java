package com.opentext.guesstheword.service;

import java.util.List;

public record UserReport(
        String username,
        List<GameReport> games
) {

    public record GameReport(
            Long gameId,
            String date,
            int guessCount,
            boolean won,
            List<String> guesses
    ) {
    }
}