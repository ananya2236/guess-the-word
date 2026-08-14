package com.opentext.guesstheword.service;

public record DailyReport(
        String date,
        long numberOfUsers,
        long numberOfCorrectGuesses
) {
}