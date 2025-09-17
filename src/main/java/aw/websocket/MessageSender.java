package aw.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSender {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void send(String topic, Object message) {
        simpMessagingTemplate.convertAndSend(topic, message);
    }
}
