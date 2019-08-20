package com.zettlmeier.speedrunner.category.controller;

import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.category.service.CategoryService;
import com.zettlmeier.speedrunner.entities.service.RestResponseEntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Objects;

@Controller
public class CategoryController {
    private static final String GET_RESOURCE_PATH = "/category";

    private final RestResponseEntityBuilder restResponseEntityBuilder;
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(RestResponseEntityBuilder restResponseEntityBuilder, CategoryService categoryService) {
        this.restResponseEntityBuilder = Objects.requireNonNull(restResponseEntityBuilder, "Rest Entity Response Builder is required");
        this.categoryService = Objects.requireNonNull(categoryService, "Category Service is required");
    }

    @GetMapping(value = GET_RESOURCE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<Collection<Category>> getCategories() {
        return this.restResponseEntityBuilder.buildGETResponse(this.categoryService.getCategories());
    }
}
