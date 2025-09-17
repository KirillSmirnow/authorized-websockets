package aw.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepository {

    private final List<User> users = List.of(
            new User("Alice", "1854"),
            new User("Bob", "9378"),
            new User("Dan", "0134")
    );

    public Optional<User> findByName(String name) {
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }

    public List<User> findAll() {
        return users;
    }
}
