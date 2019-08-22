package com.zettlmeier.speedrunner.speedrun.model;

import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.gamecategory.model.GameCategory;
import com.zettlmeier.speedrunner.user.model.User;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class SpeedrunTest {
    @Test
    public void differentGameAndCategoryAndUserEqualsReturnsFalse() {
        var speedrun1 = new Speedrun();
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
        speedrun1.setCategory(category);
        speedrun1.setGame(game);
        speedrun1.setUser(user);
        speedrun1.setDuration(1000L);
        var speedrun2 = new Speedrun();
        var category2 = new Category();
        category2.setName("Any2");
        var game2 = new Game();
        game2.setTitle("COD2");
        var gameCategories2 = new ArrayList<GameCategory>();
        var gameCategory2 = new GameCategory(category);
        gameCategories2.add(gameCategory2);
        game2.setGameCategories(gameCategories2);
        var user2 = new User();
        user2.setUserName("kzettlmeier2");
        speedrun2.setCategory(category);
        speedrun2.setGame(game);
        speedrun2.setUser(user);
        speedrun2.setDuration(1002L);

        assertThat(speedrun1.equals(speedrun2)).isEqualTo(false);
    }

    @Test
    public void sameGameAndCategoryAndUserEqualsReturnsTrue() {
        var speedrun1 = new Speedrun();
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
        speedrun1.setCategory(category);
        speedrun1.setGame(game);
        speedrun1.setUser(user);
        speedrun1.setDuration(1000L);
        var speedrun2 = new Speedrun();
        var category2 = new Category();
        category2.setName("Any");
        var game2 = new Game();
        game2.setTitle("COD");
        var gameCategories2 = new ArrayList<GameCategory>();
        var gameCategory2 = new GameCategory(category);
        gameCategories2.add(gameCategory2);
        game2.setGameCategories(gameCategories2);
        var user2 = new User();
        user2.setUserName("kzettlmeier");
        speedrun2.setCategory(category);
        speedrun2.setGame(game);
        speedrun2.setUser(user);
        speedrun2.setDuration(1000L);

        assertThat(speedrun1.equals(speedrun2)).isEqualTo(true);
    }

    @Test
    public void sameSpeedrunObjectEqualsReturnsTrue() {
        var speedrun1 = new Speedrun();
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
        speedrun1.setCategory(category);
        speedrun1.setGame(game);
        speedrun1.setUser(user);
        speedrun1.setDuration(1000L);
        var speedrun2 = speedrun1;

        assertThat(speedrun1.equals(speedrun2)).isEqualTo(true);
    }
}
