package com.zettlmeier.speedrunner.category.repository;

import com.zettlmeier.speedrunner.category.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
    Collection<Category> findAllByInactiveDateIsNull();

    Optional<Category> findByNameEqualsAndInactiveDateIsNull(String name);
}
