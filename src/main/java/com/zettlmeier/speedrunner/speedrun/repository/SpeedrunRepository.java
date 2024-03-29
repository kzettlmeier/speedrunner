package com.zettlmeier.speedrunner.speedrun.repository;

import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.speedrun.model.Speedrun;
import com.zettlmeier.speedrunner.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.UUID;

public interface SpeedrunRepository extends CrudRepository<Speedrun, UUID> {
    Collection<Speedrun> findAllByGameEqualsAndInactiveDateIsNullOrderByDurationAsc(Game game);

    Collection<Speedrun> findAllByUserEqualsAndInactiveDateIsNullOrderByDurationAsc(User user);

    Collection<Speedrun> findAllByGameEqualsAndCategoryEqualsAndInactiveDateIsNullOrderByDurationAsc(Game game, Category category);

    Collection<Speedrun> findAllByGameEqualsAndUserEqualsAndInactiveDateIsNullOrderByDurationAsc(Game game, User user);

    Collection<Speedrun> findAllByGameEqualsAndCategoryEqualsAndUserEqualsAndInactiveDateIsNullOrderByDurationAsc(Game game, Category category, User user);
}
