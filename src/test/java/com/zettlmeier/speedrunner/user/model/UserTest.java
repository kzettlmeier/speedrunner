package com.zettlmeier.speedrunner.user.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    public void differentUserNameEqualsReturnsFalse() {
        var user1 = new User();
        user1.setUserName("test1");
        var user2 = new User();
        user2.setUserName("test2");

        assertThat(user1.equals(user2)).isEqualTo(false);
    }

    @Test
    public void sameUserNameEqualsReturnsTrue() {
        var user1 = new User();
        user1.setUserName("test1");
        var user2 = new User();
        user2.setUserName("test1");

        assertThat(user1.equals(user2)).isEqualTo(true);
    }

    @Test
    public void sameUserObjectEqualsReturnsTrue() {
        var user1 = new User();
        user1.setUserName("test1");
        var user2 = user1;

        assertThat(user1.equals(user2)).isEqualTo(true);
    }
}
