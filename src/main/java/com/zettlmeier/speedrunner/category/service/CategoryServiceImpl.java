package com.zettlmeier.speedrunner.category.service;

import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = Objects.requireNonNull(categoryRepository, "Category Repository is required");
    }

    @Override
    public Category getCategory(String name) {
        var optionalCategory = this.categoryRepository.findByNameEqualsAndInactiveDateIsNull(name);
        if (optionalCategory.isEmpty()) {
            throw new NoSuchElementException("Category not found with name: " + name);
        }

        return optionalCategory.get();
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
