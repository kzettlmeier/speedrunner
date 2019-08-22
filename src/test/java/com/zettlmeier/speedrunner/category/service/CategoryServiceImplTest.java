package com.zettlmeier.speedrunner.category.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.category.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    private ObjectMapper testMapper = new ObjectMapper();

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void getCategorySuccessTest() {
        var category = new Category();
        category.setName("test");

        Mockito.when(categoryRepository.findByNameEqualsAndInactiveDateIsNull(Mockito.anyString())).thenReturn(Optional.of(category));

        var getCategory = categoryService.getCategory("test");

        assertSame(category, getCategory);

        Mockito.verify(categoryRepository, Mockito.times(1)).findByNameEqualsAndInactiveDateIsNull(Mockito.anyString());
    }

    @Test(expected = NoSuchElementException.class)
    public void getCategorySuccessThrowsNoSuchElementExceptionTest() {
        Mockito.when(categoryRepository.findByNameEqualsAndInactiveDateIsNull(Mockito.anyString())).thenReturn(Optional.empty());

        categoryService.getCategory("test");
    }

    @Test
    public void getCategoriesTest() {
        var category1 = new Category();
        category1.setName("test");
        var category2 = new Category();
        category2.setName("test2");
        var categories = new ArrayList<Category>();
        categories.add(category1);
        categories.add(category2);

        Mockito.when(categoryRepository.findAllByInactiveDateIsNull()).thenReturn(categories);

        var getCategories = categoryService.getCategories();

        assertSame(categories, getCategories);

        Mockito.verify(categoryRepository, Mockito.times(1)).findAllByInactiveDateIsNull();
    }

    @Test
    public void createCategoryTest() {
        var category = new Category();
        category.setName("test");

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        var savedCategory = categoryService.createCategory(category);

        assertSame(category, savedCategory);

        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }
}
