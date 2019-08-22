package com.zettlmeier.speedrunner.speedrun.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.entities.service.RestResponseEntityBuilder;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.game.service.GameService;
import com.zettlmeier.speedrunner.gamecategory.model.GameCategory;
import com.zettlmeier.speedrunner.speedrun.model.Speedrun;
import com.zettlmeier.speedrunner.speedrun.service.SpeedrunService;
import com.zettlmeier.speedrunner.user.model.User;
import com.zettlmeier.speedrunner.user.service.UserService;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SpeedrunController.class)
public class SpeedrunControllerTest {
    @MockBean
    private RestResponseEntityBuilder restResponseEntityBuilder;

    @MockBean
    private SpeedrunService speedrunService;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;

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
    }

    @Test
    public void getSpeedrunsByGameTitleAndCategoryTest() throws Exception {
        var speedruns = new ArrayList<Speedrun>();
        var speedrun = new Speedrun();
        var category = new Category();
        category.setName("Any");
        var game = new Game();
        game.setTitle("COD");
        var gameCategories = new ArrayList<GameCategory>();
        var gameCategory = new GameCategory(category);
        gameCategories.add(gameCategory);
        game.setGameCategories(gameCategories);
        var user = new User();
        user.setUserName("kzettlmeier");
        speedrun.setCategory(category);
        speedrun.setGame(game);
        speedrun.setUser(user);
        speedrun.setDuration(1000L);
        speedruns.add(speedrun);

        Mockito.when(gameService.getGame(anyString())).thenReturn(game);
        Mockito.when(speedrunService.getSpeedruns(any(Game.class), any(Category.class))).thenReturn(speedruns);

        this.mockMvc.perform(get("/speedrun?gameTitle=COD&category=Any"))
                .andDo(result -> {
                    var json = testMapper.readTree(result.getResponse().getContentAsByteArray());
                    assertThat(json.get(0).at("/game/title").asText()).isEqualTo("COD");
                    assertThat(json.get(0).at("/game/gameCategories").get(0).at("/category/name").asText()).isEqualTo("Any");
                    assertThat(json.get(0).at("/user/userName").asText()).isEqualTo("kzettlmeier");
                    assertThat(json.get(0).at("/duration").asText()).isEqualTo("1000");
                });

        Mockito.verify(gameService, times(1)).getGame(anyString());
        Mockito.verify(speedrunService, times(1)).getSpeedruns(any(Game.class), any(Category.class));
    }

    @Test
    public void getSpeedrunsByGameTitleTest() throws Exception {
        var speedruns = new ArrayList<Speedrun>();
        var speedrun = new Speedrun();
        var category = new Category();
        category.setName("Any");
        var game = new Game();
        game.setTitle("COD");
        var gameCategories = new ArrayList<GameCategory>();
        var gameCategory = new GameCategory(category);
        gameCategories.add(gameCategory);
        game.setGameCategories(gameCategories);
        var user = new User();
        user.setUserName("kzettlmeier");
        speedrun.setCategory(category);
        speedrun.setGame(game);
        speedrun.setUser(user);
        speedrun.setDuration(1000L);
        speedruns.add(speedrun);

        Mockito.when(gameService.getGame(anyString())).thenReturn(game);
        Mockito.when(speedrunService.getSpeedruns(any(Game.class))).thenReturn(speedruns);

        this.mockMvc.perform(get("/speedrun?gameTitle=COD"))
                .andDo(result -> {
                    var json = testMapper.readTree(result.getResponse().getContentAsByteArray());
                    assertThat(json.get(0).at("/game/title").asText()).isEqualTo("COD");
                    assertThat(json.get(0).at("/game/gameCategories").get(0).at("/category/name").asText()).isEqualTo("Any");
                    assertThat(json.get(0).at("/user/userName").asText()).isEqualTo("kzettlmeier");
                    assertThat(json.get(0).at("/duration").asText()).isEqualTo("1000");
                });

        Mockito.verify(gameService, times(1)).getGame(anyString());
        Mockito.verify(speedrunService, times(1)).getSpeedruns(any(Game.class));
    }

    @Test
    public void getSpeedrunsByUserTest() throws Exception {
        var speedruns = new ArrayList<Speedrun>();
        var speedrun = new Speedrun();
        var category = new Category();
        category.setName("Any");
        var game = new Game();
        game.setTitle("COD");
        var gameCategories = new ArrayList<GameCategory>();
        var gameCategory = new GameCategory(category);
        gameCategories.add(gameCategory);
        game.setGameCategories(gameCategories);
        var user = new User();
        user.setUserName("kzettlmeier");
        speedrun.setCategory(category);
        speedrun.setGame(game);
        speedrun.setUser(user);
        speedrun.setDuration(1000L);
        speedruns.add(speedrun);

        Mockito.when(userService.getUser(anyString())).thenReturn(user);
        Mockito.when(speedrunService.getSpeedruns(any(User.class))).thenReturn(speedruns);

        this.mockMvc.perform(get("/speedrun?userName=kzettlmeier"))
                .andDo(result -> {
                    var json = testMapper.readTree(result.getResponse().getContentAsByteArray());
                    assertThat(json.get(0).at("/game/title").asText()).isEqualTo("COD");
                    assertThat(json.get(0).at("/game/gameCategories").get(0).at("/category/name").asText()).isEqualTo("Any");
                    assertThat(json.get(0).at("/user/userName").asText()).isEqualTo("kzettlmeier");
                    assertThat(json.get(0).at("/duration").asText()).isEqualTo("1000");
                });

        Mockito.verify(userService, times(1)).getUser(anyString());
        Mockito.verify(speedrunService, times(1)).getSpeedruns(any(User.class));
    }

    @Test
    public void createSpeedrunTest() throws Exception {
        var speedrun = new Speedrun();
        speedrun.setId(UUID.randomUUID());
        var category = new Category();
        category.setName("Any");
        var game = new Game();
        game.setTitle("COD");
        var gameCategories = new ArrayList<GameCategory>();
        var gameCategory = new GameCategory(category);
        gameCategories.add(gameCategory);
        game.setGameCategories(gameCategories);
        var user = new User();
        user.setUserName("kzettlmeier");
        speedrun.setCategory(category);
        speedrun.setGame(game);
        speedrun.setUser(user);
        speedrun.setDuration(1000L);

        Mockito.when(gameService.getGame(anyString())).thenReturn(game);
        Mockito.when(userService.getUser(anyString())).thenReturn(user);
        Mockito.when(speedrunService.createSpeedrun(any())).thenReturn(speedrun);

        this.mockMvc.perform(post("/speedrun")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testMapper.writeValueAsBytes(speedrun)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().bytes(testMapper.writeValueAsBytes(speedrun)));

        Mockito.verify(gameService, times(1)).getGame("COD");
        Mockito.verify(userService, times(1)).getUser("kzettlmeier");
        Mockito.verify(speedrunService, times(1)).createSpeedrun(speedrun);
    }
}
