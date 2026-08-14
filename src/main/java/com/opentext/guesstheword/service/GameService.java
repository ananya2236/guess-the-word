package com.opentext.guesstheword.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opentext.guesstheword.model.Game;
import com.opentext.guesstheword.model.Guess;
import com.opentext.guesstheword.model.User;
import com.opentext.guesstheword.model.Word;
import com.opentext.guesstheword.repository.GameRepository;
import com.opentext.guesstheword.repository.GuessRepository;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final WordService wordService;
    private final GuessRepository guessRepository;

    public GameService(GameRepository gameRepository,
                       WordService wordService,
                       GuessRepository guessRepository) {

        this.gameRepository = gameRepository;
        this.wordService = wordService;
        this.guessRepository = guessRepository;
    }

    public Game startGame(User user) {

        LocalDate today = LocalDate.now();

        long gamesToday =
                gameRepository.countByUserAndGameDate(user, today);

        if (gamesToday >= 3) {
            throw new RuntimeException(
                    "You can play only 3 games per day"
            );
        }

        Word randomWord = wordService.getRandomWord();

        if (randomWord == null) {
            throw new RuntimeException("No words available");
        }

        Game game = new Game(user, randomWord);

        return gameRepository.save(game);
    }

    public GuessResult makeGuess(Game game, String guessedWord) {

        if (game.isWon()) {
            throw new RuntimeException("Game is already completed");
        }

        if (game.getGuessCount() >= 5) {
            throw new RuntimeException(
                    "You have already used all 5 guesses"
            );
        }

        if (guessedWord == null ||
                !guessedWord.matches("[A-Z]{5}")) {

            throw new RuntimeException(
                    "Guess must be exactly 5 uppercase letters"
            );
        }

        int nextGuessNumber = game.getGuessCount() + 1;

        Guess guess = new Guess(
                game,
                guessedWord,
                nextGuessNumber
        );

        guessRepository.save(guess);

        game.setGuessCount(nextGuessNumber);

        String secretWord = game.getWord().getWord();

        List<String> result =
                calculateResult(secretWord, guessedWord);

        boolean correct =
                guessedWord.equals(secretWord);

        if (correct) {
            game.setWon(true);
        }

        gameRepository.save(game);

        boolean gameLost =
        !correct && game.getGuessCount() == 5;

return new GuessResult(
        guessedWord,
        result,
        correct,
        game.getGuessCount(),
        game.isWon(),
        gameLost
);
    }

    private List<String> calculateResult(
            String secretWord,
            String guessedWord) {

        List<String> result = new ArrayList<>();

        char[] secret = secretWord.toCharArray();
        char[] guess = guessedWord.toCharArray();

        boolean[] used = new boolean[5];

        // First pass: GREEN
        for (int i = 0; i < 5; i++) {

            if (guess[i] == secret[i]) {
                result.add("GREEN");
                used[i] = true;
            } else {
                result.add(null);
            }
        }

        // Second pass: ORANGE / GREY
        for (int i = 0; i < 5; i++) {

            if (result.get(i) != null) {
                continue;
            }

            boolean found = false;

            for (int j = 0; j < 5; j++) {

                if (!used[j] && guess[i] == secret[j]) {
                    found = true;
                    used[j] = true;
                    break;
                }
            }

            if (found) {
                result.set(i, "ORANGE");
            } else {
                result.set(i, "GREY");
            }
        }

        return result;
    }
}