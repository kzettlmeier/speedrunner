package com.zettlmeier.speedrunner.category.service;

import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = Objects.requireNonNull(categoryRepository, "Category Repository is required");
    }

    @Override
    public Optional<Category> getCategory(String name) {
        return this.categoryRepository.findByNameEqualsAndInactiveDateIsNull(name);
    }

    @Override
    public Collection<Category> getCategories() {
        return this.categoryRepository.findAllByInactiveDateIsNull();
    }

    @Override
    public Category createCategory(Category category) {
        // No need to validate if the category exists as per requirements document
        return this.categoryRepository.save(category);
    }
}
