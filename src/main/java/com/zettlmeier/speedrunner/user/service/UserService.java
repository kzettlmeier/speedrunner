package com.zettlmeier.speedrunner.user.service;

import com.zettlmeier.speedrunner.user.model.User;

public interface UserService {
    User getUser(String userName);

    User createUser(User user);
}
