package com.zettlmeier.speedrunner.game.model;

import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.gamecategory.model.GameCategory;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {
    @Test
    public void differentGameTitleAndCategoriesEqualsReturnsFalse() {
        var game1 = new Game();
        game1.setTitle("name1");
        var category1 = new Category();
        category1.setName("test1");
        var gameCategories1 = new ArrayList<GameCategory>();
        gameCategories1.add(new GameCategory(category1));
        game1.setGameCategories(gameCategories1);
        var game2 = new Game();
        game2.setTitle("name2");
        var category2 = new Category();
        category2.setName("test2");
        var gameCategories2 = new ArrayList<GameCategory>();
        gameCategories2.add(new GameCategory(category2));
        game2.setGameCategories(gameCategories2);

        assertThat(game1.equals(game2)).isEqualTo(false);
    }

    @Test
    public void sameGameTitleAndCategoriesEqualsReturnsTrue() {
        var game1 = new Game();
        game1.setTitle("name1");
        var category1 = new Category();
        category1.setName("test1");
        var gameCategories1 = new ArrayList<GameCategory>();
        gameCategories1.add(new GameCategory(category1));
        game1.setGameCategories(gameCategories1);
        var game2 = new Game();
        game2.setTitle("name1");
        var category2 = new Category();
        category2.setName("test1");
        var gameCategories2 = new ArrayList<GameCategory>();
        gameCategories2.add(new GameCategory(category2));
        game2.setGameCategories(gameCategories2);

        assertThat(game1.equals(game2)).isEqualTo(true);
    }

    @Test
    public void sameGameObjectEqualsReturnsTrue() {
        var game1 = new Game();
        game1.setTitle("name1");
        var category1 = new Category();
        category1.setName("test1");
        var gameCategories1 = new ArrayList<GameCategory>();
        gameCategories1.add(new GameCategory(category1));
        game1.setGameCategories(gameCategories1);
        var game2 = game1;

        assertThat(game1.equals(game2)).isEqualTo(true);
    }
}
