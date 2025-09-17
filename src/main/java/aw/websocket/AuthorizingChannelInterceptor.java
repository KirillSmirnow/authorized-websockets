package aw.websocket;

import aw.service.Authorizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizingChannelInterceptor implements ChannelInterceptor {

    private final Authorizer authorizer;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info(">> {}", message);
        var headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        var command = headerAccessor.getCommand();
        var topic = headerAccessor.getDestination();
        var authorization = headerAccessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
        if (command == StompCommand.SUBSCRIBE) {
            try {
                authorizer.authorizeUserForTopic(topic, authorization);
            } catch (Exception e) {
                log.warn("Subscription failed: {}", e.getMessage(), e);
                return null;
            }
        }
        return message;
    }
}
