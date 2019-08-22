package com.zettlmeier.speedrunner.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.category.service.CategoryService;
import com.zettlmeier.speedrunner.entities.service.RestResponseEntityBuilder;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.game.service.GameService;
import com.zettlmeier.speedrunner.gamecategory.model.GameCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {
    @MockBean
    private RestResponseEntityBuilder restResponseEntityBuilder;

    @MockBean
    private GameService gameService;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper testMapper = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        Mockito.when(restResponseEntityBuilder.buildGETResponse(any())).thenCallRealMethod();
        Mockito.when(restResponseEntityBuilder.buildPOSTResponse(any(), anyString())).thenCallRealMethod();
        Mockito.when(restResponseEntityBuilder.buildPUTResponse(any(), anyString())).thenCallRealMethod();
    }

    @Test
    public void getGamesTest() throws Exception {
        var games = new ArrayList<Game>();
        var game = new Game();
        game.setTitle("COD");
        games.add(game);
        Mockito.when(gameService.getGames()).thenReturn(games);

        this.mockMvc.perform(get("/game"))
                .andDo(result -> {
                    var json = testMapper.readTree(result.getResponse().getContentAsByteArray());
                    assertThat(json.get(0).at("/title").asText()).isEqualTo("COD");
                });

        Mockito.verify(gameService, times(1)).getGames();
    }

    @Test
    public void getGameByTitleTest() throws Exception {
        var game = new Game();
        game.setTitle("COD");
        Mockito.when(gameService.getGame(anyString())).thenReturn(game);

        this.mockMvc.perform(get("/game/COD"))
                .andDo(result -> {
                    var json = testMapper.readTree(result.getResponse().getContentAsByteArray());
                    assertThat(json.at("/title").asText()).isEqualTo("COD");
                });

        Mockito.verify(gameService, times(1)).getGame("COD");
    }

    @Test
    public void createGameAlreadyExistingCategoryTest() throws Exception {
        var game = new Game();
        game.setId(UUID.randomUUID());
        game.setTitle("COD");
        Category category = new Category();
        category.setName("Any%");
        GameCategory gameCategory = new GameCategory();
        gameCategory.setCategory(category);
        List<GameCategory> categories = new ArrayList<>();
        categories.add(gameCategory);
        game.setGameCategories(categories);

        Mockito.when(gameService.createGame(any())).thenReturn(game);
        Mockito.when(categoryService.getCategory("Any%")).thenReturn(category);

        this.mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testMapper.writeValueAsBytes(game)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().bytes(testMapper.writeValueAsBytes(game)));

        Mockito.verify(gameService, times(1)).createGame(game);
        Mockito.verify(categoryService, times(1)).getCategory("Any%");
        Mockito.verify(categoryService, times(0)).createCategory(category);
    }

    @Test
    public void createGameNotAlreadyExistingCategoryTest() throws Exception {
        var game = new Game();
        game.setId(UUID.randomUUID());
        game.setTitle("COD");
        Category category = new Category();
        category.setName("Any%");
        GameCategory gameCategory = new GameCategory();
        gameCategory.setCategory(category);
        List<GameCategory> categories = new ArrayList<>();
        categories.add(gameCategory);
        game.setGameCategories(categories);

        Mockito.when(gameService.createGame(any())).thenReturn(game);
        Mockito.when(categoryService.getCategory("Any%")).thenThrow(new NoSuchElementException());
        Mockito.when(categoryService.createCategory(any())).thenReturn(category);

        this.mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testMapper.writeValueAsBytes(game)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().bytes(testMapper.writeValueAsBytes(game)));

        Mockito.verify(gameService, times(1)).createGame(game);
        Mockito.verify(categoryService, times(1)).getCategory("Any%");
        Mockito.verify(categoryService, times(1)).createCategory(category);
    }

    @Test
    public void updateGameTest() throws Exception {
        var game = new Game();
        game.setId(UUID.randomUUID());
        game.setTitle("COD");
        Category category = new Category();
        category.setName("Any%");
        GameCategory gameCategory = new GameCategory();
        gameCategory.setCategory(category);
        List<GameCategory> categories = new ArrayList<>();
        categories.add(gameCategory);
        game.setGameCategories(categories);

        Mockito.when(gameService.getGame(anyString())).thenReturn(game);
        Mockito.when(gameService.updateGame(anyString(), any())).thenReturn(game);
        Mockito.when(categoryService.getCategory("Any%")).thenReturn(category);

        this.mockMvc.perform(put("/game/COD")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testMapper.writeValueAsBytes(game)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(testMapper.writeValueAsBytes(game)));

        Mockito.verify(gameService, times(1)).updateGame("COD", game);
        Mockito.verify(categoryService, times(1)).getCategory("Any%");
    }

    @Test
    public void updateGameSends404Test() throws Exception {
        var game = new Game();
        game.setId(UUID.randomUUID());
        game.setTitle("COD");
        Category category = new Category();
        category.setName("Any%");
        GameCategory gameCategory = new GameCategory();
        gameCategory.setCategory(category);
        List<GameCategory> categories = new ArrayList<>();
        categories.add(gameCategory);
        game.setGameCategories(categories);

        Mockito.when(gameService.updateGame(anyString(), any())).thenThrow(new NoSuchElementException());
        Mockito.when(categoryService.getCategory("Any%")).thenReturn(category);

        this.mockMvc.perform(put("/game/COD")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testMapper.writeValueAsBytes(game)))
                .andDo(print())
                .andExpect(status().isNotFound());

        Mockito.verify(gameService, times(1)).updateGame("COD", game);
        Mockito.verify(categoryService, times(1)).getCategory("Any%");
    }
}
