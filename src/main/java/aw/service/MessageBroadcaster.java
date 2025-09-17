package aw.service;

import aw.websocket.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MessageBroadcaster {

    private final UserRepository userRepository;
    private final Topics topics;
    private final MessageSender messageSender;

    @EventListener(ApplicationReadyEvent.class)
    @SneakyThrows
    public void sendToAllUsers() {
        while (true) {
            userRepository.findAll().forEach(user -> {
                var topic = topics.updatesFor(user);
                var message = "Update!/" + LocalDateTime.now();
                messageSender.send(topic, message);
            });
            Thread.sleep(Duration.ofSeconds(3));
        }
    }
}
