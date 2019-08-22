package com.zettlmeier.speedrunner.game.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.game.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameServiceImplTest {
    @Mock
    private GameRepository gameRepository;

    private ObjectMapper testMapper = new ObjectMapper();

    @InjectMocks
    private GameServiceImpl gameService;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void getGameTest() {
        var game = new Game();
        game.setTitle("test");

        Mockito.when(gameRepository.findByTitleAndInactiveDateIsNull(anyString())).thenReturn(Optional.of(game));

        var getGame = gameService.getGame("test");

        assertSame(game, getGame);

        Mockito.verify(gameRepository, Mockito.times(1)).findByTitleAndInactiveDateIsNull(anyString());
    }

    @Test
    public void getGamesTest() {
        var games = new ArrayList<Game>();
        var game = new Game();
        game.setTitle("test");
        var game2 = new Game();
        game2.setTitle("test2");
        games.add(game);
        games.add(game2);

        Mockito.when(gameRepository.findAllByInactiveDateIsNull()).thenReturn(games);

        var getGames = gameService.getGames();

        assertSame(games, getGames);

        Mockito.verify(gameRepository, Mockito.times(1)).findAllByInactiveDateIsNull();
    }

    @Test
    public void createGameTest() {
        var game = new Game();
        game.setTitle("test");

        Mockito.when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        var savedGame = gameService.createGame(game);

        assertSame(game, savedGame);

        Mockito.verify(gameRepository, Mockito.times(1)).save(Mockito.any(Game.class));
    }

    @Test
    public void updateGameTest() {
        var game = new Game();
        game.setTitle("test");

        Mockito.when(gameRepository.findByTitleAndInactiveDateIsNull(anyString())).thenReturn(Optional.of(game));
        Mockito.when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        var savedGame = gameService.updateGame("test", game);

        assertSame(game, savedGame);

        Mockito.verify(gameRepository, Mockito.times(1)).save(Mockito.any(Game.class));
    }

    @Test(expected = NoSuchElementException.class)
    public void updateGameWithoutFindingAnExistingGameTest() {
        var game = new Game();
        game.setTitle("test");

        Mockito.when(gameRepository.findByTitleAndInactiveDateIsNull(anyString())).thenReturn(Optional.empty());

        gameService.updateGame("test", game);
    }
}
