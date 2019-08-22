package com.zettlmeier.speedrunner.user.repository;

import com.zettlmeier.speedrunner.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByUserNameEqualsAndInactiveDateIsNull(String userName);
}
