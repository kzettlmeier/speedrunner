package com.zettlmeier.speedrunner.user.controller;

import com.zettlmeier.speedrunner.entities.service.RestResponseEntityBuilder;
import com.zettlmeier.speedrunner.user.model.User;
import com.zettlmeier.speedrunner.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
public class UserController {
    private static final String GET_RESOURCE_PATH = "/user";
    private static final String POST_RESOURCE_PATH = GET_RESOURCE_PATH;

    private final RestResponseEntityBuilder restResponseEntityBuilder;
    private final UserService userService;

    public UserController(RestResponseEntityBuilder restResponseEntityBuilder, UserService userService) {
        this.restResponseEntityBuilder = Objects.requireNonNull(restResponseEntityBuilder, "Rest Response Entity Builder is required");
        this.userService = Objects.requireNonNull(userService, "User Service is required");
    }

    @GetMapping(value = GET_RESOURCE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<User> getUser(@RequestParam(required = true) String userName) {
        try {
            return this.restResponseEntityBuilder.buildGETResponse(this.userService.getUser(userName));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = POST_RESOURCE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<User> createUser(@RequestBody User user) {
        var createdUser = this.userService.createUser(user);
        return this.restResponseEntityBuilder.buildPOSTResponse(createdUser, createdUser.getId().toString());
    }
}
