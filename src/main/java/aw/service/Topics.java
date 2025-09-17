package aw.service;

import org.springframework.stereotype.Component;

@Component
public class Topics {

    public String updatesFor(User user) {
        return "/users/%s/updates".formatted(user.getName());
    }

    public String extractUsernameFromTopic(String topic) {
        if (!topic.matches("/users/[^/]+/updates")) {
            throw new IllegalArgumentException("Invalid topic");
        }
        try {
            return topic.split("/")[2];
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid topic", e);
        }
    }
}
