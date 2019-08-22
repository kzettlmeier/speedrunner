package com.zettlmeier.speedrunner.game.service;

import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.game.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = Objects.requireNonNull(gameRepository, "Game Repository is required");
    }

    @Override
    public Game getGame(String gameTitle) {
        var optionalGame = this.gameRepository.findByTitleAndInactiveDateIsNull(gameTitle);
        if (optionalGame.isEmpty()) {
            throw new NoSuchElementException("No game found with title: " + gameTitle);
        }

        return optionalGame.get();
    }

    @Override
    public Collection<Game> getGames() {
        return this.gameRepository.findAllByInactiveDateIsNull();
    }

    @Override
    public Game createGame(Game game) {
        // Assuming that there would be no duplicates via note in requirements
        return this.gameRepository.save(game);
    }

    @Override
    public Game updateGame(String title, Game game) throws NoSuchElementException {
        var existingGame  = this.gameRepository.findByTitleAndInactiveDateIsNull(title);
        if (existingGame.isEmpty()) {
            throw new NoSuchElementException("Game not found with title: " + title);
        }

        var oldGame = existingGame.get();
        // Use the old id
        game.setId(oldGame.getId());
        // Use the old creation date
        game.setCreationDate(oldGame.getCreationDate());
        oldGame.updateFrom(game);
        return this.gameRepository.save(game);
    }
}
