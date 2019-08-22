package com.zettlmeier.speedrunner.speedrun.service;

import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.speedrun.model.Speedrun;
import com.zettlmeier.speedrunner.user.model.User;

import java.util.Collection;

public interface SpeedrunService {
    Collection<Speedrun> getSpeedruns(Game game);

    Collection<Speedrun> getSpeedruns(User user);

    Collection<Speedrun> getSpeedruns(Game game, User user);

    Collection<Speedrun> getSpeedruns(Game game, Category category);

    Collection<Speedrun> getSpeedruns(Game game, Category category, User user);

    Speedrun createSpeedrun(Speedrun speedrun);
}
