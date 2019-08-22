package com.zettlmeier.speedrunner;

import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.category.service.CategoryService;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.game.service.GameService;
import com.zettlmeier.speedrunner.gamecategory.model.GameCategory;
import com.zettlmeier.speedrunner.speedrun.model.Speedrun;
import com.zettlmeier.speedrunner.speedrun.service.SpeedrunService;
import com.zettlmeier.speedrunner.user.model.User;
import com.zettlmeier.speedrunner.user.service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class ApplicationStartup {
    private final GameService gameService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final SpeedrunService speedrunService;

    public ApplicationStartup(GameService gameService, UserService userService, CategoryService categoryService,
                              SpeedrunService speedrunService) {
        this.gameService = Objects.requireNonNull(gameService, "Game Service is required");
        this.userService = Objects.requireNonNull(userService, "User Service is required");
        this.categoryService = Objects.requireNonNull(categoryService, "Category Service is required");
        this.speedrunService = Objects.requireNonNull(speedrunService, "Speedrun Service is required");
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws IOException {
        // Preload data into database from speedrunner.xlsx
        File file = ResourceUtils.getFile("classpath:data/speedrunner.xlsx");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        // Ensure if file exist or not
        if (file.isFile() && file.exists()) {
            // Go through data and insert each row into database
            XSSFSheet worksheet = workbook.getSheet("speedrunner");
            int rowCount = 0;
            for (Row row : worksheet) {
                int cellCount = 0;
                String game = "";
                String category = "";
                String user = "";
                long duration = 0L;
                for (Cell cell : row) {
                    // Check if header row
                    if (rowCount != 0) {
                        // Game
                        if (cellCount == 0) {
                            game = cell.toString();
                        }
                        // Category
                        if (cellCount == 1) {
                            category = cell.toString();
                        }
                        // Player
                        if (cellCount == 2) {
                            user = cell.toString();
                        }
                        // Duration
                        if (cellCount == 5) {
                            duration = (long)cell.getNumericCellValue();
                        }
                    }
                    cellCount++;
                }
                // Check if category exists
                Category categoryObj;
                try {
                    categoryObj = this.categoryService.getCategory(category);
                } catch (NoSuchElementException ex) {
                    // Create the category
                    categoryObj = new Category();
                    categoryObj.setName(category);
                    this.categoryService.createCategory(categoryObj);
                }

                // Check if game exists
                Game gameObj;
                List<GameCategory> gameCategories;
                try {
                    gameObj = this.gameService.getGame(game);
                    // Check if category already in game object, if not add it
                    gameCategories = gameObj.getGameCategories();
                    boolean foundCategory = false;
                    for (GameCategory gameCategory : gameCategories) {
                        Category gameCat = gameCategory.getCategory();
                        if (gameCat != null && gameCat.getName().equals(category)) {
                            foundCategory = true;
                            break;
                        }
                    }
                    if (!foundCategory) {
                        // Add the category
                        List<GameCategory> newList = new ArrayList<>();
                        newList.addAll(gameCategories);
                        newList.add(new GameCategory(categoryObj));
                        gameObj.setGameCategories(newList);
                        this.gameService.updateGame(gameObj.getTitle(), gameObj);
                    }
                } catch (NoSuchElementException ex) {
                    // Create the game
                    gameObj = new Game();
                    gameObj.setTitle(game);
                    gameCategories = new ArrayList<>();
                    gameCategories.add(new GameCategory(categoryObj));
                    gameObj.setGameCategories(gameCategories);
                    this.gameService.createGame(gameObj);
                }

                // Check if user exists
                User userObj;
                try {
                    userObj = this.userService.getUser(user);
                } catch (NoSuchElementException ex) {
                    // Add in user
                    userObj = new User();
                    userObj.setUserName(user);
                    this.userService.createUser(userObj);
                }

                // Create the speedrun
                Speedrun speedrun = new Speedrun();
                speedrun.setGame(gameObj);
                speedrun.setUser(userObj);
                speedrun.setDuration(duration);
                this.speedrunService.createSpeedrun(speedrun);
                rowCount++;
            }
        }
        else {
            System.out.println("speedrunner.xlsx could not be opened");
        }

        fis.close();
    }
}
