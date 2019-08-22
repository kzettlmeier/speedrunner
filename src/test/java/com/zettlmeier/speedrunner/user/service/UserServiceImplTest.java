package com.zettlmeier.speedrunner.user.service;

import com.zettlmeier.speedrunner.user.model.User;
import com.zettlmeier.speedrunner.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void getUserTest() {
        var user = new User();
        user.setUserName("test");

        Mockito.when(userRepository.findByUserNameEqualsAndInactiveDateIsNull(anyString())).thenReturn(Optional.of(user));

        var getUser = userService.getUser("test");

        assertSame(user, getUser);

        Mockito.verify(userRepository, Mockito.times(1)).findByUserNameEqualsAndInactiveDateIsNull(anyString());
    }

    @Test
    public void createUserTest() {
        var user = new User();
        user.setUserName("test");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        var savedUser = userService.createUser(user);

        assertSame(user, savedUser);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }
}
