package com.zettlmeier.speedrunner.gamecategory.repository;

import com.zettlmeier.speedrunner.gamecategory.model.GameCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GameCategoryRepository extends CrudRepository<GameCategory, UUID> {

}
