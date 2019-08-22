package com.zettlmeier.speedrunner.speedrun.service;

import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.gamecategory.model.GameCategory;
import com.zettlmeier.speedrunner.speedrun.model.Speedrun;
import com.zettlmeier.speedrunner.speedrun.repository.SpeedrunRepository;
import com.zettlmeier.speedrunner.user.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

public class SpeedrunServiceImplTest {
    @Mock
    private SpeedrunRepository speedrunRepository;

    @InjectMocks
    private SpeedrunServiceImpl speedrunService;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void getSpeedrunsByGameTest() {
        var speedruns = new ArrayList<Speedrun>();
        var speedrun = new Speedrun();
        speedrun.setId(UUID.randomUUID());
        var category = new Category();
        category.setName("Any");
        var game = new Game();
        game.setTitle("COD");
        var gameCategories = new ArrayList<GameCategory>();
        var gameCategory = new GameCategory(category);
        gameCategories.add(gameCategory);
        game.setGameCategories(gameCategories);
        var user = new User();
        user.setUserName("kzettlmeier");
        speedrun.setCategory(category);
        speedrun.setGame(game);
        speedrun.setUser(user);
        speedrun.setDuration(1000L);
        speedruns.add(speedrun);

        Mockito.when(speedrunRepository.findAllByGameEqualsAndInactiveDateIsNullOrderByDurationAsc(any(Game.class))).thenReturn(speedruns);

        var getSpeedruns = speedrunService.getSpeedruns(game);

        assertSame(speedruns, getSpeedruns);

        Mockito.verify(speedrunRepository, Mockito.times(1)).findAllByGameEqualsAndInactiveDateIsNullOrderByDurationAsc(any(Game.class));
    }

    @Test
    public void getSpeedrunsByUserTest() {
        var speedruns = new ArrayList<Speedrun>();
        var speedrun = new Speedrun();
        speedrun.setId(UUID.randomUUID());
        var category = new Category();
        category.setName("Any");
        var game = new Game();
        game.setTitle("COD");
        var gameCategories = new ArrayList<GameCategory>();
        var gameCategory = new GameCategory(category);
        gameCategories.add(gameCategory);
        game.setGameCategories(gameCategories);
        var user = new User();
        user.setUserName("kzettlmeier");
        speedrun.setCategory(category);
        speedrun.setGame(game);
        speedrun.setUser(user);
        speedrun.setDuration(1000L);
        speedruns.add(speedrun);

        Mockito.when(speedrunRepository.findAllByUserEqualsAndInactiveDateIsNullOrderByDurationAsc(any(User.class))).thenReturn(speedruns);

        var getSpeedruns = speedrunService.getSpeedruns(user);

        assertSame(speedruns, getSpeedruns);

        Mockito.verify(speedrunRepository, Mockito.times(1)).findAllByUserEqualsAndInactiveDateIsNullOrderByDurationAsc(any(User.class));
    }

    @Test
    public void getSpeedrunsByGameAndUserTest() {
        var speedruns = new ArrayList<Speedrun>();
        var speedrun = new Speedrun();
        speedrun.setId(UUID.randomUUID());
        var category = new Category();
        category.setName("Any");
        var game = new Game();
        game.setTitle("COD");
        var gameCategories = new ArrayList<GameCategory>();
        var gameCategory = new GameCategory(category);
        gameCategories.add(gameCategory);
        game.setGameCategories(gameCategories);
        var user = new User();
        user.setUserName("kzettlmeier");
        speedrun.setCategory(category);
        speedrun.setGame(game);
        speedrun.setUser(user);
        speedrun.setDuration(1000L);
        speedruns.add(speedrun);

        Mockito.when(speedrunRepository.findAllByGameEqualsAndUserEqualsAndInactiveDateIsNullOrderByDurationAsc(any(Game.class), any(User.class))).thenReturn(speedruns);

        var getSpeedruns = speedrunService.getSpeedruns(game, user);

        assertSame(speedruns, getSpeedruns);

        Mockito.verify(speedrunRepository, Mockito.times(1)).findAllByGameEqualsAndUserEqualsAndInactiveDateIsNullOrderByDurationAsc(any(Game.class), any(User.class));
    }

