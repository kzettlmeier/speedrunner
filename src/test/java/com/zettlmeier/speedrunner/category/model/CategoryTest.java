package com.zettlmeier.speedrunner.category.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest {
    @Test
    public void differentCategoryNameEqualsReturnsFalse() {
        var category1 = new Category();
        category1.setName("name1");
        var category2 = new Category();
        category2.setName("name2");

        assertThat(category1.equals(category2)).isEqualTo(false);
    }

    @Test
    public void sameCategoryNameEqualsReturnsTrue() {
        var category1 = new Category();
        category1.setName("name1");
        var category2 = new Category();
        category2.setName("name1");

        assertThat(category1.equals(category2)).isEqualTo(true);
    }

    @Test
    public void sameCategoryObjectEqualsReturnsTrue() {
        var category1 = new Category();
        category1.setName("name1");
        var category2 = category1;

        assertThat(category1.equals(category2)).isEqualTo(true);
    }
}
