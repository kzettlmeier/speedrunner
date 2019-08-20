package com.zettlmeier.speedrunner.category.service;

import com.zettlmeier.speedrunner.category.model.Category;

import java.util.Collection;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> getCategory(String name);

    Collection<Category> getCategories();

    Category createCategory(Category category);
}
