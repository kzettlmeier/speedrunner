package com.zettlmeier.speedrunner.user.service;

import com.zettlmeier.speedrunner.user.model.User;
import com.zettlmeier.speedrunner.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository, "User Repository is required");
    }

    @Override
    public User getUser(String userName) {
        var optionalUser = this.userRepository.findByUserNameEqualsAndInactiveDateIsNull(userName);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("User not found with username: " + userName);
        }

        return optionalUser.get();
    }

    @Override
    public User createUser(User user) {
        // No need to validate if the category exists as per requirements document
        return this.userRepository.save(user);
    }
}
