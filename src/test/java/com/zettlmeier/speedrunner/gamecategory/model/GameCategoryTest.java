package com.zettlmeier.speedrunner.gamecategory.model;

import com.zettlmeier.speedrunner.category.model.Category;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameCategoryTest {
    @Test
    public void differentCategoryEqualsReturnsFalse() {
        var gameCategory = new GameCategory();
        var category1 = new Category();
        category1.setName("test");
        gameCategory.setCategory(category1);

        var gameCategory2 = new GameCategory();
        var category2 = new Category();
        category2.setName("test2");
        gameCategory2.setCategory(category2);

        assertThat(gameCategory.equals(gameCategory2)).isEqualTo(false);
    }

    @Test
    public void sameCategoryEqualsReturnsTrue() {
        var gameCategory = new GameCategory();
        var category1 = new Category();
        category1.setName("test");
        gameCategory.setCategory(category1);

        var gameCategory2 = new GameCategory();
        var category2 = new Category();
        category2.setName("test");
        gameCategory2.setCategory(category2);

        assertThat(gameCategory.equals(gameCategory2)).isEqualTo(true);
    }

    @Test
    public void sameGameCategoryObjectEqualsReturnsTrue() {
        var gameCategory = new GameCategory();
        var category1 = new Category();
        category1.setName("test");
        gameCategory.setCategory(category1);
        var gameCategory2 = gameCategory;

        assertThat(gameCategory.equals(gameCategory2)).isEqualTo(true);
    }
}
