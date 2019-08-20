package com.zettlmeier.speedrunner.game.repository;

import com.zettlmeier.speedrunner.game.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends CrudRepository<Game, UUID> {
    Collection<Game> findAllByInactiveDateIsNull();

    Optional<Game> findByTitleAndInactiveDateIsNull(String title);
}
