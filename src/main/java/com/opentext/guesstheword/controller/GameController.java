package com.opentext.guesstheword.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opentext.guesstheword.model.Game;
import com.opentext.guesstheword.model.User;
import com.opentext.guesstheword.repository.GameRepository;
import com.opentext.guesstheword.repository.UserRepository;
import com.opentext.guesstheword.service.GameService;
import com.opentext.guesstheword.service.GuessResult;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public GameController(GameService gameService,
                           UserRepository userRepository,
                           GameRepository gameRepository) {

        this.gameService = gameService;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startGame(
            @RequestParam String username) {

        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() ->
                            new RuntimeException("Username not found"));

            Game game = gameService.startGame(user);

            return ResponseEntity.ok(new GameResponse(
                    game.getId(),
                    "Game started",
                    game.getGuessCount()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/{gameId}/guess")
    public ResponseEntity<?> makeGuess(
            @PathVariable Long gameId,
            @RequestParam String guessedWord) {

        try {
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() ->
                            new RuntimeException("Game not found"));

            GuessResult result =
                    gameService.makeGuess(game, guessedWord);

            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    public record GameResponse(
            Long gameId,
            String message,
            int guessCount
    ) {
    }
}