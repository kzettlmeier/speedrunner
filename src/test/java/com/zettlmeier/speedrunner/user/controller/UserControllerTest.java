package com.zettlmeier.speedrunner.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zettlmeier.speedrunner.entities.service.RestResponseEntityBuilder;
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
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockBean
    private RestResponseEntityBuilder restResponseEntityBuilder;

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
    public void getUserTest() throws Exception {
        var user = new User();
        user.setUserName("kzettlmeier");
        Mockito.when(userService.getUser(anyString())).thenReturn(user);

        this.mockMvc.perform(get("/user?userName=kzettlmeier"))
                .andDo(result -> {
                    var json = testMapper.readTree(result.getResponse().getContentAsByteArray());
                    assertThat(json.at("/userName").asText()).isEqualTo("kzettlmeier");
                });

        Mockito.verify(userService, times(1)).getUser(anyString());
    }

    @Test
    public void createUserTest() throws Exception {
        var user = new User();
        user.setId(UUID.randomUUID());
        user.setUserName("kzettlmeier");

        Mockito.when(userService.createUser(any())).thenReturn(user);

        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testMapper.writeValueAsBytes(user)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().bytes(testMapper.writeValueAsBytes(user)));

        Mockito.verify(userService, times(1)).createUser(user);
    }
}
