package com.opentext.guesstheword.service;

import com.opentext.guesstheword.model.Game;
import com.opentext.guesstheword.model.Guess;
import com.opentext.guesstheword.model.User;
import com.opentext.guesstheword.repository.GameRepository;
import com.opentext.guesstheword.repository.GuessRepository;
import com.opentext.guesstheword.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GuessRepository guessRepository;

    public AdminService(GameRepository gameRepository,
                        UserRepository userRepository,
                        GuessRepository guessRepository) {

        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.guessRepository = guessRepository;
    }

    public DailyReport getDailyReport() {

        LocalDate today = LocalDate.now();

        List<Game> games = gameRepository.findByGameDate(today);

        Set<Long> users = new HashSet<>();

        long correctGuesses = 0;

        for (Game game : games) {

            users.add(game.getUser().getId());

            if (game.isWon()) {
                correctGuesses++;
            }
        }

        return new DailyReport(
                today.toString(),
                users.size(),
                correctGuesses
        );
    }

    public UserReport getUserReport(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Username not found"));

        List<Game> games = gameRepository.findByUser(user);

        List<UserReport.GameReport> gameReports =
                new ArrayList<>();

        for (Game game : games) {

            List<Guess> guesses =
                    guessRepository.findByGameOrderByGuessNumberAsc(game);

            List<String> guessedWords = guesses.stream()
                    .map(Guess::getGuessedWord)
                    .toList();

            gameReports.add(
                    new UserReport.GameReport(
                            game.getId(),
                            game.getGameDate().toString(),
                            game.getGuessCount(),
                            game.isWon(),
                            guessedWords
                    )
            );
        }

        return new UserReport(
                user.getUsername(),
                gameReports
        );
    }
}