    @Test
    public void getSpeedrunsByGameAndCategoryTest() {
        var speedruns = new ArrayList<Speedrun>();
        var speedrun = new Speedrun();
        speedrun.setId(UUID.randomUUID());
        var category = new Category();
        category.setName("Any");
        var game = new Game();
        game.setTitle("COD");
        var gameCategories = new ArrayList<GameCategory>();
        var gameCategory = new GameCategory(category);
        gameCategories.add(gameCategory);
        game.setGameCategories(gameCategories);
        var user = new User();
        user.setUserName("kzettlmeier");
        speedrun.setCategory(category);
        speedrun.setGame(game);
        speedrun.setUser(user);
        speedrun.setDuration(1000L);
        speedruns.add(speedrun);

        Mockito.when(speedrunRepository.findAllByGameEqualsAndCategoryEqualsAndInactiveDateIsNullOrderByDurationAsc(any(Game.class), any(Category.class))).thenReturn(speedruns);

        var getSpeedruns = speedrunService.getSpeedruns(game, category);

        assertSame(speedruns, getSpeedruns);

        Mockito.verify(speedrunRepository, Mockito.times(1)).findAllByGameEqualsAndCategoryEqualsAndInactiveDateIsNullOrderByDurationAsc(any(Game.class), any(Category.class));
    }

    @Test
    public void getSpeedrunsByGameAndCategoryAndUserTest() {
        var speedruns = new ArrayList<Speedrun>();
        var speedrun = new Speedrun();
        speedrun.setId(UUID.randomUUID());
        var category = new Category();
        category.setName("Any");
        var game = new Game();
        game.setTitle("COD");
        var gameCategories = new ArrayList<GameCategory>();
        var gameCategory = new GameCategory(category);
        gameCategories.add(gameCategory);
        game.setGameCategories(gameCategories);
        var user = new User();
        user.setUserName("kzettlmeier");
        speedrun.setCategory(category);
        speedrun.setGame(game);
        speedrun.setUser(user);
        speedrun.setDuration(1000L);
        speedruns.add(speedrun);

        Mockito.when(speedrunRepository.findAllByGameEqualsAndCategoryEqualsAndUserEqualsAndInactiveDateIsNullOrderByDurationAsc(any(Game.class), any(Category.class), any(User.class))).thenReturn(speedruns);

        var getSpeedruns = speedrunService.getSpeedruns(game, category, user);

        assertSame(speedruns, getSpeedruns);

        Mockito.verify(speedrunRepository, Mockito.times(1)).findAllByGameEqualsAndCategoryEqualsAndUserEqualsAndInactiveDateIsNullOrderByDurationAsc(any(Game.class), any(Category.class), any(User.class));
    }

    @Test
    public void createSpeedrunTest() {
        var speedrun = new Speedrun();
        speedrun.setId(UUID.randomUUID());
        var category = new Category();
        category.setName("Any");
        var game = new Game();
        game.setTitle("COD");
        var gameCategories = new ArrayList<GameCategory>();
        var gameCategory = new GameCategory(category);
        gameCategories.add(gameCategory);
        game.setGameCategories(gameCategories);
        var user = new User();
        user.setUserName("kzettlmeier");
        speedrun.setCategory(category);
        speedrun.setGame(game);
        speedrun.setUser(user);
        speedrun.setDuration(1000L);

        Mockito.when(speedrunRepository.save(Mockito.any(Speedrun.class))).thenReturn(speedrun);

        var savedSpeedrun = speedrunService.createSpeedrun(speedrun);

        assertSame(speedrun, savedSpeedrun);

        Mockito.verify(speedrunRepository, Mockito.times(1)).save(Mockito.any(Speedrun.class));
    }
}
