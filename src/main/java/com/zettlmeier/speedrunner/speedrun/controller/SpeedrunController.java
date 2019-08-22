package com.zettlmeier.speedrunner.speedrun.controller;

import com.zettlmeier.speedrunner.entities.service.RestResponseEntityBuilder;
import com.zettlmeier.speedrunner.game.service.GameService;
import com.zettlmeier.speedrunner.speedrun.model.Speedrun;
import com.zettlmeier.speedrunner.speedrun.service.SpeedrunService;
import com.zettlmeier.speedrunner.user.service.UserService;
import org.h2.util.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
public class SpeedrunController {
    private static final String GET_RESOURCE_PATH = "/speedrun";
    private static final String POST_RESOURCE_PATH = GET_RESOURCE_PATH;

    private final RestResponseEntityBuilder restResponseEntityBuilder;
    private final SpeedrunService speedrunService;
    private final GameService gameService;
    private final UserService userService;

    public SpeedrunController(RestResponseEntityBuilder restResponseEntityBuilder, SpeedrunService speedrunService,
                              GameService gameService, UserService userService) {
        this.restResponseEntityBuilder = Objects.requireNonNull(restResponseEntityBuilder, "Rest Response Entity Builder is required");
        this.speedrunService = Objects.requireNonNull(speedrunService, "Speedrun Service is required");
        this.gameService = Objects.requireNonNull(gameService, "Game Service is required");
        this.userService = Objects.requireNonNull(userService, "User Service is required");
    }

    @GetMapping(value = GET_RESOURCE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<Collection<Speedrun>> getSpeedruns(@RequestParam(required = false) String gameTitle, @RequestParam(required = false) String category,
                                                      @RequestParam(required = false) String userName) {
        // Check if gameTitle and category and userName are given
        if (!StringUtils.isNullOrEmpty(gameTitle) && !StringUtils.isNullOrEmpty(category) && !StringUtils.isNullOrEmpty(userName)) {
            return this.getSpeedrunsByGameAndCategoryAndUser(gameTitle, category, userName);
        }

        // Check if gameTitle and category are given
        if (!StringUtils.isNullOrEmpty(gameTitle) && !StringUtils.isNullOrEmpty(category)) {
            return this.getSpeedrunsByGameAndCategory(gameTitle, category);
        }

        // Check if gameTitle is given
        if (!StringUtils.isNullOrEmpty(gameTitle)) {
            return this.getSpeedrunsByGame(gameTitle);
        }

        // Check if userName is given
        if (!StringUtils.isNullOrEmpty(userName)) {
            return this.getSpeedrunsByUser(userName);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = POST_RESOURCE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    ResponseEntity<Speedrun> createSpeedrun(@RequestBody Speedrun speedrun) {
        try {
            // Check to make sure Game exists (will throw record not found if doesn't exist)
            this.gameService.getGame(speedrun.getGame().getTitle());

            // Check to make sure User exists (will throw record not found if doesn't exist)
            this.userService.getUser(speedrun.getUser().getUserName());

            var createdSpeedrun = this.speedrunService.createSpeedrun(speedrun);
            return this.restResponseEntityBuilder.buildPOSTResponse(createdSpeedrun, createdSpeedrun.getId().toString());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<Collection<Speedrun>> getSpeedrunsByGame(String gameTitle) {
        try {
            // First get the game
            var game = this.gameService.getGame(gameTitle);

            return this.restResponseEntityBuilder.buildGETResponse(this.speedrunService.getSpeedruns(game));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<Collection<Speedrun>> getSpeedrunsByGameAndCategoryAndUser(String gameTitle, String category, String userName) {
        try {
            Collection<Speedrun> speedruns = new ArrayList<>();
            // First get the game
            var game = this.gameService.getGame(gameTitle);

            // Validate that the game has the specified category
            var foundCategory = false;
            var categories = game.getGameCategories();
            for (var gameCategory : categories) {
                var gameCategoryObj = gameCategory.getCategory();
                if (category != null && gameCategoryObj.getName().equals(category)) {
                    foundCategory = true;
                    break;
                }
            }

            if (foundCategory) {
                // Find if user exists
                var user = this.userService.getUser(userName);
                return this.restResponseEntityBuilder.buildGETResponse(this.speedrunService.getSpeedruns(game, user));
            }

            return this.restResponseEntityBuilder.buildGETResponse(speedruns);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<Collection<Speedrun>> getSpeedrunsByGameAndCategory(String gameTitle, String category) {
        try {
            Collection<Speedrun> speedruns = new ArrayList<>();
            // First get the game
            var game = this.gameService.getGame(gameTitle);

            // Validate that the game has the specified category
            var categories = game.getGameCategories();
            for (var gameCategory : categories) {
                var gameCategoryObj = gameCategory.getCategory();
                if (category != null && gameCategoryObj.getName().equals(category)) {
                    speedruns = this.speedrunService.getSpeedruns(game);
                    break;
                }
            }

            return this.restResponseEntityBuilder.buildGETResponse(speedruns);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<Collection<Speedrun>> getSpeedrunsByUser(String userName) {
        try {
            // First get the user
            var user = this.userService.getUser(userName);

            return this.restResponseEntityBuilder.buildGETResponse(this.speedrunService.getSpeedruns(user));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
