package com.zettlmeier.speedrunner.speedrun.service;

import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.speedrun.model.Speedrun;
import com.zettlmeier.speedrunner.speedrun.repository.SpeedrunRepository;
import com.zettlmeier.speedrunner.user.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class SpeedrunServiceImpl implements SpeedrunService {
    private final SpeedrunRepository speedrunRepository;

    public SpeedrunServiceImpl(SpeedrunRepository speedrunRepository) {
        this.speedrunRepository = Objects.requireNonNull(speedrunRepository, "Speedrun Repository is required");
    }

    @Override
    public Collection<Speedrun> getSpeedruns(Game game) {
        var speedruns = this.speedrunRepository.findAllByGameEqualsAndInactiveDateIsNullOrderByDurationAsc(game);
        if (speedruns.size() == 0) {
            throw new NoSuchElementException("No speedruns found for game: " + game.getTitle());
        }

        return speedruns;
    }

    @Override
    public Collection<Speedrun> getSpeedruns(User user) {
        var speedruns = this.speedrunRepository.findAllByUserEqualsAndInactiveDateIsNullOrderByDurationAsc(user);
        if (speedruns.size() == 0) {
            throw new NoSuchElementException("No speedruns found for user: " + user.getUserName());
        }

        return speedruns;
    }

    @Override
    public Collection<Speedrun> getSpeedruns(Game game, User user) {
        var speedruns = this.speedrunRepository.findAllByGameEqualsAndUserEqualsAndInactiveDateIsNullOrderByDurationAsc(game, user);
        if (speedruns.size() == 0) {
            throw new NoSuchElementException("No speedruns found for game: " + game.getTitle() + " and user: " + user.getUserName());
        }

        return speedruns;
    }

    @Override
    public Collection<Speedrun> getSpeedruns(Game game, Category category) {
        var speedruns = this.speedrunRepository.findAllByGameEqualsAndCategoryEqualsAndInactiveDateIsNullOrderByDurationAsc(game, category);
        if (speedruns.size() == 0) {
            throw new NoSuchElementException("No speedruns found for game: " + game.getTitle() + " and category: " + category.getName());
        }

        return speedruns;
    }

    @Override
    public Collection<Speedrun> getSpeedruns(Game game, Category category, User user) {
        var speedruns = this.speedrunRepository.findAllByGameEqualsAndCategoryEqualsAndUserEqualsAndInactiveDateIsNullOrderByDurationAsc(game, category, user);
        if (speedruns.size() == 0) {
            throw new NoSuchElementException("No speedruns found for game: " + game.getTitle() + " and category: " + category.getName() + " and user: " + user.getUserName());
        }

        return speedruns;
    }

    @Override
    public Speedrun createSpeedrun(Speedrun speedrun) {
        // No need to validate if the category exists as per requirements document
        return this.speedrunRepository.save(speedrun);
    }
}
