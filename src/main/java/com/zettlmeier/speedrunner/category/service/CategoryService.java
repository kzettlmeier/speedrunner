package com.zettlmeier.speedrunner.category.service;

import com.zettlmeier.speedrunner.category.model.Category;

import java.util.Collection;

public interface CategoryService {
    Category getCategory(String name);

    Collection<Category> getCategories();

    Category createCategory(Category category);
}
