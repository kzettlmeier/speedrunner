package com.zettlmeier.speedrunner.game.controller;

import com.zettlmeier.speedrunner.category.service.CategoryService;
import com.zettlmeier.speedrunner.entities.service.RestResponseEntityBuilder;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.game.service.GameService;
import com.zettlmeier.speedrunner.gamecategory.model.GameCategory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
public class GameController {
    private static final String GET_RESOURCE_PATH = "/game";
    private static final String POST_RESOURCE_PATH = GET_RESOURCE_PATH;
    private static final String PUT_RESOURCE_PATH = GET_RESOURCE_PATH + "/{title}";

    private final RestResponseEntityBuilder restResponseEntityBuilder;
    private final GameService gameService;
    private final CategoryService categoryService;

    public GameController(RestResponseEntityBuilder restResponseEntityBuilder, GameService gameService, CategoryService categoryService) {
        this.restResponseEntityBuilder = Objects.requireNonNull(restResponseEntityBuilder,  "Rest Entity Response Builder is required");
        this.gameService = Objects.requireNonNull(gameService, "Game Service is required");
        this.categoryService = Objects.requireNonNull(categoryService, "Category Service is required");
    }

    @GetMapping(value = GET_RESOURCE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<Collection<Game>> getGames() {
        return this.restResponseEntityBuilder.buildGETResponse(this.gameService.getGames());
    }

    @PostMapping(value = POST_RESOURCE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    ResponseEntity<Game> createGame(@RequestBody Game game) {
        // First create categories if they don't already exist
        this.createCategoryIfDoesNotExist(game);

        var createdGame = this.gameService.createGame(game);
        return this.restResponseEntityBuilder.buildPOSTResponse(game, createdGame.getId().toString());
    }

    @PutMapping(value = PUT_RESOURCE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    ResponseEntity<Game> updateGame(@PathVariable String title, @RequestBody Game game) {
        try {
            // First create categories if they don't already exist
            this.createCategoryIfDoesNotExist(game);

            var updatedGame = this.gameService.updateGame(title, game);
            return this.restResponseEntityBuilder.buildPUTResponse(updatedGame, updatedGame.getId().toString());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private void createCategoryIfDoesNotExist(Game game) {
        // First create categories if they don't already exist
        var newCategories = new ArrayList<GameCategory>();
        var categories = game.getGameCategories();
        for (var category : categories) {
            var categoryObj = category.getCategory();
            try {
                var existingCategory = this.categoryService.getCategory(categoryObj.getName());
                newCategories.add(new GameCategory(existingCategory));
            } catch (NoSuchElementException ex) {
                newCategories.add(new GameCategory(this.categoryService.createCategory(categoryObj)));
            }

        }
        game.setGameCategories(newCategories);
    }
}
