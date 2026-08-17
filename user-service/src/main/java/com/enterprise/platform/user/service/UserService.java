package com.enterprise.platform.user.service;

import com.enterprise.platform.user.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<Long, User> users = Map.of(
        1001L, new User(1001L, "John", "john@example.com"),
        1002L, new User(1002L, "Alice", "alice@example.com"),
        1003L, new User(1003L, "Bob", "bob@example.com")
    );

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }
}
