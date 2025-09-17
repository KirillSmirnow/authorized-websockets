package aw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Authorizer {

    private final Topics topics;
    private final UserRepository userRepository;

    public void authorizeUserForTopic(String topic, String authorization) {
        try {
            log.info("Authorizing user for topic '{}' with authorization '{}'", topic, authorization);
            validate(topic, authorization);
            var user = getUser(topic);
            checkPassword(user, authorization);
            log.info("User authorized");
        } catch (Exception e) {
            throw (AuthorizationException) new AuthorizationException().initCause(e);
        }
    }

    private void validate(String topic, String authorization) {
        if (topic == null || topic.isBlank()) {
            throw new IllegalArgumentException("No topic");
        }
        if (authorization == null || authorization.isBlank()) {
            throw new IllegalArgumentException("No authorization");
        }
    }

    private User getUser(String topic) {
        var username = topics.extractUsernameFromTopic(topic);
        log.info("Username: {}", username);
        return userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private void checkPassword(User user, String authorization) {
        if (!user.getPassword().equals(authorization)) {
            throw new IllegalArgumentException("Incorrect password");
        }
    }

    public static class AuthorizationException extends RuntimeException {
        public AuthorizationException() {
            super("Forbidden");
        }
    }
}
