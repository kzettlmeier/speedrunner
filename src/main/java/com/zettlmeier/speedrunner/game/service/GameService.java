package com.zettlmeier.speedrunner.game.service;

import com.zettlmeier.speedrunner.game.model.Game;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface GameService {
    Game getGame(String gameTitle);

    Collection<Game> getGames();

    Game createGame(Game game);

    Game updateGame(String title, Game game) throws NoSuchElementException;
}
