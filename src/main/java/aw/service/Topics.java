package aw.service;

import org.springframework.stereotype.Component;

@Component
public class Topics {

    public String updatesFor(User user) {
        return "/users/%s/updates".formatted(user.getName());
    }
}
